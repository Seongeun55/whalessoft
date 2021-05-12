package egovframework.com.cmm.web;

import java.util.ArrayList;
import java.util.HashMap;

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
import java.util.Map;
import java.util.Map.Entry;

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

import org.h2.mvstore.DataUtils;
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
		mainBanner(model);
		
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
	
	/*[추가] 일반 페이지 이동메소드 - 2021.04.02*/
	@RequestMapping(value = "/content.do")
	public String content(@RequestParam("id") String id, @RequestParam("menuNo") int menuNo, HttpSession session, ModelMap model) throws Exception {
		
		String link = "egovframework/com/web/content/"+id;

		if (id==null || id.equals("")){
			link="egovframework/com/admin/cmm/error/egovError";
		}
		
		menu(model);
		subBanner(model);
		selectedMenu(model, menuNo);

		return link;
	}
	
	/*[추가] boardList 이동메소드 - 2021.04.15*/
	@RequestMapping(value = "/board/list.do")
	public String boardList(@RequestParam("bbsId") String bbsId, @RequestParam("menuNo") int menuNo, ModelMap model) throws Exception {
			 
		if (bbsId==null || bbsId.equals("")){
			return "egovframework/com/admin/cmm/error/egovError";
		}

		menu(model);
		subBanner(model);
		selectedMenu(model, menuNo);
		
		return "forward:/cop/bbs/selectArticleList.do";		
	}
	
	/*[추가] boardView 이동메소드 - 2021.04.15*/
	@RequestMapping(value = "/board/view.do")
	public String boardView(@RequestParam("bbsId") String bbsId, @RequestParam("menuNo") int menuNo, ModelMap model) throws Exception {
			 
		if (bbsId==null || bbsId.equals("")){
			return "egovframework/com/admin/cmm/error/egovError";
		}

		menu(model);		
		subBanner(model);		
		selectedMenu(model, menuNo);
		
		return "forward:/cop/bbs/selectArticleDetail.do";
	}
	
	/*[추가] boardWrite 이동메소드 - 2021.04.15*/
	@RequestMapping(value = "/board/write.do")
	public String boardWrite(@RequestParam("bbsId") String bbsId, @RequestParam("menuNo") int menuNo, ModelMap model) throws Exception {
			 
		if (bbsId==null || bbsId.equals("")){
			return "egovframework/com/admin/cmm/error/egovError";
		}

		menu(model);
		subBanner(model);
		selectedMenu(model, menuNo);
		
		return "forward:/cop/bbs/insertArticleView.do";
	}
	
	/*[추가] boardModify 이동메소드 - 2021.04.15*/
	@RequestMapping(value = "/board/modify.do")
	public String boardModify(@RequestParam("bbsId") String bbsId, @RequestParam("menuNo") int menuNo, ModelMap model) throws Exception {
			 
		if (bbsId==null || bbsId.equals("")){
			return "egovframework/com/admin/cmm/error/egovError";
		}

		menu(model);
		subBanner(model);
		selectedMenu(model, menuNo);
		
		return "";
	}
	
	/*[추가] boardDelete 이동메소드 - 2021.04.15*/
	@RequestMapping(value = "/board/delete.do")
	public String boardDelete(@RequestParam("bbsId") String bbsId, @RequestParam("menuNo") int menuNo, ModelMap model) throws Exception {
			 
		if (bbsId==null || bbsId.equals("")){
			return "egovframework/com/admin/cmm/error/egovError";
		}

		menu(model);
		subBanner(model);
		selectedMenu(model, menuNo);
		
		return "";
	}
	
	/**********************************************************baord끝, Q&A 시작*****************************************************************/
	
	/*[추가] qnaList 이동메소드 - 2021.04.16*/
	@RequestMapping(value = "/qna/list.do")
	public String qnaList(@RequestParam("menuNo") int menuNo, HttpServletRequest request, @ModelAttribute("searchVO") QnaVO qnaVO, ModelMap model) throws Exception {
		
		menu(model);
		subBanner(model);
		selectedMenu(model, menuNo);
		this.checkSession(request);
		
		return "forward:/uss/olh/qna/selectQnaList.do";
	}
	
	/*[추가] qnaView 이동메소드 - 2021.04.16*/
	@RequestMapping(value = "/qna/view.do")
	public String qnaView(@RequestParam("menuNo") int menuNo, HttpServletRequest request, @ModelAttribute("searchVO") QnaVO qnaVO, ModelMap model) throws Exception {

		menu(model);
		subBanner(model);
		selectedMenu(model, menuNo);
		
		return "forward:/uss/olh/qna/selectQnaDetail.do";
	}
	
	/*[추가] qnaWrite 이동메소드 - 2021.04.15*/
	@RequestMapping(value = "/qna/write.do")
	public String qnaWrite(@RequestParam("menuNo") int menuNo, @ModelAttribute("searchVO") QnaVO qnaVO, ModelMap model) throws Exception {
	
		menu(model);
		subBanner(model);
		selectedMenu(model, menuNo);
		
		return "forward:/uss/olh/qna/insertQnaView.do";
	
	}
	
	/*[추가] qnaModify 이동메소드 - 2021.04.15*/
	@RequestMapping(value = "/qna/modify.do")
	public String qnaModify(@RequestParam("menuNo") int menuNo, @ModelAttribute("searchVO") QnaVO qnaVO, ModelMap model) throws Exception {
			 
		menu(model);
		subBanner(model);
		selectedMenu(model, menuNo);
		
		return "forward:/uss/olh/qna/updateQnaView.do";
	}
	
	/*[추가] qnaDelete 이동메소드 - 2021.04.15*/
	@RequestMapping(value = "/qna/delete.do")
	public String qnaDelete(@RequestParam("menuNo") int menuNo, @ModelAttribute("searchVO") QnaVO qnaVO, ModelMap model) throws Exception {

		menu(model);
		subBanner(model);
		selectedMenu(model, menuNo);
		
		return "forward:/uss/olh/qna/deleteQna.do";
	}
	
	/**********************************************************Q&A 끝, FAQ 시작*****************************************************************/
	
	/*[추가] faqList 이동메소드 - 2021.04.19*/
	@RequestMapping(value = "/faq/list.do")
	public String faqList(@RequestParam("menuNo") int menuNo, HttpServletRequest request, @ModelAttribute("searchVO") QnaVO qnaVO, ModelMap model) throws Exception {
		
		menu(model);
		subBanner(model);
		selectedMenu(model, menuNo);
		
		return "forward:/uss/olh/faq/selectFaqList.do";
	}
	
	/**********************************************************FAQ 끝*****************************************************************/
	
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
		
		List<?> mainMenuList = menuManageService.selectMainMenu(menuManageVO);
		model.addAttribute("mainMenuList", mainMenuList);	// 큰 타이틀만 들어옴
		List<?> subMenuList = menuManageService.selectSubMenu(menuManageVO);
		model.addAttribute("subMenuList", subMenuList);	// 서브메뉴
	}
	
	/** [추가] 선택된 메뉴값의 세로메뉴들을 뽑아내기 위해 - 2021.05.10 **/
	public void selectedMenu(ModelMap model, int menuNo) throws Exception{

		List<Map<String, Object>> mainMenuMap = (List<Map<String, Object>>) model.get("mainMenuList");
		List<Map<String, Object>> subMenuMap = (List<Map<String, Object>>) model.get("subMenuList");
		HashMap<String, Object> selectedMainMenuMap = new HashMap<>();
		List<Map<String,Object>> selectedSubMenuListMap = new ArrayList<>();
		
		int mainMenuNo = -1;
		
		for(int i=0; i<mainMenuMap.size(); i++) {
			int num = Integer.parseInt(mainMenuMap.get(i).get("menuNo").toString());			
			if(menuNo == num) {				
				mainMenuNo = menuNo;				
				break;
			}
		}
		
		if(mainMenuNo == -1) {
			for(int i=0; i<subMenuMap.size(); i++) {
				int num = Integer.parseInt(subMenuMap.get(i).get("menuNo").toString());	
				if(menuNo == num) {
					mainMenuNo = Integer.parseInt(subMenuMap.get(i).get("upperMenuId").toString());					
					break;
				}			
			}
		}
		
		for(int i=0; i<mainMenuMap.size(); i++) {
			int num = Integer.parseInt(mainMenuMap.get(i).get("menuNo").toString());
			if(mainMenuNo == num) {
				for(Entry<String, Object> map : mainMenuMap.get(i).entrySet()) {
					selectedMainMenuMap.put(map.getKey(), map.getValue());
				}
				break; 
			}
		}
		
		for(int i=0; i<subMenuMap.size(); i++) {
			int upperMenuId = Integer.parseInt(subMenuMap.get(i).get("upperMenuId").toString());			
			if(mainMenuNo != upperMenuId) continue; 
			HashMap<String, Object> selectedMap = new HashMap<>();
			for(Entry<String, Object> map : subMenuMap.get(i).entrySet()) {
				selectedMap.put(map.getKey(), map.getValue());				
			} 
			selectedSubMenuListMap.add(selectedMap);
		}	
		
		model.addAttribute("selectedMainMenuMap", selectedMainMenuMap);
		model.addAttribute("selectedSubMenuListMap", selectedSubMenuListMap);
		model.addAttribute("mainMenuNo", mainMenuNo);
		model.addAttribute("menuNo", menuNo);
	}
	
	/** [추가] main.do에 있던 부분을 분리 - 2021.04.14 **/
	public void mainBanner(ModelMap model) throws Exception{
		
		BannerVO bannerVO = new BannerVO();
		
		PaginationInfo paginationInfoBanner = new PaginationInfo();
		paginationInfoBanner.setCurrentPageNo(bannerVO.getPageIndex());
		paginationInfoBanner.setRecordCountPerPage(bannerVO.getPageUnit());
		paginationInfoBanner.setPageSize(bannerVO.getPageSize());
		
		bannerVO.setFirstIndex(paginationInfoBanner.getFirstRecordIndex());
		bannerVO.setLastIndex(paginationInfoBanner.getLastRecordIndex());
		bannerVO.setRecordCountPerPage(paginationInfoBanner.getRecordCountPerPage());
		bannerVO.setBannerList(BannerService.selectMainBannerResult(bannerVO));
		
		model.addAttribute("bannerList", bannerVO.getBannerList());
	}
	
	/** [추가] 서브페이지 배너를 위해 추가 - 2021.05.07 **/
	public void subBanner(ModelMap model) throws Exception{
		
		BannerVO bannerVO = new BannerVO();
		
		PaginationInfo paginationInfoBanner = new PaginationInfo();
		paginationInfoBanner.setCurrentPageNo(bannerVO.getPageIndex());
		paginationInfoBanner.setRecordCountPerPage(bannerVO.getPageUnit());
		paginationInfoBanner.setPageSize(bannerVO.getPageSize());
		
		bannerVO.setFirstIndex(paginationInfoBanner.getFirstRecordIndex());
		bannerVO.setLastIndex(paginationInfoBanner.getLastRecordIndex());
		bannerVO.setRecordCountPerPage(paginationInfoBanner.getRecordCountPerPage());
		bannerVO.setBannerList(BannerService.selectSubBannerResult(bannerVO));
		
		model.addAttribute("subBannerList", bannerVO.getBannerList());
	}
	
	/** [추가] 주소로 바로 접근 불가능하게 하기위해 추가 - 2021.05.03 **/
	public void checkSession(HttpServletRequest request) {
		ComUtlController utl = new ComUtlController();
		StringBuffer random = utl.random();
		HttpSession session = request.getSession();
		session.setAttribute("_access_", random);
		request.setAttribute("_access_", session.getAttribute("_access_"));
	}
}
