package egovframework.com.cop.bbs.web;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springmodules.validation.commons.DefaultBeanValidator;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.EgovWebUtil;
import egovframework.com.cmm.LoginVO;

import egovframework.com.cmm.service.FileMngService;
import egovframework.com.cmm.service.EgovFileMngUtil;
import egovframework.com.cmm.service.FileVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.cmm.util.EgovXssChecker;
import egovframework.com.cop.bbs.service.BlogVO;
import egovframework.com.cop.bbs.service.Board;
import egovframework.com.cop.bbs.service.BoardMaster;
import egovframework.com.cop.bbs.service.BoardMasterVO;
import egovframework.com.cop.bbs.service.BoardVO;
import egovframework.com.cop.bbs.service.ArticleService;
import egovframework.com.cop.bbs.service.BBSMasterService;
import egovframework.com.cop.bbs.service.BBSSatisfactionService;
import egovframework.com.cop.cmt.service.CommentVO;
import egovframework.com.cop.cmt.service.ArticleCommentService;
import egovframework.com.cop.tpl.service.TemplateManageService;
import egovframework.com.cop.tpl.service.TemplateInfVO;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.com.utl.fcc.service.EgovStringUtil;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/**
 * 게시물 관리를 위한 컨트롤러 클래스
 * 
 * @author 공통서비스개발팀 이삼섭
 * @since 2009.06.01
 * @version 1.0
 * @see
 *
 *      <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일              수정자          수정내용
 *  ----------   -------   ---------------------------
 *  2009.03.19   이삼섭          최초 생성
 *  2009.06.29   한성곤          2단계 기능 추가 (댓글관리, 만족도조사)
 *  2011.07.01   안민정          댓글, 스크랩, 만족도 조사 기능의 종속성 제거
 *  2011.08.26   정진오          IncludedInfo annotation 추가
 *  2011.09.07   서준식          유효 게시판 게시일 지나도 게시물이 조회되던 오류 수정
 *  2016.06.13   김연호          표준프레임워크 3.6 개선
 *  2019.05.17   신용호          KISA 취약점 조치 및 보완
 *      </pre>
 */

@Controller
public class ArticleController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ArticleController.class);

	@Resource(name = "ArticleService")
	private ArticleService ArticleService;

	@Resource(name = "BBSMasterService")
	private BBSMasterService BBSMasterService;

	@Resource(name = "FileMngService")
	private FileMngService fileMngService;

	@Resource(name = "EgovFileMngUtil")
	private EgovFileMngUtil fileUtil;

	@Resource(name = "propertiesService")
	protected EgovPropertyService propertyService;

	@Resource(name = "egovMessageSource")
	EgovMessageSource egovMessageSource;

	@Resource(name = "ArticleCommentService")
	protected ArticleCommentService ArticleCommentService;

	@Resource(name = "BBSSatisfactionService")
	private BBSSatisfactionService bbsSatisfactionService;

	@Resource(name = "TemplateManageService")
	private TemplateManageService TemplateManageService;

	@Autowired
	private DefaultBeanValidator beanValidator;

	// protected Logger log = Logger.getLogger(this.getClass());

	/**
	 * XSS 방지 처리.
	 * 
	 * @param data
	 * @return
	 */
	protected String unscript(String data) {
		if (data == null || data.trim().equals("")) {
			return "";
		}

		String ret = data;

		ret = ret.replaceAll("<(S|s)(C|c)(R|r)(I|i)(P|p)(T|t)", "&lt;script");
		ret = ret.replaceAll("</(S|s)(C|c)(R|r)(I|i)(P|p)(T|t)", "&lt;/script");

		ret = ret.replaceAll("<(O|o)(B|b)(J|j)(E|e)(C|c)(T|t)", "&lt;object");
		ret = ret.replaceAll("</(O|o)(B|b)(J|j)(E|e)(C|c)(T|t)", "&lt;/object");

		ret = ret.replaceAll("<(A|a)(P|p)(P|p)(L|l)(E|e)(T|t)", "&lt;applet");
		ret = ret.replaceAll("</(A|a)(P|p)(P|p)(L|l)(E|e)(T|t)", "&lt;/applet");

		ret = ret.replaceAll("<(E|e)(M|m)(B|b)(E|e)(D|d)", "&lt;embed");
		ret = ret.replaceAll("</(E|e)(M|m)(B|b)(E|e)(D|d)", "&lt;embed");

		ret = ret.replaceAll("<(F|f)(O|o)(R|r)(M|m)", "&lt;form");
		ret = ret.replaceAll("</(F|f)(O|o)(R|r)(M|m)", "&lt;form");

		return ret;
	}

	/**
	 * 관리자 페이지의 게시물에 대한 목록을 조회한다.
	 * 
	 * @param boardVO
	 * @param sessionVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/admin/cop/bbs/selectArticleList.do")
	public String selectArticleList(@ModelAttribute("searchVO") BoardVO boardVO, ModelMap model) throws Exception {
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated(); // KISA 보안취약점 조치 (2018-12-10, 이정은)

		if (!isAuthenticated) {
			return "forward:/uat/uia/LoginUsr.do";
		}

		BoardMasterVO vo = new BoardMasterVO();

		vo.setBbsId(boardVO.getBbsId());
		vo.setUniqId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
		BoardMasterVO master = BBSMasterService.selectBBSMasterInf(vo);

		// 방명록은 방명록 게시판으로 이동
		if (master.getBbsTyCode().equals("BBST03")) {
			return "forward:/cop/bbs/selectGuestArticleList.do";
		}

		boardVO.setPageUnit(propertyService.getInt("pageUnit"));
		boardVO.setPageSize(propertyService.getInt("pageSize"));

		PaginationInfo paginationInfo = new PaginationInfo();

		paginationInfo.setCurrentPageNo(boardVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(boardVO.getPageUnit());
		paginationInfo.setPageSize(boardVO.getPageSize());

		boardVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		boardVO.setLastIndex(paginationInfo.getLastRecordIndex());
		boardVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		Map<String, Object> map = ArticleService.selectArticleList(boardVO);
		int totCnt = Integer.parseInt((String) map.get("resultCnt"));

		// 공지사항 추출
		List<BoardVO> noticeList = ArticleService.selectNoticeArticleList(boardVO);

		paginationInfo.setTotalRecordCount(totCnt);

		// -------------------------------
		// 기본 BBS template 지정
		// -------------------------------
		if (master.getTmplatCours() == null || master.getTmplatCours().equals("")) {
			master.setTmplatCours("/css/egovframework/com/cop/tpl/egovBaseTemplate.css");
		}
		//// -----------------------------

		if (user != null) {
			model.addAttribute("sessionUniqId", user.getUniqId());
		}

		model.addAttribute("resultList", map.get("resultList"));
		model.addAttribute("resultCnt", map.get("resultCnt"));
		model.addAttribute("articleVO", boardVO);
		model.addAttribute("boardMasterVO", master);
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("noticeList", noticeList);
		return "egovframework/com/admin/cop/bbs/ArticleList";
	}
	
	/**
	 * 페이지의 게시물에 대한 목록을 조회한다.
	 * 
	 * @param boardVO
	 * @param sessionVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/cop/bbs/selectArticleList.do")
	public String ArticleList(@ModelAttribute("searchVO") BoardVO boardVO, ModelMap model) throws Exception {
		
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated(); 
		
		BoardMasterVO vo = new BoardMasterVO();

		vo.setBbsId(boardVO.getBbsId());
		vo.setUniqId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
		BoardMasterVO master = BBSMasterService.selectBBSMasterInf(vo);

		if(!master.getAuthList().equals("GUE")) { // 접근 권한 비회원 이상(회원 & 관리자)			
			if (!isAuthenticated ) { // 로그인이 안되어 있을 경우
				return "forward:/uat/uia/LoginUsr.do";
			}
			if(!master.getAuthList().equals(user.getUserSe()) && !user.getUserSe().equals("USR")) { // 관리자가 아니고 회원 권한이 맞지 않을 경우
				return "egovframework/com/admin/cmm/error/accessDenied";
			}
		}
		
		/* 방명록은 방명록 게시판으로 이동
		if (master.getBbsTyCode().equals("BBST03")) {
			return "forward:/cop/bbs/selectGuestArticleList.do";
		}*/

		boardVO.setPageUnit(propertyService.getInt("pageUnit"));
		boardVO.setPageSize(propertyService.getInt("pageSize"));

		PaginationInfo paginationInfo = new PaginationInfo();

		paginationInfo.setCurrentPageNo(boardVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(boardVO.getPageUnit());
		paginationInfo.setPageSize(boardVO.getPageSize());

		boardVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		boardVO.setLastIndex(paginationInfo.getLastRecordIndex());
		boardVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		Map<String, Object> map = ArticleService.selectArticleList(boardVO);
		int totCnt = Integer.parseInt((String) map.get("resultCnt"));

		// 공지사항 추출
		List<BoardVO> noticeList = ArticleService.selectNoticeArticleList(boardVO);

		paginationInfo.setTotalRecordCount(totCnt);

		// -------------------------------
		// 기본 BBS template 지정
		// -------------------------------
		if (master.getTmplatCours() == null || master.getTmplatCours().equals("")) {
			master.setTmplatCours("/css/egovframework/com/cop/tpl/egovBaseTemplate.css");
			return "egovframework/com/admin/cop/bbs/ArticleList";
		}
		
		String link = master.getTmplatCours() + "/list";
		//// -----------------------------

		if (user != null) {
			model.addAttribute("sessionUniqId", user.getUniqId());
		}
		
		//날짜 비교하기위해 추가 - 2021.04.29
		Date now = new Date();
		
		model.addAttribute("resultList", map.get("resultList"));
		model.addAttribute("resultCnt", map.get("resultCnt"));
		model.addAttribute("articleVO", boardVO);
		model.addAttribute("boardMasterVO", master);
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("noticeList", noticeList);
		model.addAttribute("user", user);
		model.addAttribute("now", now);
		
		return link;
	}
	
	/**
	 * [추가] 관리자모드에서 게시물에 대한 상세 정보를 조회한다.
	 * 
	 * @param boardVO
	 * @param sessionVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/admin/cop/bbs/selectArticleDetail.do")
	public String adminArticleDetail(@ModelAttribute("searchVO") BoardVO boardVO, ModelMap model) throws Exception {
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated(); // KISA 보안취약점 조치 (2018-12-10, 이정은)

		if (!isAuthenticated) {
			return "forward:/uat/uia/LoginUsr.do";
		}

		boardVO.setLastUpdusrId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
		BoardVO vo = ArticleService.selectArticleDetail(boardVO);

		model.addAttribute("result", vo);
		model.addAttribute("sessionUniqId", (user == null || user.getUniqId() == null) ? "" : user.getUniqId());

		// 비밀글은 작성자만 볼수 있음
		if (!EgovStringUtil.isEmpty(vo.getSecretAt()) && vo.getSecretAt().equals("Y")
				&& !((user == null || user.getUniqId() == null) ? "" : user.getUniqId()).equals(vo.getFrstRegisterId()))
			return "forward:/cop/bbs/selectArticleList.do";

		// ----------------------------
		// template 처리 (기본 BBS template 지정 포함)
		// ----------------------------
		BoardMasterVO master = new BoardMasterVO();

		master.setBbsId(boardVO.getBbsId());
		master.setUniqId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());

		BoardMasterVO masterVo = BBSMasterService.selectBBSMasterInf(master);

		if (masterVo.getTmplatCours() == null || masterVo.getTmplatCours().equals("")) {
			masterVo.setTmplatCours("/css/egovframework/com/cop/tpl/egovBaseTemplate.css");
		}

		//// -----------------------------

		// ----------------------------
		// 2009.06.29 : 2단계 기능 추가
		// 2011.07.01 : 댓글, 만족도 조사 기능의 종속성 제거
		// ----------------------------
		if (ArticleCommentService != null) {
			if (ArticleCommentService.canUseComment(boardVO.getBbsId())) {
				model.addAttribute("useComment", "true");
			}
		}
		if (bbsSatisfactionService != null) {
			if (bbsSatisfactionService.canUseSatisfaction(boardVO.getBbsId())) {
				model.addAttribute("useSatisfaction", "true");
			}
		}
		//// --------------------------

		model.addAttribute("boardMasterVO", masterVo);

		return "egovframework/com/admin/cop/bbs/ArticleDetail";
	}

	/**
	 * 게시물에 대한 상세 정보를 조회한다.
	 * 
	 * @param boardVO
	 * @param sessionVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/cop/bbs/selectArticleDetail.do")
	public String selectArticleDetail(@ModelAttribute("searchVO") BoardVO boardVO, ModelMap model) throws Exception {
		
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated(); // KISA 보안취약점 조치 (2018-12-10, 이정은)
	
		BoardMasterVO master = new BoardMasterVO();

		master.setBbsId(boardVO.getBbsId());
		master.setUniqId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());

		BoardMasterVO masterVo = BBSMasterService.selectBBSMasterInf(master);
		
		if(!masterVo.getAuthRead().equals("GUE")) { // 접근 권한 비회원 이상(회원 & 관리자)			
			if (!isAuthenticated ) { // 로그인이 안되어 있을 경우
				return "forward:/uat/uia/LoginUsr.do";
			}
			if(!masterVo.getAuthRead().equals(user.getUserSe()) && !user.getUserSe().equals("USR")) { // 관리자가 아니고 회원 권한이 맞지 않을 경우
				return "egovframework/com/admin/cmm/error/accessDenied";
			}
		}
		
		boardVO.setLastUpdusrId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
		BoardVO vo = ArticleService.selectArticleDetail(boardVO);
		model.addAttribute("result", vo);
		model.addAttribute("sessionUniqId", (user == null || user.getUniqId() == null) ? "" : user.getUniqId());

		// 비밀글은 작성자만 볼수 있음
		if (!EgovStringUtil.isEmpty(vo.getSecretAt()) && vo.getSecretAt().equals("Y")
				&& !((user == null || user.getUniqId() == null) ? "" : user.getUniqId()).equals(vo.getFrstRegisterId()))
			return "forward:/admin/cop/bbs/selectArticleList.do";

		// ----------------------------
		// template 처리 (기본 BBS template 지정 포함)
		// ----------------------------
	
		if (masterVo.getTmplatCours() == null || masterVo.getTmplatCours().equals("")) {
			masterVo.setTmplatCours("/css/egovframework/com/cop/tpl/egovBaseTemplate.css");
			return "egovframework/com/admin/cop/bbs/ArticleDetail";
		}
		String link = masterVo.getTmplatCours() + "/view";

		// ----------------------------
		// 2009.06.29 : 2단계 기능 추가
		// 2011.07.01 : 댓글, 만족도 조사 기능의 종속성 제거
		// ----------------------------
		if (ArticleCommentService != null) {
			if (ArticleCommentService.canUseComment(boardVO.getBbsId())) {
				model.addAttribute("useComment", "true");
			}
		}
		if (bbsSatisfactionService != null) {
			if (bbsSatisfactionService.canUseSatisfaction(boardVO.getBbsId())) {
				model.addAttribute("useSatisfaction", "true");
			}
		}
		//// --------------------------

		model.addAttribute("boardMasterVO", masterVo);

		return link;
	}

	/**
	 * [추가] 관리자모드에서 게시물 등록을 위한 등록페이지로 이동한다. 2021.04.21
	 * 
	 * @param boardVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/admin/cop/bbs/insertArticleView.do")
	public String adminInsertArticleView(@ModelAttribute("searchVO") BoardVO boardVO, ModelMap model) throws Exception {
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

		BoardMasterVO bdMstr = new BoardMasterVO();

		if (isAuthenticated) {

			BoardMasterVO vo = new BoardMasterVO();
			vo.setBbsId(boardVO.getBbsId());
			vo.setUniqId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());

			bdMstr = BBSMasterService.selectBBSMasterInf(vo);
		}

		// ----------------------------
		// 기본 BBS template 지정
		// ----------------------------
		if (bdMstr.getTmplatCours() == null || bdMstr.getTmplatCours().equals("")) {
			bdMstr.setTmplatCours("/css/egovframework/com/cop/tpl/egovBaseTemplate.css");
		}

		model.addAttribute("articleVO", boardVO);
		model.addAttribute("boardMasterVO", bdMstr);
		//// -----------------------------

		return "egovframework/com/admin/cop/bbs/ArticleRegist";
	}
	
	/**
	 * 게시물 등록을 위한 등록페이지로 이동한다.
	 * 
	 * @param boardVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/cop/bbs/insertArticleView.do")
	public String insertArticleView(@ModelAttribute("searchVO") BoardVO boardVO, ModelMap model) throws Exception {
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

		BoardMasterVO bdMstr = new BoardMasterVO();

		bdMstr.setBbsId(boardVO.getBbsId());
		bdMstr.setUniqId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
		BoardMasterVO master = BBSMasterService.selectBBSMasterInf(bdMstr);

		if(!master.getAuthWrite().equals("GUE")) { // 접근 권한 비회원 이상(회원 & 관리자)			
			if (!isAuthenticated ) { // 로그인이 안되어 있을 경우
				return "forward:/uat/uia/LoginUsr.do";
			}
			if(!master.getAuthWrite().equals(user.getUserSe()) && !user.getUserSe().equals("USR")) { 
				return "egovframework/com/admin/cmm/error/accessDenied";
			}
		}
		
		/*if (isAuthenticated) {

			BoardMasterVO vo = new BoardMasterVO();
			vo.setBbsId(boardVO.getBbsId());
			vo.setUniqId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());

			bdMstr = BBSMasterService.selectBBSMasterInf(vo);
		}*/

		// ----------------------------
		// 기본 BBS template 지정
		// ----------------------------
		if (master.getTmplatCours() == null || master.getTmplatCours().equals("")) {
			master.setTmplatCours("/css/egovframework/com/cop/tpl/egovBaseTemplate.css");
		}
		String link = master.getTmplatCours() + "/write";
		
		model.addAttribute("articleVO", boardVO);
		model.addAttribute("boardMasterVO", master);
		//// -----------------------------
		System.out.println("확인 : " + link);

		return link;
	}

	/**
	 * 관리자모드에서 게시물을 등록한다.
	 * 
	 * @param boardVO
	 * @param board
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/admin/cop/bbs/insertArticle.do")
	public String AdminInsertArticle(final MultipartHttpServletRequest multiRequest,
			@ModelAttribute("searchVO") BoardVO boardVO, @ModelAttribute("bdMstr") BoardMaster bdMstr,
			@ModelAttribute("board") BoardVO board, BindingResult bindingResult, ModelMap model) throws Exception {

		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

		if (!isAuthenticated) { // KISA 보안취약점 조치 (2018-12-10, 이정은)
			return "forward:/uat/uia/LoginUsr.do";
		}

		beanValidator.validate(board, bindingResult);
		if (bindingResult.hasErrors()) {

			BoardMasterVO master = new BoardMasterVO();

			master.setBbsId(boardVO.getBbsId());
			master.setUniqId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());

			master = BBSMasterService.selectBBSMasterInf(master);

			// ----------------------------
			// 기본 BBS template 지정
			// ----------------------------
			if (master.getTmplatCours() == null || master.getTmplatCours().equals("")) {
				master.setTmplatCours("css/egovframework/com/cop/tpl/egovBaseTemplate.css");
			}

			model.addAttribute("boardMasterVO", master);
			//// -----------------------------

			return "egovframework/com/admin/cop/bbs/ArticleRegist";
		}

		if (isAuthenticated) {
			List<FileVO> result = null;
			String atchFileId = "";

			final Map<String, MultipartFile> files = multiRequest.getFileMap();
			if (!files.isEmpty()) {
				result = fileUtil.parseFileInf(files, "BBS_", 0, "", "");
				atchFileId = fileMngService.insertFileInfs(result);
			}
			board.setAtchFileId(atchFileId);
			board.setFrstRegisterId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
			board.setBbsId(boardVO.getBbsId());
			board.setBlogId(boardVO.getBlogId());

			// 익명등록 처리
			if (board.getAnonymousAt() != null && board.getAnonymousAt().equals("Y")) {
				board.setNtcrId("anonymous"); // 게시물 통계 집계를 위해 등록자 ID 저장
				board.setNtcrNm("익명"); // 게시물 통계 집계를 위해 등록자 Name 저장
				board.setFrstRegisterId("anonymous");

			} else {
				board.setNtcrId((user == null || user.getUniqId() == null) ? "" : user.getUniqId()); // 게시물 통계 집계를 위해
																										// 등록자 ID 저장
				board.setNtcrNm((user == null || user.getName() == null) ? "" : user.getName()); // 게시물 통계 집계를 위해 등록자
																									// Name 저장

			}

			board.setNttCn(unscript(board.getNttCn())); // XSS 방지
			ArticleService.insertArticle(board);
		}
		// status.setComplete();
		if (boardVO.getBlogAt().equals("Y")) {
			return "forward:/cop/bbs/selectArticleBlogList.do";
		} else {
			return "forward:/admin/cop/bbs/selectArticleList.do";
		}

	}

	/**
	 * 게시물을 등록한다.
	 * 
	 * @param boardVO
	 * @param board
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/cop/bbs/insertArticle.do")
	public String insertArticle(final MultipartHttpServletRequest multiRequest, @ModelAttribute("searchVO") BoardVO boardVO, 
			@ModelAttribute("bdMstr") BoardMaster bdMstr, @ModelAttribute("board") BoardVO board, BindingResult bindingResult, ModelMap model,
			HttpServletRequest request) throws Exception {
		
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
	
		beanValidator.validate(board, bindingResult);
		if (bindingResult.hasErrors()) {

			BoardMasterVO master = new BoardMasterVO();

			master.setBbsId(boardVO.getBbsId());
			master.setUniqId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());

			master = BBSMasterService.selectBBSMasterInf(master);

			// ----------------------------
			// 기본 BBS template 지정
			// ----------------------------
			if (master.getTmplatCours() == null || master.getTmplatCours().equals("")) {
				master.setTmplatCours("css/egovframework/com/cop/tpl/egovBaseTemplate.css");
			}

			model.addAttribute("boardMasterVO", master);
			//// -----------------------------

			return "egovframework/com/admin/cop/bbs/ArticleRegist";
		}
		
		List<FileVO> result = null;
		String atchFileId = "";

		final Map<String, MultipartFile> files = multiRequest.getFileMap();
		if (!files.isEmpty()) {
			result = fileUtil.parseFileInf(files, "BBS_", 0, "", "");
			atchFileId = fileMngService.insertFileInfs(result);
		}
		board.setAtchFileId(atchFileId);
		board.setFrstRegisterId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());		
		board.setBbsId(boardVO.getBbsId());
		board.setBlogId(boardVO.getBlogId());
		board.setPassword(boardVO.getPassword());

		// 익명등록 처리
		if (board.getAnonymousAt() != null && board.getAnonymousAt().equals("Y")) {
			board.setNtcrId("anonymous"); // 게시물 통계 집계를 위해 등록자 ID 저장
			board.setNtcrNm("익명"); // 게시물 통계 집계를 위해 등록자 Name 저장
			board.setFrstRegisterId("anonymous");

		} else {
			board.setNtcrId((user == null || user.getUniqId() == null) ? "" : user.getUniqId()); // 게시물 통계 집계를 위해
																									// 등록자 ID 저장
			board.setNtcrNm((user == null || user.getName() == null) ? boardVO.getFrstRegisterNm() : user.getName()); // 게시물 통계 집계를 위해 등록자
																								// Name 저장
		}

		board.setNttCn(unscript(board.getNttCn())); // XSS 방지
		ArticleService.insertArticle(board);
		
		// status.setComplete();
		if (boardVO.getBlogAt().equals("Y")) {
			return "forward:/cop/bbs/selectArticleBlogList.do";
		} else {
			return "redirect:/board/list.do?bbsId=" + boardVO.getBbsId()+"&menuNo="+request.getParameter("menuNo");
		}

	}

	/**
	 * 게시물에 대한 답변 등록을 위한 등록페이지로 이동한다.
	 * 
	 * @param boardVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/cop/bbs/replyArticleView.do")
	public String addReplyBoardArticle(@ModelAttribute("searchVO") BoardVO boardVO, ModelMap model) throws Exception {
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();// KISA 보안취약점 조치 (2018-12-10, 이정은)

		if (!isAuthenticated) {
			return "forward:/uat/uia/LoginUsr.do";
		}

		BoardMasterVO master = new BoardMasterVO();
		BoardVO articleVO = new BoardVO();
		master.setBbsId(boardVO.getBbsId());
		master.setUniqId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());

		master = BBSMasterService.selectBBSMasterInf(master);
		boardVO = ArticleService.selectArticleDetail(boardVO);

		// ----------------------------
		// 기본 BBS template 지정
		// ----------------------------
		if (master.getTmplatCours() == null || master.getTmplatCours().equals("")) {
			master.setTmplatCours("/css/egovframework/com/cop/tpl/egovBaseTemplate.css");
		}

		model.addAttribute("boardMasterVO", master);
		model.addAttribute("result", boardVO);

		model.addAttribute("articleVO", articleVO);

		if (boardVO.getBlogAt().equals("chkBlog")) {
			return "egovframework/com/admin/cop/bbs/EgovArticleBlogReply";
		} else {
			return "egovframework/com/admin/cop/bbs/EgovArticleReply";
		}
	}

	/**
	 * 게시물에 대한 답변을 등록한다.
	 * 
	 * @param boardVO
	 * @param board
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/cop/bbs/replyArticle.do")
	public String replyBoardArticle(final MultipartHttpServletRequest multiRequest,
			@ModelAttribute("searchVO") BoardVO boardVO, @ModelAttribute("bdMstr") BoardMaster bdMstr,
			@ModelAttribute("board") BoardVO board, BindingResult bindingResult, ModelMap model) throws Exception {

		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

		if (!isAuthenticated) { // KISA 보안취약점 조치 (2018-12-10, 이정은)
			return "forward:/uat/uia/LoginUsr.do";
		}

		beanValidator.validate(board, bindingResult);
		if (bindingResult.hasErrors()) {
			BoardMasterVO master = new BoardMasterVO();

			master.setBbsId(boardVO.getBbsId());
			master.setUniqId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());

			master = BBSMasterService.selectBBSMasterInf(master);

			// ----------------------------
			// 기본 BBS template 지정
			// ----------------------------
			if (master.getTmplatCours() == null || master.getTmplatCours().equals("")) {
				master.setTmplatCours("/css/egovframework/com/cop/tpl/egovBaseTemplate.css");
			}

			model.addAttribute("articleVO", boardVO);
			model.addAttribute("boardMasterVO", master);
			//// -----------------------------

			return "egovframework/com/admin/cop/bbs/EgovArticleReply";
		}

		if (isAuthenticated) {
			final Map<String, MultipartFile> files = multiRequest.getFileMap();
			String atchFileId = "";

			if (!files.isEmpty()) {
				List<FileVO> result = fileUtil.parseFileInf(files, "BBS_", 0, "", "");
				atchFileId = fileMngService.insertFileInfs(result);
			}

			board.setAtchFileId(atchFileId);
			board.setReplyAt("Y");
			board.setFrstRegisterId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
			board.setBbsId(board.getBbsId());
			board.setParnts(Long.toString(boardVO.getNttId()));
			board.setSortOrdr(boardVO.getSortOrdr());
			board.setReplyLc(Integer.toString(Integer.parseInt(boardVO.getReplyLc()) + 1));

			// 익명등록 처리
			if (board.getAnonymousAt() != null && board.getAnonymousAt().equals("Y")) {
				board.setNtcrId("anonymous"); // 게시물 통계 집계를 위해 등록자 ID 저장
				board.setNtcrNm("익명"); // 게시물 통계 집계를 위해 등록자 Name 저장
				board.setFrstRegisterId("anonymous");

			} else {
				board.setNtcrId((user == null || user.getId() == null) ? "" : user.getId()); // 게시물 통계 집계를 위해 등록자 ID 저장
				board.setNtcrNm((user == null || user.getName() == null) ? "" : user.getName()); // 게시물 통계 집계를 위해 등록자
																									// Name 저장

			}
			board.setNttCn(unscript(board.getNttCn())); // XSS 방지

			ArticleService.insertArticle(board);
		}

		return "forward:/admin/cop/bbs/selectArticleList.do";
	}

	/**
	 * 관리자페이지에서 게시물 수정을 위한 수정페이지로 이동한다.
	 * 
	 * @param boardVO
	 * @param vo
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/admin/cop/bbs/updateArticleView.do")
	public String adminUpdateArticleView(@ModelAttribute("searchVO") BoardVO boardVO, @ModelAttribute("board") BoardVO vo,
			ModelMap model) throws Exception {

		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

		boardVO.setFrstRegisterId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());

		BoardMasterVO bmvo = new BoardMasterVO();
		BoardVO bdvo = new BoardVO();

		vo.setBbsId(boardVO.getBbsId());

		bmvo.setBbsId(boardVO.getBbsId());
		bmvo.setUniqId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());

		if (isAuthenticated) {
			bmvo = BBSMasterService.selectBBSMasterInf(bmvo);
			bdvo = ArticleService.selectArticleDetail(boardVO);
		}

		// ----------------------------
		// 기본 BBS template 지정
		// ----------------------------
		if (bmvo.getTmplatCours() == null || bmvo.getTmplatCours().equals("")) {
			bmvo.setTmplatCours("/css/egovframework/com/cop/tpl/egovBaseTemplate.css");
		}

		// 익명 등록글인 경우 수정 불가
		if (bdvo.getNtcrId().equals("anonymous")) {
			model.addAttribute("result", bdvo);
			model.addAttribute("boardMasterVO", bmvo);
			return "egovframework/com/admin/cop/bbs/ArticleDetail";
		}

		model.addAttribute("articleVO", bdvo);
		model.addAttribute("boardMasterVO", bmvo);

		if (boardVO.getBlogAt().equals("chkBlog")) {
			return "egovframework/com/admin/cop/bbs/EgovArticleBlogUpdt";
		} else {
			return "egovframework/com/admin/cop/bbs/ArticleUpdt";
		}

	}
	
	/**
	 * [추가] 게시물 수정을 위한 수정페이지로 이동한다. - 2021.06.04
	 * 
	 * @param boardVO
	 * @param vo
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/cop/bbs/updateArticleView.do")
	public String updateArticleView(@ModelAttribute("searchVO") BoardVO boardVO, @ModelAttribute("board") BoardVO vo,
			ModelMap model) throws Exception {

		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

		boardVO.setFrstRegisterId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());

		BoardMasterVO bmvo = new BoardMasterVO();	//게시판 관련
		BoardVO bdvo = new BoardVO();			//게시물 관련

		vo.setBbsId(boardVO.getBbsId());

		bmvo.setBbsId(boardVO.getBbsId());
		bmvo.setUniqId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());

		bmvo = BBSMasterService.selectBBSMasterInf(bmvo);
		bdvo = ArticleService.selectArticleDetail(boardVO);

		if(!bmvo.getAuthWrite().equals("GUE")) { // 접근 권한 비회원 이상(회원 & 관리자)			
			if (!isAuthenticated ) { // 로그인이 안되어 있을 경우
				return "forward:/uat/uia/LoginUsr.do";
			}
			if(!bmvo.getAuthWrite().equals(user.getUserSe()) && !user.getUserSe().equals("USR")) { // 관리자가 아니고 회원 권한이 맞지 않을 경우
				return "egovframework/com/admin/cmm/error/accessDenied";
			}
		}

		// ----------------------------
		// 기본 BBS template 지정
		// ----------------------------
		if (bmvo.getTmplatCours() == null || bmvo.getTmplatCours().equals("")) {
			bmvo.setTmplatCours("/css/egovframework/com/cop/tpl/egovBaseTemplate.css");
		}

		String link = bmvo.getTmplatCours() + "/modify";
		
		// 익명 등록글인 경우 수정 불가
		if (bdvo.getNtcrId().equals("anonymous")) {
			model.addAttribute("result", bdvo);
			model.addAttribute("boardMasterVO", bmvo);
			return "egovframework/com/admin/cop/bbs/ArticleDetail";
		}

		model.addAttribute("articleVO", bdvo);
		model.addAttribute("boardMasterVO", bmvo);

		if (boardVO.getBlogAt().equals("chkBlog")) {
			return "egovframework/com/admin/cop/bbs/EgovArticleBlogUpdt";
		} else {
			return link;
		}
	}

	/**
	 * 관리자 게시물에 대한 내용을 수정한다.
	 * 
	 * @param boardVO
	 * @param board
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/admin/cop/bbs/updateArticle.do")
	public String updateAdminBoardArticle(final MultipartHttpServletRequest multiRequest,
			@ModelAttribute("searchVO") BoardVO boardVO, @ModelAttribute("bdMstr") BoardMaster bdMstr,
			@ModelAttribute("board") Board board, BindingResult bindingResult, ModelMap model) throws Exception {

		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

		if (!isAuthenticated) { // KISA 보안취약점 조치 (2018-12-10, 이정은)
			return "forward:/uat/uia/LoginUsr.do";
		}

		// --------------------------------------------------------------------------------------------
		// @ XSS 대응 권한체크 체크 START
		// param1 : 사용자고유ID(uniqId,esntlId)
		// --------------------------------------------------------
		LOGGER.debug("@ XSS 권한체크 START ----------------------------------------------");
		// step1 DB에서 해당 게시물의 uniqId 조회
		BoardVO vo = ArticleService.selectArticleDetail(boardVO);

		// step2 EgovXssChecker 공통모듈을 이용한 권한체크
		EgovXssChecker.checkerUserXss(multiRequest, vo.getFrstRegisterId());
		LOGGER.debug("@ XSS 권한체크 END ------------------------------------------------");
		// --------------------------------------------------------
		// @ XSS 대응 권한체크 체크 END
		// --------------------------------------------------------------------------------------------

		String atchFileId = boardVO.getAtchFileId();

		beanValidator.validate(board, bindingResult);
		if (bindingResult.hasErrors()) {

			boardVO.setFrstRegisterId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());

			BoardMasterVO bmvo = new BoardMasterVO();
			BoardVO bdvo = new BoardVO();

			bmvo.setBbsId(boardVO.getBbsId());
			bmvo.setUniqId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());

			bmvo = BBSMasterService.selectBBSMasterInf(bmvo);
			bdvo = ArticleService.selectArticleDetail(boardVO);

			model.addAttribute("articleVO", bdvo);
			model.addAttribute("boardMasterVO", bmvo);

			return "egovframework/com/web/board/bbs/ArticleUpdt";
		}

		if (isAuthenticated) {
			final Map<String, MultipartFile> files = multiRequest.getFileMap();
			if (!files.isEmpty()) {
				if ("".equals(atchFileId)) {
					List<FileVO> result = fileUtil.parseFileInf(files, "BBS_", 0, atchFileId, "");
					atchFileId = fileMngService.insertFileInfs(result);
					board.setAtchFileId(atchFileId);
				} else {
					FileVO fvo = new FileVO();
					fvo.setAtchFileId(atchFileId);
					int cnt = fileMngService.getMaxFileSN(fvo);
					List<FileVO> _result = fileUtil.parseFileInf(files, "BBS_", cnt, atchFileId, "");
					fileMngService.updateFileInfs(_result);
				}
			}

			board.setLastUpdusrId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());

			board.setNtcrNm(""); // dummy 오류 수정 (익명이 아닌 경우 validator 처리를 위해 dummy로 지정됨)
			board.setPassword(""); // dummy 오류 수정 (익명이 아닌 경우 validator 처리를 위해 dummy로 지정됨)

			board.setNttCn(unscript(board.getNttCn())); // XSS 방지

			ArticleService.updateArticle(board);
		}

		return "forward:/admin/cop/bbs/selectArticleList.do";
	}

	/**
	 * [추가] 게시물에 대한 내용을 수정한다. - 2021.06.07
	 * 
	 * @param boardVO
	 * @param board
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/cop/bbs/updateArticle.do")
	public String updateBoardArticle(final MultipartHttpServletRequest multiRequest, HttpServletRequest request,
			@ModelAttribute("searchVO") BoardVO boardVO, @ModelAttribute("bdMstr") BoardMaster bdMstr,
			@ModelAttribute("board") Board board, BindingResult bindingResult, ModelMap model) throws Exception {

		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

		// --------------------------------------------------------------------------------------------
		// @ XSS 대응 권한체크 체크 START
		// param1 : 사용자고유ID(uniqId,esntlId)
		// --------------------------------------------------------
		LOGGER.debug("@ XSS 권한체크 START ----------------------------------------------");
		// step1 DB에서 해당 게시물의 uniqId 조회
		BoardVO vo = ArticleService.selectArticleDetail(boardVO);

		// step2 EgovXssChecker 공통모듈을 이용한 권한체크
		EgovXssChecker.checkerUserXss(multiRequest, vo.getFrstRegisterId());
		LOGGER.debug("@ XSS 권한체크 END ------------------------------------------------");
		// --------------------------------------------------------
		// @ XSS 대응 권한체크 체크 END
		// --------------------------------------------------------------------------------------------
		String atchFileId = boardVO.getAtchFileId();
		boardVO.setFrstRegisterId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
		BoardMasterVO bmvo = new BoardMasterVO();

		bmvo.setBbsId(boardVO.getBbsId());
		bmvo.setUniqId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());

		bmvo = BBSMasterService.selectBBSMasterInf(bmvo);

		beanValidator.validate(board, bindingResult);
		if (bindingResult.hasErrors()) {

			BoardVO bdvo = new BoardVO();
			bdvo = ArticleService.selectArticleDetail(boardVO);

			model.addAttribute("articleVO", bdvo);
			model.addAttribute("boardMasterVO", bmvo);

			return bmvo.getTmplatCours() + "/modify";
		}

		if(!bmvo.getAuthWrite().equals("GUE")) { // 접근 권한 비회원 이상(회원 & 관리자)			
			if (!isAuthenticated ) { // 로그인이 안되어 있을 경우
				return "forward:/uat/uia/LoginUsr.do";
			}
			if(!bmvo.getAuthWrite().equals(user.getUserSe()) && !user.getUserSe().equals("USR")) { // 관리자가 아니고 회원 권한이 맞지 않을 경우
				return "egovframework/com/admin/cmm/error/accessDenied";
			}
		}

		final Map<String, MultipartFile> files = multiRequest.getFileMap();
		if (!files.isEmpty()) {
			if ("".equals(atchFileId)) {
				List<FileVO> result = fileUtil.parseFileInf(files, "BBS_", 0, atchFileId, "");
				atchFileId = fileMngService.insertFileInfs(result);
				board.setAtchFileId(atchFileId);
			} else {
				FileVO fvo = new FileVO();
				fvo.setAtchFileId(atchFileId);
				int cnt = fileMngService.getMaxFileSN(fvo);
				List<FileVO> _result = fileUtil.parseFileInf(files, "BBS_", cnt, atchFileId, "");
				fileMngService.updateFileInfs(_result);
			}
		}

		board.setLastUpdusrId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());

		board.setNtcrNm(""); // dummy 오류 수정 (익명이 아닌 경우 validator 처리를 위해 dummy로 지정됨)
		board.setPassword(""); // dummy 오류 수정 (익명이 아닌 경우 validator 처리를 위해 dummy로 지정됨)

		board.setNttCn(unscript(board.getNttCn())); // XSS 방지

		ArticleService.updateArticle(board);

		return "redirect:/board/list.do?bbsId=" + boardVO.getBbsId()+"&menuNo="+request.getParameter("menuNo");
	}
	
	  
    /**
     * [추가]-2021.06.07
     * 비회원이 게시물 수정,삭제하기위한 페이지를 이동하기 위해
     *
     */
    @RequestMapping("/cop/bbs/guestArticlePre.do")
    public String guestArticlePre(ModelMap model, HttpServletRequest request) {
    	model.addAttribute("bbsId", request.getParameter("bbsId"));
    	model.addAttribute("nttId", request.getParameter("nttId"));
    	model.addAttribute("state", request.getParameter("state"));
    	model.addAttribute("menuNo", request.getParameter("menuNo"));
    	
    	return "egovframework/com/web/board/articlePasswordCheck";
    }
    
    /**
     * [추가]-2021.06.07
     * 비회원게시글 비밀번호 체크
     * @throws Exception 
     *
     */
    @RequestMapping("/cop/bbs/guestArticle.do")
    public String guestArticle(ModelMap model, HttpServletRequest request, HttpServletResponse response, @ModelAttribute("searchVO") BoardVO boardVO) throws Exception {
    
    	BoardVO vo = ArticleService.selectArticleDetail(boardVO);
    	
    	String password = request.getParameter("pass");	// 비밀번호 확인창에서 입력한 비밀번호		
		String state = request.getParameter("state");
		String menuNo = request.getParameter("menuNo");

		PrintWriter out = response.getWriter();
		response.setContentType("text/html; charset=UTF-8");

		if(password.equals(vo.getPassword())) {
			if(state.equals("update")) {	//수정일 때
				out.print("<script lauguage='javascript'>");
				out.print("opener.location.href='/board/modify.do?parnts="+vo.getParnts()+"&sortOrdr="+vo.getSortOrdr()+"&replyLc="+vo.getReplyLc()+"&nttSj="+vo.getNttSj()
											+"&nttId="+vo.getNttId()+"&bbsId="+vo.getBbsId()+"&menuNo="+menuNo+"';");
				out.print("self.close();");
				out.print("</script>");
				out.flush();
				out.close();		
				return "blank";
			}else {	//삭제 
				out.print("<script lauguage='javascript'>");
				out.print("opener.location.href='/board/delete.do?&nttId="+vo.getNttId()+"&bbsId="+vo.getBbsId()+"&menuNo="+menuNo+"';");
				out.print("self.close();");
				out.print("</script>");
				out.flush();
				out.close();		
				return "blank";
			}
		}else {
			out.print("<script lauguage='javascript'>");
			out.print("alert('비밀번호가 일치하지 않습니다.');");
			out.print("location.href='"+request.getHeader("referer")+"';");
			out.print("</script>");
			out.flush();
			out.close();
			return "blank";
		}    	
    }
	
	/**
	 * 관리자에서 게시물에 대한 내용을 삭제한다.
	 * 
	 * @param boardVO
	 * @param board
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/admin/cop/bbs/deleteArticle.do")
	public String deleteAdminBoardArticle(HttpServletRequest request, @ModelAttribute("searchVO") BoardVO boardVO,
			@ModelAttribute("board") Board board, @ModelAttribute("bdMstr") BoardMaster bdMstr, ModelMap model)
			throws Exception {

		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

		// --------------------------------------------------------------------------------------------
		// @ XSS 대응 권한체크 체크 START
		// param1 : 사용자고유ID(uniqId,esntlId)
		// --------------------------------------------------------
		LOGGER.debug("@ XSS 권한체크 START ----------------------------------------------");
		// step1 DB에서 해당 게시물의 uniqId 조회
		BoardVO vo = ArticleService.selectArticleDetail(boardVO);

		// step2 EgovXssChecker 공통모듈을 이용한 권한체크
		EgovXssChecker.checkerUserXss(request, vo.getFrstRegisterId());
		LOGGER.debug("@ XSS 권한체크 END ------------------------------------------------");
		// --------------------------------------------------------
		// @ XSS 대응 권한체크 체크 END
		// --------------------------------------------------------------------------------------------

		BoardVO bdvo = ArticleService.selectArticleDetail(boardVO);
		// 익명 등록글인 경우 수정 불가
		if (bdvo.getNtcrId().equals("anonymous")) {
			model.addAttribute("result", bdvo);
			model.addAttribute("boardMasterVO", bdMstr);
			return "egovframework/com/admin/cop/bbs/ArticleDetail";
		}

		if (isAuthenticated) {
			board.setLastUpdusrId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());

			ArticleService.deleteArticle(board);
		}

		if (boardVO.getBlogAt().equals("chkBlog")) {
			return "forward:/cop/bbs/selectArticleBlogList.do";
		} else {
			return "forward:/admin/cop/bbs/allArticleList.do";
		}
	}
	
	/**
	 * [추가] 게시물에 대한 내용을 삭제한다. - 2021.06.08
	 * 
	 * @param boardVO
	 * @param board
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/cop/bbs/deleteArticle.do")
	public String deleteBoardArticle(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("searchVO") BoardVO boardVO,
			@ModelAttribute("board") Board board, @ModelAttribute("bdMstr") BoardMaster bdMstr, ModelMap model)
			throws Exception {

		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

		// --------------------------------------------------------------------------------------------
		// @ XSS 대응 권한체크 체크 START
		// param1 : 사용자고유ID(uniqId,esntlId)
		// --------------------------------------------------------
		LOGGER.debug("@ XSS 권한체크 START ----------------------------------------------");
		// step1 DB에서 해당 게시물의 uniqId 조회
		BoardVO vo = ArticleService.selectArticleDetail(boardVO);

		// step2 EgovXssChecker 공통모듈을 이용한 권한체크
		EgovXssChecker.checkerUserXss(request, vo.getFrstRegisterId());
		LOGGER.debug("@ XSS 권한체크 END ------------------------------------------------");
		// --------------------------------------------------------
		// @ XSS 대응 권한체크 체크 END
		// --------------------------------------------------------------------------------------------
		boardVO.setFrstRegisterId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
		BoardMasterVO bmvo = new BoardMasterVO();

		bmvo.setBbsId(boardVO.getBbsId());
		bmvo.setUniqId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());

		bmvo = BBSMasterService.selectBBSMasterInf(bmvo);
		
		if(!bmvo.getAuthRead().equals("GUE")) { // 접근 권한 비회원 이상(회원 & 관리자)			
			if (!isAuthenticated ) { // 로그인이 안되어 있을 경우
				return "forward:/uat/uia/LoginUsr.do";
			}
			if(!bmvo.getAuthRead().equals(user.getUserSe()) && !user.getUserSe().equals("USR")) { // 관리자가 아니고 회원 권한이 맞지 않을 경우
				return "egovframework/com/admin/cmm/error/accessDenied";
			}
		}
		
		BoardVO bdvo = ArticleService.selectArticleDetail(boardVO);
		// 익명 등록글인 경우 수정 불가
		if (bdvo.getNtcrId().equals("anonymous")) {
			model.addAttribute("result", bdvo);
			model.addAttribute("boardMasterVO", bdMstr);
			return "egovframework/com/admin/cop/bbs/ArticleDetail";
		}

		board.setLastUpdusrId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());

		ArticleService.deleteArticle(board);

		if (boardVO.getBlogAt().equals("chkBlog")) {
			return "forward:/cop/bbs/selectArticleBlogList.do";
		} else {
			return "redirect:/board/list.do?bbsId=" + boardVO.getBbsId()+"&menuNo="+request.getParameter("menuNo");
		}
	}

	/**
	 * 방명록에 대한 목록을 조회한다.
	 * 
	 * @param boardVO
	 * @param sessionVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/cop/bbs/selectGuestArticleList.do")
	public String selectGuestArticleList(@ModelAttribute("searchVO") BoardVO boardVO, ModelMap model) throws Exception {

		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

		if (!isAuthenticated) { // KISA 보안취약점 조치 (2018-12-10, 이정은)
			return "forward:/uat/uia/LoginUsr.do";
		}

		// 수정 및 삭제 기능 제어를 위한 처리
		model.addAttribute("sessionUniqId", (user == null || user.getUniqId() == null) ? "" : user.getUniqId());

		BoardVO vo = new BoardVO();

		vo.setBbsId(boardVO.getBbsId());
		vo.setBbsNm(boardVO.getBbsNm());
		vo.setNtcrNm((user == null || user.getName() == null) ? "" : user.getName());
		vo.setNtcrId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());

		BoardMasterVO masterVo = new BoardMasterVO();

		masterVo.setBbsId(vo.getBbsId());
		masterVo.setUniqId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());

		BoardMasterVO mstrVO = BBSMasterService.selectBBSMasterInf(masterVo);

		vo.setPageIndex(boardVO.getPageIndex());
		vo.setPageUnit(propertyService.getInt("pageUnit"));
		vo.setPageSize(propertyService.getInt("pageSize"));

		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(vo.getPageIndex());
		paginationInfo.setRecordCountPerPage(vo.getPageUnit());
		paginationInfo.setPageSize(vo.getPageSize());

		vo.setFirstIndex(paginationInfo.getFirstRecordIndex());
		vo.setLastIndex(paginationInfo.getLastRecordIndex());
		vo.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		Map<String, Object> map = ArticleService.selectGuestArticleList(vo);
		int totCnt = Integer.parseInt((String) map.get("resultCnt"));

		paginationInfo.setTotalRecordCount(totCnt);

		model.addAttribute("user", user);
		model.addAttribute("resultList", map.get("resultList"));
		model.addAttribute("resultCnt", map.get("resultCnt"));
		model.addAttribute("boardMasterVO", mstrVO);
		model.addAttribute("articleVO", vo);
		model.addAttribute("paginationInfo", paginationInfo);

		return "egovframework/com/admin/cop/bbs/GuestArticleList";
	}

	/**
	 * 방명록에 대한 내용을 등록한다.
	 * 
	 * @param boardVO
	 * @param board
	 * @param sessionVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/cop/bbs/insertGuestArticle.do")
	public String insertGuestList(@ModelAttribute("searchVO") BoardVO boardVO, @ModelAttribute("Board") Board board,
			BindingResult bindingResult, ModelMap model) throws Exception {

		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

		if (!isAuthenticated) { // KISA 보안취약점 조치 (2018-12-10, 이정은)
			return "forward:/uat/uia/LoginUsr.do";
		}

		beanValidator.validate(board, bindingResult);
		if (bindingResult.hasErrors()) {

			BoardVO vo = new BoardVO();

			vo.setBbsId(boardVO.getBbsId());
			vo.setBbsNm(boardVO.getBbsNm());
			vo.setNtcrNm(user == null ? "" : EgovStringUtil.isNullToString(user.getName()));
			vo.setNtcrId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());

			BoardMasterVO masterVo = new BoardMasterVO();

			masterVo.setBbsId(vo.getBbsId());
			masterVo.setUniqId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());

			BoardMasterVO mstrVO = BBSMasterService.selectBBSMasterInf(masterVo);

			vo.setPageUnit(propertyService.getInt("pageUnit"));
			vo.setPageSize(propertyService.getInt("pageSize"));

			PaginationInfo paginationInfo = new PaginationInfo();
			paginationInfo.setCurrentPageNo(vo.getPageIndex());
			paginationInfo.setRecordCountPerPage(vo.getPageUnit());
			paginationInfo.setPageSize(vo.getPageSize());

			vo.setFirstIndex(paginationInfo.getFirstRecordIndex());
			vo.setLastIndex(paginationInfo.getLastRecordIndex());
			vo.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

			Map<String, Object> map = ArticleService.selectGuestArticleList(vo);
			int totCnt = Integer.parseInt((String) map.get("resultCnt"));

			paginationInfo.setTotalRecordCount(totCnt);

			model.addAttribute("resultList", map.get("resultList"));
			model.addAttribute("resultCnt", map.get("resultCnt"));
			model.addAttribute("boardMasterVO", mstrVO);
			model.addAttribute("articleVO", vo);
			model.addAttribute("paginationInfo", paginationInfo);

			return "egovframework/com/admin/cop/bbs/GuestArticleList";

		}

		if (isAuthenticated) {
			board.setFrstRegisterId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());

			ArticleService.insertArticle(board);

			boardVO.setNttCn("");
			boardVO.setPassword("");
			boardVO.setNtcrId("");
			boardVO.setNttId(0);
		}

		return "forward:/cop/bbs/selectGuestArticleList.do";
	}

	/**
	 * 방명록에 대한 내용을 삭제한다.
	 * 
	 * @param boardVO
	 * @param sessionVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/cop/bbs/deleteGuestArticle.do")
	public String deleteGuestList(@ModelAttribute("searchVO") BoardVO boardVO, @ModelAttribute("articleVO") Board board,
			ModelMap model) throws Exception {
		@SuppressWarnings("unused")
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

		if (isAuthenticated) {
			ArticleService.deleteArticle(boardVO);
		}

		return "forward:/cop/bbs/selectGuestArticleList.do";
	}

	/**
	 * 방명록 수정을 위한 특정 내용을 조회한다.
	 * 
	 * @param boardVO
	 * @param sessionVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/cop/bbs/updateGuestArticleView.do")
	public String updateGuestArticleView(@ModelAttribute("searchVO") BoardVO boardVO,
			@ModelAttribute("boardMasterVO") BoardMasterVO brdMstrVO, ModelMap model) throws Exception {

		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

		if (!isAuthenticated) { // KISA 보안취약점 조치 (2018-12-10, 이정은)
			return "forward:/uat/uia/LoginUsr.do";
		}

		// 수정 및 삭제 기능 제어를 위한 처리
		model.addAttribute("sessionUniqId", (user == null || user.getUniqId() == null) ? "" : user.getUniqId());

		BoardVO vo = ArticleService.selectArticleDetail(boardVO);

		boardVO.setBbsId(boardVO.getBbsId());
		boardVO.setBbsNm(boardVO.getBbsNm());
		boardVO.setNtcrNm((user == null || user.getName() == null) ? "" : user.getName());

		boardVO.setPageUnit(propertyService.getInt("pageUnit"));
		boardVO.setPageSize(propertyService.getInt("pageSize"));

		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(boardVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(boardVO.getPageUnit());
		paginationInfo.setPageSize(boardVO.getPageSize());

		boardVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		boardVO.setLastIndex(paginationInfo.getLastRecordIndex());
		boardVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		Map<String, Object> map = ArticleService.selectGuestArticleList(boardVO);
		int totCnt = Integer.parseInt((String) map.get("resultCnt"));

		paginationInfo.setTotalRecordCount(totCnt);

		model.addAttribute("resultList", map.get("resultList"));
		model.addAttribute("resultCnt", map.get("resultCnt"));
		model.addAttribute("articleVO", vo);
		model.addAttribute("paginationInfo", paginationInfo);

		return "egovframework/com/admin/cop/bbs/GuestArticleList";
	}

	/**
	 * 방명록을 수정하고 게시판 메인페이지를 조회한다.
	 * 
	 * @param boardVO
	 * @param sessionVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/cop/bbs/updateGuestArticle.do")
	public String updateGuestArticle(@ModelAttribute("searchVO") BoardVO boardVO, @ModelAttribute Board board,
			BindingResult bindingResult, ModelMap model) throws Exception {

		// BBST02, BBST04
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

		if (!isAuthenticated) { // KISA 보안취약점 조치 (2018-12-10, 이정은)
			return "forward:/uat/uia/LoginUsr.do";
		}

		beanValidator.validate(board, bindingResult);
		if (bindingResult.hasErrors()) {

			BoardVO vo = new BoardVO();

			vo.setBbsId(boardVO.getBbsId());
			vo.setBbsNm(boardVO.getBbsNm());
			vo.setNtcrNm((user == null || user.getName() == null) ? "" : user.getName());
			vo.setNtcrId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());

			BoardMasterVO masterVo = new BoardMasterVO();

			masterVo.setBbsId(vo.getBbsId());
			masterVo.setUniqId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());

			BoardMasterVO mstrVO = BBSMasterService.selectBBSMasterInf(masterVo);

			vo.setPageUnit(propertyService.getInt("pageUnit"));
			vo.setPageSize(propertyService.getInt("pageSize"));

			PaginationInfo paginationInfo = new PaginationInfo();
			paginationInfo.setCurrentPageNo(vo.getPageIndex());
			paginationInfo.setRecordCountPerPage(vo.getPageUnit());
			paginationInfo.setPageSize(vo.getPageSize());

			vo.setFirstIndex(paginationInfo.getFirstRecordIndex());
			vo.setLastIndex(paginationInfo.getLastRecordIndex());
			vo.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

			Map<String, Object> map = ArticleService.selectGuestArticleList(vo);
			int totCnt = Integer.parseInt((String) map.get("resultCnt"));

			paginationInfo.setTotalRecordCount(totCnt);

			model.addAttribute("resultList", map.get("resultList"));
			model.addAttribute("resultCnt", map.get("resultCnt"));
			model.addAttribute("boardMasterVO", mstrVO);
			model.addAttribute("articleVO", vo);
			model.addAttribute("paginationInfo", paginationInfo);

			return "egovframework/com/admin/cop/bbs/GuestArticleList";
		}

		if (isAuthenticated) {
			ArticleService.updateArticle(board);
			boardVO.setNttCn("");
			boardVO.setPassword("");
			boardVO.setNtcrId("");
			boardVO.setNttId(0);
		}

		return "forward:/cop/bbs/selectGuestArticleList.do";
	}

	/*********************
	 * 블로그관련
	 ********************/

	/**
	 * 블로그 게시판에 대한 목록을 조회한다.
	 * 
	 * @param boardVO
	 * @param sessionVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/cop/bbs/selectArticleBlogList.do")
	public String selectArticleBlogList(@ModelAttribute("searchVO") BoardVO boardVO, ModelMap model) throws Exception {

		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated(); // KISA 보안취약점 조치 (2018-12-10, 이정은)

		if (!isAuthenticated) {
			return "forward:/uat/uia/LoginUsr.do";
		}

		BlogVO blogVo = new BlogVO();
		blogVo.setFrstRegisterId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
		blogVo.setBbsId(boardVO.getBbsId());
		blogVo.setBlogId(boardVO.getBlogId());
		BlogVO master = BBSMasterService.selectBlogDetail(blogVo);

		boardVO.setFrstRegisterId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());

		// 블로그 카테고리관리 권한(로그인 한 사용자만 가능)
		int loginUserCnt = ArticleService.selectLoginUser(boardVO);

		// 블로그 게시판 제목 추출
		List<BoardVO> blogNameList = ArticleService.selectBlogNmList(boardVO);

		if (user != null) {
			model.addAttribute("sessionUniqId", (user == null || user.getUniqId() == null) ? "" : user.getUniqId());
		}

		model.addAttribute("articleVO", boardVO);
		model.addAttribute("boardMasterVO", master);
		model.addAttribute("blogNameList", blogNameList);
		model.addAttribute("loginUserCnt", loginUserCnt);

		return "egovframework/com/admin/cop/bbs/EgovArticleBlogList";
	}

	/**
	 * 블로그 게시물에 대한 상세 타이틀을 조회한다.
	 * 
	 * @param boardVO
	 * @param sessionVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/cop/bbs/selectArticleBlogDetail.do")
	public ModelAndView selectArticleBlogDetail(@ModelAttribute("searchVO") BoardVO boardVO, ModelMap model)
			throws Exception {
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated(); // KISA 보안취약점 조치 (2018-12-10, 이정은)

		if (!isAuthenticated) {
			throw new IllegalAccessException("Login Required!");
		}

		BoardVO vo = new BoardVO();

		boardVO.setLastUpdusrId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());

		boardVO.setPageUnit(propertyService.getInt("pageUnit"));
		boardVO.setPageSize(propertyService.getInt("pageSize"));

		PaginationInfo paginationInfo = new PaginationInfo();

		paginationInfo.setCurrentPageNo(boardVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(boardVO.getPageUnit());
		paginationInfo.setPageSize(boardVO.getPageSize());

		boardVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		boardVO.setLastIndex(paginationInfo.getLastRecordIndex());
		boardVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		List<BoardVO> blogSubJectList = ArticleService.selectArticleDetailDefault(boardVO);
		vo = ArticleService.selectArticleCnOne(boardVO);

		int totCnt = ArticleService.selectArticleDetailDefaultCnt(boardVO);
		paginationInfo.setTotalRecordCount(totCnt);

		ModelAndView mav = new ModelAndView("jsonView");
		mav.addObject("blogSubJectList", blogSubJectList);
		mav.addObject("paginationInfo", paginationInfo);

		if (vo.getNttCn() != null) {
			mav.addObject("blogCnOne", vo);
		}

		// 비밀글은 작성자만 볼수 있음
		if (!EgovStringUtil.isEmpty(vo.getSecretAt()) && vo.getSecretAt().equals("Y")
				&& !((user == null || user.getUniqId() == null) ? "" : user.getUniqId()).equals(vo.getFrstRegisterId()))
			mav.setViewName("forward:/admin/cop/bbs/selectArticleList.do");
		return mav;
	}

	/**
	 * 블로그 게시물에 대한 상세 내용을 조회한다.
	 * 
	 * @param boardVO
	 * @param sessionVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/cop/bbs/selectArticleBlogDetailCn.do")
	public ModelAndView selectArticleBlogDetailCn(@ModelAttribute("searchVO") BoardVO boardVO,
			@ModelAttribute("commentVO") CommentVO commentVO, ModelMap model) throws Exception {
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

		boardVO.setLastUpdusrId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());

		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated(); // KISA 보안취약점 조치 (2018-12-10, 이정은)

		if (!isAuthenticated) {
			throw new IllegalAccessException("Login Required!");
		}

		BoardVO vo = ArticleService.selectArticleDetail(boardVO);

		// ----------------------------
		// 댓글 처리
		// ----------------------------
		CommentVO articleCommentVO = new CommentVO();
		commentVO.setWrterNm((user == null || user.getName() == null) ? "" : user.getName());

		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(commentVO.getSubPageIndex());
		paginationInfo.setRecordCountPerPage(commentVO.getSubPageUnit());
		paginationInfo.setPageSize(commentVO.getSubPageSize());

		commentVO.setSubFirstIndex(paginationInfo.getFirstRecordIndex());
		commentVO.setSubLastIndex(paginationInfo.getLastRecordIndex());
		commentVO.setSubRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		Map<String, Object> map = ArticleCommentService.selectArticleCommentList(commentVO);
		int totCnt = Integer.parseInt((String) map.get("resultCnt"));

		paginationInfo.setTotalRecordCount(totCnt);

		// 댓글 처리 END
		// ----------------------------

		List<BoardVO> blogCnList = ArticleService.selectArticleDetailCn(boardVO);
		ModelAndView mav = new ModelAndView("jsonView");

		// 수정 처리된 후 댓글 등록 화면으로 처리되기 위한 구현
		if (commentVO.isModified()) {
			commentVO.setCommentNo("");
			commentVO.setCommentCn("");
		}

		// 수정을 위한 처리
		if (!commentVO.getCommentNo().equals("")) {
			mav.setViewName("forward:/cop/cmt/updateArticleCommentView.do");
		}

		mav.addObject("blogCnList", blogCnList);
		mav.addObject("resultUnder", vo);
		mav.addObject("paginationInfo", paginationInfo);
		mav.addObject("resultList", map.get("resultList"));
		mav.addObject("resultCnt", map.get("resultCnt"));
		mav.addObject("articleCommentVO", articleCommentVO); // validator 용도

		commentVO.setCommentCn(""); // 등록 후 댓글 내용 처리

		// 비밀글은 작성자만 볼수 있음
		if (!EgovStringUtil.isEmpty(vo.getSecretAt()) && vo.getSecretAt().equals("Y")
				&& !((user == null || user.getUniqId() == null) ? "" : user.getUniqId()).equals(vo.getFrstRegisterId()))
			mav.setViewName("forward:/admin/cop/bbs/selectArticleList.do");
		return mav;

	}

	/**
	 * 개인블로그 관리
	 * 
	 * @param boardVO
	 * @param sessionVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/cop/bbs/selectBlogListManager.do")
	public String selectBlogMasterList(@ModelAttribute("searchVO") BoardVO boardVO, ModelMap model) throws Exception {

		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

		boardVO.setPageUnit(propertyService.getInt("pageUnit"));
		boardVO.setPageSize(propertyService.getInt("pageSize"));

		PaginationInfo paginationInfo = new PaginationInfo();

		paginationInfo.setCurrentPageNo(boardVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(boardVO.getPageUnit());
		paginationInfo.setPageSize(boardVO.getPageSize());

		boardVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		boardVO.setLastIndex(paginationInfo.getLastRecordIndex());
		boardVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		boardVO.setFrstRegisterId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());

		Map<String, Object> map = ArticleService.selectBlogListManager(boardVO);
		int totCnt = Integer.parseInt((String) map.get("resultCnt"));

		paginationInfo.setTotalRecordCount(totCnt);

		model.addAttribute("resultList", map.get("resultList"));
		model.addAttribute("resultCnt", map.get("resultCnt"));
		model.addAttribute("paginationInfo", paginationInfo);

		return "egovframework/com/admin/cop/bbs/EgovBlogListManager";
	}

	/**
	 * 템플릿에 대한 미리보기용 게시물 목록을 조회한다.
	 * 
	 * @param boardVO
	 * @param sessionVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/cop/bbs/previewBoardList.do")
	public String previewBoardArticles(@ModelAttribute("searchVO") BoardVO boardVO, ModelMap model) throws Exception {
		// LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

		String template = boardVO.getSearchWrd(); // 템플릿 URL

		BoardMasterVO master = new BoardMasterVO();

		master.setBbsNm("미리보기 게시판");

		boardVO.setPageUnit(propertyService.getInt("pageUnit"));
		boardVO.setPageSize(propertyService.getInt("pageSize"));

		PaginationInfo paginationInfo = new PaginationInfo();

		paginationInfo.setCurrentPageNo(boardVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(boardVO.getPageUnit());
		paginationInfo.setPageSize(boardVO.getPageSize());

		boardVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		boardVO.setLastIndex(paginationInfo.getLastRecordIndex());
		boardVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		BoardVO target = null;
		List<BoardVO> list = new ArrayList<BoardVO>();

		target = new BoardVO();
		target.setNttSj("게시판 기능 설명");
		target.setFrstRegisterId("ID");
		target.setFrstRegisterNm("관리자");
		target.setFrstRegisterPnttm("2019-01-01");
		target.setInqireCo(7);
		target.setParnts("0");
		target.setReplyAt("N");
		target.setReplyLc("0");
		target.setUseAt("Y");

		list.add(target);

		target = new BoardVO();
		target.setNttSj("게시판 부가 기능 설명");
		target.setFrstRegisterId("ID");
		target.setFrstRegisterNm("관리자");
		target.setFrstRegisterPnttm("2019-01-01");
		target.setInqireCo(7);
		target.setParnts("0");
		target.setReplyAt("N");
		target.setReplyLc("0");
		target.setUseAt("Y");

		list.add(target);

		boardVO.setSearchWrd("");

		int totCnt = list.size();

		// 공지사항 추출
		List<BoardVO> noticeList = ArticleService.selectNoticeArticleList(boardVO);

		paginationInfo.setTotalRecordCount(totCnt);

		master.setTmplatCours(template);

		model.addAttribute("resultList", list);
		model.addAttribute("resultCnt", Integer.toString(totCnt));
		model.addAttribute("articleVO", boardVO);
		model.addAttribute("boardMasterVO", master);
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("noticeList", noticeList);

		model.addAttribute("preview", "true");

		return "egovframework/com/admin/cop/bbs/ArticleList";
	}

	/**
	 * 미리보기 커뮤니티 메인페이지를 조회한다.
	 * 
	 * @param cmmntyVO
	 * @param sessionVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/cop/bbs/previewBlogMainPage.do")
	public String previewBlogMainPage(@ModelAttribute("searchVO") BoardVO boardVO, ModelMap model) throws Exception {

		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated(); // KISA 보안취약점 조치 (2018-12-10, 이정은)

		String tmplatCours = boardVO.getSearchWrd();

		BlogVO master = new BlogVO();
		master.setBlogNm("미리보기 블로그");
		master.setBlogIntrcn("미리보기를 위한 블로그입니다.");
		master.setUseAt("Y");
		master.setFrstRegisterId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());

		boardVO.setFrstRegisterId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());

		// 블로그 카테고리관리 권한(로그인 한 사용자만 가능)
		int loginUserCnt = ArticleService.selectLoginUser(boardVO);

		// 블로그 게시판 제목 추출
		List<BoardVO> blogNameList = new ArrayList<BoardVO>();

		BoardVO target = null;
		target = new BoardVO();
		target.setBbsNm("블로그게시판#1");

		blogNameList.add(target);

		if (user != null) {
			model.addAttribute("sessionUniqId", user.getUniqId());
		}

		model.addAttribute("articleVO", boardVO);
		model.addAttribute("boardMasterVO", master);
		model.addAttribute("blogNameList", blogNameList);
		model.addAttribute("loginUserCnt", 1);

		model.addAttribute("preview", "true");

		// 안전한 경로 문자열로 조치
		tmplatCours = EgovWebUtil.filePathBlackList(tmplatCours);

		// 화이트 리스트 체크
		List<TemplateInfVO> templateWhiteList = TemplateManageService.selectTemplateWhiteList();
		LOGGER.debug("Template > WhiteList Count = {}", templateWhiteList.size());
		if (tmplatCours == null)
			tmplatCours = "";
		for (TemplateInfVO templateInfVO : templateWhiteList) {
			LOGGER.debug("Template > whiteList TmplatCours = " + templateInfVO.getTmplatCours());
			if (tmplatCours.equals(templateInfVO.getTmplatCours())) {
				return tmplatCours;
			}
		}

		LOGGER.debug("Template > WhiteList mismatch! Please check Admin page!");
		return "egovframework/com/admin/cmm/egovError";
	}
	
	//-------------------------------------------------
	/**
	 * [추가] 전체 게시물에 대한 목록을 조회한다. - 2021.04.20
	 * 
	 * @param boardVO
	 * @param sessionVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/admin/cop/bbs/allArticleList.do")
	public String allArticleList(@ModelAttribute("searchVO") BoardVO boardVO, ModelMap model) throws Exception {
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
	
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated(); // KISA 보안취약점 조치 (2018-12-10, 이정은)
		
		if (!isAuthenticated ) {
			return "forward:/uat/uia/LoginUsr.do";
		}

		boardVO.setPageUnit(propertyService.getInt("pageUnit"));
		boardVO.setPageSize(propertyService.getInt("pageSize"));

		PaginationInfo paginationInfo = new PaginationInfo();

		paginationInfo.setCurrentPageNo(boardVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(boardVO.getPageUnit());
		paginationInfo.setPageSize(boardVO.getPageSize());

		boardVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		boardVO.setLastIndex(paginationInfo.getLastRecordIndex());
		boardVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		Map<String, Object> map = ArticleService.allArticleList(boardVO);
		int totCnt = Integer.parseInt((String) map.get("resultCnt"));
		
		BoardMasterVO boardMasterVO = new BoardMasterVO();
		Map<String, Object> bbsList = BBSMasterService.selectBBSList(boardMasterVO);	
	
		paginationInfo.setTotalRecordCount(totCnt);

		if (user != null) {
			model.addAttribute("sessionUniqId", user.getUniqId());
		}

		model.addAttribute("resultList", map.get("resultList"));
		model.addAttribute("resultCnt", map.get("resultCnt"));
		model.addAttribute("articleVO", boardVO);
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("bbsList", bbsList.get("resultList"));	
		
		return "egovframework/com/admin/cop/bbs/AllArticleList";
	}
	
	/**
	 * [추가] 2021.04.22
	 * 게시물관리에서 게시물을 등록페이지. 
	 *
	 *
	 */
	@RequestMapping("/cop/bbs/allInsertArticleView.do")
	public String allInsertArticleView(@ModelAttribute("searchVO") BoardVO boardVO, ModelMap model, BindingResult bindingResult) 
			throws Exception {
		
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
    		return "forward:/uat/uia/LoginUsr.do";
    	}
    	
		BoardMasterVO boardMasterVO = new BoardMasterVO();
		boardMasterVO.setUniqId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
		Map<String, Object> map = BBSMasterService.selectBBSList(boardMasterVO);
		model.addAttribute("articleVO", boardVO);
		model.addAttribute("resultList", map.get("resultList"));		
		
    	return "egovframework/com/web/board/write";
	}
	
	/**
	 * [추가] 2021.04.23
	 * 게시물 등록. 
	 *
	 *
	 */
	@RequestMapping("/cop/bbs/allInsertArticle.do")
	public String allInsertArticle(final MultipartHttpServletRequest multiRequest, @ModelAttribute("searchVO") BoardVO boardVO, 
			@ModelAttribute("bdMstr") BoardMaster bdMstr, @ModelAttribute("board") BoardVO board, BindingResult bindingResult, ModelMap model) throws Exception {
		
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

		if (!isAuthenticated) { // KISA 보안취약점 조치 (2018-12-10, 이정은)
			return "forward:/uat/uia/LoginUsr.do";
		}

		beanValidator.validate(board, bindingResult);
		if (bindingResult.hasErrors()) {

			BoardMasterVO boardMasterVO = new BoardMasterVO();
			boardMasterVO.setUniqId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
			Map<String, Object> map = BBSMasterService.selectBBSList(boardMasterVO);
			model.addAttribute("articleVO", boardVO);
			model.addAttribute("resultList", map.get("resultList"));		
			
	    	return "egovframework/com/web/board/write";
		}

		if (isAuthenticated) {		
			List<FileVO> result = null;
			String atchFileId = "";

			final Map<String, MultipartFile> files = multiRequest.getFileMap();
			if (!files.isEmpty()) {
				result = fileUtil.parseFileInf(files, "BBS_", 0, "", "");
				atchFileId = fileMngService.insertFileInfs(result);
			}
			board.setAtchFileId(atchFileId);
			board.setFrstRegisterId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
			board.setBbsId(boardVO.getBbsId());
			board.setBlogId(boardVO.getBlogId());

			// 익명등록 처리
			if (board.getAnonymousAt() != null && board.getAnonymousAt().equals("Y")) {
				board.setNtcrId("anonymous"); // 게시물 통계 집계를 위해 등록자 ID 저장
				board.setNtcrNm("익명"); // 게시물 통계 집계를 위해 등록자 Name 저장
				board.setFrstRegisterId("anonymous");

			} else {
				board.setNtcrId((user == null || user.getUniqId() == null) ? "" : user.getUniqId()); // 게시물 통계 집계를 위해		// 등록자 ID 저장
				board.setNtcrNm((user == null || user.getName() == null) ? "" : user.getName()); // 게시물 통계 집계를 위해 등록자		// Name 저장
			}

			board.setNttCn(unscript(board.getNttCn())); // XSS 방지
			ArticleService.insertArticle(board);
		}
		
		// status.setComplete();
		if (boardVO.getBlogAt().equals("Y")) {
			return "forward:/cop/bbs/selectArticleBlogList.do";
		} else {
			return "forward:/admin/cop/bbs/allArticleList.do";
		}

	}
}
