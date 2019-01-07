package com.risepu.ftk.server.service;

import com.risepu.ftk.server.domain.DocumentData;
import com.risepu.ftk.server.domain.DocumentData.ID;

import java.util.List;

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

    /**
     * 根据文档id得到所有文档数据
     *
     * @param documentId 文档id
     * @return
     */
    List<DocumentData> getByDocumentId(Long documentId);
}
