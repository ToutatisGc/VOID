package cn.toutatis.xvoid.common.standard

/**
 * @author Toutatis_Gc
 * 标准命名池
 * SpringBoot 自定义Component/自定义Bean标准命名
 */
class StandardComponentPool {

   companion object {

       const val VOID_AUTO_INSERT_OBJS_COMPONENT = "VOID_AUTO_INSERT_OBJS_COMPONENT"

       /**
        * 异步线程池
        */
       const val VOID_ASYNC_THREAD_POOL = "VOID_ASYNC_THREAD_POOL"

       /**
        * VOID SQLite Context 上下文数据库
        */
       const val VOID_CONTEXT_SQLITE_DB_BEAN = "VOID_SQLITE_CONTEXT"
   }

}