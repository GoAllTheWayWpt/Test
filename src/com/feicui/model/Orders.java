package com.feicui.model;

import java.util.List;

public class Orders {
	private String id;
	private Integer money;
	private String receiverinfo;
	private Integer paysate;
	private String ordertime;
	private Integer userId;
	private List<OrderItem> orderItem;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getMoney() {
		return money;
	}
	public void setMoney(Integer money) {
		this.money = money;
	}
	public String getReceiverinfo() {
		return receiverinfo;
	}
	public void setReceiverinfo(String receiverinfo) {
		this.receiverinfo = receiverinfo;
	}
	public Integer getPaysate() {
		return paysate;
	}
	public void setPaysate(Integer paysate) {
		this.paysate = paysate;
	}
	public String getOrdertime() {
		return ordertime;
	}
	public void setOrdertime(String ordertime) {
		this.ordertime = ordertime;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public List<OrderItem> getOrderItem() {
		return orderItem;
	}
	public void setOrderItem(List<OrderItem> orderItem) {
		this.orderItem = orderItem;
	}
	@Override
	public String toString() {
		return "Orders [id=" + id + ", money=" + money + ", receiverinfo=" + receiverinfo + ", paysate=" + paysate
				+ ", ordertime=" + ordertime + ", userId=" + userId + ", orderItem=" + orderItem + "]";
	}
	
	
}
