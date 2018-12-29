package com.risepu.ftk.server.service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.risepu.ftk.server.domain.ProofDocument;

/**
 * @author q-wang
 */
public interface ChainService {

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    String sign(Long documentId);

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    ProofDocument verify(String hash, String id);
}
