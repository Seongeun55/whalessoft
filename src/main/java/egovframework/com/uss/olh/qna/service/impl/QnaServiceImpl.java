package egovframework.com.uss.olh.qna.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.com.uss.olh.qna.service.QnaService;
import egovframework.com.uss.olh.qna.service.QnaVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

@Service("QnaService")
public class QnaServiceImpl extends EgovAbstractServiceImpl implements QnaService {

	@Resource(name = "QnaDAO")
	private QnaDAO QnaDAO;

	/** ID Generation */
	@Resource(name = "egovQnaManageIdGnrService")
	private EgovIdGnrService idgenService;
	
	@Override
	public List<?> selectQnaList(QnaVO searchVO) {
		return QnaDAO.selectQnaList(searchVO);
	}

	@Override
	public int selectQnaListCnt(QnaVO searchVO) {
		return QnaDAO.selectQnaListCnt(searchVO);
	}

	@Override
	public QnaVO selectQnaDetail(QnaVO qnaVO) throws Exception {
		QnaVO resultVO = QnaDAO.selectQnaDetail(qnaVO);
		if (resultVO == null)
			throw processException("info.nodata.msg");
		return resultVO;
	}

	@Override
	public void updateQnaInqireCo(QnaVO qnaVO) {
		QnaDAO.updateQnaInqireCo(qnaVO);
	}

	@Override
	public void insertQna(QnaVO qnaVO) throws FdlException {
		String qaId = idgenService.getNextStringId();
		qnaVO.setQaId(qaId);
		
		QnaDAO.insertQna(qnaVO);
	}

	@Override
	public void updateQna(QnaVO qnaVO) {
		QnaDAO.updateQna(qnaVO);
	}

	@Override
	public void deleteQna(QnaVO qnaVO) {
		QnaDAO.deleteQna(qnaVO);
	}

	@Override
	public List<?> selectQnaAnswerList(QnaVO searchVO) {
		return QnaDAO.selectQnaAnswerList(searchVO);
	}

	@Override
	public int selectQnaAnswerListCnt(QnaVO searchVO) {
		return QnaDAO.selectQnaAnswerListCnt(searchVO);
	}

	@Override
	public void updateQnaAnswer(QnaVO qnaVO) {
		QnaDAO.updateQnaAnswer(qnaVO);
	}

}
