package cn.toutatis.xvoid.spring.amqp

import cn.toutatis.xvoid.common.standard.StandardFields

/**
 * @author Toutatis_Gc
 * 消息中间件标准和默认命名
 */
object XvoidSystemAmqpNamingDescription {
    /**
     * TopicExchange
     * XVOID默认交换机
     */
    const val XVOID_SYSTEM_TOPIC_EXCHANGE = "XVOID-SYSTEM-TOPIC-EXCHANGE"

    /**
     * Queue
     * 日志默认队列名称
     * 本来想将日志处理分情况处理来着
     */
    const val XVOID_SYSTEM_LOG_QUEUE = "XVOID-SYSTEM-LOG-QUEUE"

    /**
     * 日志默认键
     */
    const val XVOID_SIMPLE_LOG_ROUTE_KEY = StandardFields.SYSTEM_PREFIX + ".LOG.*"
}