package com.risepu.ftk.server.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import net.lc4ever.framework.domain.BaseEntity;

/**
 * @author L-heng
 */
@Entity
@Table(name = "FTK_ORGANIZATION_TEMPLATE")
public class OrganizationTemplate implements BaseEntity<OrganizationTemplate.ID> {

    private static final long serialVersionUID = 1L;

    @Embeddable
    public static class ID implements Serializable {

        private static final long serialVersionUID = 1L;

        /**
         * 企业id
         */
        private String organizationId;

        /**
         * 模板id
         */
        private Long templateId;

        @Column(name = "ORGANIZATION")
        public String getOrganizationId() {
            return organizationId;
        }

        public void setOrganizationId(String organizationId) {
            this.organizationId = organizationId;
        }

        @Column(name = "TEMPLATE")
        public Long getTemplateId() {
            return templateId;
        }

        public void setTemplateId(Long templateId) {
            this.templateId = templateId;
        }
    }

    private ID id;

    @EmbeddedId
    @Override
    public ID getId() {
        return id;
    }

    @Override
    public void setId(ID id) {
        this.id = id;
    }
}
