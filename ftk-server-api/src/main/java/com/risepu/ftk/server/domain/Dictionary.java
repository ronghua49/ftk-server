package com.risepu.ftk.server.domain;    /*
 * @author  Administrator
 * @date 2019/1/3
 */

import net.lc4ever.framework.domain.AuditableObject;

import javax.persistence.*;

/**
 * @author ronghaohua
 */
@Entity
@Table(name = "FTK_DICTIONARY")
public class Dictionary extends AuditableObject<Long> {

    /**
     * 主键
     */
    private Long id;

    /**
     * 字典编号
     */
    private String dictCode;

    /**
     * 字典字段名
     */
    private String name;

    /**
     * 字典说明
     */
    private String  description;

    @Id
    @Column(name = "ID", precision = 19)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id=id;
    }

    @Column(name = "DICT_CODE",length = 2)
    public String getDictCode() {
        return dictCode;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }

    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
