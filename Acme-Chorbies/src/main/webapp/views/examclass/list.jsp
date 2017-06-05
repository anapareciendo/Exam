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

<display:table name="examClass" id="examClass" requestURI="examClass/list.do"
	pagesize="5" class="displaytag">
	
	<spring:message code="examclass.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader }" sortable="true" />
	
	<spring:message code="examclass.amount" var="amountHeader" />
	<display:column property="amount" title="${amountHeader }" sortable="true" />
	
	<display:column>
	<jstl:if test="${banner.display == false }">
	  	<div>
	  	<a href="banner/admin/show.do?bannerId=${banner.id}">
	  		<spring:message code="banner.show" var="showHeader" />
	  		<jstl:out value="${showHeader}" />
	  	</a>
	  	</div>
  	</jstl:if>
  	
	<jstl:if test="${banner.display == true }">
	  	<div>
	  	<a href="banner/admin/hide.do?bannerId=${banner.id}">
	  		<spring:message code="banner.hide" var="hideHeader" />
	  		<jstl:out value="${hideHeader}" />
	  	</a>
	  	</div>
  	</jstl:if>

  	</display:column>
  	
  	<display:column>
		<div>
			<a href="banner/admin/edit.do?bannerId=${banner.id}">
				<spring:message code="banner.edit" var="editHeader" />
				<jstl:out value="${editHeader}" />
			</a>
		</div>
	</display:column>
	
	<%-- <jstl:if test="${make == true }">
	<display:column>
	  	<div>
	  	<a href="likes/chorbi/delete.do?likesId=${likes.id}">
	  		<spring:message code="likes.delete" var="deleteHeader" />
	  		<jstl:out value="${deleteHeader}" />
	  	</a>
	  	</div>
  	</display:column>
  	</jstl:if> --%>
	
</display:table>

<div>
	<a href="banner/admin/create.do">
		<spring:message code="banner.create" var="createHeader" />
		<jstl:out value="${createHeader}" />
	</a>
</div>