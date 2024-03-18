package cn.toutatis.xvoid.ddns.commands;

import cn.toutatis.xvoid.ddns.DynamicDNSResolver;
import cn.toutatis.xvoid.ddns.commands.support.BaseCommand;
import cn.toutatis.xvoid.ddns.commands.support.CommandHelper;
import cn.toutatis.xvoid.toolkit.log.LoggerToolkit;
import cn.toutatis.xvoid.toolkit.log.LoggerToolkitKt;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;

import static cn.toutatis.xvoid.ddns.constance.CommonConstance.MODULE_NAME;

/**
 * @author Toutatis_Gc
 */
public class BaseLib extends CommandHelper implements BaseCommand {

    private final static Logger logger = LoggerToolkit.getLogger(BaseLib.class);

    public static void exit(String target,Object args){
        if (DynamicDNSResolver.Companion.getModeCache()){
            System.exit(0);
        }else {
            logger.error("["+MODULE_NAME+"]指令模式禁止退出");
        }
    }

    public static void help(String target,Object args){
        JSONObject analysisArgs = analysisArgs(args);
        String command = analysisArgs.getString("c");
        JSONObject commandTable = DynamicDNSResolver.COMMAND_INTERPRETER.getCommandTable();
        // TODO 命令解析
        if (command == null){
            commandTable.forEach((key, value) -> {
                System.out.println("----------------------------------------------------------------------------");
                JSONObject obj = commandTable.getJSONObject(key);
                if ("command".equals(obj.getString("type"))){
                    System.out.println("命令:"+key+"\t"+obj.getString("description"));
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
                            System.out.println(sb);
                        });
                    }
                }
            });
        }else{
            if("true".equals(command)){
                LoggerToolkitKt.warnWithModule(logger,MODULE_NAME,"command参数：请输入具体指令");
            }else{

            }
        }


    }

//    public static void next(String target,Object args){
//        StringBuilder sb = new StringBuilder("输入命令[");
//        JSONObject commandTable = DynamicDNSResolver.Companion.getCommandInterpreter().getCommandTable();
//        JSONObject targetCommand = commandTable.getJSONObject(target);
//        JSONObject next = targetCommand.getJSONObject("next");
//        logger.info(sb.toString());
//    }


}
