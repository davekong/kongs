package com.kongs.common;

import java.io.Serializable;
import java.util.List;

public class Page implements Serializable {

	private static final long serialVersionUID = 9126912421501466145L;
	
	private Integer recodeCount;//��¼��
	private Integer pageCount;//ҳ��
	private Integer pageSize;//ÿҳ��ʾ��¼��
	private Integer pageNo;//�ڼ�ҳ
	private List list;//����
	
	
	public Integer getRecodeCount() {
		return recodeCount;
	}
	public void setRecodeCount(Integer recodeCount) {
		this.recodeCount = recodeCount;
	}
	public Integer getPageCount() {
		return pageCount;
	}
	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}
}
