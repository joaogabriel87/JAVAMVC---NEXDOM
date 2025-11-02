<%--
  Created by IntelliJ IDEA.
  User: Joao Gabriel
  Date: 01/11/2025
  Time: 19:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Resultado da Autorização</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f9f9f9;
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100vh;
        }
        .card {
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0px 3px 8px rgba(0,0,0,0.2);
            width: 400px;
            text-align: center;
        }
        h2 {
            margin-bottom: 20px;
        }
        .autorizado {
            color: green;
            font-weight: bold;
        }
        .negado {
            color: red;
            font-weight: bold;
        }
        a {
            display: inline-block;
            margin-top: 20px;
            text-decoration: none;
            background-color: #1565c0;
            color: white;
            padding: 10px 15px;
            border-radius: 5px;
        }
        a:hover {
            background-color: #0d47a1;
        }
    </style>
</head>
<body>

<div class="card">
    <h2>Resultado da Autorização</h2>

    <%
        String mensagem = (String) request.getAttribute("mensagem");
        String status = (String) request.getAttribute("status");
    %>

    <p class="<%= "AUTORIZADO".equals(status) ? "autorizado" : "negado" %>">
        <%= mensagem %>
    </p>

    <a href="${pageContext.request.contextPath}/autorizacao/FormAutorizar.jsp">Nova Verificação</a>
</div>

</body>
</html>

