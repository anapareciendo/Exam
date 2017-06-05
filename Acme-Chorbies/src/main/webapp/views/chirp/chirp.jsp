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

<form:form action="chirp/chorbi/send.do" modelAttribute="chirp">
	
	<jstl:if test="${mode == 'send' }">
		<form:hidden path="recipient"/>
	</jstl:if>
	<jstl:if test="${mode == 'forward' }">
		<form:hidden path="id"/>
	</jstl:if>
	
	<jstl:if test="${mode == 'reply' }">
		<form:hidden path="id"/>
	</jstl:if>

	<jstl:if test="${mode == 'send' }">
		<acme:textbox code="chirp.subject" path="subject"/>
	</jstl:if>
	
	<jstl:if test="${mode != 'forward' }">
		<acme:textarea code="chirp.text" path="text"/>
		<br/>
	</jstl:if>
	
	<jstl:if test="${mode == 'send' }">
		<acme:textbox code="chirp.attachments" path="attachments"/>
	</jstl:if>
	
	<jstl:if test="${mode == 'forward' }">
		<acme:select items="${chorbi}" itemLabel="userAccount.username" code="chirp.recipient" path="recipient"/>
	</jstl:if>
	
	<input type="submit" name="${mode}" value="<spring:message code="like.save" />" />
	
	<input type="button" name="cancel" value="<spring:message code="like.cancel" />" onclick="window.location='welcome/index.do'" /> <br />
	
	<div>
		<jstl:out value="${errors}"/>
	</div>
	
</form:form>
