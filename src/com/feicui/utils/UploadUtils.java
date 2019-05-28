package com.feicui.utils;

import java.awt.image.BufferedImage;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;

import org.springframework.web.multipart.MultipartFile;

public class UploadUtils {

	public static String upload(MultipartFile file) {
		// 访问位置
		String fwPath = "img/";
		String filename = null;
		int width = 100;
		int height = 100;
		// E:\project\workspace\ks

		try {
			// 定义上传的位置
			String path = "E:\\project\\workspace\\ks\\bookshopping\\WebContent\\img\\";

			File filePath = new File(path);
			if (!filePath.exists()) {

				filePath.mkdir();
			}

			// 获取上传文件的真实名称
			filename = file.getOriginalFilename();

			// 获取上传文件的后缀名
			filename = filename.substring(filename.lastIndexOf('.'));

			// 上传文件新的名称
			filename = UUIDUtils.getUUID() + filename;
			path = path + filename;

			file.transferTo(new File(path));
			
		     Icon icon = null;
		     try
		     {
		    	 icon = getFixedIcon(path,width,height);
		     }
		     catch(Exception e)
		     {
		      System.out.println("exception : " +e);
		     }
		     System.out.println(" ### " +icon); //生成新图片的位置；
		
	
			System.out.println("上传成功");
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return fwPath + filename;

	}

	public static Icon getFixedIcon(String filePath, int width, int height) throws Exception {
		File f = new File(filePath);

		BufferedImage bi = ImageIO.read(f);

		double wRatio = (new Integer(width)).doubleValue() / bi.getWidth(); // 宽度的比例

		double hRatio = (new Integer(height)).doubleValue() / bi.getHeight(); // 高度的比例

		Image image = bi.getScaledInstance(width, height, Image.SCALE_SMOOTH); // 设置图像的缩放大小

		AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(wRatio, hRatio), null); // 设置图像的缩放比例

		image = op.filter(bi, null);

		int lastLength = filePath.lastIndexOf(".");
		String subFilePath = filePath.substring(0, lastLength); // 得到图片输出路径
		String fileType = filePath.substring(lastLength); // 图片类型
		File zoomFile = new File(subFilePath + "_" + width + "_" + height + fileType);

		Icon ret = null;
		try {
			ImageIO.write((BufferedImage) image, "jpg", zoomFile);
			ret = new ImageIcon(zoomFile.getPath());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ret;
	}

}
