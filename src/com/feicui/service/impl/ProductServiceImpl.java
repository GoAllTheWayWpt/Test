package com.feicui.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feicui.dao.ProductMapper;
import com.feicui.model.PageBean;
import com.feicui.model.Products;
import com.feicui.service.ProductService;
@Service
public class ProductServiceImpl implements ProductService{
	@Autowired
	private ProductMapper productMapper;

	@Override
	public int saveProduct(Products pro) {
		// TODO Auto-generated method stub
		return productMapper.saveProduct(pro);
	}

	@Override
	public int updateProduct(Products pro) {
		// TODO Auto-generated method stub
		return productMapper.updateProduct(pro);
	}

	@Override
	public int delProduct(String id) {
		// TODO Auto-generated method stub
		return productMapper.delProduct(id);
	}

	@Override
	public Products findProductById(String id) {
		// TODO Auto-generated method stub
		return productMapper.findProductById(id);
	}

	@Override
	public List<Products> findProductsList() {
		// TODO Auto-generated method stub
		return productMapper.findProductsList();
	}

	@Override
	public List<Products> findProductByType(String type) {
		// TODO Auto-generated method stub
		return productMapper.findProductByType(type);
	}

	@Override
	public Integer findCount() {
		// TODO Auto-generated method stub
		return productMapper.findCount();
	}

	@Override
	public List<Products> findProductsListPage(PageBean pageBean) {
		// TODO Auto-generated method stub
		return productMapper.findProductsListPage(pageBean);
	}

	@Override
	public Integer findCountByType(String type) {
		// TODO Auto-generated method stub
		return productMapper.findCountByType(type);
	}

	@Override
	public List<Products> findProductByTypePage(String type, PageBean pages) {
		// TODO Auto-generated method stub
		return productMapper.findProductByTypePage(type,pages);
	}

}
