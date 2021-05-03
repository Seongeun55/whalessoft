<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>    

<%@include file="/WEB-INF/jsp/egovframework/com/web/header.jsp" %>
<link rel="stylesheet" type="text/css" href="/css/gallery.css"/>
<script>
function searchArticle(){
	document.articleForm.pageIndex.value = 1;
	document.articleForm.submit();
}

</script>

<!-- 콘텐츠 시작 { -->
<div class="container">
	
	<%@include file="/WEB-INF/jsp/egovframework/com/web/subheader.jsp" %>
    
    <div class="sub-con">
    	<div class="title-wrap">갤러리</div>
	</div>
	
	<!-- 게시판 목록 시작 { -->
	<div id="bo_gall">
        <!-- } 게시판 페이지 정보 및 버튼 끝 -->
		<form name="articleForm" id="fboardlist" action="/board/list.do" onsubmit="return searchArticle();" method="get">
			<!-- 게시판 조회 및 버튼 시작 { -->
   			<div class="search_box">
				<ul>
					<li>
						<select name="searchCnd" title="<spring:message code="title.searchCondition" /> <spring:message code="input.cSelect" />">
						<option value="0"  <c:if test="${searchVO.searchCnd == '0'}">selected="selected"</c:if> ><spring:message code="comCopBbs.articleVO.list.nttSj" /></option><!-- 글 제목  -->
						<option value="1"  <c:if test="${searchVO.searchCnd == '1'}">selected="selected"</c:if> ><spring:message code="comCopBbs.articleVO.list.nttCn" /></option><!-- 글 내용 -->
						<option value="2"  <c:if test="${searchVO.searchCnd == '2'}">selected="selected"</c:if> ><spring:message code="table.reger" /></option><!-- 작성자 -->
						</select>
					</li>
					
					<!-- 조회 및 등록버튼 -->
					<li>
						<input class="s_input" name="searchWrd" type="text"  size="35" title="<spring:message code="title.search" /> <spring:message code="input.input" />" value='<c:out value="${searchVO.searchWrd}"/>'  maxlength="155" >
						<input type="submit" class="s_btn" value="<spring:message code="button.inquire" />" title="<spring:message code="title.inquire" /> <spring:message code="input.button" />" />					
						<c:if test="${user.userSe=='USR' }">
							<span class="btn_b"><a href="<c:url value='/board/write.do?bbsId=${param.bbsId}' />"  title="<spring:message code="button.create" /> <spring:message code="input.button" />"><spring:message code="button.create" /></a></span><!-- 등록 -->
						</c:if>
					</li>
				</ul>
			</div>
		<input name="bbsId" type="hidden" value="${boardMasterVO.bbsId}">
		<input name="pageIndex" type="hidden" value="">
		</form>
		
		<!-- 게시판 페이지 정보 및 버튼 시작 { -->
        <div id="bo_btn_top">
            <div id="bo_list_total">
                <span>Total ${resultCnt }건</span>
                ${searchVO.pageIndex } 페이지
            </div>
        </div>
        
        <ul id="gall_ul" class="gall_row">
        <c:forEach items="${resultList}" var="result" varStatus="status">
        	<li class="gall_li col-gn-3">
        	<form name="mainForm" action="/board/view.do" method="get">
				<input name="nttId" id="nttId" type="hidden" value="<c:out value="${result.nttId}"/>">
			    <input name="bbsId" id="bbsId" type="hidden" value="<c:out value="${result.bbsId}"/>">
			    <input name="pageIndex" id="pageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>"/>
        		<div class="gall_box">
        			<div class="gall_chk chk_box">
                        <span class="sound_only">
                            <c:out value="${(searchVO.pageIndex-1) * searchVO.pageSize + status.count}"/> 
                        </span>
                    </div>
                    <div class="gall_con">
                    	<div class="gall_img">
                            <button type="submit">
                            	<img src="<c:url value='/cmm/fms/getImage.do'/>?atchFileId=<c:out value='${result.atchFileId}'/>" alt="">
                            </button>	                                	                            
                        </div>
                        <div class="gall_text_href">	                            	                                                  
                               <c:out value='${fn:substring(result.nttSj, 0, 40)}'/>
                               <input type="submit" class="badge" value="자세히 보기">
                        </div>
                    </div>
        		</div>
        	</form>
        	</li>
        </c:forEach>            
        </ul>
	</div>
	<!-- } 게시판 목록 끝 -->
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