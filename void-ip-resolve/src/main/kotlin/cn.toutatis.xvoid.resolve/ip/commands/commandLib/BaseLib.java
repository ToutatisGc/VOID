package cn.toutatis.xvoid.resolve.ip.commands.commandLib;

import cn.toutatis.xvoid.resolve.ip.IPResolver;
import cn.toutatis.xvoid.resolve.ip.commands.commandLib.support.BaseCommand;
import cn.toutatis.xvoid.resolve.ip.commands.commandLib.support.CommandHelper;
import cn.toutatis.xvoid.toolkit.log.LoggerToolkit;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;

import static cn.toutatis.xvoid.resolve.ip.PkgInfo.MODULE_NAME;

/**
 * @author Toutatis_Gc
 * @date 2022/10/7 22:32
 */
public class BaseLib extends CommandHelper implements BaseCommand {

    private final static Logger logger = LoggerToolkit.getLogger(BaseLib.class);

    public static void exit(String target,Object args){
        boolean modea = IPResolver.Companion.getModea();
        if (!modea){
            System.exit(0);
        }else {
            logger.error("["+MODULE_NAME+"]指令模式禁止退出");
        }
    }

    public static void help(String target,Object args){
        JSONObject commandTable = IPResolver.commandInterpreter.getCommandTable();
        commandTable.forEach((key, value) -> {
            JSONObject obj = commandTable.getJSONObject(key);
            if ("command".equals(obj.getString("type"))){
                logger.info("命令:"+key+"\t"+obj.getString("description"));
                if (obj.containsKey("args")){
                    JSONObject innerArgs = obj.getJSONObject("args");
                    innerArgs.forEach((akey,avalue) ->{
                        StringBuilder sb = new StringBuilder("\t\t-");
                        sb.append(akey);
                        JSONObject arg = innerArgs.getJSONObject(akey);
                        Boolean required =(Boolean) arg.getOrDefault("required",false);
                        if (required){
                            sb.append("[*]");
                        }
                        sb.append(" ").append(arg.getString("fullName"));
                        sb.append(" ").append(arg.getString("description"));
                        logger.info(sb.toString());
                    });
                }
            }
        });
    }


}
