package com.yi.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yi.domain.BoardVO;
import com.yi.domain.Criteria;
import com.yi.domain.SearchCriteria;

@Repository
public class BoardDAOImpl implements BoardDAO {
	
	private static final String namespace = "com.yi.mapper.BoardMapper";
	
	@Autowired
	SqlSession sqlSession;

	@Override
	public void create(BoardVO vo) throws Exception {
		sqlSession.insert(namespace+".create", vo);
	}

	@Override
	public BoardVO read(int bno) throws Exception {
		return sqlSession.selectOne(namespace+".read", bno);
	}

	@Override
	public List<BoardVO> listAll() throws Exception {
		return sqlSession.selectList(namespace+".listAll");
	}

	@Override
	public void update(BoardVO vo) throws Exception {
		sqlSession.update(namespace+".update", vo);
	}

	@Override
	public void delete(int bno) throws Exception {
		sqlSession.delete(namespace+".delete", bno);
	}

	@Override
	public List<BoardVO> listPage(int page) throws Exception {
		page = (page-1)*10;
		return sqlSession.selectList(namespace+".listPage", page);
	}

	@Override
	public List<BoardVO> listCriteria(Criteria cri) throws Exception {
		return sqlSession.selectList(namespace+".listCriteria", cri);
	}

	@Override
	public int countPaging() throws Exception {
		return sqlSession.selectOne(namespace+".countPaging");
	}

	@Override
	public List<BoardVO> listSearch(SearchCriteria cri) throws Exception {
		return sqlSession.selectList(namespace+".listSearch", cri);
	}

	@Override
	public int listSearchCount(SearchCriteria cri) throws Exception {
		return sqlSession.selectOne(namespace+".listSearchCount", cri);
	}

	@Override
	public void updateReplyCnt(int amount, int bno) throws Exception {
		Map<String,Integer> map = new HashMap<String, Integer>();
		map.put("amount", amount);
		map.put("bno", bno);
		sqlSession.update(namespace+".updateReplyCnt",map);
	}

	@Override
	public void addAttach(String fullName) throws Exception {
		sqlSession.insert(namespace+".addAttach",fullName);
	}

	@Override
	public List<String> getAttach(int bno) throws Exception {
		return sqlSession.selectList(namespace+".getAttach", bno);
	}

	@Override
	public void deleteAttach(int bno) throws Exception {
		sqlSession.delete(namespace+".deleteAttach",bno);
	}

	@Override
	public void deleteAttachByFullName(int bno, String fullname) throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("bno", bno);
		map.put("fullname", fullname);
		sqlSession.delete(namespace+".deleteAttachByFullName" ,map);
	}

	@Override
	public void addAttachByBno(String fullName, int bno) throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("fullName", fullName);
		map.put("bno", bno);
		
		sqlSession.insert(namespace+".addAttachByBno",map);
	}

}
