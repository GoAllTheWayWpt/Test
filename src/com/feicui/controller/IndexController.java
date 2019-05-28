package com.feicui.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.feicui.model.Down;
import com.feicui.model.PageBean;
import com.feicui.model.Products;
import com.feicui.service.DownService;
import com.feicui.service.ProductService;
import com.feicui.utils.DownPOIUtils;
import com.feicui.utils.WordUtils;

@Controller
public class IndexController {
	@Autowired
	private ProductService productService;
	@Autowired
	private DownService downService;
	@RequestMapping("/showIndex")
	public String showIndex(Model model,Integer page){
		//List<Products> products=productService.findProductsList();
		
		//获取商品的总记录数
		Integer count = productService.findCount();
		
		//创建pageBean的对象，设置页面信息
		PageBean pages = new PageBean(4,page,count);
		model.addAttribute("pages", pages);
		//调用具有分页功能的查询商品方法
		List<Products> products = productService.findProductsListPage(pages);
		model.addAttribute("products",products);
		return "index";
	}
	
	@RequestMapping("showAdminIndex")
	public String showAdminIndex(){
		
		return "admin/index";
	}
	@RequestMapping("down")
	public void down(HttpServletResponse response) {
		List<Down> down = downService.findProductBuyNum();
		System.out.println(down);
		try {
			DownPOIUtils.downPoi(response, down);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@RequestMapping("wordDown")
	public void wordDown(String id,HttpServletResponse response){
		System.out.println("下载中");
		Products product = productService.findProductById(id);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String filename = product.getImgurl().substring(product.getImgurl().lastIndexOf("/"));
		filename = filename.substring(1);
		System.out.println(filename);
		String filepath = "D:\\download\\workspace\\upload\\"+filename;
		String pic="";
		try {
			 pic =WordUtils.getImageString(filepath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dataMap.put("img",pic);
		dataMap.put("name", product.getName());
		dataMap.put("price", product.getPrice().toString());
		dataMap.put("category", product.getCategory());
		dataMap.put("pnum", product.getPnum().toString());
		dataMap.put("cbtime", product.getCbtime());
		dataMap.put("description", product.getDescription());
		//文件路径  
        String filePath= "D://download//workspace//upload//download";  
          
        //文件名称  
        String fileName = System.currentTimeMillis()+".doc";  
          
        /** 生成word */  
        WordUtils.createWord(dataMap, "products.ftl", filePath, fileName,response);
        System.out.println("下载成功");
	}
}
