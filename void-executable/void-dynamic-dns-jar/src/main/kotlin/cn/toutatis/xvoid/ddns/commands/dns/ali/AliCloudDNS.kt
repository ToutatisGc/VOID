package cn.toutatis.xvoid.ddns.commands.dns.ali

import cn.toutatis.xvoid.ddns.IPResolver
import cn.toutatis.xvoid.ddns.IPResolver.Companion.commandInterpreter
import cn.toutatis.xvoid.ddns.IPResolver.Companion.config
import cn.toutatis.xvoid.ddns.PkgInfo.MODULE_NAME
import cn.toutatis.xvoid.toolkit.validator.Validator
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

    /**
     * 获取阿里云云解析域名带有_CROSS_备注的记录
     */
    fun getDescribeDomainRecordsRequest(args: JSONObject): Unit {
        val domainName = config.getProperty("Resolve-Domain")
        if (Validator.strNotBlank(domainName)){
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
                if ("_CROSS_" == record.remark){
                    lastRequestRecords[index+1] = aliCloudDnsObj
                }else if (args.getBooleanValue("a")){
                    lastRequestRecords[index+1] = aliCloudDnsObj
                }
            }
            alreadyCheck = domainName
            this.getLastRecords()
        }else{
            logger.info("必要配置[Resolve-Domain]不存在")
        }
    }

    fun getLastRecords(){
        if (lastRequestRecords.size > 0 ){
            for (entry in lastRequestRecords.entries) {
                logger.info("[ ${entry.key} ]${entry.value}")
            }
            logger.info("${alreadyCheck}下有${lastRequestRecords.size}条记录.")
        }else{
            if (alreadyCheck == ""){
                logger.info("请使用[dns]命令查询域名下解析记录.")
            }else{
                logger.info("${alreadyCheck}所属域名没有记录.")
            }
        }
    }

    fun modifyRecord(n0: String?, args: JSONObject): Unit {
        val orderNum = args.getInteger("s")
        if (orderNum == null || orderNum > lastRequestRecords.size){
            logger.info("请检查序号是否正确.")
        }else{
            if (orderNum == 0){
                args["allChange"] = true
                lastRequestRecords.entries.forEach {
                    args["s"] = it.key
                    this.modifyOneRecord(args)
                }
            }else{
                this.modifyOneRecord(args)
            }

        }
    }

    private fun modifyOneRecord(args: JSONObject): Unit {
        val orderNum = args.getInteger("s")
        val dnsObj = lastRequestRecords[orderNum]
        if (dnsObj == null){
            logger.info("未查询到此条目.")
            return
        }else{
            logger.info("选择条目：$dnsObj")
        }
        val remark = dnsObj.remark
        if (remark?.startsWith("_CROSS_") == true || args.getBooleanValue("f")){
            val updateDomainRecordRequest = UpdateDomainRecordRequest()
            val address = args.getString("a")
            if (address != null){
                updateDomainRecordRequest.value = address
            }else{
                val lastRecord = IPResolver.lastRecord
                if (Validator.strNotBlank(lastRecord)){
                    if (lastRecord == dnsObj.value){
                        logger.info("[${MODULE_NAME}]OLD:${dnsObj.hashCode()}[STATUS:=]")
                        return
                    }
                    updateDomainRecordRequest.value = lastRecord
                }else{
                    commandInterpreter.execute("scan")
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
            logger.info("[${MODULE_NAME}]OLD:${dnsObj.hashCode()},NEW:${newRecord.hashCode()}[STATUS:${if (dnsObj.hashCode() != newRecord.hashCode()) "√" else "×"}]")
            if (dnsObj.hashCode() != newRecord.hashCode()){
                lastRequestRecords[orderNum] = newRecord
            }
        }else{
            logger.info("该条目不属于公网IP解析记录,如需修改请在参数中加入 -f(force 强制)")
        }
    }
}