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
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<form:form action="chorbi/edit.do" modelAttribute="chorbi">
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<acme:textbox code="chorbi.name" path="name"/>
	<acme:textbox code="chorbi.surname" path="surname"/>
	<acme:textbox code="chorbi.email" path="email"/>
	<acme:textbox code="chorbi.phone" path="phone"/>
	<acme:textbox code="chorbi.birthDate" path="birthDate"/>
	<acme:textbox code="chorbi.coordiates.country" path="coordinates.country"/>
	<acme:textbox code="chorbi.coordinates.city" path="coordinates.city"/>
	<acme:textbox code="chorbi.picture" path="picture"/>
	
	
	<spring:message code='chorbi.kindRelationship' var="kindRelationship"/> 
	<jstl:out value="${kindRelationship }"/>
	<form:select path="kindRelationship">
		<option value="0">Activities</option>
		<option value="1">Friendship</option>
		<option value="2">Love</option>
	</form:select>
	
	<br/>
	<spring:message code='chorbi.genre' var="genre"/> 
	<jstl:out value="${genre }"/>
	<form:select path="genre">
		<option value="0">Woman</option>
		<option value="1">Men</option>
		<option value="2">Other</option>
	</form:select>
	<br/>
	
	
	
	<input type="submit" name="save" value="<spring:message code="chorbi.save" />" />
	<input type="button" name="cancel" value="<spring:message code="chorbi.cancel" />" onclick="window.location='welcome/index.do'" /> <br />
	
	<div>
		<jstl:out value="${errors}"/>
	</div>

</form:form>