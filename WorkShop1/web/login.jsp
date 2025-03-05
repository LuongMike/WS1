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
                justify-content: center;
                align-items: center;
                height: 100vh;
                background-color: #f0f2f5;
                margin: 0;
            }

            form {
                background: white;
                padding: 20px;
                border-radius: 10px;
                box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
                text-align: center;
                width: 300px;
            }

            h2 {
                margin-bottom: 20px;
            }

            input[type="text"],
            input[type="password"] {
                width: 100%;
                padding: 10px;
                margin: 10px 0;
                border: 1px solid #ccc;
                border-radius: 5px;
                box-sizing: border-box;
            }

            input[type="submit"] {
                width: 100%;
                padding: 10px;
                background-color: #007bff;
                border: none;
                color: white;
                border-radius: 5px;
                cursor: pointer;
                font-size: 16px;
            }

            input[type="submit"]:hover {
                background-color: #0056b3;
            }

            .login-container {
                display: flex;
                flex-direction: column;
                align-items: center; /* Căn giữa nội dung */
            }

            .error-message {
                color: red;
                font-size: 14px;
                margin-top: 10px; /* Tạo khoảng cách với nút Login */
                background: #ffe6e6;
                padding: 10px;
                border: 1px solid #ff4d4d;
                border-radius: 5px;
                width: 300px; /* Đảm bảo cùng độ rộng với form */
                text-align: center;
            }



        </style>
    </head>
    <body>
        <div class="login-container">
            <form action="MainController" method="post">
                <input type="hidden" name="action" value="login"/> <br/>
                <h2>Login</h2>
                <input type="text" name="strUserID" placeholder="User ID" required/> <br/>
                <input type="password" name="strPassword" placeholder="Password" required/> <br/>
                <input type="submit" value="Login"/>
            </form>

            <% if (request.getAttribute("message") != null) {%>
            <p class="error-message"><%= request.getAttribute("message")%></p>
            <% }%>
        </div>

    </body>
</html>
