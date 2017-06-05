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

<form:form action="administrator/config.do" modelAttribute="config">
	
	<acme:textbox code="admin.cache" path="cache"/>
	<acme:textbox code="admin.fee" path="fee"/>
	<acme:textbox code="admin.registrationFee" path="registrationFee"/>
	<br>
	<div>
		<a href="administrator/monthlyFee.do">
		 	<spring:message code="admin.monthly.fee" var="feeHeader" />
			<jstl:out value="${feeHeader}" />
		</a>
	</div>
	<br>
	<input type="submit" name="save" value="<spring:message code="like.save" />" />
	
	<input type="button" name="cancel" value="<spring:message code="like.cancel" />" onclick="window.location='welcome/index.do'" /> <br />
	
	<div>
		<jstl:out value="${errors}"/>
	</div>
	
</form:form>
