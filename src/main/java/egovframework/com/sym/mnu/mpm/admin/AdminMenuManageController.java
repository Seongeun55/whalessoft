package egovframework.com.sym.mnu.mpm.admin;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import egovframework.com.cmm.ComDefaultVO;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.annotation.IncludedInfo;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.sym.mnu.mpm.service.MenuManageService;
import egovframework.com.sym.mnu.mpm.service.MenuManageVO;
import egovframework.com.sym.prm.service.ProgrmManageService;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springmodules.validation.commons.DefaultBeanValidator;

/**
 * 메뉴목록 관리및 메뉴생성, 사이트맵 생성을 처리하는 비즈니스 구현 클래스
 * @author 개발환경 개발팀 이용
 * @since 2009.06.01
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2009.03.20  이  용          최초 생성
 *	 2011.07.01	 서준식	   	메뉴정보 삭제시 참조되고 있는 하위 메뉴가 있는지 체크하는 로직 추가
 *	 2011.07.27	 서준식	   	deleteMenuManageList() 메서드에서 메뉴 멀티 삭제 버그 수정
 *	 2011.08.26	 정진오		IncludedInfo annotation 추가
 *	 2011.10.07	 이기하		보안취약점 수정(파일 업로드시 엑셀파일만 가능하도록 추가)
 *  2015.05.28	조정국		메뉴리스트관리 선택시 "정상적으로 조회되었습니다"라는 alert창이 제일 먼저 뜨는것 수정 : 출력메시지 주석처리
 * </pre>
 */

@Controller
public class AdminMenuManageController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AdminMenuManageController.class);

	/* Validator */
	@Autowired
	private DefaultBeanValidator beanValidator;
	/** EgovPropertyService */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;

    /** MenuManageService */
	@Resource(name = "meunManageService")
    private MenuManageService menuManageService;

    /** EgovMenuManageService */
	@Resource(name = "progrmManageService")
	private ProgrmManageService progrmManageService;

	/** EgovMessageSource */
    @Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;


    /**
     * 메뉴정보목록을 상세화면 호출 및 상세조회한다.
     * @param req_menuNo  String
     * @return 출력페이지정보 "sym/mnu/mpm/EgovMenuDetailSelectUpdt"
     * @exception Exception
     */
    @RequestMapping(value="/sym/mnu/mpm/AdminMenuManageListDetailSelect.do")
    public String selectMenuManage(@RequestParam("req_menuNo") String req_menuNo, @ModelAttribute("searchVO") ComDefaultVO searchVO, ModelMap model) throws Exception {
    	// 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "egovframework/com/admin/uat/uia/LoginUsr";
    	}
    	searchVO.setSearchKeyword(req_menuNo);

    	MenuManageVO resultVO = menuManageService.selectMenuManage(searchVO);
        model.addAttribute("menuManageVO", resultVO);

        return "egovframework/com/admin/sym/mnu/mpm/MenuDetailSelectUpdt";
    }

    /**
     * 메뉴목록 리스트조회한다.
     * @param searchVO ComDefaultVO
     * @return 출력페이지정보 "sym/mnu/mpm/EgovMenuManage"
     * @exception Exception
     */
    @IncludedInfo(name="메뉴관리리스트", order = 1091 ,gid = 60)
    @RequestMapping(value="/admin/sym/mnu/mpm/AdminMenuManageSelect.do")
    public String selectMenuManageList(@ModelAttribute("searchVO") ComDefaultVO searchVO, ModelMap model) throws Exception {
        // 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "egovframework/com/admin/uat/uia/LoginUsr";
    	}
    	// 내역 조회
    	/** EgovPropertyService.sample */
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

		List<?> list_menumanage = menuManageService.selectMenuManageList(searchVO);
		model.addAttribute("list_menumanage", list_menumanage);

        int totCnt = menuManageService.selectMenuManageListTotCnt(searchVO);
		paginationInfo.setTotalRecordCount(totCnt);
        model.addAttribute("paginationInfo", paginationInfo);

      	return "egovframework/com/admin/sym/mnu/mpm/MenuManage";
    }

    /**
     * 메뉴목록 멀티 삭제한다.
     * @param checkedMenuNoForDel  String
     * @return 출력페이지정보 "forward:/admin/sym/mnu/mpm/AdminMenuManageSelect.do"
     * @exception Exception
     */
    @RequestMapping("/sym/mnu/mpm/AdminMenuManageListDelete.do")
    public String deleteMenuManageList(@RequestParam("checkedMenuNoForDel") String checkedMenuNoForDel, @ModelAttribute("menuManageVO") MenuManageVO menuManageVO,
            ModelMap model) throws Exception {
    	// 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "egovframework/com/admin/uat/uia/LoginUsr";
    	}
		String sLocationUrl = null;
    	String resultMsg    = "";

		String [] delMenuNo = checkedMenuNoForDel.split(",");
		menuManageVO.setMenuNo(Integer.parseInt(delMenuNo[0]));

		if (menuManageService.selectUpperMenuNoByPk(menuManageVO) != 0){
    		resultMsg = egovMessageSource.getMessage("fail.common.delete.upperMenuExist");
    		sLocationUrl = "forward:/admin/sym/mnu/mpm/AdminMenuManageSelect.do";
		}else if (delMenuNo == null || (delMenuNo.length ==0)){
    		resultMsg = egovMessageSource.getMessage("fail.common.delete");
    		sLocationUrl = "forward:/admin/sym/mnu/mpm/AdminMenuManageSelect.do";
		}else{
			menuManageService.deleteMenuManageList(checkedMenuNoForDel);
			resultMsg = egovMessageSource.getMessage("success.common.delete");
			sLocationUrl ="forward:/admin/sym/mnu/mpm/AdminMenuManageSelect.do";
		}
		model.addAttribute("resultMsg", resultMsg);
        return sLocationUrl;
    }

    /**
     * 메뉴정보를 등록화면으로 이동 및 등록 한다.
     * @param menuManageVO    MenuManageVO
     * @param commandMap      Map
     * @return 출력페이지정보 등록화면 호출시 "sym/mnu/mpm/EgovMenuRegist",
     *         출력페이지정보 등록처리시 "forward:/admin/sym/mnu/mpm/AdminMenuManageSelect.do"
     * @exception Exception
     */
    @RequestMapping(value="/sym/mnu/mpm/AdminMenuRegistInsert.do")
    public String insertMenuManage(@RequestParam Map<?, ?> commandMap, @ModelAttribute("menuManageVO") MenuManageVO menuManageVO, BindingResult bindingResult,
    		ModelMap model) throws Exception {
    	
		String sLocationUrl = null;
    	String resultMsg    = "";
        // 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "egovframework/com/admin/uat/uia/LoginUsr";
    	}
        String sCmd = commandMap.get("cmd") == null ? "" : (String)commandMap.get("cmd");
		if(sCmd.equals("insert")){
            beanValidator.validate(menuManageVO, bindingResult);
    		if (bindingResult.hasErrors()){
    			sLocationUrl = "egovframework/com/admin/sym/mnu/mpm/MenuRegist";
    			return sLocationUrl;
    		}
    		if(menuManageService.selectMenuNoByPk(menuManageVO) == 0){
    			ComDefaultVO searchVO = new ComDefaultVO();
    			searchVO.setSearchKeyword(menuManageVO.getProgrmFileNm());
    			if(progrmManageService.selectProgrmNMTotCnt(searchVO)==0){
    	    		resultMsg = egovMessageSource.getMessage("fail.common.insert");
    		        sLocationUrl = "egovframework/com/admin/sym/mnu/mpm/MenuRegist";
    			}else{
    	        	menuManageService.insertMenuManage(menuManageVO);
            		resultMsg = egovMessageSource.getMessage("success.common.insert");
    		        sLocationUrl = "forward:/admin/sym/mnu/mpm/AdminMenuManageSelect.do";
    			}
    		}else{
        		resultMsg = egovMessageSource.getMessage("common.isExist.msg");
        		sLocationUrl = "egovframework/com/admin/sym/mnu/mpm/MenuRegist";
    		}
    		model.addAttribute("resultMsg", resultMsg);
		}else{
            sLocationUrl = "egovframework/com/admin/sym/mnu/mpm/MenuRegist";
        }
		return sLocationUrl;
    }

    /**
     * 메뉴정보를 수정 한다.
     * @param menuManageVO  MenuManageVO
     * @return 출력페이지정보 "forward:/admin/sym/mnu/mpm/AdminMenuManageSelect.do"
     * @exception Exception
     */
    @RequestMapping(value="/sym/mnu/mpm/AdminMenuDetailSelectUpdt.do")
    public String updateMenuManage(@ModelAttribute("menuManageVO") MenuManageVO menuManageVO, BindingResult bindingResult, ModelMap model) throws Exception {
        String sLocationUrl = null;
    	String resultMsg    = "";
    	// 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "egovframework/com/admin/uat/uia/LoginUsr";
    	}
        beanValidator.validate(menuManageVO, bindingResult);
		if (bindingResult.hasErrors()){
			sLocationUrl = "forward:/sym/mnu/mpm/AdminMenuManageListDetailSelect.do";
			return sLocationUrl;
		}
		ComDefaultVO searchVO = new ComDefaultVO();
		searchVO.setSearchKeyword(menuManageVO.getProgrmFileNm());
		if(progrmManageService.selectProgrmNMTotCnt(searchVO)==0){
    		resultMsg = egovMessageSource.getMessage("fail.common.update");
	        sLocationUrl = "forward:/sym/mnu/mpm/AdminMenuManageListDetailSelect.do";
		}else{
			menuManageService.updateMenuManage(menuManageVO);
	    	resultMsg = egovMessageSource.getMessage("success.common.update");
	       	sLocationUrl = "forward:/admin/sym/mnu/mpm/AdminMenuManageSelect.do";
		}
    	model.addAttribute("resultMsg", resultMsg);
		return sLocationUrl;
    }

    /**
     * 메뉴정보를 삭제 한다.
     * @param menuManageVO MenuManageVO
     * @return 출력페이지정보 "forward:/admin/sym/mnu/mpm/AdminMenuManageSelect.do"
     * @exception Exception
     */
    @RequestMapping(value="/sym/mnu/mpm/AdminMenuManageDelete.do")
    public String deleteMenuManage(@ModelAttribute("menuManageVO") MenuManageVO menuManageVO, ModelMap model) throws Exception {
    	String resultMsg    = "";
    	// 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "egovframework/com/admin/uat/uia/LoginUsr";
    	}
    	if (menuManageService.selectUpperMenuNoByPk(menuManageVO) != 0){
    		resultMsg = egovMessageSource.getMessage("fail.common.delete.upperMenuExist");
    		model.addAttribute("resultMsg", resultMsg);
    		return "forward:/admin/sym/mnu/mpm/AdminMenuManageSelect.do";
		}

    	menuManageService.deleteMenuManage(menuManageVO);
    	resultMsg = egovMessageSource.getMessage("success.common.delete");
    	String _MenuNm = "%";
    	menuManageVO.setMenuNm(_MenuNm);
    	model.addAttribute("resultMsg", resultMsg);
      	return "forward:/admin/sym/mnu/mpm/AdminMenuManageSelect.do";
    }

    /**
     * 메뉴리스트를 조회한다.
     * @param searchVO ComDefaultVO
     * @return 출력페이지정보 "sym/mnu/mpm/EgovMenuList"
     * @exception Exception
     */
    @IncludedInfo(name="메뉴리스트관리", order = 1090 ,gid = 60)
    @RequestMapping(value="/admin/sym/mnu/mpm/AdminMenuListSelect.do")
    public String selectMenuList(@ModelAttribute("searchVO") ComDefaultVO searchVO, ModelMap model) throws Exception {
    	String resultMsg    = "";
    	// 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "egovframework/com/admin/uat/uia/LoginUsr";
    	}
    	List<?> list_menulist = menuManageService.selectMenuList();
    	resultMsg = egovMessageSource.getMessage("success.common.select");
        model.addAttribute("list_menulist", list_menulist);
//        model.addAttribute("resultMsg", resultMsg);
      	return "egovframework/com/admin/sym/mnu/mpm/MenuList";
    }

    /**
     * 메뉴리스트의 메뉴정보를 등록한다.
     * @param menuManageVO MenuManageVO
     * @return 출력페이지정보 "sym/mnu/mpm/EgovMenuList"
     * @exception Exception
     */
    @RequestMapping(value="/sym/mnu/mpm/EgovMenuListInsert.do")
    public String insertMenuList(@ModelAttribute("menuManageVO") MenuManageVO menuManageVO, BindingResult bindingResult, ModelMap model) throws Exception {
        String sLocationUrl = null;
    	String resultMsg    = "";
    	// 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "egovframework/com/admin/uat/uia/LoginUsr";
    	}

        beanValidator.validate(menuManageVO, bindingResult);
		if (bindingResult.hasErrors()){
			sLocationUrl = "egovframework/com/sym/mnu/mpm/EgovMenuList";
			return sLocationUrl;
		}

		if(menuManageService.selectMenuNoByPk(menuManageVO) == 0){
			ComDefaultVO searchVO = new ComDefaultVO();
			searchVO.setSearchKeyword(menuManageVO.getProgrmFileNm());
			if(progrmManageService.selectProgrmNMTotCnt(searchVO)==0){
	    		resultMsg = egovMessageSource.getMessage("fail.common.insert");
		        sLocationUrl = "forward:/admin/sym/mnu/mpm/AdminMenuListSelect.do";
			}else{
	        	menuManageService.insertMenuManage(menuManageVO);
	    		resultMsg = egovMessageSource.getMessage("success.common.insert");
		        sLocationUrl = "forward:/admin/sym/mnu/mpm/AdminMenuListSelect.do";
			}
		}else{
    		resultMsg = egovMessageSource.getMessage("common.isExist.msg");
    		sLocationUrl = "forward:/admin/sym/mnu/mpm/AdminMenuListSelect.do";
		}
		model.addAttribute("resultMsg", resultMsg);
      	return sLocationUrl;
    }

    /**
     * 메뉴리스트의 메뉴정보를 수정한다.
     * @param menuManageVO MenuManageVO
     * @return 출력페이지정보 "sym/mnu/mpm/EgovMenuList"
     * @exception Exception
     */
    @RequestMapping(value="/sym/mnu/mpm/AdminMenuListUpdt.do")
    public String updateMenuList(@ModelAttribute("menuManageVO") MenuManageVO menuManageVO, BindingResult bindingResult, ModelMap model) throws Exception {
        String sLocationUrl = null;
    	String resultMsg    = "";
    	// 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "egovframework/com/admin/uat/uia/LoginUsr";
    	}

        beanValidator.validate(menuManageVO, bindingResult);
		if (bindingResult.hasErrors()){
			sLocationUrl = "forward:/admin/sym/mnu/mpm/AdminMenuListSelect.do";
			return sLocationUrl;
		}
		ComDefaultVO searchVO = new ComDefaultVO();
		searchVO.setSearchKeyword(menuManageVO.getProgrmFileNm());
		if(progrmManageService.selectProgrmNMTotCnt(searchVO)==0){
    		resultMsg = egovMessageSource.getMessage("fail.common.update");
	        sLocationUrl = "forward:/admin/sym/mnu/mpm/AdminMenuListSelect.do";
		}else{
			menuManageService.updateMenuManage(menuManageVO);
			resultMsg = egovMessageSource.getMessage("success.common.update");
	        sLocationUrl = "forward:/admin/sym/mnu/mpm/AdminMenuListSelect.do";
		}
		model.addAttribute("resultMsg", resultMsg);
      	return sLocationUrl;
    }

    /**
     * 메뉴리스트의 메뉴정보를 삭제한다.
     * @param menuManageVO MenuManageVO
     * @return 출력페이지정보 "sym/mnu/mpm/EgovMenuList"
     * @exception Exception
     */
    @RequestMapping(value="/sym/mnu/mpm/AdminMenuListDelete.do")
    public String deleteMenuList(@ModelAttribute("menuManageVO") MenuManageVO menuManageVO, BindingResult bindingResult, ModelMap model) throws Exception {
        String sLocationUrl = null;
    	String resultMsg    = "";
    	// 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "egovframework/com/admin/uat/uia/LoginUsr";
    	}

        beanValidator.validate(menuManageVO, bindingResult);
		if (bindingResult.hasErrors()){
			sLocationUrl = "egovframework/com/sym/mnu/mpm/EgovMenuList";
			return sLocationUrl;
		}
		menuManageService.deleteMenuManage(menuManageVO);
		resultMsg = egovMessageSource.getMessage("success.common.delete");
        sLocationUrl = "forward:/admin/sym/mnu/mpm/AdminMenuListSelect.do";
		model.addAttribute("resultMsg", resultMsg);
      	return sLocationUrl;
    }

    /**
     * 메뉴리스트의 메뉴정보를 이동 메뉴목록을 조회한다.
     * @param searchVO  ComDefaultVO
     * @return 출력페이지정보 "sym/mnu/mpm/EgovMenuMvmn"
     * @exception Exception
     */
    @RequestMapping(value="/sym/mnu/mpm/AdminMenuListSelectMvmn.do")
    public String selectMenuListMvmn(@ModelAttribute("searchVO") ComDefaultVO searchVO, ModelMap model) throws Exception {
    	// 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "egovframework/com/admin/uat/uia/LoginUsr";
    	}

    	List<?> list_menulist = menuManageService.selectMenuList();
        model.addAttribute("list_menulist", list_menulist);
      	return "egovframework/com/admin/sym/mnu/mpm/MenuMvmn";
    }

    /**
     * 메뉴리스트의 메뉴정보를 이동 메뉴목록을 조회한다. (New)
     * @param searchVO  ComDefaultVO
     * @return 출력페이지정보 "sym/mnu/mpm/EgovMenuMvmn"
     * @exception Exception
     */
    @RequestMapping(value="/sym/mnu/mpm/EgovMenuListSelectMvmnNew.do")
    public String selectMenuListMvmnNew(@ModelAttribute("searchVO") ComDefaultVO searchVO, ModelMap model) throws Exception {
    	// 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "egovframework/com/admin/uat/uia/LoginUsr";
    	}

    	List<?> list_menulist = menuManageService.selectMenuList();
        model.addAttribute("list_menulist", list_menulist);
      	return "egovframework/com/admin/sym/mnu/mpm/EgovMenuMvmnNew";
    }


    /*### 일괄처리 프로세스 ###*/

    /**
     * 메뉴생성 일괄삭제프로세스
     * @param menuManageVO MenuManageVO
     * @return 출력페이지정보 "sym/mnu/mpm/EgovMenuBndeRegist"
     * @exception Exception
     */
    @RequestMapping(value="/sym/mnu/mpm/EgovMenuBndeAllDelete.do")
    public String menuBndeAllDelete(@ModelAttribute("menuManageVO") MenuManageVO menuManageVO, ModelMap model) throws Exception {
    	String resultMsg = "";
        // 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "egovframework/com/admin/uat/uia/LoginUsr";
    	}
    	menuManageService.menuBndeAllDelete();
    	resultMsg = egovMessageSource.getMessage("success.common.delete");
    	model.addAttribute("resultMsg", resultMsg);
        return "egovframework/com/admin/sym/mnu/mpm/EgovMenuBndeRegist";
    }


    /**
     * 메뉴일괄등록화면 호출 및  메뉴일괄등록처리 프로세스
     * @param commandMap    Map
     * @param menuManageVO  MenuManageVO
     * @param request       HttpServletRequest
     * @return 출력페이지정보 "sym/mnu/mpm/EgovMenuBndeRegist"
     * @exception Exception
     */
    @RequestMapping(value="/sym/mnu/mpm/EgovMenuBndeRegist.do")
    public String menuBndeRegist(@RequestParam Map<?, ?> commandMap, final HttpServletRequest request, @ModelAttribute("menuManageVO") MenuManageVO menuManageVO,
    		ModelMap model) throws Exception {
        String sLocationUrl = null;
        String resultMsg = "";
        String sMessage  = "";
        // 0. Spring Security 사용자권한 처리
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "egovframework/com/admin/uat/uia/LoginUsr";
    	}
        String sCmd = commandMap.get("cmd") == null ? "" : (String)commandMap.get("cmd");
        if(sCmd.equals("bndeInsert")){
	    	final MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			final Map<String, MultipartFile> files = multiRequest.getFileMap();
			Iterator<Entry<String, MultipartFile>> itr = files.entrySet().iterator();
			MultipartFile file;
			while (itr.hasNext()) {
				Entry<String, MultipartFile> entry = itr.next();
				file = entry.getValue();
				if (!"".equals(file.getOriginalFilename())) {
					// 2011.10.07 업로드 파일에 대한 확장자를 체크
					if (file.getOriginalFilename().endsWith(".xls")
							|| file.getOriginalFilename().endsWith(".xlsx")
							|| file.getOriginalFilename().endsWith(".XLS")
							|| file.getOriginalFilename().endsWith(".XLSX")) {

						if(menuManageService.menuBndeAllDelete()){
							sMessage = menuManageService.menuBndeRegist(menuManageVO, file.getInputStream());
				    	    resultMsg = sMessage;
						}else{
							resultMsg = egovMessageSource.getMessage("fail.common.msg");
							menuManageVO.setTmpCmd("EgovMenuBndeRegist Error!!");
					        model.addAttribute("resultVO", menuManageVO);
						}
					}else{
						LOGGER.info("xls, xlsx 파일 타입만 등록이 가능합니다.");
						resultMsg = egovMessageSource.getMessage("fail.common.msg");
						model.addAttribute("resultMsg", resultMsg);
						return "egovframework/com/admin/sym/mnu/mpm/EgovMenuBndeRegist";
					}
					// *********** 끝 ***********

				}else{
					resultMsg = egovMessageSource.getMessage("fail.common.msg");
				}
			}
			sLocationUrl = "egovframework/com/sym/mnu/mpm/EgovMenuBndeRegist";
	    	model.addAttribute("resultMsg", resultMsg);
        }else{
            sLocationUrl = "egovframework/com/sym/mnu/mpm/EgovMenuBndeRegist";
        }
    	return sLocationUrl;
    }
}