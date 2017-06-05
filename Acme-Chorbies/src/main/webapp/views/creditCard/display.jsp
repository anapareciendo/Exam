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
			<spring:message code="creditcard.holder"/>: <jstl:out value="${card.holder}"/><br>
			<spring:message code="creditcard.brand"/>: <jstl:out value="${card.brand}"/><br>
			<spring:message code="creditcard.number"/>: <jstl:out value="${card.number}"/><br>
			<spring:message code="creditcard.expirationMonth"/>: <jstl:out value="${card.expirationMonth}"/><br>
			<spring:message code="creditcard.expirationYear"/>: <jstl:out value="${card.expirationYear}"/><br>
			<spring:message code="creditcard.cvv"/>: <jstl:out value="${card.cvv}"/><br>
		</p>
	</div>