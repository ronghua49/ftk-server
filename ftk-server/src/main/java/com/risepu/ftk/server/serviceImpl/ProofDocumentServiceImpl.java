package com.risepu.ftk.server.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.risepu.ftk.server.domain.ProofDocument;
import com.risepu.ftk.server.service.ProofDocumentService;

import net.lc4ever.framework.service.GenericCrudService;

/**
 * 
 * @author L-heng
 *
 */
@Service
public class ProofDocumentServiceImpl implements ProofDocumentService {
	private GenericCrudService crudService;

	@Autowired
	public void setCrudService(GenericCrudService crudService) {
		this.crudService = crudService;
	}

	@Override
	public Long add(ProofDocument proofDocument) {
		// TODO Auto-generated method stub
		System.out.println("22222222222222222"+proofDocument.getFilePath());
		return crudService.save(proofDocument);
	}

}
