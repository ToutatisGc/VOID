package cn.toutatis.ip.commands.commandLib

import cn.toutatis.ip.commands.commandLib.dns.ali.AliCloudDNS
import cn.toutatis.ip.commands.commandLib.support.BaseCommand
import cn.toutatis.ip.commands.commandLib.support.CommandHelper

object DNSLib:BaseCommand,CommandHelper() {

    private val aliCloudDNS = AliCloudDNS()

    override fun execute(target: String?, args: Any?) {
        val analysisArgs = this.analysisArgs(args)
        when {
            analysisArgs.getBooleanValue("last") -> {
                aliCloudDNS.getLastRecords()
            }
            else -> {
                aliCloudDNS.getDescribeDomainRecordsRequest(analysisArgs)
            }
        }
    }

    override fun execute(): Any? {
        TODO("Not yet implemented")
    }

    fun childMenuUpdateDNS(target: String?, args: Any?): Unit {
        aliCloudDNS.modifyRecord(target,this.analysisArgs(args))
    }

}