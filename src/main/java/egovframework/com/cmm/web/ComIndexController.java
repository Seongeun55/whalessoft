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


import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.sym.mnu.mpm.service.MenuManageService;
import egovframework.com.sym.mnu.mpm.service.MenuManageVO;
import egovframework.com.uss.ion.bnr.service.BannerService;
import egovframework.com.uss.ion.bnr.service.BannerVO;
import egovframework.com.uss.ion.pwm.service.PopupManageService;
import egovframework.com.uss.ion.pwm.service.PopupManageVO;
import egovframework.com.uss.olh.faq.service.FaqService;
import egovframework.com.uss.olh.faq.service.FaqVO;
import egovframework.com.uss.olh.qna.service.QnaService;
import egovframework.com.uss.olh.qna.service.QnaVO;
import egovframework.com.utl.fcc.service.EgovStringUtil;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ComIndexController implements ApplicationContextAware, InitializingBean {

	private ApplicationContext applicationContext;
	
	/** [추가] **/
	@Resource(name = "BannerService")
	private BannerService BannerService;
	
	@Resource(name = "meunManageService")
	private MenuManageService menuManageService;

	@Resource(name = "PopupManageService")
	private PopupManageService PopupManageService;
	
	@Resource(name = "QnaService")
	private QnaService QnaService;
	
	@Resource(name = "FaqService")
	private FaqService FaqService;
	
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;

	private static final Logger LOGGER = LoggerFactory.getLogger(ComIndexController.class);

	public void afterPropertiesSet() throws Exception {}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
		
		LOGGER.info("ComIndexController setApplicationContext method has called!");
	}

	@RequestMapping("/main.do")
	public String index(HttpServletRequest request, ModelMap model) throws Exception{
		
		//[추가] 메인화면에 팝업창 -2021.03.31
		PopupManageVO popupManageVO = new PopupManageVO();
		List<?> resultList = PopupManageService.selectPopupMainList(popupManageVO);
		model.addAttribute("resultList", resultList);
		
		menu(model);
		banner(model);
		
		return "egovframework/com/web/main";
	}

	@RequestMapping("/AdminContent.do")		//수정 - EgovContent.do -> AdminContent.do
	public String adminContent(ModelMap model) {

		LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		model.addAttribute("loginVO", loginVO);
		return "egovframework/com/admin/cmm/AdminMainContent";
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
	
	/*[추가] jsp페이지 이동메소드 - 2021.04.02*/
	@RequestMapping(value = "/content.do")
	public String content(@RequestParam("id") String id, HttpSession session, ModelMap model) throws Exception {
		
		String link = "egovframework/com/web/content/"+id;

		if (id==null || id.equals("")){
			link="egovframework/com/admin/cmm/error/egovError";
		}
		
		menu(model);
		
		return link;
	}
	
	/*[추가] jsp페이지 이동메소드 - 2021.04.06*/
	@RequestMapping(value = "/board.do")
	public String boardList(@RequestParam("id") String id, @ModelAttribute("searchVO") QnaVO qnaVO, @ModelAttribute("faqVO") FaqVO faqVO, HttpSession session,  ModelMap model) throws Exception {
		String link = "egovframework/com/web/board/"+id;
		
		// service 사용하여 리턴할 결과값 처리하는 부분은 생략하고 단순 페이지 링크만 처리함
		if (id==null || id.equals("")){
			link="egovframework/com/admin/cmm/error/egovError";
		}
		
		/**[추가] Q&A목록을 불러오기위해 - 2021.04.08**/
		if(id.equals("page9")) {						
			/** EgovPropertyService.SiteList */
			qnaVO.setPageUnit(propertiesService.getInt("pageUnit"));
			qnaVO.setPageSize(propertiesService.getInt("pageSize"));

			/** pageing */
			PaginationInfo paginationInfo = new PaginationInfo();
			paginationInfo.setCurrentPageNo(qnaVO.getPageIndex());		
			paginationInfo.setRecordCountPerPage(qnaVO.getPageUnit());
			paginationInfo.setPageSize(qnaVO.getPageSize());

			qnaVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
			qnaVO.setLastIndex(paginationInfo.getLastRecordIndex());
			qnaVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

			List<?> QnaList = QnaService.selectQnaList(qnaVO);
			model.addAttribute("resultList", QnaList);

			// 인증여부 체크
			Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

			if (!isAuthenticated) {
				model.addAttribute("certificationAt", "N");
			} else {
				model.addAttribute("certificationAt", "Y");
			}

			int totCnt = QnaService.selectQnaListCnt(qnaVO);
			paginationInfo.setTotalRecordCount(totCnt);
			model.addAttribute("paginationInfo", paginationInfo);
			model.addAttribute("searchVO", qnaVO);
		}
		
		if(id.equals("page10")) {		
			/** EgovPropertyService.SiteList */
			faqVO.setPageUnit(propertiesService.getInt("pageUnit"));
			faqVO.setPageSize(propertiesService.getInt("pageSize"));

			/** pageing */
			PaginationInfo paginationInfo = new PaginationInfo();
			paginationInfo.setCurrentPageNo(faqVO.getPageIndex());
			paginationInfo.setRecordCountPerPage(faqVO.getPageUnit());
			paginationInfo.setPageSize(faqVO.getPageSize());

			faqVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
			faqVO.setLastIndex(paginationInfo.getLastRecordIndex());
			faqVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

			List<?> FaqList = FaqService.selectFaqList(faqVO);
			model.addAttribute("resultList", FaqList);

			int totCnt = FaqService.selectFaqListCnt(faqVO);
			paginationInfo.setTotalRecordCount(totCnt);
			model.addAttribute("paginationInfo", paginationInfo);	
		}

		menu(model);
		return link;
	}
	
	/** [추가] main.do에 있던 부분을 분리 - 2021.04.07 **/
	public void menu(ModelMap model) throws Exception{
		
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
		menuManageVO.setTmpUniqId(user == null ? "USRCNFRM_00000000001" : EgovStringUtil.isNullToString(user.getUniqId()));
		
		List<?> list_headmenu = menuManageService.selectMainMenuHead(menuManageVO);
		model.addAttribute("list_headmenu", list_headmenu);	// 큰 타이틀만 들어옴
		List<?> list_submenu = menuManageService.selectSubMenu(menuManageVO);
		model.addAttribute("list_submenu", list_submenu);	// 서브메뉴
	}
	
	/** [추가] main.do에 있던 부분을 분리 - 2021.04.14 **/
	public void banner(ModelMap model) throws Exception{
		
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
	}
	
}
