/**
 *
 */
package net.lc4ever.framework.dao.hibernate;

import org.hibernate.dialect.MySQL5InnoDBDialect;

@Deprecated
public class Mysql5InnoDBDialect extends MySQL5InnoDBDialect {

	public Mysql5InnoDBDialect() {
		registerKeyword("describe");
		registerKeyword("type");
	}

}
