package com.yi.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Calendar;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.springframework.util.FileCopyUtils;

public class UploadFileUtils {
	public static String uploadFile(String uploadPath, String originalName, byte[] fileData) throws Exception {
		String path = calcPath(uploadPath);// 2019/09/11
		
		UUID uid = UUID.randomUUID();
		String savedName = uid + "_" + originalName;
		
		File target = new File(uploadPath+path,savedName);//outUploadPath 경로가 반드시 존재한다는 가정하에 처리됨
		FileCopyUtils.copy(fileData, target);
		
		String thumbFile = null;
		thumbFile =makeThumbnail(uploadPath, path, savedName);
		
		return thumbFile; // 년,월,일/s_파일
	}
	
	private static String calcPath(String uploadPath) {
		//년, 월, 일 폴더
		Calendar cal = Calendar.getInstance();
		// 2019
		String yearPath = "/"+cal.get(Calendar.YEAR);
		// 2019/09
		String monthPath = String.format("%s/%02d", yearPath, cal.get(Calendar.MONTH)+1);
		// 2019/09/11
		String datePath = String.format("%s/%02d", monthPath, cal.get(Calendar.DATE));
		
		makeDir(uploadPath,yearPath,monthPath,datePath);
		
		return datePath;
	}
	
	public static void makeDir(String uploadPath, String... paths) {
		for(String path : paths) {
			File dir = new File(uploadPath+path);
			if(dir.exists() == false) {
				dir.mkdir();
			}
		}
	}
	
	private static String makeThumbnail(String uploadPath, String path, String fileName) throws Exception {
		
		//메모리에 원본 이미지 정보를 읽어 들임
		BufferedImage sourceImg = ImageIO.read(new File(uploadPath+path,fileName));
		//원본 이미지를 활용하여 메모리에 사이즈 변경한 새 이미지 만들기, 높이 100으로 고정하여 가로 비율은 자동 조절
		BufferedImage destImg = Scalr.resize(sourceImg, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT,100);
		// 작은 이미지 경로, 파일명에 s_가 붙도록 한다.
		String thumbnailName = uploadPath+path+"/s_"+fileName;
		
		File newFile = new File(thumbnailName);
		//파일 확장자 찾기
		String formatName = fileName.substring(fileName.lastIndexOf(".")+1);
		//메모리에 담긴 작은 이미지를 파일에 저장
		ImageIO.write(destImg, formatName.toUpperCase(), newFile);//작은 파일이 생섬됨
		// c/zzz/upload를 뺀 나머지 경로를 리턴함
		return thumbnailName.substring(uploadPath.length());
	}
	
	
	public static void deleteFile(String uploadPath,String filename) {
		//작은 이미지 파일 삭제
		File file = new File(uploadPath+filename); //s_
		file.delete();
		
		//원본 파일 삭제
		String originFileName = filename.substring(0, 12) + filename.substring(14);
		File file2 = new File(uploadPath+originFileName);
		file2.delete();
	}
	
	
}
