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
<script type="text/javascript" src="<c:url value="/validator.do"/>"></script>
<validator:javascript formName="articleCommentVO" staticJavascript="false" xhtml="true" cdata="false"/>
<script type="text/javascript">

//댓글 삭제
function fn_egov_deleteCommentList(commentNo) {
	
	var form = document.getElementById("articleCommentVO");
	var loginVo = "<c:out value='${loginVO}'/>";
	var bbsId = "<c:out value='${param.bbsId}'/>";
	if(loginVo==null || loginVo==""){	//로그인이 안되어있을 때 새창에 비밀번호 확인 띄우기 위해서
		window.open("<c:url value='/cop/cmt/deleteArticleCommentPre.do?bbsId="+bbsId+"&commnetNo="+commentNo+"'/>", "", "width=500, height=230");
	}else{
		if (confirm('<spring:message code="common.delete.msg" />')) {
			form.modified.value = "true";
			form.commentNo.value = commentNo;
			form.action = "<c:url value='/cop/cmt/deleteArticleComment.do'/>";
			form.submit();
		}
	}
}

//댓글 등록
function fn_egov_insert_commentList() {
	var form = document.getElementById("articleCommentVO");

	if (!validateArticleCommentVO(form)){
		return;
	}
	if (confirm('<spring:message code="common.regist.msg" />')) {
		form.submit();
	}
}

//댓글 수정 전처리
function fn_egov_selectCommentForupdt(commentNo) {
	
	var form = document.getElementById("articleCommentVO");
	var commentInsert = document.getElementById("commentInsert");
	var commentUpdate = document.getElementById("commentUpdate");	
	var _commentCn = document.getElementById("_commentCn"+commentNo);
	var loginVo = "<c:out value='${loginVO}'/>";
	
	form.commentNo.value = commentNo;	
	form.commentCn.value = _commentCn.value;	
	
	if(loginVo==null || loginVo==""){
		var _wrterNm = document.getElementById("_wrterNm"+commentNo);
		form.wrterNm.value = _wrterNm.value;
	}	
	commentInsert.style.display = "none";
	commentUpdate.style.display = "";
}

//댓글 수정
function fn_egov_updt_commentList() {
	
	var form = document.getElementById("articleCommentVO");
	
	if (!validateArticleCommentVO(form)){
		return;
	}

	if (confirm('<spring:message code="common.update.msg" />')) {
		form.modified.value = "true";
		form.action = "<c:url value='/cop/cmt/updateArticleComment.do'/>";
		form.submit();
	}
}

/* 댓글페이징 */
function fn_egov_select_commentList(pageNo) {
	
	var form = document.getElementById("articleCommentVO");
	
	form.subPageIndex.value = pageNo;
	form.commentNo.value = '';
	form.action = "<c:url value='/board/view.do'/>";
	form.submit();
}

function guestPasswordCheck(menuNo, state){
	var loginVo = "<c:out value='${loginVO}'/>";
	var parnts = "<c:out value='${result.parnts}'/>";
	var sortOrdr = "<c:out value='${result.sortOrdr}'/>";
	var replyLc = "<c:out value='${result.replyLc}'/>";
	var nttSj = "<c:out value='${result.nttSj}'/>";
	var nttId = "<c:out value='${result.nttId}'/>";
	var bbsId = "<c:out value='${boardMasterVO.bbsId}'/>";

	if(state=="update"){	// 수정 눌렀을 때
		if(loginVo==null || loginVo==""){	//로그인이 안되어있을 때 새창에 비밀번호 확인 띄우기 위해서
			window.open("<c:url value='/cop/bbs/guestArticlePre.do?bbsId="+bbsId+"&nttId="+nttId+"&state="+state+"&menuNo="+menuNo+"'/>", "", "width=500, height=230");
		}else{
			location.href="/board/modify.do?parnts="+parnts+"&sortOrdr="+sortOrdr+"&replyLc="+replyLc+"&nttSj="+nttSj+"&nttId="+nttId+"&bbsId="+bbsId+"&menuNo="+menuNo;
		}
	}else{		// 삭제 눌렀을 때
		if(confirm("<spring:message code="common.delete.msg" />")){	
			if(loginVo==null || loginVo==""){	//로그인이 안되어있을 때 새창에 비밀번호 확인 띄우기 위해서
				window.open("<c:url value='/cop/bbs/guestArticlePre.do?bbsId="+bbsId+"&nttId="+nttId+"&state="+state+"&menuNo="+menuNo+"'/>", "", "width=500, height=230");
			}else{
				location.href="/board/delete.do?nttId="+nttId+"&bbsId="+bbsId+"&menuNo="+menuNo;
			}	
		}		
	}
	
}
</script>
<!-- 콘텐츠 시작 { -->
<div class="container">
	<%@include file="/WEB-INF/jsp/egovframework/com/web/subheader.jsp" %>
        
	<div class="sub-con">
    	<div class="title-wrap"><c:out value="${menuNm}"/></div>
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
        	<img src="<c:url value='/cmm/fms/getImage.do'/>?atchFileId=<c:out value='${result.atchFileId}'/>" alt="">
        	<br /><br /><br />
        	${fn:replace(result.nttCn , crlf , '<br/>')}
		</div>		
        <!-- } 본문 내용 끝 -->
        
        <!-- 하단 버튼 -->
		<div class="btn" style="float:right">
			<c:if test="${result.ntcrId == sessionUniqId || loginVO==null || loginVO.userSe == 'USR'}">		
				<a href="javascript:guestPasswordCheck('${param.menuNo}', 'update');" style="display:block; line-height:21px; float:left; margin:0 0 0 3px;" class="s_submit">
					<spring:message code="button.update" />
				</a>
				<a href="javascript:guestPasswordCheck('${param.menuNo}', 'delete');" style="display:block; line-height:21px; float:left; margin:0 0 0 3px;" class="s_submit">
					<spring:message code="button.delete" />
				</a>
			</c:if>
			<a href="/board/list.do?bbsId=${param.bbsId}&pageIndex=${param.pageIndex}&searchWrd=${param.searchWrd}&searchCnd=${param.searchCnd}&menuNo=${param.menuNo}" style="display:block; line-height:21px; float:left; margin:0 0 0 3px;" class="s_submit">
				<spring:message code="button.list" />
			</a>	
		</div>
		<div style="clear:both;"></div>
	
		<!-- 댓글 -->
		<c:if test="${useComment == 'true'}">
			<c:import url="/cop/cmt/selectArticleCommentList.do" charEncoding="utf-8" />	
		</c:if>
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