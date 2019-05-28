package com.feicui.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.feicui.model.PageBean;
import com.feicui.model.Products;
import com.feicui.service.ProductService;
import com.feicui.utils.DeleteFileUtils;
import com.feicui.utils.UUIDUtils;
import com.feicui.utils.UploadUtils;

@Controller
public class ProductController {
	@Autowired
	private ProductService service;
	//打开商品管理页面
	@RequestMapping("showProductList")
	public String showProductList(Model model){
		//查询所有的商品
		List<Products> productslist = service.findProductsList();
		
		//将查询的商品传递到页面中显示
		//相当于request.setAttribute
		model.addAttribute("productslist",productslist);
		
		return "admin/product";
	}
	//打开添加商品的页面
	@RequestMapping("showAddProduct")
	public String showAddProduct(){
		
		return "admin/addProduct";
	}
	
	//添加商品
	
	@RequestMapping("saveProduct")
	public String saveProduct(Products pro,MultipartFile imgpic) {
			String upload = UploadUtils.upload(imgpic);
			pro.setImgurl(upload);
			pro.setState(1);
			pro.setId(UUIDUtils.getUUID());
			service.saveProduct(pro);
		System.out.println(pro);
		System.out.println(imgpic.getOriginalFilename());
		return "redirect:showProductList";
	}
	//删除商品
	@RequestMapping("delProduct")
	public String delProduct(String id) {
		System.out.println(id);
		//调用service删除
		Products product =service.findProductById(id);
		DeleteFileUtils.deleteFile(product.getImgurl());
		service.delProduct(id);
		return "redirect:showProductList";
	}
	//打开修改商品的页面
	@RequestMapping("showEditProduct")
	public String showEditProduct(String id,Model model) {
		//查询要修改的商品
		Products product = service.findProductById(id);
		System.out.println(product);
		//将商品传到页面展示数据
		model.addAttribute("product", product);
		return "admin/editProduct";
	}
	//修改商品
	@RequestMapping("updateProduct")
	public String updateProduct(Products pro,MultipartFile imgpic){
		//判断是否有上传的文件
		if(!(imgpic.getOriginalFilename() == null) && !imgpic.getOriginalFilename().equals("")){
			String upload = UploadUtils.upload(imgpic);
			DeleteFileUtils.deleteFile(pro.getImgurl());
			pro.setImgurl(upload);
		}
		service.updateProduct(pro);
		return "redirect:showProductList";
	}
	
	//打开商品展示的页面
	@RequestMapping("showProductkinds")
	public String showProductkinds(String type,Model model,Integer page) {
		Integer count = service.findCountByType(type);
		PageBean pages = new PageBean(4,page,count);
		model.addAttribute("pages", pages);
		model.addAttribute("type",type);
		//查询要显示的类型的商品
		List<Products> productList = service.findProductByTypePage(type,pages);
		//将商品传递到页面中展示
		model.addAttribute("productList",productList);
		return "productkinds";
	}
	@RequestMapping("findProductById")
	public String findProductById(String id,Model model) {
		Products product = service.findProductById(id);
		model.addAttribute("product",product);
		return "productinfo";
	}
}
