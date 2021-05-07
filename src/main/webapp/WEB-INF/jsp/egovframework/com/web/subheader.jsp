<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
	
	<!-- 서브헤더 시작  -->	
	<c:forEach var="sub_result" items="${list_submenu}" varStatus="status">
		<c:set var="i" value="${i+1}"/>
		<c:choose>
		<c:when test="${sub_result.upperMenuId==100}">
			<div class="sub_bg sub_bg_intro">
				<h2 id="sub_menu_title" class="top" title=<c:out value="${sub_result.menuNm}"/>>
					<strong title="<c:out value="${sub_result.menuNm}"/>"><c:out value="${sub_result.menuNm}"/></strong>
		            <span class="sub-title">든든한 당신의 파트너로 곁에 있겠습니다.</span>
	        	</h2>
       		</div>	
		</c:when>
		<c:when test="${sub_result.upperMenuId==200}">
			<div class="sub_bg sub_bg_bus">
				<h2 id="sub_menu_title" class="top" title=<c:out value="${sub_result.menuNm}"/>>
					<strong title="<c:out value="${sub_result.menuNm}"/>"><c:out value="${sub_result.menuNm}"/></strong>
		            <span class="sub-title">든든한 당신의 파트너로 곁에 있겠습니다.</span>
	        	</h2>
       		</div>	
		</c:when>
		<c:when test="${sub_result.upperMenuId==300}">
			<div class="sub_bg sub_bg_product">
				<h2 id="sub_menu_title" class="top" title=<c:out value="${sub_result.menuNm}"/>>
					<strong title="<c:out value="${sub_result.menuNm}"/>"><c:out value="${sub_result.menuNm}"/></strong>
		            <span class="sub-title">든든한 당신의 파트너로 곁에 있겠습니다.</span>
	        	</h2>
       		</div>	
		</c:when>
		<c:when test="${sub_result.upperMenuId==400}">
			<div class="sub_bg sub_bg_">
				<h2 id="sub_menu_title" class="top" title=<c:out value="${sub_result.menuNm}"/>>
					<strong title="<c:out value="${sub_result.menuNm}"/>"><c:out value="${sub_result.menuNm}"/></strong>
		            <span class="sub-title">든든한 당신의 파트너로 곁에 있겠습니다.</span>
	        	</h2>
       		</div>	
		</c:when>
		</c:choose>
	</c:forEach>

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
		            <c:choose>
			            <c:when test="${param.id eq 'page1'}">
				            <li class="leftmenu_s border-left gnb_2dli_1 active"><a href="/content.do?id=page1" target="_self" >회사소개</a></li>
				            <script language='javascript'> display_submenu(0); </script> 	 
			            	<li class="leftmenu_s  gnb_2dli_2 "><a href="/content.do?id=page2" target="_self" >연혁</a></li>               
			            	<li class="leftmenu_s  gnb_2dli_3 "><a href="/content.do?id=page3" target="_self" >조직도</a></li>
			            	<li class="leftmenu_s  gnb_2dli_4 "><a href="/content.do?id=page4" target="_self" >오시는길</a></li>
			            </c:when>
			            <c:when test="${param.id eq 'page2'}">
				            <li class="leftmenu_s border-left gnb_2dli_1 "><a href="/content.do?id=page1" target="_self" >회사소개</a></li>
			            	<li class="leftmenu_s  gnb_2dli_2 active"><a href="/content.do?id=page2" target="_self" >연혁</a></li>    
			            	<script language='javascript'> display_submenu(0); </script> 	            
			            	<li class="leftmenu_s  gnb_2dli_3 "><a href="/content.do?id=page3" target="_self" >조직도</a></li>
			            	<li class="leftmenu_s  gnb_2dli_4 "><a href="/content.do?id=page4" target="_self" >오시는길</a></li>
			            </c:when>
			            <c:when test="${param.id eq 'page3'}">
				            <li class="leftmenu_s border-left gnb_2dli_1 "><a href="/content.do?id=page1" target="_self" >회사소개</a></li>				            
			            	<li class="leftmenu_s  gnb_2dli_2 "><a href="/content.do?id=page2" target="_self" >연혁</a></li>               
			            	<li class="leftmenu_s  gnb_2dli_3 active"><a href="/content.do?id=page3" target="_self" >조직도</a></li>
			            	<script language='javascript'> display_submenu(0); </script> 	 
			            	<li class="leftmenu_s  gnb_2dli_4 "><a href="/content.do?id=page4" target="_self" >오시는길</a></li>
			            </c:when>
			            <c:when test="${param.id eq 'page4'}">
				            <li class="leftmenu_s border-left gnb_2dli_1 "><a href="/content.do?id=page1" target="_self" >회사소개</a></li>				            	 
			            	<li class="leftmenu_s  gnb_2dli_2 "><a href="/content.do?id=page2" target="_self" >연혁</a></li>               
			            	<li class="leftmenu_s  gnb_2dli_3 "><a href="/content.do?id=page3" target="_self" >조직도</a></li>
			            	<li class="leftmenu_s  gnb_2dli_4 active"><a href="/content.do?id=page4" target="_self" >오시는길</a></li>
			            	<script language='javascript'> display_submenu(0); </script> 
			            </c:when>
			            <c:otherwise>
			            </c:otherwise>
		            </c:choose>	                
	            </ul>
	    	</li>
	    </ul>
	    
	    <ul id="mysub1" style="display:none">
	        <li>
	        	<span class="sound_only">사업안내</span>
	        	<c:if test="${param.id eq 'page5'}">
		            <ul class="leftmenu ">
		            	<li class="leftmenu_s border-left gnb_2dli_1 active"><a href="/content.do?id=page5" target="_self" >사업안내</a></li>
	           			<script language='javascript'> display_submenu(1); </script> 
	           		</ul>          
           		</c:if> 		
	    	</li>
	    </ul>
	    
	    <ul id="mysub2" style="display:none">
	        <li>
	        	<span class="sound_only">제품소개</span>
	        	<c:if test="${param.type eq 'basic'}">
		            <ul class="leftmenu ">
		                <li class="leftmenu_s border-left gnb_2dli_1 active"><a href="/board/list.do?type=basic" target="_self" >제품소개</a></li>
		                <script language='javascript'> display_submenu(2); </script> 
		            </ul>
				</c:if>
	    	</li>
	    </ul>
	    
	    <ul id="mysub3" style="display:none">
	        <li>
	        	<span class="sound_only">고객센터</span>	        	
	            <ul class="leftmenu ">
	            	<c:choose>
				    	<c:when test="${param.type eq 'temp'}">
					    	<li class="leftmenu_s border-left gnb_2dli_1 active"><a href="/board/list.do?type=temp" target="_self" >공지사항</a></li>
					    	<script language='javascript'> display_submenu(3); </script> 
			            	<li class="leftmenu_s  gnb_2dli_2 "><a href="/board/list.do?type=gallery" target="_self" >갤러리</a></li>
			            	<li class="leftmenu_s  gnb_2dli_3 "><a href="/qna/list.do" target="_self" >Q&A</a></li>
			            	<li class="leftmenu_s  gnb_2dli_4 "><a href="/faq/list.do" target="_self" >FAQ</a></li>
				    	</c:when>
				    	<c:when test="${param.type eq 'gallery'}">
					    	<li class="leftmenu_s border-left gnb_2dli_1 "><a href="/board/list.do?type=temp" target="_self" >공지사항</a></li>
			            	<li class="leftmenu_s  gnb_2dli_2 active"><a href="/board/list.do?type=gallery" target="_self" >갤러리</a></li>
			            	<script language='javascript'> display_submenu(3); </script>
			            	<li class="leftmenu_s  gnb_2dli_3 "><a href="/qna/list.do" target="_self" >Q&A</a></li>
			            	<li class="leftmenu_s  gnb_2dli_4 "><a href="/faq/list.do" target="_self" >FAQ</a></li>
				    	</c:when>
				    	<c:when test="${param.type eq 'qna'}">
					    	<li class="leftmenu_s border-left gnb_2dli_1 "><a href="/board/list.do?type=temp" target="_self" >공지사항</a></li>
			            	<li class="leftmenu_s  gnb_2dli_2 "><a href="/board/list.do?type=gallery" target="_self" >갤러리</a></li>
			            	<li class="leftmenu_s  gnb_2dli_3 active"><a href="/qna/list.do" target="_self" >Q&A</a></li>
			            	<script language='javascript'> display_submenu(3); </script>
			            	<li class="leftmenu_s  gnb_2dli_4 "><a href="/faq/list.do" target="_self" >FAQ</a></li>
				    	</c:when>
				    	<c:when test="${param.type eq 'faq'}">
					    	<li class="leftmenu_s border-left gnb_2dli_1 "><a href="/board/list.do?type=temp" target="_self" >공지사항</a></li>
			            	<li class="leftmenu_s  gnb_2dli_2 "><a href="/board/list.do?type=gallery" target="_self" >갤러리</a></li>
			            	<li class="leftmenu_s  gnb_2dli_3 "><a href="/qna/list.do" target="_self" >Q&A</a></li>
			            	<li class="leftmenu_s  gnb_2dli_4 active"><a href="/faq/list.do" target="_self" >FAQ</a></li>
			            	<script language='javascript'> display_submenu(3); </script>
				    	</c:when>
					</c:choose>	                
	            </ul>
	    	</li>
	    </ul>
	</div>
	