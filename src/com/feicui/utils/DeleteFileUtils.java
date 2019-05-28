package com.feicui.utils;

import java.io.File;

public class DeleteFileUtils {
	
	public static void deleteFile(String imgurl) {
		String filename = imgurl.substring(imgurl.lastIndexOf("/"));
		filename = filename.substring(1);
		String filepath = "D:\\download\\workspace\\upload\\"+filename;
		File file = new File(filepath);
		file.delete();
	}

}
