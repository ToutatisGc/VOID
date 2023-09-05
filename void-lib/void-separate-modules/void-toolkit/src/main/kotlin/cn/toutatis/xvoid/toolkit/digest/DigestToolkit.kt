package cn.toutatis.xvoid.toolkit.digest

import cn.toutatis.xvoid.toolkit.locale.I18n
import cn.toutatis.xvoid.toolkit.validator.Validator
import org.apache.commons.codec.binary.Hex
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.lang3.RandomStringUtils
import java.security.*
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec


/**
 * Digest toolkit
 * 加密工具类,并拓展方法
 * @constructor Create empty Digest toolkit
 * @author Toutatis_Gc
 */
object DigestToolkit {

    /**
     * SHA256WithRSA Algorithm
     * 一般常用公私钥加密算法
     */
    const val SHA256_WITH_RSA_ALGORITHM = "SHA256withRSA"

    private val SHA256withRSASignature =  Signature.getInstance(SHA256_WITH_RSA_ALGORITHM)

    /**
     * Sign SHA256withRSA
     * 对数据签名
     * @param data 需要签名数据
     * @param privateKey 私钥
     * @return 加密数据
     */
    @JvmStatic
    fun signSHA256withRSA(data:String,privateKey: PrivateKey) : ByteArray {
        SHA256withRSASignature.initSign(privateKey)
        SHA256withRSASignature.update(data.toByteArray())
        return SHA256withRSASignature.sign()
    }

    /**
     * 用于验证使用RSA算法和SHA-256哈希算法生成的数字签名
     *
     * @param data 要进行验证的原始数据,类型为字符串
     * @param publicKey 用于验证签名的公钥
     * @param signature 待验证的数字签名
     * @return 返回验证结果
     */
    @JvmStatic
    fun verifySHA256withRSA(data:String,publicKey: PublicKey, signature: ByteArray) : Boolean {
        SHA256withRSASignature.initVerify(publicKey)
        SHA256withRSASignature.update(data.toByteArray())
        return SHA256withRSASignature.verify(signature)
    }

    /**
     * AES with base64 AES加密后使用Base64编码
     * @param key aes secret
     * @param data 数据字节组
     */
    @JvmStatic
    fun AESWithBase64(key:String,data: ByteArray): String {
        val secretKeySpec = SecretKeySpec(key.toByteArray(), "AES")
        val cipher: Cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING")
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec)
        val encryptedData: ByteArray = cipher.doFinal(data)
        return encodeBase64ToString(encryptedData)
    }

    /**
     * Encode base64
     * 编码数据到Base64
     * @param data 数据
     * @return Base64数据
     */
    @JvmStatic
    fun encodeBase64ToString(data:ByteArray):String{
        val encoder = Base64.getEncoder()
        return encoder.encodeToString(data)
    }


    /**
     * 解码Base64数据
     * @param data base64数据
     * @return 解码数据
     */
    @JvmStatic
    fun decodeBase64ToString(data:ByteArray):ByteArray{
        val decoder = Base64.getDecoder()
        return decoder.decode(data)
    }

    /**
     * 解码Base64数据
     * @param data base64数据
     * @return 解码数据
     */
    @JvmStatic
    fun decodeBase64ToString(data:String):ByteArray{
        val decoder = Base64.getDecoder()
        return decoder.decode(data)
    }

    /**
     * 使用SHA256加密
     * @param str 加密的字符串
     * @param salt 盐
     * @param roll 重复加密次数
     * @return 加密后的字符串
     */
    @JvmStatic
    fun sha256(str: String, salt: String?, roll: Int): String? {
        if (Validator.strIsBlank(str)) return null
        if (roll < 0 || roll > 11) throw DigestException(I18n.translate("commons.digest.failArgs","加密次数错误"))
        var saltStr = str + salt
        var bytes = saltStr.toByteArray()
        for (i in 0 until roll + 1) {
            bytes = DigestUtils.sha256(bytes)
        }
        saltStr = Hex.encodeHexString(bytes)
        return saltStr
    }

    /**
     * 使用SHA256加密
     * @param str 加密的字符串
     * @param salt 盐
     * @return 加密后的字符串
     */
    @JvmStatic
    fun sha256(str: String, salt: String?): String? {
        return sha256(str, salt, 0)
    }

    /**
     * 使用SHA256加密
     * @param str 加密的字符串
     * @return
     */
    @JvmStatic
    fun sha256(str: String): String? {
        return sha256(str, null, 0)
    }

    /**
     * 生成盐值
     * 其长度在17~29之间，使用A~Z,a~z,0~9组成随机字符串
     * @return 盐
     */
    @JvmStatic
    fun generateSalt(): String {
        return RandomStringUtils.randomAlphanumeric(17, 29)
    }
}

fun String.SHA256():String?{
    return DigestToolkit.sha256(this)
}

fun String.signSHA256withRSA(privateKey: PrivateKey, algorithm: String = "SHA256withRSA") : ByteArray {
    return DigestToolkit.signSHA256withRSA(this, privateKey)
}

fun String.verifySignature(publicKey: PublicKey, signature: ByteArray) : Boolean {
    return DigestToolkit.verifySHA256withRSA(this, publicKey,signature)
}

fun Key.encodeToString() : String {
    return DigestToolkit.encodeBase64ToString(this.encoded)
}