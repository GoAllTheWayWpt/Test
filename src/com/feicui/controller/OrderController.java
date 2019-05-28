package com.feicui.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.feicui.model.OrderItem;
import com.feicui.model.Orders;
import com.feicui.model.Products;
import com.feicui.model.Users;
import com.feicui.service.OrdersService;
import com.feicui.utils.DateUtils;
import com.feicui.utils.UUIDUtils;

@Controller
public class OrderController {
	@Autowired
	private OrdersService serice;
	
	//打开生成订单的页面
	@RequestMapping("showCreateOrder")
	public String showCreateOrder(){
		
		return "order";
	}
	
	//生成订单
	@RequestMapping("createOrder")
	public String createOrder(Orders order,HttpServletRequest request){
		//只获取到了金额和收货地信息，需要补充订单的其他信息
		String id = UUIDUtils.getUUID();
		order.setId(id);
		order.setOrdertime(DateUtils.format(new Date()));
		order.setPaysate(0);//0:未支付   1:已支付
		HttpSession session = request.getSession();
		Users user = (Users) session.getAttribute("user");
		order.setUserId(user.getId());
		
		//获取购物车信息
		Map<Products, Integer> cart = (Map<Products, Integer>) session.getAttribute("cart");
		Set<Products> keySet = cart.keySet();
		
		List<OrderItem> items = new ArrayList<OrderItem>();
		
		for (Products products : keySet) {
			//设置订单项信息
			OrderItem item = new OrderItem();
			item.setOrderId(id);
			item.setProductId(products.getId());
			item.setBuynum(cart.get(products));
			
			items.add(item);
		}
		
		order.setOrderItem(items);
		
		
		serice.saveOrder(order);
		
		session.removeAttribute("cart");
		
		
		
		
		return "index";
	}
	@RequestMapping("showOrder")
	public String showOrder(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Users user = (Users) session.getAttribute("user");
		List<Orders> orders = new ArrayList<Orders>();
		orders = serice.findOrders(user.getId());
		System.out.println(orders);
		request.setAttribute("orders", orders);
		return "showorder";
	}
}
