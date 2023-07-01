package cn.toutatis.xvoid.blockchain
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.PublicKey


/**
 * Wallet 加密钱包
 * @property publicKey 公钥
 * @property privateKey 私钥
 * @property blockChain 区块链
 * @constructor Create empty Wallet
 */
data class Wallet(val publicKey: PublicKey, val privateKey: PrivateKey, val blockChain: BlockChain) {

    companion object {
        /**
         * 创建一个钱包
         * @param blockChain
         * @return
         */
        fun create(blockChain: BlockChain): Wallet {
            val generator = KeyPairGenerator.getInstance("RSA")
            generator.initialize(2048)
            val keyPair = generator.generateKeyPair()

            return Wallet(keyPair.public, keyPair.private, blockChain)
        }
    }

    val balance: Int get() {
        return getMyTransactions().sumOf { it.amount }
    }

    private fun getMyTransactions() : Collection<TransactionOutput> {
        return blockChain.UTXO.filterValues { it.isMine(publicKey) }.values
    }

    /**
     * Send funds to
     *
     * @param recipient
     * @param amountToSend
     * @return
     */
    fun sendFundsTo(recipient: PublicKey, amountToSend: Int) : Transaction {

        if (amountToSend > balance) {
            throw IllegalArgumentException("Insufficient funds")
        }

        val tx = Transaction.create(sender = publicKey, recipient = publicKey, amount = amountToSend)
        tx.outputs.add(TransactionOutput(recipient = recipient, amount = amountToSend, transactionHash = tx.hash))

        var collectedAmount = 0
        for (myTx in getMyTransactions()) {
            collectedAmount += myTx.amount
            tx.inputs.add(myTx)

            if (collectedAmount > amountToSend) {
                val change = collectedAmount - amountToSend
                tx.outputs.add(TransactionOutput(recipient = publicKey, amount = change, transactionHash = tx.hash))
            }

            if (collectedAmount >= amountToSend) {
                break
            }
        }
        return tx.sign(privateKey)
    }
}