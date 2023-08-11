/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package cn.toutatis.xvoid.orm.support.jpa;

import cn.toutatis.xvoid.spring.configure.system.VoidGlobalConfiguration;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 带表前缀的命名策略
 * @author Toutatis_Gc
 */
@Component
public class VoidNamingStrategyStandardImpl implements PhysicalNamingStrategy, Serializable {

	private final VoidGlobalConfiguration voidGlobalConfiguration;

	public VoidNamingStrategyStandardImpl(VoidGlobalConfiguration voidGlobalConfiguration) {
		this.voidGlobalConfiguration = voidGlobalConfiguration;
	}


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
		VoidGlobalConfiguration.GlobalOrmConfig globalOrmConfig = voidGlobalConfiguration.getGlobalOrmConfig();

		if (globalOrmConfig.getUseTablePrefix()){
			return new Identifier(globalOrmConfig.getTablePrefix()+name,false);
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
