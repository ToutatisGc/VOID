
import cn.toutatis.xvoid.cache.core.ehcache.VoidEhCacheManager
import cn.toutatis.xvoid.toolkit.file.FileToolkit
import org.junit.jupiter.api.Test

/**
 * @author Toutatis_Gc
 * @date 2022/6/26 12:15
 *
 */
class CacheTest {

    private val fileToolkit = FileToolkit.INSTANCE

    @Test
    fun cacheTest() : Unit {

        VoidEhCacheManager().init(fileToolkit.getRuntimePath(javaClass),"VOID-TEST-PERSISTENCE")
    }

}