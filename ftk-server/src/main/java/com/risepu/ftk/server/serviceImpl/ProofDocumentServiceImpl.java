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
	public Long add(Long template, String chainHash, String organization, String personalUser) {
		// TODO Auto-generated method stub
		ProofDocument proofDocument = new ProofDocument();
		proofDocument.setChainHash(chainHash);
		proofDocument.setTemplate(template);
		proofDocument.setOrganization(organization);
		proofDocument.setPersonalUser(personalUser);
		return crudService.save(proofDocument);
	}

}
