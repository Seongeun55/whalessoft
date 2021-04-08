<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@include file="/WEB-INF/jsp/egovframework/com/web/header.jsp" %>
<!-- 콘텐츠 시작 { -->
<div class="container">
	<div class="sub_bg sub_bg_intro">
		<h2 id="sub_menu_title" class="top" title="오시는길">
			<strong title="오시는길">오시는길</strong>
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
				<li class="leftmenu_s  gnb_2dli_4 active"><a href="/content.do?id=page4" target="_self" >오시는길</a></li>
            	<script language='javascript'> display_submenu(0); </script> </ul>
    	</li>
    	</ul>
		
		<ul id="mysub1" style="display:none">
        <li>
        	<span class="sound_only">사업안내</span>
            <ul class="leftmenu ">
                <li class="leftmenu_s border-left gnb_2dli_1 "><a href="/content.do?id=page5" target="_self" >사업안내</a></li>
            </ul>
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
   	<div class="title-wrap">오시는길</div>
   	<div class="con-box">
   		<div id="daumRoughmapContainer1604656394296" class="root_daum_roughmap root_daum_roughmap_landing"></div>
   		<div class="map-info"><i class="xi-compass"></i> 서울특별시 구로구 디지털로 236(가리봉동 134-117) 1F 웨일즈소프트 1661-9440</div>
    </div>
    <div class="con-box">
    	<ol class="item-map">
    		<li>
    			<div class="tit">
    				<strong class="num">01</strong>
    				<strong>지하철 이용시</strong>
    			</div>
    			<div class="txt">웨일즈소프트역1번 출구 700m</div>
    		</li>
    		<li>
    			<div class="tit">
    				<strong class="num">02</strong>
    				<strong>버스 이용시</strong>
    			</div>
    			<div class="txt">
    				<ul class="layout-flex">
    					<li>
    						직통버스<br />
    						<span class="badge">000</span> <span class="badge">000</span>
    					</li>
    					<li>
    						일반버스<br />
    						<span class="badge green">000</span> <span class="badge green">000</span>
    					</li>
    					<li>
    						마을버스<br />
    						<span class="badge purple">000</span> <span class="badge purple">000</span>
    					</li>
    					<li>
    						광역버스<br />
    						<span class="badge red">000</span> <span class="badge red">000</span>
    					</li>
    				</ul>
    			</div>
    		</li>
    		<li>
    			<div class="tit">
    				<strong class="num">03</strong>
    				<strong>자가용 이용시</strong>
    			</div>
    			<div class="txt">
    				<ul class="item-space">
    					<li>
    						<strong class="c-main">1) AA방면쪽으로 오는 길</strong><br />
    						<span>설명텍스내용설명텍스내용설명텍스내용설명텍스내용</span>
    					</li>
    					<li>
    						<strong class="c-main">2) AA방면쪽으로 오는 길</strong><br />
    						<span>설명텍스내용설명텍스내용설명텍스내용설명텍스내용</span>
    					</li>
    					<li>
    						<strong class="c-main">3) AA방면쪽으로 오는 길</strong><br />
    						<span>설명텍스내용설명텍스내용설명텍스내용설명텍스내용</span>
    					</li>
    				</ul>
    			</div>
    		</li>
    	</ol>
    </div>
   </div>
   <script charset="UTF-8" class="daum_roughmap_loader_script" src="https://ssl.daumcdn.net/dmaps/map_js_init/roughmapLoader.js"></script>
   <script type="text/javascript">
$(document).ready(function(){
	new daum.roughmap.Lander({
		"timestamp" : "1604656394296",
		"key" : "22tvp",
		"mapHeight" : "360"
	}).render();
});
</script>

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