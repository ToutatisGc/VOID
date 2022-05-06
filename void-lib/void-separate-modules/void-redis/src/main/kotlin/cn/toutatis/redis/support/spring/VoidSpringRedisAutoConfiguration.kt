package cn.toutatis.redis.support.spring

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

/**
 * @author Toutatis_Gc
 * @date 2022/5/6 19:25
 * Spring环境引入即支持
 */
@Configuration
class VoidSpringRedisAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    fun getRedisTemplate(factory: RedisConnectionFactory): RedisTemplate<String, Any> {
        val template = RedisTemplate<String, Any>()
        template.setConnectionFactory(factory)
        val objectMapper = ObjectMapper()
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY)
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL)
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        val jackson2JsonRedisSerializer = Jackson2JsonRedisSerializer(Any::class.java)
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper)
        val stringRedisSerializer = StringRedisSerializer()
        template.keySerializer = stringRedisSerializer
        template.hashKeySerializer = stringRedisSerializer
        template.valueSerializer = jackson2JsonRedisSerializer
        template.hashValueSerializer = jackson2JsonRedisSerializer
        template.afterPropertiesSet()
        return template
    }

}