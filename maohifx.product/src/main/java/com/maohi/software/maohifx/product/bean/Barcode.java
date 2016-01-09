package com.maohi.software.maohifx.product.bean;
// Generated 8 janv. 2016 12:27:52 by Hibernate Tools 4.0.0

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

/**
 * Barcode generated by hbm2java
 */
@Entity
@Table(name = "barcode")
public class Barcode implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String code;
	private Date creationDate;
	private Date updateDate;
	private Set<ProductPackagingBarcode> productPackagingBarcodes = new HashSet<>(0);

	public Barcode() {
	}

	public Barcode(final String code) {
		this.code = code;
	}

	public Barcode(final String code, final Date creationDate, final Date updateDate, final Set<ProductPackagingBarcode> productPackagingBarcodes) {
		this.code = code;
		this.creationDate = creationDate;
		this.updateDate = updateDate;
		this.productPackagingBarcodes = productPackagingBarcodes;
	}

	@Id

	@Column(name = "code", unique = true, nullable = false)
	public String getCode() {
		return this.code;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creation_date", length = 19)
	public Date getCreationDate() {
		return this.creationDate;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "barcode")
	public Set<ProductPackagingBarcode> getProductPackagingBarcodes() {
		return this.productPackagingBarcodes;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_date", length = 19)
	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setCode(final String code) {
		this.code = code;
	}

	public void setCreationDate(final Date creationDate) {
		this.creationDate = creationDate;
	}

	public void setProductPackagingBarcodes(final Set<ProductPackagingBarcode> productPackagingBarcodes) {
		this.productPackagingBarcodes = productPackagingBarcodes;
	}

	public void setUpdateDate(final Date updateDate) {
		this.updateDate = updateDate;
	}

}
