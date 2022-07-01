
import cn.toutatis.xvoid.cache.core.VoidCommonCacheDefinition
import cn.toutatis.xvoid.cache.core.ehcache.VoidEhCacheManager
import cn.toutatis.xvoid.toolkit.file.FileToolkit
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.zip
import org.ehcache.Cache
import org.ehcache.PersistentCacheManager
import org.ehcache.config.builders.CacheConfigurationBuilder
import org.ehcache.config.builders.CacheManagerBuilder
import org.ehcache.config.builders.ResourcePoolsBuilder
import org.ehcache.config.units.EntryUnit
import org.ehcache.config.units.MemoryUnit
import org.junit.jupiter.api.Test
import java.io.File


/**
 * @author Toutatis_Gc
 * @date 2022/6/26 12:15
 *
 */
class CacheTest {

    private val fileToolkit = FileToolkit.INSTANCE

    @Test
    fun cacheTest() : Unit {
        val voidEhCacheManager = VoidEhCacheManager();
        voidEhCacheManager.init(fileToolkit.getRuntimePath(javaClass),"VOID-TEST-PERSISTENCE",null)
//        voidEhCacheManager.putValue(VoidCommonCacheDefinition.VOID_SECURITY_CODE_CACHE,"test","abc")
//        voidEhCacheManager.putValue(VoidCommonCacheDefinition.VOID_SECURITY_CODE_CACHE,"tes2t","abc")
//        voidEhCacheManager.putValue(VoidCommonCacheDefinition.VOID_SECURITY_CODE_CACHE,"te5st","abc")
//        voidEhCacheManager.putValue(VoidCommonCacheDefinition.VOID_SECURITY_CODE_CACHE,"te35st","abc")
////        val value = voidEhCacheManager.getValue<String>(VoidCommonCacheDefinition.VOID_SECURITY_CODE_CACHE, "test")
//        val value = voidEhCacheManager.getValue<String>(VoidCommonCacheDefinition.VOID_SECURITY_CODE_CACHE, "te35st")
//        System.err.println(value)


        val cache = voidEhCacheManager.getCache(VoidCommonCacheDefinition.VOID_SECURITY_CODE_CACHE)
//        cache.put("test", "abc")
        System.err.println( cache.get("test"))
        voidEhCacheManager.close()
    }

    @Test
    fun defaultCacheManager(): Unit {
        val persistentCacheManager: PersistentCacheManager = CacheManagerBuilder.newCacheManagerBuilder()
            .with(CacheManagerBuilder.persistence(File(fileToolkit.getRuntimePath(javaClass), "myData")))
            .withCache("threeTieredCache",
                CacheConfigurationBuilder.newCacheConfigurationBuilder(String::class.java, String::class.java,
                    ResourcePoolsBuilder.newResourcePoolsBuilder()
                        .heap(10, EntryUnit.ENTRIES)
                        .offheap(1, MemoryUnit.MB)
                        .disk(20, MemoryUnit.MB, true)
                )
            ).build(true)

        val threeTieredCache: Cache<String, String> = persistentCacheManager.getCache("threeTieredCache",
            String::class.java,
            String::class.java)
        threeTieredCache.put("1L", "stillAvailableAfterRestart")

        persistentCacheManager.close()
    }

    
}