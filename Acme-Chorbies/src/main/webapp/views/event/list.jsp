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
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jstl:choose>
<jstl:when test="${isEmpty == true }">
<spring:message code="event.empty"/>
</jstl:when>
<jstl:otherwise>
<display:table name="event" id="event" requestURI="${requestURI}" pagesize="8" class="displaytag">
	<jstl:set var="off" value="${event.seatsOffered }"/>
	<jstl:set var="on" value="${fn:length(event.chorbies)}"/>
	
	<jstl:if test="${allEvents == true }">
	<display:column>
		<jstl:if test="${off-on>0 && year==event.year && ((month==event.month && day<=event.day) || month+1==event.month)}">
			<img src="./images/star.png" alt="Highlighted" width="25">
		</jstl:if>
		<jstl:if test="${year>event.year || (year==event.year && month>event.month) }">
			<img src="./images/skull.png" alt="Greyed" width="25">
		</jstl:if>
	</display:column>
	</jstl:if>
	
	<spring:message code="event.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" sortable="true" />
	
	<spring:message code="event.moment" var="momentHeader" />
	<display:column property="date" title="${momentHeader}" sortable="true" />
	<%-- <jstl:set var="moment" value="${event.day } / ${event.month} / ${event.year } - ${event.hour }:${event.minutes }"/>
	<display:column value="${moment}" title="${momentHeader}" sortable="false" /> --%>
	
	
	<spring:message code="event.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader }" sortable="false" />
	
	<spring:message code="event.seatsOffered" var="seatsOfferedHeader" />
	<display:column property="seatsOffered" title="${seatsOfferedHeader}" sortable="true" />
	
	<jstl:if test="${available == true }">
	<spring:message code="event.seatsAvailable" var="seatsAvailableHeader" />
	<display:column value="${off-on }" title="${seatsAvailableHeader}" sortable="true" />
	</jstl:if>
	
	<display:column>
		<img src="${event.picture }" alt="${event.title }" width="50">
	</display:column>
	
	<security:authorize access="hasRole('CHORBI')">
	<jstl:if test="${own == true }">
		<display:column>
	  		<a href="event/chorbi/unregister.do?eventId=${event.id}">
	 			<spring:message code="event.unregister" var="unregisterHeader" />
		  		<jstl:out value="${unregisterHeader}" />
			 </a>
		</display:column>
	</jstl:if>
	<jstl:if test="${all == true }">
		<display:column>
	  		<a href="event/chorbi/register.do?eventId=${event.id}">
	 			<spring:message code="event.register" var="registerHeader" />
		  		<jstl:out value="${registerHeader}" />
			 </a>
		</display:column>
	</jstl:if>
	</security:authorize>
	
	
	<security:authorize access="hasRole('MANAGER')">
	<jstl:if test="${edit == true }">
	<display:column>
	  	<a href="event/manager/edit.do?eventId=${event.id}">
	 			<spring:message code="event.edit" var="editHeader" />
		  		<jstl:out value="${editHeader}" />
		 </a>
	</display:column>
	</jstl:if>
	</security:authorize>
	
</display:table>
</jstl:otherwise>
</jstl:choose>