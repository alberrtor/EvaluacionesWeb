<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!-- para los jstl -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>

<!-- Saca el parametro usuario y lo convierte a mayuscula -->
<%
	String usuario = request.getParameter("usuario");
	usuario = usuario.toUpperCase();
%>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="css/style.css" media="screen" />
<title>Insert title here</title>
</head>
<body>

	<!--  Realiza la conexion a la bd iemsdf_db-->
	<sql:setDataSource var="iems" driver="org.postgresql.Driver" url="jdbc:postgresql://10.4.7.1:5432/iemsdf_db" user="kylix" password="kylix" scope="session" />

	<!-- Define la variable usuario_tutor y lo inicializa con el parametro usuario -->
	<c:set  var="usuario_tutor" scope="session" value="<%= usuario%>" />
	
	<h1>Bienvenido ${param.usuario}</h1>
	
	<!-- crea la consulta de los tutorados asignados -->
	<sql:query var="tutorados" dataSource="${iems}">
	select a.matricula as matricula, a.nombres as nombres, a.apellidos as apellidos
	from alumnos a, usuarios u
	where a.id_profesor=u.link_id and a.id_situacion=1 and u.usuario='${usuario_tutor}'
	</sql:query>

	
	<h2>Sus tutorados con situación Activo: </h2><br />
	
	<table border="1" align="center">
	<tr><th align="center">Estudiante</th><th align="center" colspan="2">Reportes de evaluación</th></tr>
	<c:forEach items="${tutorados.rows}" var="tutorado">
	     <tr> 
	        <td>
	        ${tutorado.matricula} ${tutorado.nombres} ${tutorado.apellidos}
	        </td>
	        <td>
	        <a href="ReporteCompendiada.do?matricula=${tutorado.matricula}&tutor=${usuario_tutor}">Compendiada</a>
	        </td>
	        <td>
	        <a href="ReporteFormativa.do?matricula=${tutorado.matricula}&tutor=${usuario_tutor}">Formativa</a>  
	        </td>
	      </tr>
	</c:forEach>
	
	</table>
	<br />
	<center><a href="ReporteListaTutorados.do?tutor=${usuario_tutor}">Lista de tutorados, servirá como acuse</a></center>
</body>
</html>