package egovframework.com.uss.olh.faq.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.com.uss.olh.faq.service.FaqService;
import egovframework.com.uss.olh.faq.service.FaqVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

@Service("FaqService")
public class FaqServiceImpl extends EgovAbstractServiceImpl implements FaqService {

	@Resource(name = "FaqDAO")
	private FaqDAO FaqDAO;

	/** ID Generation */
	@Resource(name = "egovFaqManageIdGnrService")
	private EgovIdGnrService idgenService;
	
	@Override
	public List<?> selectFaqList(FaqVO searchVO) {
		return FaqDAO.selectFaqList(searchVO);
	}

	@Override
	public int selectFaqListCnt(FaqVO searchVO) {
		return FaqDAO.selectFaqListCnt(searchVO);
	}

	@Override
	public FaqVO selectFaqDetail(FaqVO searchVO) throws Exception {
		
		//조회수 증가
		FaqDAO.updateFaqInqireCo(searchVO);
		
		FaqVO resultVO = FaqDAO.selectFaqDetail(searchVO);
		if (resultVO == null)
			throw processException("info.nodata.msg");
		return resultVO;
	}

	@Override
	public void insertFaq(FaqVO faqVO) throws FdlException {
		String faqId = idgenService.getNextStringId();
		faqVO.setFaqId(faqId);
		FaqDAO.insertFaq(faqVO);
	}

	@Override
	public void updateFaq(FaqVO faqVO) {
		FaqDAO.updateFaq(faqVO);
	}

	@Override
	public void deleteFaq(FaqVO faqVO) {
		FaqDAO.deleteFaq(faqVO);
	}

}
