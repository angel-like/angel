package com.xmsy.common.define.page;

import java.io.Serializable;

/**
 * 分页请求参数
 * 
 * @author Administrator
 *
 */
public class PageParam implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 最大分页30条
	 */
	private int maxLimit = 100;

	/**
	 * 当前页,默认第一页（最小页也是从1开始）
	 */
	private int page = 1;

	/**
	 * 每页数量，默认每页10条记录（必须大于0）
	 */
	private int limit = 10;

	/**
	 * 需要排序的字段名，和实体的属性名匹配（多个字段进行排序用逗号隔开）
	 */
	private String sort;

	/**
	 * 排序方式desc或asc（支持desc和asc，默认asc( true asc，false desc)）
	 */
	private Boolean direction = true;

	/**
	 * 搜索条件:(全局搜索) 搜索的关键字（where xxx='search'）
	 */
	private String search;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		if (page > 1) {
			this.page = page;
		}
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		if(limit>maxLimit) {
			this.limit=maxLimit;
		}else {
			this.limit = limit;
		}
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public Boolean getDirection() {
		return direction;
	}

	public void setDirection(Boolean direction) {
		this.direction = direction;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

}
