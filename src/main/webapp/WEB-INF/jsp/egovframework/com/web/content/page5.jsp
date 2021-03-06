<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@include file="/WEB-INF/jsp/egovframework/com/web/header.jsp" %>
<!-- 콘텐츠 시작 { -->
<div class="container">
	<%@include file="/WEB-INF/jsp/egovframework/com/web/subheader.jsp" %>
	
	<div class="sub-con">
   	<div class="title-wrap">사업안내</div>
   	<p class="txt-c" data-aos="fade-up" data-aos-duration="800">
   		<strong><big>웨일즈소프트, 웨일즈소프트 웨일즈소프트<br />고객의 신뢰와 함께 성장해 왔습니다.</big></strong><br /><br />
   		<span>웨일즈소프트 웨일즈소프트 웨일즈소프트 웨일즈소프트 웨일즈소프트 웨일즈소프트<br />웨일즈소프트 웨일즈소프트웨일즈소프트웨일즈소프트</span>
    </p>
    
    <div class="con-box">
    	<ul class="item-bus">
    		<li>
    			<span class="round"></span>
    			<div class="img" data-aos="fade-right" data-aos-duration="1000">
    				<img src="/images/sub/s08.jpg" alt="" />
    			</div>
    			<div class="txt" data-aos="fade-left" data-aos-duration="1000">
    				<div class="txt-box">
    					<div class="tit">웨일즈소프트</div>
    					<p class="con">
    						Lorem Ipsum is simply dummy text of the printing Lorem Ipsum has been the industry's standard dummy text ever since 1500s, when an unknown printer took a galley
    					</p>
    					<div class="txt-r"><a href="#">Read More</a></div>
    				</div>
    			</div>
    		</li>
    		<li>
    			<span class="round-border"></span>
    			<div class="img" data-aos="fade-left" data-aos-duration="1000">
    				<img src="/images/sub/s08.jpg" alt="" />
    			</div>
    			<div class="txt" data-aos="fade-right" data-aos-duration="1000">
    				<div class="txt-box">
    					<div class="tit">웨일즈소프트</div>
    					<p class="con">
    						Lorem Ipsum is simply dummy text of the printing Lorem Ipsum has been the industry's standard dummy text ever since 1500s, when an unknown printer took a galley
    					</p>
    					<div class="txt-r"><a href="#">Read More</a></div>
    				</div>
    			</div>
    		</li>
    	</ul>
    </div>
   </div>
<div class="vision">
	<div class="wrapper">
		<div class="side_con">
			<h3>우리 비전을 소개합니다.</h3>
			<p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic</p>
			<a href="/content.do?id=page1" class="view_btn box blue">DETAIL VIEW</a>
		</div>
	</div>
</div>

</div>
<!-- } 콘텐츠 끝 -->

<script>
$(function() {
	AOS.init();
    // 폰트 리사이즈 쿠키있으면 실행
    font_resize("container", get_cookie("ck_font_resize_rmv_class"), get_cookie("ck_font_resize_add_class"));
});
</script>



<!-- ie6,7에서 사이드뷰가 게시판 목록에서 아래 사이드뷰에 가려지는 현상 수정 -->
<!--[if lte IE 7]>
<script>
$(function() {
    var $sv_use = $(".sv_use");
    var count = $sv_use.length;

    $sv_use.each(function() {
        $(this).css("z-index", count);
        $(this).css("position", "relative");
        count = count - 1;
    });
});
</script>
<![endif]-->
<%@include file="/WEB-INF/jsp/egovframework/com/web/footer.jsp" %>