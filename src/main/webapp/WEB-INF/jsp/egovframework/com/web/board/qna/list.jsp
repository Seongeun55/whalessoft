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
	document.qnaForm.pageIndex.value = pageNo;
   	document.qnaForm.submit();
}

//[추가] 검색 후 다시 목록으로 돌아올 때 조건을 넘겨주기위해 추가 - 2021.05.04
function viewClick(qaId, pageNo) {
	var searchWrd=document.getElementsByName("searchWrd")[0].value;
	var searchCnd=$("select[name=searchCnd]").val();
	location.href="/qna/view.do?qaId="+qaId+"&pageIndex="+pageNo+"&searchWrd="+searchWrd+"&searchCnd="+searchCnd;
}
</script>

<!-- 콘텐츠 시작 { -->
<div class="container">
	<%@include file="/WEB-INF/jsp/egovframework/com/web/subheader.jsp" %>
        
	<div class="sub-con">
    	<div class="title-wrap">Q&A</div>
	</div>
	
	<!-- 게시판  시작 { -->
	<div id="bo_list">   
		<form name="qnaForm" id="fboardlist" action="/qna/list.do" onsubmit="return searchQna(this);" method="get">
		    <!-- 게시판 조회 및 버튼 시작 { -->
		    <div class="search_box">
			<ul>
				<li>
					<select name="searchCnd" title="<spring:message code="title.searchCondition" /> <spring:message code="input.cSelect" />">
						<option value="0"  <c:if test="${searchVO.searchCnd == '0'}">selected="selected"</c:if> ><spring:message code="table.reger" /></option><!-- 작성자 -->
						<option value="1"  <c:if test="${searchVO.searchCnd == '1'}">selected="selected"</c:if> ><spring:message code="comUssOlhQna.qnaVO.qestnSj" /></option><!-- 질문제목 -->
					</select>
				</li>
				<!-- 조회 및 등록버튼 -->
				<li>
					<input class="s_input" name="searchWrd" type="text"  size="35" title="<spring:message code="title.search" /> <spring:message code="input.input" />" value='<c:out value="${searchVO.searchWrd}"/>'  maxlength="155" >
					<input type="submit" class="s_btn" value="<spring:message code="button.inquire" />" title="<spring:message code="title.inquire" /> <spring:message code="input.button" />" />
					<span class="btn_b"><a href="/qna/write.do"  title="<spring:message code="button.create" /> <spring:message code="input.button" />"><spring:message code="button.create" /></a></span>
				</li>
			</ul>
			</div>
		</form>
    	<!-- } 게시판 조회 및 버튼 끝 -->
        
        <!-- 게시판 목록 시작 -->	
	    <div class="tbl_head01 tbl_wrap">
	        <table>
		        <caption>질문 목록</caption>
		        <colgroup>
					<col style="width: 5%;">
					<col style="width: 40%;">
					<col style="width: 12%;">
					<col style="width: 9%;">
					<col style="width: 13%;">
				</colgroup>
		        <thead>
		        <tr>
		            <th scope="col">번호</th>
		            <th scope="col">제목</th>
		            <th scope="col">작성자</th>
		            <th scope="col">진행상태</th>
		            <th scope="col">등록일</th>            
		        </tr>
		        </thead>
	        
		        <c:forEach items="${resultList}" var="resultInfo" varStatus="status">
		        <c:set var="even" value=""/>
		        <c:if test="${(resultCnt-(searchVO.pageIndex-1) * searchVO.pageSize - status.index) %2==0}"><c:set var="even" value="even"/></c:if>
		        	<tbody>	        			        	
		        		<tr class="<c:out value="${even}"/>">
		        			<td align="center"><c:out value="${resultCnt-(searchVO.pageIndex-1) * searchVO.pageSize - status.index}"/></td>
		        			<td class="td_subject" style="padding-left:10px">
		        				<div>
		        					<a href="javascript:viewClick('${resultInfo.qaId}', '${searchVO.pageIndex}');">
		        						<c:out value='${fn:substring(resultInfo.qestnSj, 0, 40)}'/>
		        					</a>		        					
		        				</div>
		        			</td>
		        			<td class="td_num"><c:out value='${resultInfo.wrterNm}'/></td>
		        			<td class="td_datetime"><c:out value='${resultInfo.qnaProcessSttusCodeNm}'/></td>
		        			<td class="td_num"><c:out value='${resultInfo.frstRegisterPnttm}'/></td>
		        		</tr>	        
		        	</tbody>
		        </c:forEach>
	        </table>
	    </div>
	    <!-- 게시판 목록 끝 -->
		<!-- paging navigation -->
		<div class="pagination">
			<ul>
			<ui:pagination paginationInfo="${paginationInfo}" type="image" jsFunction="linkPage"/>
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