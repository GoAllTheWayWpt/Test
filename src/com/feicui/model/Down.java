package com.feicui.model;

public class Down {
	private String name;
	private Long price;
	private String category;
	private Integer pnum;
	private String description;
	private String cbtime;
	private Integer num;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Integer getPnum() {
		return pnum;
	}
	public void setPnum(Integer pnum) {
		this.pnum = pnum;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCbtime() {
		return cbtime;
	}
	public void setCbtime(String cbtime) {
		this.cbtime = cbtime;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	@Override
	public String toString() {
		return "Down [name=" + name + ", price=" + price + ", category=" + category + ", pnum=" + pnum
				+ ", description=" + description + ", cbtime=" + cbtime + ", num=" + num + "]";
	}
	
}
