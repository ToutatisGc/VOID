package cn.toutatis.xvoid.blockchain

import cn.toutatis.xvoid.toolkit.digest.SHA256
import java.time.Instant

/**
 * Block 区块
 *
 * 区块是区块链中的基本单位，类似于一个数据容器。它包含了一些重要的元素：
 * 区块头（Block Header）：区块的元数据，包括区块的唯一标识符（哈希值）、时间戳、难度目标等信息。
 * 交易数据（Transaction Data）：区块中存储的交易记录，可以是货币转账、智能合约执行或其他数据操作。
 * 前一区块哈希（Previous Block Hash）：每个区块都包含了前一个区块的哈希值，形成了链式结构。
 *
 * 当新的交易发生时，这些交易会被打包成一个新的区块，并通过共识算法被验证和添加到区块链的末尾。
 * 每个区块的哈希值取决于区块头和交易数据，如果其中任何一个发生改变，该区块的哈希值也会发生变化。
 * 由于区块链的去中心化性质，每个参与者都可以获取和复制整个区块链的副本，并参与区块的验证和添加过程。
 * 这种分布式的共识机制保证了数据的安全性和可靠性。
 * 通过链式链接起来的区块构成了完整的区块链，形成了一个不可篡改的交易历史记录。
 * 每个区块的存在都依赖于前一个区块的哈希值，确保了数据的连续性和一致性。
 * 这种结构使得区块链成为一个可信任的分布式数据库，被广泛应用于加密货币、供应链管理、数字资产交易等领域。
 * @property previousHash 前一个区块的hash
 * @property data 区块数据
 * @property timestamp 时间戳
 * @property nonce
 * @property hash 当前区块hash
 * @constructor Create empty Block
 */
data class Block(val previousHash: String,
                 val data: String = "",
                 val transactions: MutableList<Transaction> = mutableListOf(),
                 val timestamp: Long = Instant.now().toEpochMilli(),
                 val nonce: ULong = 0U,
                 var hash: String = "") {

    /**
     * 初始化计算前一个区块的hash
     */
    init {
        hash = calculateHash()
    }

    /**
     * Calculate hash
     * 计算区块hash
     * @return
     */
    fun calculateHash(): String = "$previousHash$data$timestamp$nonce".SHA256()!!

    fun addTransaction(transaction: Transaction) : Block {

        if (transaction.isSignatureValid())
            transactions.add(transaction)
        return this
    }

}