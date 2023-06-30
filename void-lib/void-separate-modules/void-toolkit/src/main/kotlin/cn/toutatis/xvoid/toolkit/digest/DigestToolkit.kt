package cn.toutatis.xvoid.toolkit.digest

import cn.toutatis.xvoid.toolkit.locale.I18n
import cn.toutatis.xvoid.toolkit.validator.Validator
import org.apache.commons.codec.binary.Hex
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.lang3.RandomStringUtils
import java.security.*
import java.util.*


/**
 * Digest toolkit
 * 加密工具类,并拓展方法
 * @constructor Create empty Digest toolkit
 * @author Toutatis_Gc
 */
object DigestToolkit {

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

fun String.sha256():String?{
    return DigestToolkit.sha256(this)
}

fun String.sign(privateKey: PrivateKey, algorithm: String = "SHA256withRSA") : ByteArray {
    val rsa = Signature.getInstance(algorithm)
    rsa.initSign(privateKey)
    rsa.update(this.toByteArray())
    return rsa.sign()
}

fun String.verifySignature(publicKey: PublicKey, signature: ByteArray, algorithm: String = "SHA256withRSA") : Boolean {
    val rsa = Signature.getInstance(algorithm)
    rsa.initVerify(publicKey)
    rsa.update(this.toByteArray())
    return rsa.verify(signature)
}

fun Key.encodeToString() : String {
    return Base64.getEncoder().encodeToString(this.encoded)
}