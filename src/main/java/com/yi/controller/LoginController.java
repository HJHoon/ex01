package com.yi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yi.domain.LoginVO;
import com.yi.domain.MemberVO;
import com.yi.service.MemberService;
@RequestMapping("/auth")
@Controller
public class LoginController {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	@Autowired
	MemberService service;
	
	@RequestMapping(value="login", method=RequestMethod.GET)
	public void loginGET() {
		logger.info("------------login GET");
	}
	
	@RequestMapping(value="loginPost", method=RequestMethod.POST)
	public void loginPOST(MemberVO member, Model model) {
		logger.info("------------login POST,"+member);
		
		MemberVO dbMember = service.selectMemberByIdAndPw(member.getUserid(), member.getUserpw());
		
		if(dbMember == null) {
			logger.info("loginPOST .......... login fail, not member");
			return;
		}
		LoginVO dto = new LoginVO();
		dto.setUserid(dbMember.getUserid());
		dto.setUsername(dbMember.getUsername());
		model.addAttribute("loginDTO", dto);
		
	}
}
