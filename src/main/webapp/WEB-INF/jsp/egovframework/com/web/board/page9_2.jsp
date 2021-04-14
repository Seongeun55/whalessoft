<!-- q&a 디테일 화면 추가 페이지 - 2021.04.13-->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>    
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>

<%@include file="/WEB-INF/jsp/egovframework/com/web/header.jsp" %>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<script type="text/javascript">
/* ********************************************************
 * 저장처리화면
 ******************************************************** */
function fn_egov_regist_qna(form){
	//input item Client-Side validate
	if (!validateQnaVO(form)) {	
		return false;
	} else {
		if(confirm("<spring:message code="common.regist.msg" />")){	
			form.submit();	
		}
	} 
}

function fn_egov_delete_qna(form){
	if(confirm("<spring:message code="common.delete.msg" />")){	
		// Delete하기 위한 키값을 셋팅
		form.submit();	
	}	
}
</script>
<!-- 콘텐츠 시작 { -->
<div class="container">
	<div class="sub_bg sub_bg_">
		<h2 id="sub_menu_title" class="top" title="온라인문의 글쓰기">
        	<strong title="온라인문의 글쓰기">Q&A</strong>
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
            <ul class="leftmenu ">
                <li class="leftmenu_s border-left gnb_2dli_1 "><a href="/content.do?id=page5" target="_self" >사업안내</a></li>
            </ul>
    	</li>
    	</ul>
		
		<ul id="mysub2" style="display:none">
	        <li>
	        	<span class="sound_only">제품소개</span>
	        	<ul class="leftmenu">
	                <li class="leftmenu_s border-left gnb_2dli_1 active"><a href="/board.do?id=page6" target="_self" >제품소개</a></li>
	         	</ul>
	    	</li>
    	</ul>
    	
    	<ul id="mysub3" style="display:none">
	        <li>
	        	<span class="sound_only">고객센터</span>
	            <ul class="leftmenu ">
	                <li class="leftmenu_s border-left gnb_2dli_1 "><a href="/board.do?id=page7" target="_self" >공지사항</a></li>
					<li class="leftmenu_s  gnb_2dli_2 "><a href="/board.do?id=page8" target="_self" >갤러리</a></li>
	            	<li class="leftmenu_s  gnb_2dli_3 active"><a href="/board.do?id=page9" target="_self" >Q&A</a></li>
					<script language='javascript'> display_submenu(3); </script> 
	            	<li class="leftmenu_s  gnb_2dli_4 "><a href="/board.do?id=page10" target="_self" >FAQ</a></li>
	            </ul>
	    	</li>
	    </ul>
	</div>
        
	<div class="sub-con">
    	<div class="title-wrap">Q&A</div>
	</div>
	<!-- 게시물 읽기 시작 { -->

	<article id="bo_v">
    <div class="bo_v_header">
        <h2 id="bo_v_title">
			<span class="bo_v_tit">${result.qestnSj}</span>
        </h2>
    </div>

    <section id="bo_v_info">
        <h2>페이지 정보</h2>
        <div class="profile_info">
        	<div class="pf_img"><img src="/images/sub/no_profile.gif" alt="profile_image"></div>
        	<div class="profile_info_ct">
        		<span class="sound_only">작성자</span> <strong><span class="sv_member">${result.wrterNm}</span></strong><br>
        		<c:choose>
        			<c:when test="${result.qnaProcessSttusCode ==  '3'}">
        				<span class="sound_only">댓글</span><strong><a href="#bo_vc"> <i class="fa fa-commenting-o" aria-hidden="true"></i> 1건</a></strong>
        			</c:when>
        			<c:otherwise>
        				<span class="sound_only">댓글</span><strong><a href="#bo_vc"> <i class="fa fa-commenting-o" aria-hidden="true"></i> 0건</a></strong>
        			</c:otherwise>	
        		</c:choose>       		 	
        		<span class="sound_only">조회</span><strong><i class="fa fa-eye" aria-hidden="true"></i> ${result.inqireCo}회</strong>
        		<strong class="if_date"><span class="sound_only">작성일</span><i class="fa fa-clock-o" aria-hidden="true"></i> ${result.frstRegisterPnttm}</strong>
    		</div>
    	</div>
    </section>

    <section id="bo_v_atc">
        <h2 id="bo_v_atc_title">본문</h2>
        <div id="bo_v_share"></div>

        <!-- 본문 내용 시작 { -->
        <div id="bo_v_con">
        	${fn:replace(result.qestnCn , crlf , '<br/>')}
		</div>
        <!-- } 본문 내용 끝 -->
        <!-- 하단 버튼 -->
		<div class="btn" style="float:right">
			<c:if test="${user.userSe == 'USR'}">		
				<form name="qnaForm" action="<c:url value='/admin/index.do'/>" method="post" style="float:left;">
					<input type="submit" class="s_submit" value="<spring:message code="button.reply" />" title="<spring:message code="title.reply" /> <spring:message code="input.button" />" />
					<input name="qaId" type="hidden" value="${result.qaId}">
				</form>
			</c:if>
			<c:if test="${user.uniqId==result.frstRegisterId || user.userSe == 'USR' }">
				<form name="formUpdate" action="<c:url value='/uss/olh/qna/updateQnaView.do'/>" method="post" style="float:left; margin:0 0 0 3px;">
					<input type="submit" class="s_submit" value="<spring:message code="button.update" />" title="<spring:message code="title.update" /> <spring:message code="input.button" />" />
					<input name="qaId" type="hidden" value="${result.qaId}">
				</form>
				<form name="formDelete" action="<c:url value='/uss/olh/qna/deleteQna.do'/>" method="post" style="float:left; margin:0 0 0 3px;">
					<input type="submit" class="s_submit" value="<spring:message code="button.delete" />" title="<spring:message code="button.delete" /> <spring:message code="input.button" />" onclick="fn_egov_delete_qna(this.form); return false;">
					<input name="qaId" type="hidden" value="${result.qaId}">
				</form>
			</c:if>
			<form name="formList" action="<c:url value='/board.do?id=page9'/>" method="post" style="float:left; margin:0 0 0 3px;">
				<input type="submit" class="s_submit" value="<spring:message code="button.list" />">
			</form>
		</div><div style="clear:both;"></div>

		<input name="cmd" type="hidden" value="">
        
        <!-- 답변 내용 시작 -->
        <c:if test="${result.qnaProcessSttusCode ==  '3'}">
        	<div class="coment_wrap">
        		<h2>답변</h2>
        		<div class="comment">
        			${fn:replace(result.answerCn , crlf , '<br/>')}
        		</div>
        	</div>
        </c:if>
        <!-- 답변 내용 끝 -->
    </section>
	
	<ul class="bo_v_nb">
		<li class="btn_next"><span class="nb_tit"><i class="fa fa-chevron-down" aria-hidden="true"></i> 다음글</span><a href="http://theme001.whalessoft.com/bbs/board.php?bo_table=notice&amp;wr_id=1">우리는 더 앞선 발걸음으로 나아갑니다.</a>  <span class="nb_date">20.11.15</span></li>    </ul>
    
    </article>
<!-- } 게시판 읽기 끝 -->
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