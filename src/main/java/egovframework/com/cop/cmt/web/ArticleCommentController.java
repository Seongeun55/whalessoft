package egovframework.com.cop.cmt.web;

import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springmodules.validation.commons.DefaultBeanValidator;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.cop.cmt.service.Comment;
import egovframework.com.cop.cmt.service.CommentVO;
import egovframework.com.cop.bbs.service.BBSMasterService;
import egovframework.com.cop.bbs.service.BoardMasterVO;
import egovframework.com.cop.bbs.service.BoardVO;
import egovframework.com.cop.cmt.service.ArticleCommentService;
import egovframework.com.utl.fcc.service.EgovStringUtil;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/**
 * 댓글 관리를 위한 컨트롤러 클래스
 * @author 공통서비스개발팀 신용호
 * @since 2016.07.22
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------       --------    ---------------------------
 *   2016.07.22   신용호              최초 생성
 *   2018.06.27     신용호		    댓글 등록후 처리 예외 수정
 * </pre>
 */

@Controller
public class ArticleCommentController {

	@Resource(name = "ArticleCommentService")
    protected ArticleCommentService ArticleCommentService;
    
    @Resource(name="propertiesService")
    protected EgovPropertyService propertyService;
    
    @Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;
    
	@Resource(name = "BBSMasterService")
	private BBSMasterService BBSMasterService;
    
    @Autowired
    private DefaultBeanValidator beanValidator;
    
    //protected Logger log = Logger.getLogger(this.getClass());
    
    /**
     * 댓글관리 목록 조회를 제공한다.
     * 
     * @param boardVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/cmt/selectArticleCommentList.do")
    public String selectArticleCommentList(@ModelAttribute("searchVO") CommentVO commentVO, BoardVO boardVO, ModelMap model, HttpServletRequest request) throws Exception {

    	CommentVO articleCommentVO = new CommentVO();
    	
		// 수정 처리된 후 댓글 등록 화면으로 처리되기 위한 구현
		if (commentVO.isModified()) {
		    commentVO.setCommentNo("");
		    commentVO.setCommentCn("");
		}
		
		// 수정을 위한 처리
		if (!commentVO.getCommentNo().equals("")) {
		    return "forward:/cop/cmt/updateArticleCommentView.do";
		}
		
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
   	 	// KISA 보안취약점 조치 (2018-12-10, 신용호)
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	
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
		
		model.addAttribute("sessionUniqId", user == null ? "" : EgovStringUtil.isNullToString(user.getUniqId()));
		
		commentVO.setWrterNm(user == null ? "" : EgovStringUtil.isNullToString(user.getName()));
	
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(commentVO.getSubPageIndex());
		paginationInfo.setRecordCountPerPage(commentVO.getSubPageUnit());
		paginationInfo.setPageSize(commentVO.getSubPageSize());
	
		commentVO.setSubFirstIndex(paginationInfo.getFirstRecordIndex());
		commentVO.setSubLastIndex(paginationInfo.getLastRecordIndex());
		commentVO.setSubRecordCountPerPage(paginationInfo.getRecordCountPerPage());
	
		Map<String, Object> map = ArticleCommentService.selectArticleCommentList(commentVO);
		int totCnt = Integer.parseInt((String)map.get("resultCnt"));
		
		paginationInfo.setTotalRecordCount(totCnt);
	
		model.addAttribute("resultList", map.get("resultList"));
		model.addAttribute("resultCnt", map.get("resultCnt"));
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("type", "body");	// 댓글 페이지 body import용
		
		model.addAttribute("articleCommentVO", articleCommentVO);	// validator 용도 	
		
		commentVO.setCommentCn("");	// 등록 후 댓글 내용 처리
		
		//[추가] 어디에서 들어왔는지에 따라 댓글 jsp 변경
 		String uri = request.getRequestURI();
 
		if(uri.contains("/admin")) {
			return "egovframework/com/admin/cop/cmt/AdminArticleCommentList";
		}
		
		return "egovframework/com/admin/cop/cmt/ArticleCommentList";
		
    }
    
    /**
     * 댓글을 등록한다.
     * 
     * @param commentVO
     * @param comment
     * @param bindingResult
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/cmt/insertArticleComment.do")
    public String insertArticleComment(@ModelAttribute("searchVO") CommentVO commentVO, @ModelAttribute("comment") Comment comment, BoardVO boardVO,
	    BindingResult bindingResult, ModelMap model, @RequestParam HashMap<String, String> map, HttpServletRequest request) throws Exception {
		
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
	
		beanValidator.validate(comment, bindingResult);
		if (bindingResult.hasErrors()) {
		    model.addAttribute("msg", "댓글내용은 필수 입력값입니다.");
		    
		    return "forward:/cop/bbs/selectArticleDetail.do";
		}
	
		BoardMasterVO vo = new BoardMasterVO();

		vo.setBbsId(boardVO.getBbsId());
		vo.setUniqId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
		BoardMasterVO master = BBSMasterService.selectBBSMasterInf(vo);

		if(!master.getAuthComment().equals("GUE")) { // 접근 권한 비회원 이상(회원 & 관리자)			
			if (!isAuthenticated ) { // 로그인이 안되어 있을 경우
				return "forward:/uat/uia/LoginUsr.do";
			}
			if(!master.getAuthComment().equals(user.getUserSe()) && !user.getUserSe().equals("USR")) { 
				return "egovframework/com/admin/cmm/error/accessDenied";
			}
		}
		
	    comment.setFrstRegisterId(user == null ? "" : EgovStringUtil.isNullToString(user.getUniqId()));
	    comment.setWrterId(user == null ? "" : EgovStringUtil.isNullToString(user.getUniqId()));
	    comment.setWrterNm(user == null ? commentVO.getWrterNm() : EgovStringUtil.isNullToString(user.getName()));
	    if(user==null) {
	    	comment.setCommentPassword(commentVO.getCommentPassword());
	    }
	    ArticleCommentService.insertArticleComment(comment);
	    
	    commentVO.setCommentCn("");
	    commentVO.setCommentNo("");
		
		String chkBlog = map.get("blogAt");
		
		if("Y".equals(chkBlog)){
			return "forward:/cop/bbs/selectArticleBlogList.do";
		}else{
			return "redirect:"+request.getHeader("referer");
		}
    }
    
    
    /**
     * 댓글을 삭제한다.
     * 
     * @param commentVO
     * @param comment
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/cmt/deleteArticleComment.do")
    public String deleteArticleComment(@ModelAttribute("searchVO") CommentVO commentVO, @ModelAttribute("comment") Comment comment, BoardVO boardVO,
    		ModelMap model, @RequestParam HashMap<String, String> map, HttpServletRequest request) throws Exception {
		@SuppressWarnings("unused")
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

		BoardMasterVO vo = new BoardMasterVO();

		vo.setBbsId(boardVO.getBbsId());
		vo.setUniqId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
		BoardMasterVO master = BBSMasterService.selectBBSMasterInf(vo);

		if(!master.getAuthComment().equals("GUE")) { // 접근 권한 비회원 이상(회원 & 관리자)			
			if (!isAuthenticated ) { // 로그인이 안되어 있을 경우
				return "forward:/uat/uia/LoginUsr.do";
			}
			if(!master.getAuthComment().equals(user.getUserSe()) && !user.getUserSe().equals("USR")) { 
				return "egovframework/com/admin/cmm/error/accessDenied";
			}
		}
	
		if (isAuthenticated) {
		    ArticleCommentService.deleteArticleComment(commentVO);
		}
		
		commentVO.setCommentCn("");
		commentVO.setCommentNo("");
		
		String chkBlog = map.get("blogAt");
		
		if("Y".equals(chkBlog)){
			return "forward:/cop/bbs/selectArticleBlogList.do";
		}else{
			return "redirect:"+request.getHeader("referer");
		}
    }
    
    /**
     * [추가]-2021.06.01
     * 비회원이 댓글을 삭제하기위한 페이지를 이동하기 위해
     *
     */
    @RequestMapping("/cop/cmt/deleteArticleCommentPre.do")
    public String deleteArticleCommentPre(ModelMap model, HttpServletRequest request) {
    	model.addAttribute("bbsId", request.getParameter("bbsId"));
    	model.addAttribute("commnetNo", request.getParameter("commnetNo"));

    	return "egovframework/com/web/board/passwordCheck";
    }
    
    /**
     * 비회원 댓글을 삭제한다.
     * 
     * @param commentVO
     * @param comment
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/cmt/guestDeleteArticleComment.do")
    public String guestDeleteArticleComment(@ModelAttribute("searchVO") CommentVO commentVO, @ModelAttribute("comment") Comment comment, BoardVO boardVO,
    		ModelMap model, @RequestParam HashMap<String, String> map, HttpServletRequest request, HttpServletResponse response) throws Exception {
		@SuppressWarnings("unused")
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

		/****************************************************/
		// 해당 게시판의 댓글에 대한 권한을 확인하기 위해서
		BoardMasterVO vo = new BoardMasterVO();

		vo.setBbsId(request.getParameter("bbsId"));
		vo.setUniqId((user == null || user.getUniqId() == null) ? "" : user.getUniqId());
		BoardMasterVO master = BBSMasterService.selectBBSMasterInf(vo);

		if(!master.getAuthComment().equals("GUE")) { // 접근 권한 비회원 이상(회원 & 관리자)			
			if (!isAuthenticated ) { // 로그인이 안되어 있을 경우
				return "forward:/uat/uia/LoginUsr.do";
			}
			if(!master.getAuthComment().equals(user.getUserSe()) && !user.getUserSe().equals("USR")) { 
				return "egovframework/com/admin/cmm/error/accessDenied";
			}
		}
		/****************************************************/
			
		String password = request.getParameter("pass");	// 비밀번호 확인창에서 입력한 비밀번호
		String commentNo = request.getParameter("commnetNo");	// 댓글의 고유 번호
		
		commentVO.setCommentNo(commentNo);
		
		CommentVO articleCommentVO = new CommentVO();
		articleCommentVO = ArticleCommentService.selectArticleCommentDetail(commentVO);
		
		PrintWriter out = response.getWriter();
		response.setContentType("text/html; charset=UTF-8");
		
		if (articleCommentVO.getCommentPassword().equals(password)) {
		    ArticleCommentService.deleteArticleComment(commentVO);
		    commentVO.setCommentCn("");
			commentVO.setCommentNo("");
			out.print("<script lauguage='javascript'>");
			out.print("alert('삭제되었습니다.');");
			out.print("opener.document.location.reload();");
			out.print("self.close();");
			out.print("</script>");
			out.flush();
			out.close();
			return "blank";
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
     * 댓글 수정 페이지로 이동한다.
     * 
     * @param commentVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/cmt/updateArticleCommentView.do")
    public String updateArticleCommentView(@ModelAttribute("searchVO") CommentVO commentVO, ModelMap model) throws Exception {

		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		 //KISA 보안취약점 조치 (2018-12-10, 신용호)
	    Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
	
	    if(!isAuthenticated) {
	    	return "forward:/uat/uia/LoginUsr.do";
	    }
	
		CommentVO articleCommentVO = new CommentVO();
		
		commentVO.setWrterNm(user == null ? "" : EgovStringUtil.isNullToString(user.getName()));
	
		commentVO.setSubPageUnit(propertyService.getInt("pageUnit"));
		commentVO.setSubPageSize(propertyService.getInt("pageSize"));
	
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(commentVO.getSubPageIndex());
		paginationInfo.setRecordCountPerPage(commentVO.getSubPageUnit());
		paginationInfo.setPageSize(commentVO.getSubPageSize());
	
		commentVO.setSubFirstIndex(paginationInfo.getFirstRecordIndex());
		commentVO.setSubLastIndex(paginationInfo.getLastRecordIndex());
		commentVO.setSubRecordCountPerPage(paginationInfo.getRecordCountPerPage());
	
		Map<String, Object> map = ArticleCommentService.selectArticleCommentList(commentVO);
		int totCnt = Integer.parseInt((String)map.get("resultCnt"));
		
		paginationInfo.setTotalRecordCount(totCnt);
	
		model.addAttribute("resultList", map.get("resultList"));
		model.addAttribute("resultCnt", map.get("resultCnt"));
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("type", "body");	// body import
		
		articleCommentVO = ArticleCommentService.selectArticleCommentDetail(commentVO);
		
		model.addAttribute("articleCommentVO", articleCommentVO);
		
		
		return "egovframework/com/admin/cop/cmt/ArticleCommentList";
    }
    
    
    /**
     * 댓글을 수정한다.
     * 
     * @param commentVO
     * @param comment
     * @param bindingResult
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/cop/cmt/updateArticleComment.do")
    public String updateArticleComment(@ModelAttribute("searchVO") CommentVO commentVO, @ModelAttribute("comment") Comment comment,
	    BindingResult bindingResult, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {
    
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

		beanValidator.validate(comment, bindingResult);
		if (bindingResult.hasErrors()) {
		    model.addAttribute("msg", "내용은 필수 입력 값입니다.");
		    
		    return "forward:/cop/bbs/selectArticleDetail.do";
		}
		
		if(user==null) {	//로그인 안되어있을 때			
			String wrterNm = comment.getWrterNm();		// 이름
			String commentPassword = comment.getCommentPassword();		// 비밀번호
			
			CommentVO articleCommentVO = new CommentVO();
			articleCommentVO = ArticleCommentService.selectArticleCommentDetail(commentVO);

			if(!articleCommentVO.getCommentPassword().equals(commentPassword) || !articleCommentVO.getWrterNm().equals(wrterNm)) {
				PrintWriter out = response.getWriter();
				response.setContentType("text/html; charset=UTF-8");
				out.print("<script lauguage='javascript'>");
				out.print("alert('이름과 비밀번호가 일치하지 않습니다.');");
				out.print("location.href='"+request.getHeader("referer")+"';");
				out.print("</script>");
				out.flush();
				out.close();
				
				return "blank";
			}
		}
		
	    comment.setLastUpdusrId(user == null ? "" : EgovStringUtil.isNullToString(user.getUniqId()));
	    
	    ArticleCommentService.updateArticleComment(comment);
	    
	    commentVO.setCommentCn("");
	    commentVO.setCommentNo("");

		return "redirect:"+request.getHeader("referer");
    }	
}
