<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@include file="/WEB-INF/jsp/egovframework/com/web/header.jsp" %>
<!-- 콘텐츠 시작 { -->
<div class="container">
	<div class="sub_bg sub_bg_intro">
		<h2 id="sub_menu_title" class="top" title="조직도">
			<strong title="조직도">조직도</strong>
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
            	<li class="leftmenu_s  gnb_2dli_3 active"><a href="/content.do?id=page3" target="_self" >조직도</a></li>
            	<script language='javascript'> display_submenu(0); </script> 	                
            	<li class="leftmenu_s  gnb_2dli_4 "><a href="/content.do?id=page4" target="_self" >오시는길</a></li>
            </ul>
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