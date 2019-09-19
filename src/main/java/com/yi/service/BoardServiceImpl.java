package com.yi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yi.domain.BoardVO;
import com.yi.domain.Criteria;
import com.yi.domain.SearchCriteria;
import com.yi.persistence.BoardDAO;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	BoardDAO dao;

	@Override
	@Transactional
	public void regist(BoardVO board) throws Exception {
		dao.create(board);
		for(String fullName : board.getFiles()) {
			dao.addAttach(fullName);
		}
	}

	@Override
	public List<BoardVO> listAll() throws Exception {
		return dao.listAll();
	}

	@Override
	@Transactional
	public BoardVO read(int bno) throws Exception {
		BoardVO vo = dao.read(bno);
		List<String> files = dao.getAttach(bno);
		vo.setFiles(files);
		return vo;
	}
	@Transactional
	@Override
	public void modify(BoardVO board,String[] delFiles) throws Exception {
		//삭제함
		if(delFiles != null) {
			for(String file : delFiles) {
				dao.deleteAttachByFullName(board.getBno(), file);
			}
		}
		
		//추가함
		for(String filename : board.getFiles()) {
			dao.addAttachByBno(filename, board.getBno());
		}
		
		
		dao.update(board);
	}
	
	@Override
	public void modify(BoardVO board) throws Exception {
		dao.update(board);
	}

	@Transactional
	@Override
	public void remove(int bno) throws Exception {
		dao.deleteAttach(bno);
		dao.delete(bno);
	}

	@Override
	public List<BoardVO> listCriteria(Criteria cri) throws Exception {
		return dao.listCriteria(cri);
	}

	@Override
	public int listCountCriteria() throws Exception {
		return dao.countPaging();
	}

	@Override
	public List<BoardVO> listSearch(SearchCriteria cri) throws Exception {
		return dao.listSearch(cri);
	}

	@Override
	public int listSearchCount(SearchCriteria cri) throws Exception {
		return dao.listSearchCount(cri);
	}

}
