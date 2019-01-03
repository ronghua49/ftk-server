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
@Table(name = "FTK_DICTIONARY_DATA")
public class DictionaryData extends AuditableObject<Long> {


    /**
     * 主键
     */
    private Long id;


    /**
     * 父类行业数据字典id
     */
    private Long dictId;


    /**
     *编号
     */
    private  String code;


    /**
     * 字典数据名称
     */
    private String dictdataName;


    /**
     * 字典数据描述
     */
    private String description;


    /**
     * 是否删除
     */
    private boolean idDelete;


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


    @Column(name = "DICT_ID",precision = 19)
    public Long getDictId() {
        return dictId;
    }

    public void setDictId(Long dictId) {
        this.dictId = dictId;
    }

    @Column(name = "CODE")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "DICT_DATA_NAME")
    public String getDictdataName() {
        return dictdataName;
    }

    public void setDictdataName(String dictdataName) {
        this.dictdataName = dictdataName;
    }

    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "IS_DELETE")
    public boolean isIdDelete() {
        return idDelete;
    }

    public void setIdDelete(boolean idDelete) {
        this.idDelete = idDelete;
    }
}
