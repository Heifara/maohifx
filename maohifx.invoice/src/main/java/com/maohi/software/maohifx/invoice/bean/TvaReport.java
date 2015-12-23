/**
 *
 */
package com.maohi.software.maohifx.invoice.bean;

/**
 * @author heifara
 *
 */
public class TvaReport {

	public Double tvaRate;
	public Double amount;

	public TvaReport() {
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
		final TvaReport other = (TvaReport) obj;
		if (this.amount == null) {
			if (other.amount != null) {
				return false;
			}
		} else if (!this.amount.equals(other.amount)) {
			return false;
		}
		if (this.tvaRate == null) {
			if (other.tvaRate != null) {
				return false;
			}
		} else if (!this.tvaRate.equals(other.tvaRate)) {
			return false;
		}
		return true;
	}

	public Double getAmount() {
		return this.amount;
	}

	public Double getTvaRate() {
		return this.tvaRate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((this.amount == null) ? 0 : this.amount.hashCode());
		result = (prime * result) + ((this.tvaRate == null) ? 0 : this.tvaRate.hashCode());
		return result;
	}

	public void setAmount(final Double amount) {
		this.amount = amount;
	}

	public void setTvaRate(final Double tvaRate) {
		this.tvaRate = tvaRate;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("TvaReport [tvaRate=");
		builder.append(this.tvaRate);
		builder.append(", amount=");
		builder.append(this.amount);
		builder.append("]");
		return builder.toString();
	}

}
