package com.risepu.ftk.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.risepu.ftk.server.domain.DocumentData;
import com.risepu.ftk.server.domain.DocumentData.ID;
import com.risepu.ftk.server.service.DocumentDateService;

import net.lc4ever.framework.service.GenericCrudService;

/**
 * 
 * @author L-heng
 *
 */
@Service
public class DocumentDateServiceImpl implements DocumentDateService {
	private GenericCrudService crudService;

	@Autowired
	public void setCrudService(GenericCrudService crudService) {
		this.crudService = crudService;
	}

	@Override
	public ID add(Long domainId, Long documentId,String value) {
		// TODO Auto-generated method stub
		ID id = new ID();
		id.setDocumentId(documentId);
		id.setDomainId(domainId);
		DocumentData documentData = new DocumentData();
		documentData.setId(id);
		documentData.setValue(value);
		return crudService.save(documentData);
	}

	@Override
	public void delete(Long domainId, Long documentId,String value) {
		// TODO Auto-generated method stub
		ID id = new ID();
		id.setDocumentId(documentId);
		id.setDomainId(domainId);
		DocumentData documentData = new DocumentData();
		documentData.setId(id);
		documentData.setValue(value);
		crudService.delete(documentData);
	}

}
