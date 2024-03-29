package cn.toutatis.xvoid.spring.core.config;

import cn.toutatis.xvoid.common.standard.StandardComponentPool;
import cn.toutatis.xvoid.common.standard.StandardFields;
import cn.toutatis.xvoid.project.spring.VoidSpringContextVariables;
import cn.toutatis.xvoid.project.spring.entities.SqliteVoidContext;
import cn.toutatis.xvoid.sql.dql.DQLBuilder;
import cn.toutatis.xvoid.sqlite.SQLiteShell;
import cn.toutatis.xvoid.toolkit.clazz.ReflectToolkit;
import cn.toutatis.xvoid.toolkit.log.LoggerToolkit;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 环境属性注入配置类
 * @author Toutatis_Gc
 */
@Configuration
public class VoidContextConfiguration {

    private final Logger logger = LoggerToolkit.getLogger(this.getClass());

    private final SQLiteShell sqLiteShell;

    private final JdbcTemplate template;

    public VoidContextConfiguration(
            @Qualifier(StandardComponentPool.VOID_CONTEXT_SQLITE_DB_SHELL_BEAN) SQLiteShell sqLiteShell, JdbcTemplate template) throws Exception {
        logger.info("通过[SQLite-VOID]数据库,加载环境数据");
        this.sqLiteShell = sqLiteShell;
        this.template = template;
    }

    @Bean(StandardComponentPool.VOID_CONTEXT_VARIABLES)
    public VoidSpringContextVariables exposeContextVariables() throws Exception {
        DQLBuilder<SqliteVoidContext> dictDQLBuilder = new DQLBuilder<>(SqliteVoidContext.class,true);
        dictDQLBuilder.eq(SqliteVoidContext::getMchId, StandardFields.VOID_BUSINESS_DEFAULT_CREATOR);
        try {
            List<Map<String, Object>> listMap = sqLiteShell.selectListMap(dictDQLBuilder);
            VoidSpringContextVariables voidSpringContextVariables = ReflectToolkit.convertMapToEntity(
                    listMap.stream().collect(Collectors.toMap(
                            map -> (String) map.get(SqliteVoidContext.KEY_FIELD),
                            map -> map.get(SqliteVoidContext.VALUE_FIELD),
                            (existingValue, newValue) -> {
                                return existingValue;
                            }
                    )), VoidSpringContextVariables.class
            );
            return voidSpringContextVariables;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
