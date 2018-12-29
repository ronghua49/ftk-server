package com.risepu.ftk.server.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import net.lc4ever.framework.domain.AuditableObject;

/**
 * 模板数据定义.
 *
 * @author q-wang
 */
@Entity
@Table(name = "FTK_DOMAIN")
public class Domain extends AuditableObject<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

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
     * 数据最小长度
     */
    private Integer min;

    /**
     * 数据最大长度
     */
    private Integer max;

    /**
     * 校验规则
     */
    private String kegex;

    /**
     * 是否删除 0否，1是
     */
    private Integer isDelete;

    /**
     * 描述
     */
    private String description;

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

    @Column(name = "CODE",length = 50)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "LABEL",length = 50)
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Column(name = "TYPE",length = 50)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "MIN",length = 50)
    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    @Column(name = "MAX",length = 150)
    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    @Column(name = "KEGEX",length = 50)
    public String getKegex() {
        return kegex;
    }

    public void setKegex(String kegex) {
        this.kegex = kegex;
    }

    @Column(name = "ISDELETE", length = 1)
    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
