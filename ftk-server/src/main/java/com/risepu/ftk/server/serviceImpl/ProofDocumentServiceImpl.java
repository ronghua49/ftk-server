package com.risepu.ftk.server.serviceImpl;

import com.risepu.ftk.server.domain.DocumentData;
import com.risepu.ftk.server.domain.Domain;
import com.risepu.ftk.server.domain.ProofDocument;
import com.risepu.ftk.server.domain.Template;
import com.risepu.ftk.server.service.DocumentDateService;
import com.risepu.ftk.server.service.DomainService;
import com.risepu.ftk.server.service.ProofDocumentService;
import com.risepu.ftk.utils.PageResult;
import com.risepu.ftk.web.b.dto.VerifyHistory;
import net.lc4ever.framework.service.GenericCrudService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
        List<ProofDocument> list = crudService.hql(ProofDocument.class, "from ProofDocument where organization = ?1 and filePath is not null", organization);
        return list;
    }

    @Override
    public PageResult getDocuments(String organization, Integer pageNo, Integer pageSize, String name) {
        Integer firstIndex = pageNo * pageSize;
        List<ProofDocument> proofDocuments1 = new ArrayList<>();
        List proofDocuments = new ArrayList();
        if (StringUtils.isEmpty(name)) {
            proofDocuments1 = proofDocumentService.getByOrganization(organization);
            proofDocuments = crudService.hql(firstIndex, pageSize, "from ProofDocument where organization = ?1 and filePath is not null and state = 0 order by createTimestamp desc", organization);
        } else {
            proofDocuments1 = crudService.hql(ProofDocument.class, "from ProofDocument where organization = ?1 and filePath is not null and state = 0 and (number like ?2 or personalUser like ?3) order by createTimestamp desc", organization, "%" + name + "%", "%" + name + "%");
            proofDocuments = crudService.hql(firstIndex, pageSize, "from ProofDocument where organization = ?1 and filePath is not null and state = 0 and (number like ?2 or personalUser like ?3) order by createTimestamp desc", organization, "%" + name + "%", "%" + name + "%");
        }

        List list = new ArrayList();
        List<DocumentData> documentDataList = new ArrayList<>();
        PageResult<Template> pageResult = new PageResult<>();
        for (int i = 0; i < proofDocuments.size(); i++) {
            Map map = new HashMap();
            ProofDocument proofDocument = (ProofDocument) proofDocuments.get(i);
            documentDataList = documentDateService.getByDocumentId(proofDocument.getId());
            map.put("chainHash", proofDocument.getChainHash());
            map.put("number", proofDocument.getNumber());
            map.put("createTimestamp", proofDocument.getCreateTimestamp());
            for (int j = 0; j < documentDataList.size(); j++) {
                DocumentData documentData = documentDataList.get(j);
                Domain domain = domainService.selectById(documentData.getId().getDomainId());
                if (domain != null) {
                    map.put(domain.getCode(), documentData.getValue());
                }
            }
            list.add(map);
        }
        pageResult.setTotalElements(proofDocuments1.size());
        pageResult.setContent(list);
        return pageResult;
    }

    @Override
    public ProofDocument getDocument(String chainHash) {
        ProofDocument proofDocument = crudService.uniqueResultHql(ProofDocument.class, "from ProofDocument where chainHash = ?1", chainHash);
        return proofDocument;
    }

    @Override
    public PageResult<VerifyHistory> getVerfifyHistoryData(String orgId, Integer pageNo, Integer pageSize, String name) {
        Integer firstIndex = pageNo * pageSize;
        List<VerifyHistory> objects = new ArrayList<>();
        List<VerifyHistory> list = new ArrayList<>();
        if (StringUtils.isNotEmpty(name)) {
            objects = crudService.hql(VerifyHistory.class, "select new com.risepu.ftk.web.b.dto.VerifyHistory (a.chainHash as chainHash,b.number as number,a.createTimestamp as createTimestamp,b.personalUser as idCard) from AuthorizationStream a,ProofDocument b where  a.chainHash=b.chainHash and a.verifyState in (3,4) and a.orgId = ?1 and (b.personalUser like ?2 or b.number like ?3) ORDER BY a.createTimestamp DESC", orgId, "%" + name + "%", "%" + name + "%");
            list = crudService.hql(VerifyHistory.class, firstIndex, pageSize, "select new com.risepu.ftk.web.b.dto.VerifyHistory (a.chainHash as chainHash,b.number as number,a.createTimestamp as createTimestamp,b.personalUser as idCard) from AuthorizationStream a,ProofDocument b where a.chainHash=b.chainHash and a.verifyState in (3,4) and a.orgId = ?1 and (b.personalUser like ?2  or b.number like ?3) ORDER BY a.createTimestamp DESC", orgId, "%" + name + "%", "%" + name + "%");
        } else {
            objects = crudService.hql(VerifyHistory.class, "select new com.risepu.ftk.web.b.dto.VerifyHistory (a.chainHash as chainHash,b.number as number,a.createTimestamp as createTimestamp,b.personalUser as idCard) from AuthorizationStream a,ProofDocument b where a.chainHash=b.chainHash and a.verifyState in (3,4) and a.orgId = ?1 ORDER BY a.createTimestamp DESC", orgId);
            list = crudService.hql(VerifyHistory.class, firstIndex, pageSize, "select new com.risepu.ftk.web.b.dto.VerifyHistory (a.chainHash as chainHash,b.number as number,a.createTimestamp as createTimestamp,b.personalUser as idCard) from AuthorizationStream a,ProofDocument b where a.chainHash=b.chainHash and a.verifyState in (3,4) and a.orgId = ?1 ORDER BY a.createTimestamp DESC", orgId);
        }
        PageResult<VerifyHistory> pageResult = new PageResult<>();
        pageResult.setTotalElements(objects.size());
        pageResult.setContent(list);
        return pageResult;
    }

    /**
     * 根据chainHash 查询文档用户身份证号
     *
     * @param chainHash
     * @return
     */
    @Override
    public String getDocumentPersonCardNo(String chainHash) {
        return crudService.uniqueResultHql(ProofDocument.class, "from ProofDocument where chainHash = ?1", chainHash).getPersonalUser();
    }

    @Override
    public ProofDocument getDocumentById(Long documentId) {
        return crudService.uniqueResultHql(ProofDocument.class, "from ProofDocument where id = ?1", documentId);
    }

    @Override
    public void updateDocument(ProofDocument proofDocument) {
        crudService.update(proofDocument);
    }
}
