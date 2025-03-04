package controller;

import dao.StartupProjectsDAO;
import dao.UserDAO;
import dto.StartupProjectsDTO;
import dto.UserDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "MainController", urlPatterns = {"/MainController"})
public class MainController extends HttpServlet {

    private static final String LOGIN_PAGE = "login.jsp";
    private StartupProjectsDAO spdao = new StartupProjectsDAO();

    //ham nay de lay ra user 
    public UserDTO getUser(String strUserID) {
        UserDAO udao = new UserDAO();
        UserDTO user = udao.readByID(strUserID);
        return user;
    }

    public boolean isValidLogin(String strUserID, String strPassword) {
        UserDTO user = getUser(strUserID);
        if (user != null && user.getPassword().equals(strPassword)) {
            return true;
        } else {
            return false;
        }
    }

    private void search(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String searchTerm = request.getParameter("searchTerm");
        if (searchTerm == null) {
            searchTerm = "";
        }
        List<StartupProjectsDTO> projects = spdao.search(searchTerm);
        request.setAttribute("projects", projects);
        request.setAttribute("searchTerm", searchTerm);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = LOGIN_PAGE;
        try {
            String action = request.getParameter("action");
            if (action == null) {
                url = LOGIN_PAGE;
            }

            if (action != null && action.equals("login")) {
                //login aciton
                String strUserID = request.getParameter("strUserID");
                String strPassword = request.getParameter("strPassword");
                if (isValidLogin(strUserID, strPassword)) {

                    UserDTO user = getUser(strUserID);
                    request.getSession().setAttribute("user", user);

                    // Kiểm tra user có null không trước khi lấy role
                    if (user != null && user.getRole() != null) {
                        if ("Founder".equals(user.getRole())) {
                            url = "search.jsp";
                            search(request, response);
                        } else if ("Team Member".equals(user.getRole())) {
                            url = "search1.jsp";
                        } else {
                            request.setAttribute("message", "Unauthorized role.");
                            url = LOGIN_PAGE;
                        }
                    } else {
                        request.setAttribute("message", "User role is not defined.");
                        url = LOGIN_PAGE;
                    }
                } else {
                    url = "login.jsp";
                    request.setAttribute("message", "Incorrect User or Password! Please try again.");
                }
            } else if (action != null && action.equals("logout")) {
                url = "login.jsp";
                request.getSession().invalidate();
            } else if (action != null && action.equals("search")) {
                url = "search.jsp";
                search(request, response);
            } else if (action != null && action.equals("update")) {
                int projectID = Integer.parseInt(request.getParameter("id"));

                StartupProjectsDTO project = spdao.getProjectByID(projectID);

                if (project != null) {
                    request.setAttribute("project", project);
                    url = "update.jsp";
                } else {
                    request.setAttribute("message", "Project Not Found!");
                    url = "search.jsp";
                }
            } else if (action != null && action.equals("doUpdate")) {
                int projectID = Integer.parseInt(request.getParameter("projectId"));
                String newStatus = request.getParameter("newStatus");

                boolean success = spdao.updateStatus(projectID, newStatus);

                if (success) {
                    request.setAttribute("message", "Successful updated!");
                } else {
                    request.setAttribute("message", "Update Fail!");
                }
                search(request, response);
                url = "search.jsp";
            } else if (action != null && action.equals("addProject")) {
                String projectName = request.getParameter("projectName");
                String description = request.getParameter("description");
                String status = request.getParameter("status");
                String estimatedLaunch = request.getParameter("estimatedLaunch");

                String errorMessage = null;
                java.sql.Date estimatedLaunchDate = null;

                if (projectName == null || projectName.trim().isEmpty()) {
                    errorMessage = "Project Name cannot be empty!";
                } else if (description == null || description.trim().isEmpty()) {
                    errorMessage = "Description cannot be empty!";
                } else if (status == null || status.trim().isEmpty()) {
                    errorMessage = "Invalid status selected!";
                } else if (estimatedLaunch == null || estimatedLaunch.trim().isEmpty()) {
                    errorMessage = "Estimated Launch date cannot be empty!";
                } else {
                    try {
                        estimatedLaunchDate = java.sql.Date.valueOf(estimatedLaunch);
                    } catch (IllegalArgumentException e) {
                        errorMessage = "Invalid date format!";
                    }
                }
                if (errorMessage != null) {
                    request.setAttribute("message", errorMessage);
                    url = "add.jsp";
                } else {
                    StartupProjectsDTO newProject = new StartupProjectsDTO(0, projectName, description, status, estimatedLaunchDate);
                    boolean success = spdao.create(newProject);

                    if (success) {
                        request.setAttribute("message", "Project added successfully!");
                        url = "MainController?action=search"; // Quay lại search.jsp                    }
                    } else {
                        request.setAttribute("message", "Failed to add project.");
                        url = "add.jsp"; // Giữ nguyên trang nếu có lỗi
                    }
                }
            }
        } catch (Exception e) {
            log("Error at MainController: " + e.toString());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            if (!url.equals("MainController")) {
                rd.forward(request, response);
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
