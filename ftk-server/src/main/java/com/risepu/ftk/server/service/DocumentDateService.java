package com.risepu.ftk.server.service;

import com.risepu.ftk.server.domain.DocumentData.ID;

public interface DocumentDateService {
	/** 新增文档数据 */
	public ID add(Long domainId, Long documentId);

}
