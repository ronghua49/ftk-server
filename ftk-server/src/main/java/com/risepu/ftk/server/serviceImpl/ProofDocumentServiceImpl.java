package com.risepu.ftk.server.serviceImpl;

import com.risepu.ftk.server.domain.DocumentData;
import com.risepu.ftk.server.domain.Domain;
import com.risepu.ftk.server.domain.Template;
import com.risepu.ftk.server.service.DocumentDateService;
import com.risepu.ftk.server.service.DomainService;
import com.risepu.ftk.utils.PageResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.risepu.ftk.server.domain.ProofDocument;
import com.risepu.ftk.server.service.ProofDocumentService;

import net.lc4ever.framework.service.GenericCrudService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author L-heng
 */
@Service
public class ProofDocumentServiceImpl implements ProofDocumentService {
    private GenericCrudService crudService;
    @Autowired
    private DomainService domainService;

    @Autowired
    private ProofDocumentService proofDocumentService;

    @Autowired
    private DocumentDateService documentDateService;

    @Autowired
    public void setCrudService(GenericCrudService crudService) {
        this.crudService = crudService;
    }

    @Override
    public Long add(ProofDocument proofDocument) {
        // TODO Auto-generated method stub
        return crudService.save(proofDocument);
    }

    @Override
    public List<ProofDocument> getByOrganization(String organization) {
        List<ProofDocument> list = crudService.hql(ProofDocument.class, "from ProofDocument where organization = ?1", organization);
        return list;
    }

    @Override
    public PageResult getDocuments(String organization, Integer pageNo, Integer pageSize, String name) {
        Integer firstIndex = pageNo * pageSize;
        List<ProofDocument> proofDocuments1 = proofDocumentService.getByOrganization(organization);
        List proofDocuments = crudService.hql(firstIndex, pageSize, "from ProofDocument where organization = ?1", organization);
        List list = new ArrayList();
        List<DocumentData> documentDataList = new ArrayList<>();
        PageResult<Template> pageResult = new PageResult<>();
        for (int i = 0; i < proofDocuments.size(); i++) {
            Map map = new HashMap();
            ProofDocument proofDocument = (ProofDocument) proofDocuments.get(i);
            if (StringUtils.isEmpty(name)) {
                documentDataList = documentDateService.getByDocumentId(proofDocument.getId());
            } else {
                documentDataList = crudService.hql(DocumentData.class, "from DocumentData where id.documentId = ?1 and value = ?2", proofDocument.getId(), name);
            }
            for (int j = 0; j < documentDataList.size(); j++) {
                map.put("chainHash", proofDocument.getChainHash());
                map.put("number", proofDocument.getId());
                DocumentData documentData = documentDataList.get(j);
                Domain domain = domainService.selectById(documentData.getId().getDomainId());
                map.put(domain.getCode(), documentData.getValue());

            }
            list.add(map);
        }
        pageResult.setTotalElements(proofDocuments1.size());
        pageResult.setContent(list);
        return pageResult;
    }

    @Override
    public String getDocument(String chainHash) {
        ProofDocument proofDocument = crudService.uniqueResultHql(ProofDocument.class, "from ProofDocument where chainHash = ?1", chainHash);
        return proofDocument.getFilePath();
    }

}
