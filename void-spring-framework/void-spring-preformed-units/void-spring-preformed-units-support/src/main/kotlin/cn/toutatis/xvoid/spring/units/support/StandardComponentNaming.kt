package cn.toutatis.xvoid.spring.units.support

/**
 * @author Toutatis_Gc
 * 标准命名池
 * SpringBoot 自定义Component/自定义Bean标准命名
 */
class StandardComponentNaming {

   companion object {

       /*日志支持*/

       /**
        * 默认日志发送器
        */
       const val VOID_SIMPLE_LOGGER_SENDER = "VOID_SIMPLE_LOGGER_SENDER"

       /**
        * 默认日志接收器
        */
       const val VOID_SIMPLE_LOGGER_RECEIVER = "VOID_SIMPLE_LOGGER_RECEIVER"

       /*Spring Boot 异步支持*/

       /**
        * 异步线程池
        */
       const val VOID_ASYNC_THREAD_POOL = "VOID_ASYNC_THREAD_POOL"

       /**
        * VOID SQLite Context 上下文数据库
        */
       const val VOID_CONTEXT_SQLITE_DB_SHELL_BEAN = "VOID_SQLITE_CONTEXT_SHELL"

       const val VOID_CONTEXT_VARIABLES = "VOID_CONTEXT_VARIABLES"

       const val VOID_JPA_CREATOR_AUDIT_AWARE = "VOID_JPA_CREATOR_AUDIT_AWARE"

       const val VOID_AUTO_INSERT_OBJS_COMPONENT = "VOID_AUTO_INSERT_OBJS_COMPONENT"
   }

}