<%--
 * display.jsp
 *
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<div>
		<p>
			<img src="${chorbi.picture }" alt="${chorbi.name }" width="150"><br>
			<spring:message code="chorbi.name"/>: <jstl:out value="${chorbi.name}"/><br>
			<spring:message code="chorbi.surname"/>: <jstl:out value="${chorbi.surname}" /><br>
			<spring:message code="chorbi.email"/>: <jstl:out value="${chorbi.email}" /><br>
			<spring:message code="chorbi.phone"/>: <jstl:out value="${chorbi.phone}" /><br>
			<spring:message code="chorbi.kindRelationship"/>: <jstl:out value="${chorbi.kindRelationship}" /><br>
			<spring:message code="chorbi.birthDate"/>: <jstl:out value="${chorbi.birthDate}" /><br>
			<spring:message code="chorbi.genre"/>: <jstl:out value="${chorbi.genre}" /><br>
			<br>
			<spring:message code="chorbi.country"/>: <jstl:out value="${chorbi.coordinates.country}" /><br>
			<spring:message code="chorbi.city"/>: <jstl:out value="${chorbi.coordinates.city}" /><br>
			<spring:message code="chorbi.state"/>: <jstl:out value="${chorbi.coordinates.state}" /><br>
			<spring:message code="chorbi.province"/>: <jstl:out value="${chorbi.coordinates.province}" /><br>
		</p>
	</div>