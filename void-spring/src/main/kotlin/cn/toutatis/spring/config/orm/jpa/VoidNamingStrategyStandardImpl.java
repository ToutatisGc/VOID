/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package cn.toutatis.spring.config.orm.jpa;

import java.io.Serializable;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Standard implementation of the PhysicalNamingStrategy contract.
 *
 * @author Steve Ebersole
 */
@Component
public class VoidNamingStrategyStandardImpl implements PhysicalNamingStrategy, Serializable {

	@Value("${void.global-orm-config.table-prefix}")
	private String tablePrefix;

	@Value("${void.global-orm-config.use-table-prefix}")
	private Boolean useTablePrefix;

	/**
	 * Singleton access
	 */
	public static final VoidNamingStrategyStandardImpl INSTANCE = new VoidNamingStrategyStandardImpl();

	@Override
	public Identifier toPhysicalCatalogName(Identifier name, JdbcEnvironment context) {
		return name;
	}

	@Override
	public Identifier toPhysicalSchemaName(Identifier name, JdbcEnvironment context) {
		return name;
	}

	@Override
	public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
		if (useTablePrefix){
			return new Identifier(tablePrefix+name,false);
		}else{
			return name;
		}
	}

	@Override
	public Identifier toPhysicalSequenceName(Identifier name, JdbcEnvironment context) {
		return name;
	}

	@Override
	public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment context) {
		return name;
	}
}
