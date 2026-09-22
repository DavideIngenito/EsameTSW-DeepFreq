<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // Reindirizza l'utente alla HomeServlet usando il context path corretto
    response.sendRedirect(request.getContextPath() + "/home");
%>