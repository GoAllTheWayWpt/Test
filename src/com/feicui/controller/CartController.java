package com.feicui.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.feicui.model.Products;
import com.feicui.service.ProductService;
import com.feicui.utils.ProductUtils;
@Controller
public class CartController {
	@Autowired
	private ProductService productService;
	//显示购物车页面
	@RequestMapping("showCart")
	public String showCart() {
		return "showcat";
	}
	//加入购物车
	@RequestMapping("addCart")
	@ResponseBody
	public String addCart(String id,HttpServletRequest request) {
		//获取到加入购物车的商品
		Products product =null;
		//先从session中获取购物车对象
		HttpSession session = request.getSession();
		Map<Products,Integer> cart = (Map<Products,Integer>)session.getAttribute("cart");
		//定义一个map集合作为购物车对象
		if(cart==null) {
		 cart = new HashMap<Products,Integer>();
		 product = productService.findProductById(id);
		 cart.put(product, 1);
		}
		else {
		//判断加入购物车的商品，在购物车是否存在
		Set<Products> keySet = cart.keySet();
		boolean f =false;
		for(Products products : keySet) {
			if(products.getId().equals(id)) {
				product = products;
				break;
			}
		}
		if(product==null) {
			product = productService.findProductById(id);
			cart.put(product, 1);
		}else {
			cart.put(product, cart.get(product)+1);
		}
		}
		
		//将购物车对象放到session中
		
		session.setAttribute("cart", cart);
		
		return "{\"msg\":\"true\"}";
	}
	//修改购物车信息
	@RequestMapping("updateCart")
	public String updateCart(String id,Integer count,HttpServletRequest request) {
		//获取到购物车对象
		System.out.println(id+"+++"+count);
		HttpSession session = request.getSession();
		Map<Products,Integer> cart = (Map<Products,Integer>)session.getAttribute("cart");
		Set<Products> keySet = cart.keySet();
		Products product = ProductUtils.findProduct(keySet,id);
		cart.put(product, count);
		if(count==0) {
			cart.remove(product);
		}
		session.setAttribute("cart", cart);
		return "redirect:showCart";
		
		
	}
}
