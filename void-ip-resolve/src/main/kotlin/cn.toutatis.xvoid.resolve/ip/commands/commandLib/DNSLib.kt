package cn.toutatis.xvoid.resolve.ip.commands.commandLib

//import cn.toutatis.xvoid.resolve.ip.commands.commandLib.dns.ali.AliCloudDNS
//import cn.toutatis.xvoid.resolve.ip.commands.commandLib.support.CommandHelper
//
//object DNSLib: CommandHelper() {
//
//    private val aliCloudDNS = AliCloudDNS()
//
//    fun execute(target: String?, args: Any?) {
//        val analysisArgs = analysisArgs(args)
//        when {
//            analysisArgs.getBooleanValue("last") -> {
//                aliCloudDNS.getLastRecords()
//            }
//            else -> {
//                aliCloudDNS.getDescribeDomainRecordsRequest(analysisArgs)
//            }
//        }
//    }
//
//    fun execute(): Any? {
//        TODO("Not yet implemented")
//    }
//
//    fun childMenuUpdateDNS(target: String?, args: Any?): Unit {
//        aliCloudDNS.modifyRecord(target,analysisArgs(args))
//    }
//
//}