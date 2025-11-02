<%--
  Created by IntelliJ IDEA.
  User: Joao Gabriel
  Date: 01/11/2025
  Time: 14:11
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="java.util.List" %>
<%@ page import="com.nexdom.autorizacao.dao.ProcedimentoDAO" %>
<%@ page import="com.nexdom.autorizacao.model.Procedimento" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Procedimentos Cadastrados</title>
</head>
<body>

<h2>Procedimentos Pré-Cadastrados</h2>

<%
    ProcedimentoDAO dao = new ProcedimentoDAO();
    List<Procedimento> lista = dao.listar();
%>

<table border="1" cellpadding="6">
    <tr>
        <th>ID</th>
        <th>Código</th>
        <th>Idade</th>
        <th>Sexo</th>
        <th>Permitido</th>
    </tr>

    <%
        for (Procedimento p : lista) {
    %>
    <tr>
        <td><%= p.getId() %></td>
        <td><%= p.getCodigo() %></td>
        <td><%= p.getIdade() %></td>
        <td><%= p.getSexo() %></td>
        <td><%= p.getPermitido() ? "SIM" : "NÃO" %></td>
    </tr>
    <%
        }
    %>
</table>

<br>
<a href="${pageContext.request.contextPath}/autorizacao/form.jsp">Ir para autorização</a>

</body>
</html>
