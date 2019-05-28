package com.feicui.service;

import java.util.List;

import com.feicui.model.PageBean;
import com.feicui.model.Products;

public interface ProductService {
	public int saveProduct(Products pro);
	public int updateProduct(Products pro);
	public int delProduct(String id);
	public Products findProductById(String id);
	public List<Products> findProductsList();
	public List<Products> findProductByType(String type);
	public Integer findCount();
	public List<Products> findProductsListPage(PageBean pageBean);
	public Integer findCountByType(String type);
	public List<Products> findProductByTypePage(String type, PageBean pages);
}
