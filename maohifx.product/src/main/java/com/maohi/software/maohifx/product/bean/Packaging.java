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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Packaging generated by hbm2java
 */
@Entity
@Table(name = "packaging")
@JsonIgnoreProperties("productPackagings")
public class Packaging implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String code;
	private Date creationDate;
	private Date updateDate;
	private Set<ProductPackaging> productPackagings = new HashSet<>(0);

	public Packaging() {
	}

	public Packaging(final String code) {
		this.code = code;
	}

	public Packaging(final String code, final Date creationDate, final Date updateDate, final Set<ProductPackaging> productPackagings) {
		this.code = code;
		this.creationDate = creationDate;
		this.updateDate = updateDate;
		this.productPackagings = productPackagings;
	}

	@Id

	@Column(name = "code", unique = true, nullable = false, length = 45)
	public String getCode() {
		return this.code;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creation_date", length = 19)
	public Date getCreationDate() {
		return this.creationDate;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "packaging")
	public Set<ProductPackaging> getProductPackagings() {
		return this.productPackagings;
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

	public void setProductPackagings(final Set<ProductPackaging> productPackagings) {
		this.productPackagings = productPackagings;
	}

	public void setUpdateDate(final Date updateDate) {
		this.updateDate = updateDate;
	}

}
