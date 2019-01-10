package com.risepu.ftk.web.m.dto;

public class DocumentNumber {
    private Integer year;

    private Integer month;

    private Long number;

    public DocumentNumber() {
    }

    public DocumentNumber(Integer year, Integer month, Long number) {
        this.year = year;
        this.month = month;
        this.number = number;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }
}
