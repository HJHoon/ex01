package com.yi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yi.domain.MemberVO;
import com.yi.persistence.MemberDao;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	MemberDao dao;
	
	@Override
	public String getTime() {
		
		return dao.getTime();
	}

	@Override
	public void insertMember(MemberVO vo) {
		dao.insertMember(vo);

	}

	@Override
	public MemberVO readMember(String userid) {
		
		return dao.selectMember(userid);
	}

	@Override
	public List<MemberVO> selectAll() {
		// TODO Auto-generated method stub
		return dao.selectAll();
	}

	@Override
	public void update(MemberVO vo) {
		dao.update(vo);

	}

	@Override
	public void delete(MemberVO vo) {
		dao.delete(vo);

	}

	@Override
	public MemberVO selectMemberByIdAndPw(String userid, String userpw) {
		return dao.selectMemberByIdAndPw(userid, userpw);
	}

}
