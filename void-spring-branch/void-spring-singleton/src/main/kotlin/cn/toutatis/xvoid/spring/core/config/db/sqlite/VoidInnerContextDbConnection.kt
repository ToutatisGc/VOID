package cn.toutatis.xvoid.spring.core.config.db.sqlite

import cn.toutatis.xvoid.common.standard.StandardComponentPool
import cn.toutatis.xvoid.project.spring.VoidSpringContext
import cn.toutatis.xvoid.sqlite.SQLiteShell
import cn.toutatis.xvoid.sqlite.common.VoidSQLiteShellBuilder
import cn.toutatis.xvoid.toolkit.log.LoggerToolkit
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class VoidInnerContextDbConnection {

    private val logger = LoggerToolkit.getLogger(this.javaClass)

    @Bean(StandardComponentPool.VOID_CONTEXT_SQLITE_DB_SHELL_BEAN)
    fun sqliteShell(): SQLiteShell {
        return VoidSpringContext.systemSqliteShell
    }

}