package com.maohi.software.maohifx.invoice.bean;
// Generated 10 d�c. 2015 13:34:05 by Hibernate Tools 4.0.0

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * InvoiceLine generated by hbm2java
 */
@Entity
@Table(name = "invoice_line")
@JsonIgnoreProperties({ "invoice" })
public class InvoiceLine implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String uuid;
	private Invoice invoice;
	private Date creationDate;
	private Date updateDate;
	private Integer position;
	private String barCode;
	private String label;
	private Double quantity;
	private Double sellingPrice;
	private Double discountRate;
	private Double tvaRate;
	private Tva tva;

	public InvoiceLine() {
	}

	public InvoiceLine(final String uuid, final Invoice invoice) {
		this.uuid = uuid;
		this.invoice = invoice;
	}

	public InvoiceLine(final String uuid, final Invoice invoice, final Date creationDate, final Date updateDate, final Integer position, final String barCode, final String label, final Double quantity, final Double sellingPrice, final Double discountRate, final Double tvaRate) {
		this.uuid = uuid;
		this.invoice = invoice;
		this.creationDate = creationDate;
		this.updateDate = updateDate;
		this.position = position;
		this.barCode = barCode;
		this.label = label;
		this.quantity = quantity;
		this.sellingPrice = sellingPrice;
		this.discountRate = discountRate;
		this.tvaRate = tvaRate;
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
		final InvoiceLine other = (InvoiceLine) obj;
		if (this.barCode == null) {
			if (other.barCode != null) {
				return false;
			}
		} else if (!this.barCode.equals(other.barCode)) {
			return false;
		}
		if (this.creationDate == null) {
			if (other.creationDate != null) {
				return false;
			}
		} else if (!this.creationDate.equals(other.creationDate)) {
			return false;
		}
		if (this.discountRate == null) {
			if (other.discountRate != null) {
				return false;
			}
		} else if (!this.discountRate.equals(other.discountRate)) {
			return false;
		}
		if (this.label == null) {
			if (other.label != null) {
				return false;
			}
		} else if (!this.label.equals(other.label)) {
			return false;
		}
		if (this.position == null) {
			if (other.position != null) {
				return false;
			}
		} else if (!this.position.equals(other.position)) {
			return false;
		}
		if (this.quantity == null) {
			if (other.quantity != null) {
				return false;
			}
		} else if (!this.quantity.equals(other.quantity)) {
			return false;
		}
		if (this.sellingPrice == null) {
			if (other.sellingPrice != null) {
				return false;
			}
		} else if (!this.sellingPrice.equals(other.sellingPrice)) {
			return false;
		}
		if (this.tvaRate == null) {
			if (other.tvaRate != null) {
				return false;
			}
		} else if (!this.tvaRate.equals(other.tvaRate)) {
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

	@Column(name = "bar_code")
	public String getBarCode() {
		return this.barCode;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creation_date", length = 19)
	public Date getCreationDate() {
		return this.creationDate;
	}

	@Transient
	public Double getDiscountAmount() {
		return this.sellingPrice * (this.discountRate / 100);
	}

	@Column(name = "discount_rate", precision = 22, scale = 0)
	public Double getDiscountRate() {
		return this.discountRate;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "invoice_uuid", nullable = false)
	public Invoice getInvoice() {
		return this.invoice;
	}

	@Column(name = "label")
	public String getLabel() {
		return this.label;
	}

	@Column(name = "position")
	public Integer getPosition() {
		return this.position;
	}

	@Column(name = "quantity", precision = 22, scale = 0)
	public Double getQuantity() {
		return this.quantity;
	}

	@Column(name = "selling_price", precision = 22, scale = 0)
	public Double getSellingPrice() {
		return this.sellingPrice;
	}

	@Transient
	public Double getTotalAmount() {
		Double iTotalAmount = 0.0;
		iTotalAmount = this.getTotalWithNoTaxAmount();
		iTotalAmount += this.getTvaAmount();
		iTotalAmount -= this.getDiscountAmount();
		return iTotalAmount;
	}

	@Transient
	public Double getTotalWithNoTaxAmount() {
		return this.sellingPrice * this.quantity;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "tva_type", nullable = false)
	public Tva getTva() {
		return this.tva;
	}

	@Transient
	public Double getTvaAmount() {
		return this.sellingPrice * (this.tvaRate / 100);
	}

	@Column(name = "tva_rate", precision = 22, scale = 0)
	public Double getTvaRate() {
		return this.tvaRate;
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
		result = (prime * result) + ((this.barCode == null) ? 0 : this.barCode.hashCode());
		result = (prime * result) + ((this.creationDate == null) ? 0 : this.creationDate.hashCode());
		result = (prime * result) + ((this.discountRate == null) ? 0 : this.discountRate.hashCode());
		result = (prime * result) + ((this.label == null) ? 0 : this.label.hashCode());
		result = (prime * result) + ((this.position == null) ? 0 : this.position.hashCode());
		result = (prime * result) + ((this.quantity == null) ? 0 : this.quantity.hashCode());
		result = (prime * result) + ((this.sellingPrice == null) ? 0 : this.sellingPrice.hashCode());
		result = (prime * result) + ((this.tvaRate == null) ? 0 : this.tvaRate.hashCode());
		result = (prime * result) + ((this.updateDate == null) ? 0 : this.updateDate.hashCode());
		result = (prime * result) + ((this.uuid == null) ? 0 : this.uuid.hashCode());
		return result;
	}

	public void setBarCode(final String barCode) {
		this.barCode = barCode;
	}

	public void setCreationDate(final Date creationDate) {
		this.creationDate = creationDate;
	}

	public void setDiscountRate(final Double discountRate) {
		this.discountRate = discountRate;
	}

	public void setInvoice(final Invoice invoice) {
		this.invoice = invoice;
	}

	public void setLabel(final String label) {
		this.label = label;
	}

	public void setPosition(final Integer position) {
		this.position = position;
	}

	public void setQuantity(final Double quantity) {
		this.quantity = quantity;
	}

	public void setSellingPrice(final Double sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	public void setTva(final Tva tva) {
		this.tva = tva;
	}

	public void setTvaRate(final Double tvaRate) {
		this.tvaRate = tvaRate;
	}

	public void setUpdateDate(final Date updateDate) {
		this.updateDate = updateDate;
	}

	public void setUuid(final String uuid) {
		this.uuid = uuid;
	}

}
