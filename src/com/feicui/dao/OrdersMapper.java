package com.feicui.dao;

import java.util.List;

import com.feicui.model.Orders;

public interface OrdersMapper {
	public int saveOrder(Orders order);

	public List<Orders> findOrders(int id);
}
