<%--
 * header.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div>
	<img src="images/logo.png" alt="Acme-Chorbies" />
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('CHORBI')">
			<li><a class="fNiv"><spring:message	code="master.page.profile" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="chorbi/display.do"><spring:message code="master.page.chorbi.display" /></a></li>
					<li><a href="chorbi/edit.do"><spring:message code="master.page.chorbi.edit" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
			<li><a class="fNiv" href="security/signin.do"><spring:message code="master.page.signin" /></a></li>
		</security:authorize>
		
		<security:authorize access="isAuthenticated()">
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>
					<security:authorize access="hasRole('ADMIN')">
					<li><a href="administrator/config.do"><spring:message code="master.page.config" /> </a></li>
					<li><a href="dashboard/dashboard.do"><spring:message code="master.page.dashboard" /> </a></li>
					</security:authorize>
					<security:authorize access="hasRole('CHORBI') or hasRole('MANAGER')">
					<li><a href="creditCard/creditCard.do"><spring:message code="master.page.card" /> </a></li>
					</security:authorize>
					<security:authorize access="hasRole('MANAGER')">
					<li><a href="managerr/display.do"><spring:message code="master.page.manager.display" /></a></li>
					</security:authorize>
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
			
			<security:authorize access="hasRole('ADMIN') or hasRole('CHORBI')">
			<li><a class="fNiv"><spring:message	code="master.page.chorbi" /></a>
				<ul>
					<li class="arrow"></li>
					<security:authorize access="hasRole('ADMIN')">
						<li><a href="chorbi/admin/list.do"><spring:message code="master.page.chorbi.list" /></a></li>
					</security:authorize>
					
					<security:authorize access="hasRole('CHORBI')">
						<li><a href="chorbi/list.do"><spring:message code="master.page.chorbi.list" /></a></li>
						<li><a href="template/search.do"><spring:message code="master.page.template" /></a></li>
						<li><a href="template/result.do"><spring:message code="master.page.template.result" /></a></li>
					</security:authorize>
				</ul>
			</li>
			</security:authorize>
			
			<security:authorize access="hasRole('ADMIN')">
				<li><a class="fNiv"><spring:message	code="master.page.banner" /></a>
					<ul>
					<li class="arrow"></li>
						<li><a href="banner/admin/list.do"><spring:message code="master.page.banner.list" /></a></li>
					</ul>
				</li>
			</security:authorize>
			
			<security:authorize access="hasRole('CHORBI')">
			<li><a class="fNiv"><spring:message	code="master.page.likes" /></a>
				<ul>
					<li><a href="likes/chorbi/listMakeLikes.do"><spring:message code="master.page.likes.chorbi.make" /></a></li>
					<li><a href="likes/chorbi/listReceivedLikes.do"><spring:message code="master.page.likes.chorbi.received" /></a></li>
				</ul>
			</li>
			
			<li><a class="fNiv"><spring:message	code="master.page.chirp" /></a>
				<ul>
					<li><a href="chirp/chorbi/received.do"><spring:message code="master.page.chirp.list" /></a></li>
					<li><a href="chirp/chorbi/sent.do"><spring:message code="master.page.chirp.sent" /></a></li>
				</ul>
			</li>
			</security:authorize>
			
		</security:authorize>
		
		<li><a class="fNiv"><spring:message	code="master.page.event" /></a>
			<ul>
				<li class="arrow"></li>
				<li><a href="event/listAvailable.do"><spring:message code="master.page.event.available" /></a></li>
				<li><a href="event/listAll.do"><spring:message code="master.page.event.all" /></a></li>
				<security:authorize access="hasRole('MANAGER')">
					<li><a href="event/manager/list.do"><spring:message code="master.page.manager.list" /></a></li>
					<li><a href="event/manager/create.do"><spring:message code="master.page.manager.create" /></a></li>
					<li><a href="managerr/broadcast.do"><spring:message code="master.page.manager.broadcast" /></a></li>
				</security:authorize>
				<security:authorize access="hasRole('CHORBI')">
				<li><a href="event/chorbi/list.do"><spring:message code="master.page.manager.list" /></a></li>
				<li><a href="chirp/chorbi/broadcast.do"><spring:message code="master.page.broadcast.list" /></a></li>
				</security:authorize>
			</ul>
		</li>
			
		<li><a class="fNiv" href="aboutUs/acme.do"><spring:message code="master.page.about" /></a></li>
		
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a> 
</div>

