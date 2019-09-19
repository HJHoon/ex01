package com.yi.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yi.domain.BoardVO;
import com.yi.domain.Criteria;
import com.yi.domain.PageMaker;
import com.yi.service.BoardService;

@Controller
@RequestMapping("/board/*")
public class BoardController {
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

	@Autowired
	BoardService service;

	@RequestMapping(value = "register", method = RequestMethod.GET)
	public void registerGET() {
		logger.info("------------- registerGET");

		// board/register
	}

	@RequestMapping(value = "register", method = RequestMethod.POST)
	public String registerPOST(BoardVO vo) throws Exception {
		logger.info("------------- registerPOST");
		logger.info(vo.toString());

		service.regist(vo);
		// jsp가 아니라 controller로 감
		// 리다이렉트 : 브라우저에 돌아갈때 /board/listAll주소로 바로 이동하라고 처리하는 것임
		// 브라우저가 화면을 그리기 전에 바로 http://localhost:8080/ex01/board/ListAll로 이동하게 됨

		return "redirect:/board/listAll";
	}

	// board/listAll
	@RequestMapping(value = "listAll", method = RequestMethod.GET)
	public void listAll(Model model) throws Exception {
		logger.info("------------ listAll");

		List<BoardVO> list = service.listAll();
		model.addAttribute("list", list);

	}

	@RequestMapping(value = "read", method = RequestMethod.GET)
	public void read(int bno, Model model) throws Exception {
		logger.info("-------------- read, bno=" + bno);
		BoardVO vo = service.read(bno);
		model.addAttribute("board", vo);
	}

	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	public String remove(int bno) throws Exception {
		service.remove(bno);
		return "redirect:/board/listAll";
	}

	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public void modifyGET(int bno, Model model) throws Exception {
		BoardVO vo = service.read(bno);
		model.addAttribute("board", vo);
	}

	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String modifyPOST(BoardVO board) throws Exception {
		service.modify(board);
		return "redirect:/board/listAll";
	}

	@RequestMapping(value = "/listPage", method = RequestMethod.GET)
	public void listPage(Model model, Criteria cri) throws Exception {
		List<BoardVO> list = service.listCriteria(cri);
		model.addAttribute("list", list);

		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(service.listCountCriteria());
		model.addAttribute("pageMaker", pageMaker);
	}

	@RequestMapping(value = "readPage", method = RequestMethod.GET)
	public void readPage(int bno, Criteria cri, Model model) throws Exception {
		logger.info("-------------- readPage, bno=" + bno);
		BoardVO vo = service.read(bno);
		model.addAttribute("board", vo);
		model.addAttribute("cri", cri);
	}

	@RequestMapping(value = "/removePage", method = RequestMethod.POST)
	public String removePage(int bno, int page) throws Exception {
		service.remove(bno);
		return "redirect:/board/listPage?page=" + page;
	}

	@RequestMapping(value = "/modifyPage", method = RequestMethod.GET)
	public void modifyPageGET(int bno, Criteria cri, Model model) throws Exception {
		BoardVO vo = service.read(bno);
		model.addAttribute("board", vo);
	}

	@RequestMapping(value = "/modifyPage", method = RequestMethod.POST)
	public String modifyPagePOST(BoardVO board, Criteria cri) throws Exception {
		service.modify(board);
		return "redirect:/board/listPage?page=" + cri.getPage();
	}
}
