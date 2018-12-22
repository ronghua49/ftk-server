package com.risepu.ftk.server.service;

import com.risepu.ftk.server.domain.ProofDocument;
import com.risepu.ftk.utils.PageResult;
import com.risepu.ftk.web.api.Response;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * @author L-heng
 */
public interface ProofDocumentService {
    /**
     * 新增文档
     */
    Long add(ProofDocument proofDocument);

    /**
     * 根据企业id获得所有文档
     *
     * @param organization
     * @return
     */
    List<ProofDocument> getByOrganization(String organization);

    /**
     * 单据历史(全部）
     *
     * @param organization 企业id
     * @param pageNo       当前页码
     * @param pageSize     每页显示条数
     * @param name         搜索条件
     * @return
     */
//    PageResult getAllDocument(String organization, Integer pageNo, Integer pageSize, String name);

    /**
     * 单据历史（固定）
     *
     * @param organization
     * @param pageNo
     * @param pageSize
     * @param name
     * @return
     */
    PageResult getDocument(String organization, Integer pageNo, Integer pageSize, String name);

}
