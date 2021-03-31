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
		<div class="menu_bg"></div>
		<div class="container">
			<div class="visual_wrap">
				<ul class="visual wideslick">
				<!-- [수정] 이미지 불러오는부분 수정 - 2021.03.26 -->
				<c:forEach var="banner" items="${bannerList}" varStatus="status">
					<li>
                  		<div class="roll_content">							
				        	<img src='<c:url value='/cmm/fms/getImage.do'/>?atchFileId=<c:out value="${banner.bannerImageFile}"/>&fileSn=0' alt="배너이미지"/>
                    	</div>
                	</li>
				</c:forEach>	
				</ul>
				<ul class="dots">
					<li class="on"><a href="javascript:void(0);">01. 미래를 만들어 갑니다.</a></li>
					<li><a href="javascript:void(0);">02. 내일을 바라봅니다.</a></li>
					<li><a href="javascript:void(0);">03. 사람을 생각합니다.</a></li>
					<li><a href="javascript:void(0);">04. 신뢰를 쌓아갑니다.</a></li>
				</ul>
			</div>
			<div class="info wrapper">
				<ul class="company_img autoslick">
					<li><img src="/images/web/whales_images/pic/pic_info01.png" alt="회사 전경"></li>
					<li><img src="/images/web/whales_images/pic/pic_info02.png" alt="회사 전경"></li>
				</ul>
				<div class="side_con">
					<h3>우리 회사를 소개합니다.</h3>
					<p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic</p>
					<a href="#" class="view_btn box blue">DETAIL VIEW</a>
				</div>	
			</div>
			<div class="admin">
				<div class="sub title">
					<h3>관리자가 등록할 수 있습니다.</h3>
					<p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown</p>
				</div>
				<ul class="admin_slick board_list">
					<li>
						<a href="#">
							<p class="thumb_img"><img src="/images/web/whales_images/pic/pic_admin01.png" alt=""></p>
							<div class="txt_box">
								<strong class="tit">제목은 이렇습니다.</strong>
								<p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown</p>
								<span class="view_btn">DETIAL VIEW</span>
							</div>
						</a>
					</li>			
					<li>
						<a href="#">
							<p class="thumb_img"><img src="/images/web/whales_images/pic/pic_admin02.png" alt=""></p>
							<div class="txt_box">
								<strong class="tit">우리는 생각합니다.</strong>
								<p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown</p>
								<span class="view_btn">DETIAL VIEW</span>
							</div>
						</a>
					</li>	
					<li>
						<a href="#">
							<p class="thumb_img"><img src="/images/web/whales_images/pic/pic_admin03.png" alt=""></p>
							<div class="txt_box">
								<strong class="tit">신뢰를 쌓아갑니다.</strong>
								<p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown</p>
								<span class="view_btn">DETIAL VIEW</span>
							</div>
						</a>
					</li>	
					<li>
						<a href="#">
							<p class="thumb_img"><img src="/images/web/whales_images/pic/pic_admin01.png" alt=""></p>
							<div class="txt_box">
								<strong class="tit">제목은 이렇습니다.</strong>
								<p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown</p>
								<span class="view_btn">DETIAL VIEW</span>
							</div>
						</a>
					</li>			
					<li>
						<a href="#">
							<p class="thumb_img"><img src="/images/web/whales_images/pic/pic_admin02.png" alt=""></p>
							<div class="txt_box">
								<strong class="tit">우리는 생각합니다.</strong>
								<p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown</p>
								<span class="view_btn">DETIAL VIEW</span>
							</div>
						</a>
					</li>	
					<li>
						<a href="#">
							<p class="thumb_img"><img src="/images/web/whales_images/pic/pic_admin03.png" alt=""></p>
							<div class="txt_box">
								<strong class="tit">신뢰를 쌓아갑니다.</strong>
								<p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown</p>
								<span class="view_btn">DETIAL VIEW</span>
							</div>
						</a>
					</li>	
				</ul>
			</div>
			<div class="vision">
				<div class="wrapper">
					<div class="side_con">
						<h3>우리 비전을 소개합니다.</h3>
						<p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic</p>
						<a href="#" class="view_btn box blue">DETAIL VIEW</a>
					</div>
				</div>
			</div>
			<div class="product wrapper">
				<div class="title">
					<h3>제품을 소개합니다.</h3>
					<p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown</p>
				</div>	
				<div class="tab_wrap">
					<ul class="tab">
						<li class="on"><a href="#">카테고리</a></li>
						<li><a href="#">카테고리</a></li>
						<li><a href="#">카테고리</a></li>
						<li><a href="#">카테고리</a></li>
					</ul>
					<div class="tab_con">
						<div id="con01">
							<ul class="board_list">
								<li>
									<a href="#">
										<p class="thumb_img"><img src="/images/web/whales_images/pic/pic_product01.png" alt=""></p>
										<div class="txt_box">
											<strong class="tit">제목은 이렇습니다.</strong>
											<p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown</p>
											<span class="view_btn">DETIAL VIEW</span>
										</div>
									</a>
								</li>	
								<li>
									<a href="#">
										<p class="thumb_img"><img src="/images/web/whales_images/pic/pic_product02.png" alt=""></p>
										<div class="txt_box">
											<strong class="tit">제목은 이렇습니다.</strong>
											<p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown</p>
											<span class="view_btn">DETIAL VIEW</span>
										</div>
									</a>
								</li>
								<li>
									<a href="#">
										<p class="thumb_img"><img src="/images/web/whales_images/pic/pic_product03.png" alt=""></p>
										<div class="txt_box">
											<strong class="tit">제목은 이렇습니다.</strong>
											<p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown</p>
											<span class="view_btn">DETIAL VIEW</span>
										</div>
									</a>
								</li>
								<li>
									<a href="#">
										<p class="thumb_img"><img src="/images/web/whales_images/pic/pic_product04.png" alt=""></p>
										<div class="txt_box">
											<strong class="tit">제목은 이렇습니다.</strong>
											<p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown</p>
											<span class="view_btn">DETIAL VIEW</span>
										</div>
									</a>
								</li>	
								<li>
									<a href="#">
										<p class="thumb_img"><img src="/images/web/whales_images/pic/pic_product01.png" alt=""></p>
										<div class="txt_box">
											<strong class="tit">제목은 이렇습니다.</strong>
											<p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown</p>
											<span class="view_btn">DETIAL VIEW</span>
										</div>
									</a>
								</li>	
								<li>
									<a href="#">
										<p class="thumb_img"><img src="/images/web/whales_images/pic/pic_product02.png" alt=""></p>
										<div class="txt_box">
											<strong class="tit">제목은 이렇습니다.</strong>
											<p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown</p>
											<span class="view_btn">DETIAL VIEW</span>
										</div>
									</a>
								</li>
								<li>
									<a href="#">
										<p class="thumb_img"><img src="/images/web/whales_images/pic/pic_product03.png" alt=""></p>
										<div class="txt_box">
											<strong class="tit">제목은 이렇습니다.</strong>
											<p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown</p>
											<span class="view_btn">DETIAL VIEW</span>
										</div>
									</a>
								</li>
								<li>
									<a href="#">
										<p class="thumb_img"><img src="/images/web/whales_images/pic/pic_product04.png" alt=""></p>
										<div class="txt_box">
											<strong class="tit">제목은 이렇습니다.</strong>
											<p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown</p>
											<span class="view_btn">DETIAL VIEW</span>
										</div>
									</a>
								</li>	
							</ul>
						</div>
						<div id="con02">
							<ul class="board_list">
								<li>
									<a href="#">
										<p class="thumb_img"><img src="/images/web/whales_images/pic/pic_product01.png" alt=""></p>
										<div class="txt_box">
											<strong class="tit">제목은 이렇습니다.</strong>
											<p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown</p>
											<span class="view_btn">DETIAL VIEW</span>
										</div>
									</a>
								</li>	
							</ul>
						</div>
						<div id="con03">
							<ul class="board_list">
								<li>
									<a href="#">
										<p class="thumb_img"><img src="/images/web/whales_images/pic/pic_product01.png" alt=""></p>
										<div class="txt_box">
											<strong class="tit">제목은 이렇습니다.</strong>
											<p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown</p>
											<span class="view_btn">DETIAL VIEW</span>
										</div>
									</a>
								</li>	
								<li>
									<a href="#">
										<p class="thumb_img"><img src="/images/web/whales_images/pic/pic_product02.png" alt=""></p>
										<div class="txt_box">
											<strong class="tit">제목은 이렇습니다.</strong>
											<p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown</p>
											<span class="view_btn">DETIAL VIEW</span>
										</div>
									</a>
								</li>
							</ul>
						</div>
						<div id="con04">
							<ul class="board_list">
								<li>
									<a href="#">
										<p class="thumb_img"><img src="/images/web/whales_images/pic/pic_product01.png" alt=""></p>
										<div class="txt_box">
											<strong class="tit">제목은 이렇습니다.</strong>
											<p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown</p>
											<span class="view_btn">DETIAL VIEW</span>
										</div>
									</a>
								</li>	
								<li>
									<a href="#">
										<p class="thumb_img"><img src="/images/web/whales_images/pic/pic_product02.png" alt=""></p>
										<div class="txt_box">
											<strong class="tit">제목은 이렇습니다.</strong>
											<p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown</p>
											<span class="view_btn">DETIAL VIEW</span>
										</div>
									</a>
								</li>
								<li>
									<a href="#">
										<p class="thumb_img"><img src="/images/web/whales_images/pic/pic_product03.png" alt=""></p>
										<div class="txt_box">
											<strong class="tit">제목은 이렇습니다.</strong>
											<p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown</p>
											<span class="view_btn">DETIAL VIEW</span>
										</div>
									</a>
								</li>
							</ul>
						</div>
					</div>
				</div>
			</div>
			<div class="different">
				<div class="wrapper">
					<div class="title">
						<h3>웨일즈소프트는 다릅니다.</h3>
						<p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown</p>
					</div>
					<div class="count">
						<ul>
							<li class="year">
								<strong class="num counter">2020</strong>
								<p>설립년도</p>
							</li>
							<li class="project">
								<strong class="num counter">8469</strong>
								<p>프로젝트</p>
							</li>
							<li class="staff">
								<strong class="num counter">246</strong>
								<p>직원수</p>
							</li>
							<li class="partner">
								<strong class="num counter">1678</strong>
								<p>파트너사</p>
							</li>
						</ul>
					</div>
				</div>
			</div>
			<div class="vision_list wrapper">
				<div class="title">
					<h3>비전을 소개합니다.</h3>
					<p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown</p>
				</div>
				<ul class="board_list">
					<li>
						<a href="#">
							<p class="thumb_img"><img src="/images/web/whales_images/pic/pic_vision01.png" alt="우리는 더 앞선 발걸음을 걸어갑니다."></p>
							<div class="txt_box">
								<strong class="tit">우리는 더 앞선 발걸음을 걸어갑니다.</strong>
								<p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown, Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown</p>
								<span class="view_btn">DETIAL VIEW</span>
							</div>
						</a>
					</li>	
					<li>
						<a href="#">
							<p class="thumb_img"><img src="/images/web/whales_images/pic/pic_vision02.png" alt="우리는 더 앞선 발걸음을 걸어갑니다."></p>
							<div class="txt_box">
								<strong class="tit">우리는 더 앞선 발걸음을 걸어갑니다.</strong>
								<p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown, Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown</p>
								<span class="view_btn">DETIAL VIEW</span>
							</div>
						</a>
					</li>
					<li>
						<a href="#">
							<p class="thumb_img"><img src="/images/web/whales_images/pic/pic_vision03.png" alt="우리는 더 앞선 발걸음을 걸어갑니다."></p>
							<div class="txt_box">
								<strong class="tit">우리는 더 앞선 발걸음을 걸어갑니다.</strong>
								<p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown, Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown</p>
								<span class="view_btn">DETIAL VIEW</span>
							</div>
						</a>
					</li>	
					<li>
						<a href="#">
							<p class="thumb_img"><img src="/images/web/whales_images/pic/pic_vision04.png" alt="우리는 더 앞선 발걸음을 걸어갑니다."></p>
							<div class="txt_box">
								<strong class="tit">우리는 더 앞선 발걸음을 걸어갑니다.</strong>
								<p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown, Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown</p>
								<span class="view_btn">DETIAL VIEW</span>
							</div>
						</a>
					</li>
				</ul>
			</div>
			<div class="notice">
				<div class="wrapper">
					<div class="title">
						<h3>공지사항</h3>
						<p>새소식을 알려드립니다.</p>
					</div>
					<div class="notice_list">
						<ul>
							<li>
								<a href="#">
									<p class="thumbimg"><img src="/images/web/whales_images/pic/pic_notice01.png" alt=""></p>
									<div class="txtbox">
										<strong class="tit">우리는 더 앞선 발걸음을 우리는 더 앞선 발걸음을</strong>
										<p class="sub">Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic</p>
										<span class="date">2020.10.30</span>
									</div>
								</a>
							</li>
							<li>
								<a href="#">
									<p class="thumbimg"><img src="/images/web/whales_images/pic/pic_notice02.png" alt=""></p>
									<div class="txtbox">
										<strong class="tit">우리는 더 앞선 발걸음을 우리는 더 앞선 발걸음을</strong>
										<p class="sub">Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic</p>
										<span class="date">2020.10.30</span>
									</div>
								</a>
							</li>
						</ul>
					</div>
					<a href="#"><span class="view_btn">DETAIL VIEW</span></a>
				</div>
			</div>
		</div>
		<footer>
			<a href="#" class="to_top"><img src="/images/web/whales_images/ico/ico_click_top01.png" alt="상단으로 이동"></a>
			<div class="wrapper">
				<h1 class="logo"><a href="#">웨일즈소프트</a></h1>
				<div class="content">
					<ul class="imformation">
						<li><a href="#">회사소개</a></li>
						<li><a href="#">이용약관</a></li>
						<li><a href="#">고객센터</a></li>
						<li><a href="#">개인정보처리방침</a></li>
					</ul>
					<div class="address">
						<p><span class="no_deco">웨일즈소프트</span><span>대표.황윤규</span><span>서울특별시 구로구 디지털로 236(가리봉동 134-117) 1F 웨일즈 소프트</span><span class="no_deco">사업자등록번호. 134-30-39407</span>	</p>
						<p><span class="no_deco">통신판매업신고번호.제 2017-서울구로-1318호</span><a href="tel:1661-9440">Tel. 1661-9440</a><span>Fax. 02-6969-9450</span><a href="#">E-mail.marster@whalessoft.com</a></p>
						<p class="copy">Copyright © 웨일즈소프트. All rights reserved. Hosting by Whalessoft</p>
					</div>
				</div>
				<div class="sns">
					<ul>
						<li><a href="#"><img src="/images/web/whales_images/ico/ico_sns01.png" alt="웨일즈소프트 네이버 바로가기"></a></li>	
						<li><a href="#"><img src="/images/web/whales_images/ico/ico_sns02.png" alt="웨일즈소프트 페이스북 바로가기"></a></li>	
						<li><a href="#"><img src="/images/web/whales_images/ico/ico_sns03.png" alt="웨일즈소프트 인스타그램 바로가기"></a></li>	
						<li><a href="#"><img src="/images/web/whales_images/ico/ico_sns04.png" alt="웨일즈소프트 카카오톡 바로가기"></a></li>	
					</ul>
				</div>
			</div>
		</footer>
	</div>
</body>
</html>