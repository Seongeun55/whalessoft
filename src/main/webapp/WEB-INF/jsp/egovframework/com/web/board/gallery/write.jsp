<%
 /**
  *	ArticleRegist.jsp 그대로 가져옴
  *  
  */
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>

<%@include file="/WEB-INF/jsp/egovframework/com/web/header.jsp" %>

<link type="text/css" rel="stylesheet" href="<c:url value='/css/egovframework/com/cmm/jqueryui.css' />">
<script type="text/javascript" src="<c:url value='/js/egovframework/com/cmm/fms/EgovMultiFiles.js'/>" ></script>
<script type="text/javascript" src="<c:url value='/js/egovframework/com/cmm/utl/EgovCmmUtl.js'/>" ></script>
<script type="text/javascript" src="<c:url value='/html/egovframework/com/cmm/utl/ckeditor/ckeditor.js?t=B37D54V'/>" ></script>
<script type="text/javascript" src="<c:url value="/validator.do"/>"></script>
<script src="<c:url value='/js/egovframework/com/cmm/jqueryui.js' />"></script>
<validator:javascript formName="articleVO" staticJavascript="false" xhtml="true" cdata="false"/>
<script type="text/javascript">

$(function() {
	$("#ntceBgnde").datepicker(   
	        {dateFormat:'yy-mm-dd' 
	         , showOn: 'button' 
	         , buttonImage: '<c:url value='/images/egovframework/com/cmm/icon/bu_icon_carlendar.gif'/>'   
	         , buttonImageOnly: true 
	         
	         , showMonthAfterYear: true
	         , showOtherMonths: true
		     , selectOtherMonths: true
				
	         , changeMonth: true // 월선택 select box 표시 (기본은 false)
	         , changeYear: true  // 년선택 selectbox 표시 (기본은 false)
	         , showButtonPanel: true // 하단 today, done  버튼기능 추가 표시 (기본은 false)
	});
	$("#ntceEndde").datepicker(   
	        {dateFormat:'yy-mm-dd' 
	         , showOn: 'button' 
	         , buttonImage: '<c:url value='/images/egovframework/com/cmm/icon/bu_icon_carlendar.gif'/>'   
	         , buttonImageOnly: true 
	         
	         , showMonthAfterYear: true
	         , showOtherMonths: true
		     , selectOtherMonths: true
				
	         , changeMonth: true // 월선택 select box 표시 (기본은 false)
	         , changeYear: true  // 년선택 selectbox 표시 (기본은 false)
	         , showButtonPanel: true // 하단 today, done  버튼기능 추가 표시 (기본은 false)
	});
});

/* ********************************************************
 * 초기화
 ******************************************************** */
 window.onload = function fn_egov_init(){
	
	//filebrowserUploadUrl: '${pageContext.request.contextPath}/utl/wed/insertImage.do', // 파일 업로드를 처리 할 경로 설정.
	var ckeditor_config = {
		//filebrowserImageUploadUrl: '${pageContext.request.contextPath}/utl/wed/insertImageCk.do', // 파일 업로드를 처리 할 경로 설정.
		filebrowserImageUploadUrl: '${pageContext.request.contextPath}/ckUploadImage', // 파일 업로드를 처리 할 경로 설정(CK필터).
	};
	CKEDITOR.replace('nttCn',ckeditor_config);
	
	// 첫 입력란에 포커스
	document.getElementById("articleVO").nttSj.focus();
	

	}
	/* ********************************************************
	 * 저장처리화면
	 ******************************************************** */
	function fn_egov_regist_article(form) {
		CKEDITOR.instances.nttCn.updateElement();
		//input item Client-Side validate
		if (!validateArticleVO(form)) {
			return false;
		} else {
			var validateForm = document.getElementById("articleVO");

			//비밀글은 제목 진하게 할 수 없음.
			//비밀글은 익명게시 불가.
			//비밀글은 공지게시 불가.
			if (validateForm.secretAt.checked) {
				if (validateForm.sjBoldAt.checked) {
					alert("<spring:message code="comCopBbs.articleVO.secretBold" />");
					return;
				}
				if (validateForm.anonymousAt.checked) {
					alert("<spring:message code="comCopBbs.articleVO.secretAnonymous" />");
					return;
				}
				if (validateForm.noticeAt.checked) {
					alert("<spring:message code="comCopBbs.articleVO.secretNotice" />");
					return;
				}
			}

			//익명글은 공지게시 불가.
			if (validateForm.anonymousAt.checked) {
				if (validateForm.noticeAt.checked) {
					alert("<spring:message code="comCopBbs.articleVO.anonymousNotice" />");
					return;
				}
			}
			
			if (confirm("<spring:message code="common.regist.msg" />")) {
				form.submit();
			}

			/*게시기간 
			var ntceBgnde = getRemoveFormat(validateForm.ntceBgnde.value);
			var ntceEndde = getRemoveFormat(validateForm.ntceEndde.value);

			if (ntceBgnde == '' && ntceEndde != '') {
				validateForm.ntceBgnde.value = '1900-01-01';
			}
			if (ntceBgnde != '' && ntceEndde == '') {
				validateForm.ntceEndde.value = '9999-12-31';
			}
			if (ntceBgnde == '' && ntceEndde == '') {
				validateForm.ntceBgnde.value = '1900-01-01';
				validateForm.ntceEndde.value = '9999-12-31';
			}

			ntceBgnde = getRemoveFormat(validateForm.ntceBgnde.value);
			ntceEndde = getRemoveFormat(validateForm.ntceEndde.value);

			if (ntceBgnde > ntceEndde) {
				alert("<spring:message code="comCopBbs.articleVO.ntceDeError" />");
				return;
			}
			*/			
		}
	}
</script>

<!-- 콘텐츠 시작 -->
<div class="container">
	<%@include file="/WEB-INF/jsp/egovframework/com/web/subheader.jsp" %>
	<div class="sub-con">
	    <div class="title-wrap">갤러리</div>
	</div>
	
	<!-- javascript warning tag  -->
	<noscript class="noScriptTitle"><spring:message code="common.noScriptTitle.msg" /></noscript>
	<section id="bo_w">
		<form:form commandName="articleVO" action="${pageContext.request.contextPath}/cop/bbs/insertArticle.do" method="post" onSubmit="fn_egov_regist_article(document.forms[0]); return false;" enctype="multipart/form-data"> 
		<div class="wTableFrm">	
			<!-- 등록폼 -->
			<table class="wTable" summary="<spring:message code="common.summary.list" arguments="${pageTitle}" />">		
				<colgroup>
					<col style="width: 20%;">
					<col style="width: ;">
					<col style="width: ;">
					<col style="width: ;">
				</colgroup>
				<tbody>
					<!-- 비회원 글쓰기일 경우 -->
					<c:if test="${loginVO == null}">
					<tr>
						<th><label for="frstRegisterNm">이름 <span class="pilsu">*</span></label></th>
						<td>
						    <form:input path="frstRegisterNm" title="이름" size="50" maxlength="50" />
			   				<div><form:errors path="frstRegisterNm" cssClass="error" /></div>     
			   			</td>
			   			<th><label for="frstRegisterId">비밀번호 <span class="pilsu">*</span></label></th>
						<td>
						    <form:input path="frstRegisterId" title="비밀번호" size="50" maxlength="50" />
			   				<div><form:errors path="frstRegisterId" cssClass="error" /></div>     
						</td>
					</tr>
					</c:if>
					<!-- 입력 -->
					<c:set var="inputTxt"><spring:message code="input.input" /></c:set>
					<!-- 글 제목, 제목 Bold여부   -->
					<c:set var="title"><spring:message code="comCopBbs.articleVO.regist.nttSj"/> </c:set>
					<tr>
						<th><label for="nttSj">${title} <span class="pilsu">*</span></label></th>
						<td class="left" colspan="3">
						    <form:input path="nttSj" title="${title} ${inputTxt}" size="100" maxlength="100" />
			   				<div><form:errors path="nttSj" cssClass="error" /></div>     
						</td>
						<!--<c:set var="title"><spring:message code="comCopBbs.articleVO.regist.sjBoldAt"/> </c:set>
						<th><label for="sjBoldAt">${title}</label></th>
						<td class="left">
						    <form:checkbox path="sjBoldAt" value="Y"/>
			   				<div><form:errors path="sjBoldAt" cssClass="error" /></div>     
						</td>-->
					</tr>
					<!-- 글 내용  -->
					<c:set var="title"><spring:message code="comCopBbs.articleVO.regist.nttCn"/> </c:set>
					<tr>
						<th><label for="nttCn">${title } <span class="pilsu">*</span></label></th>
						<td class="nopd" colspan="3">
							<form:textarea path="nttCn" title="${title} ${inputTxt}" cols="160" rows="20" />   
			
							<div><form:errors path="nttCn" cssClass="error" /></div>  
						</td>
					</tr>
					
					<!-- 공지신청 여부  
					<c:if test="${loginVO.userSe == 'USR'}">	
					<c:set var="title"><spring:message code="comCopBbs.articleVO.regist.noticeAt"/> </c:set>
					<tr>
						<th><label for="noticeAt">${title}</label></th>
						<td class="left" colspan="3">
							<form:checkbox path="noticeAt" value="Y"/>
							<div><form:errors path="noticeAt" cssClass="error" /></div>       
						</td>
					</tr>					
					</c:if>-->
					
					<!-- 비밀글 여부 -->
					<c:set var="title"><spring:message code="comCopBbs.articleVO.regist.secretAt"/> </c:set>
					<tr>
						<th><label for="secretAt">${title}</label></th>
						<td class="left" colspan="3">
							<form:checkbox path="secretAt" value="Y"/>
							<div><form:errors path="secretAt" cssClass="error" /></div>       
						</td>
					</tr>
					
					<!-- 익명등록 여부  -->
					<c:set var="title"><spring:message code="comCopBbs.articleVO.regist.anonymousAt"/> </c:set>
					<tr>
						<th><label for="anonymousAt">${title}</label></th>
						<td class="left" colspan="3">
							<form:checkbox path="anonymousAt" value="Y"/>
							<div><form:errors path="anonymousAt" cssClass="error" /></div>       
						</td>
					</tr>
					
					<!-- 유효기간 설정  
					<c:set var="title"><spring:message code="comCopBbs.articleVO.regist.ntceDe"/> </c:set>
					<tr>
						<th><label for="ntceBgnde">${title} </label></th>
						<td class="left" colspan="3">
							<form:input path="ntceBgnde" title="${title} ${inputTxt}" size="70" maxlength="70" style="width:70px; margin-right:5px;" readonly="true" />
							&nbsp;~&nbsp;<form:input path="ntceEndde" title="${title} ${inputTxt}" size="70" maxlength="70" style="width:70px; margin-right:5px;" readonly="true" />
							<div><form:errors path="ntceBgnde" cssClass="error" /></div>       
							<div><form:errors path="ntceEndde" cssClass="error" /></div>       
						</td>
					</tr>-->
					
					<c:if test="${boardMasterVO.fileAtchPosblAt == 'Y'}">
					<!-- 첨부파일  -->
					<c:set var="title"><spring:message code="comCopBbs.articleVO.regist.atchFile"/></c:set>
					<tr>
						<th><label for="file_1">${title}</label> </th>
						<td class="nopd" colspan="3">
							<input name="file_1" id="egovComFileUploader" type="file" title="<spring:message code="comCopBbs.articleVO.regist.atchFile"/>" multiple/><!-- 첨부파일 -->
						    <div id="egovComFileList"></div>
						</td>
					</tr>
				  	</c:if>
			
				</tbody>
			</table>
		
			<!-- 하단 버튼 -->
			 <div class="btn_confirm write_div">
		        <a href="/board/list.do?bbsId=${boardMasterVO.bbsId}&menuNo=${param.menuNo}" class="btn_cancel btn">취소</a>
		        <button type="submit" id="btn_submit" accesskey="s" class="btn_submit btn">작성완료</button>
		    </div>	
		</div>
	
		<input type="hidden" name="pageIndex"  value="<c:out value='${searchVO.pageIndex}'/>"/>
		<input type="hidden" name="bbsTyCode" value="<c:out value='${boardMasterVO.bbsTyCode}'/>" />
		<input type="hidden" name="replyPosblAt" value="<c:out value='${boardMasterVO.replyPosblAt}'/>" />
		<input type="hidden" name="fileAtchPosblAt" value="<c:out value='${boardMasterVO.fileAtchPosblAt}'/>" />
		<input type="hidden" id="atchPosblFileNumber" name="atchPosblFileNumber" value="<c:out value='${boardMasterVO.atchPosblFileNumber}'/>" />
		<input type="hidden" name="atchPosblFileSize" value="<c:out value='${boardMasterVO.atchPosblFileSize}'/>" />
		<input type="hidden" name="tmplatId" value="<c:out value='${boardMasterVO.tmplatId}'/>" />
		<input type="hidden" name="blogId" value="<c:out value='${searchVO.blogId}'/>" />
		<input type="hidden" name="blogAt" value="<c:out value='${articleVO.blogAt}'/>"/>
		<input type="hidden" name="cmd" value="<c:out value='save'/>">
		<input type="hidden" name="bbsId" value="<c:out value='${articleVO.bbsId}'/>">
		<input type="hidden" name="menuNo" value="<c:out value='${param.menuNo}'/>">
		</form:form>
	</section>
</div>
<!-- 콘텐츠 끝 -->

<!-- 첨부파일 업로드 가능화일 설정 Start..-->  
<script type="text/javascript">
var maxFileNum = document.getElementById('atchPosblFileNumber').value;
if(maxFileNum==null || maxFileNum==""){
	maxFileNum = 3;
}
var multi_selector = new MultiSelector( document.getElementById( 'egovComFileList' ), maxFileNum );
multi_selector.addElement( document.getElementById( 'egovComFileUploader' ) );
</script> 
<!-- 첨부파일 업로드 가능화일 설정 End.-->
<%@include file="/WEB-INF/jsp/egovframework/com/web/footer.jsp" %>