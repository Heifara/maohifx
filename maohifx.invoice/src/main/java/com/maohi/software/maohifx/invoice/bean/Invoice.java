package com.maohi.software.maohifx.invoice.bean;
// Generated 10 d�c. 2015 13:34:05 by Hibernate Tools 4.0.0

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.maohi.software.maohifx.common.server.AnnotatedClass;
import com.maohi.software.maohifx.contact.bean.Customer;

/**
 * Invoice generated by hbm2java
 */
@Entity
@Table(name = "invoice")
public class Invoice implements java.io.Serializable, AnnotatedClass {

	private static final long serialVersionUID = 1L;

	private String uuid;
	private Customer customer;
	private Date creationDate;
	private Date updateDate;
	private Integer number;
	private Date date;
	private String customerName;
	private Date validDate;
	private String href;
	private Set<InvoiceLine> invoiceLines = new HashSet<>(0);
	private Set<InvoicePaymentLine> invoicePaymentLines = new HashSet<>(0);

	public Invoice() {
	}

	public Invoice(final String uuid) {
		this.uuid = uuid;
	}

	public Invoice(final String uuid, final Date creationDate, final Date updateDate, final Integer number, final Date date, final String customerName, final Set<InvoiceLine> invoiceLines, final Set<InvoicePaymentLine> invoicePaymentLines) {
		this.uuid = uuid;
		this.creationDate = creationDate;
		this.updateDate = updateDate;
		this.number = number;
		this.date = date;
		this.customerName = customerName;
		this.invoiceLines = invoiceLines;
		this.invoicePaymentLines = invoicePaymentLines;
	}

	public void bindChildren() {
		for (final InvoiceLine iInvoiceLine : this.invoiceLines) {
			if (iInvoiceLine.getUuid() == null) {
				iInvoiceLine.setUuid(UUID.randomUUID().toString());
			}
			iInvoiceLine.setInvoice(this);
		}

		for (final InvoicePaymentLine invoicePaymentLine : this.invoicePaymentLines) {
			if (invoicePaymentLine.getUuid() == null) {
				invoicePaymentLine.setUuid(UUID.randomUUID().toString());
			}

			invoicePaymentLine.setInvoice(this);
		}
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
		if (this.customerName == null) {
			if (other.customerName != null) {
				return false;
			}
		} else if (!this.customerName.equals(other.customerName)) {
			return false;
		}
		if (this.date == null) {
			if (other.date != null) {
				return false;
			}
		} else if (!this.date.equals(other.date)) {
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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "customer_uuid")
	public Customer getCustomer() {
		return this.customer;
	}

	@Column(name = "customer_name")
	public String getCustomerName() {
		return this.customerName;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date", length = 19)
	public Date getDate() {
		return this.date;
	}

	@Transient
	public Double getDiscountAmount() {
		Double iDiscountAmount = 0.0;
		for (final InvoiceLine iInvoiceLine : this.invoiceLines) {
			iDiscountAmount += iInvoiceLine.getDiscountAmount();
		}
		return iDiscountAmount;
	}

	@Transient
	public String getHref() {
		return this.href;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "invoice", cascade = CascadeType.ALL)
	public Set<InvoiceLine> getInvoiceLines() {
		return this.invoiceLines;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "invoice", cascade = CascadeType.ALL)
	public Set<InvoicePaymentLine> getInvoicePaymentLines() {
		return this.invoicePaymentLines;
	}

	@Column(name = "number")
	public Integer getNumber() {
		return this.number;
	}

	@Transient
	public Double getTotalAmount() {
		Double iTotalAmount = 0.0;
		iTotalAmount += this.getTotalWithNoTaxAmount();
		iTotalAmount += this.getTvaAmount();
		iTotalAmount -= this.getDiscountAmount();
		return iTotalAmount;
	}

	@Transient
	public Double getTotalWithNoTaxAmount() {
		Double iTotalWithNoTaxAmount = 0.0;
		for (final InvoiceLine invoiceLine : this.invoiceLines) {
			iTotalWithNoTaxAmount += invoiceLine.getTotalWithNoTaxAmount();
		}
		return iTotalWithNoTaxAmount;
	}

	@Transient
	public Double getTvaAmount() {
		Double iTvaAmount = 0.0;
		for (final InvoiceLine iInvoiceLine : this.invoiceLines) {
			iTvaAmount += iInvoiceLine.getTvaAmount();
		}
		return iTvaAmount;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_date", length = 19)
	public Date getUpdateDate() {
		return this.updateDate;
	}

	@Override
	@Id

	@Column(name = "uuid", unique = true, nullable = false)
	public String getUuid() {
		return this.uuid;
	}

	@Column(name = "valid_date")
	public Date getValidDate() {
		return this.validDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((this.creationDate == null) ? 0 : this.creationDate.hashCode());
		result = (prime * result) + ((this.customerName == null) ? 0 : this.customerName.hashCode());
		result = (prime * result) + ((this.date == null) ? 0 : this.date.hashCode());
		result = (prime * result) + ((this.number == null) ? 0 : this.number.hashCode());
		result = (prime * result) + ((this.updateDate == null) ? 0 : this.updateDate.hashCode());
		result = (prime * result) + ((this.uuid == null) ? 0 : this.uuid.hashCode());
		return result;
	}

	@Override
	public void setCreationDate(final Date creationDate) {
		this.creationDate = creationDate;
	}

	public void setCustomer(final Customer customer) {
		this.customer = customer;
	}

	public void setCustomerName(final String customerName) {
		this.customerName = customerName;
	}

	public void setDate(final Date date) {
		this.date = date;
	}

	@Override
	public void setHref(final String href) {
		this.href = href;
	}

	public void setInvoiceLines(final Set<InvoiceLine> invoiceLines) {
		this.invoiceLines = invoiceLines;
	}

	public void setInvoicePaymentLines(final Set<InvoicePaymentLine> invoicePaymentLines) {
		this.invoicePaymentLines = invoicePaymentLines;
	}

	public void setNumber(final Integer number) {
		this.number = number;
	}

	@Override
	public void setUpdateDate(final Date updateDate) {
		this.updateDate = updateDate;
	}

	@Override
	public void setUuid(final String uuid) {
		this.uuid = uuid;
	}

	public void setValidDate(final Date validDate) {
		this.validDate = validDate;
	}

}
