<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>

<%@include file="header.jsp" %>
	
	<div class="container-fluid">
		<iframe name="_content" src="${pageContext.request.contextPath}/AdminContent.do" onload="this.height=(this.contentWindow.document.body.scrollHeight+30);" width="100%" frameborder="0" framespacing="0" marginheight="0" marginwidth="0" scrolling="yes" vspace="0"></iframe>
	</div>

<%@include file="footer.jsp" %>          