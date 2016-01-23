/**
 *
 */
package com.maohi.software.maohifx.product.dao;

import org.hibernate.Query;

import com.maohi.software.maohifx.common.server.AbstractDAO;
import com.maohi.software.maohifx.product.bean.Product;
import com.maohi.software.maohifx.product.bean.ProductPackagingMovement;

/**
 * @author heifara
 *
 */
public class ProductPackagingMovementDAO extends AbstractDAO<ProductPackagingMovement> {

	@Override
	public Class<ProductPackagingMovement> getAnnotatedClass() {
		return ProductPackagingMovement.class;
	}

	public Double getQuantities(final Product aProduct, final String aPackagingCode, final Integer aLot) {
		final StringBuilder iStatement = new StringBuilder();
		iStatement.append("SELECT SUM(quantity) FROM ");
		iStatement.append(this.getAnnotatedClass().getSimpleName());
		iStatement.append(" WHERE id.productUuid = '" + aProduct.getUuid() + "' ");
		if (aPackagingCode != null) {
			iStatement.append("AND id.packagingCode = :packagingCode ");
		}
		if (aLot != null) {
			iStatement.append("AND id.lot = :lot ");
		}

		final Query iQuery = session.createQuery(iStatement.toString());
		if (aPackagingCode != null) {
			iQuery.setString("packagingCode", "'" + aPackagingCode + "'");
		}
		if (aLot != null) {
			iQuery.setInteger("lot", aLot);
		}
		final Double iResult = (Double) iQuery.uniqueResult();
		if (iResult == null) {
			return 0.0;
		}

		return iResult;
	}

}
