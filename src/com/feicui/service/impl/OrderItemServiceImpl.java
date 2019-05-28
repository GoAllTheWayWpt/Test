package com.feicui.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feicui.dao.OrderItemMapper;
import com.feicui.model.OrderItem;
import com.feicui.service.OrderItemService;
@Service
public class OrderItemServiceImpl implements OrderItemService {
	@Autowired
	private OrderItemMapper mapper;
	@Override
	public int saveOrderItem(OrderItem item) {
		return mapper.saveOrderItem(item);
	}

}
