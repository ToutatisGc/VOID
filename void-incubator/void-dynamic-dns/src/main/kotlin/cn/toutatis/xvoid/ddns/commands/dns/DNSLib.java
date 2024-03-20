package cn.toutatis.xvoid.ddns.commands.dns;

import cn.toutatis.xvoid.ddns.commands.dns.ali.AliCloudDNS;
import cn.toutatis.xvoid.ddns.commands.support.BaseCommand;
import cn.toutatis.xvoid.ddns.commands.support.CommandHelper;
import com.alibaba.fastjson.JSONObject;

public class DNSLib extends CommandHelper implements BaseCommand {

    private static AliCloudDNS aliCloudDNS = new AliCloudDNS();

    public static void execute(String target, Object args){
        JSONObject analysisArgs = analysisArgs(args);
        if(analysisArgs.getBooleanValue("l")){
            aliCloudDNS.getLastRecords();
        }else{
            aliCloudDNS.getDescribeDomainRecordsRequest(analysisArgs);
        }
    }

    public static void updateDNS(String target, Object args){
        aliCloudDNS.modifyRecord(target,analysisArgs(args));
    }
}
