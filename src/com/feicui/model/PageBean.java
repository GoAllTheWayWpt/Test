package com.feicui.model;
/**
 * 分页的实体类
 * @author Administrator
 *
 */
public class PageBean {
	//每页中显示的记录数
	private Integer pageSize;
	//显示的页数
	private Integer page;
	//总记录数
	private Integer count;
	//偏移量
	private Integer pageNum;
	//总页数
	private Integer pageCount;
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSzie) {
		this.pageSize = pageSzie;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		if(page==null) {
		this.page = 1;
		}else {
			this.page=page;
		}
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Integer getPageCount() {
		return pageCount;
	}
	//总页数
	public void setPageCount() {
		//总页数=总记录数/每页显示的记录数
		if(this.count%this.pageSize==0) {
		this.pageCount = this.count/this.pageSize;
		}else {
			this.pageCount = this.count/this.pageSize+1;
		}
	}
	public Integer getPageNum() {
		return pageNum;
	}
	public void setPageNum() {
		this.pageNum=(page-1)*pageSize;
	}
	public PageBean() {
		
	}
    public PageBean(Integer pageSize,Integer page,Integer count) {
		this.pageSize=pageSize;
		this.count=count;
		this.setPageCount();
		this.setPage(page);
		this.setPageNum();
	}
	@Override
	public String toString() {
		return "PageBean [pageSize=" + pageSize + ", page=" + page + ", count=" + count + ", pageNum=" + pageNum
				+ ", pageCount=" + pageCount + "]";
	}
	
}
