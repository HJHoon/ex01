package com.yi.persistence;

import java.util.List;

import com.yi.domain.BoardVO;
import com.yi.domain.Criteria;
import com.yi.domain.SearchCriteria;

public interface BoardDAO {
	public void create(BoardVO vo) throws Exception;	
	public BoardVO read(int bno) throws Exception;	
	public List<BoardVO> listAll() throws Exception;
	public List<BoardVO> listPage(int page) throws Exception;
	public List<BoardVO> listCriteria(Criteria cri) throws Exception;	
	public List<BoardVO> listSearch(SearchCriteria cri) throws Exception;
	
	public void update(BoardVO vo) throws Exception;	
	public void delete(int bno) throws Exception;
	
	public int countPaging() throws Exception;
	public int listSearchCount(SearchCriteria cri) throws Exception;
	
	public void updateReplyCnt(int amount, int bno) throws Exception;
	
	public void addAttach(String fullName) throws Exception;
	
	public List<String> getAttach(int bno) throws Exception;
	
	public void deleteAttach(int bno) throws Exception;
	public void deleteAttachByFullName(int bno, String fullname) throws Exception;
	
	public void addAttachByBno(String fullName, int bno) throws Exception;
}
