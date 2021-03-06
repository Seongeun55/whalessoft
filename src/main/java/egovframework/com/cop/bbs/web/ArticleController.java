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
 * ????????? ????????? ?????? ???????????? ?????????
 * 
 * @author ???????????????????????? ?????????
 * @since 2009.06.01
 * @version 1.0
 * @see
 *
 *      <pre>
 * << ????????????(Modification Information) >>
 *   
 *   ?????????              ?????????          ????????????
 *  ----------   -------   ---------------------------
 *  2009.03.19   ?????????          ?????? ??????
 *  2009.06.29   ?????????          2?????? ?????? ?????? (????????????, ???????????????)
 *  2011.07.01   ?????????          ??????, ?????????, ????????? ?????? ????????? ????????? ??????
 *  2011.08.26   ?????????          IncludedInfo annotation ??????
 *  2011.09.07   ?????????          ?????? ????????? ????????? ????????? ???????????? ???????????? ?????? ??????
 *  2016.06.13   ?????????          ????????????????????? 3.6 ??????
 *  2019.05.17   ?????????          KISA ????????? ?????? ??? ??????
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
	 * XSS ?????? ??????.
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
	 * ????????? ???????????? ???????????? ?????? ????????? ????????????.
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

		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated(); // KISA ??????????????? ?????? (2018-12-10, ?????????)

		if (!isAuthenticated) {
			return "forward:/uat/uia/LoginUsr.do";
		}

		BoardMasterVO vo = new BoardMasterVO();

		vo.setBbsId(boardVO.getBbsId());
		vo.setUniqId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
		BoardMasterVO master = BBSMasterService.selectBBSMasterInf(vo);

		// ???????????? ????????? ??????????????? ??????
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

		// ???????????? ??????
		List<BoardVO> noticeList = ArticleService.selectNoticeArticleList(boardVO);

		paginationInfo.setTotalRecordCount(totCnt);

		// -------------------------------
		// ?????? BBS template ??????
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
	 * ???????????? ???????????? ?????? ????????? ????????????.
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

		if(!master.getAuthList().equals("GUE")) { // ?????? ?????? ????????? ??????(?????? & ?????????)			
			if (!isAuthenticated ) { // ???????????? ????????? ?????? ??????
				return "forward:/uat/uia/LoginUsr.do";
			}
			if(!master.getAuthList().equals(user.getUserSe()) && !user.getUserSe().equals("USR")) { // ???????????? ????????? ?????? ????????? ?????? ?????? ??????
				return "egovframework/com/admin/cmm/error/accessDenied";
			}
		}
		
		/* ???????????? ????????? ??????????????? ??????
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

		// ???????????? ??????
		List<BoardVO> noticeList = ArticleService.selectNoticeArticleList(boardVO);

		paginationInfo.setTotalRecordCount(totCnt);

		// -------------------------------
		// ?????? BBS template ??????
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
		
		//?????? ?????????????????? ?????? - 2021.04.29
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
	 * [??????] ????????????????????? ???????????? ?????? ?????? ????????? ????????????.
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

		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated(); // KISA ??????????????? ?????? (2018-12-10, ?????????)

		if (!isAuthenticated) {
			return "forward:/uat/uia/LoginUsr.do";
		}

		boardVO.setLastUpdusrId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
		BoardVO vo = ArticleService.selectArticleDetail(boardVO);

		model.addAttribute("result", vo);
		model.addAttribute("sessionUniqId", (user == null || user.getUniqId() == null) ? "" : user.getUniqId());

		// ???????????? ???????????? ?????? ??????
		if (!EgovStringUtil.isEmpty(vo.getSecretAt()) && vo.getSecretAt().equals("Y")
				&& !((user == null || user.getUniqId() == null) ? "" : user.getUniqId()).equals(vo.getFrstRegisterId()))
			return "forward:/cop/bbs/selectArticleList.do";

		// ----------------------------
		// template ?????? (?????? BBS template ?????? ??????)
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
		// 2009.06.29 : 2?????? ?????? ??????
		// 2011.07.01 : ??????, ????????? ?????? ????????? ????????? ??????
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
	 * ???????????? ?????? ?????? ????????? ????????????.
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
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated(); // KISA ??????????????? ?????? (2018-12-10, ?????????)
	
		BoardMasterVO master = new BoardMasterVO();

		master.setBbsId(boardVO.getBbsId());
		master.setUniqId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());

		BoardMasterVO masterVo = BBSMasterService.selectBBSMasterInf(master);
		
		if(!masterVo.getAuthRead().equals("GUE")) { // ?????? ?????? ????????? ??????(?????? & ?????????)			
			if (!isAuthenticated ) { // ???????????? ????????? ?????? ??????
				return "forward:/uat/uia/LoginUsr.do";
			}
			if(!masterVo.getAuthRead().equals(user.getUserSe()) && !user.getUserSe().equals("USR")) { // ???????????? ????????? ?????? ????????? ?????? ?????? ??????
				return "egovframework/com/admin/cmm/error/accessDenied";
			}
		}
		
		boardVO.setLastUpdusrId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
		BoardVO vo = ArticleService.selectArticleDetail(boardVO);
		model.addAttribute("result", vo);
		model.addAttribute("sessionUniqId", (user == null || user.getUniqId() == null) ? "" : user.getUniqId());

		// ???????????? ???????????? ?????? ??????
		if (!EgovStringUtil.isEmpty(vo.getSecretAt()) && vo.getSecretAt().equals("Y")
				&& !((user == null || user.getUniqId() == null) ? "" : user.getUniqId()).equals(vo.getFrstRegisterId()))
			return "forward:/admin/cop/bbs/selectArticleList.do";

		// ----------------------------
		// template ?????? (?????? BBS template ?????? ??????)
		// ----------------------------
	
		if (masterVo.getTmplatCours() == null || masterVo.getTmplatCours().equals("")) {
			masterVo.setTmplatCours("/css/egovframework/com/cop/tpl/egovBaseTemplate.css");
			return "egovframework/com/admin/cop/bbs/ArticleDetail";
		}
		String link = masterVo.getTmplatCours() + "/view";

		// ----------------------------
		// 2009.06.29 : 2?????? ?????? ??????
		// 2011.07.01 : ??????, ????????? ?????? ????????? ????????? ??????
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
	 * [??????] ????????????????????? ????????? ????????? ?????? ?????????????????? ????????????. 2021.04.21
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
		// ?????? BBS template ??????
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
	 * ????????? ????????? ?????? ?????????????????? ????????????.
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

		if(!master.getAuthWrite().equals("GUE")) { // ?????? ?????? ????????? ??????(?????? & ?????????)			
			if (!isAuthenticated ) { // ???????????? ????????? ?????? ??????
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
		// ?????? BBS template ??????
		// ----------------------------
		if (master.getTmplatCours() == null || master.getTmplatCours().equals("")) {
			master.setTmplatCours("/css/egovframework/com/cop/tpl/egovBaseTemplate.css");
		}
		String link = master.getTmplatCours() + "/write";
		
		model.addAttribute("articleVO", boardVO);
		model.addAttribute("boardMasterVO", master);
		//// -----------------------------
		System.out.println("?????? : " + link);

		return link;
	}

	/**
	 * ????????????????????? ???????????? ????????????.
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

		if (!isAuthenticated) { // KISA ??????????????? ?????? (2018-12-10, ?????????)
			return "forward:/uat/uia/LoginUsr.do";
		}

		beanValidator.validate(board, bindingResult);
		if (bindingResult.hasErrors()) {

			BoardMasterVO master = new BoardMasterVO();

			master.setBbsId(boardVO.getBbsId());
			master.setUniqId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());

			master = BBSMasterService.selectBBSMasterInf(master);

			// ----------------------------
			// ?????? BBS template ??????
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

			// ???????????? ??????
			if (board.getAnonymousAt() != null && board.getAnonymousAt().equals("Y")) {
				board.setNtcrId("anonymous"); // ????????? ?????? ????????? ?????? ????????? ID ??????
				board.setNtcrNm("??????"); // ????????? ?????? ????????? ?????? ????????? Name ??????
				board.setFrstRegisterId("anonymous");

			} else {
				board.setNtcrId((user == null || user.getUniqId() == null) ? "" : user.getUniqId()); // ????????? ?????? ????????? ??????
																										// ????????? ID ??????
				board.setNtcrNm((user == null || user.getName() == null) ? "" : user.getName()); // ????????? ?????? ????????? ?????? ?????????
																									// Name ??????

			}

			board.setNttCn(unscript(board.getNttCn())); // XSS ??????
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
	 * ???????????? ????????????.
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
			// ?????? BBS template ??????
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

		// ???????????? ??????
		if (board.getAnonymousAt() != null && board.getAnonymousAt().equals("Y")) {
			board.setNtcrId("anonymous"); // ????????? ?????? ????????? ?????? ????????? ID ??????
			board.setNtcrNm("??????"); // ????????? ?????? ????????? ?????? ????????? Name ??????
			board.setFrstRegisterId("anonymous");

		} else {
			board.setNtcrId((user == null || user.getUniqId() == null) ? "" : user.getUniqId()); // ????????? ?????? ????????? ??????
																									// ????????? ID ??????
			board.setNtcrNm((user == null || user.getName() == null) ? boardVO.getFrstRegisterNm() : user.getName()); // ????????? ?????? ????????? ?????? ?????????
																								// Name ??????
		}

		board.setNttCn(unscript(board.getNttCn())); // XSS ??????
		ArticleService.insertArticle(board);
		
		// status.setComplete();
		if (boardVO.getBlogAt().equals("Y")) {
			return "forward:/cop/bbs/selectArticleBlogList.do";
		} else {
			return "redirect:/board/list.do?bbsId=" + boardVO.getBbsId()+"&menuNo="+request.getParameter("menuNo");
		}

	}

	/**
	 * ???????????? ?????? ?????? ????????? ?????? ?????????????????? ????????????.
	 * 
	 * @param boardVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/cop/bbs/replyArticleView.do")
	public String addReplyBoardArticle(@ModelAttribute("searchVO") BoardVO boardVO, ModelMap model) throws Exception {
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();// KISA ??????????????? ?????? (2018-12-10, ?????????)

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
		// ?????? BBS template ??????
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
	 * ???????????? ?????? ????????? ????????????.
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

		if (!isAuthenticated) { // KISA ??????????????? ?????? (2018-12-10, ?????????)
			return "forward:/uat/uia/LoginUsr.do";
		}

		beanValidator.validate(board, bindingResult);
		if (bindingResult.hasErrors()) {
			BoardMasterVO master = new BoardMasterVO();

			master.setBbsId(boardVO.getBbsId());
			master.setUniqId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());

			master = BBSMasterService.selectBBSMasterInf(master);

			// ----------------------------
			// ?????? BBS template ??????
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

			// ???????????? ??????
			if (board.getAnonymousAt() != null && board.getAnonymousAt().equals("Y")) {
				board.setNtcrId("anonymous"); // ????????? ?????? ????????? ?????? ????????? ID ??????
				board.setNtcrNm("??????"); // ????????? ?????? ????????? ?????? ????????? Name ??????
				board.setFrstRegisterId("anonymous");

			} else {
				board.setNtcrId((user == null || user.getId() == null) ? "" : user.getId()); // ????????? ?????? ????????? ?????? ????????? ID ??????
				board.setNtcrNm((user == null || user.getName() == null) ? "" : user.getName()); // ????????? ?????? ????????? ?????? ?????????
																									// Name ??????

			}
			board.setNttCn(unscript(board.getNttCn())); // XSS ??????

			ArticleService.insertArticle(board);
		}

		return "forward:/admin/cop/bbs/selectArticleList.do";
	}

	/**
	 * ???????????????????????? ????????? ????????? ?????? ?????????????????? ????????????.
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
		// ?????? BBS template ??????
		// ----------------------------
		if (bmvo.getTmplatCours() == null || bmvo.getTmplatCours().equals("")) {
			bmvo.setTmplatCours("/css/egovframework/com/cop/tpl/egovBaseTemplate.css");
		}

		// ?????? ???????????? ?????? ?????? ??????
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
	 * [??????] ????????? ????????? ?????? ?????????????????? ????????????. - 2021.06.04
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

		BoardMasterVO bmvo = new BoardMasterVO();	//????????? ??????
		BoardVO bdvo = new BoardVO();			//????????? ??????

		vo.setBbsId(boardVO.getBbsId());

		bmvo.setBbsId(boardVO.getBbsId());
		bmvo.setUniqId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());

		bmvo = BBSMasterService.selectBBSMasterInf(bmvo);
		bdvo = ArticleService.selectArticleDetail(boardVO);

		if(!bmvo.getAuthWrite().equals("GUE")) { // ?????? ?????? ????????? ??????(?????? & ?????????)			
			if (!isAuthenticated ) { // ???????????? ????????? ?????? ??????
				return "forward:/uat/uia/LoginUsr.do";
			}
			if(!bmvo.getAuthWrite().equals(user.getUserSe()) && !user.getUserSe().equals("USR")) { // ???????????? ????????? ?????? ????????? ?????? ?????? ??????
				return "egovframework/com/admin/cmm/error/accessDenied";
			}
		}

		// ----------------------------
		// ?????? BBS template ??????
		// ----------------------------
		if (bmvo.getTmplatCours() == null || bmvo.getTmplatCours().equals("")) {
			bmvo.setTmplatCours("/css/egovframework/com/cop/tpl/egovBaseTemplate.css");
		}

		String link = bmvo.getTmplatCours() + "/modify";
		
		// ?????? ???????????? ?????? ?????? ??????
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
	 * ????????? ???????????? ?????? ????????? ????????????.
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

		if (!isAuthenticated) { // KISA ??????????????? ?????? (2018-12-10, ?????????)
			return "forward:/uat/uia/LoginUsr.do";
		}

		// --------------------------------------------------------------------------------------------
		// @ XSS ?????? ???????????? ?????? START
		// param1 : ???????????????ID(uniqId,esntlId)
		// --------------------------------------------------------
		LOGGER.debug("@ XSS ???????????? START ----------------------------------------------");
		// step1 DB?????? ?????? ???????????? uniqId ??????
		BoardVO vo = ArticleService.selectArticleDetail(boardVO);

		// step2 EgovXssChecker ??????????????? ????????? ????????????
		EgovXssChecker.checkerUserXss(multiRequest, vo.getFrstRegisterId());
		LOGGER.debug("@ XSS ???????????? END ------------------------------------------------");
		// --------------------------------------------------------
		// @ XSS ?????? ???????????? ?????? END
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

			board.setNtcrNm(""); // dummy ?????? ?????? (????????? ?????? ?????? validator ????????? ?????? dummy??? ?????????)
			board.setPassword(""); // dummy ?????? ?????? (????????? ?????? ?????? validator ????????? ?????? dummy??? ?????????)

			board.setNttCn(unscript(board.getNttCn())); // XSS ??????

			ArticleService.updateArticle(board);
		}

		return "forward:/admin/cop/bbs/selectArticleList.do";
	}

	/**
	 * [??????] ???????????? ?????? ????????? ????????????. - 2021.06.07
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
		// @ XSS ?????? ???????????? ?????? START
		// param1 : ???????????????ID(uniqId,esntlId)
		// --------------------------------------------------------
		LOGGER.debug("@ XSS ???????????? START ----------------------------------------------");
		// step1 DB?????? ?????? ???????????? uniqId ??????
		BoardVO vo = ArticleService.selectArticleDetail(boardVO);

		// step2 EgovXssChecker ??????????????? ????????? ????????????
		EgovXssChecker.checkerUserXss(multiRequest, vo.getFrstRegisterId());
		LOGGER.debug("@ XSS ???????????? END ------------------------------------------------");
		// --------------------------------------------------------
		// @ XSS ?????? ???????????? ?????? END
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

		if(!bmvo.getAuthWrite().equals("GUE")) { // ?????? ?????? ????????? ??????(?????? & ?????????)			
			if (!isAuthenticated ) { // ???????????? ????????? ?????? ??????
				return "forward:/uat/uia/LoginUsr.do";
			}
			if(!bmvo.getAuthWrite().equals(user.getUserSe()) && !user.getUserSe().equals("USR")) { // ???????????? ????????? ?????? ????????? ?????? ?????? ??????
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

		board.setNtcrNm(""); // dummy ?????? ?????? (????????? ?????? ?????? validator ????????? ?????? dummy??? ?????????)
		board.setPassword(""); // dummy ?????? ?????? (????????? ?????? ?????? validator ????????? ?????? dummy??? ?????????)

		board.setNttCn(unscript(board.getNttCn())); // XSS ??????

		ArticleService.updateArticle(board);

		return "redirect:/board/list.do?bbsId=" + boardVO.getBbsId()+"&menuNo="+request.getParameter("menuNo");
	}
	
	  
    /**
     * [??????]-2021.06.07
     * ???????????? ????????? ??????,?????????????????? ???????????? ???????????? ??????
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
     * [??????]-2021.06.07
     * ?????????????????? ???????????? ??????
     * @throws Exception 
     *
     */
    @RequestMapping("/cop/bbs/guestArticle.do")
    public String guestArticle(ModelMap model, HttpServletRequest request, HttpServletResponse response, @ModelAttribute("searchVO") BoardVO boardVO) throws Exception {
    
    	BoardVO vo = ArticleService.selectArticleDetail(boardVO);
    	
    	String password = request.getParameter("pass");	// ???????????? ??????????????? ????????? ????????????		
		String state = request.getParameter("state");
		String menuNo = request.getParameter("menuNo");

		PrintWriter out = response.getWriter();
		response.setContentType("text/html; charset=UTF-8");

		if(password.equals(vo.getPassword())) {
			if(state.equals("update")) {	//????????? ???
				out.print("<script lauguage='javascript'>");
				out.print("opener.location.href='/board/modify.do?parnts="+vo.getParnts()+"&sortOrdr="+vo.getSortOrdr()+"&replyLc="+vo.getReplyLc()+"&nttSj="+vo.getNttSj()
											+"&nttId="+vo.getNttId()+"&bbsId="+vo.getBbsId()+"&menuNo="+menuNo+"';");
				out.print("self.close();");
				out.print("</script>");
				out.flush();
				out.close();		
				return "blank";
			}else {	//?????? 
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
			out.print("alert('??????????????? ???????????? ????????????.');");
			out.print("location.href='"+request.getHeader("referer")+"';");
			out.print("</script>");
			out.flush();
			out.close();
			return "blank";
		}    	
    }
	
	/**
	 * ??????????????? ???????????? ?????? ????????? ????????????.
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
		// @ XSS ?????? ???????????? ?????? START
		// param1 : ???????????????ID(uniqId,esntlId)
		// --------------------------------------------------------
		LOGGER.debug("@ XSS ???????????? START ----------------------------------------------");
		// step1 DB?????? ?????? ???????????? uniqId ??????
		BoardVO vo = ArticleService.selectArticleDetail(boardVO);

		// step2 EgovXssChecker ??????????????? ????????? ????????????
		EgovXssChecker.checkerUserXss(request, vo.getFrstRegisterId());
		LOGGER.debug("@ XSS ???????????? END ------------------------------------------------");
		// --------------------------------------------------------
		// @ XSS ?????? ???????????? ?????? END
		// --------------------------------------------------------------------------------------------

		BoardVO bdvo = ArticleService.selectArticleDetail(boardVO);
		// ?????? ???????????? ?????? ?????? ??????
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
	 * [??????] ???????????? ?????? ????????? ????????????. - 2021.06.08
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
		// @ XSS ?????? ???????????? ?????? START
		// param1 : ???????????????ID(uniqId,esntlId)
		// --------------------------------------------------------
		LOGGER.debug("@ XSS ???????????? START ----------------------------------------------");
		// step1 DB?????? ?????? ???????????? uniqId ??????
		BoardVO vo = ArticleService.selectArticleDetail(boardVO);

		// step2 EgovXssChecker ??????????????? ????????? ????????????
		EgovXssChecker.checkerUserXss(request, vo.getFrstRegisterId());
		LOGGER.debug("@ XSS ???????????? END ------------------------------------------------");
		// --------------------------------------------------------
		// @ XSS ?????? ???????????? ?????? END
		// --------------------------------------------------------------------------------------------
		boardVO.setFrstRegisterId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
		BoardMasterVO bmvo = new BoardMasterVO();

		bmvo.setBbsId(boardVO.getBbsId());
		bmvo.setUniqId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());

		bmvo = BBSMasterService.selectBBSMasterInf(bmvo);
		
		if(!bmvo.getAuthRead().equals("GUE")) { // ?????? ?????? ????????? ??????(?????? & ?????????)			
			if (!isAuthenticated ) { // ???????????? ????????? ?????? ??????
				return "forward:/uat/uia/LoginUsr.do";
			}
			if(!bmvo.getAuthRead().equals(user.getUserSe()) && !user.getUserSe().equals("USR")) { // ???????????? ????????? ?????? ????????? ?????? ?????? ??????
				return "egovframework/com/admin/cmm/error/accessDenied";
			}
		}
		
		BoardVO bdvo = ArticleService.selectArticleDetail(boardVO);
		// ?????? ???????????? ?????? ?????? ??????
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
	 * ???????????? ?????? ????????? ????????????.
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

		if (!isAuthenticated) { // KISA ??????????????? ?????? (2018-12-10, ?????????)
			return "forward:/uat/uia/LoginUsr.do";
		}

		// ?????? ??? ?????? ?????? ????????? ?????? ??????
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
	 * ???????????? ?????? ????????? ????????????.
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

		if (!isAuthenticated) { // KISA ??????????????? ?????? (2018-12-10, ?????????)
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
	 * ???????????? ?????? ????????? ????????????.
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
	 * ????????? ????????? ?????? ?????? ????????? ????????????.
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

		if (!isAuthenticated) { // KISA ??????????????? ?????? (2018-12-10, ?????????)
			return "forward:/uat/uia/LoginUsr.do";
		}

		// ?????? ??? ?????? ?????? ????????? ?????? ??????
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
	 * ???????????? ???????????? ????????? ?????????????????? ????????????.
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

		if (!isAuthenticated) { // KISA ??????????????? ?????? (2018-12-10, ?????????)
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
	 * ???????????????
	 ********************/

	/**
	 * ????????? ???????????? ?????? ????????? ????????????.
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

		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated(); // KISA ??????????????? ?????? (2018-12-10, ?????????)

		if (!isAuthenticated) {
			return "forward:/uat/uia/LoginUsr.do";
		}

		BlogVO blogVo = new BlogVO();
		blogVo.setFrstRegisterId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
		blogVo.setBbsId(boardVO.getBbsId());
		blogVo.setBlogId(boardVO.getBlogId());
		BlogVO master = BBSMasterService.selectBlogDetail(blogVo);

		boardVO.setFrstRegisterId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());

		// ????????? ?????????????????? ??????(????????? ??? ???????????? ??????)
		int loginUserCnt = ArticleService.selectLoginUser(boardVO);

		// ????????? ????????? ?????? ??????
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
	 * ????????? ???????????? ?????? ?????? ???????????? ????????????.
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

		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated(); // KISA ??????????????? ?????? (2018-12-10, ?????????)

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

		// ???????????? ???????????? ?????? ??????
		if (!EgovStringUtil.isEmpty(vo.getSecretAt()) && vo.getSecretAt().equals("Y")
				&& !((user == null || user.getUniqId() == null) ? "" : user.getUniqId()).equals(vo.getFrstRegisterId()))
			mav.setViewName("forward:/admin/cop/bbs/selectArticleList.do");
		return mav;
	}

	/**
	 * ????????? ???????????? ?????? ?????? ????????? ????????????.
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

		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated(); // KISA ??????????????? ?????? (2018-12-10, ?????????)

		if (!isAuthenticated) {
			throw new IllegalAccessException("Login Required!");
		}

		BoardVO vo = ArticleService.selectArticleDetail(boardVO);

		// ----------------------------
		// ?????? ??????
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

		// ?????? ?????? END
		// ----------------------------

		List<BoardVO> blogCnList = ArticleService.selectArticleDetailCn(boardVO);
		ModelAndView mav = new ModelAndView("jsonView");

		// ?????? ????????? ??? ?????? ?????? ???????????? ???????????? ?????? ??????
		if (commentVO.isModified()) {
			commentVO.setCommentNo("");
			commentVO.setCommentCn("");
		}

		// ????????? ?????? ??????
		if (!commentVO.getCommentNo().equals("")) {
			mav.setViewName("forward:/cop/cmt/updateArticleCommentView.do");
		}

		mav.addObject("blogCnList", blogCnList);
		mav.addObject("resultUnder", vo);
		mav.addObject("paginationInfo", paginationInfo);
		mav.addObject("resultList", map.get("resultList"));
		mav.addObject("resultCnt", map.get("resultCnt"));
		mav.addObject("articleCommentVO", articleCommentVO); // validator ??????

		commentVO.setCommentCn(""); // ?????? ??? ?????? ?????? ??????

		// ???????????? ???????????? ?????? ??????
		if (!EgovStringUtil.isEmpty(vo.getSecretAt()) && vo.getSecretAt().equals("Y")
				&& !((user == null || user.getUniqId() == null) ? "" : user.getUniqId()).equals(vo.getFrstRegisterId()))
			mav.setViewName("forward:/admin/cop/bbs/selectArticleList.do");
		return mav;

	}

	/**
	 * ??????????????? ??????
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
	 * ???????????? ?????? ??????????????? ????????? ????????? ????????????.
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

		String template = boardVO.getSearchWrd(); // ????????? URL

		BoardMasterVO master = new BoardMasterVO();

		master.setBbsNm("???????????? ?????????");

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
		target.setNttSj("????????? ?????? ??????");
		target.setFrstRegisterId("ID");
		target.setFrstRegisterNm("?????????");
		target.setFrstRegisterPnttm("2019-01-01");
		target.setInqireCo(7);
		target.setParnts("0");
		target.setReplyAt("N");
		target.setReplyLc("0");
		target.setUseAt("Y");

		list.add(target);

		target = new BoardVO();
		target.setNttSj("????????? ?????? ?????? ??????");
		target.setFrstRegisterId("ID");
		target.setFrstRegisterNm("?????????");
		target.setFrstRegisterPnttm("2019-01-01");
		target.setInqireCo(7);
		target.setParnts("0");
		target.setReplyAt("N");
		target.setReplyLc("0");
		target.setUseAt("Y");

		list.add(target);

		boardVO.setSearchWrd("");

		int totCnt = list.size();

		// ???????????? ??????
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
	 * ???????????? ???????????? ?????????????????? ????????????.
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
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated(); // KISA ??????????????? ?????? (2018-12-10, ?????????)

		String tmplatCours = boardVO.getSearchWrd();

		BlogVO master = new BlogVO();
		master.setBlogNm("???????????? ?????????");
		master.setBlogIntrcn("??????????????? ?????? ??????????????????.");
		master.setUseAt("Y");
		master.setFrstRegisterId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());

		boardVO.setFrstRegisterId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());

		// ????????? ?????????????????? ??????(????????? ??? ???????????? ??????)
		int loginUserCnt = ArticleService.selectLoginUser(boardVO);

		// ????????? ????????? ?????? ??????
		List<BoardVO> blogNameList = new ArrayList<BoardVO>();

		BoardVO target = null;
		target = new BoardVO();
		target.setBbsNm("??????????????????#1");

		blogNameList.add(target);

		if (user != null) {
			model.addAttribute("sessionUniqId", user.getUniqId());
		}

		model.addAttribute("articleVO", boardVO);
		model.addAttribute("boardMasterVO", master);
		model.addAttribute("blogNameList", blogNameList);
		model.addAttribute("loginUserCnt", 1);

		model.addAttribute("preview", "true");

		// ????????? ?????? ???????????? ??????
		tmplatCours = EgovWebUtil.filePathBlackList(tmplatCours);

		// ????????? ????????? ??????
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
	 * [??????] ?????? ???????????? ?????? ????????? ????????????. - 2021.04.20
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
	
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated(); // KISA ??????????????? ?????? (2018-12-10, ?????????)
		
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
	 * [??????] 2021.04.22
	 * ????????????????????? ???????????? ???????????????. 
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
	 * [??????] 2021.04.23
	 * ????????? ??????. 
	 *
	 *
	 */
	@RequestMapping("/cop/bbs/allInsertArticle.do")
	public String allInsertArticle(final MultipartHttpServletRequest multiRequest, @ModelAttribute("searchVO") BoardVO boardVO, 
			@ModelAttribute("bdMstr") BoardMaster bdMstr, @ModelAttribute("board") BoardVO board, BindingResult bindingResult, ModelMap model) throws Exception {
		
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

		if (!isAuthenticated) { // KISA ??????????????? ?????? (2018-12-10, ?????????)
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

			// ???????????? ??????
			if (board.getAnonymousAt() != null && board.getAnonymousAt().equals("Y")) {
				board.setNtcrId("anonymous"); // ????????? ?????? ????????? ?????? ????????? ID ??????
				board.setNtcrNm("??????"); // ????????? ?????? ????????? ?????? ????????? Name ??????
				board.setFrstRegisterId("anonymous");

			} else {
				board.setNtcrId((user == null || user.getUniqId() == null) ? "" : user.getUniqId()); // ????????? ?????? ????????? ??????		// ????????? ID ??????
				board.setNtcrNm((user == null || user.getName() == null) ? "" : user.getName()); // ????????? ?????? ????????? ?????? ?????????		// Name ??????
			}

			board.setNttCn(unscript(board.getNttCn())); // XSS ??????
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
