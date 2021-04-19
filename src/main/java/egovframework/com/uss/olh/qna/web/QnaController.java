package egovframework.com.uss.olh.qna.web;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springmodules.validation.commons.DefaultBeanValidator;

import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.annotation.IncludedInfo;
import egovframework.com.cmm.service.CmmUseService;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.cmm.util.EgovXssChecker;
import egovframework.com.cmm.web.ComUtlController;
import egovframework.com.sym.mnu.mpm.service.MenuManageService;
import egovframework.com.sym.mnu.mpm.service.MenuManageVO;
import egovframework.com.uss.olh.qna.service.QnaService;
import egovframework.com.uss.olh.qna.service.QnaDefaultVO;
import egovframework.com.uss.olh.qna.service.QnaVO;
import egovframework.com.utl.fcc.service.EgovStringUtil;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
/**
*
* Q&A를 처리하는 Controller 클래스
* @author 공통서비스 개발팀 박정규
* @since 2009.04.01
* @version 1.0
* @see
*
* <pre>
* << 개정이력(Modification Information) >>
*
*   수정일     	수정자           			수정내용
*  ------------   --------    ---------------------------------------------
*   2009.04.01  	박정규          최초 생성
*   2011.08.26		정진오			IncludedInfo annotation 추가
*   2011.10.21		이기하			삭제시 비밀번호 확인 추가(최종감리 반영)
*   2016.08.05		김연호			표준프레임워크 3.6 개선
*
* </pre>
*/
@Controller
public class QnaController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(QnaController.class);
	
	@Resource(name = "QnaService")
	private QnaService QnaService;

	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	@Resource(name = "CmmUseService")
	private CmmUseService cmmUseService;

	/** EgovMessageSource */
	@Resource(name = "egovMessageSource")
	EgovMessageSource egovMessageSource;
	
	/** MenuManageService */
	@Resource(name = "meunManageService")
	private MenuManageService menuManageService;

	// Validation 관련
	@Autowired
	private DefaultBeanValidator beanValidator;
	
	/**
	 * Q&A정보 목록을 조회한다. (pageing)
	 * @param searchVO
	 * @param model
	 * @return	"/uss/olh/qna/EgovQnaListInqire"
	 * @throws Exception
	 */
	@IncludedInfo(name = "Q&A관리", order = 550, gid = 50)
	@RequestMapping(value = "/uss/olh/qna/selectQnaList.do")
	public String selectQnaList(HttpServletRequest request, @ModelAttribute("searchVO") QnaVO searchVO, ModelMap model) throws Exception {
		
		//문제점 : 세션에 저장된 값이 없을 때, 위 주소로 바로 들어오면 session & request값 모두 null이 된다.
		HttpSession session = request.getSession();
		System.out.println("확인 : request" + request.getAttribute("_access_"));
		System.out.println("확인 : session" + session.getAttribute("_access_"));
		if(request.getAttribute("_access_") != session.getAttribute("_access_")) {
			return "egovframework/com/admin/cmm/error/dataAccessFailure";
		}
		
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

		return "egovframework/com/web/board/qna/list";
	}
	
	/**
	 * Q&A정보 목록에 대한 상세정보를 조회한다.
	 * @param passwordConfirmAt
	 * @param qnaVO
	 * @param searchVO
	 * @param model
	 * @return	"/uss/olh/qna/EgovQnaDetail"
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping("/uss/olh/qna/selectQnaDetail.do")
	public String selectQnaDetail(HttpServletRequest request, @RequestParam("qaId") String qaId, QnaVO qnaVO, @ModelAttribute("searchVO") QnaDefaultVO searchVO, ModelMap model) throws Exception {
		/*
		System.out.println("============================================================");
		System.out.println();
		HttpSession session = request.getSession();
		//if(request.getAttribute("_access_") != session.getAttribute("_access_")) return 오류페이지;
		System.out.println("============================================================");*/
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		///////////////////////////////////////
		qnaVO.setQaId(qaId);
		
		//조회수 수정처리
		QnaService.updateQnaInqireCo(qnaVO);
		
		QnaVO vo = QnaService.selectQnaDetail(qnaVO);

		model.addAttribute("user", user);
		model.addAttribute("result", vo);

		return "egovframework/com/web/board/qna/view";
	}
	
	/**
	 * Q&A정보를 등록하기 위한 전 처리(인증체크)
	 * @param searchVO
	 * @param qnaManageVO
	 * @param model
	 * @return	"/uss/olh/qna/EgovQnaRegist"
	 * @throws Exception
	 */
	@RequestMapping("/uss/olh/qna/insertQnaView.do")
	public String insertQnaView(@ModelAttribute("searchVO") QnaVO searchVO, QnaVO qnaVO, Model model) throws Exception {

		/* 인증여부 체크
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

		if (!isAuthenticated) {	
			return "egovframework/com/admin/uat/uia/LoginUsr";
		}*/

		// 로그인VO에서  사용자 정보 가져오기
		LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

		String wrterNm = loginVO == null ? "" : EgovStringUtil.isNullToString(loginVO.getName()); // 사용자명
		String emailAdres = loginVO == null ? "" : EgovStringUtil.isNullToString(loginVO.getEmail()); // email 주소

		qnaVO.setWrterNm(wrterNm); // 작성자명
		qnaVO.setEmailAdres(emailAdres); // email 주소

		model.addAttribute("qnaVO", qnaVO);
		
		return "egovframework/com/web/board/qna/write";

	}
	
	/**
	 * Q&A정보를 등록한다.
	 * @param searchVO
	 * @param qnaVO
	 * @param bindingResult
	 * @return	"forward:/uss/olh/qna/selectQnaList.do"
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping("/uss/olh/qna/insertQna.do")
	public String insertQna(@ModelAttribute("searchVO") QnaVO searchVO, @ModelAttribute("qnaVO") QnaVO qnaVO, BindingResult bindingResult, ModelMap model) throws Exception {
		beanValidator.validate(qnaVO, bindingResult);

		if (bindingResult.hasErrors()) {
			return "egovframework/com/admin/uss/olh/qna/QnaRegist";
		}

		// 로그인VO에서  사용자 정보 가져오기
		LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

		String frstRegisterId = loginVO == null ? "" : EgovStringUtil.isNullToString(loginVO.getUniqId());

		qnaVO.setFrstRegisterId(frstRegisterId); // 최초등록자ID
		qnaVO.setLastUpdusrId(frstRegisterId); // 최종수정자ID

		// 작성비밀번호를 암호화 하기 위해서 Get
//		String writngPassword = qnaVO.getWritngPassword();

		// EgovFileScrty Util에 있는 암호화 모듈을 적용해서 암호화 한다.
//		qnaVO.setWritngPassword(EgovFileScrty.encode(writngPassword));

		QnaService.insertQna(qnaVO);

		return "redirect:/board/list.do?type=qna";
	}
	
	/**
	 * Q&A정보를 수정하기 위한 전 처리
	 * @param qnaVO
	 * @param searchVO
	 * @param model
	 * @return	"/uss/olh/qna/EgovQnaUpdt
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping("/uss/olh/qna/updateQnaView.do")
	public String updateQnaView(QnaVO qnaVO, @ModelAttribute("searchVO") QnaVO searchVO, ModelMap model) throws Exception {

		QnaVO vo = QnaService.selectQnaDetail(qnaVO);

		// 작성 비밀번호를 얻는다.
//		String writngPassword = vo.getWritngPassword();

		// EgovFileScrty Util에 있는 암호화 모듈을 적용해서 복호화한다.
//		vo.setWritngPassword(EgovFileScrty.decode(writngPassword));

		model.addAttribute("qnaVO", vo);

		return "egovframework/com/web/board/qna/modify";
	}
	
	/**
	 * Q&A정보를 수정처리한다.
	 * @param searchVO
	 * @param qnaVO
	 * @param bindingResult
	 * @return	"forward:/uss/olh/qna/selectQnaList.do"
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping("/uss/olh/qna/updateQna.do")
	public String updateQna(HttpServletRequest request, @ModelAttribute("searchVO") QnaVO searchVO, @ModelAttribute("qnaVO") QnaVO qnaVO, BindingResult bindingResult) throws Exception {

		// Validation
		beanValidator.validate(qnaVO, bindingResult);

		if (bindingResult.hasErrors()) {
			return "egovframework/com/admin/uss/olh/qna/QnaUpdt";
		}
		
    	//--------------------------------------------------------------------------------------------
    	// @ XSS 사용자권한체크 START
    	// param1 : 사용자고유ID(uniqId,esntlId)
    	//--------------------------------------------------------
    	LOGGER.debug("@ XSS 권한체크 START ----------------------------------------------");
    	//step1 DB에서 해당 게시물의 uniqId 조회
    	QnaVO vo = QnaService.selectQnaDetail(qnaVO);;
    	
    	//step2 EgovXssChecker 공통모듈을 이용한 권한체크
    	EgovXssChecker.checkerUserXss(request, vo.getFrstRegisterId()); 
      	LOGGER.debug("@ XSS 권한체크 END ------------------------------------------------");
    	//--------------------------------------------------------
    	// @ XSS 사용자권한체크 END
    	//--------------------------------------------------------------------------------------------

		// 로그인VO에서  사용자 정보 가져오기
		LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		String lastUpdusrId = loginVO == null ? "" : EgovStringUtil.isNullToString(loginVO.getUniqId());
		qnaVO.setLastUpdusrId(lastUpdusrId); // 최종수정자ID

		// 작성비밀번호를 암호화 하기 위해서 Get
//		String writngPassword = qnaManageVO.getWritngPassword();

		// EgovFileScrty Util에 있는 암호화 모듈을 적용해서 암호화 한다.
//		qnaManageVO.setWritngPassword(EgovFileScrty.encode(writngPassword));

		QnaService.updateQna(qnaVO);

		return "redirect:/qnz/list.do";

	}
	
	/**
	 * Q&A정보를 삭제처리한다.
	 * @param qnaVO
	 * @param searchVO
	 * @return	"forward:/uss/olh/qna/selectQnaList.do"
	 * @throws Exception
	 */
	@RequestMapping("/uss/olh/qna/deleteQna.do")
	public String deleteQna(HttpServletRequest request, QnaVO qnaVO, @ModelAttribute("searchVO") QnaVO searchVO) throws Exception {

    	//--------------------------------------------------------------------------------------------
    	// @ XSS 사용자권한체크 START
    	// param1 : 사용자고유ID(uniqId,esntlId)
    	//--------------------------------------------------------
    	LOGGER.debug("@ XSS 권한체크 START ----------------------------------------------");
    	
    	//step1 DB에서 해당 게시물의 uniqId 조회
    	QnaVO vo = QnaService.selectQnaDetail(qnaVO);;
    	
    	//step2 EgovXssChecker 공통모듈을 이용한 권한체크
    	EgovXssChecker.checkerUserXss(request, vo.getFrstRegisterId()); 
      	LOGGER.debug("@ XSS 권한체크 END ------------------------------------------------");
    	//--------------------------------------------------------
    	// @ XSS 사용자권한체크 END
    	//--------------------------------------------------------------------------------------------
    
		QnaService.deleteQna(qnaVO);

		return "redirect:/board/list.do?type=qna";
	}
}
