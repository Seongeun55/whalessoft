package egovframework.com.cop.cmy.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.com.cop.bbs.service.BoardMasterVO;
import egovframework.com.cop.cmy.service.CommuBBSMasterService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

@Service("CommuBBSMasterService")
public class CommuBBSMasterServiceImpl extends EgovAbstractServiceImpl implements CommuBBSMasterService {

	@Resource(name = "CommuBBSMasterDAO")
    private CommuBBSMasterDAO CommuBBSMasterDAO;
	
	@Resource(name = "egovBBSMstrIdGnrService")
    private EgovIdGnrService idgenService;
	
	@Override
	public List<BoardMasterVO> selectCommuBBSMasterListMain(BoardMasterVO boardMasterVO) {
		return CommuBBSMasterDAO.selectCommuBBSMasterListMain(boardMasterVO);
	}


}
