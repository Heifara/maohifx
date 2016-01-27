/**
 *
 */
package com.maohi.software.maohifx.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;

import com.maohi.software.maohifx.common.server.HibernateUtil;
import com.maohi.software.maohifx.product.bean.ProductPackagingLot;
import com.maohi.software.maohifx.product.dao.ProductPackagingLotDAO;

/**
 * @author heifara
 *
 */
public class ProductPackagingMovementManager implements Runnable {

	private static ProductPackagingMovementManager singleton;

	public static void entry(final String aProductUuid, final String aPackagingCode, final double aQuantities) {
		getInstance().runLater(new ProductPackagingMovementEntryEvent(getInstance(), aProductUuid, aPackagingCode, aQuantities));
	}

	public static ProductPackagingMovementManager getInstance() {
		if (singleton == null) {
			singleton = new ProductPackagingMovementManager();
		}
		return singleton;
	}

	public static boolean isRunning() {
		if (getInstance().running) {
			return true;
		} else if (!getInstance().runnables.isEmpty()) {
			return true;
		}
		return false;
	}

	public static void out(final String aProductUuid, final String aPackagingCode, final double aQuantities) {
		getInstance().runLater(new ProductPackagingMovementOutEvent(getInstance(), aProductUuid, aPackagingCode, aQuantities));
	}

	private final Map<String, ProductPackagingLot> currentProductPackagingLot;
	private final List<Runnable> runnables;
	private final Session session;

	private boolean running;

	private ProductPackagingMovementManager() {
		this.currentProductPackagingLot = new HashMap<>();
		this.runnables = new ArrayList<>();
		this.session = HibernateUtil.getSessionFactory().openSession();
	}

	public void beginTransaction() {
		this.session.beginTransaction();
	}

	public void commit() {
		this.session.getTransaction().commit();
	}

	public ProductPackagingLot getCurrentProductPackagingLot(final String aProductUuid, final String aPackagingCode) {
		ProductPackagingLot iCurrentLot = this.currentProductPackagingLot.get(aProductUuid + aPackagingCode);
		if (iCurrentLot == null) {
			iCurrentLot = new ProductPackagingLotDAO(this.session).getCurrentLot(aProductUuid, aPackagingCode);
			this.currentProductPackagingLot.put(aProductUuid + aPackagingCode, iCurrentLot);
		}
		return iCurrentLot;
	}

	@Override
	synchronized public void run() {
		this.running = true;
		final List<Runnable> iRunningRunnables = new ArrayList<>();
		iRunningRunnables.addAll(this.runnables);
		for (final Runnable iRunnable : iRunningRunnables) {
			iRunnable.run();
		}
		this.runnables.removeAll(iRunningRunnables);
		this.running = false;
	}

	public void runLater(final Runnable aRunnable) {
		this.runnables.add(aRunnable);
		final Thread iThread = new Thread(this);
		iThread.start();
	}

	public void update(final Object aElement) {
		this.session.update(aElement);
	}

}
