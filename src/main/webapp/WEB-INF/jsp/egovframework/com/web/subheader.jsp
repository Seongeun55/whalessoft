<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
	
<!-- 서브헤더 시작  -->
<!-- 서브배너 시작 -->		
	<div class="sub_bg" style="background:url('/cmm/fms/getImage.do?atchFileId=<c:out value="${bannerImageFile}"/>')no-repeat 50% 50%;">								
		<h2 id="sub_menu_title" class="top" title=<c:out value="${menuNm}"/>>
			<strong title="<c:out value="${menuNm}"/>"><c:out value="${menuNm}"/></strong> 	<!-- 대메뉴 눌렀을 경우 -->
			<span class="sub-title">${menuDc}</span>
	   	</h2>
   	</div>  
<!-- 서브배너 끝 -->
	
	<script type="text/javascript">
	<!--
	    function display_submenu(num) { 
	         document.getElementById("mysub"+num).style.display="block";
	    }
	//-->
	</script>
	
	<!-- 서브메뉴 시작 -->
	<div id="submenu">
	<c:forEach var="mainMenu" items="${mainMenuList}" varStatus="status">
	<c:set var="selected" value=""/>
	<c:if test="${subMenuNo==menuNo}">
		<c:set var="selected" value="active"/>
	</c:if>
		<ul id="mysub<c:out value="${mainMenu.menuOrdr}"/>" style="display:none">
			<c:forEach var="subMenu" items="${selectedSubMenuListMap}" varStatus="status">
			<li>
				<span class="sound_only"><c:out value="${mainMenu.menuNm}"/></span>
				<ul class="leftmenu">				
					<c:if test="${subMenu.menuOrdr==1}">
						<li class="leftmenu_s border-left gnb_2dli_1 <c:out value="${selected}" />"><a href="<c:out value='${subMenu.chkURL}'/>"><c:out value="${subMenu.menuNm}"/></a></li>
					</c:if>
					<c:if test="${subMenu.menuOrdr!=1}">
						<li class="leftmenu_s  gnb_2dli_<c:out value="${subMenu.menuOrdr}"/> <c:out value="${selected}" />"><a href="<c:out value='${subMenu.chkURL}'/>"><c:out value="${subMenu.menuNm}"/></a></li>
					<!-- <script language='javascript'> display_submenu(<c:out value="${mainMenu.menuOrdr}"/>); </script> -->
					</c:if> 
				
				</ul>
			</li>
			</c:forEach>
		</ul>
	</c:forEach>
	</div>
	<!-- 서브메뉴 끝 -->
	