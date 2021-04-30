<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>    

<%@include file="/WEB-INF/jsp/egovframework/com/web/header.jsp" %>
<script type="text/javascript">
	function searchArticle(){
		document.articleForm.pageIndex.value = 1;
		document.articleForm.submit();
	}
	function linkPage(pageNo){	
		document.pageForm.pageIndex.value = pageNo;		
	   	document.pageForm.submit();
	}
	</script>
<form name="pageForm">
	<input type="hidden" name="bbsId" value="<c:out value='${param.bbsId}'/>" >
	<input type="hidden" name="pageIndex" value="<c:out value='${searchVO.pageIndex}'/>">
	<!-- <input type="hidden" name="searchCnd" value="">
	<input type="hidden" name="searchWrd" value=""> -->
</form>

<!-- 콘텐츠 시작 { -->
<div class="container">
	<%@include file="/WEB-INF/jsp/egovframework/com/web/subheader.jsp" %>
        
	<div class="sub-con">
    	<div class="title-wrap">공지사항</div>
	</div>
	
	<!-- 게시판  시작 { -->
	<div id="bo_list">   
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
    	<!-- } 게시판 조회 및 버튼 끝 -->
        
        <!-- 게시판 목록 시작 -->	
	    <div class="tbl_head01 tbl_wrap">
	        <table>
		        <caption>목록</caption>
		        <colgroup>
					<col style="width: 5%;">
					<col style="width: 40%;">
					<col style="width: 12%;">
					<col style="width: 13%;">
					<col style="width: 9%;">
				</colgroup>
		        <thead>
		        <tr>
		            <th scope="col">번호</th>
		            <th scope="col">제목</th>
		            <th scope="col">작성자</th>
		            <th scope="col">등록일</th>
		            <th scope="col">조회수</th>            
		        </tr>
		        </thead>
       	
	       		<tbody>
	       			<!-- 공지사항 부분 -->
		       		<c:forEach items="${noticeList}" var="noticeInfo" varStatus="status">		    		       	
						<tr class=" even">
							<td align="center"><img src="<c:url value='/images/egovframework/com/cop/bbs/icon_notice.png'/>" alt="notice"></td>
							<td class="bold">
								<form name="subForm" method="get" action="<c:url value='/board/view.do'/>">
								    <input name="nttId" type="hidden" value="<c:out value="${noticeInfo.nttId}"/>">
								    <input name="bbsId" type="hidden" value="<c:out value="${noticeInfo.bbsId}"/>">
								    <input name="pageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>"/>
								    <span class="link"><input type="submit" value="<c:out value='${fn:substring(noticeInfo.nttSj, 0, 40)}'/><c:if test="${noticeInfo.commentCo != ''}">	<c:out value='[${noticeInfo.commentCo}]'/></c:if>" style="border:0px solid #e0e0e0;">
								    </span>
								</form>
							</td>
							<td class="td_num"><c:out value='${noticeInfo.frstRegisterNm}'/></td>
							<td class="td_datetime"><c:out value='${noticeInfo.frstRegisterPnttm}'/></td>
							<td class="td_num"><c:out value='${noticeInfo.inqireCo}'/></td>		
						</tr>
					</c:forEach>
					
					<!-- 게시글 부분 -->
		        	<c:forEach items="${resultList}" var="resultInfo" varStatus="status">	
		        		<c:if test="${(resultCnt-(searchVO.pageIndex-1) * searchVO.pageSize - status.index) %2==0}">						   
		        		<tr class=" even">
		        			<td align="center"><c:out value="${resultCnt-(searchVO.pageIndex-1) * searchVO.pageSize - status.index}"/></td>		        			
		        			<c:choose>		        				
		        				<c:when test="${resultInfo.sjBoldAt == 'Y'}">	<!-- 제목이 Bold인 경우 -->
		        					<td class="bold">
										<form name="subForm" method="get" action="<c:url value='/board/view.do'/>">
											    <input name="nttId" type="hidden" value="<c:out value="${resultInfo.nttId}"/>">
											    <input name="bbsId" type="hidden" value="<c:out value="${resultInfo.bbsId}"/>">
											    <input name="pageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>"/>
											    <span class="link"><c:if test="${resultInfo.replyLc!=0}"><c:forEach begin="0" end="${resultInfo.replyLc}" step="1">&nbsp;	</c:forEach><img src="<c:url value='/images/egovframework/com/cop/bbs/icon_reply.png'/>" alt="secret"></c:if><input type="submit" value="<c:out value='${fn:substring(resultInfo.nttSj, 0, 40)}'/><c:if test="${resultInfo.commentCo != ''}">	<c:out value='[${resultInfo.commentCo}]'/></c:if>" style="border:0px solid #e0e0e0;"></span>
										</form>
									</td>
		        				</c:when>
		        				<c:otherwise>
		        					<td class="td_subject" style="padding-left:10px">
				        				<div class="bo_tit">
				        					<form name="subForm" method="get" action="<c:url value='/board/view.do'/>">
											    <input name="nttId" type="hidden" value="<c:out value="${resultInfo.nttId}"/>">
											    <input name="bbsId" type="hidden" value="<c:out value="${resultInfo.bbsId}"/>">
											    <input name="pageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>"/>								
											    <span class="link"><input type="submit" value="<c:out value='${fn:substring(resultInfo.nttSj, 0, 40)}'/>" style="border:0px solid #e0e0e0; background:rgba(0,0,0,0);"></span>
											</form>
				        				</div>
		        					</td>
		        				</c:otherwise>
		        			</c:choose>
		        			
		        			<td class="td_num"><c:out value='${resultInfo.frstRegisterNm}'/></td>
		        			<td class="td_datetime"><c:out value='${resultInfo.frstRegisterPnttm}'/></td>
		        			<td class="td_num"><c:out value='${resultInfo.inqireCo}'/></td>
		        		</tr>
		        		</c:if>	  
		        		
		        		<c:if test="${(resultCnt-(searchVO.pageIndex-1) * searchVO.pageSize - status.index) %2==1}">						   
		        		<tr>
		        			<td align="center"><c:out value="${resultCnt-(searchVO.pageIndex-1) * searchVO.pageSize - status.index}"/></td>		        			
		        			<c:choose>		        				
		        				<c:when test="${resultInfo.sjBoldAt == 'Y'}">	<!-- 제목이 Bold인 경우 -->
		        					<td class="bold">
										<form name="subForm" method="get" action="<c:url value='/board/view.do'/>">
											    <input name="nttId" type="hidden" value="<c:out value="${resultInfo.nttId}"/>">
											    <input name="bbsId" type="hidden" value="<c:out value="${resultInfo.bbsId}"/>">
											    <input name="pageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>"/>
											    <span class="link"><c:if test="${resultInfo.replyLc!=0}"><c:forEach begin="0" end="${resultInfo.replyLc}" step="1">&nbsp;	</c:forEach><img src="<c:url value='/images/egovframework/com/cop/bbs/icon_reply.png'/>" alt="secret"></c:if><input type="submit" value="<c:out value='${fn:substring(resultInfo.nttSj, 0, 40)}'/><c:if test="${resultInfo.commentCo != ''}">	<c:out value='[${resultInfo.commentCo}]'/></c:if>" style="border:0px solid #e0e0e0;"></span>
										</form>
									</td>
		        				</c:when>
		        				<c:otherwise>
		        					<td class="td_subject" style="padding-left:10px">
				        				<div class="bo_tit">
				        					<form name="subForm" method="get" action="<c:url value='/board/view.do'/>">
											    <input name="nttId" type="hidden" value="<c:out value="${resultInfo.nttId}"/>">
											    <input name="bbsId" type="hidden" value="<c:out value="${resultInfo.bbsId}"/>">
											    <input name="pageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>"/>								
											    <span class="link"><input type="submit" value="<c:out value='${fn:substring(resultInfo.nttSj, 0, 40)}'/>" style="border:0px solid #e0e0e0; background:rgba(0,0,0,0);"></span>
											</form>
				        				</div>
		        					</td>
		        				</c:otherwise>
		        			</c:choose>
		        			
		        			<td class="td_num"><c:out value='${resultInfo.frstRegisterNm}'/></td>
		        			<td class="td_datetime"><c:out value='${resultInfo.frstRegisterPnttm}'/></td>
		        			<td class="td_num"><c:out value='${resultInfo.inqireCo}'/></td>
		        		</tr>
		        		</c:if>	       				        
		        	</c:forEach>
		        </tbody>
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