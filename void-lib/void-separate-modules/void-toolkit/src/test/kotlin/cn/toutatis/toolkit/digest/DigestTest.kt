package cn.toutatis.toolkit.digest

import cn.toutatis.xvoid.toolkit.digest.DigestToolkit
import org.apache.commons.lang3.RandomStringUtils
import org.junit.Test
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec




class DigestTest {

    val secretKey = "XVOID-AES-wjEyrV8ktDjHkezZdkcXpd"

    @Test
    fun `test generate aes key`(){
        val randomAlphanumeric = RandomStringUtils.randomAlphanumeric(22)
        System.err.println(randomAlphanumeric)
    }

    @Test
    fun `test AES encoding`(){
        val dataToEncrypt = "hello world"
        val aesWithBase64 = DigestToolkit.AESWithBase64(secretKey, dataToEncrypt.toByteArray())
        System.err.println(aesWithBase64)
    }
}