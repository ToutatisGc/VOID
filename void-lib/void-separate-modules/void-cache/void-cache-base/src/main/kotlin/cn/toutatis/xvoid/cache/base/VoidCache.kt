package cn.toutatis.xvoid.cache.base

class VoidCache {

    companion object{

        private var cacheManager: VoidCacheManager<*,*>? = null

        @JvmStatic
        fun init(cacheManager: VoidCacheManager<*,*>){
            if (this.cacheManager != null){
                throw IllegalStateException("VoidCacheManager已初始化"+ this.cacheManager!!.javaClass)
            }
            this.cacheManager = cacheManager
        }
    }


}