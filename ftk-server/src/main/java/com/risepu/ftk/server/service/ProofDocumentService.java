package com.risepu.ftk.server.service;

import com.risepu.ftk.server.domain.ProofDocument;
import com.risepu.ftk.utils.PageResult;
import com.risepu.ftk.web.api.Response;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

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
     * 单据历史（固定）
     *
     * @param organization
     * @param pageNo
     * @param pageSize
     * @param name
     * @return
     */
    PageResult getDocuments(String organization, Integer pageNo, Integer pageSize, String name);

    /**
     * 单据历史详情页
     *
     * @param chainHash 区块链哈希
     * @return
     */
    String getDocument(String chainHash);

    /**
     * 验证历史
     *
     * @param chainHash 哈希串
     * @return
     */
    PageResult getDocuments(List<String> chainHash, Integer pageNo, Integer pageSize, String name);

    /**
     * 根据chainHash 查询文档用户身份证号
     * @param chainHash
     * @return
     */
    String getDocumentPersonCardNo(String chainHash);
}
