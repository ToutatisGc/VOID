import cn.hutool.json.JSONArray
import cn.toutatis.xvoid.blockchain.*
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.alibaba.fastjson.serializer.SerializerFeature
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

    @Test
    fun `Test transaction`(): Unit {
        val blockChain = BlockChain("0000")
        //初始化两个钱包信息
        val traderA = Wallet.create(blockChain)
        val traderB = Wallet.create(blockChain)
        System.err.println("Trader A balance:${traderA.balance}")
        System.err.println("Trader B balance:${traderB.balance}")

        val tx1 = Transaction.create(sender = traderA.publicKey, recipient = traderA.publicKey, amount = 100)
        tx1.outputs.add(TransactionOutput(recipient = traderA.publicKey, amount = 100, transactionHash = tx1.hash))
        tx1.sign(traderA.privateKey)

        var genesisBlock = Block(previousHash = "0")
        genesisBlock.addTransaction(tx1)
        genesisBlock = blockChain.pushBlock(genesisBlock)

        System.err.println("Trader A balance:${traderA.balance}")
        System.err.println("Trader B balance:${traderB.balance}")

//        val tx2 = traderA.sendFundsTo(recipient = traderB.publicKey, amountToSend = 50)
//        val secondBlock = blockChain.pushBlock(Block(genesisBlock.hash).addTransaction(tx2))
//
//        System.err.println("Trader A balance:${traderA.balance}")
//        System.err.println("Trader B balance:${traderB.balance}")

        System.err.println(blockChain.getBlocks())

    }

}