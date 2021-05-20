<!DOCTYPE html>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
 /**
  * [추가] - 2021.05.17
  */
  /* Image Path 설정 */
  String imagePath_icon   = "/images/egovframework/com/sym/prm/icon/";
  String imagePath_button = "/images/egovframework/com/sym/prm/button/";
%>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><spring:message code="ussIonBnr.bannerNmSearch.pageTop.title"/></title><!-- 배너명 검색 -->
<link href="<c:url value="/css/egovframework/com/com.css"/>" rel="stylesheet" type="text/css">
<link href="<c:url value="/css/egovframework/com/button.css"/>" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<c:url value='/js/egovframework/com/cmm/jquery-1.4.2.min.js' />" ></script>
<script language="javascript1.2"  type="text/javaScript">

/* ********************************************************
 * 페이징 처리 함수
 ******************************************************** */
function linkPage(pageNo){
	document.progrmManageForm.pageIndex.value = pageNo;
	document.progrmManageForm.action = "<c:url value='/uss/ion/bnr/BannerListSearchNew.do'/>";
   	document.progrmManageForm.submit();
}

/* ********************************************************
 * 조회 처리 함수
 ******************************************************** */
function selectBannerListSearch() {	
	document.progrmManageForm.pageIndex.value = 1;
	document.progrmManageForm.action = "<c:url value='/uss/ion/bnr/BannerListSearchNew.do'/>";
	document.progrmManageForm.submit();
}

/* ********************************************************
 * 프로그램목록 선택 처리 함수
 ******************************************************** */
function choisBannerListSearch(vFileNm) {
	var parentFrom = parent.document.getElementsByTagName('form');
	parentFrom[0].bannerId.value = vFileNm;
    parent.$('.ui-dialog-content').dialog('close');
}

</script>
</head>
<body>
<form name="bannerManageForm" action ="<c:url value='/uss/ion/bnr/BannerListSearchNew.do'/>" method="post">
<input name="pageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>"/>
<div class="board" style="width:480px">
	<h1><spring:message code="ussIonBnr.bannerNmSearch.pageTop.title"/></h1><!-- 배너명 검색 -->

	<div class="search_box" title="<spring:message code="common.searchCondition.msg" />"><!-- 이 레이아웃은 하단 정보를 대한 검색 정보로 구성되어 있습니다. -->
		<ul>
			<li>
				<label for=""><spring:message code="ussIonBnr.bannerNmSearch.bannerNm"/> : </label><!-- 배너명 -->
				<input class="s_input2 vat" name="searchKeyword" type="text"  value='<c:out value="${searchVO.searchKeyword}"/>'  size="30" maxlength="60" title="<spring:message code="title.searchCondition"/>" /><!-- 검색조건 -->
				
				<input class="s_btn" type="submit" value='<spring:message code="button.inquire" />' title="<spring:message code="title.inquire"/>" onclick="selectBannerListSearch(); return false;" /><!-- 조회 -->
			</li>
		</ul>
	</div>

	<table class="board_list">
		<caption></caption>
		<colgroup>
			<col style="width:30%" />
			<col style="width:70%" />
		</colgroup>
		<thead>
			<tr>
			   <th scope="col"><spring:message code="ussIonBnr.bannerNmSearch.bannerNm"/></th><!-- 배너명 -->
			   <th scope="col"><spring:message code="ussIonBnr.bannerNmSearch.bannerDc"/></th><!-- 배너설명 -->
			</tr>
		</thead>
		<tbody>
			<c:forEach var="result" items="${bannerList}" varStatus="status">			
			  <tr>			     
			    <td>
			      <span class="link"><a href="#LINK" onclick="choisBannerListSearch('<c:out value="${result.bannerId}"/>'); return false;">
			      <c:out value="${result.bannerNm}"/></a></span>
			   	</td>
			    <td><c:out value="${result.bannerDc}"/></td>					    
			  </tr>
			 </c:forEach>
		</tbody>
	</table>

	<!-- paging navigation -->
	<div class="pagination">
		<ul>
			<ui:pagination paginationInfo="${paginationInfo}" type="image" jsFunction="linkPage"/>
		</ul>
	</div>
</div>

</form>
</body>
</html>

