<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@include file="/WEB-INF/jsp/egovframework/com/web/header.jsp" %>
<!-- 콘텐츠 시작 { -->
<div class="container">
	<div class="sub_bg sub_bg_bus">
		<h2 id="sub_menu_title" class="top" title="사업안내">
			<strong title="사업안내">사업안내</strong>
			<span class="sub-title">든든한 당신의 파트너로 곁에 있겠습니다.</span>
         </h2>
	</div>
<script type="text/javascript">
<!--
    function display_submenu(num) { 
         document.getElementById("mysub"+num).style.display="block";
    }
//-->
</script>
	<div id="submenu">
		<ul id="mysub0" style="display:none">
        <li>
        	<span class="sound_only">회사소개</span>
            <ul class="leftmenu ">
                <li class="leftmenu_s border-left gnb_2dli_1 "><a href="/content.do?id=page1" target="_self" >회사소개</a></li>
				<li class="leftmenu_s  gnb_2dli_2 "><a href="/content.do?id=page2" target="_self" >연혁</a></li>
				<li class="leftmenu_s  gnb_2dli_3 "><a href="/content.do?id=page3" target="_self" >조직도</a></li>
				<li class="leftmenu_s  gnb_2dli_4 "><a href="/content.do?id=page4" target="_self" >오시는길</a></li>
            </ul>
    	</li>
    	</ul>
			
		<ul id="mysub1" style="display:none">
        <li>
        	<span class="sound_only">사업안내</span>
            <script language='javascript'> display_submenu(1); </script> <ul class="leftmenu ">
            <li class="leftmenu_s border-left gnb_2dli_1 active"><a href="/content.do?id=page5" target="_self" >사업안내</a></li>
            <script language='javascript'> display_submenu(1); </script> </ul>
    	</li>
    	</ul>
	
		<ul id="mysub2" style="display:none">
        <li>
        	<span class="sound_only">제품소개</span>
            <ul class="leftmenu ">
                <li class="leftmenu_s border-left gnb_2dli_1 "><a href="/board.do?id=page6" target="_self" >제품소개</a></li>
            </ul>
    	</li>
    	</ul>
    	
    	<ul id="mysub3" style="display:none">
        <li>
        	<span class="sound_only">고객센터</span>
            <ul class="leftmenu ">
                <li class="leftmenu_s border-left gnb_2dli_1 "><a href="/board.do?id=page7" target="_self" >공지사항</a></li>
				<li class="leftmenu_s  gnb_2dli_2 "><a href="/board.do?id=page8" target="_self" >갤러리</a></li>
				<li class="leftmenu_s  gnb_2dli_3 "><a href="/board.do?id=page9" target="_self" >온라인문의</a></li>
				<li class="leftmenu_s  gnb_2dli_4 "><a href="/board.do?id=page10" target="_self" >자주묻는질문</a></li>
            </ul>
    	</li>
    	</ul>
    </div>
	
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