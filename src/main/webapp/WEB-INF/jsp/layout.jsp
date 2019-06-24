<%@ page language="java" contentType="text/html; charset=ISO-8859-15" pageEncoding="ISO-8859-15" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>

<head>

	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
	<meta http-equiv="Pragma" content="no-cache" />
	<meta http-equiv="Expires" content="0" />
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-15">
	<meta http-equiv="Window-target" content="_top">
	<meta http-equiv="Content-language" content="sv">
	<meta name="apple-mobile-web-app-capable" content="yes" />
	
	<title><c:out value="${title}" /></title>
	
	<link rel="shortcut icon" href="${pageContext.request.contextPath}/build/assets/ico/favicon1.ico" />
	<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat" />
	<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto" />
	

</head>


<body id="body" class="body">
	<main>
		<c:if test="${not empty sessionScope.mobilSession}">
			<!-- Mobile -->
			<table class="mobilContentTable">
				<tr>
					<td id="top">
						<table>
							<tr>
								<td id="logga" align="center"><tiles:insertAttribute
										name="logga" /></td>			
								<td id="header"><tiles:insertAttribute name="header" /></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td id="center">
						<table>
							<tr>
								<td id="sidemenu"><tiles:insertAttribute name="menu" /></td>
								<td id="content" d-scroll><tiles:insertAttribute name="body" /></td>
							</tr>
						</table>
					</td>

				</tr>

			</table>
		</c:if>

		<c:if test="${empty sessionScope.mobilSession}">
			<table class="contentTable">
				<tr>
					<td id="top">
						<table>
							<tr>
								<td id="logga" align="center"><tiles:insertAttribute
										name="logga" /></td>
								<td id="header"><tiles:insertAttribute name="header" /></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td id="center">
						<table>
							<tr>
								<td id="sidemenu"><tiles:insertAttribute name="menu" /></td>
								
								
								<td id="content" d-scroll><tiles:insertAttribute name="body" /></td>
								
								
							</tr>
						</table>
					</td>

				</tr>
				<tr>
					<td id="bottom">
						<table>
							<tr>
								<td id="footer"><tiles:insertAttribute name="footer" /></td>
							</tr>
						</table>
					</td>

				</tr>

			</table>
			
		</c:if>
	</main>


</body>
</html>
