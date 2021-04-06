$(function(){
	var visual = $('.visual_wrap .visual'),
		visualDots = $('.visual_wrap .dots > li');
	
	$('.wideslick').slick({
		arrows:true,
		fade:true,
		autoplay:true,
		autoplaySpeed:5000,
		speed:3000,
		pauseOnFocus:false,
		pauseOnHover:false,
	});
	
	// 비주얼 slick과 dots연동
	visualDots.on('click',function(){
		var i = $(this).index();
		visual.slick('slickGoTo', i);
	});
	visual.on('afterChange', function(){
		var i = visual.slick('slickCurrentSlide');
		visualDots.removeClass('on').eq(i).addClass('on');
	});
	//info 슬릭
	$('.autoslick').slick({
		arrows:false,
		autoplay:true,
		autoplaySpeed:5000,
		speed:3000,
		pauseOnFocus:false,
		pauseOnHover:false,
	});
	//admin 슬릭
	$('.admin_slick').slick({
		arrows:true,
		infinite: true,
		slidesToShow: 3,
		slidesToScroll: 3,
		pauseOnFocus:false,
		pauseOnHover:false,
		responsive: [
			{
			  breakpoint:769,
			  settings: {
				slidesToShow: 2,
				slidesToScroll: 2,
			  }
			},
			{
			  breakpoint:640,
			  settings: {
				slidesToShow: 1,
				slidesToScroll: 1,
			  }
			}
			// instead of a settings object
		  ]
	});
	//tab 구현
	var tabWrap = $('.tab_wrap');
	tabWrap.each(function(){
		var thisTab = $(this).find('.tab li'),
			thisCon = $(this).find('.tab_con > div');

		thisTab.on('click', function(){
			var i = $(this).index();
			thisTab.removeClass('on').eq(i).addClass('on');
			thisCon.hide().eq(i).show();
		});
	thisTab.eq(0).trigger('click');
	});
	//메뉴 버튼 
	var allMenu = $('header .allmenu'),
		$nav = $('nav');

	allMenu.on('click',function(){
		if ($nav.is('.on')){
			$nav.removeClass('on');
		}else{
			$nav.addClass('on');
		}
	});

	//하단 화살표 클릭시 맨위로
	var topBtn = $('footer .to_top');

	topBtn.on('click', function(){
		$('html, body').animate({ 
			scrollTop: 0 
		}, 500);
	});

	//하단 화살표 footer에서 클래스명 넣기
	var footPs = $(document).height() - $(window).height(),
		scrollYN = false;
	
	$(window).scroll(function(){
		var scrT = $(window).scrollTop();

		if (scrT > $(document).height() - $(window).height() - 200){
			topBtn.addClass('ft_position');
		}else{
			topBtn.removeClass('ft_position');
		}

		if (scrT > $('.different').offset().top - 300){
			if (scrollYn == false){
				countAni();
				scrollYn = true;
			}else{
				return false;
			}
		}
	});

	//different 카운트
	function countAni(){
		$('.counter').counterUp({
			delay: 10,
			time: 1000
		});
	}countAni();

	//메뉴 오버시 하위메뉴 노출
	var $win = $(window);
		$nav = $('header nav')
		menuBg = $('.menu_bg'),
		menuList = $nav.find('.depth01 > li');
		depthTwo = $nav.find('.depth02');
	//pc메뉴
	$nav.on({
		'mouseenter':function(){
			if ($win.width() < 769) return false;
			menuBg.stop().slideDown();
			depthTwo.stop().slideDown();
		},
		'mouseleave':function(){
			if ($win.width() < 769) return false;
			menuBg.stop().slideUp()
			depthTwo.stop().slideUp()
		}
	});
	//mobile 메뉴
	menuList.on('click', function(){
		if ($win.width() < 769){
			var thisDept02 = $(this).find('.depth02'),
				thisSiblings = $(this).siblings();
	
			if ($(this).is('.on')){ 
				$(this).removeClass('on');
				thisDept02.stop().slideUp();
			}else{
				$(this).addClass('on');
				thisDept02.stop().slideDown();
				thisSiblings.removeClass('on').find('.depth02').slideUp();
			}
		}
	});
	
	$win.on('resize', function(){
		if ($win.width() > 768){
			depthTwo.stop().hide();
			menuList.removeClass('on');
		}
	});
});	