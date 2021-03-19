package egovframework.com.uss.olh.qna.admin;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
public class AdminQnaController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AdminQnaController.class);
	
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

	// Validation 관련
	@Autowired
	private DefaultBeanValidator beanValidator;
	
	/**
	 * Q&A답변정보 목록을 조회한다. (pageing)
	 * @param searchVO
	 * @param model
	 * @return	"/uss/olh/qna/EgovQnaAnswerList"
	 * @throws Exception
	 */
	@IncludedInfo(name = "Q&A답변관리", order = 551, gid = 50)
	@RequestMapping(value = "/uss/olh/qna/selectQnaAnswerList.do")
	public String selectQnaAnswerList(@ModelAttribute("searchVO") QnaVO searchVO, ModelMap model) throws Exception {

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

		List<?> QnaAnswerList = QnaService.selectQnaAnswerList(searchVO);
		model.addAttribute("resultList", QnaAnswerList);

		int totCnt = QnaService.selectQnaAnswerListCnt(searchVO);
		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("paginationInfo", paginationInfo);

		return "egovframework/com/admin/uss/olh/qna/QnaAnswerList";
	}

	/**
	 * Q&A답변정보 목록에 대한 상세정보를 조회한다.
	 * @param qnaVO
	 * @param searchVO
	 * @param model
	 * @return	"/uss/olh/qna/EgovQnaAnswerDetail"
	 * @throws Exception
	 */
	@RequestMapping("/uss/olh/qna/selectQnaAnswerDetail.do")
	public String selectQnaAnswerDetail(QnaVO qnaVO, @ModelAttribute("searchVO") QnaVO searchVO, ModelMap model) throws Exception {

		QnaVO vo = QnaService.selectQnaDetail(qnaVO);

		model.addAttribute("result", vo);

		return "egovframework/com/admin/uss/olh/qna/QnaAnswerDetail";
	}
	
	/**
	 * Q&A답변정보를 수정하기 위한 전 처리(공통코드 처리)
	 * @param qnaVO
	 * @param searchVO
	 * @param model
	 * @return	"/uss/olh/qna/EgovQnaAnswerUpdt"
	 * @throws Exception
	 */
	@RequestMapping("/uss/olh/qna/updateQnaAnswerView.do")
	public String updateQnaAnswerView(QnaVO qnaVO, @ModelAttribute("searchVO") QnaVO searchVO, ModelMap model) throws Exception {

		// 공통코드를 가져오기 위한 Vo
		ComDefaultCodeVO vo = new ComDefaultCodeVO();
		vo.setCodeId("COM028");

		List<?> _result = cmmUseService.selectCmmCodeDetail(vo);
		model.addAttribute("qnaProcessSttusCode", _result);

		qnaVO = QnaService.selectQnaDetail(qnaVO);
		model.addAttribute("qnaVO", qnaVO);

		return "egovframework/com/admin/uss/olh/qna/EgovQnaAnswerUpdt";
	}
	
	/**
	 * Q&A답변정보를 수정처리한다.
	 * @param qnaVO
	 * @param searchVO
	 * @return	"forward:/uss/olh/qnm/selectQnaAnswerList.do"
	 * @throws Exception
	 */
	@RequestMapping("/uss/olh/qna/updateQnaAnswer.do")
	public String updateQnaAnswer(QnaVO qnaVO, @ModelAttribute("searchVO") QnaVO searchVO) throws Exception {

		// 로그인VO에서  사용자 정보 가져오기
		LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		String lastUpdusrId = loginVO == null ? "" : EgovStringUtil.isNullToString(loginVO.getUniqId());
		qnaVO.setLastUpdusrId(lastUpdusrId); // 최종수정자ID

		QnaService.updateQnaAnswer(qnaVO);

		return "forward:/uss/olh/qna/selectQnaAnswerList.do";

	}
}
