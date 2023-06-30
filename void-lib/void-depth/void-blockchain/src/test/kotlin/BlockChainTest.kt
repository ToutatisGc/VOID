import cn.hutool.json.JSONArray
import cn.toutatis.xvoid.blockchain.Block
import cn.toutatis.xvoid.blockchain.BlockChain
import com.alibaba.fastjson.JSON
import org.apache.commons.lang3.RandomStringUtils
import org.junit.Test

class BlockChainTest {

    @Test
    fun `Proactive test`(): Unit {
        System.err.println("0".repeat(5))
    }
    
    @Test
    fun `Test create block`(){
        val genesisBlock = Block(previousHash = "0", data = "I'm the first")
        val secondBlock = Block(genesisBlock.hash, "I'm the second")
        val thirdBlock = Block(secondBlock.hash, "I'm the third")


        println(genesisBlock)
        println(secondBlock)
        println(thirdBlock)

        val blockChain = BlockChain()
        blockChain.pushBlock(genesisBlock)
        blockChain.pushBlock(secondBlock)
        blockChain.pushBlock(thirdBlock)
    }

    @Test
    fun `Test create block chain`(){
        val blockChain = BlockChain()
        val genesisBlock = Block(previousHash = "0", data = "I'm the first")
        val genesisPushBlock = blockChain.pushBlock(genesisBlock)
        val secondBlock = Block(genesisPushBlock.hash, "I'm the second")
        blockChain.pushBlock(secondBlock)
        System.err.println(blockChain.isValid())
        System.err.println(JSON.toJSONString(blockChain.getBlocks()))
    }

}