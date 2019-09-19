package com.yi.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yi.domain.Criteria;
import com.yi.domain.ReplyVO;
@Repository
public class ReplyDAOImpl implements ReplyDAO{
	private static final String namespace = "com.yi.mapper.ReplyMapper";
	
	@Autowired
	SqlSession sqlSession;
	
	@Override
	public List<ReplyVO> list(int bno) throws Exception {
		return sqlSession.selectList(namespace+".list",bno);
	}

	@Override
	public void create(ReplyVO vo) throws Exception {
		sqlSession.insert(namespace+".create",vo);
	}

	@Override
	public void update(ReplyVO vo) throws Exception {
		sqlSession.update(namespace+".update",vo);
	}

	@Override
	public void delete(int rno) throws Exception {
		sqlSession.delete(namespace+".delete",rno);
	}

	@Override
	public List<ReplyVO> listPage(int bno, Criteria cri) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bno", bno);
		map.put("cri", cri);
		
		return sqlSession.selectList(namespace+".listPage", map);
	}

	@Override
	public int totalCount(int bno) throws Exception {
		return sqlSession.selectOne(namespace+".totalCount", bno);
	}

	@Override
	public int getBno(int rno) throws Exception {
		return sqlSession.selectOne(namespace+".getBno", rno);
	}

}
