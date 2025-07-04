package com.github.semiprojectshop.repository.seungho.domain;

import org.springframework.stereotype.Repository;


public class ProductVO {

	private long product_id;
	private String product_name;
	private String product_info;
	private String product_contents;
	private long price;
	private long stock;
	private String product_size;
	private String metter;
	private String category_id;
	private CategoryVO cvo;
	
	public long getProduct_id() {
		return product_id;
	}
	
	public void setProduct_id(long product_id) {
		this.product_id = product_id;
	}
	
	public String getProduct_name() {
		return product_name;
	}
	
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	
	public String getProduct_info() {
		return product_info;
	}
	
	public void setProduct_info(String product_info) {
		this.product_info = product_info;
	}
	
	public String getProduct_contents() {
		return product_contents;
	}
	
	public void setProduct_contents(String product_contents) {
		this.product_contents = product_contents;
	}
	
	public long getPrice() {
		return price;
	}
	
	public void setPrice(long price) {
		this.price = price;
	}
	
	public long getStock() {
		return stock;
	}
	
	public void setStock(long stock) {
		this.stock = stock;
	}
	
	public String getProduct_size() {
		return product_size;
	}
	
	public void setProduct_size(String product_size) {
		this.product_size = product_size;
	}
	
	public String getMetter() {
		return metter;
	}
	
	public void setMetter(String metter) {
		this.metter = metter;
	}
	
	public String getCategory_id() {
		return category_id;
	}
	
	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}
	
	public CategoryVO getCvo() {
		return cvo;
	}
	
	public void setCvo(CategoryVO cvo) {
		this.cvo = cvo;
	}
	
	
	
}
