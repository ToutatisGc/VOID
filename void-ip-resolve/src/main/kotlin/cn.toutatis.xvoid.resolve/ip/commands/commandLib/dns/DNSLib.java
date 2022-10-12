package cn.toutatis.xvoid.resolve.ip.commands.commandLib.dns;

import cn.toutatis.xvoid.resolve.ip.commands.commandLib.dns.ali.AliCloudDNS;
import cn.toutatis.xvoid.resolve.ip.commands.commandLib.support.BaseCommand;
import cn.toutatis.xvoid.resolve.ip.commands.commandLib.support.CommandHelper;
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
