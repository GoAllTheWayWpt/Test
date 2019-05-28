package com.feicui.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feicui.dao.DownMapper;
import com.feicui.model.Down;
import com.feicui.service.DownService;
@Service
public class DownServiceImpl implements DownService{
	@Autowired
	private DownMapper downMapper;
	@Override
	public List<Down> findProductBuyNum() {
		// TODO Auto-generated method stub
		return downMapper.findProductBuyNum();
	}

}
