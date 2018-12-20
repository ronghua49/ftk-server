package com.risepu.ftk.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author L-heng
 */
public class PageResult<T> {

	private int code; // 状态码, 0表示成功

	private String msg; // 提示信息

	private long count; // 总数量, total

	private List<T> data; // 当前数据, bootstrapTable是rows

	public PageResult() {
	}

	public PageResult(List<T> rows) {
		this.data = rows;
		this.count = rows.size();
		this.code = 0;
		this.msg = "";
	}

	public PageResult(long total, List<T> rows) {
		this.count = total;
		this.data = rows;
		this.code = 0;
		this.msg = "";
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
		this.count = data.size();
	}

	private String resultCode;

	/** 总页数 */
	private Integer totalPages;
	/** 总条数 */
	private Integer totalElements;
	/** 当前页数 */
	private Integer number;
	/** 每页显示条数 */
	private Integer size;

	private List<T> content;
	/** 总页码数组 */
	private List<Integer> pages = new ArrayList<>();

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public Integer getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int count, int size) {
		int totalPages = 1;
		if (count >= size) {
			// 如果总条数大于 分页的条数
			totalPages = count / size;
			// 如果余数不为0
			if (!(count % size == 0)) {
				totalPages++;
			}
		}
		this.totalPages = totalPages;

		for (int i = 0; i < totalPages; i++) {
			this.pages.add(i + 1);
		}
	}

	public Integer getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(Integer totalElements) {
		this.totalElements = totalElements;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public List<T> getContent() {
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}

	public List<Integer> getPages() {
		return pages;
	}

	@Override
	public String toString() {
		return "PageResult [resultCode=" + resultCode + ", totalPages=" + totalPages + ", totalElements=" + totalElements + ", number=" + number + ", size=" + size + ", content=" + content + ", pages=" + pages + "]";
	}

}
