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
	<link rel="stylesheet" type="text/css" href="/css/web/whales_css/style.css"/>
	<script src="/js/web/whales_js/jquery-1.12.4.min.js"></script>
	<script src="/js/web/whales_js/waypoints.min.js"></script>
	<script src="/js/web/whales_js/jquery.counterup.min.js"></script>
	<script src="/js/web/whales_js/slick.min.js"></script>
	<script src="/js/web/whales_js/front.js"></script>
	<title>웨일즈소프트</title>
</head>
<body>
 	<div id="wrap">
		<header>
			<div class="admin_wrap">
				<ul>
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
				<h1><a href="/index.do">웨일즈소프트</a></h1>
				<a href="#" class="allmenu"></a>
				<nav>
					<ul class="cate depth01">
						<li>
							<a href="#">회사소개</a>
							<div class="depth02">
								<ul>
									<li><a href="#">하위메뉴1</a></li>
									<li><a href="#">하위메뉴2</a></li>
									<li><a href="#">하위메뉴3</a></li>
									<li><a href="#">하위메뉴4</a></li>
								</ul>
							</div>
						</li>
						<li>
							<a href="#">사업안내</a>
							<div class="depth02">
								<ul>
									<li><a href="#">하위메뉴1</a></li>
									<li><a href="#">하위메뉴2</a></li>
									<li><a href="#">하위메뉴3</a></li>
									<li><a href="#">하위메뉴4</a></li>
									<li><a href="#">하위메뉴5</a></li>
								</ul>
							</div>
						</li>
						<li>
							<a href="#">제품소개</a>
							<div class="depth02">
								<ul>
									<li><a href="#">하위메뉴1</a></li>
									<li><a href="#">하위메뉴2</a></li>
									<li><a href="#">하위메뉴3</a></li>
									<li><a href="#">하위메뉴4</a></li>
									<li><a href="#">하위메뉴5</a></li>
									<li><a href="#">하위메뉴6</a></li>
								</ul>
							</div>
						</li>
						<li>
							<a href="#">채용정보</a>
							<div class="depth02">
								<ul>
									<li><a href="#">하위메뉴1</a></li>
									<li><a href="#">하위메뉴2</a></li>
									<li><a href="#">하위메뉴3</a></li>
									<li><a href="#">하위메뉴4</a></li>
								</ul>
							</div>
						</li>
						<li>
							<a href="#">고객센터</a>
							<div class="depth02">
								<ul>
									<li><a href="#">공지사항</a></li>
									<li><a href="#">Q&A</a></li>
									<li><a href="#">FAQ</a></li>
								</ul>
							</div>
						</li>
					</ul>
				</nav>
			</div>
		</header>