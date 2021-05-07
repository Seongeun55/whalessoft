<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="robots" content="all" />
	<meta name="format-detection" content="telephone=no">
	<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no, viewport-fit=cover" />
	<title>웨일즈소프트</title>	
	<link rel="stylesheet" type="text/css" href="/css/style.css"/>
	<link rel="stylesheet" type="text/css" href="/css/default.css"/>
	<link rel="stylesheet" type="text/css" href="/css/aos.css"/>
	<link rel="stylesheet" type="text/css" href="/css/tablestyle.css"/>
	<script src="/js/jquery-1.12.4.min.js"></script>
	<script src="/js/waypoints.min.js"></script>
	<script src="/js/jquery.counterup.min.js"></script>
	<script src="/js/slick.min.js"></script>
	<script src="/js/front.js"></script>
	<script src="/js/aos.js"></script>
</head>
<body>
 	<div id="wrap">
		<header class="hongm hongs">
			<div class="admin_wrap">
				<ul class="util">
					<c:if test="${loginVO == null}">
						<li class="join"><a href="/uss/umt/StplatCnfirmMber.do"><span>회원가입</span></a></li>							
						<li class="login"><a href="/uat/uia/LoginUsr.do"><span>로그인</span></a></li>
					</c:if>
					
					<c:if test="${loginVO != null}">
						<li class="login"><a href="/uat/uia/securityLogout.do"><span>로그아웃</span></a></li>					
						<li><a>${loginVO.name}님</a></li>	
						<c:if test="${loginVO.userSe == 'USR'}">			
							<li><a href="/admin/index.do">관리자모드</a></li>
						</c:if>	
					</c:if>
				</ul>
			</div>
			<div class="wrapper">
				<h1><a href="/main.do">웨일즈소프트</a></h1>
				<a href="#" class="allmenu"></a>
				<nav>
					<ul class="cate depth01">
						<c:forEach var="result" items="${mainMenuList}" varStatus="status">
						<c:set var="sc1" value="?"/>
						<c:if test="${fn:contains(result.chkURL, '?')}">
							<c:set var="sc1" value="&"/>
						</c:if>
							<li>
								<a href="<c:out value='${result.chkURL}'/><c:out value='${sc1}'/>menuNo=<c:out value='${result.menuNo}'/>"><c:out value="${result.menuNm}"/></a>
						   		<div class="depth02">
						   			<ul>						   			
						   			<c:forEach var="sub_result" items="${subMenuList}" varStatus="status">
					   				<c:set var="sc2" value="?"/>
									<c:if test="${fn:contains(sub_result.chkURL, '?')}">
										<c:set var="sc2" value="&"/>
									</c:if>
						   			<c:if test="${result.menuNo==sub_result.upperMenuId}">						   								
					   					<li>
					   						<a href="<c:out value="${sub_result.chkURL}"/><c:out value='${sc2}'/>menuNo=<c:out value="${sub_result.menuNo}"/>" ><c:out value="${sub_result.menuNm}"/></a>
					   					</li>
					   				</c:if>
						   			</c:forEach>
						   			</ul>
						   		</div>
						   </li>
						</c:forEach>									
					</ul>
				</nav>
			</div>
		</header>
		<div class="menu_bg"></div>