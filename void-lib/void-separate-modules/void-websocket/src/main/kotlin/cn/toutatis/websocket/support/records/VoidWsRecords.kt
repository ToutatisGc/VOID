package cn.toutatis.websocket.support.records

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component

/**
 * @author Toutatis_Gc
 * @date 2022/5/6 19:15
 *
 */
@Component
class VoidWsRecords {

    @Autowired
    private lateinit var redisTemplate: RedisTemplate<String,String>


}