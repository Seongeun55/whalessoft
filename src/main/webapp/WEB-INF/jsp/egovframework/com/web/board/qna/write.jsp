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
<script type="text/javascript">
window.onload = function fn_egov_init(){

		// 첫 입력란에 포커스
		document.getElementById("qnaVO").qestnSj.focus();
}

function fn_egov_regist_qna(form){
	if (confirm("<spring:message code="common.regist.msg" />")) {
		form.submit();
	}
}
</script>
<!-- 콘텐츠 시작 { -->
<div class="container">
	<%@include file="/WEB-INF/jsp/egovframework/com/web/subheader.jsp" %>
	
	<div class="sub-con">
    	<div class="title-wrap">Q&A 글쓰기</div>
	</div>
	<section id="bo_w">
    <h2 class="sound_only">온라인문의 글쓰기</h2>

	<!-- javascript warning tag  -->
	<noscript class="noScriptTitle"><spring:message code="common.noScriptTitle.msg" /></noscript>
	
    <!-- 게시물 작성 시작 -->
    <form:form commandName="qnaVO" action="${pageContext.request.contextPath}/uss/olh/qna/insertQna.do" onSubmit="fn_egov_regist_qna(document.forms[0]); return false;" method="post">    
	    <div class="bo_w_info write_div">
	    	<div style="width:24%;" class="three_div">
	    		<!-- 작성자명 -->
				<c:set var="title"><spring:message code="table.reger"/> </c:set>
				
			    <label for="wrterNm" class="sound_only">${title}<strong>필수</strong></label>
			    <form:input type="text" path="wrterNm" id="wrterNm" required="required" class="frm_input full_input required" placeholder="이름" />
			    <div><form:errors path="wrterNm" cssClass="error" /></div>     
		    </div>
		    
		    <div style="width:24%;" class="three_div">
		    	<!-- 이메일주소 -->
				<c:set var="title"><spring:message code="comUssOlhQna.qnaVO.emailAdres"/> </c:set>
				
			    <label for="emailAdres" class="sound_only">${title}</label>
			    <form:input type="email" path="emailAdres" value="" id="emailAdres" class="frm_input full_input email " placeholder="이메일" />
			    <div><form:errors path="emailAdres" cssClass="error" /></div>  
		    </div>
			
			<div style="width:50%;" class="three_div">
				<!-- 전화번호 -->
				<c:set var="title"><spring:message code="comUssOlhQna.qnaVO.telNo"/> </c:set>

				<label for="areaNo" class="sound_only">${title}<strong>필수</strong></label>
			    <form:input type="text" path="areaNo" id="areaNo" required="required" class="frm_input three_input required" placeholder="010" />
			    <Span>-</Span>
			    <form:input type="text" path="middleTelno" id="middleTelno" required="required" class="frm_input three_input required" placeholder="1234" />
			    <span>-</span>
			    <form:input type="text" path="endTelno" id="endTelno" required="required" class="frm_input three_input required" placeholder="5678" />
			    <div><form:errors path="areaNo" cssClass="error" /></div>    
			</div>	
		</div>

	    <div class="bo_w_tit write_div">
	    	<!-- 질문제목 -->
			<c:set var="title"><spring:message code="comUssOlhQna.qnaVO.qestnSj"/> </c:set>
			
	        <label for="qestnSj" class="sound_only">${title}<strong>필수</strong></label>
	        <div id="autosave_wrapper" class="write_div">
	            <form:input type="text" path="qestnSj" value="" id="qestnSj" required="required" class="frm_input full_input required" size="50" maxlength="255" placeholder="제목" />
	            <div><form:errors path="qestnSj" cssClass="error" /></div>     
	        </div>
	        
	    </div>
	
	    <div class="write_div">
	    	<!-- 질문내용 -->
			<c:set var="title"><spring:message code="comUssOlhQna.qnaVO.qestnCn"/> </c:set>
			
	        <label for="qestnCn" class="sound_only">${title}<strong>필수</strong></label>
	        <div class="wr_content ">
				<span class="sound_only">웹에디터 시작</span>
					<form:textarea id="qestnCn" path="qestnCn" required="required" maxlength="65536" style="width:100%;height:300px" />
					<div><form:errors path="qestnCn" cssClass="error" /></div> 
				<span class="sound_only">웹 에디터 끝</span>                    
			</div>        
	    </div>
	
	    <div class="btn_confirm write_div">
	        <a href="/qna/list.do?menuNo=${param.menuNo}" class="btn_cancel btn">취소</a>
	        <button type="submit" id="btn_submit" accesskey="s" class="btn_submit btn">작성완료</button>
	    </div>
	    
	    <input name="answerCn" type="hidden" value="<c:out value='answer'/>">
		<input name="cmd" type="hidden" value="<c:out value='save'/>">
		<input name="menuNo" type="hidden" value="${param.menuNo}">
    </form:form>
	</section>
<!-- } 게시물 작성/수정 끝 -->
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