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
	<%@include file="/WEB-INF/jsp/egovframework/com/web/subheader.jsp" %>
        
	<div class="sub-con">
    	<div class="title-wrap">공지사항</div>
	</div>
	<!-- 게시물 읽기 시작 { -->

	<article id="bo_v">
	    <div class="bo_v_header">
	        <h2 id="bo_v_title">
				<span class="bo_v_tit">${result.nttSj}</span>
	        </h2>
	    </div>
	
	    <section id="bo_v_info">
	        <h2>페이지 정보</h2>
	        <div class="profile_info">
	        	<div class="pf_img"><img src="/images/sub/no_profile.gif" alt="profile_image"></div>
	        	<div class="profile_info_ct">
	        		<span class="sound_only">작성자</span> <strong><span class="sv_member">${result.frstRegisterNm}</span></strong><br>  		 	
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
	        	${fn:replace(result.nttCn , crlf , '<br/>')}
			</div>
										
			<!-- 첨부파일  -->
			<c:if test="${not empty result.atchFileId}">
			<hr>	
				<spring:message code="comCopBbs.articleVO.detail.atchFile" />			
				<c:import url="/cmm/fms/selectFileInfs.do" charEncoding="utf-8">
					<c:param name="param_atchFileId" value="${result.atchFileId}" />
				</c:import>	
			<hr>
		  	</c:if>
	
	        <!-- } 본문 내용 끝 -->
	        <!-- 하단 버튼 -->
			<div class="btn" style="float:right">
				<form name="formList" action="/board/list.do?bbsId=${param.bbsId}" method="post" style="float:left; margin:7px 0 0 3px;">
					<input type="submit" class="s_submit" value="<spring:message code="button.list" />">
				</form>
			</div>
			<div style="clear:both;"></div>
		  	
		  	<input name="cmd" type="hidden" value="">
	    </section>
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