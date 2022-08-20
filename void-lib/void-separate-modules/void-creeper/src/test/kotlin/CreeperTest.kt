import cn.toutatis.creeper.DocumentResolver
import org.junit.jupiter.api.Test

/**
 * @author Toutatis_Gc
 * @date 2022/8/20 12:48
 *
 */

class CreeperTest {

    @Test
    fun test() {
        DocumentResolver.resolve("https://movie.douban.com/review/best/?start=0")
    }

}