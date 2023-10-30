package cn.toutatis.xvoid.spring.core.config;

import cn.toutatis.xvoid.common.standard.StandardComponentPool;
import cn.toutatis.xvoid.common.standard.StandardFields;
import cn.toutatis.xvoid.spring.support.core.db.sqlite.entities.SqliteVoidContext;
import cn.toutatis.xvoid.sql.dql.DQLBuilder;
import cn.toutatis.xvoid.sqlite.SQLiteShell;
import cn.toutatis.xvoid.toolkit.log.LoggerToolkit;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * 环境属性注入配置类
 * @author Toutatis_Gc
 */
@Configuration
public class VoidPropertiesConfiguration {

    private final Logger logger = LoggerToolkit.getLogger(this.getClass());

    public VoidPropertiesConfiguration(
            @Qualifier(StandardComponentPool.VOID_CONTEXT_SQLITE_DB_BEAN) SQLiteShell sqLiteShell
    ) throws Exception {
        logger.info("通过[SQLite-VOID]数据库,加载环境数据");
        DQLBuilder<SqliteVoidContext> dictDQLBuilder = new DQLBuilder<>(SqliteVoidContext.class,true);
        dictDQLBuilder.eq(SqliteVoidContext::getMchId, StandardFields.VOID_BUSINESS_DEFAULT_CREATOR);
        dictDQLBuilder.eq(SqliteVoidContext::getKey, SqliteVoidContext.AES_SECRET_KEY);
        Map<String, Object> aesMap = sqLiteShell.selectOneMap(dictDQLBuilder);
        System.err.println(aesMap);
//        SqliteVoidDict convert = dictDQLBuilder.convert(aesMap);
//        System.err.println(convert);
//        VoidSpringProperties properties = VoidSpringContext.getProperties();
//        properties.aesSecret = "";
    }
}
