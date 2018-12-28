package com.risepu.ftk.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ronghaohua
 */
public class PageResult<T> {
    private String resultCode;

    /**
     * 总页数
     */
    private Integer totalPages;
    /**
     * 总条数
     */
    private Integer totalElements;
    /**
     * 当前页数
     */
    private Integer number;
    /**
     * 每页显示条数
     */
    private Integer size;

    private List<T> content;
    /**
     * 总页码数组
     */
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
