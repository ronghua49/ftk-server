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

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((organizationId == null) ? 0 : organizationId.hashCode());
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
			if (organizationId == null) {
				if (other.organizationId != null) {
					return false;
				}
			} else if (!organizationId.equals(other.organizationId)) {
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
