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
	
	var ckeditor_config = {
		filebrowserImageUploadUrl: '${pageContext.request.contextPath}/utl/wed/insertImageCk.do', // 파일 업로드를 처리 할 경로 설정.
	};
	CKEDITOR.replace('nttCn',ckeditor_config);
	
	// 첫 입력란에 포커스
	document.getElementById("articleVO").nttSj.focus();
	

	}
	/* ********************************************************
	 * 저장처리화면
	 ******************************************************** */
	function fn_egov_updt_article(form) {
		CKEDITOR.instances.nttCn.updateElement();
	
		if (!validateArticleVO(form)) {
			return false;
		} else {
			var validateForm = document.getElementById("articleVO");

			//비밀글은 제목 진하게 할 수 없음.
			//비밀글은 공지게시 불가.
			if (validateForm.secretAt.checked) {
				if (validateForm.sjBoldAt.checked) {
					alert("<spring:message code="comCopBbs.articleVO.secretBold" />");
					return;
				}
				if (validateForm.noticeAt.checked) {
					alert("<spring:message code="comCopBbs.articleVO.secretNotice" />");
					return;
				}
			}

			if (confirm("<spring:message code="common.update.msg" />")) {
				var result = check();
				
				if(result=="empty"){
					alert("보안숫자를 입력해 주세요.");
					answer.focus();
					return;
				}else if(result=="correct"){
					form.submit();
				}else{
					alert('보안숫자값이 일치하지 않습니다.'); 
					getImage(); 
					answer.focus();
					return;
				}
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
			}*/	
		}
	}
	
	/*스팸 방지 관련*/
	function check(){
		getImage(); // 이미지 가져오기
		var answer = document.getElementById('answer');
		var result;

		if(answer.value==""){
			result="empty";
		}else{
			$.ajax({
				url:"/chkAnswer.do",
				type:"POST",
				dataType:"text",
				data:{"answer" : answer.value},
				async:false,	//ajax는 기본적으로 비동기 통신이라 값을 전달하지 못한다. 그래서 동기식으로 전환하기위해 추가
				success:function(returnData){
					if(returnData == 200){	//보안값이 일치할 때
						result="correct";
					}else{ 
						result="wrong";
					} 
				}
			})	
		}

		return result;
	}

	/*매번 랜덤값을 파라미터로 전달하는 이유 : IE의 경우 매번 다른 임의 값을 전달하지 않으면 '새로고침' 클릭해도 정상 호출되지 않아 이미지가 변경되지 않는 문제가 발생된다*/ 
	function audio(){ 
		var rand = Math.random(); 
		var uAgent = navigator.userAgent; 
		var soundUrl = '${ctx}/captchaAudio.do?rand='+rand; 
		if(uAgent.indexOf('Trident')>-1 || uAgent.indexOf('MISE')>-1){ /*IE 경우 */ 
			audioPlayer(soundUrl); 
		}else if(!!document.createElement('audio').canPlayType){ /*Chrome 경우 */ 
			try { 
				new Audio(soundUrl).play(); 
			} catch (e) { 
				audioPlayer(soundUrl); 
			} 
		}else{ 
			window.open(soundUrl,'','width=1,height=1'); 
		} 
	} 

	function getImage(){ 
		var rand = Math.random(); 
		var url = '${ctx}/captchaImg.do?rand='+rand;
		var captchaImg = document.getElementById('captchaImg'); 
		
		captchaImg.setAttribute('src', url); 
	} 

	function audioPlayer(objUrl){ 
		var ccautio = document.getElementById('ccaudio');
		
		ccautio.innerHTML = '<bgsoun src="' +objUrl +'">'; 
	}
</script>

<!-- 콘텐츠 시작 -->
<div class="container">
	<%@include file="/WEB-INF/jsp/egovframework/com/web/subheader.jsp" %>
	<div class="sub-con">
	    <div class="title-wrap"><c:out value="${menuNm}"/></div>
	</div>
	
	<!-- javascript warning tag  -->
	<noscript class="noScriptTitle"><spring:message code="common.noScriptTitle.msg" /></noscript>
	<section id="bo_w">
		<form:form commandName="articleVO" action="${pageContext.request.contextPath}/cop/bbs/updateArticle.do" method="post" onSubmit="fn_egov_regist_article(document.forms[0]); return false;" enctype="multipart/form-data"> 
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
			   			<th><label for="password">비밀번호 <span class="pilsu">*</span></label></th>
						<td>
						    <form:input path="password" title="비밀번호" size="50" maxlength="50" readonly="true" />
			   				<div><form:errors path="password" cssClass="error" /></div>     
						</td>
					</tr>
					</c:if>
					<!-- 입력 -->
					<c:set var="inputTxt"><spring:message code="input.input" /></c:set>
					<!-- 글 제목, 제목 Bold여부   -->
					<c:set var="title"><spring:message code="comCopBbs.articleVO.updt.nttSj"/> </c:set>
					<tr>
						<th><label for="nttSj">${title} <span class="pilsu">*</span></label></th>
						<td class="left" colspan="3">
						    <form:input path="nttSj" title="${title} ${inputTxt}" size="100" maxlength="100" />
			   				<div><form:errors path="nttSj" cssClass="error" /></div>     
						</td>						
					</tr>
					<!-- 글 내용  -->
					<c:set var="title"><spring:message code="comCopBbs.articleVO.updt.nttCn"/> </c:set>
					<tr>
						<th><label for="nttCn">${title } <span class="pilsu">*</span></label></th>
						<td class="nopd" colspan="3">
							<form:textarea path="nttCn" title="${title} ${inputTxt}" cols="160" rows="20" />   
			
							<div><form:errors path="nttCn" cssClass="error" /></div>  
						</td>
					</tr>
										
					<!-- 비밀글 여부 -->
					<c:set var="title"><spring:message code="comCopBbs.articleVO.updt.secretAt"/> </c:set>
					<tr>
						<th><label for="secretAt">${title}</label></th>
						<td class="left" colspan="3">
							<form:checkbox path="secretAt" value="Y"/>
							<div><form:errors path="secretAt" cssClass="error" /></div>       
						</td>
					</tr>
				
					<c:if test="${boardMasterVO.fileAtchPosblAt == 'Y'}">
					<!-- 기존 첨부파일  -->
					<c:set var="title"><spring:message code="comCopBbs.articleVO.updt.atchFile"/></c:set>
					<tr>
						<th>${title}</th>
						<td class="nopd" colspan="3">
							<c:import url="/cmm/fms/selectFileInfsForUpdate.do" charEncoding="utf-8">
								<c:param name="param_atchFileId" value="${articleVO.atchFileId}" />
							</c:import>			
						</td>
					</tr>
					<!-- 첨부파일 끝 -->
					<!-- 첨부파일 추가 시작 -->
					<c:set var="title"><spring:message code="comCopBbs.articleVO.updt.atchFileAdd"/></c:set>
					<tr>
						<th><label for="file_1">${title}</label> </th>
						<td class="nopd" colspan="3">
							<input name="file_1" id="egovComFileUploader" type="file" title="<spring:message code="comCopBbs.articleVO.updt.atchFile"/>" multiple/><!-- 첨부파일 -->
							<div id="egovComFileList"></div>
						</td>
					</tr>
					<!-- 첨부파일 추가 끝 -->
				  	</c:if>
			
					<!-- 보안코드, 비회원일 때만 -->
				  	<c:if test="${loginVO == null}">
				  	<tr>
						<th><label for="captcha" style="display:block">보안숫자입력</label> </th>
						<td class="nopd" colspan="3">
							<div style="overflow:hidden"> 
								<div style="float:left"> 
									<img id="captchaImg" title="캡차이미지" src="/captchaImg.do" alt="캡차이미지"/> 
									<div id="ccaudio" style="display:none"></div> 
								</div> 			
							
								<div style="padding:3px 0 0 155px"> 
									<input id="reload" type="button" onclick="javaScript:getImage()" value="새로고침" /> 
									<input id="soundOn" type="button" onclick="javaScript:audio()" value="음성듣기"/> 
								</div> 
								
								<div style="padding:3px 0 0 155px"> 
									<input id="answer" type="text" size="10" style="width:14%;"> 
								</div>
							</div> 
						</td>
					</tr>	
					</c:if>
				</tbody>
			</table>
		
			<!-- 하단 버튼 -->
			 <div class="btn_confirm write_div">
		        <a href="/board/list.do?bbsId=${boardMasterVO.bbsId}&menuNo=${param.menuNo}" class="btn_cancel btn">취소</a>
		        <button type="submit" id="btn_submit" accesskey="s" class="btn_submit btn">수정완료</button>
		    </div>	
		</div>
	
		<input type="hidden" name="pageIndex"  value="<c:out value='${searchVO.pageIndex}'/>"/>
		<input type="hidden" name="bbsTyCode" value="<c:out value='${boardMasterVO.bbsTyCode}'/>" />
		<input type="hidden" name="replyPosblAt" value="<c:out value='${boardMasterVO.replyPosblAt}'/>" />
		<input type="hidden" name="fileAtchPosblAt" value="<c:out value='${boardMasterVO.fileAtchPosblAt}'/>" />
		<input type="hidden" id="atchPosblFileNumber" name="atchPosblFileNumber" value="<c:out value='${boardMasterVO.atchPosblFileNumber}'/>" />
		<input type="hidden" name="atchPosblFileSize" value="<c:out value='${boardMasterVO.atchPosblFileSize}'/>" />
		<input type="hidden" name="tmplatId" value="<c:out value='${boardMasterVO.tmplatId}'/>" />
		<input type="hidden" name="bbsId" value="<c:out value='${articleVO.bbsId}'/>">
		<input type="hidden" name="nttId" value="${articleVO.nttId}">
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