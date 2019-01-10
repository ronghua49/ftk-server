package com.risepu.ftk.server.domain;

import net.lc4ever.framework.domain.AuditableObject;

import javax.persistence.*;

@Entity
@Table(name = "FTK_SIMPLE_TEMPLATE")
public class SimpleTemplate extends AuditableObject<Long> {
    /**
     * 模板主键
     */
    private Long id;

    private String code;

    /**
     * 模板名称
     */
    private String name;

    @Id
    @Column(name = "ID", precision = 19)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Override
    public Long getId() {
        return id;
    }

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

    @Column(name = "NAME", length = 32)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
