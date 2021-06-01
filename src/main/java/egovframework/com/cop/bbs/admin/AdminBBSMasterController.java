package egovframework.com.cop.bbs.admin;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springmodules.validation.commons.DefaultBeanValidator;

import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.EgovComponentChecker;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.annotation.IncludedInfo;
import egovframework.com.cmm.service.CmmUseService;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.cop.bbs.service.Blog;
import egovframework.com.cop.bbs.service.BlogUserVO;
import egovframework.com.cop.bbs.service.BlogVO;
import egovframework.com.cop.bbs.service.BoardMaster;
import egovframework.com.cop.bbs.service.BoardMasterVO;
import egovframework.com.cop.tpl.service.TemplateInfVO;
import egovframework.com.cop.tpl.service.TemplateManageService;
import egovframework.com.cop.bbs.service.BBSMasterService;
import egovframework.com.utl.fcc.service.EgovStringUtil;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/**
 * 게시판 속성관리를 위한 컨트롤러 클래스
 * 
 * @author 공통서비스개발팀 이삼섭
 * @since 2009.06.01
 * @version 1.0
 * @see
 *
 *      <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------       --------    ---------------------------
 *   2009.3.12  이삼섭          최초 생성
 *   2009.06.26	한성곤		    2단계 기능 추가 (댓글관리, 만족도조사)
 *	 2011.07.21 안민정          커뮤니티 관련 메소드 분리 (->EgovBBSAttributeManageController)
 *	 2011.8.26	정진오			IncludedInfo annotation 추가
 *   2011.09.15 서준식           2단계 기능 추가 (댓글관리, 만족도조사) 적용방법 변경
 *   2016.06.13 김연호          표준프레임워크 v3.6 개선
 *      </pre>
 */

@Controller
public class AdminBBSMasterController {

	@Resource(name = "BBSMasterService")
	private BBSMasterService BBSMasterService;

	@Resource(name = "CmmUseService")
	private CmmUseService cmmUseService;

	@Resource(name = "propertiesService")
	protected EgovPropertyService propertyService;

	@Resource(name = "egovBBSMstrIdGnrService")
	private EgovIdGnrService idgenServiceBbs;

	@Resource(name = "egovBlogIdGnrService")
	private EgovIdGnrService idgenServiceBlog;

	@Resource(name = "TemplateManageService")
	private TemplateManageService tmplatService;
	
	/** EgovMessageSource */
	@Resource(name = "egovMessageSource")
	EgovMessageSource egovMessageSource;

	@Autowired
	private DefaultBeanValidator beanValidator;

	// Logger log = Logger.getLogger(this.getClass());

	/**
	 * 신규 게시판 마스터 등록을 위한 등록페이지로 이동한다.
	 * 
	 * @param boardMasterVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/cop/bbs/insertBBSMasterView.do")
	public String insertBBSMasterView(@ModelAttribute("searchVO") BoardMasterVO boardMasterVO, ModelMap model) throws Exception {
		BoardMasterVO boardMaster = new BoardMasterVO();
		// 공통코드(게시판유형)
		ComDefaultCodeVO vo = new ComDefaultCodeVO();
		vo.setCodeId("COM101");
		List<?> codeResult = cmmUseService.selectCmmCodeDetail(vo);

		model.addAttribute("bbsTyCode", codeResult);
		model.addAttribute("boardMasterVO", boardMaster);

		//추가
		TemplateInfVO tmplatInfVO = new TemplateInfVO();
		Map<String, Object> map = tmplatService.selectTemplate(tmplatInfVO);	
		model.addAttribute("resultList", map.get("resultList"));
		
		// ---------------------------------
		// 2011.09.15 : 2단계 기능 추가 반영 방법 변경
		// ---------------------------------

		if (EgovComponentChecker.hasComponent("ArticleCommentService")) {
			model.addAttribute("useComment", "true");
		}
		if (EgovComponentChecker.hasComponent("BBSSatisfactionService")) {
			model.addAttribute("useSatisfaction", "true");
		}

		return "egovframework/com/admin/cop/bbs/BBSMasterRegist";
	}

	/**
	 * 신규 게시판 마스터 정보를 등록한다.
	 * 
	 * @param boardMasterVO
	 * @param boardMaster
	 * @param status
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/cop/bbs/insertBBSMaster.do")
	public String insertBBSMaster(@ModelAttribute("searchVO") BoardMasterVO boardMasterVO, @ModelAttribute("boardMaster") BoardMaster boardMaster, 
			BindingResult bindingResult, ModelMap model) throws Exception {

		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

		beanValidator.validate(boardMaster, bindingResult);
		if (bindingResult.hasErrors()) {
			ComDefaultCodeVO vo = new ComDefaultCodeVO();

			// 게시판유형코드
			vo.setCodeId("COM101");
			List<?> codeResult = cmmUseService.selectCmmCodeDetail(vo);
			model.addAttribute("bbsTyCode", codeResult);

			return "egovframework/com/admin/cop/bbs/BBSMasterRegist";
		}

		if (isAuthenticated) {
			boardMaster.setFrstRegisterId(user == null ? "" : EgovStringUtil.isNullToString(user.getUniqId()));
			if ((boardMasterVO == null ? "" : EgovStringUtil.isNullToString(boardMasterVO.getBlogAt())).equals("Y")) {
				boardMaster.setBlogAt("Y");
			} else {
				boardMaster.setBlogAt("N");
			}
			BBSMasterService.insertBBSMasterInf(boardMaster);
		}
		if (boardMaster.getBlogAt().equals("Y")) {
			return "forward:/cop/bbs/selectArticleBlogList.do";
		} else {
			return "forward:/admin/cop/bbs/selectBBSMasterInfs.do";
		}

	}

	/**
	 * 게시판 마스터 목록을 조회한다.
	 * 
	 * @param boardMasterVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@IncludedInfo(name = "게시판관리", order = 180, gid = 40)
	@RequestMapping("/admin/cop/bbs/selectBBSMasterInfs.do")
	public String selectBBSMasterInfs(@ModelAttribute("searchVO") BoardMasterVO boardMasterVO, ModelMap model) throws Exception {
		boardMasterVO.setPageUnit(propertyService.getInt("pageUnit"));
		boardMasterVO.setPageSize(propertyService.getInt("pageSize"));

		PaginationInfo paginationInfo = new PaginationInfo();

		paginationInfo.setCurrentPageNo(boardMasterVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(boardMasterVO.getPageUnit());
		paginationInfo.setPageSize(boardMasterVO.getPageSize());

		boardMasterVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		boardMasterVO.setLastIndex(paginationInfo.getLastRecordIndex());
		boardMasterVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		Map<String, Object> map = BBSMasterService.selectBBSMasterInfs(boardMasterVO);
		int totCnt = Integer.parseInt((String) map.get("resultCnt"));

		paginationInfo.setTotalRecordCount(totCnt);

		model.addAttribute("resultList", map.get("resultList"));
		model.addAttribute("resultCnt", map.get("resultCnt"));
		model.addAttribute("paginationInfo", paginationInfo);

		return "egovframework/com/admin/cop/bbs/BBSMasterList";
	}

	/**
	 * 블로그에 대한 목록을 조회한다.
	 * 
	 * @param blogVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@IncludedInfo(name = "블로그관리", order = 170, gid = 40)
	@RequestMapping("/cop/bbs/selectBlogList.do")
	public String selectBlogMasterList(@ModelAttribute("searchVO") BoardMasterVO boardMasterVO, ModelMap model) throws Exception {

		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		// KISA 보안취약점 조치 (2018-12-10, 신용호)
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

		if (!isAuthenticated) {
			return "forward:/uat/uia/LoginUsr.do";
		}

		boardMasterVO.setPageUnit(propertyService.getInt("pageUnit"));
		boardMasterVO.setPageSize(propertyService.getInt("pageSize"));

		PaginationInfo paginationInfo = new PaginationInfo();

		paginationInfo.setCurrentPageNo(boardMasterVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(boardMasterVO.getPageUnit());
		paginationInfo.setPageSize(boardMasterVO.getPageSize());

		boardMasterVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		boardMasterVO.setLastIndex(paginationInfo.getLastRecordIndex());
		boardMasterVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		boardMasterVO.setFrstRegisterId(user == null ? "" : EgovStringUtil.isNullToString(user.getUniqId()));

		Map<String, Object> map = BBSMasterService.selectBlogMasterInfs(boardMasterVO);
		int totCnt = Integer.parseInt((String) map.get("resultCnt"));

		paginationInfo.setTotalRecordCount(totCnt);

		model.addAttribute("resultList", map.get("resultList"));
		model.addAttribute("resultCnt", map.get("resultCnt"));
		model.addAttribute("paginationInfo", paginationInfo);

		return "egovframework/com/admin/cop/bbs/EgovBlogList";
	}

	/**
	 * 블로그 등록을 위한 등록페이지로 이동한다.
	 * 
	 * @param blogVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/cop/bbs/insertBlogMasterView.do")
	public String insertBlogMasterView(@ModelAttribute("searchVO") BlogVO blogVO, ModelMap model) throws Exception {
		model.addAttribute("blogMasterVO", new BlogVO());
		return "egovframework/com/admin/cop/bbs/EgovBlogRegist";
	}

	/**
	 * 블로그 생성 유무를 판단한다.
	 * 
	 * @param blogVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/cop/bbs/selectChkBloguser.do")
	public ModelAndView chkBlogUser(@ModelAttribute("searchVO") BlogVO blogVO, ModelMap model) throws Exception {
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		// KISA 보안취약점 조치 (2018-12-10, 신용호)
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

		if (!isAuthenticated) {
			throw new IllegalAccessException("Login Required!");
		}

		model.addAttribute("blogMasterVO", new BlogVO());

		String userVal = "";
		blogVO.setFrstRegisterId(user == null ? "" : EgovStringUtil.isNullToString(user.getUniqId()));
		userVal = BBSMasterService.checkBlogUser(blogVO);

		ModelAndView mav = new ModelAndView("jsonView");
		mav.addObject("userChk", userVal);
		return mav;
	}

	/**
	 * 블로그 정보를 등록한다.
	 * 
	 * @param blogVO
	 * @param blog
	 * @param status
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/cop/bbs/insertBlogMaster.do")
	public String insertBlogMaster(@ModelAttribute("searchVO") BlogVO blogVO, @ModelAttribute("blogMaster") Blog blog,
			BindingResult bindingResult, ModelMap model) throws Exception {

		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

		if (!isAuthenticated) { // KISA 보안약점 조치 (2018-12-10, 신용호)
			return "forward:/uat/uia/LoginUsr.do";
		}

		blogVO.setFrstRegisterId(user == null ? "" : EgovStringUtil.isNullToString(user.getUniqId()));
		BlogVO vo = BBSMasterService.checkBlogUser2(blogVO);

		if (vo != null) {
			model.addAttribute("blogMasterVO", new BlogVO());
			model.addAttribute("message", egovMessageSource.getMessage("comCopBlog.validate.blogUserCheck"));
			return "egovframework/com/admin/cop/bbs/EgovBlogRegist";
		}

		beanValidator.validate(blog, bindingResult);

		if (bindingResult.hasErrors()) {
			return "egovframework/com/admin/cop/bbs/EgovBlogRegist";
		}

		String blogId = idgenServiceBlog.getNextStringId(); // 블로그 아이디 채번
		String bbsId = idgenServiceBbs.getNextStringId(); // 게시판 아이디 채번

		blog.setRegistSeCode("REGC02");
		blog.setFrstRegisterId(user == null ? "" : EgovStringUtil.isNullToString(user.getUniqId()));
		blog.setBbsId(bbsId);
		blog.setBlogId(blogId);
		blog.setBlogAt("Y");
		BBSMasterService.insertBlogMaster(blog);

		if (isAuthenticated) {
			// 블로그 개설자의 정보를 등록한다.
			BlogUserVO blogUserVO = new BlogUserVO();
			blogUserVO.setBlogId(blogId);
			blogUserVO.setEmplyrId(user == null ? "" : EgovStringUtil.isNullToString(user.getUniqId()));
			blogUserVO.setMngrAt("Y");
			blogUserVO.setMberSttus("P");
			blogUserVO.setUseAt("Y");
			blogUserVO.setFrstRegisterId(user == null ? "" : EgovStringUtil.isNullToString(user.getUniqId()));

			BBSMasterService.insertBoardBlogUserRqst(blogUserVO);
		}
		return "forward:/cop/bbs/selectBlogList.do";
	}

	/**
	 * 게시판 마스터 상세내용을 조회한다.
	 * 
	 * @param boardMasterVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/cop/bbs/selectBBSMasterDetail.do")
	public String selectBBSMasterDetail(@ModelAttribute("searchVO") BoardMasterVO searchVO, ModelMap model) throws Exception {
		BoardMasterVO vo = BBSMasterService.selectBBSMasterInf(searchVO);
		String resultList = "";
		String resultWrite = "";
		String resultRead = "";
		String resultComment = "";
		
		if(vo.getAuthList().equals("GUE")) resultList = "비회원";
		else if (vo.getAuthList().equals("GNR")) resultList = "회원";
		else if (vo.getAuthList().equals("USR")) resultList = "관리자";
		
		if(vo.getAuthWrite().equals("GUE")) resultWrite = "비회원";
		else if (vo.getAuthWrite().equals("GNR")) resultWrite = "회원";
		else if (vo.getAuthWrite().equals("USR")) resultWrite = "관리자";
		
		if(vo.getAuthRead().equals("GUE")) resultRead = "비회원";
		else if (vo.getAuthRead().equals("GNR")) resultRead = "회원";
		else if (vo.getAuthRead().equals("USR")) resultRead = "관리자";
		
		if(vo.getAuthComment().equals("GUE")) resultComment = "비회원";
		else if (vo.getAuthComment().equals("GNR"))	resultComment = "회원";
		else if (vo.getAuthComment().equals("USR")) resultComment = "관리자";
		
		model.addAttribute("resultList", resultList);
		model.addAttribute("resultWrite", resultWrite);
		model.addAttribute("resultRead", resultRead);
		model.addAttribute("resultComment", resultComment);
		model.addAttribute("result", vo);

		// ---------------------------------
		// 2011.09.15 : 2단계 기능 추가 반영 방법 변경
		// ---------------------------------

		if (EgovComponentChecker.hasComponent("ArticleCommentService")) {
			model.addAttribute("useComment", "true");
		}
		if (EgovComponentChecker.hasComponent("BBSSatisfactionService")) {
			model.addAttribute("useSatisfaction", "true");
		}

		return "egovframework/com/admin/cop/bbs/BBSMasterDetail";
	}

	/**
	 * 게시판 마스터정보를 수정하기 위한 전 처리
	 * 
	 * @param bbsId
	 * @param searchVO
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/cop/bbs/updateBBSMasterView.do")
	public String updateBBSMasterView(@RequestParam("bbsId") String bbsId, @ModelAttribute("searchVO") BoardMaster searchVO, ModelMap model) throws Exception {

		BoardMasterVO boardMasterVO = new BoardMasterVO();

		// 게시판유형코드
		ComDefaultCodeVO vo = new ComDefaultCodeVO();
		vo.setCodeId("COM101");
		List<?> codeResult = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("bbsTyCode", codeResult);
		
		// [추가] 템플릿 종류
		TemplateInfVO tmplatInfVO = new TemplateInfVO();
		Map<String, Object> map = tmplatService.selectTemplate(tmplatInfVO);	
		model.addAttribute("resultList", map.get("resultList"));

		// Primary Key 값 세팅
		boardMasterVO.setBbsId(bbsId);

		model.addAttribute("boardMasterVO", BBSMasterService.selectBBSMasterInf(boardMasterVO));

		// ---------------------------------
		// 2011.09.15 : 2단계 기능 추가 반영 방법 변경
		// ---------------------------------

		if (EgovComponentChecker.hasComponent("ArticleCommentService")) {
			model.addAttribute("useComment", "true");
		}
		if (EgovComponentChecker.hasComponent("BBSSatisfactionService")) {
			model.addAttribute("useSatisfaction", "true");
		}

		return "egovframework/com/admin/cop/bbs/EgovBBSMasterUpdt";
	}

	/**
	 * 게시판 마스터 정보를 수정한다.
	 * 
	 * @param boardMasterVO
	 * @param boardMaster
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/cop/bbs/updateBBSMaster.do")
	public String updateBBSMaster(@ModelAttribute("searchVO") BoardMasterVO boardMasterVO,
			@ModelAttribute("boardMaster") BoardMaster boardMaster, BindingResult bindingResult, ModelMap model)
			throws Exception {

		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

		beanValidator.validate(boardMaster, bindingResult);
		if (bindingResult.hasErrors()) {
			BoardMasterVO vo = BBSMasterService.selectBBSMasterInf(boardMasterVO);

			model.addAttribute("result", vo);

			ComDefaultCodeVO comVo = new ComDefaultCodeVO();
			comVo.setCodeId("COM101");
			List<?> codeResult = cmmUseService.selectCmmCodeDetail(comVo);
			model.addAttribute("bbsTyCode", codeResult);

			return "egovframework/com/admin/cop/bbs/EgovBBSMasterUpdt";
		}

		if (isAuthenticated) {
			boardMaster.setLastUpdusrId(user == null ? "" : EgovStringUtil.isNullToString(user.getUniqId()));
			BBSMasterService.updateBBSMasterInf(boardMaster);
		}

		return "forward:/admin/cop/bbs/selectBBSMasterInfs.do";
	}

	/**
	 * 게시판 마스터 정보를 삭제한다.
	 * 
	 * @param boardMasterVO
	 * @param boardMaster
	 * @param status
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/cop/bbs/deleteBBSMaster.do")
	public String deleteBBSMaster(@ModelAttribute("searchVO") BoardMasterVO boardMasterVO,
			@ModelAttribute("boardMaster") BoardMaster boardMaster) throws Exception {

		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

		if (isAuthenticated) {
			boardMaster.setLastUpdusrId(user == null ? "" : EgovStringUtil.isNullToString(user.getUniqId()));
			BBSMasterService.deleteBBSMasterInf(boardMaster);
		}
		// status.setComplete();
		return "forward:/admin/cop/bbs/selectBBSMasterInfs.do";
	}

	/**
	 * 포트릿을 위한 블로그 목록 정보를 조회한다.
	 * 
	 * @param blogVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/cop/bbs/selectBlogListPortlet.do")
	public String selectBlogListPortlet(@ModelAttribute("searchVO") BlogVO blogVO, ModelMap model) throws Exception {
		List<BlogVO> result = BBSMasterService.selectBlogListPortlet(blogVO);

		model.addAttribute("resultList", result);

		return "egovframework/com/admin/cop/bbs/EgovBlogListPortlet";
	}

	/**
	 * 포트릿을 위한 게시판 목록 정보를 조회한다.
	 * 
	 * @param blogVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/cop/bbs/selectBBSListPortlet.do")
	public String selectBBSListPortlet(@ModelAttribute("searchVO") BoardMasterVO boardMasterVO, ModelMap model)
			throws Exception {
		List<BoardMasterVO> result = BBSMasterService.selectBBSListPortlet(boardMasterVO);

		model.addAttribute("resultList", result);

		return "egovframework/com/admin/cop/bbs/EgovBBSListPortlet";
	}

}
