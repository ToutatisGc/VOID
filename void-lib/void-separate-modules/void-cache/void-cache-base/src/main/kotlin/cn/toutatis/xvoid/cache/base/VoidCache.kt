package cn.toutatis.xvoid.cache.base

class VoidCache {

    companion object{

        private lateinit var cacheManager: VoidCacheManager

        @JvmStatic
        fun init(cacheManager: VoidCacheManager){
            this.cacheManager = cacheManager
        }
    }


}