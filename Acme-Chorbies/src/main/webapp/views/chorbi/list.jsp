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

<display:table name="chorbi" id="chorbi" requestURI="chorbi/list.do"
	pagesize="5" class="displaytag">
	
	<display:column>
		<img src="${chorbi.picture }" alt="${chorbi.name }" width="100">
	</display:column>
	
	<spring:message code="chorbi.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader }" sortable="true" />
	
	<spring:message code="chorbi.surname" var="surnameHeader" />
	<display:column property="surname" title="${surnameHeader }" sortable="true" />	
	
	<spring:message code="chorbi.kindRelationship" var="kindRelationshipHeader" />
	<display:column property="kindRelationship" title="${kindRelationshipHeader }" sortable="true" />
	
	<spring:message code="chorbi.birthDate" var="birthDateHeader" />
	<display:column property="birthDate" title="${birthDateHeader }" sortable="true" />
	
	<spring:message code="chorbi.genre" var="genreHeader" />
	<display:column property="genre" title="${genreHeader }" sortable="true" />
	
	<spring:message code="chorbi.coordinates.city" var="cityHeader" />
	<display:column property="coordinates.city" title="${cityHeader }" sortable="true" />
	
	<security:authorize access="hasRole('CHORBI')">
	<display:column>
	  	<a href="chorbi/listMyLikes.do?chorbiId=${chorbi.id}">
	 			<spring:message code="chorbi.likes" var="likesHeader" />
		  		<jstl:out value="${likesHeader}" />
		 </a>
	</display:column>	
	
	<display:column>
	  	<a href="likes/chorbi/like.do?chorbiId=${chorbi.id}">
	 			<spring:message code="chorbi.like" var="likeHeader" />
		  		<jstl:out value="${likeHeader}" />
		 </a>
	</display:column>
	
	<display:column>
	  	<a href="chirp/chorbi/chirp.do?chorbiId=${chorbi.id}">
	 			<spring:message code="chorbi.chirp" var="chirpHeader" />
		  		<jstl:out value="${chirpHeader}" />
		 </a>
	</display:column>
	</security:authorize>
	
	<security:authorize access="hasRole('ADMIN')">
	<display:column>
	<jstl:if test="${chorbi.banned == false }">
	  	<a href="chorbi/admin/banned.do?chorbiId=${chorbi.id}">
	 			<spring:message code="chorbi.banned" var="bannedHeader" />
		  		<jstl:out value="${bannedHeader}" />
		 </a>
	</jstl:if>
	
	<jstl:if test="${chorbi.banned == true }">
	  	<a href="chorbi/admin/unbanned.do?chorbiId=${chorbi.id}">
	 			<spring:message code="chorbi.unbanned" var="unbannedHeader" />
		  		<jstl:out value="${unbannedHeader}" />
		 </a>
	</jstl:if>
	</display:column>
	</security:authorize>
	

	
	
</display:table>


