package cn.toutatis.xvoid.resolve.ip.commands.commandLib;

import cn.toutatis.ip.commands.commandLib.support.BaseCommand;
import cn.toutatis.ip.commands.commandLib.support.CommandHelper;
import cn.toutatis.xvoid.resolve.ip.IPResolver;
import cn.toutatis.xvoid.toolkit.log.LoggerToolkit;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;

import static cn.toutatis.xvoid.resolve.ip.PkgInfo.MODULE_NAME;

/**
 * @author Toutatis_Gc
 * @date 2022/10/7 22:32
 */
public class BaseLib extends CommandHelper implements BaseCommand {

    private final static Logger logger = LoggerToolkit.INSTANCE.getLogger(BaseLib.class);

    @Override
    public Object execute(String target,Object args) {
        return execute();
    }

    @Override
    public Object execute() {
        return null;
    }

    public static void exit(){
        boolean modea = IPResolver.Companion.getModea();
        if (!modea){
            System.exit(0);
        }else {
            logger.error("["+MODULE_NAME+"]指令模式禁止退出");
        }
    }

    public static void help(){
        JSONObject commandTable = IPResolver.commandInterpreter.getCommandTable();
        commandTable.forEach((key, value) -> {
            JSONObject obj = commandTable.getJSONObject(key);
            if ("command".equals(obj.getString("type"))){
                logger.info("命令:"+key+"\t"+obj.getString("description"));
                if (obj.containsKey("args")){
                    JSONObject args = obj.getJSONObject("args");
                    args.forEach((akey,avalue) ->{
                        StringBuilder sb = new StringBuilder("\t\t-");
                        sb.append(akey);
                        JSONObject arg = args.getJSONObject(akey);
                        Boolean required = arg.getBoolean("required");
                        if (required){
                            sb.append("[*]");
                        }
                        sb.append(" ").append(arg.getString("description"));
                        logger.info(sb.toString());
                    });
                }
            }
        });
    }


}
