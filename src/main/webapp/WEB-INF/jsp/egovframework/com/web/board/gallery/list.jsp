<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>    

<%@include file="/WEB-INF/jsp/egovframework/com/web/header.jsp" %>
<link rel="stylesheet" type="text/css" href="/css/gallery.css"/>

<!-- 콘텐츠 시작 { -->
<div class="container">
	
	<%@include file="/WEB-INF/jsp/egovframework/com/web/subheader.jsp" %>
    
    <div class="sub-con">
    	<div class="title-wrap">갤러리</div>
	</div>
	
	<!-- 게시판 목록 시작 { -->
	<div id="bo_gall">
        <!-- 게시판 페이지 정보 및 버튼 시작 { -->
        <div id="bo_btn_top">
            <div id="bo_list_total">
                <span>Total ${resultCnt }건</span>
                ${searchVO.pageIndex } 페이지
            </div>
	
    	   <!-- <ul class="btn_bo_user">
                <li>
                    <button type="button" class="btn_bo_sch btn_b01 btn" title="게시판 검색"><i class="fa fa-search" aria-hidden="true"></i><span class="sound_only">게시판 검색</span></button>
                </li>
           	 	</ul> -->
        </div>
        <!-- } 게시판 페이지 정보 및 버튼 끝 -->
		
        <ul id="gall_ul" class="gall_row">
        <c:forEach items="${resultList}" var="result" varStatus="status">
        	<li class="gall_li col-gn-3">
        	<form name="mainForm" action="/board/view.do" method="post">
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

	    <!-- 게시판 검색 시작
	    <div class="bo_sch_wrap">
	        <fieldset class="bo_sch">
	            <h3>검색</h3>
	            <form name="fsearch" method="get">
	                <input type="hidden" name="bo_table" value="gallery">
	                <input type="hidden" name="sca" value="">
	                <input type="hidden" name="sop" value="and">
	                <label for="sfl" class="sound_only">검색대상</label>
	                <select name="sfl" id="sfl">
	                    <option value="wr_subject">제목</option>
	                    <option value="wr_content">내용</option>
	                    <option value="wr_subject||wr_content">제목+내용</option>
	                    <option value="wr_name,1">글쓴이</option>
	                    <option value="wr_name,0">글쓴이(코)</option>
	                </select>
	                <label for="stx" class="sound_only">검색어<strong class="sound_only"> 필수</strong></label>
	                <div class="sch_bar">
	                    <input type="text" name="stx" value="" required id="stx" class="sch_input" size="25" maxlength="20" placeholder="검색어를 입력해주세요">
	                    <button type="submit" value="검색" class="sch_btn"><i class="fa fa-search" aria-hidden="true"></i><span class="sound_only">검색</span></button>
	                </div>
	                <button type="button" class="bo_sch_cls"><i class="fa fa-times" aria-hidden="true"></i><span class="sound_only">닫기</span></button>
	            </form>
	        </fieldset>
	        <div class="bo_sch_bg"></div>
	    </div>
	    <script>
	        // 게시판 검색
	        $(".btn_bo_sch").on("click", function() {
	            $(".bo_sch_wrap").toggle();
	        })
	        $('.bo_sch_bg, .bo_sch_cls').click(function() {
	            $('.bo_sch_wrap').hide();
	        });
	    </script>
	    <!-- } 게시판 검색 끝 -->
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