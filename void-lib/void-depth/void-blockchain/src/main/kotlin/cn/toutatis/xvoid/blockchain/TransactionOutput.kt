package cn.toutatis.xvoid.blockchain

import cn.toutatis.xvoid.toolkit.digest.encodeToString
import cn.toutatis.xvoid.toolkit.digest.SHA256
import cn.toutatis.xvoid.toolkit.digest.signSHA256withRSA
import cn.toutatis.xvoid.toolkit.digest.verifySignature
import java.security.PrivateKey
import java.security.PublicKey

data class TransactionOutput(val recipient: PublicKey,
                             val amount: Int,
                             val transactionHash: String,
                             var hash: String = "") {
    init {
        hash = "${recipient.encodeToString()}$amount$transactionHash".SHA256()!!
    }

    fun isMine(me: PublicKey) : Boolean {
        return recipient == me
    }

    override fun toString(): String {
        return "TransactionOutput(amount=$amount, transactionHash='$transactionHash', hash='$hash')"
    }


}

data class Transaction(val sender: PublicKey,
                       val recipient: PublicKey,
                       val amount: Int,
                       var hash: String = "",
                       val inputs: MutableList<TransactionOutput> = mutableListOf(),
                       val outputs: MutableList<TransactionOutput> = mutableListOf()) {

    private var signature: ByteArray = ByteArray(0)

    init {
        hash = "${sender.encodeToString()}${recipient.encodeToString()}$amount$salt".SHA256()!!
    }

    companion object {
        fun create(sender: PublicKey, recipient: PublicKey, amount: Int) : Transaction {
            return Transaction(sender, recipient, amount)
        }

        var salt: Long = 0
            get() {
                field += 1
                return field
            }
    }

    fun sign(privateKey: PrivateKey) : Transaction {
        signature = "${sender.encodeToString()}${recipient.encodeToString()}$amount".signSHA256withRSA(privateKey)
        return this
    }

    fun isSignatureValid() : Boolean {
        return "${sender.encodeToString()}${recipient.encodeToString()}$amount".verifySignature(sender, signature)
    }

    override fun toString(): String {
        return "Transaction(amount=$amount, hash='$hash', inputs=$inputs, outputs=$outputs)"
    }


}
