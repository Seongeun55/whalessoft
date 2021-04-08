package egovframework.com.cmm.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import egovframework.com.cmm.EgovWebUtil;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.sym.mnu.mpm.service.MenuManageService;
import egovframework.com.uss.olh.faq.service.FaqService;
import egovframework.com.uss.olh.faq.service.FaqVO;
import egovframework.com.uss.olh.qna.service.QnaService;
import egovframework.com.sym.mnu.mpm.service.MenuManageVO;
import egovframework.com.uss.olh.qna.service.QnaVO;
import egovframework.com.utl.fcc.service.EgovStringUtil;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/**
 * @Class Name : EgovComUtlController.java
 * @Description : 공통유틸리티성 작업을 위한 Controller
 * @Modification Information
 * @
 * @ 수정일              수정자          수정내용
 * @ ----------  --------  ---------------------------
 *   2009.03.02  조재영          최초 생성
 *   2011.10.07  이기하          .action -> .do로 변경하면서 동일 매핑이 되어 삭제처리
 *   2015.11.12  김연호          한국인터넷진흥원 웹 취약점 개선
 *   2019.04.25  신용호          moveToPage() 화이트리스트 처리
 *
 *  @author 공통서비스 개발팀 조재영
 *  @since 2009.03.02
 *  @version 1.0
 *  @see
 *
 */
@Controller
public class ComUtlController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ComUtlController.class);
	
	@Resource(name = "egovPageLinkWhitelist")
    protected List<String> egovWhitelist;

    /** EgovPropertyService */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;
	
	/** MenuManageService */
	@Resource(name = "meunManageService")
	private MenuManageService menuManageService;
	
	/**[추가] 2021.04.08**/
	@Resource(name = "QnaService")
	private QnaService QnaService;
	
	@Resource(name = "FaqService")
	private FaqService FaqService;
	
    /**
	 * JSP 호출작업만 처리하는 공통 함수
	 */
	@RequestMapping(value="/PageLink.do")
	public String moveToPage(@RequestParam("link") String linkPage){
		String link = linkPage;
		link = link.replace(";", "");
		link = link.replace(".", "");
		
		// service 사용하여 리턴할 결과값 처리하는 부분은 생략하고 단순 페이지 링크만 처리함
		if (linkPage==null || linkPage.equals("")){
			link="egovframework/com/cmm/egovError";
		}
		
		// 화이트 리스트 처리
		// whitelist목록에 있는 경우 결과가 true, 결과가 false인경우 FAIL처리
		if (egovWhitelist.contains(linkPage) == false) {
			LOGGER.debug("Page Link WhiteList Error! Please check whitelist!");
			link="egovframework/com/cmm/egovError";
		}
		
		// 안전한 경로 문자열로 조치
		link = EgovWebUtil.filePathBlackList(link);
		
		return link;
	}
	
	/*[추가] jsp페이지 이동메소드 - 2021.04.02*/
	@RequestMapping(value = "/content.do")
	public String moveToContent(@RequestParam("id") String id, HttpSession session, ModelMap model) throws Exception {
		String link = "egovframework/com/web/content/"+id;
		
		// service 사용하여 리턴할 결과값 처리하는 부분은 생략하고 단순 페이지 링크만 처리함
		if (id==null || id.equals("")){
			link="egovframework/com/admin/cmm/error/egovError";
		}
		
		header(model);
		
		return link;
	}
	
	/*[추가] jsp페이지 이동메소드 - 2021.04.06*/
	@RequestMapping(value = "/board.do")
	public String moveToboard(@RequestParam("id") String id, HttpSession session,  ModelMap model) throws Exception {
		String link = "egovframework/com/web/board/"+id;
		
		// service 사용하여 리턴할 결과값 처리하는 부분은 생략하고 단순 페이지 링크만 처리함
		if (id==null || id.equals("")){
			link="egovframework/com/admin/cmm/error/egovError";
		}
		
		/**[추가] Q&A목록을 불러오기위해 - 2021.04.08**/
		if(id.equals("page9")) {			
			QnaVO searchVO = new QnaVO();
			
			/** EgovPropertyService.SiteList */
			searchVO.setPageUnit(propertiesService.getInt("pageUnit"));
			searchVO.setPageSize(propertiesService.getInt("pageSize"));

			/** pageing */
			PaginationInfo paginationInfo = new PaginationInfo();
			paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
			paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
			paginationInfo.setPageSize(searchVO.getPageSize());

			searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
			searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
			searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

			List<?> QnaList = QnaService.selectQnaList(searchVO);
			model.addAttribute("resultList", QnaList);

			// 인증여부 체크
			Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

			if (!isAuthenticated) {
				model.addAttribute("certificationAt", "N");
			} else {
				model.addAttribute("certificationAt", "Y");
			}

			int totCnt = QnaService.selectQnaListCnt(searchVO);
			paginationInfo.setTotalRecordCount(totCnt);
			model.addAttribute("paginationInfo", paginationInfo);
			model.addAttribute("searchVO", searchVO);
			header(model);
			return link;
		}
		
		if(id.equals("page10")) {
			FaqVO searchVO = new FaqVO();
			/** EgovPropertyService.SiteList */
			searchVO.setPageUnit(propertiesService.getInt("pageUnit"));
			searchVO.setPageSize(propertiesService.getInt("pageSize"));

			/** pageing */
			PaginationInfo paginationInfo = new PaginationInfo();
			paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
			paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
			paginationInfo.setPageSize(searchVO.getPageSize());

			searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
			searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
			searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

			List<?> FaqList = FaqService.selectFaqList(searchVO);
			model.addAttribute("resultList", FaqList);

			int totCnt = FaqService.selectFaqListCnt(searchVO);
			paginationInfo.setTotalRecordCount(totCnt);
			model.addAttribute("paginationInfo", paginationInfo);

			header(model);		
			return link;
		}
		
		header(model);		
		return link;
	}
	
	/** [추가] ComIndexController.java에 있는 부분을 가져옴(불러오기가 안돼서) - 2021.04.08 **/
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
		menuManageVO.setTmpUniqId(user == null ? "USRCNFRM_00000000001" : EgovStringUtil.isNullToString(user.getUniqId()));

		List<?> list_headmenu = menuManageService.selectMainMenuHead(menuManageVO);
		model.addAttribute("list_headmenu", list_headmenu);	// 큰 타이틀만 들어옴
		List<?> list_submenu = menuManageService.selectSubMenu(menuManageVO);
		model.addAttribute("list_submenu", list_submenu);	// 서브메뉴
	}
	
    /**
	 * 모달조회
	 * @return String
	 * @exception Exception
	 */
    @RequestMapping(value="/EgovModal.do")
    public String selectUtlJsonInquire()  throws Exception {
        return "egovframework/com/admin/cmm/EgovModal";
    }
    
    /**
	 * validato rule dynamic Javascript
	 */
	@RequestMapping("/validator.do")
	public String validate(){
		return "egovframework/com/admin/cmm/validator";
	}

}