/**
 *
 */
package com.maohi.software.maohifx.invoice.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.maohi.software.maohifx.common.server.AbstractDAO;
import com.maohi.software.maohifx.invoice.bean.InvoiceLine;
import com.maohi.software.maohifx.invoice.bean.TvaReport;

/**
 * @author heifara
 *
 */
public class InvoiceLineDAO extends AbstractDAO<InvoiceLine> {

	@Override
	public Class<InvoiceLine> getAnnotatedClass() {
		return InvoiceLine.class;
	}

	public List<TvaReport> tvaReport(final String aStart, final String aEnd) {
		final StringBuilder iStatement = new StringBuilder();
		iStatement.append("SELECT tvaRate as tvaRate,  SUM(ROUND((sellingPrice * quantity) * (tvaRate/100))) as amount");
		iStatement.append(" FROM " + this.getAnnotatedClass().getSimpleName());
		iStatement.append(" WHERE invoice.date >= :start");
		iStatement.append(" AND invoice.date <= :end");
		iStatement.append(" GROUP BY tvaRate");

		final Query iQuery = session.createQuery(iStatement.toString());
		iQuery.setString("start", aStart);
		iQuery.setString("end", aEnd);
		iQuery.setResultTransformer(new AliasToBeanResultTransformer(TvaReport.class));
		final List<TvaReport> iResults = iQuery.list();
		return iResults;
	}

}
