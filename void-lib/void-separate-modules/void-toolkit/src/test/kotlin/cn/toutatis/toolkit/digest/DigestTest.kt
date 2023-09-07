package cn.toutatis.toolkit.digest

import cn.toutatis.xvoid.toolkit.digest.DigestToolkit
import org.apache.commons.lang3.RandomStringUtils
import org.junit.Test


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
        val aesWithBase64 = DigestToolkit.aesUseECBWithBase64(secretKey, dataToEncrypt.toByteArray())
        System.err.println(aesWithBase64)
        val randomAlphanumeric = RandomStringUtils.randomAlphanumeric(16)
        val aesUseCBCWithBase64 = DigestToolkit.aesUseCBCWithBase64(secretKey, randomAlphanumeric, dataToEncrypt.toByteArray())

        val decryptAESUseCBCWithBase64 =
            DigestToolkit.decryptAESUseCBCWithBase64(secretKey, randomAlphanumeric, aesUseCBCWithBase64)

        System.err.println(decryptAESUseCBCWithBase64)
        val decryptAESUseCBCWithBase641 =
            DigestToolkit.decryptAESUseCBCWithBase64(secretKey, "random1111111111", aesUseCBCWithBase64)
        System.err.println(decryptAESUseCBCWithBase641)
    }
}