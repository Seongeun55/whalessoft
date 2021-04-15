package egovframework.com.uss.olh.faq.web;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.service.FileMngService;
import egovframework.com.cmm.service.EgovFileMngUtil;
import egovframework.com.uss.olh.faq.service.FaqService;
import egovframework.com.uss.olh.faq.service.FaqVO;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/**
*
* FAQ내용을 처리하는 비즈니스 구현 클래스
* @author 공통서비스 개발팀 박정규
* @since 2009.04.01
* @version 1.0
* @see
*
* <pre>
* << 개정이력(Modification Information) >>
*
*   수정일      수정자           수정내용
*  -------    --------    ---------------------------
*   2009.04.01  박정규          최초 생성
*   2011.8.26	정진오			IncludedInfo annotation 추가
*   2016.08.03	김연호			표준프레임워크 3.6 개선
*
* </pre>
*/

@Controller
public class FaqController {
	
	@Resource(name = "FaqService")
	private FaqService FaqService;

	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	// 첨부파일 관련
	@Resource(name = "FileMngService")
	private FileMngService fileMngService;

	@Resource(name = "EgovFileMngUtil")
	private EgovFileMngUtil fileUtil;

	/** EgovMessageSource */
	@Resource(name = "egovMessageSource")
	EgovMessageSource egovMessageSource;
	
	/**
	 * FAQ 목록을 조회한다.
	 * @param searchVO
	 * @param model
	 * @return	"/uss/olh/faq/EgovFaqList"
	 * @throws Exception
	 */
	@RequestMapping(value = "/uss/olh/faq/selectFaqList.do")
	public String selectFaqList(@ModelAttribute("searchVO") FaqVO searchVO, ModelMap model) throws Exception {

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

		return "egovframework/com/web/board/faq/list";
	}
	
	/**
	 * FAQ 목록에 대한 상세정보를 조회한다.
	 * @param faqVO
	 * @param searchVO
	 * @param model
	 * @return	"/uss/olh/faq/EgovFaqDetail"
	 * @throws Exception
	 */
	@RequestMapping("/uss/olh/faq/faqDetail.do")
	public String selectFaqDetail(FaqVO faqVO, @ModelAttribute("searchVO") FaqVO searchVO, ModelMap model) throws Exception {

		FaqVO vo = FaqService.selectFaqDetail(searchVO);

		model.addAttribute("result", vo);

		return "egovframework/com/admin/uss/olh/faq/FaqDetail";
	}
}
