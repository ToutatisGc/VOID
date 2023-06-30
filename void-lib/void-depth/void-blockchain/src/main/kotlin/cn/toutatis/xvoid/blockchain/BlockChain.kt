package cn.toutatis.xvoid.blockchain

import cn.toutatis.xvoid.toolkit.log.LoggerToolkit

/**
 * Block chain
 * 区块链是由多个前后连接的区块组成
 * @constructor Create empty Block chain
 */
class BlockChain {

    /**
     * Mined Difficulty 挖掘区块难度
     */
    private val minedDifficulty = 2

    /**
     * Mined Valid prefix 挖掘验证前缀
     */
    private val minedValidPrefix:String

    /**
     * Mined times 挖掘次数
     */
    private var minedTimes:Long = 0L

    /**
     * 一个区块链中保存着多个区块
     */
    private var blocks: MutableList<Block> = mutableListOf()

    var UTXO: MutableMap<String, TransactionOutput> = mutableMapOf()

    constructor(){ minedValidPrefix = "0".repeat(minedDifficulty) }

    constructor(customPrefix:String){ minedValidPrefix = customPrefix }

    /**
     * 推送区块到区块链中
     * 1.检测是否为已挖掘区块
     * 2.如果不是,就挖掘正确区块
     * 3.挖掘成功,添加到链
     * @param block 区块
     * @return
     */
    fun pushBlock(block: Block) : Block {
        val minedBlock = if (this.checkMined(block)){ block } else { mine(block) }
        blocks.add(minedBlock)
        return minedBlock
    }

    fun getBlocks():MutableList<Block>{
        return this.blocks
    }

    /**
     * Mine 挖掘区块
     * 如何判断是正确的区块:
     * 当前链上所挖掘的区块的hash和chain的验证前缀一致
     * 说明hash是区块链所要求的
     * 否则一直向下挖掘,直到找到所需要的hash前缀
     * @param block 需要挖掘的区块
     * @return 挖掘出的正确区块
     */
    private fun mine(block: Block) : Block {
        var minedBlock = block.copy()
        while (!this.checkMined(minedBlock)) {
            minedBlock = minedBlock.copy(nonce = minedBlock.nonce + 1)
            updateUTXO(minedBlock)
            minedTimes++
            println("NONCE:${minedBlock.nonce},HASH:${minedBlock.hash},TIMES:${minedTimes}")
        }
        return minedBlock
    }

    /**
     * Is mined 验证区块是否已发掘
     * @param block 区块信息
     * @return 区块是否已发掘
     */
    private fun checkMined(block: Block) : Boolean {
        return block.hash.startsWith(minedValidPrefix)
    }

    /**
     * Is valid 验证区块链是否可信
     * @return 区块链是否可信
     */
    fun isValid() : Boolean {
        when {
            blocks.isEmpty() -> return true
            blocks.size == 1 -> return blocks[0].hash == blocks[0].calculateHash()
            else -> {
                for (i in 1 until blocks.size) {
                    val previousBlock = blocks[i - 1]
                    val currentBlock = blocks[i]
                    when {
                        currentBlock.hash != currentBlock.calculateHash() -> return false
                        currentBlock.previousHash != previousBlock.calculateHash() -> return false
                        !(checkMined(previousBlock) && checkMined(currentBlock)) -> return false
                    }
                }
                return true
            }
        }
    }

    private fun updateUTXO(block: Block) {
        block.transactions.flatMap { it.inputs }.map { it.hash }.forEach { UTXO.remove(it) }
        UTXO.putAll(block.transactions.flatMap { it.outputs }.associateBy { it.hash })
    }

}