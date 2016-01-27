/**
 *
 */
package com.maohi.software.maohifx.product;

import com.maohi.software.maohifx.product.bean.ProductPackagingLot;

/**
 * @author heifara
 *
 */
public class ProductPackagingMovementEntryEvent implements Runnable {

	protected final ProductPackagingMovementManager manager;
	protected final String productUuid;
	protected final String packagingCode;
	protected final double quantities;

	public ProductPackagingMovementEntryEvent(final ProductPackagingMovementManager aManager, final String aProductUuid, final String aPackagingCode, final double aQuantities) {
		this.manager = aManager;
		this.productUuid = aProductUuid;
		this.packagingCode = aPackagingCode;
		this.quantities = aQuantities;
	}

	@Override
	public void run() {
		this.manager.beginTransaction();

		final ProductPackagingLot iCurrentLot = this.manager.getCurrentProductPackagingLot(this.productUuid, this.packagingCode);
		if (iCurrentLot == null) {
			throw new NullPointerException("Current Lot can never be null");
		}
		iCurrentLot.add(this.quantities);

		this.manager.update(iCurrentLot);
		this.manager.commit();
	}

}
