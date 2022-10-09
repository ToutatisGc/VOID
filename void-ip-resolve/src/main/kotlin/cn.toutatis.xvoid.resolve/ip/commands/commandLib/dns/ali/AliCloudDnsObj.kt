package cn.toutatis.ip.commands.commandLib.dns.ali

data class AliCloudDnsObj(val recordId :String, val name:String,val domain:String,val value:String,val type:String,val remark:String?){

    override fun toString(): String {
        return "阿里云DNS解析记录[$recordId][主机记录='$name', 所属域名='$domain', 记录值='$value', 类型='$type', 备注=$remark]"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AliCloudDnsObj

        if (recordId != other.recordId) return false
        if (name != other.name) return false
        if (domain != other.domain) return false
        if (value != other.value) return false
        if (type != other.type) return false

        return true
    }

    override fun hashCode(): Int {
        var result = recordId.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + domain.hashCode()
        result = 31 * result + value.hashCode()
        result = 31 * result + type.hashCode()
        return result
    }


}
