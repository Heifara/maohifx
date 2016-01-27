/**
 *
 */
package com.maohi.software.maohifx.product;

import com.maohi.software.maohifx.product.bean.ProductPackagingLot;
import com.maohi.software.maohifx.product.bean.ProductPackagingMovement;

/**
 * @author heifara
 *
 */
public class ProductPackagingMovementOutEvent extends ProductPackagingMovementEntryEvent {

	public ProductPackagingMovementOutEvent(final ProductPackagingMovementManager aManager, final String aProductUuid, final String aPackagingCode, final double aQuantities) {
		super(aManager, aProductUuid, aPackagingCode, aQuantities);
	}

	@Override
	public void run() {
		System.out.println("ProductUuid: " + this.productUuid + " PackagagingCode: " + this.packagingCode + " Quantities: " + this.quantities);

		double iQuantities = this.quantities;

		while (iQuantities > 0) {
			this.manager.beginTransaction();

			final ProductPackagingLot iCurrentLot = this.manager.getCurrentProductPackagingLot(this.productUuid, this.packagingCode);
			if (iCurrentLot == null) {
				throw new NullPointerException("Current Lot can never be null");
			}
			final ProductPackagingMovement iMovement = iCurrentLot.add(-iQuantities);
			iQuantities += iMovement.getQuantity();

			this.manager.update(iCurrentLot);
			this.manager.commit();
		}
	}

}
