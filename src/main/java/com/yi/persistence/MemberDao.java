package com.yi.persistence;

import java.util.List;

import com.yi.domain.MemberVO;

public interface MemberDao {
	public String getTime();
	public void insertMember(MemberVO vo);
	public MemberVO selectMember(String userid);
	
	public List<MemberVO> selectAll();
	public void update(MemberVO vo);
	public void delete(MemberVO vo);
	
	public MemberVO selectMemberByIdAndPw(String userid, String userpw);
}
