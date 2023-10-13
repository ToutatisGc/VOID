package cn.toutatis.xvoid.spring.core.config;

import cn.toutatis.xvoid.common.standard.StandardComponentPool;
import cn.toutatis.xvoid.project.spring.VoidSpringContext;
import cn.toutatis.xvoid.project.spring.VoidSpringProperties;
import cn.toutatis.xvoid.spring.support.core.db.sqlite.entities.SqliteVoidDict;
import cn.toutatis.xvoid.sql.dql.DQLBuilder;
import cn.toutatis.xvoid.sqlite.SQLiteShell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

/**
 * 环境属性注入配置类
 * @author Toutatis_Gc
 */
@Configuration
public class VoidPropertiesConfiguration {

    private final SQLiteShell sqLiteShell;


    public VoidPropertiesConfiguration(
            @Qualifier(StandardComponentPool.VOID_CONTEXT_SQLITE_DB_BEAN) SQLiteShell sqLiteShell
    ) {
        this.sqLiteShell = sqLiteShell;
        DQLBuilder<SqliteVoidDict> dictDQLBuilder = new DQLBuilder<>(SqliteVoidDict.class);
        dictDQLBuilder.eq(SqliteVoidDict::getMchId,);
        VoidSpringProperties properties = VoidSpringContext.getProperties();

    }
}
