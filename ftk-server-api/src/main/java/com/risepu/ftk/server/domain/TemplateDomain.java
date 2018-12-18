package com.risepu.ftk.server.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import net.lc4ever.framework.domain.BaseEntity;

/**
 * 模板数据关联
 *
 * @author q-wang
 */
@Entity
@Table(name = "FTK_TEMPLATE_DOMAIN")
public class TemplateDomain implements BaseEntity<TemplateDomain.ID> {

    private static final long serialVersionUID = 1L;

    @Embeddable
    public static class ID implements Serializable {

        private static final long serialVersionUID = 1L;

        /**
         * 模板id
         */
        private Long templateId;

        /**
         * 模板数据id
         */
        private Long domainId;

        @Column(name = "TEMPLATE")
        public Long getTemplateId() {
            return templateId;
        }

        public void setTemplateId(Long templateId) {
            this.templateId = templateId;
        }

        @Column(name = "DOMAIN")
        public Long getDomainId() {
            return domainId;
        }

        public void setDomainId(Long domainId) {
            this.domainId = domainId;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((domainId == null) ? 0 : domainId.hashCode());
            result = prime * result + ((templateId == null) ? 0 : templateId.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            ID other = (ID) obj;
            if (domainId == null) {
                if (other.domainId != null) {
                    return false;
                }
            } else if (!domainId.equals(other.domainId)) {
                return false;
            }
            if (templateId == null) {
                if (other.templateId != null) {
                    return false;
                }
            } else if (!templateId.equals(other.templateId)) {
                return false;
            }
            return true;
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
