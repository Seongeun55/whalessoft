package egovframework.com.cop.bbs.service;

import java.util.List;
import java.util.Map;

import egovframework.rte.fdl.cmmn.exception.FdlException;

public interface ArticleService {

	Map<String, Object> selectArticleList(BoardVO boardVO);
	
	Map<String, Object> allArticleList(BoardVO boardVO);	//[추가] 2021.04.20

	BoardVO selectArticleDetail(BoardVO boardVO);
	
	void insertArticle(Board board) throws FdlException;

	void updateArticle(Board board);

	void deleteArticle(Board board) throws Exception;

	List<BoardVO> selectNoticeArticleList(BoardVO boardVO);
	
	Map<String, Object> selectGuestArticleList(BoardVO vo);
	
	/*
	 * 블로그 관련
	 */
	BoardVO selectArticleCnOne(BoardVO boardVO);
	
	List<BoardVO> selectBlogNmList(BoardVO boardVO);
	
	Map<String, Object> selectBlogListManager(BoardVO boardVO);
	
	List<BoardVO> selectArticleDetailDefault(BoardVO boardVO);
	
	int selectArticleDetailDefaultCnt(BoardVO boardVO);
	
	List<BoardVO> selectArticleDetailCn(BoardVO boardVO);
	
	int selectLoginUser(BoardVO boardVO);
}
