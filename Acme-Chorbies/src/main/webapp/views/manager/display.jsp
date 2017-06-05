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
			<strong><spring:message code="manager.name"/>:</strong> <jstl:out value="${manager.name}"/><br>
			<strong><spring:message code="manager.surname"/>:</strong> <jstl:out value="${manager.surname}" /><br>
			<strong><spring:message code="manager.email"/>:</strong> <jstl:out value="${manager.email}" /><br>
			<strong><spring:message code="manager.phone"/>:</strong> <jstl:out value="${manager.phone}" /><br>
			<strong><spring:message code="manager.company"/>:</strong> <jstl:out value="${manager.company}" /><br>
			<strong><spring:message code="manager.VATNumber"/>:</strong> <jstl:out value="${manager.VATNumber}" /><br>
			<strong><spring:message code="manager.amount"/>:</strong> <jstl:out value="${manager.amount}" />
			<jstl:if test="${manager.amount > 0.0 }">
			<a href="managerr/pay.do">
	 			<spring:message code="manager.pay" var="payHeader" />
		  		<jstl:out value="${payHeader}" />
			</a>
			</jstl:if>
		</p>
	</div>