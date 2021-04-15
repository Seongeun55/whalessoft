<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
	
	<!-- 서브헤더 시작  -->
	<div class="sub_bg sub_bg_">
		<c:choose>
			<c:when test="${param.id eq 'page1'}">
				<h2 id="sub_menu_title" class="top" title="회사소개">
					<strong title="회사소개">회사소개</strong>
		            <span class="sub-title">든든한 당신의 파트너로 곁에 있겠습니다.</span>
	        	</h2>
			</c:when>
			<c:when test="${param.id eq 'page2'}">
				<h2 id="sub_menu_title" class="top" title="연혁">
					<strong title="연혁">연혁</strong>
		            <span class="sub-title">든든한 당신의 파트너로 곁에 있겠습니다.</span>
		        </h2>
			</c:when>
			<c:when test="${param.id eq 'page3'}">
				<h2 id="sub_menu_title" class="top" title="조직도">
					<strong title="조직도">조직도</strong>
					<span class="sub-title">든든한 당신의 파트너로 곁에 있겠습니다.</span>
				</h2>
			</c:when>
			<c:when test="${param.id eq 'page4'}">
				<h2 id="sub_menu_title" class="top" title="오시는길">
					<strong title="오시는길">오시는길</strong>
					<span class="sub-title">든든한 당신의 파트너로 곁에 있겠습니다.</span>
				</h2>
			</c:when>
			<c:when test="${param.id eq 'page5'}">
				<h2 id="sub_menu_title" class="top" title="사업안내">
					<strong title="사업안내">사업안내</strong>
					<span class="sub-title">든든한 당신의 파트너로 곁에 있겠습니다.</span>
		        </h2>
			</c:when>
			<c:when test="${param.id eq 'page6'}">
				<h2 id="sub_menu_title" class="top" title="제품소개 1 페이지">
					<strong title="제품소개 1 페이지">제품소개</strong>
					<span class="sub-title">든든한 당신의 파트너로 곁에 있겠습니다.</span>
				</h2>
			</c:when>
			<c:when test="${param.id eq 'page7'}">
				<h2 id="sub_menu_title" class="top" title="공지사항 1 페이지">
					<strong title="공지사항 1 페이지">공지사항</strong>
		            <span class="sub-title">든든한 당신의 파트너로 곁에 있겠습니다.</span>
				</h2>
			</c:when>
			<c:when test="${param.id eq 'page8'}">
				<h2 id="sub_menu_title" class="top" title="갤러리 1 페이지">
		        	<strong title="갤러리 1 페이지">갤러리</strong>
		            <span class="sub-title">든든한 당신의 파트너로 곁에 있겠습니다.</span>
				</h2>
			</c:when>
			<c:when test="${param.type eq 'qna'}">
				<h2 id="sub_menu_title" class="top" title="온라인문의 글쓰기">
		        	<strong title="온라인문의 글쓰기">Q&A</strong>
		            <span class="sub-title">든든한 당신의 파트너로 곁에 있겠습니다.</span>
				</h2>
			</c:when>
			<c:when test="${param.type eq 'faq'}">
				<h2 id="sub_menu_title" class="top" title="자주묻는질문">
		        	<strong title="자주묻는질문">자주묻는질문</strong>
		            <span class="sub-title">든든한 당신의 파트너로 곁에 있겠습니다.</span>
				</h2>
			</c:when>
		</c:choose>
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
	        	<c:if test="${param.id eq 'page6'}">
		            <ul class="leftmenu ">
		                <li class="leftmenu_s border-left gnb_2dli_1 active"><a href="/board.do?id=page6" target="_self" >제품소개</a></li>
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
				    	<c:when test="${param.id eq 'page7'}">
					    	<li class="leftmenu_s border-left gnb_2dli_1 active"><a href="/board.do?id=page7" target="_self" >공지사항</a></li>
					    	<script language='javascript'> display_submenu(3); </script> 
			            	<li class="leftmenu_s  gnb_2dli_2 "><a href="/board.do?id=page8" target="_self" >갤러리</a></li>
			            	<li class="leftmenu_s  gnb_2dli_3 "><a href="/board/list.do?type=qna" target="_self" >Q&A</a></li>
			            	<li class="leftmenu_s  gnb_2dli_4 "><a href="/board/list.do?type=faq" target="_self" >FAQ</a></li>
				    	</c:when>
				    	<c:when test="${param.id eq 'page8'}">
					    	<li class="leftmenu_s border-left gnb_2dli_1 "><a href="/board.do?id=page7" target="_self" >공지사항</a></li>
			            	<li class="leftmenu_s  gnb_2dli_2 active"><a href="/board.do?id=page8" target="_self" >갤러리</a></li>
			            	<script language='javascript'> display_submenu(3); </script>
			            	<li class="leftmenu_s  gnb_2dli_3 "><a href="/board/list.do?type=qna" target="_self" >Q&A</a></li>
			            	<li class="leftmenu_s  gnb_2dli_4 "><a href="/board/list.do?type=faq" target="_self" >FAQ</a></li>
				    	</c:when>
				    	<c:when test="${param.type eq 'qna'}">
					    	<li class="leftmenu_s border-left gnb_2dli_1 "><a href="/board.do?id=page7" target="_self" >공지사항</a></li>
			            	<li class="leftmenu_s  gnb_2dli_2 "><a href="/board.do?id=page8" target="_self" >갤러리</a></li>
			            	<li class="leftmenu_s  gnb_2dli_3 active"><a href="/board/list.do?type=qna" target="_self" >Q&A</a></li>
			            	<script language='javascript'> display_submenu(3); </script>
			            	<li class="leftmenu_s  gnb_2dli_4 "><a href="/board/list.do?type=faq" target="_self" >FAQ</a></li>
				    	</c:when>
				    	<c:when test="${param.type eq 'faq'}">
					    	<li class="leftmenu_s border-left gnb_2dli_1 "><a href="/board.do?id=page7" target="_self" >공지사항</a></li>
			            	<li class="leftmenu_s  gnb_2dli_2 "><a href="/board.do?id=page8" target="_self" >갤러리</a></li>
			            	<li class="leftmenu_s  gnb_2dli_3 "><a href="/board/list.do?type=qna" target="_self" >Q&A</a></li>
			            	<li class="leftmenu_s  gnb_2dli_4 active"><a href="/board/list.do?type=faq" target="_self" >FAQ</a></li>
			            	<script language='javascript'> display_submenu(3); </script>
				    	</c:when>
					</c:choose>	                
	            </ul>
	    	</li>
	    </ul>
	</div>
	