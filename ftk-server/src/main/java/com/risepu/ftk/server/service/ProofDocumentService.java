package com.risepu.ftk.server.service;

/**
 * 
 * @author L-heng
 *
 */
public interface ProofDocumentService {
	/** 新增文档 */
	public Long add(Long template, String chainHash, String organization, String personalUser);

}
