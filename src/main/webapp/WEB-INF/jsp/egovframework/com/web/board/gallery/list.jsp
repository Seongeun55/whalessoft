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
		 
	    <!-- <nav id="bo_cate">
	        <h2>갤러리 카테고리</h2>
	        <ul id="bo_cate_ul">
	            <li><a href="http://theme001.whalessoft.com/bbs/board.php?bo_table=gallery" id="bo_cate_on">전체</a></li>
	            <li><a href="http://theme001.whalessoft.com/bbs/board.php?bo_table=gallery&amp;sca=%EC%B9%B4%ED%85%8C%EA%B3%A0%EB%A6%AC1">카테고리1</a></li>
	            <li><a href="http://theme001.whalessoft.com/bbs/board.php?bo_table=gallery&amp;sca=%EC%B9%B4%ED%85%8C%EA%B3%A0%EB%A6%AC2">카테고리2</a></li>
	            <li><a href="http://theme001.whalessoft.com/bbs/board.php?bo_table=gallery&amp;sca=%EC%B9%B4%ED%85%8C%EA%B3%A0%EB%A6%AC3">카테고리3</a></li>
	            <li><a href="http://theme001.whalessoft.com/bbs/board.php?bo_table=gallery&amp;sca=%EC%B9%B4%ED%85%8C%EA%B3%A0%EB%A6%AC4">카테고리4</a></li>
	        </ul>
	    </nav> -->
	
	    <form name="fboardlist" id="fboardlist" action="http://theme001.whalessoft.com/bbs/board_list_update.php" onsubmit="return fboardlist_submit(this);" method="post">
	        <input type="hidden" name="bo_table" value="gallery">
	        <input type="hidden" name="sfl" value="">
	        <input type="hidden" name="stx" value="">
	        <input type="hidden" name="spt" value="">
	        <input type="hidden" name="sst" value="wr_num, wr_reply">
	        <input type="hidden" name="sod" value="">
	        <input type="hidden" name="page" value="1">
	        <input type="hidden" name="sw" value="">
	
	        <!-- 게시판 페이지 정보 및 버튼 시작 { -->
	        <div id="bo_btn_top">
	            <div id="bo_list_total">
	                <span>Total 15건</span>
	                1 페이지
	            </div>
	
	            <ul class="btn_bo_user">
	                <li>
	                    <button type="button" class="btn_bo_sch btn_b01 btn" title="게시판 검색"><i class="fa fa-search" aria-hidden="true"></i><span class="sound_only">게시판 검색</span></button>
	                </li>
	            </ul>
	        </div>
	        <!-- } 게시판 페이지 정보 및 버튼 끝 -->
	
	
	        <ul id="gall_ul" class="gall_row">
	            <li class="gall_li col-gn-3">
	                <a href="http://theme001.whalessoft.com/bbs/board.php?bo_table=gallery&amp;wr_id=15" class="line-wrap">
	                    <div class="line-box"><span class="line line-top"></span><span class="line line-right"></span><span class="line line-bottom"></span><span class="line line-left"></span></div>
	                </a>
	                <div class="gall_box">
	                    <div class="gall_chk chk_box">
	                        <span class="sound_only">
	                            15 </span>
	                    </div>
	                    <div class="gall_con">
	                        <div class="gall_img">
	                            <a href="http://theme001.whalessoft.com/bbs/board.php?bo_table=gallery&amp;wr_id=15">
	                                <img src="http://theme001.whalessoft.com/data/file/gallery/thumb-7_copy_15_3_copy_7_3067531272_tUv6XPdg_bae5901543c31ab65cea6c44a2074372e56e9913_280x240.png" alt=""> </a>
	                        </div>
	                        <div class="gall_text_href">
	                            <!--<a href="http://theme001.whalessoft.com/bbs/board.php?bo_table=gallery&amp;sca=%EC%B9%B4%ED%85%8C%EA%B3%A0%EB%A6%AC1" class="bo_cate_link">카테고리1</a>-->
	                            <a href="http://theme001.whalessoft.com/bbs/board.php?bo_table=gallery&amp;wr_id=15" class="bo_tit">
	                                웨일즈소프트 신소프트 출시
	                                <span class="badge">자세히 보기</span>
	                            </a>
	                        </div>
	                    </div>
	                </div>
	            </li>
	            <li class="gall_li col-gn-3">
	                <a href="http://theme001.whalessoft.com/bbs/board.php?bo_table=gallery&amp;wr_id=14" class="line-wrap">
	                    <div class="line-box"><span class="line line-top"></span><span class="line line-right"></span><span class="line line-bottom"></span><span class="line line-left"></span></div>
	                </a>
	                <div class="gall_box">
	                    <div class="gall_chk chk_box">
	                        <span class="sound_only">
	                            14 </span>
	                    </div>
	                    <div class="gall_con">
	                        <div class="gall_img">
	                            <a href="http://theme001.whalessoft.com/bbs/board.php?bo_table=gallery&amp;wr_id=14">
	                                <img src="http://theme001.whalessoft.com/data/file/gallery/thumb-6_copy_14_2_copy_6_3067531272_zh0K2H3o_7759fab6959d6957254036dc218b7fd22f103175_280x240.png" alt=""> </a>
	                        </div>
	                        <div class="gall_text_href">
	                            <!--<a href="http://theme001.whalessoft.com/bbs/board.php?bo_table=gallery&amp;sca=%EC%B9%B4%ED%85%8C%EA%B3%A0%EB%A6%AC1" class="bo_cate_link">카테고리1</a>-->
	                            <a href="http://theme001.whalessoft.com/bbs/board.php?bo_table=gallery&amp;wr_id=14" class="bo_tit">
	                                제품을 소개합니다.
	                                <span class="badge">자세히 보기</span>
	                            </a>
	                        </div>
	                    </div>
	                </div>
	            </li>
	            <li class="gall_li col-gn-3">
	                <a href="http://theme001.whalessoft.com/bbs/board.php?bo_table=gallery&amp;wr_id=13" class="line-wrap">
	                    <div class="line-box"><span class="line line-top"></span><span class="line line-right"></span><span class="line line-bottom"></span><span class="line line-left"></span></div>
	                </a>
	                <div class="gall_box">
	                    <div class="gall_chk chk_box">
	                        <span class="sound_only">
	                            13 </span>
	                    </div>
	                    <div class="gall_con">
	                        <div class="gall_img">
	                            <a href="http://theme001.whalessoft.com/bbs/board.php?bo_table=gallery&amp;wr_id=13">
	                                <img src="http://theme001.whalessoft.com/data/file/gallery/thumb-5_copy_13_1_copy_5_3067531272_HrYkz7uJ_0d3a817cbf3883dc9a24ece672c59000c98e593f_280x240.png" alt=""> </a>
	                        </div>
	                        <div class="gall_text_href">
	                            <!--<a href="http://theme001.whalessoft.com/bbs/board.php?bo_table=gallery&amp;sca=%EC%B9%B4%ED%85%8C%EA%B3%A0%EB%A6%AC1" class="bo_cate_link">카테고리1</a>-->
	                            <a href="http://theme001.whalessoft.com/bbs/board.php?bo_table=gallery&amp;wr_id=13" class="bo_tit">
	                                웨일즈소프트 신소프트 출시
	                                <span class="badge">자세히 보기</span>
	                            </a>
	                        </div>
	                    </div>
	                </div>
	            </li>
	            <li class="gall_li col-gn-3 box_clear">
	                <a href="http://theme001.whalessoft.com/bbs/board.php?bo_table=gallery&amp;wr_id=12" class="line-wrap">
	                    <div class="line-box"><span class="line line-top"></span><span class="line line-right"></span><span class="line line-bottom"></span><span class="line line-left"></span></div>
	                </a>
	                <div class="gall_box">
	                    <div class="gall_chk chk_box">
	                        <span class="sound_only">
	                            12 </span>
	                    </div>
	                    <div class="gall_con">
	                        <div class="gall_img">
	                            <a href="http://theme001.whalessoft.com/bbs/board.php?bo_table=gallery&amp;wr_id=12">
	                                <img src="http://theme001.whalessoft.com/data/file/gallery/thumb-4_copy_12_3067531272_xJj7CK1v_b91ebd9ae01f94641820155a934a40b74d43c0c0_280x240.png" alt=""> </a>
	                        </div>
	                        <div class="gall_text_href">
	                            <!--<a href="http://theme001.whalessoft.com/bbs/board.php?bo_table=gallery&amp;sca=%EC%B9%B4%ED%85%8C%EA%B3%A0%EB%A6%AC1" class="bo_cate_link">카테고리1</a>-->
	                            <a href="http://theme001.whalessoft.com/bbs/board.php?bo_table=gallery&amp;wr_id=12" class="bo_tit">
	                                제품을 소개합니다.
	                                <span class="badge">자세히 보기</span>
	                            </a>
	                        </div>
	                    </div>
	                </div>
	            </li>
	            <li class="gall_li col-gn-3">
	                <a href="http://theme001.whalessoft.com/bbs/board.php?bo_table=gallery&amp;wr_id=11" class="line-wrap">
	                    <div class="line-box"><span class="line line-top"></span><span class="line line-right"></span><span class="line line-bottom"></span><span class="line line-left"></span></div>
	                </a>
	                <div class="gall_box">
	                    <div class="gall_chk chk_box">
	                        <span class="sound_only">
	                            11 </span>
	                    </div>
	                    <div class="gall_con">
	                        <div class="gall_img">
	                            <a href="http://theme001.whalessoft.com/bbs/board.php?bo_table=gallery&amp;wr_id=11">
	                                <img src="http://theme001.whalessoft.com/data/file/gallery/thumb-3_copy_11_3067531272_tUv6XPdg_bae5901543c31ab65cea6c44a2074372e56e9913_280x240.png" alt=""> </a>
	                        </div>
	                        <div class="gall_text_href">
	                            <!--<a href="http://theme001.whalessoft.com/bbs/board.php?bo_table=gallery&amp;sca=%EC%B9%B4%ED%85%8C%EA%B3%A0%EB%A6%AC1" class="bo_cate_link">카테고리1</a>-->
	                            <a href="http://theme001.whalessoft.com/bbs/board.php?bo_table=gallery&amp;wr_id=11" class="bo_tit">
	                                웨일즈소프트 신소프트 출시
	                                <span class="badge">자세히 보기</span>
	                            </a>
	                        </div>
	                    </div>
	                </div>
	            </li>
	            <li class="gall_li col-gn-3">
	                <a href="http://theme001.whalessoft.com/bbs/board.php?bo_table=gallery&amp;wr_id=10" class="line-wrap">
	                    <div class="line-box"><span class="line line-top"></span><span class="line line-right"></span><span class="line line-bottom"></span><span class="line line-left"></span></div>
	                </a>
	                <div class="gall_box">
	                    <div class="gall_chk chk_box">
	                        <span class="sound_only">
	                            10 </span>
	                    </div>
	                    <div class="gall_con">
	                        <div class="gall_img">
	                            <a href="http://theme001.whalessoft.com/bbs/board.php?bo_table=gallery&amp;wr_id=10">
	                                <img src="http://theme001.whalessoft.com/data/file/gallery/thumb-2_copy_10_3067531272_zh0K2H3o_7759fab6959d6957254036dc218b7fd22f103175_280x240.png" alt=""> </a>
	                        </div>
	                        <div class="gall_text_href">
	                            <!--<a href="http://theme001.whalessoft.com/bbs/board.php?bo_table=gallery&amp;sca=%EC%B9%B4%ED%85%8C%EA%B3%A0%EB%A6%AC1" class="bo_cate_link">카테고리1</a>-->
	                            <a href="http://theme001.whalessoft.com/bbs/board.php?bo_table=gallery&amp;wr_id=10" class="bo_tit">
	                                제품을 소개합니다.
	                                <span class="badge">자세히 보기</span>
	                            </a>
	                        </div>
	                    </div>
	                </div>
	            </li>
	            <li class="gall_li col-gn-3 box_clear">
	                <a href="http://theme001.whalessoft.com/bbs/board.php?bo_table=gallery&amp;wr_id=9" class="line-wrap">
	                    <div class="line-box"><span class="line line-top"></span><span class="line line-right"></span><span class="line line-bottom"></span><span class="line line-left"></span></div>
	                </a>
	                <div class="gall_box">
	                    <div class="gall_chk chk_box">
	                        <span class="sound_only">
	                            9 </span>
	                    </div>
	                    <div class="gall_con">
	                        <div class="gall_img">
	                            <a href="http://theme001.whalessoft.com/bbs/board.php?bo_table=gallery&amp;wr_id=9">
	                                <img src="http://theme001.whalessoft.com/data/file/gallery/thumb-1_copy_9_3067531272_HrYkz7uJ_0d3a817cbf3883dc9a24ece672c59000c98e593f_280x240.png" alt=""> </a>
	                        </div>
	                        <div class="gall_text_href">
	                            <!--<a href="http://theme001.whalessoft.com/bbs/board.php?bo_table=gallery&amp;sca=%EC%B9%B4%ED%85%8C%EA%B3%A0%EB%A6%AC1" class="bo_cate_link">카테고리1</a>-->
	                            <a href="http://theme001.whalessoft.com/bbs/board.php?bo_table=gallery&amp;wr_id=9" class="bo_tit">
	                                웨일즈소프트 신소프트 출시
	                                <span class="badge">자세히 보기</span>
	                            </a>
	                        </div>
	                    </div>
	                </div>
	            </li>
	            <li class="gall_li col-gn-3">
	                <a href="http://theme001.whalessoft.com/bbs/board.php?bo_table=gallery&amp;wr_id=8" class="line-wrap">
	                    <div class="line-box"><span class="line line-top"></span><span class="line line-right"></span><span class="line line-bottom"></span><span class="line line-left"></span></div>
	                </a>
	                <div class="gall_box">
	                    <div class="gall_chk chk_box">
	                        <span class="sound_only">
	                            8 </span>
	                    </div>
	                    <div class="gall_con">
	                        <div class="gall_img">
	                            <a href="http://theme001.whalessoft.com/bbs/board.php?bo_table=gallery&amp;wr_id=8">
	                                <img src="http://theme001.whalessoft.com/data/file/gallery/thumb-4_copy_8_3067531272_xJj7CK1v_b91ebd9ae01f94641820155a934a40b74d43c0c0_280x240.png" alt=""> </a>
	                        </div>
	                        <div class="gall_text_href">
	                            <a href="http://theme001.whalessoft.com/bbs/board.php?bo_table=gallery&amp;wr_id=8" class="bo_tit">
	                                제품을 소개합니다.
	                                <span class="badge">자세히 보기</span>
	                            </a>
	                        </div>
	                    </div>
	                </div>
	            </li>
	            <li class="gall_li col-gn-3">
	                <a href="http://theme001.whalessoft.com/bbs/board.php?bo_table=gallery&amp;wr_id=7" class="line-wrap">
	                    <div class="line-box"><span class="line line-top"></span><span class="line line-right"></span><span class="line line-bottom"></span><span class="line line-left"></span></div>
	                </a>
	                <div class="gall_box">
	                    <div class="gall_chk chk_box">
	                        <span class="sound_only">
	                            7 </span>
	                    </div>
	                    <div class="gall_con">
	                        <div class="gall_img">
	                            <a href="http://theme001.whalessoft.com/bbs/board.php?bo_table=gallery&amp;wr_id=7">
	                                <img src="http://theme001.whalessoft.com/data/file/gallery/thumb-3_copy_7_3067531272_tUv6XPdg_bae5901543c31ab65cea6c44a2074372e56e9913_280x240.png" alt=""> </a>
	                        </div>
	                        <div class="gall_text_href">
	                            <a href="http://theme001.whalessoft.com/bbs/board.php?bo_table=gallery&amp;wr_id=7" class="bo_tit">
	                                웨일즈소프트 신소프트 출시
	                                <span class="badge">자세히 보기</span>
	                            </a>
	                        </div>
	                    </div>
	                </div>
	            </li>
	            <li class="gall_li col-gn-3 box_clear">
	                <a href="http://theme001.whalessoft.com/bbs/board.php?bo_table=gallery&amp;wr_id=6" class="line-wrap">
	                    <div class="line-box"><span class="line line-top"></span><span class="line line-right"></span><span class="line line-bottom"></span><span class="line line-left"></span></div>
	                </a>
	                <div class="gall_box">
	                    <div class="gall_chk chk_box">
	                        <span class="sound_only">
	                            6 </span>
	                    </div>
	                    <div class="gall_con">
	                        <div class="gall_img">
	                            <a href="http://theme001.whalessoft.com/bbs/board.php?bo_table=gallery&amp;wr_id=6">
	                                <img src="http://theme001.whalessoft.com/data/file/gallery/thumb-2_copy_6_3067531272_zh0K2H3o_7759fab6959d6957254036dc218b7fd22f103175_280x240.png" alt=""> </a>
	                        </div>
	                        <div class="gall_text_href">
	                            <a href="http://theme001.whalessoft.com/bbs/board.php?bo_table=gallery&amp;wr_id=6" class="bo_tit">
	                                제품을 소개합니다.
	                                <span class="badge">자세히 보기</span>
	                            </a>
	                        </div>
	                    </div>
	                </div>
	            </li>
	            <li class="gall_li col-gn-3">
	                <a href="http://theme001.whalessoft.com/bbs/board.php?bo_table=gallery&amp;wr_id=5" class="line-wrap">
	                    <div class="line-box"><span class="line line-top"></span><span class="line line-right"></span><span class="line line-bottom"></span><span class="line line-left"></span></div>
	                </a>
	                <div class="gall_box">
	                    <div class="gall_chk chk_box">
	                        <span class="sound_only">
	                            5 </span>
	                    </div>
	                    <div class="gall_con">
	                        <div class="gall_img">
	                            <a href="http://theme001.whalessoft.com/bbs/board.php?bo_table=gallery&amp;wr_id=5">
	                                <img src="http://theme001.whalessoft.com/data/file/gallery/thumb-1_copy_5_3067531272_HrYkz7uJ_0d3a817cbf3883dc9a24ece672c59000c98e593f_280x240.png" alt=""> </a>
	                        </div>
	                        <div class="gall_text_href">
	                            <a href="http://theme001.whalessoft.com/bbs/board.php?bo_table=gallery&amp;wr_id=5" class="bo_tit">
	                                웨일즈소프트 신소프트 출시
	                                <span class="badge">자세히 보기</span>
	                            </a>
	                        </div>
	                    </div>
	                </div>
	            </li>
	            <li class="gall_li col-gn-3">
	                <a href="http://theme001.whalessoft.com/bbs/board.php?bo_table=gallery&amp;wr_id=4" class="line-wrap">
	                    <div class="line-box"><span class="line line-top"></span><span class="line line-right"></span><span class="line line-bottom"></span><span class="line line-left"></span></div>
	                </a>
	                <div class="gall_box">
	                    <div class="gall_chk chk_box">
	                        <span class="sound_only">
	                            4 </span>
	                    </div>
	                    <div class="gall_con">
	                        <div class="gall_img">
	                            <a href="http://theme001.whalessoft.com/bbs/board.php?bo_table=gallery&amp;wr_id=4">
	                                <img src="http://theme001.whalessoft.com/data/file/gallery/thumb-3067531272_xJj7CK1v_b91ebd9ae01f94641820155a934a40b74d43c0c0_280x240.png" alt=""> </a>
	                        </div>
	                        <div class="gall_text_href">
	                            <a href="http://theme001.whalessoft.com/bbs/board.php?bo_table=gallery&amp;wr_id=4" class="bo_tit">
	                                제품을 소개합니다.
	                                <span class="badge">자세히 보기</span>
	                            </a>
	                        </div>
	                    </div>
	                </div>
	            </li>
	            <li class="gall_li col-gn-3 box_clear">
	                <a href="http://theme001.whalessoft.com/bbs/board.php?bo_table=gallery&amp;wr_id=3" class="line-wrap">
	                    <div class="line-box"><span class="line line-top"></span><span class="line line-right"></span><span class="line line-bottom"></span><span class="line line-left"></span></div>
	                </a>
	                <div class="gall_box">
	                    <div class="gall_chk chk_box">
	                        <span class="sound_only">
	                            3 </span>
	                    </div>
	                    <div class="gall_con">
	                        <div class="gall_img">
	                            <a href="http://theme001.whalessoft.com/bbs/board.php?bo_table=gallery&amp;wr_id=3">
	                                <img src="http://theme001.whalessoft.com/data/file/gallery/thumb-3067531272_tUv6XPdg_bae5901543c31ab65cea6c44a2074372e56e9913_280x240.png" alt=""> </a>
	                        </div>
	                        <div class="gall_text_href">
	                            <!--<a href="http://theme001.whalessoft.com/bbs/board.php?bo_table=gallery&amp;sca=%EC%B9%B4%ED%85%8C%EA%B3%A0%EB%A6%AC3" class="bo_cate_link">카테고리3</a>-->
	                            <a href="http://theme001.whalessoft.com/bbs/board.php?bo_table=gallery&amp;wr_id=3" class="bo_tit">
	                                웨일즈소프트 신소프트 출시
	                                <span class="badge">자세히 보기</span>
	                            </a>
	                        </div>
	                    </div>
	                </div>
	            </li>
	            <li class="gall_li col-gn-3">
	                <a href="http://theme001.whalessoft.com/bbs/board.php?bo_table=gallery&amp;wr_id=2" class="line-wrap">
	                    <div class="line-box"><span class="line line-top"></span><span class="line line-right"></span><span class="line line-bottom"></span><span class="line line-left"></span></div>
	                </a>
	                <div class="gall_box">
	                    <div class="gall_chk chk_box">
	                        <span class="sound_only">
	                            2 </span>
	                    </div>
	                    <div class="gall_con">
	                        <div class="gall_img">
	                            <a href="http://theme001.whalessoft.com/bbs/board.php?bo_table=gallery&amp;wr_id=2">
	                                <img src="http://theme001.whalessoft.com/data/file/gallery/thumb-3067531272_zh0K2H3o_7759fab6959d6957254036dc218b7fd22f103175_280x240.png" alt=""> </a>
	                        </div>
	                        <div class="gall_text_href">
	                            <!--<a href="http://theme001.whalessoft.com/bbs/board.php?bo_table=gallery&amp;sca=%EC%B9%B4%ED%85%8C%EA%B3%A0%EB%A6%AC2" class="bo_cate_link">카테고리2</a>-->
	                            <a href="http://theme001.whalessoft.com/bbs/board.php?bo_table=gallery&amp;wr_id=2" class="bo_tit">
	                                제품을 소개합니다.
	                                <span class="badge">자세히 보기</span>
	                            </a>
	                        </div>
	                    </div>
	                </div>
	            </li>
	            <li class="gall_li col-gn-3">
	                <a href="http://theme001.whalessoft.com/bbs/board.php?bo_table=gallery&amp;wr_id=1" class="line-wrap">
	                    <div class="line-box"><span class="line line-top"></span><span class="line line-right"></span><span class="line line-bottom"></span><span class="line line-left"></span></div>
	                </a>
	                <div class="gall_box">
	                    <div class="gall_chk chk_box">
	                        <span class="sound_only">
	                            1 </span>
	                    </div>
	                    <div class="gall_con">
	                        <div class="gall_img">
	                            <a href="http://theme001.whalessoft.com/bbs/board.php?bo_table=gallery&amp;wr_id=1">
	                                <img src="http://theme001.whalessoft.com/data/file/gallery/thumb-3067531272_HrYkz7uJ_0d3a817cbf3883dc9a24ece672c59000c98e593f_280x240.png" alt=""> </a>
	                        </div>
	                        <div class="gall_text_href">
	                            <!--<a href="http://theme001.whalessoft.com/bbs/board.php?bo_table=gallery&amp;sca=%EC%B9%B4%ED%85%8C%EA%B3%A0%EB%A6%AC4" class="bo_cate_link">카테고리4</a>-->
	                            <a href="http://theme001.whalessoft.com/bbs/board.php?bo_table=gallery&amp;wr_id=1" class="bo_tit">
	                                웨일즈소프트 신소프트 출시
	                                <span class="badge">자세히 보기</span>
	                            </a>
	                        </div>
	                    </div>
	                </div>
	            </li>
	        </ul>
	
	        <!-- 페이지 -->
	        <!-- 페이지 -->
	    </form>
	
	    <!-- 게시판 검색 시작 { -->
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