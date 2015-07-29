package com.maohi.software.maohifx.invoice.bean;
// Generated 24 juil. 2015 10:26:57 by Hibernate Tools 4.0.0

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Invoice generated by hbm2java
 */
@XmlRootElement
@Entity
@Table(name = "invoice")
public class Invoice implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String uuid;

	private Date creationDate;

	private Date updateDate;
	private Integer number;
	private Date date;
	private Set<InvoiceLine> invoiceLines = new HashSet<InvoiceLine>(0);
	private String customerName;

	public Invoice() {
	}

	public Invoice(final String uuid) {
		this.uuid = uuid;
	}

	public Invoice(final String uuid, final Date creationDate, final Date updateDate, final Integer number, final Date date, final Set<InvoiceLine> invoiceLines) {
		this.uuid = uuid;
		this.creationDate = creationDate;
		this.updateDate = updateDate;
		this.number = number;
		this.date = date;
		this.invoiceLines = invoiceLines;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final Invoice other = (Invoice) obj;
		if (this.creationDate == null) {
			if (other.creationDate != null) {
				return false;
			}
		} else if (!this.creationDate.equals(other.creationDate)) {
			return false;
		}
		if (this.date == null) {
			if (other.date != null) {
				return false;
			}
		} else if (!this.date.equals(other.date)) {
			return false;
		}
		if (this.invoiceLines == null) {
			if (other.invoiceLines != null) {
				return false;
			}
		} else if (!this.invoiceLines.equals(other.invoiceLines)) {
			return false;
		}
		if (this.number == null) {
			if (other.number != null) {
				return false;
			}
		} else if (!this.number.equals(other.number)) {
			return false;
		}
		if (this.updateDate == null) {
			if (other.updateDate != null) {
				return false;
			}
		} else if (!this.updateDate.equals(other.updateDate)) {
			return false;
		}
		if (this.uuid == null) {
			if (other.uuid != null) {
				return false;
			}
		} else if (!this.uuid.equals(other.uuid)) {
			return false;
		}
		return true;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creation_date", length = 19)
	public Date getCreationDate() {
		return this.creationDate;
	}

	@Transient
	public String getCustomerName() {
		return this.customerName;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date", length = 19)
	public Date getDate() {
		return this.date;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "invoice")
	public Set<InvoiceLine> getInvoiceLines() {
		return this.invoiceLines;
	}

	@Column(name = "number")
	public Integer getNumber() {
		return this.number;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_date", length = 19)
	public Date getUpdateDate() {
		return this.updateDate;
	}

	@Id

	@Column(name = "uuid", unique = true, nullable = false)
	public String getUuid() {
		return this.uuid;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((this.creationDate == null) ? 0 : this.creationDate.hashCode());
		result = (prime * result) + ((this.date == null) ? 0 : this.date.hashCode());
		result = (prime * result) + ((this.invoiceLines == null) ? 0 : this.invoiceLines.hashCode());
		result = (prime * result) + ((this.number == null) ? 0 : this.number.hashCode());
		result = (prime * result) + ((this.updateDate == null) ? 0 : this.updateDate.hashCode());
		result = (prime * result) + ((this.uuid == null) ? 0 : this.uuid.hashCode());
		return result;
	}

	public void setCreationDate(final Date creationDate) {
		this.creationDate = creationDate;
	}

	public void setCustomerName(final String customerName) {
		this.customerName = customerName;
	}

	public void setDate(final Date date) {
		this.date = date;
	}

	public void setInvoiceLines(final Set<InvoiceLine> invoiceLines) {
		this.invoiceLines = invoiceLines;
	}

	public void setNumber(final Integer number) {
		this.number = number;
	}

	public void setUpdateDate(final Date updateDate) {
		this.updateDate = updateDate;
	}

	public void setUuid(final String uuid) {
		this.uuid = uuid;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Invoice [");
		if (this.uuid != null) {
			builder.append("uuid=");
			builder.append(this.uuid);
			builder.append(", ");
		}
		if (this.creationDate != null) {
			builder.append("creationDate=");
			builder.append(this.creationDate);
			builder.append(", ");
		}
		if (this.updateDate != null) {
			builder.append("updateDate=");
			builder.append(this.updateDate);
			builder.append(", ");
		}
		if (this.number != null) {
			builder.append("number=");
			builder.append(this.number);
			builder.append(", ");
		}
		if (this.date != null) {
			builder.append("date=");
			builder.append(this.date);
			builder.append(", ");
		}
		if (this.invoiceLines != null) {
			builder.append("invoiceLines=");
			builder.append(this.invoiceLines);
		}
		builder.append("]");
		return builder.toString();
	}

}
