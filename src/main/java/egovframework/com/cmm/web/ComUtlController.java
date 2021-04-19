package egovframework.com.cmm.web;

import java.util.List;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import egovframework.com.cmm.EgovWebUtil;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.sym.mnu.mpm.service.MenuManageService;
import egovframework.com.uss.olh.faq.service.FaqService;
import egovframework.com.uss.olh.faq.service.FaqVO;
import egovframework.com.uss.olh.qna.service.QnaService;
import egovframework.com.sym.mnu.mpm.service.MenuManageVO;
import egovframework.com.uss.olh.qna.service.QnaVO;
import egovframework.com.utl.fcc.service.EgovStringUtil;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/**
 * @Class Name : EgovComUtlController.java
 * @Description : 공통유틸리티성 작업을 위한 Controller
 * @Modification Information
 * @
 * @ 수정일              수정자          수정내용
 * @ ----------  --------  ---------------------------
 *   2009.03.02  조재영          최초 생성
 *   2011.10.07  이기하          .action -> .do로 변경하면서 동일 매핑이 되어 삭제처리
 *   2015.11.12  김연호          한국인터넷진흥원 웹 취약점 개선
 *   2019.04.25  신용호          moveToPage() 화이트리스트 처리
 *
 *  @author 공통서비스 개발팀 조재영
 *  @since 2009.03.02
 *  @version 1.0
 *  @see
 *
 */
@Controller
public class ComUtlController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ComUtlController.class);
	
	@Resource(name = "egovPageLinkWhitelist")
    protected List<String> egovWhitelist;

    /** EgovPropertyService */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;
	
    // 기본생성자 추가 - 2021.04.16
    public ComUtlController() {	}
    
    /**
	 * JSP 호출작업만 처리하는 공통 함수
	 */
	@RequestMapping(value="/PageLink.do")
	public String moveToPage(@RequestParam("link") String linkPage){
		String link = linkPage;
		link = link.replace(";", "");
		link = link.replace(".", "");
		
		// service 사용하여 리턴할 결과값 처리하는 부분은 생략하고 단순 페이지 링크만 처리함
		if (linkPage==null || linkPage.equals("")){
			link="egovframework/com/cmm/egovError";
		}
		
		// 화이트 리스트 처리
		// whitelist목록에 있는 경우 결과가 true, 결과가 false인경우 FAIL처리
		if (egovWhitelist.contains(linkPage) == false) {
			LOGGER.debug("Page Link WhiteList Error! Please check whitelist!");
			link="egovframework/com/cmm/egovError";
		}
		
		// 안전한 경로 문자열로 조치
		link = EgovWebUtil.filePathBlackList(link);
		
		return link;
	}

    /**
	 * 모달조회
	 * @return String
	 * @exception Exception
	 */
    @RequestMapping(value="/EgovModal.do")
    public String selectUtlJsonInquire()  throws Exception {
        return "egovframework/com/admin/cmm/EgovModal";
    }
    
    /**
	 * validato rule dynamic Javascript
	 */
	@RequestMapping("/validator.do")
	public String validate(){
		return "egovframework/com/admin/cmm/validator";
	}
	
	//[추가] 토큰값을 랜덤으로 발생하기 위해 - 2021.04.16
	public StringBuffer random() {
		StringBuffer temp = new StringBuffer();
		Random rnd = new Random();
		for (int i = 0; i < 10; i++) {
		    int rIndex = rnd.nextInt(3);
		    switch (rIndex) {
		    case 0:		// a-z
		        temp.append((char) ((int) (rnd.nextInt(26)) + 97));
		        break;
		    case 1:		// A-Z
		        temp.append((char) ((int) (rnd.nextInt(26)) + 65));
		        break;
		    case 2:		// 0-9
		        temp.append((rnd.nextInt(10)));
		        break;
		    }
		}
		return temp;
	}

}