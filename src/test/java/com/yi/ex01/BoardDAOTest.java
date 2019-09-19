package com.yi.ex01;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yi.domain.BoardVO;
import com.yi.domain.Criteria;
import com.yi.persistence.BoardDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class BoardDAOTest {
	
	@Autowired
	BoardDAO dao;
	
//	@Test
	public void testCreate() throws Exception {
		BoardVO vo = new BoardVO();
		vo.setContent("새로운 글을 넣습니다.");
		vo.setTitle("새로운 글을 넣습니다.");
		vo.setWriter("user00");
		dao.create(vo);
	}
//	@Test
	public void testListPage() throws Exception {
		List<BoardVO> list = dao.listPage(1);
		for(BoardVO vo : list) {
			System.out.println(vo);
		}
	}
	@Test
	public void testListCriteria() throws Exception {
		Criteria cri = new Criteria();
		cri.setPerPageNum(20);
		cri.setPage(1);
		List<BoardVO> list = dao.listCriteria(cri);
		for(BoardVO vo : list) {
			System.out.println(vo);
		}
	}
}
