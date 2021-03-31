<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="header.jsp" %>
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
<%@include file="footer.jsp" %>