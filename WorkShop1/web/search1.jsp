<%-- 
    Document   : search1
    Created on : Mar 2, 2025, 8:21:08 AM
    Author     : PC
--%>

<%@page import="dto.UserDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                display: flex;
                flex-direction: column;
                justify-content: center;
                align-items: center;
                height: 100vh;
                background-color: #f0f2f5;
                margin: 0;
                text-align: center;
            }

            h1 {
                color: #333;
                margin-bottom: 10px;
            }

            form {
                background: white;
                padding: 20px;
                border-radius: 10px;
                box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
                text-align: center;
                width: 300px;
                margin-top: 20px;
            }

            input[type="submit"] {
                width: 100%;
                padding: 10px;
                background-color: #dc3545;
                border: none;
                color: white;
                border-radius: 5px;
                cursor: pointer;
                font-size: 16px;
            }

            input[type="submit"]:hover {
                background-color: #c82333;
            }

        </style>
    </head>
    <body>
        <%
            if (session.getAttribute("user") != null) {
                UserDTO user = (UserDTO) session.getAttribute("user");
                user.getName();


        %>
        <h1>Welcome <%=user.getName()%> </h1>
        <h1>Role: <%=user.getRole()%></h1>
        <h1>Account: <%=user.getUserName()%></h1>

        <h1>Password: <%=user.getPassword()%></h1>

        <form action="MainController" method="get">
            <input type="hidden" name="action" value="logout"/> 
            <input type="submit" value="Logout"/>
        </form>    
        <%}%>
    </body>
</html>
