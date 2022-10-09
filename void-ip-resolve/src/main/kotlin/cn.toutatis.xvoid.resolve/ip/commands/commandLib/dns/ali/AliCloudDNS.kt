package cn.toutatis.ip.commands.commandLib.dns.ali


import cn.toutatis.ip.commands.commandLib.IpScanCmd
import cn.toutatis.xvoid.resolve.ip.IPResolver.Companion.commandInterpreter
import cn.toutatis.xvoid.resolve.ip.IPResolver.Companion.config
import com.alibaba.fastjson.JSONObject
import com.aliyun.alidns20150109.Client
import com.aliyun.alidns20150109.models.DescribeDomainRecordInfoRequest
import com.aliyun.alidns20150109.models.DescribeDomainRecordsRequest
import com.aliyun.alidns20150109.models.UpdateDomainRecordRequest
import com.aliyun.teaopenapi.models.Config
import org.slf4j.LoggerFactory


class AliCloudDNS {

    companion object{

        private lateinit var client: Client

        private val lastRequestRecords = LinkedHashMap<Int, AliCloudDnsObj>()

        private val logger = LoggerFactory.getLogger(AliCloudDNS::class.java)

        private var alreadyCheck = ""

    }

    init {
        val config: Config = Config()
            .setAccessKeyId(config.getProperty("Ali-Access-Key-Id"))
            .setAccessKeySecret(config.getProperty("ALi-Access-Key-Secret"))
        config.endpoint = "alidns.cn-hangzhou.aliyuncs.com"
        client = Client(config)
    }

    fun getDescribeDomainRecordsRequest(params:JSONObject): Unit {
        val domainName = params.getString("d")
        if (domainName != null){
            val describeDomainRecordsRequest = DescribeDomainRecordsRequest()
            describeDomainRecordsRequest.domainName = domainName
            val describeDomainRecords = client.describeDomainRecords(describeDomainRecordsRequest)
            val domainRecords = describeDomainRecords.body.getDomainRecords()
            val size = domainRecords.record.size
            /*{"RR":"minecraft","rR":"minecraft","line":"default","weight":1,"type":"A","TTL":600,"tTL":600,
            "recordId":"735497802049472512","domainName":"xvoid.cn","locked":false,"value":"110.177.180.111","status":"ENABLE"}*/
            for (index in 0 until size){
                val record = domainRecords.record[index]
                val aliCloudDnsObj = AliCloudDnsObj(record.recordId,record.RR,record.domainName,record.value,record.type,record.remark)
                lastRequestRecords[index+1] = aliCloudDnsObj
            }
            alreadyCheck = domainName
            this.getLastRecords()
        }else{
            logger.info("必要参数 domain [-d] 不存在.")
        }
    }

    fun getLastRecords(){
        if (lastRequestRecords.size > 0 ){
            for (entry in lastRequestRecords.entries) {
                logger.info("[ ${entry.key} ]${entry.value}")
            }
            logger.info("${alreadyCheck}下有${lastRequestRecords.size}条记录.")
            logger.info("输入命令 updns -ali <序号> [-auto 自动修改(传入时间 例如 24H)] \r\n" +
                    " [-name 主机记录][-domain 域名(默认使用ipscan命令所得值)][-value 记录值][-type 类型]")
        }else{
            if (alreadyCheck == ""){
                logger.info("请使用dns <-d 域名> 查询域名下解析记录.")
            }else{
                logger.info("${alreadyCheck}所属域名没有记录.")
            }
        }
    }

    fun modifyRecord(n0: String?, args: JSONObject): Unit {
        val orderNum = args.getInteger("ali")
        if (orderNum == null || orderNum > lastRequestRecords.size){
            logger.info("请检查序号是否正确.")
        }else{
            if (orderNum == 0){
                args["allChange"] = true
                lastRequestRecords.entries.forEach {
                    args["ali"] = it.key
                    this.modifyOneRecord(args)
                }
            }else{
                this.modifyOneRecord(args)
            }

        }
    }

    private fun modifyOneRecord(args: JSONObject): Unit {
        val orderNum = args.getInteger("ali")
        val dnsObj = lastRequestRecords[orderNum]
        if (dnsObj == null){
            logger.info("未查询到此条目.")
            return
        }else{
            logger.info("选择条目：$dnsObj")
        }
        val remark = dnsObj.remark
        if (remark?.startsWith("PN") == true || args.getBooleanValue("f")){
            val updateDomainRecordRequest = UpdateDomainRecordRequest()
            val value = args.getString("value")
            if (value != null){
                updateDomainRecordRequest.value = value
            }else{
                val lastRecord = IpScanCmd.lastRecord
                if (lastRecord != null){
                    updateDomainRecordRequest.value = lastRecord
                }else{
                    commandInterpreter.execute("ipscan -p false")
                    this.modifyOneRecord(args)
                    return
                }
            }
            updateDomainRecordRequest.recordId = args.getOrDefault("name",dnsObj.recordId) as String
            updateDomainRecordRequest.type = args.getOrDefault("type",dnsObj.type) as String
            updateDomainRecordRequest.rr = args.getOrDefault("name",dnsObj.name) as String
            val updateDomainRecord = client.updateDomainRecord(updateDomainRecordRequest)
            val describeDomainRecordInfoRequest = DescribeDomainRecordInfoRequest()
            describeDomainRecordInfoRequest.recordId = updateDomainRecord.body.recordId
            val describeDomainRecordInfo = client.describeDomainRecordInfo(describeDomainRecordInfoRequest)
            val body = describeDomainRecordInfo.body
            val newRecord = AliCloudDnsObj(body.recordId, body.RR, body.domainName, body.value, body.type, dnsObj.remark)
            logger.info("OLD:${dnsObj.hashCode()},NEW:${newRecord.hashCode()}[STATUS:${if (dnsObj.hashCode() != newRecord.hashCode()) "√" else "×"}]")
            if (dnsObj.hashCode() != newRecord.hashCode()){
                lastRequestRecords[orderNum] = newRecord
            }
        }else{
            logger.info("该条目不属于公网IP解析记录,如需修改请在参数中加入 -f(force 强制)")
        }
    }
}