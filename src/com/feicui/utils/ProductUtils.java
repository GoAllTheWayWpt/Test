package com.feicui.utils;

import java.util.Set;

import com.feicui.model.Products;

public class ProductUtils {
	public static Products findProduct(Set<Products> set,String id) {
		for(Products products : set) {
			if(products.getId().equals(id)) {
				return products;
			}
		}
		return null;
	}

}
