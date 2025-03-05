package controller;

import dao.StartupProjectsDAO;
import dao.UserDAO;
import dto.StartupProjectsDTO;
import dto.UserDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.AuthenUtils;

@WebServlet(name = "MainController", urlPatterns = {"/MainController"})
public class MainController extends HttpServlet {

    private static final String LOGIN_PAGE = "login.jsp";
    private StartupProjectsDAO spdao = new StartupProjectsDAO();

    private String processSearch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String searchTerm = request.getParameter("searchTerm");
        if (searchTerm == null) {
            searchTerm = "";
        }
        List<StartupProjectsDTO> projects = spdao.search(searchTerm);
        request.setAttribute("projects", projects);
        request.setAttribute("searchTerm", searchTerm);
        return "search.jsp";
    }

    private String processLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = LOGIN_PAGE;
        //login aciton
        String strUserID = request.getParameter("strUserID");
        String strPassword = request.getParameter("strPassword");
        if (AuthenUtils.isValidLogin(strUserID, strPassword)) {
            url = "search.jsp";
            UserDTO user = AuthenUtils.getUser(strUserID);
            request.getSession().setAttribute("user", user);
            processSearch(request, response);
        } else {
            url = "login.jsp";
            request.setAttribute("message", "Incorrect User or Password! Please try again.");
        }
        return url;
    }

    private String processLogout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = LOGIN_PAGE;
        url = "login.jsp";
        request.getSession().invalidate();
        return url;
    }

    private String processUpdate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = LOGIN_PAGE;
        int projectID = Integer.parseInt(request.getParameter("id"));

        StartupProjectsDTO project = spdao.getProjectByID(projectID);

        if (project != null) {
            request.setAttribute("project", project);
            url = "update.jsp";
        } else {
            request.setAttribute("message", "Project Not Found!");
            url = "search.jsp";
        }
        return url;
    }

    private String processDoUpdate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = LOGIN_PAGE;
        int projectID = Integer.parseInt(request.getParameter("projectId"));
        String newStatus = request.getParameter("newStatus");

        boolean success = spdao.updateStatus(projectID, newStatus);

        if (success) {
            request.setAttribute("message", "Successful updated!");
        } else {
            request.setAttribute("message", "Update Fail!");
        }
        processSearch(request, response);

        return url = "search.jsp";
    }

    private String processAdd(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        String url = LOGIN_PAGE;

        String projectName = request.getParameter("projectName");
        String description = request.getParameter("description");
        String status = request.getParameter("status");
        String estimatedLaunch = request.getParameter("estimatedLaunch");

        String errorMessage = null;
        java.sql.Date estimatedLaunchDate = null;

        if (projectName == null || projectName.trim().isEmpty()) {
            errorMessage = "Project Name cannot be empty!";
        } else if (spdao.isProjectExists(projectName)) {
            errorMessage = "Project Name already exists! Please choose another name.";
        } else if (description == null || description.trim().isEmpty()) {
            errorMessage = "Description cannot be empty!";
        } else if (status == null || status.trim().isEmpty()) {
            errorMessage = "Invalid status selected!";
        } else if (estimatedLaunch == null || estimatedLaunch.trim().isEmpty()) {
            errorMessage = "Estimated Launch date cannot be empty!";
        } else {
            try {
                LocalDate inputDate = LocalDate.parse(estimatedLaunch);
                LocalDate today = LocalDate.now();

                if (inputDate.isAfter(today)) {
                    errorMessage = "Estimated Launch date cannot be in the future! Please enter a date before " + today + ".";
                } else {
                    estimatedLaunchDate = Date.valueOf(inputDate);
                }
            } catch (DateTimeParseException e) {
                errorMessage = "Invalid date format! Please use YYYY-MM-DD.";
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
                url = "MainController?action=search"; // Quay lại search.jsp 
            } else {
                request.setAttribute("message", "Failed to add project.");
                url = "add.jsp"; // Giữ nguyên trang nếu có lỗi
            }
        }
        return url;
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
                url = processLogin(request, response);
            } else if (action != null && action.equals("logout")) {
                url = processLogout(request, response);
            } else if (action != null && action.equals("search")) {
                url = processSearch(request, response);
            } else if (action != null && action.equals("update")) {
                url = processUpdate(request, response);
            } else if (action != null && action.equals("doUpdate")) {
                url = processDoUpdate(request, response);
            } else if (action != null && action.equals("addProject")) {
                url = processAdd(request, response);
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
