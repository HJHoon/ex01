package com.yi.service;

import java.util.List;

import com.yi.domain.MemberVO;

public interface MemberService {
	public String getTime();
	public void insertMember(MemberVO vo);
	public MemberVO readMember(String userid);
	public List<MemberVO> selectAll();
	public void update(MemberVO vo);
	public void delete(MemberVO vo);
	public MemberVO selectMemberByIdAndPw(String userid, String userpw);
}
