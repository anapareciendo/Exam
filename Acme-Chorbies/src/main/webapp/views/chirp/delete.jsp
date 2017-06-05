<%--
 * edit.jsp
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
<div>
	<h2><jstl:out value="${chirp.subject}" /></h2>
	<jstl:out value="${chirp.text}" />
</div>
<form:form action="chirp/chorbi/delete.do" modelAttribute="chirp">
	<form:hidden path="id" />
	
	<input type="submit" name="delete"
			value="<spring:message code="likes.delete" />"
			onclick="return confirm('<spring:message code="likes.confirm.delete" />')" />&nbsp;
	
	<input type="button" name="cancel" value="<spring:message code="like.cancel" />" onclick="window.location='welcome/index.do'" /> <br />
	
</form:form>