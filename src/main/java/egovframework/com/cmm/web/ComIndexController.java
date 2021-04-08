package egovframework.com.cmm.web;

/**
 * 컴포넌트 설치 후 설치된 컴포넌트들을 IncludedInfo annotation을 통해 찾아낸 후
 * 화면에 표시할 정보를 처리하는 Controller 클래스
 * <Notice>
 * 		개발시 메뉴 구조가 잡히기 전에 배포파일들에 포함된 공통 컴포넌트들의 목록성 화면에
 * 		URL을 제공하여 개발자가 편하게 활용하도록 하기 위해 작성된 것으로,
 * 		실제 운영되는 시스템에서는 적용해서는 안 됨
 *      실 운영 시에는 삭제해서 배포해도 좋음
 * <Disclaimer>
 * 		운영시에 본 컨트롤을 사용하여 메뉴를 구성하는 경우 성능 문제를 일으키거나
 * 		사용자별 메뉴 구성에 오류를 발생할 수 있음
 * @author 공통컴포넌트 정진오
 * @since 2011.08.26
 * @version 2.0.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *  수정일		  수정자		수정내용
 *  ----------   --------   ---------------------------
 *  2011.08.26   정진오            최초 생성
 *  2011.09.16   서준식            컨텐츠 페이지 생성
 *  2011.09.26     이기하		header, footer 페이지 생성
 *  2019.12.04   신용호            KISA 보안코드 점검 : Map<Integer, IncludedCompInfoVO> map를 지역변수로 수정
 * </pre>
 */

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import egovframework.com.cmm.IncludedCompInfoVO;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.annotation.IncludedInfo;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.sym.mnu.mpm.service.MenuManageService;
import egovframework.com.sym.mnu.mpm.service.MenuManageVO;
import egovframework.com.uss.ion.bnr.service.BannerService;
import egovframework.com.uss.ion.bnr.service.BannerVO;
import egovframework.com.uss.ion.pwm.service.PopupManageService;
import egovframework.com.uss.ion.pwm.service.PopupManageVO;
import egovframework.com.utl.fcc.service.EgovStringUtil;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ComIndexController implements ApplicationContextAware, InitializingBean {

	private ApplicationContext applicationContext;
	 
	@Resource(name = "BannerService")
	private BannerService BannerService;
	
	/** MenuManageService */
	@Resource(name = "meunManageService")
	private MenuManageService menuManageService;

	/** PopupManageService */
	@Resource(name = "PopupManageService")
	private PopupManageService PopupManageService;

	private static final Logger LOGGER = LoggerFactory.getLogger(ComIndexController.class);

	public void afterPropertiesSet() throws Exception {}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
		
		LOGGER.info("ComIndexController setApplicationContext method has called!");
	}

	@RequestMapping("/main.do")
	public String index(HttpServletRequest request, ModelMap model) throws Exception{
	
		//[추가] 메인 배너 컨텐츠 조회 시작 ---------------------------------2021.03.26
		BannerVO bannerVO = new BannerVO();
	
		PaginationInfo paginationInfo_Banner = new PaginationInfo();
		paginationInfo_Banner.setCurrentPageNo(bannerVO.getPageIndex());
		paginationInfo_Banner.setRecordCountPerPage(bannerVO.getPageUnit());
		paginationInfo_Banner.setPageSize(bannerVO.getPageSize());
		bannerVO.setFirstIndex(paginationInfo_Banner.getFirstRecordIndex());
		bannerVO.setLastIndex(paginationInfo_Banner.getLastRecordIndex());
		bannerVO.setRecordCountPerPage(paginationInfo_Banner.getRecordCountPerPage());
		bannerVO.setBannerList(BannerService.selectBannerResult(bannerVO));
		model.addAttribute("bannerList", bannerVO.getBannerList());
		// 메인 배너 컨텐츠 조회 끝 ---------------------------------
		
		//[추가] 메인화면에 팝업창 -2021.03.31
		PopupManageVO popupManageVO = new PopupManageVO();
		List<?> resultList = PopupManageService.selectPopupMainList(popupManageVO);
		model.addAttribute("resultList", resultList);
		
		header(model);
		
		return "egovframework/com/web/main";
	}

	@RequestMapping("/EgovTop.do")
	public String top() {
		return "egovframework/com/admin/cmm/EgovUnitTop";
	}

	@RequestMapping("/EgovBottom.do")
	public String bottom() {
		return "egovframework/com/admin/cmm/EgovUnitBottom";
	}

	@RequestMapping("/AdminContent.do")		//수정 - EgovContent.do -> AdminContent.do
	public String setContent(ModelMap model) {

		LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		model.addAttribute("loginVO", loginVO);
		return "egovframework/com/admin/cmm/AdminMainContent";
	}

	@RequestMapping("/EgovLeft.do")
	public String setLeftMenu(ModelMap model) {

		Map<Integer, IncludedCompInfoVO> map = new TreeMap<Integer, IncludedCompInfoVO>();
		RequestMapping rmAnnotation;
		IncludedInfo annotation;
		IncludedCompInfoVO zooVO;

		/*
		 * EgovLoginController가 AOP Proxy되는 바람에 클래스를 reflection으로 가져올 수 없음
		 */
		try {
			Class<?> loginController = Class.forName("egovframework.com.uat.uia.web.LoginController");
			Method[] methods = loginController.getMethods();
			for (int i = 0; i < methods.length; i++) {
				annotation = methods[i].getAnnotation(IncludedInfo.class);

				if (annotation != null) {
					LOGGER.debug("Found @IncludedInfo Method : {}", methods[i]);
					zooVO = new IncludedCompInfoVO();
					zooVO.setName(annotation.name());
					zooVO.setOrder(annotation.order());
					zooVO.setGid(annotation.gid());

					rmAnnotation = methods[i].getAnnotation(RequestMapping.class);
					if ("".equals(annotation.listUrl()) && rmAnnotation != null) {
						zooVO.setListUrl(rmAnnotation.value()[0]);
					} else {
						zooVO.setListUrl(annotation.listUrl());
					}
					map.put(zooVO.getOrder(), zooVO);
				}
			}
		} catch (ClassNotFoundException e) {
			LOGGER.error("No egovframework.com.uat.uia.web.LoginController!!");
		}
		/* 여기까지 AOP Proxy로 인한 코드 */

		/*@Controller Annotation 처리된 클래스를 모두 찾는다.*/
		Map<String, Object> myZoos = applicationContext.getBeansWithAnnotation(Controller.class);
		LOGGER.debug("How many Controllers : ", myZoos.size());
		for (final Object myZoo : myZoos.values()) {
			Class<? extends Object> zooClass = myZoo.getClass();

			Method[] methods = zooClass.getMethods();
			LOGGER.debug("Controller Detected {}", zooClass);
			for (int i = 0; i < methods.length; i++) {
				annotation = methods[i].getAnnotation(IncludedInfo.class);

				if (annotation != null) {
					//LOG.debug("Found @IncludedInfo Method : " + methods[i] );
					zooVO = new IncludedCompInfoVO();
					zooVO.setName(annotation.name());
					zooVO.setOrder(annotation.order());
					zooVO.setGid(annotation.gid());
					/*
					 * 목록형 조회를 위한 url 매핑은 @IncludedInfo나 @RequestMapping에서 가져온다
					 */
					rmAnnotation = methods[i].getAnnotation(RequestMapping.class);
					if ("".equals(annotation.listUrl())) {
						zooVO.setListUrl(rmAnnotation.value()[0]);
					} else {
						zooVO.setListUrl(annotation.listUrl());
					}

					map.put(zooVO.getOrder(), zooVO);
				}
			}
		}

		model.addAttribute("resultList", map.values());
		
		LOGGER.debug("ComIndexController index is called ");

		return "egovframework/com/admin/cmm/EgovUnitLeft";
	}
	//-----------------------------------추가--------------------------------------------------//
	
	@RequestMapping("/admin/index.do")
	public String adminIndex(ModelMap model) {
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		String url = "egovframework/com/admin/cmm/error/accessDenied";
		if(user.getUserSe().equals("USR")) {
			return "egovframework/com/admin/admin";
		}
		return url;
	}
	
	/** [추가] main.do에 있던 부분을 분리 - 2021.04.07 **/
	public void header(ModelMap model) throws Exception{
		//[추가] 메인화면에 메뉴리스트 -2021.03.31
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		MenuManageVO menuManageVO = new MenuManageVO();
		
		//[추가] 메인화면에 메뉴리스트 -2021.04.06
		menuManageVO.setTmpId(user == null ? "" : EgovStringUtil.isNullToString(user.getId()));
		menuManageVO.setTmpPassword(user == null ? "" : EgovStringUtil.isNullToString(user.getPassword()));
		menuManageVO.setTmpUserSe(user == null ? "" : EgovStringUtil.isNullToString(user.getUserSe()));
		menuManageVO.setTmpName(user == null ? "" : EgovStringUtil.isNullToString(user.getName()));
		menuManageVO.setTmpEmail(user == null ? "" : EgovStringUtil.isNullToString(user.getEmail()));
		menuManageVO.setTmpOrgnztId(user == null ? "" : EgovStringUtil.isNullToString(user.getOrgnztId()));
		menuManageVO.setTmpUniqId(user == null ? "USRCNFRM_99999999999" : EgovStringUtil.isNullToString(user.getUniqId()));

		List<?> list_headmenu = menuManageService.selectMainMenuHead(menuManageVO);
		model.addAttribute("list_headmenu", list_headmenu);	// 큰 타이틀만 들어옴
		List<?> list_submenu = menuManageService.selectSubMenu(menuManageVO);
		model.addAttribute("list_submenu", list_submenu);	// 서브메뉴
	}
	
}
