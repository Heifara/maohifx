/**
 *
 */
package com.maohi.software.maohifx.product.dao;

import com.maohi.software.maohifx.common.server.AbstractDAO;
import com.maohi.software.maohifx.product.bean.Barcode;

/**
 * @author heifara
 *
 */
public class BarcodeDAO extends AbstractDAO<Barcode> {

	@Override
	public Class<Barcode> getAnnotatedClass() {
		return Barcode.class;
	}

}
