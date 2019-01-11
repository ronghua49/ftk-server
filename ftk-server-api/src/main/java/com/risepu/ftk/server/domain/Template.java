package com.risepu.ftk.server.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import net.lc4ever.framework.domain.AuditableObject;

/**
 * 模板
 *
 * @author q-wang
 */
@Entity
@Table(name = "FTK_TEMPLATE")
public class Template extends AuditableObject<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 模板主键
     */
    private Long id;

    private String code;

    /**
     * 模板名称
     */
    private String name;

    /**
     * 模板状态 0已启用，1未启用
     */
    private Integer state;

    private Integer titleSize;

    private Integer hashSize;

    private Integer contentSize;

    /**
     * 模板描述
     */
    private String description;

    /**
     * 一次模板值
     */
    private String _template;

    /**
     * 二次模板路径
     */
    private String filePath;

    @Id
    @Column(name = "ID", precision = 19)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "CODE", length = 32)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "NAME", length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "TITLE_SIZE")
    public Integer getTitleSize() {
        return titleSize;
    }

    public void setTitleSize(Integer titleSize) {
        this.titleSize = titleSize;
    }

    @Column(name = "HASH_SIZE")
    public Integer getHashSize() {
        return hashSize;
    }

    public void setHashSize(Integer hashSize) {
        this.hashSize = hashSize;
    }

    @Column(name = "CONTENT_SIZE")
    public Integer getContentSize() {
        return contentSize;
    }

    public void setContentSize(Integer contentSize) {
        this.contentSize = contentSize;
    }

    @Column(name = "STATE", length = 1)
    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "_TEMPLATE", length = 1000)
    public String get_template() {
        return _template;
    }

    public void set_template(String _template) {
        this._template = _template;
    }

    @Column(name = "FILE_PATH", length = 128)
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

}
