package cn.toutatis.ip.commands.commandLib


import cn.toutatis.ip.commands.commandLib.support.BaseCommand
import cn.toutatis.ip.commands.commandLib.support.CommandHelper
import cn.toutatis.ip.scan.UrlPoolScanner
import cn.toutatis.xvoid.resolve.ip.IPResolver
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.Charset


object IpScanCmd : BaseCommand,CommandHelper() {

    var lastRecord : String? = null

    private val urlPoolScanner = UrlPoolScanner(IPResolver.urlPool)

    override fun execute(n0: String?, args: Any?) {
        val ipGroup = urlPoolScanner.getIp()
        if (ipGroup != null){
            val analysisArgs = this.analysisArgs(args)
            val choosePingIp = analysisArgs.getOrDefault("p", true) as Boolean
            when(ipGroup.size){
                1 ->{
                    val ip = ipGroup[0] as Map.Entry<*, *>
                    logger.info("当前公网IP为[${ip.key}]")
                    lastRecord = ip.key as String
                    if (choosePingIp){ pingIP(lastRecord!!) }
                }
                else ->{
                    logger.info("当前命中目标${ipGroup.size}个,按优先级排列.")
                    for (ips in ipGroup) {
                        ips as Map.Entry<*, *>
                        logger.info("${ips.key} HITS:${ips.value}")
                    }
                    val defaultChooseFirstAddress = IPResolver.config.getProperty("Default-Choose-First-Address")
                    if(defaultChooseFirstAddress.toBoolean()){
                        lastRecord = ipGroup[0] as String
                        if (choosePingIp){ pingIP(lastRecord!!) }
                    }
                }
            }
        }else{
            logger.error("当前未发现公网IP,请检查网络或配置文件.")
        }

    }

    override fun execute(): Any? {
        TODO("Not yet implemented")
    }

    private fun pingIP(address:String): Unit {
        logger.info("等待 ping 公网IP:${address}")
        val process = Runtime.getRuntime().exec("ping $address")
        val inputStream: InputStream = process.inputStream
        val br = BufferedReader(InputStreamReader(inputStream,Charset.forName("GBK")))
        var line = br.readLine()
        while (line != null) {
            println(line)
            line = br.readLine()
        }
    }

}

object IpRefreshCmd : BaseCommand {

    override fun execute(target: String?, args: Any?) {
        TODO("Not yet implemented")
    }

    override fun execute(): Any? {
        TODO("Not yet implemented")
    }

}