package com.yi.service;

import java.util.List;

import com.yi.domain.BoardVO;
import com.yi.domain.Criteria;
import com.yi.domain.SearchCriteria;

public interface BoardService {
	public void regist(BoardVO board) throws Exception;
	public List<BoardVO> listAll() throws Exception;
	public BoardVO read(int bno) throws Exception;
	public void modify(BoardVO board, String[] delFiles) throws Exception;
	public void modify(BoardVO board) throws Exception;
	public void remove(int bno )throws Exception;
	
	public List<BoardVO> listCriteria(Criteria cri) throws Exception;	
	public int listCountCriteria() throws Exception;
	
	public List<BoardVO> listSearch(SearchCriteria cri) throws Exception;
	public int listSearchCount(SearchCriteria cri) throws Exception;
}

