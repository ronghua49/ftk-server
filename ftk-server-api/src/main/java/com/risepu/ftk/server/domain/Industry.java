package com.risepu.ftk.server.domain;    /*
 * @author  Administrator
 * @date 2019/1/3
 */

import net.lc4ever.framework.domain.AuditableObject;

/**
 * @author ronghaohua
 */
public class Industry extends AuditableObject<Long> {

    /**
     * 主键
     */
    private Long id;

    /**
     * 字典编号
     */
    private String dictCode;

    /**
     * 字典说明
     */
    private String  description;


    /**
     * 值字段名
     */
    private String name;































    @Override
    public Long getId() {
        return null;
    }

    @Override
    public void setId(Long id) {

    }


}
