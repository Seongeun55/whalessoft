<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
	<script src="/js/jquery-1.12.4.min.js"></script>
	<script src="/js/waypoints.min.js"></script>
	<script src="/js/jquery.counterup.min.js"></script>
	<script src="/js/slick.min.js"></script>
	<script src="/js/front.js"></script>
	<script src="/js/aos.js"></script>
	<script type="text/javascript">
	function menuMove(menuNo, url){
	    location.href = "/sym/mnu/mpm/MainMenuIndex.do?menuNo="+menuNo+"&chkURL="+url;
	}
	</script>
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
						<c:forEach var="result" items="${list_headmenu}" varStatus="status">								
						   <li><a href="#" onclick="menuMove('<c:out value="${result.menuNo}"/>','<c:out value="${result.chkURL}"/>')" ><c:out value="${result.menuNm}"/></a></li>
						</c:forEach>									
					</ul>
				</nav>
			</div>
		</header>
		<div class="menu_bg"></div>