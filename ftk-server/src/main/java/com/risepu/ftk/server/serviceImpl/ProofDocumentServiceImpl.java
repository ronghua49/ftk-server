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

import java.math.BigInteger;
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
        Domain domain1 = crudService.uniqueResultHql(Domain.class, "from Domain where code = ?1", "name");
        if (StringUtils.isEmpty(name)) {
            proofDocuments1 = proofDocumentService.getByOrganization(organization);
            proofDocuments = crudService.hql(firstIndex, pageSize, "from ProofDocument where organization = ?1 and filePath is not null order by createTimestamp desc", organization);
        } else {
            proofDocuments1 = crudService.hql(ProofDocument.class, "from ProofDocument where organization = ?1 and filePath is not null and id in (select id.documentId from DocumentData where  id.domainId = ?2 and value like ?3) order by createTimestamp desc", organization, domain1.getId(), "%" + name + "%");
            proofDocuments = crudService.hql(firstIndex, pageSize, "from ProofDocument where organization = ?1 and filePath is not null and id in (select id.documentId from DocumentData where  id.domainId = ?2 and value like ?3) order by createTimestamp desc", organization, domain1.getId(), "%" + name + "%");
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
                map.put(domain.getCode(), documentData.getValue());
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
        int total = 0;
        List<?> objects = new ArrayList<>();
        List<VerifyHistory> list = new ArrayList<>();
        if (StringUtils.isNotEmpty(name)) {
            String sql = "select a.CHAIN_HASH,p.NUMBER,a.CREATE_TIMESTAMP,p.PERSONAL_USER from FTK_AUTHORIZATION_STREAM a ,FTK_PROOF_DOCUMENT p  where a.VERIFY_STATE in (3,4) and p.CHAIN_HASH=a.CHAIN_HASH  and a.ORG_ID = ?  ORDER BY a.CREATE_TIMESTAMP DESC";
            String sql2 = "select count(*) from  FTK_AUTHORIZATION_STREAM a ,FTK_PROOF_DOCUMENT p  where a.VERIFY_STATE in (3,4) and p.CHAIN_HASH=a.CHAIN_HASH  and a.ORG_ID = ?";
            objects = crudService.sql(firstIndex, pageSize, sql, orgId);
            BigInteger bigInteger = (BigInteger) crudService.uniqueResultSql(sql2, orgId);
            total = bigInteger.intValue();
        } else {
            String sql = "select a.CHAIN_HASH,p.NUMBER,a.CREATE_TIMESTAMP,p.PERSONAL_USER from FTK_AUTHORIZATION_STREAM a ,FTK_PROOF_DOCUMENT p  where a.VERIFY_STATE in (3,4) and p.CHAIN_HASH=a.CHAIN_HASH  and a.ORG_ID = ?  ORDER BY a.CREATE_TIMESTAMP DESC";
            String sql2 = "select count(*) from  FTK_AUTHORIZATION_STREAM a ,FTK_PROOF_DOCUMENT p  where a.VERIFY_STATE in (3,4) and p.CHAIN_HASH=a.CHAIN_HASH  and a.ORG_ID = ?";
            objects = crudService.sql(firstIndex, pageSize, sql, orgId);
            BigInteger bigInteger = (BigInteger) crudService.uniqueResultSql(sql2, orgId);
            total = bigInteger.intValue();
        }
        for (int i = 0; i < objects.size(); i++) {
            VerifyHistory history = new VerifyHistory();
            Object[] object = (Object[]) objects.get(i);
            history.setChainHash((String) object[0]);
            history.setNumber((String) object[1]);
            history.setCreateTimestamp((Date) object[2]);
            history.setIdCard((String) object[3]);
            list.add(history);
        }
        PageResult<VerifyHistory> pageResult = new PageResult<>();
        pageResult.setTotalElements(total);
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
