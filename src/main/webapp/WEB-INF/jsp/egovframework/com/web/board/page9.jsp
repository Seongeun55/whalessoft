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
function fn_egov_search_qna(){
	document.qnaForm.pageIndex.value = 1;
	document.qnaForm.submit();
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
	
	<!-- 게시판 목록 시작 { -->
	<div id="bo_list">   
		<form name="qnaForm" id="fboardlist" action="<c:url value='/board.do?id=page9'/>" onsubmit="fn_egov_search_qna(); return false;" method="post">
		    <!-- 게시판 페이지 정보 및 버튼 시작 { -->
		    <div class="search_box">
			<ul>
				<li>
					<select name="searchCnd" title="<spring:message code="title.searchCondition" /> <spring:message code="input.cSelect" />">
						<option value="0"  <c:if test="${searchVO.searchCnd == '0'}">selected="selected"</c:if> ><spring:message code="table.reger" /></option><!-- 작성자 -->
						<option value="1"  <c:if test="${searchVO.searchCnd == '1'}">selected="selected"</c:if> ><spring:message code="comUssOlhQna.qnaVO.qestnSj" /></option><!-- 질문제목 -->
					</select>
				</li>
				<!-- 검색키워드 및 조회버튼 -->
				<li>
					<input class="s_input" name="searchWrd" type="text"  size="35" title="<spring:message code="title.search" /> <spring:message code="input.input" />" value='<c:out value="${searchVO.searchWrd}"/>'  maxlength="155" >
					<input type="submit" class="s_btn" value="<spring:message code="button.inquire" />" title="<spring:message code="title.inquire" /> <spring:message code="input.button" />" />
					<span class="btn_b"><a href="<c:url value='/uss/olh/qna/insertQnaView.do' />"  title="<spring:message code="button.create" /> <spring:message code="input.button" />"><spring:message code="button.create" /></a></span>
				</li>
			</ul>
			</div>
		</form>
    	<!-- } 게시판 페이지 정보 및 버튼 끝 -->
        	
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
		        	<tbody>
		        		<tr class=" even">
		        			<td align="center"><c:out value="${(searchVO.pageIndex-1) * searchVO.pageSize + status.count}"/></td>
		        			<td class="td_subject" style="padding-left:10px">
		        				<div class="bo_tit">
		        					<form name="subForm" method="post" action="<c:url value='/uss/olh/qna/selectQnaDetail.do'/>">
									    <input name="qaId" type="hidden" value="<c:out value="${resultInfo.qaId}"/>">
									    <input name="pageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>"/>								
									    <span class="link"><input type="submit" value="<c:out value='${fn:substring(resultInfo.qestnSj, 0, 40)}'/>" style="border:0px solid #e0e0e0; background:rgba(0,0,0,0);"></span>
									</form>
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
   		

	    <!-- 게시판 검색 시작 { -->
	    <div class="bo_sch_wrap">
	        <fieldset class="bo_sch">
	            <h3>검색</h3>
	            <form name="fsearch" method="get">
	            <input type="hidden" name="bo_table" value="notice">
	            <input type="hidden" name="sca" value="">
	            <input type="hidden" name="sop" value="and">
	            <label for="sfl" class="sound_only">검색대상</label>
	            <select name="sfl" id="sfl">
	                <option value="wr_subject" >제목</option><option value="wr_content" >내용</option><option value="wr_subject||wr_content" >제목+내용</option><option value="wr_name,1" >글쓴이</option><option value="wr_name,0" >글쓴이(코)</option>            </select>
	            <label for="stx" class="sound_only">검색어<strong class="sound_only"> 필수</strong></label>
	            <div class="sch_bar">
	                <input type="text" name="stx" value="" required id="stx" class="sch_input" size="25" maxlength="20" placeholder=" 검색어를 입력해주세요">
	                <button type="submit" value="검색" class="sch_btn"><i class="fa fa-search" aria-hidden="true"></i><span class="sound_only">검색</span></button>
	            </div>
	            <button type="button" class="bo_sch_cls" title="닫기"><i class="fa fa-times" aria-hidden="true"></i><span class="sound_only">닫기</span></button>
	            </form>
	        </fieldset>
	        <div class="bo_sch_bg"></div>
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