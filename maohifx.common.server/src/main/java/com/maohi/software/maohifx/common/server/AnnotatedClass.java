/**
 *
 */
package com.maohi.software.maohifx.common.server;

import java.util.Date;

/**
 * @author heifara
 *
 */
public interface AnnotatedClass {

	String getUuid();

	void setCreationDate(Date aDate);

	void setHref(String aHref);

	void setUpdateDate(Date aDate);

	void setUuid(String aUuid);

}
