/**
 *
 */
package com.risepu.ftk.server.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.token.Sha512DigestUtils;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.risepu.ftk.server.domain.DocumentData;
import com.risepu.ftk.server.domain.ProofDocument;
import com.risepu.ftk.server.service.ChainService;

import net.lc4ever.framework.service.GenericCrudService;

/**
 * @author q-wang
 */
@Service
public class ChainServiceImpl implements ChainService {

    private GenericCrudService crudService;

    @Autowired
    public void setCrudService(GenericCrudService crudService) {
        this.crudService = crudService;
    }

    @Override
    public String sign(Long documentId) {
        //		ProofDocument document = crudService.get(ProofDocument.class, documentId);
        List<DocumentData> datas = crudService.hql(DocumentData.class, "from DocumentData where id.documentId = ?1 order by id.domainId", documentId);
        String dataJson = new Gson().toJson(datas);
        String hash = Sha512DigestUtils.shaHex(dataJson);
        return hash;
    }

    @Override
    public ProofDocument verify(String hash, String id) {
        ProofDocument document = crudService.uniqueResultHql(ProofDocument.class, "from ProofDocument where chainHash = ?1", hash);
        if (id.equals(document.getPersonalUser())) {
            return document;
        }
        return null;
    }

}
