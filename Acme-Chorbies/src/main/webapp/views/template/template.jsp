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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="template/template.do" modelAttribute="template">
	
	<spring:message code='template.kindRelationship' var="kindRelationship"/> 
	<jstl:out value="${kindRelationship }"/>
	<form:select path="kindRelationship">
		<option value="0">Activities</option>
		<option value="1">Friendship</option>
		<option value="2">Love</option>
	</form:select>
	
	<br/>
	<spring:message code='template.genre' var="genre"/> 
	<jstl:out value="${genre }"/>
	<form:select path="genre">
		<option value="0">Woman</option>
		<option value="1">Men</option>
		<option value="2">Other</option>
	</form:select>
	
	<acme:textbox code="template.age" path="aproximateAge"/>
	
	<acme:textbox code="template.keyword" path="keyword"/>
	<acme:textbox code="template.country" path="country"/>
	<acme:textbox code="template.city" path="city"/>
	<acme:textbox code="template.state" path="state"/>
	<acme:textbox code="template.province" path="province"/>
	
	<input type="submit" name="search" value="<spring:message code="like.save" />" />
	
	<input type="button" name="cancel" value="<spring:message code="like.cancel" />" onclick="window.location='welcome/index.do'" /> <br />
	
	<div>
		<jstl:out value="${errors}"/>
	</div>
	
</form:form>
