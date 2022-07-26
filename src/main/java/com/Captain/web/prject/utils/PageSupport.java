package com.Captain.web.prject.utils;

public class PageSupport {
	//当前页码-来自于用户输入
	private long currentPageNo = 1;
	
	//总数量（表）
	private long totalCount = 0;
	
	//页面容量
	private long pageSize = 0;
	
	//总页数-totalCount/pageSize（+1）
	private long totalPageCount = 1;

	public long getCurrentPageNo() {
		return currentPageNo;
	}

	public void setCurrentPageNo(long currentPageNo) {
		if(currentPageNo > 0){
			this.currentPageNo = currentPageNo;
		}
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		if(totalCount > 0){
			this.totalCount = totalCount;
			//设置总页数
			this.setTotalPageCountByRs();
		}
	}
	public long getPageSize() {
		return pageSize;
	}

	public void setPageSize(long pageSize) {
		if(pageSize > 0){
			this.pageSize = pageSize;
		}
	}

	public long getTotalPageCount() {
		return totalPageCount;
	}

	public void setTotalPageCount(long totalPageCount) {
		this.totalPageCount = totalPageCount;
	}

	public void setTotalPageCountByRs(){
		if(this.totalCount % this.pageSize == 0){
			this.totalPageCount = this.totalCount / this.pageSize;
		}else if(this.totalCount % this.pageSize > 0){
			this.totalPageCount = this.totalCount / this.pageSize + 1;
		}else{
			this.totalPageCount = 0;
		}
	}
	
}