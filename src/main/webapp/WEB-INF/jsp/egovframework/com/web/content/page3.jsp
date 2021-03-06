<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@include file="/WEB-INF/jsp/egovframework/com/web/header.jsp" %>
<!-- 콘텐츠 시작 { -->
<div class="container">
	<%@include file="/WEB-INF/jsp/egovframework/com/web/subheader.jsp" %>
    
	<div class="sub-con">
   	<div class="title-wrap">조직도</div>
   	<div class="con-box sub-org">
   		<ul class="item-org">
   			<li class="ceo bline"><span class="box">CEO</span></li>
   			<li class="md bline">
   				<span class="box">이사</span>
   				<span class="box etc">감사팀</span>
   			</li>
   			<li class="team tline">
   				<span class="box">선행개발팀</span>
   				<ul>
   					<li>웨일즈소프트</li>
   					<li>웨일즈소프트</li>
   					<li>웨일즈소프트</li>
   					<li>웨일즈소프트</li>
   				</ul>
   			</li>
   			<li class="team tline">
   				<span class="box">마케팅팀</span>
   				<ul>
   					<li>웨일즈소프트</li>
   					<li>웨일즈소프트</li>
   					<li>웨일즈소프트</li>
   					<li>웨일즈소프트</li>
   				</ul>
   			</li>
   			<li class="team tline">
   				<span class="box">기획팀</span>
   				<ul>
   					<li>웨일즈소프트</li>
   					<li>웨일즈소프트</li>
   					<li>웨일즈소프트</li>
   					<li>웨일즈소프트</li>
   				</ul>
   			</li>
   			<li class="team tline">
   				<span class="box">디자인팀</span>
   				<ul>
   					<li>웨일즈소프트</li>
   					<li>웨일즈소프트</li>
   					<li>웨일즈소프트</li>
   					<li>웨일즈소프트</li>
   				</ul>
   			</li>
   		</ul>
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