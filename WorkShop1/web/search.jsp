<%@page import="dto.StartupProjectsDTO"%>
<%@page import="java.util.List"%>
<%@page import="dto.UserDTO"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Project Management</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
            display: flex;
            flex-direction: column;
            align-items: center;
        }
        .container {
            width: 80%;
            background: white;
            padding: 20px;
            margin-top: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            text-align: center;
            position: relative;
        }
        .logout-container {
            position: absolute;
            top: 20px;
            right: 20px;
        }
        h1 {
            color: #333;
        }
        form {
            margin: 10px 0;
        }
        input[type="text"], input[type="submit"] {
            padding: 8px;
            margin: 5px;
            border-radius: 4px;
            border: 1px solid #ccc;
        }
        input[type="submit"] {
            background-color: #28a745;
            color: white;
            cursor: pointer;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        table, th, td {
            border: 1px solid #ddd;
        }
        th, td {
            padding: 10px;
            text-align: center;
        }
        th {
            background-color: #007bff;
            color: white;
        }
        a, button {
            text-decoration: none;
            padding: 6px 12px;
            border-radius: 4px;
            border: none;
            cursor: pointer;
        }
        button {
            background-color: #ffc107;
        }
        a.add-project {
            display: inline-block;
            background-color: #007bff;
            color: white;
            padding: 10px 15px;
            margin-top: 10px;
            border-radius: 4px;
        }
        p {
            color: green;
        }
    </style>
</head>
<body>
    <div class="container">
        <% if (session.getAttribute("user") != null) { 
            UserDTO user = (UserDTO) session.getAttribute("user"); %>
            <div class="logout-container">
                <form action="MainController" method="get">
                    <input type="hidden" name="action" value="logout"/> 
                    <input type="submit" value="Logout"/>
                </form>
            </div>
            <h1>Welcome <%= user.getName() %></h1>
            <% String searchTerm = request.getAttribute("searchTerm") + "";
               searchTerm = searchTerm.equals("null") ? "" : searchTerm; %>
            <form action="MainController" method="get">
                <input type="hidden" name="action" value="search"/>
                Search Project Startup: <input type="text" name="searchTerm" value="<%= searchTerm %>"/>
                <input type="submit" value="Search"/>
            </form>
            <a href="add.jsp" class="add-project">Add New Project</a>
            <% if (request.getAttribute("projects") != null) {
                List<StartupProjectsDTO> listProjects = (List<StartupProjectsDTO>) request.getAttribute("projects"); %>
                <% if (listProjects.isEmpty()) { %>
                    <p>No projects found for "<%= searchTerm %>"</p>
                <% } else { %>
                    <table>
                        <tr>
                            <th>Project ID</th>
                            <th>Project Name</th>
                            <th>Description</th>
                            <th>Status</th>
                            <th>Estimated Launch</th>
                            <th>Update Status</th>
                        </tr>
                        <% for (StartupProjectsDTO pdto : listProjects) { %>
                        <tr>
                            <td><%= pdto.getProject_id() %></td>
                            <td><%= pdto.getProject_name() %></td>
                            <td><%= pdto.getDescription() %></td>
                            <td><%= pdto.getStatus() %></td>
                            <td><%= pdto.getEstimated_launch() %></td>
                            <td><a href="MainController?action=update&id=<%= pdto.getProject_id() %>"><button>Update</button></a></td>
                        </tr>
                        <% } %>
                    </table>
                <% } %>
            <% } %>
        <% } %>
        <% if (request.getAttribute("message") != null) { %>
            <p><%= request.getAttribute("message") %></p>
        <% } %>
    </div>
</body>
</html>

