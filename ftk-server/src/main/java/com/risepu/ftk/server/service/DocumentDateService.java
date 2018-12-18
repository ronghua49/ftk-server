package com.risepu.ftk.server.service;

import com.risepu.ftk.server.domain.DocumentData.ID;

/**
 * @author L-heng
 */
public interface DocumentDateService {
    /**
     * 新增文档数据
     */
    ID add(Long domainId, Long documentId, String value);

    /**
     * 删除文档数据
     */
    void delete(Long domainId, Long documentId, String value);

}
