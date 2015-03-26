<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@page isELIgnored="false"%>

<!DOCTYPE html>


<html>
	<head>
		<title>Eventos</title>
		<link rel="stylesheet" type="text/css" href="css/main.css" />
		<meta charset="utf-8">
	</head>
	<body>
	
		<div style="width: 100%;">
			<div class="line"></div>
			<div class="topLine">
				<div style="float: left;" class="headline">Eventos</div>
				<div style="float: right;">
					<a
						href="<c:url value="${url}"/>"><c:out value="${urlLinktext}"/></a>
					<c:if test="${user != null}"><c:out value="${user.nickname}"/></c:if>
				</div>
			</div>
		</div>
	
		<div style="clear: both;" />
		Tienes un total de <c:out value="${fn:length(events)}" />
		eventos.
	
		<table>
			<tr>
				<th>Título</th>
				<th>Descripción</th>
				
			</tr>
	
			<c:forEach items="${events}" var="event">
				<tr>
					<td><c:out value="${event.title}" /></td>
					<td><c:out value="${event.description}" /></td>
					<td><a class="done"
						href="<c:url value="/done?id=${event.id}" />">Terminado</a></td>
				</tr>
			</c:forEach>
		</table>
	
	
		<hr />
	
		<div class="main">
	
			<div class="headline">Nuevo evento</div>
	
			<c:choose>
				<c:when test="${user != null}">
					<form action="/new" method="post" accept-charset="utf-8">
						<table>
							<tr>
								<td><label for="title">Título</label></td>
								<td><input type="text" name="title" id="title" size="65" /></td>
							</tr>
							<tr>
								<td valign="description"><label for="description">Descripción</label></td>
								<td><textarea rows="4" cols="50" name="description"
										id="description"></textarea></td>
							</tr>
							<tr>
							<tr>
								<td colspan="2" align="right"><input type="submit"
									value="Crear" /></td>
							</tr>
						</table>
					</form>
				</c:when>
				<c:otherwise>
	
	Por favor registrese con su cuenta de Google
				</c:otherwise>
			</c:choose>
		</div>
	</body>
</html>
