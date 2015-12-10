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

/**
 * InvoiceLine generated by hbm2java
 */
@Entity
@Table(name = "invoice_line")
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

	@Column(name = "bar_code")
	public String getBarCode() {
		return this.barCode;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creation_date", length = 19)
	public Date getCreationDate() {
		return this.creationDate;
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
