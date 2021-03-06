<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>	
<!-- 서브헤더 시작  -->
	<!-- 서브배너 시작 -->		
	<div class="sub_bg" style="background:url('/cmm/fms/getImage.do?atchFileId=<c:out value="${bannerImageFile}"/>')no-repeat 50% 50%;">								
		<h2 id="sub_menu_title" class="top" title=<c:out value="${menuNm}"/>>
			<strong title="<c:out value="${menuNm}"/>"><c:out value="${menuNm}"/></strong> 	<!-- 대메뉴 눌렀을 경우 -->
			<span class="sub-title">${menuDc}</span>
	   	</h2>
   	</div>  
	<!-- 서브배너 끝 -->
	
	<!-- 서브메뉴 시작 -->
	<div id="submenu">	
		<ul>
			<li>
				<span class="sound_only"><c:out value="${menuNm}"/></span>
				<ul class="leftmenu ">
					<c:forEach var="subMenu" items="${selectedSubMenuListMap}" varStatus="status">
						<!-- 첫번째 메뉴일 때 -->
						<c:set var="order" value=""/>
						<c:if test="${subMenu.menuOrdr==1}">
							<c:set var="order" value=" border-left"/>
						</c:if>
						<!-- 선택된 메뉴 -->
						<c:set var="selected" value=""/>
						<c:if test="${subMenu.menuNo==menuNo || (mainMenuNo==menuNo && subMenu.menuOrdr==1)}">
							<c:set var="selected" value=" active"/>
						</c:if>
						<!-- 주소에 menuNo를 붙이기 위해 -->
						<c:set var="sc" value="?"/>
						<c:if test="${fn:contains(subMenu.chkURL, '?')}">
							<c:set var="sc" value="&"/>
						</c:if>		
							<li class="leftmenu_s<c:out value="${order}" /> gnb_2dli_<c:out value="${subMenu.menuOrdr}" /><c:out value="${selected}" />">
								<a href="<c:out value="${subMenu.chkURL}" /><c:out value='${sc}'/>menuNo=<c:out value='${subMenu.menuNo}'/>" target="_self" ><c:out value="${subMenu.menuNm}" /></a>
							</li>		
					</c:forEach>
				</ul>
			</li>
		</ul>
	</div>
	<!-- 서브메뉴 끝 -->
	