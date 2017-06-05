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
			<spring:message code="dashboard.numChorbiesPerCity"/>: <jstl:out value="${dashboard.numChorbiesPerCity}" /><br>
			<spring:message code="dashboard.numChorbiesPerCountry"/>: <jstl:out value="${dashboard.numChorbiesPerCountry}" /><br>
			<spring:message code="dashboard.minAgeChorbies"/>: <jstl:out value="${dashboard.minAgeChorbies}" /><br>
			<spring:message code="dashboard.maxAgeChorbies"/>: <jstl:out value="${dashboard.maxAgeChorbies}" /><br>
			<spring:message code="dashboard.avgAgeChorbies"/>: <jstl:out value="${dashboard.avgAgeChorbies}" /><br>
			<spring:message code="dashboard.ratioChorbiesCreditCard"/>: <jstl:out value="${dashboard.ratioChorbiesCreditCard}" /><br>
			<spring:message code="dashboard.ratioChorbisWhoSearchActivities"/>: <jstl:out value="${dashboard.ratioChorbisWhoSearchActivities}" /><br>
			<spring:message code="dashboard.ratioChorbisWhoSearchFriendship"/>: <jstl:out value="${dashboard.ratioChorbisWhoSearchFriendship}" /><br>
			<spring:message code="dashboard.ratioChorbisWhoSearchLove"/>: <jstl:out value="${dashboard.ratioChorbisWhoSearchLove}" /><br>
			<br>
			<spring:message code="dashboard.chorbiesSortedByLikes"/>: <jstl:out value="${dashboard.chorbiesSortedByLikes}" /><br>
			<spring:message code="dashboard.minLikesPerChorbi"/>: <jstl:out value="${dashboard.minLikesPerChorbi}" /><br>
			<spring:message code="dashboard.maxLikesPerChorbi"/>: <jstl:out value="${dashboard.maxLikesPerChorbi}" /><br>
			<spring:message code="dashboard.avgLikesPerChorbi"/>: <jstl:out value="${dashboard.avgLikesPerChorbi}" /><br>		
			<br>
			<spring:message code="dashboard.minChirpsReceived"/>: <jstl:out value="${dashboard.minChirpsReceived}" /><br>
			<spring:message code="dashboard.maxChirpsReceived"/>: <jstl:out value="${dashboard.maxChirpsReceived}" /><br>
			<spring:message code="dashboard.avgChirpsReceived"/>: <jstl:out value="${dashboard.avgChirpsReceived}" /><br>
			<spring:message code="dashboard.minChirpsSend"/>: <jstl:out value="${dashboard.minChirpsSend}" /><br>
			<spring:message code="dashboard.maxChirpsSend"/>: <jstl:out value="${dashboard.maxChirpsSend}" /><br>
			<spring:message code="dashboard.avgChirpsSend"/>: <jstl:out value="${dashboard.avgChirpsSend}" /><br>
			<spring:message code="dashboard.chorbiesMoreChirpsReceived"/>: <jstl:out value="${dashboard.chorbiesMoreChirpsReceived}" /><br>
			<spring:message code="dashboard.chorbiesMoreChirpsSent"/>: <jstl:out value="${dashboard.chorbiesMoreChirpsSent}" /><br>
			
			<spring:message code="dashboard.listManagersOrderByEvents"/>: <jstl:out value="${dashboard.listManagersOrderByEvents}" /><br>
			<spring:message code="dashboard.listManagersOrderByAmount"/>: <jstl:out value="${dashboard.listManagersOrderByAmount}" /><br>
			<spring:message code="dashboard.listChorbiesOrderyByEvents"/>: <jstl:out value="${dashboard.listChorbiesOrderyByEvents}" /><br>
			<spring:message code="dashboard.listChorbiesOrderByAmount"/>: <jstl:out value="${dashboard.listChorbiesOrderByAmount}" /><br>
			
			<spring:message code="dashboard.minStars"/>: <jstl:out value="${dashboard.minStars}" /><br>
			<spring:message code="dashboard.maxStars"/>: <jstl:out value="${dashboard.maxStars}" /><br>
			<spring:message code="dashboard.avgStars"/>: <jstl:out value="${dashboard.avgStars}" /><br>
			<spring:message code="dashboard.chorbiesOrderByStars"/>: <jstl:out value="${dashboard.chorbiesOrderByStars}" /><br>
		
		</p>
	</div>