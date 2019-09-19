package com.yi.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.yi.domain.BoardVO;
import com.yi.domain.PageMaker;
import com.yi.domain.SearchCriteria;
import com.yi.service.BoardService;
import com.yi.util.UploadFileUtils;

@Controller
@RequestMapping("/sboard/*")
public class SearchBoardController {
	private static final Logger logger = LoggerFactory.getLogger(SearchBoardController.class);
	
	@Autowired
	private BoardService service;
	
	@Resource(name="uploadPath")
	private String uploadPath;
	
	//@ModelAttribute("cri") -> model.addAttribute("cri", value);	
	@RequestMapping(value="list", method=RequestMethod.GET)
	public void listPage(@ModelAttribute("cri") SearchCriteria cri, Model model) throws Exception {
		logger.info("----------- list");
		List<BoardVO> list = service.listSearch(cri);
		model.addAttribute("list", list);
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(service.listSearchCount(cri));
		model.addAttribute("pageMaker", pageMaker);
	}//return : views/sboard/list.jsp
	
	@RequestMapping(value="register", method=RequestMethod.GET)
	public void registerGET() {
		logger.info("------------- registerGET");	
		
		// board/register
	}
	
	@RequestMapping(value="register", method=RequestMethod.POST)
	public String registerPOST(BoardVO vo, List<MultipartFile> imgFiles) throws Exception {
		logger.info("------------- registerPOST");	
		logger.info(vo.toString());
		
		ArrayList<String> list = new ArrayList<>();
		for(MultipartFile file : imgFiles) {
			logger.info("file name =" + file.getOriginalFilename());
			logger.info("file size =" + file.getSize());
			String savedName = UploadFileUtils.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes());
			list.add(savedName);
		}
		vo.setFiles(list);
		
		service.regist(vo);
		//jsp가 아니라 controller로 감
		//리다이렉트 : 브라우저에 돌아갈때 /board/listAll주소로 바로 이동하라고 처리하는 것임
		//          브라우저가 화면을 그리기 전에 바로 http://localhost:8080/ex01/board/ListAll로 이동하게 됨
		
		return "redirect:/sboard/list"; 
	}
	
	@RequestMapping(value="readPage", method=RequestMethod.GET)
	public void readPage(int bno, @ModelAttribute("cri") SearchCriteria cri, Model model) throws Exception {
		logger.info("-------------- readPage, bno="+bno+", cri+"+cri);
		BoardVO vo = service.read(bno);
		model.addAttribute("board", vo);
	}
	
	@RequestMapping(value="/removePage", method=RequestMethod.POST)
	public String removePage(int bno, int page, SearchCriteria cri,  RedirectAttributes rattr) throws Exception{
		BoardVO vo = service.read(bno);
		service.remove(bno);
		//파일도 지워지도록 처리, 지울 파일 목록
		for(String filename : vo.getFiles()) {
			UploadFileUtils.deleteFile(uploadPath, filename);
		}
		
		rattr.addAttribute("page", cri.getPage());
		rattr.addAttribute("searchType", cri.getSearchType());
		rattr.addAttribute("keyword", cri.getKeyword());
		return "redirect:/sboard/list";
	}
	
	@RequestMapping(value="/modifyPage", method=RequestMethod.GET)
	public void modifyPageGET(int bno, @ModelAttribute("cri") SearchCriteria cri, Model model) throws Exception{
		logger.info("-------------- modifyPage[GET], bno="+bno+", cri+"+cri);
		BoardVO vo = service.read(bno);
		model.addAttribute("board", vo);
	}
	
	@RequestMapping(value="/modifyPage", method=RequestMethod.POST)
	public String modifyPagePOST(BoardVO board,String[] delFiles,List<MultipartFile> imgfiles, SearchCriteria cri, RedirectAttributes rattr ) throws Exception{
		logger.info("-------------- modifyPage[POST], board="+board+", cri"+cri);
		logger.info("delFiles ="+ delFiles);
		
		//파일 업로드
		List<String> list = new ArrayList<>();
		for(MultipartFile file : imgfiles) {
			logger.info("fileNmae ="+file.getOriginalFilename());
			logger.info("size ="+file.getSize()+"");
			if(file.getSize() <= 0) {
				continue;
			}
			String savedName = UploadFileUtils.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes());
			list.add(savedName);
		}
		
		board.setFiles(list);
		
		service.modify(board, delFiles);
		//파일삭제
		if(delFiles != null) {
			for(String delFile : delFiles) {
				logger.info(delFile);
				UploadFileUtils.deleteFile(uploadPath, delFile);
			}
		}
		rattr.addAttribute("page", cri.getPage());
		rattr.addAttribute("searchType", cri.getSearchType());
		rattr.addAttribute("keyword", cri.getKeyword());
		return "redirect:/sboard/list";
	}
	
	@RequestMapping(value="/displayFile", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<byte[]> displayFile(String filename){
		logger.info("--------------------------- displayFile, filename= " + filename);
		
		String formatName = filename.substring(filename.lastIndexOf(".")+1);//확장자만 뽑아냄
		MediaType mType = null;
		ResponseEntity<byte[]> entity = null;
		
		if(formatName.equalsIgnoreCase("jpg")) {
			mType = MediaType.IMAGE_JPEG;
		}else if(formatName.equalsIgnoreCase("gif")) {
			mType = MediaType.IMAGE_GIF;
		}else if(formatName.equalsIgnoreCase("png")) {
			mType = MediaType.IMAGE_PNG;
		}
		InputStream in = null;
		
		try {
		HttpHeaders headers = new HttpHeaders();
		in = new FileInputStream(uploadPath+"/"+filename);
		headers.setContentType(mType);
		
		entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in),headers,HttpStatus.CREATED);
		}catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		}finally {
			if(in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return entity;
	}
}

