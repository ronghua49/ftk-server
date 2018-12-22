package com.risepu.ftk.web.b.dto;    /*
 * @author  Administrator
 * @date 2018/12/22
 */

public class PageRequest {


    private String key;

    private Integer pageNo;

    private Integer  pageSize;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
