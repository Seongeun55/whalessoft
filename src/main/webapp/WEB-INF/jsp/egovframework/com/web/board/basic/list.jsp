<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>    

<%@include file="/WEB-INF/jsp/egovframework/com/web/header.jsp" %>
<script type="text/javascript">
/*********************************************************
 * 조회 처리 함수
 ******************************************************** */
function searchQna(form){
	form.searchWrd.value = form.searchWrd.value.trim();
	if(form.searchWrd.value == "") {
		alert("검색어를 입력해주세요.");
		form.searchWrd.focus();
		return false;
	}
}
/*********************************************************
 * 페이징 처리 함수
 ******************************************************** */
function linkPage(pageNo){
	document.pageForm.pageIndex.value = pageNo;
   	document.pageForm.submit();
}
</script>
<form name="pageForm">
	<!-- input type="hidden" name="type" value="<c:out value='${param.type}'/>" -->
	<input type="hidden" name="pageIndex" value="<c:out value='${searchVO.pageIndex}'/>">
</form>
<!-- 콘텐츠 시작 { -->
<div class="container">
	<%@include file="/WEB-INF/jsp/egovframework/com/web/subheader.jsp" %>
        
	<div class="sub-con">
    	<div class="title-wrap">공지사항</div>
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