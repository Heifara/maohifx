package com.maohi.software.maohifx.contact.bean;
// Generated 17 d�c. 2015 12:07:24 by Hibernate Tools 4.0.0

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

import com.maohi.software.maohifx.common.AnnotatedClass;

/**
 * Contact generated by hbm2java
 */
@Entity
@Table(name = "contact")
public class Contact implements java.io.Serializable, AnnotatedClass {

	private static final long serialVersionUID = 1L;

	private String uuid;
	private Date creationDate;
	private Date updateDate;
	private String lastname;
	private String middlename;
	private String firstname;
	private Set<Phone> phones = new HashSet<Phone>(0);
	private Set<Email> emails = new HashSet<Email>(0);
	private String href;

	public Contact() {
	}

	public Contact(final String uuid) {
		this.uuid = uuid;
	}

	public Contact(final String uuid, final Date creationDate, final Date updateDate, final String lastname, final String middlename, final String firstname, final Set<Phone> phones, final Set<Email> emails) {
		this.uuid = uuid;
		this.creationDate = creationDate;
		this.updateDate = updateDate;
		this.lastname = lastname;
		this.middlename = middlename;
		this.firstname = firstname;
		this.phones = phones;
		this.emails = emails;
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
		final Contact other = (Contact) obj;
		if (this.creationDate == null) {
			if (other.creationDate != null) {
				return false;
			}
		} else if (!this.creationDate.equals(other.creationDate)) {
			return false;
		}
		if (this.emails == null) {
			if (other.emails != null) {
				return false;
			}
		} else if (!this.emails.equals(other.emails)) {
			return false;
		}
		if (this.firstname == null) {
			if (other.firstname != null) {
				return false;
			}
		} else if (!this.firstname.equals(other.firstname)) {
			return false;
		}
		if (this.href == null) {
			if (other.href != null) {
				return false;
			}
		} else if (!this.href.equals(other.href)) {
			return false;
		}
		if (this.lastname == null) {
			if (other.lastname != null) {
				return false;
			}
		} else if (!this.lastname.equals(other.lastname)) {
			return false;
		}
		if (this.middlename == null) {
			if (other.middlename != null) {
				return false;
			}
		} else if (!this.middlename.equals(other.middlename)) {
			return false;
		}
		if (this.phones == null) {
			if (other.phones != null) {
				return false;
			}
		} else if (!this.phones.equals(other.phones)) {
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "contact")
	public Set<Email> getEmails() {
		return this.emails;
	}

	@Column(name = "firstname")
	public String getFirstname() {
		return this.firstname;
	}

	@Transient
	public String getHref() {
		return this.href;
	}

	@Column(name = "lastname")
	public String getLastname() {
		return this.lastname;
	}

	@Column(name = "middlename")
	public String getMiddlename() {
		return this.middlename;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "contact")
	public Set<Phone> getPhones() {
		return this.phones;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((this.creationDate == null) ? 0 : this.creationDate.hashCode());
		result = (prime * result) + ((this.emails == null) ? 0 : this.emails.hashCode());
		result = (prime * result) + ((this.firstname == null) ? 0 : this.firstname.hashCode());
		result = (prime * result) + ((this.href == null) ? 0 : this.href.hashCode());
		result = (prime * result) + ((this.lastname == null) ? 0 : this.lastname.hashCode());
		result = (prime * result) + ((this.middlename == null) ? 0 : this.middlename.hashCode());
		result = (prime * result) + ((this.phones == null) ? 0 : this.phones.hashCode());
		result = (prime * result) + ((this.updateDate == null) ? 0 : this.updateDate.hashCode());
		result = (prime * result) + ((this.uuid == null) ? 0 : this.uuid.hashCode());
		return result;
	}

	@Override
	public void setCreationDate(final Date creationDate) {
		this.creationDate = creationDate;
	}

	public void setEmails(final Set<Email> emails) {
		this.emails = emails;
	}

	public void setFirstname(final String firstname) {
		this.firstname = firstname;
	}

	@Override
	public void setHref(final String aHref) {
		this.href = aHref;
	}

	public void setLastname(final String lastname) {
		this.lastname = lastname;
	}

	public void setMiddlename(final String middlename) {
		this.middlename = middlename;
	}

	public void setPhones(final Set<Phone> phones) {
		this.phones = phones;
	}

	@Override
	public void setUpdateDate(final Date updateDate) {
		this.updateDate = updateDate;
	}

	@Override
	public void setUuid(final String uuid) {
		this.uuid = uuid;
	}

}
