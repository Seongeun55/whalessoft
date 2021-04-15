<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@include file="/WEB-INF/jsp/egovframework/com/web/header.jsp" %>

<div class="container">
	<%@include file="/WEB-INF/jsp/egovframework/com/web/subheader.jsp" %>
	
    <div class="sub-con sub-intro">
    	<div class="title-wrap">회사소개</div>
    	<div class="con-box">
	    	<div class="layout-flex resopnse">
	    		<div class="img" data-aos="flip-left" data-aos-duration="1000"><img src="/images/sub/s02.jpg" alt="회사소개" /></div>
	    		<div class="txt">
	    			<strong class="tit" data-aos="fade-left" data-aos-duration="500">우리 회사를 소개합니다.<br />항상 노력하겠습니다.<br />웨일즈소프트</strong>
	    			<p data-aos="fade-left" data-aos-duration="600">Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic</p>
	    			<ol data-aos="fade-left" data-aos-duration="700">
	    				<li>01.웨일즈소프트는 고객님과의 약속을 항상 최우선으로 생각합니다.</li>
						<li>02.웨일즈소프트는 고객님과의 약속을 항상 최우선으로 생각합니다.</li>
						<li>03.웨일즈소프트는 고객님과의 약속을 항상 최우선으로 생각합니다.</li>
						<li>04.웨일즈소프트는 고객님과의 약속을 항상 최우선으로 생각합니다.</li>
						<li>05.웨일즈소프트는 고객님과의 약속을 항상 최우선으로 생각합니다.</li>
	    			</ol>
	    			<div class="name" data-aos="fade-left" data-aos-duration="1200">웨일즈소프트 대표이사  <strong class="f-mjo">황윤규</strong></div>
	    		</div>
	    	</div>
	    </div>
    </div>

</div>	<!-- 콘텐츠 끝 -->
	

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