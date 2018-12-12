/**
 *
 */
package com.risepu.ftk.server.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import net.lc4ever.framework.domain.TimestampObject;

/**
 * 
 * 文档数据
 * @author q-wang
 */
@Entity
@Table(name = "FTK_DOCUMENT_DATA")
public class DocumentData extends TimestampObject<DocumentData.ID> {

	@Embeddable
	public static class ID implements Serializable {
		
		/** 模板数据id */
		private Long domainId;
		
		/**文档id*/
		private Long documentId;
		
		@Column(name = "DOMAIN")
		public Long getDomainId() {
			return domainId;
		}

		public void setDomainId(Long domainId) {
			this.domainId = domainId;
		}

		@Column(name = "DOCUMENT")
		public Long getDocumentId() {
			return documentId;
		}

		public void setDocumentId(Long documentId) {
			this.documentId = documentId;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((documentId == null) ? 0 : documentId.hashCode());
			result = prime * result + ((domainId == null) ? 0 : domainId.hashCode());
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
			if (documentId == null) {
				if (other.documentId != null) {
					return false;
				}
			} else if (!documentId.equals(other.documentId)) {
				return false;
			}
			if (domainId == null) {
				if (other.domainId != null) {
					return false;
				}
			} else if (!domainId.equals(other.domainId)) {
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
	
	private String value;

}
