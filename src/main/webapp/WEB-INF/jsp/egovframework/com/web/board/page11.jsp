<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>    

<%@include file="/WEB-INF/jsp/egovframework/com/web/header.jsp" %>
<script type="text/javascript">
/* ********************************************************
 * 저장처리화면
 ******************************************************** */
function fn_egov_regist_qna(form){
	//input item Client-Side validate
	if (!validateQnaVO(form)) {	
		return false;
	} else {
		if(confirm("<spring:message code="common.regist.msg" />")){	
			alert("확인 : 스크립트")
			form.submit();	
		}
	} 
}
</script>
<!-- 콘텐츠 시작 { -->
<div class="container">
	<div class="sub_bg sub_bg_">
		<h2 id="sub_menu_title" class="top" title="온라인문의 글쓰기">
        	<strong title="온라인문의 글쓰기">Q&A 글쓰기</strong>
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
    	<div class="title-wrap">Q&A 글쓰기</div>
	</div>
	<section id="bo_w">
    <h2 class="sound_only">온라인문의 글쓰기</h2>

    <!-- 게시물 작성/수정 시작 { -->
    <form name="qnaVO" id="qnaVO" action="${pageContext.request.contextPath}/uss/olh/qna/insertQna.do" onSubmit="fn_egov_regist_qna(document.forms[0]); return false;" method="post">    
	    <div class="bo_w_info write_div">
	    	<div style="width:24%;" class="three_div">
			    <label for="wrterNm" class="sound_only">이름<strong>필수</strong></label>
			    <input type="text" name="wrterNm" value="" id="wrterNm" required class="frm_input full_input required" placeholder="이름">
		    </div>
		    
		    <div style="width:24%;" class="three_div">
			    <label for="emailAdres" class="sound_only">이메일</label>
			    <input type="email" name="emailAdres" value="" id="emailAdres" class="frm_input full_input email " placeholder="이메일">
		    </div>
			
			<div style="width:50%;" class="three_div">
				<label for="areaNo" class="sound_only">연락처<strong>필수</strong></label>
			    <input type="text" name="areaNo" id="areaNo" required class="frm_input three_input required" placeholder="010">
			    <Span>-</Span>
			    <input type="text" name="middleTelno" id="middleTelno" required class="frm_input three_input required" placeholder="1234">
			    <span>-</span>
			    <input type="text" name="endTelno" id="endTelno" required class="frm_input three_input required" placeholder="5678">
			</div>	
		<!-- 
		    <label for="wr_homepage" class="sound_only">홈페이지</label>
		    <input type="text" name="wr_homepage" value="" id="wr_homepage" class="frm_input half_input" size="50" placeholder="홈페이지"> -->
		</div>

	    <div class="bo_w_tit write_div">
	        <label for="qestnSj" class="sound_only">제목<strong>필수</strong></label>
	        
	        <div id="autosave_wrapper" class="write_div">
	            <input type="text" name="qestnSj" value="" id="qestnSj" required class="frm_input full_input required" size="50" maxlength="255" placeholder="제목">
	        </div>
	        
	    </div>
	
	    <div class="write_div">
	        <label for="qestnCn" class="sound_only">내용<strong>필수</strong></label>
	        <div class="wr_content ">
				<span class="sound_only">웹에디터 시작</span>
					<textarea id="qestnCn" name="qestnCn" class="" maxlength="65536" style="width:100%;height:300px"></textarea>
				<span class="sound_only">웹 에디터 끝</span>                    
			</div>        
	    </div>
	
	    <div class="btn_confirm write_div">
	        <a href="/board.do?id=page9" class="btn_cancel btn">취소</a>
	        <button type="submit" id="btn_submit" accesskey="s" class="btn_submit btn">작성완료</button>
	    </div>
    </form>

    <script>
        function html_auto_br(obj)
    {
        if (obj.checked) {
            result = confirm("자동 줄바꿈을 하시겠습니까?\n\n자동 줄바꿈은 게시물 내용중 줄바뀐 곳을<br>태그로 변환하는 기능입니다.");
            if (result)
                obj.value = "html2";
            else
                obj.value = "html1";
        }
        else
            obj.value = "";
    }

    function fwrite_submit(f)
    {
        var wr_content_editor = document.getElementById('wr_content');
if (!wr_content_editor.value) { alert("내용을 입력해 주십시오."); wr_content_editor.focus(); return false; }

        var subject = "";
        var content = "";
        $.ajax({
            url: g5_bbs_url+"/ajax.filter.php",
            type: "POST",
            data: {
                "subject": f.wr_subject.value,
                "content": f.wr_content.value
            },
            dataType: "json",
            async: false,
            cache: false,
            success: function(data, textStatus) {
                subject = data.subject;
                content = data.content;
            }
        });

        if (subject) {
            alert("제목에 금지단어('"+subject+"')가 포함되어있습니다");
            f.wr_subject.focus();
            return false;
        }

        if (content) {
            alert("내용에 금지단어('"+content+"')가 포함되어있습니다");
            if (typeof(ed_wr_content) != "undefined")
                ed_wr_content.returnFalse();
            else
                f.wr_content.focus();
            return false;
        }

        if (document.getElementById("char_count")) {
            if (char_min > 0 || char_max > 0) {
                var cnt = parseInt(check_byte("wr_content", "char_count"));
                if (char_min > 0 && char_min > cnt) {
                    alert("내용은 "+char_min+"글자 이상 쓰셔야 합니다.");
                    return false;
                }
                else if (char_max > 0 && char_max < cnt) {
                    alert("내용은 "+char_max+"글자 이하로 쓰셔야 합니다.");
                    return false;
                }
            }
        }

        if (!chk_captcha()) return false;

        document.getElementById("btn_submit").disabled = "disabled";

        return true;
    }
    </script>
</section>
<!-- } 게시물 작성/수정 끝 -->
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