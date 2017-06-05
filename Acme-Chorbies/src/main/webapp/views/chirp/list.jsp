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

<display:table name="chirps" id="chirps" requestURI="${requestUri}" pagesize="5" class="displaytag">
	
	<spring:message code="chirp.sender" var="senderHeader" />
	<display:column property="sender.name" title="${senderHeader}" sortable="false" />
	
	<spring:message code="chirp.moment" var="momentHeader" />
	<display:column property="moment" title="${momentHeader }" sortable="false" />
	
	<spring:message code="chirp.subject" var="subjectHeader" />
	<display:column property="subject" title="${subjectHeader }" sortable="true" />
	
	<spring:message code="chirp.text" var="textHeader" />
	<display:column property="text" title="${textHeader }" sortable="true" />
	
	<jstl:if test="${received == true }">
	<display:column>
	  	<div>
	  	<a href="chirp/chorbi/reply.do?chirpId=${chirps.id}">
	  		<spring:message code="chirp.reply" var="replyHeader" />
	  		<jstl:out value="${replyHeader}" />
	  	</a>
	  	</div>
  	</display:column>
  	
  	<display:column>
	  	<div>
	  	<a href="chirp/chorbi/forward.do?chirpId=${chirps.id}">
	  		<spring:message code="chirp.forward" var="forwardHeader" />
	  		<jstl:out value="${forwardHeader}" />
	  	</a>
	  	</div>
  	</display:column>
  	</jstl:if>
  	
  	<display:column>
	  	<div>
	  	<a href="chirp/chorbi/delete.do?chirpId=${chirps.id}">
	  		<spring:message code="chirp.delete" var="deleteHeader" />
	  		<jstl:out value="${deleteHeader}" />
	  	</a>
	  	</div>
  	</display:column>
	
</display:table>