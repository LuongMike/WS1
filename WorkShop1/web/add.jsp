<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add New Project</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f4f4f4;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
                margin: 0;
            }
            .form-container {
                background: white;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0 0 15px rgba(0, 0, 0, 0.2);
                width: 400px;
                text-align: center;
            }
            h2 {
                color: #333;
                margin-bottom: 10px;
            }
            /* Hi?n th? l?i màu ?? */
            .error-message {
                color: red;
                font-size: 14px;
                margin-bottom: 10px;
            }
            input[type="text"], textarea, select, input[type="date"] {
                width: 100%;
                padding: 10px;
                margin: 8px 0;
                border: 1px solid #ccc;
                border-radius: 5px;
                font-size: 14px;
                box-sizing: border-box;
            }
            textarea {
                height: 80px;
                resize: none;
            }
            input[type="submit"] {
                width: 100%;
                background-color: #28a745;
                color: white;
                padding: 12px;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                font-size: 16px;
                transition: background 0.3s;
            }
            input[type="submit"]:hover {
                background-color: #218838;
            }
            a {
                display: block;
                margin-top: 10px;
                color: #007bff;
                text-decoration: none;
                font-size: 14px;
            }
            a:hover {
                text-decoration: underline;
            }
        </style>
    </head>
    <body>
        <div class="form-container">
            <h2>Add New Project</h2>

            <% if (request.getAttribute("message") != null) {%>
            <p class="error-message"><%= request.getAttribute("message")%></p>
            <% }%>

            <form action="MainController" method="post">
                <input type="hidden" name="action" value="addProject"/>

                <input type="text" name="projectName" placeholder="Project Name" 
                       value="<%= (request.getParameter("projectName") != null) ? request.getParameter("projectName") : ""%>" 
                       required/>

                <textarea name="description" placeholder="Description" required><%=(request.getParameter("description") != null) ? request.getParameter("description") : ""%></textarea>

                <select name="status">
                    <option value="Pending" <%= "Pending".equals(request.getParameter("status")) ? "selected" : ""%>>Pending</option>
                    <option value="In Progress" <%= "In Progress".equals(request.getParameter("status")) ? "selected" : ""%>>In Progress</option>
                    <option value="Completed" <%= "Completed".equals(request.getParameter("status")) ? "selected" : ""%>>Completed</option>
                </select>

                <input type="date" name="estimatedLaunch" 
                       value="<%= (request.getParameter("estimatedLaunch") != null) ? request.getParameter("estimatedLaunch") : ""%>" 
                       required/>

                <input type="submit" value="Add Project"/>
            </form>

            <a href="MainController?action=search">Cancel</a>
        </div>
    </body>

</html>
