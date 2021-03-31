package egovframework.com.sym.log.clg.service;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * @Class Name : EgovLoginLogAspect.java
 * @Description : 시스템 로그 생성을 위한 ASPECT 클래스
 * @Modification Information
 *
 *    수정일         수정자         수정내용
 *    -------        -------     -------------------
 *    2009. 3. 11.   이삼섭         최초생성
 *    2011. 7. 01.   이기하         패키지 분리(sym.log -> sym.log.clg)
 *
 * @author 공통 서비스 개발팀 이삼섭
 * @since 2009. 3. 11.
 * @version
 * @see
 *
 */
public class EgovLoginLogAspect {
	
	@Resource(name="LoginLogService")
	private LoginLogService loginLogService;
	private HttpServletRequest request;
	
	/**
	 * 로그인 로그정보를 생성한다.
	 * EgovLoginController.actionMain Method
	 * 
	 * @param 
	 * @return void
	 * @throws Exception 
	 */
	public void logLogin(JoinPoint joinPoint) throws Throwable{
		
		String uniqId = "";
		String ip = "";
		
		/* [ADD] Request Reset - 2021.03.26 */
		this.resetRequest(joinPoint);
		
		/* Authenticated  */
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(isAuthenticated.booleanValue()) {
    		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
			uniqId = (user == null || user.getUniqId() == null) ? "" : user.getUniqId();
			ip = request.getRemoteAddr(); /* [EDIT] Get IP From Request- 2021.03.26 */
    	}

    	LoginLog loginLog = new LoginLog();
    	loginLog.setLoginId(uniqId);
        loginLog.setLoginIp(ip);
        loginLog.setLoginMthd("I"); 
        loginLog.setErrOccrrAt("N");
        loginLog.setErrorCode("");
        loginLogService.logInsertLoginLog(loginLog);

	}
	
	/**
	 * 로그아웃 로그정보를 생성한다.
	 * EgovLoginController.actionLogout Method
	 * 
	 * @param 
	 * @return void
	 * @throws Exception 
	
	public void logLogout() throws Throwable {
		
		String uniqId = "";
		String ip = "";
		
        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	if(isAuthenticated.booleanValue()) {
			LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
			uniqId = (user == null || user.getUniqId() == null) ? "" : user.getUniqId();
			ip = (user == null || user.getIp() == null) ? "" : request.getRemoteAddr();
    	}

    	LoginLog loginLog = new LoginLog();
    	loginLog.setLoginId(uniqId);
        loginLog.setLoginIp(ip);
        loginLog.setLoginMthd("O"); // 로그인:I, 로그아웃:O
        loginLog.setErrOccrrAt("N");
        loginLog.setErrorCode("");
        loginLogService.logInsertLoginLog(loginLog);
	} */
	
	/* [추가] request 정보 세팅  - 2021.03.26 */
	public void resetRequest(JoinPoint joinPoint) throws Throwable {
	    for (Object obj : joinPoint.getArgs()) {
	        if (obj instanceof HttpServletRequest || obj instanceof MultipartHttpServletRequest) {
	            this.request = (HttpServletRequest) obj;
	        }
	    }
	}
}
