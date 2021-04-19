<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>   
<%@include file="/WEB-INF/jsp/egovframework/com/web/header.jsp" %>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<script src="/js/viewimageresize.js"></script>
<script type="text/javascript">
/*********************************************************
 * 조회 처리 함수
 ******************************************************** */
function searchFaq(){
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
<!-- 콘텐츠 시작 { -->
<div class="container">
	<%@include file="/WEB-INF/jsp/egovframework/com/web/subheader.jsp" %>
		
	<!-- FAQ 시작 { -->
	<div id="faq_hhtml">
		<div class="sub-con">
    		<div class="title-wrap">자주묻는질문</div>
		</div>
	</div>	
	
	<div class="sub-con">
		<fieldset id="faq_sch">
		    <legend>FAQ 검색</legend>
		    <form name="faqForm" action="/faq/list.do" onsubmit="searchFaq(); return false;" method="post">
			    <!-- 게시판 페이지 정보 및 버튼 시작 { -->
			    <span class="sch_tit">FAQ 검색</span>
			    <input type="hidden" name="searchCnd" value="0">
			    <label for="stx" class="sound_only">검색어<strong class="sound_only"> 필수</strong></label>
			    <input type="text" name="searchWrd" required id="stx" class="frm_input" size="15" maxlength="15">
			    <button type="submit" value="검색" class="btn_submit"><i class="fa fa-search" aria-hidden="true"></i> 검색</button>			    
			</form>
		</fieldset>
	
		<div id="faq_wrap" class="faq_1">
			<section id="faq_con">
	        	<h2>자주묻는질문 목록</h2>
	       	 	<ol>
	       	 		<c:forEach items="${resultList}" var="resultInfo" varStatus="status">
	       	 			<li>
	       	 				<h3>
	       	 					<span class="tit_bg">Q</span><a href="#none" onclick="return faq_open(this);">
					                <p><c:out value='${fn:substring(resultInfo.qestnSj, 0, 40)}'/><br /></p>
					            </a>
					            <button class="tit_btn" onclick="return faq_open(this);"><i class="fa fa-plus" aria-hidden="true"></i><span class="sound_only">열기</span></button>
	       	 				</h3>
	       	 				<div class="con_inner">
					            <p><c:out value="${fn:replace(resultInfo.answerCn , crlf , '<br/>')}" escapeXml="false" /></p>					           
					            <c:if test="${not empty resultInfo.atchFileId}">
					            	<div class = "file_wrap">
					            		<c:import url="/cmm/fms/selectFileInfs.do" charEncoding="utf-8">
											<c:param name="param_atchFileId" value="${resultInfo.atchFileId}" />
										</c:import>
					            	</div>					            	
					            </c:if>
								<button type="button" class="closer_btn"><i class="fa fa-minus" aria-hidden="true"></i><span class="sound_only">닫기</span></button>
					        </div>
	       	 			</li>	       	 			
	       	 		</c:forEach>
				 </ol>  
	    	</section>
	    </div>    
	</div>	<!-- sub con 끝 -->	
	<div id="faq_thtml"></div>
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