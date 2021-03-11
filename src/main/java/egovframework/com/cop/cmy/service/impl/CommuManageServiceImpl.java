package egovframework.com.cop.cmy.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import egovframework.com.cop.cmy.service.CommunityUser;
import egovframework.com.cop.cmy.service.CommunityUserVO;
import egovframework.com.cop.cmy.service.CommunityVO;
import egovframework.com.cop.cmy.service.CommuManageService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

@Service("CommuManageService")
public class CommuManageServiceImpl extends EgovAbstractServiceImpl implements CommuManageService {

	@Resource(name = "CommuMasterDAO")
	CommuMasterDAO CommuMasterDAO;
	
	@Resource(name = "CommuManageDAO")
	CommuManageDAO CommuManageDAO;
	
	@Resource(name = "egovCmmntyIdGnrService")
    private EgovIdGnrService idgenService;

    private static final Logger LOGGER = LoggerFactory.getLogger(CommuManageServiceImpl.class);
	
	@Override
	public Map<String, Object> selectCommuInf(CommunityVO cmmntyVO) {
		
		//커뮤니티 기본정보 확인
		CommunityVO vo = CommuMasterDAO.selectCommuMasterDetail(cmmntyVO);

		CommunityUser cmmntyUser = new CommunityUser();

		cmmntyUser.setCmmntyId(cmmntyVO.getCmmntyId());
		cmmntyUser.setEmplyrId(cmmntyVO.getEmplyrId());

		cmmntyUser = CommuManageDAO.selectSingleCommuUserDetail(cmmntyUser);

		//-----------------------------------------------------------------
		// 관리자 정보를 처리한다. (여러 명이 있을 수 있음 - DB 설계 문제상 문제)
		// 위의 처리는 cmmntyVO.getEmplyrId()가 ""이기 때문에 의미 없음..
		//-----------------------------------------------------------------
		List<CommunityUser> managers = CommuManageDAO.selectCommuManagerList(cmmntyVO);

		if (cmmntyUser == null) {
		    cmmntyUser = new CommunityUser();
		}
		if (managers.size() == 1) {
			
		    cmmntyUser.setEmplyrId(managers.get(0).getEmplyrId());
		    cmmntyUser.setEmplyrNm(managers.get(0).getEmplyrNm());
		} else if (managers.size() > 1) {
		    cmmntyUser.setEmplyrId(managers.get(0).getEmplyrId());
		    cmmntyUser.setEmplyrNm(managers.get(0).getEmplyrNm() + "외 " + (managers.size() - 1) + "명");
		} else {
			LOGGER.debug("No managers...");
		}
		////---------------------------------------------------------------

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("cmmntyVO", vo);
		map.put("cmmntyUser", cmmntyUser);

		return map;
	}

	@Override
	public String checkCommuUserDetail(CommunityUser cmmntyUser) {

		//cmmntyId
		CommunityVO vo = new CommunityVO();
		vo.setCmmntyId(cmmntyUser.getCmmntyId());
		
		int userCnt = CommuManageDAO.checkExistUser(cmmntyUser);
		
		if (userCnt == 0) {
		    return "";
		} else {
		    return "EXIST";
		}
	}

	@Override
	public void insertCommuUserRqst(CommunityUser cmmntyUser) {
		CommuManageDAO.insertCommuUserRqst(cmmntyUser);
	}

	@Override
	public Map<String, Object> selectCommuUserList(CommunityUserVO cmmntyUserVO) {
		List<?> result = CommuManageDAO.selectCommuUserList(cmmntyUserVO);
		int cnt = CommuManageDAO.selectCommuUserListCnt(cmmntyUserVO);

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("resultList", result);
		map.put("resultCnt", Integer.toString(cnt));

		return map;
	}

	@Override
	public Boolean selectIsCommuAdmin(CommunityUserVO userVO) {
		
		CommunityUser cmmntyUser = CommuManageDAO.selectSingleCommuUserDetail(userVO);
		
		if(cmmntyUser==null) {
			return false;
		} else if(cmmntyUser.getMngrAt().equals("Y")) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void insertCommuUser(CommunityUserVO cmmntyUserVO) {
		CommuManageDAO.insertCommuUser(cmmntyUserVO);
	}

	@Override
	public void deleteCommuUser(CommunityUserVO cmmntyUserVO) {
		CommuManageDAO.deleteCommuUser(cmmntyUserVO);
	}

	@Override
	public void insertCommuUserAdmin(CommunityUserVO cmmntyUserVO) {
		CommuManageDAO.insertCommuUserAdmin(cmmntyUserVO);
	}

	@Override
	public void deleteCommuUserAdmin(CommunityUserVO cmmntyUserVO) {
		CommuManageDAO.deleteCommuUserAdmin(cmmntyUserVO);
	}

}
