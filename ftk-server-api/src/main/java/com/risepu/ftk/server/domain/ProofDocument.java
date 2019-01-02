package com.risepu.ftk.server.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import net.lc4ever.framework.domain.AuditableObject;

/**
 * 文档
 *
 * @author q-wang
 */
@Entity
@Table(name = "FTK_PROOF_DOCUMENT")
public class ProofDocument extends AuditableObject<Long> {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 模板id
     */
    private Long template;

    /**
     * 单号
     */
    private String number;

    /**
     * 区块hash
     */
    private String chainHash;

    /**
     * 企业信息id
     */
    private String organization;

    /**
     * 个人信息id
     */
    private String personalUser;

    /**
     * 文档路径
     */
    private String filePath;

    private Integer index;

    @Id
    @Column(name = "ID", precision = 19)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "TEMPLATE", precision = 19)
    public Long getTemplate() {
        return template;
    }

    public void setTemplate(Long template) {
        this.template = template;
    }

    @Column(name = "NUMBER")
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Column(name = "CHAIN_HASH")
    public String getChainHash() {
        return chainHash;
    }

    public void setChainHash(String chainHash) {
        this.chainHash = chainHash;
    }

    @Column(name = "ORGANIZATION")
    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    @Column(name = "PERSONAL_USER", length = 50)
    public String getPersonalUser() {
        return personalUser;
    }

    public void setPersonalUser(String personalUser) {
        this.personalUser = personalUser;
    }

    @Column(name = "FILE_PATH")
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Column(name = "_INDEX")
    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }
}
