<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="dto.StartupProjectsDTO" %>
<%
    StartupProjectsDTO project = (StartupProjectsDTO) request.getAttribute("project");
%>

<html>
    <head>
        <title>Update Project Status</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f4f4f4;
                display: flex;
                flex-direction: column;
                align-items: center;
                justify-content: center;
                height: 100vh;
                margin: 0;
            }
            .container {
                background: white;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
                text-align: center;
                width: 400px;
            }
            h2 {
                color: #333;
            }
            label {
                font-weight: bold;
            }
            input, select {
                width: 100%;
                padding: 8px;
                margin: 5px 0;
                border-radius: 4px;
                border: 1px solid #ccc;
            }
            input[type="submit"] {
                background-color: #007bff;
                color: white;
                border: none;
                cursor: pointer;
                font-size: 16px;
            }
            input[type="submit"]:hover {
                background-color: #0056b3;
            }
            a {
                text-decoration: none;
                color: #dc3545;
                font-weight: bold;
            }
            a:hover {
                text-decoration: underline;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h2>Update Project Status</h2>
            <form action="MainController" method="post">
                <input type="hidden" name="action" value="doUpdate"/>
                <input type="hidden" name="projectId" value="<%= project.getProject_id()%>"/>

                <label>Project ID:</label>
                <input type="text" value="<%= project.getProject_id()%>" readonly/><br><br>

                <label>Status you want to update:</label>
                <select name="newStatus">
                    <option value="Pending" <%= project.getStatus().equals("Pending") ? "selected" : ""%>>Pending</option>
                    <option value="In Progress" <%= project.getStatus().equals("In Progress") ? "selected" : ""%>>In Progress</option>
                    <option value="Completed" <%= project.getStatus().equals("Completed") ? "selected" : ""%>>Completed</option>
                    <option value="Canceled" <%= project.getStatus().equals("Canceled") ? "selected" : ""%>>Canceled</option>
                </select><br><br>

                <input type="submit" value="Submit"/>
            </form>
            <br>
            <a href="MainController?action=search">Cancel</a>
        </div>
    </body>
</html>
