package com.feicui.service;

import java.util.List;

import com.feicui.model.Orders;

public interface OrdersService {

	public int saveOrder(Orders order);

	public List<Orders> findOrders(int id);
}
