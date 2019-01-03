package com.risepu.ftk.web.m.dto;

public class DomainRequest {

    /**
     * 数据名称code
     */
    private String code;

    /**
     * 模板数据名称
     */
    private String label;

    /**
     * 模板数据类型
     */
    private String type;

    /**
     * 校验规则
     */
    private String kegex;

    /**
     * 描述
     */
    private String description;

    /**
     * 模板id
     */
    private Long templateId;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKegex() {
        return kegex;
    }

    public void setKegex(String kegex) {
        this.kegex = kegex;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }
}
