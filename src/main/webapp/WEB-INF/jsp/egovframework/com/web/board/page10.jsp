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
function fn_egov_search_faq(){
	document.faqForm.pageIndex.value = 1;
	document.faqForm.submit();
}
/* ********************************************************
 * 상세회면 처리 함수
 ******************************************************** */
function fn_egov_inquire_faqdetail(faqId) {
	// 사이트 키값(siteId) 셋팅.
	document.faqForm.faqId.value = faqId;
  	document.faqForm.action = "<c:url value='/uss/olh/faq/selectFaqDetail.do'/>";
  	document.faqForm.submit();
}
</script>
<!-- 콘텐츠 시작 { -->
<div class="container">
	<%@include file="/WEB-INF/jsp/egovframework/com/web/subheader.jsp" %>
		
	<!-- FAQ 시작 { -->
	<div id="faq_hhtml"><div class="sub-con">
    	<div class="title-wrap">자주묻는질문</div>
	</div></div>	
	
<!-- 게시판 목록 시작 { -->
	<div id="bo_list">   
		<form name="faqForm" action="<c:url value='/board.do?id=page10'/>" onsubmit="fn_egov_search_faq(); return false;" method="post">
		    <!-- 게시판 페이지 정보 및 버튼 시작 { -->
		    <div class="search_box">
			<ul>
				<li>
					<select name="searchCnd" title="<spring:message code="title.searchCondition" /> <spring:message code="input.cSelect" />">
						<option value="0"  <c:if test="${faqVO.searchCnd == '0'}">selected="selected"</c:if> ><spring:message code="comUssOlhFaq.faqVO.qestnSj" /></option><!-- 질문제목 -->
					</select>
				</li>
				<!-- 검색키워드 및 조회버튼 -->
				<li>
					<input class="s_input" name="searchWrd" type="text"  size="35" title="<spring:message code="title.search" /> <spring:message code="input.input" />" value='<c:out value="${searchVO.searchWrd}"/>'  maxlength="155" >
					<input type="submit" class="s_btn" value="<spring:message code="button.inquire" />" title="<spring:message code="title.inquire" /> <spring:message code="input.button" />" />					
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
					<col style="width: 9%;">
					<col style="width: 13%;">
				</colgroup>
		        <thead>
		        <tr>
		            <th scope="col">번호</th>
		            <th scope="col">제목</th>
		            <th scope="col">조회수</th>
		            <th scope="col">등록일</th>            
		        </tr>
		        </thead>
	        
		        <c:forEach items="${resultList}" var="resultInfo" varStatus="status">
		        	<tbody>
		        		<tr class=" even">
		        			<td align="center"><c:out value="${(searchVO.pageIndex-1) * searchVO.pageSize + status.count}"/></td>
		        			<td class="td_subject" style="padding-left:10px">
		        				<div class="bo_tit">
		        					<a href="<c:url value='/uss/olh/faq/selectFaqDetail.do?faqId=${resultInfo.faqId}'/>" onClick="fn_egov_inquire_faqdetail('<c:out value="${resultInfo.faqId}"/>');return false;"><c:out value='${fn:substring(resultInfo.qestnSj, 0, 40)}'/></a></td>
		        				</div>
		        			</td>
		        			<td class="td_datetime"><c:out value='${resultInfo.inqireCo}'/></td>
		        			<td class="td_num"><c:out value='${resultInfo.frstRegisterPnttm}'/></td>
		        		</tr>
		        	</tbody>
		        </c:forEach>
	        </table>
	    </div>
	<div id="faq_thtml"></div>

<!-- } FAQ 끝 -->


<script src="http://theme001.whalessoft.com/js/viewimageresize.js"></script>
<script>
jQuery(function() {
    $(".closer_btn").on("click", function() {
        $(this).closest(".con_inner").slideToggle('slow', function() {
			var $h3 = $(this).closest("li").find("h3");

			$("#faq_con li h3").removeClass("faq_li_open");
			if($(this).is(":visible")) {
				$h3.addClass("faq_li_open");
			}
		});
    });
});

function faq_open(el)
{	
    var $con = $(el).closest("li").find(".con_inner"),
		$h3 = $(el).closest("li").find("h3");

    if($con.is(":visible")) {
        $con.slideUp();
		$h3.removeClass("faq_li_open");
    } else {
        $("#faq_con .con_inner:visible").css("display", "none");

        $con.slideDown(
            function() {
                // 이미지 리사이즈
                $con.viewimageresize2();
				$("#faq_con li h3").removeClass("faq_li_open");

				$h3.addClass("faq_li_open");
            }
        );
    }

    return false;
}
</script>
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