package com.maohi.software.maohifx.product.bean;
// Generated 22 janv. 2016 11:29:41 by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * ProductPackagingMovementId generated by hbm2java
 */
@Embeddable
public class ProductPackagingMovementId implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String productUuid;
	private String packagingCode;
	private int lot;
	private int id;

	public ProductPackagingMovementId() {
	}

	public ProductPackagingMovementId(final String productUuid, final String packagingCode, final int lot, final int id) {
		this.productUuid = productUuid;
		this.packagingCode = packagingCode;
		this.lot = lot;
		this.id = id;
	}

	@Override
	public boolean equals(final Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof ProductPackagingMovementId)) {
			return false;
		}
		final ProductPackagingMovementId castOther = (ProductPackagingMovementId) other;

		return ((this.getProductUuid() == castOther.getProductUuid()) || ((this.getProductUuid() != null) && (castOther.getProductUuid() != null) && this.getProductUuid().equals(castOther.getProductUuid()))) && ((this.getPackagingCode() == castOther.getPackagingCode()) || ((this.getPackagingCode() != null) && (castOther.getPackagingCode() != null) && this.getPackagingCode().equals(castOther.getPackagingCode()))) && (this.getLot() == castOther.getLot()) && (this.getId() == castOther.getId());
	}

	@Column(name = "id", nullable = false)
	public int getId() {
		return this.id;
	}

	@Column(name = "lot", nullable = false)
	public int getLot() {
		return this.lot;
	}

	@Column(name = "packaging_code", nullable = false, length = 45)
	public String getPackagingCode() {
		return this.packagingCode;
	}

	@Column(name = "product_uuid", nullable = false)
	public String getProductUuid() {
		return this.productUuid;
	}

	@Override
	public int hashCode() {
		int result = 17;

		result = (37 * result) + (this.getProductUuid() == null ? 0 : this.getProductUuid().hashCode());
		result = (37 * result) + (this.getPackagingCode() == null ? 0 : this.getPackagingCode().hashCode());
		result = (37 * result) + this.getLot();
		result = (37 * result) + this.getId();
		return result;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public void setLot(final int lot) {
		this.lot = lot;
	}

	public void setPackagingCode(final String packagingCode) {
		this.packagingCode = packagingCode;
	}

	public void setProductUuid(final String productUuid) {
		this.productUuid = productUuid;
	}

}
