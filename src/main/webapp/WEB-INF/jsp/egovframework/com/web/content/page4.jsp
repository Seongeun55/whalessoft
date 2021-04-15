<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@include file="/WEB-INF/jsp/egovframework/com/web/header.jsp" %>
<!-- 콘텐츠 시작 { -->
<div class="container">
	<%@include file="/WEB-INF/jsp/egovframework/com/web/subheader.jsp" %>

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