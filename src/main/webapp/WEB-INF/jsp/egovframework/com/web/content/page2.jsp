<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@include file="/WEB-INF/jsp/egovframework/com/web/header.jsp" %>
<!-- 콘텐츠 시작 { -->
<div class="container">
	<%@include file="/WEB-INF/jsp/egovframework/com/web/subheader.jsp" %>

<div class="sub-con">
	<div class="title-wrap">연혁</div>
   	<div class="title-img">
   		<div class="title-logo"><img src="/images/sub/logo.gif" alt="logo" /></div>
   	</div>
   	<div class="con-box">
   		<ul class="item-history">
   			<li data-aos="fade-left" data-aos-duration="800">
   				<div class="txt">
   					<dl>
   						<dt>2020</dt>
   						<dd>
   							<ul class="item-dot">
   								<li>Lorem Ipsum is simply dummy text of the printing</li>
								<li>Lorem Ipsum has been the industry's standard dummy text ever since</li>
								<li>1500s, when an unknown printer took a galley</li> 
								<li>make a type specimen book. It has survived not only five centuries, but also the leap into electronic</li>
   							</ul>
   						</dd>
   					</dl>
   				</div>
   				<div class="img">
   					<img src="/images/sub/s04.jpg" alt="history" />
   				</div>
   			</li>
   			<li data-aos="fade-right" data-aos-duration="800">
   				<div class="txt">
   					<dl>
   						<dt>2019</dt>
   						<dd>
   							<ul class="item-dot">
   								<li>Lorem Ipsum is simply dummy text</li>
								<li>Lorem Ipsum has been the industry's standard dummy</li>
								<li>1500s, when an unknown printer took a galley</li> 
								<li>make a type specimen book. It has survived not only five centuries, but also the leap into electronic</li>
   							</ul>
   						</dd>
   					</dl>
   				</div>
   				<div class="img">
   					<img src="/images/sub/s05.jpg" alt="history" />
   				</div>
   			</li>
   			<li data-aos="fade-left" data-aos-duration="800">
   				<div class="txt">
   					<dl>
   						<dt>2018</dt>
   						<dd>
   							<ul class="item-dot">
   								<li>Lorem Ipsum is simply dummy text of the printing</li>
								<li>Lorem Ipsum has been the industry's standard dummy text ever since</li>
								<li>1500s, when an unknown printer took a galley</li> 
								<li>make a type specimen book. It has survived not only five centuries, but also the leap into electronic</li>
   							</ul>
   						</dd>
   					</dl>
   				</div>
   				<div class="img">
   					<img src="/images/sub/s06.jpg" alt="history" />
   				</div>
   			</li>
   			<li data-aos="fade-right" data-aos-duration="800">
   				<div class="txt">
   					<dl>
   						<dt>2016</dt>
   						<dd>
   							<ul class="item-dot">
   								<li>Lorem Ipsum is simply dummy text</li>
								<li>Lorem Ipsum has been the industry's standard dummy</li>
								<li>1500s, when an unknown printer took a galley</li> 
								<li>make a type specimen book. It has survived not only five centuries, but also the leap into electronic</li>
   							</ul>
   						</dd>
   					</dl>
   				</div>
   				<div class="img">
   					<img src="/images/sub/s07.png" alt="history" />
   				</div>
   			</li>
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