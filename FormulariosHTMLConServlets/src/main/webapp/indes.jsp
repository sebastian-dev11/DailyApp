<%-- 
    Document   : indes
    Created on : 4/02/2025, 7:22:07 p. m.
    Author     : sebas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Formulario Servlet</title>
</head>
<body>
    <h2>Formulario de Registro</h2>
    <form action="ProcesarServlet" method="post">
        Nombre: <input type="text" name="nombre"><br>
        Email: <input type="email" name="email"><br>
        <input type="submit" value="Enviar">
    </form>
</body>
</html>
