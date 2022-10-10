package cn.toutatis.xvoid.resolve.ip.commands.commandLib.dns;

import cn.toutatis.xvoid.resolve.ip.IPResolver;
import cn.toutatis.xvoid.resolve.ip.commands.commandLib.support.CommandHelper;
import cn.toutatis.xvoid.resolve.ip.scan.UrlPoolScanner;
import cn.toutatis.xvoid.toolkit.log.LoggerToolkit;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * @author Toutatis_Gc
 * @date 2022/10/10 16:16
 * IP 相关命令库
 */
public class IpLib extends CommandHelper {

    private final static Logger logger = LoggerToolkit.getLogger(IpLib.class);


    private static String lastRecord = null;

    private static final UrlPoolScanner urlPoolScanner = new UrlPoolScanner(IPResolver.urlPool);

    public static void execute(String target,Object args) {
        JSONArray ipGroup = urlPoolScanner.getIp();
        if (ipGroup!= null && !ipGroup.isEmpty()){
            JSONObject analysisArgs = analysisArgs(args);
            Boolean choosePingIp = (Boolean) analysisArgs.getOrDefault("p", false);
            if (ipGroup.size() == 1){
                Map.Entry<String, Integer> ip = (Map.Entry<String, Integer>) ipGroup.get(0);
                logger.info("当前公网IP为["+ip.getKey()+"]");
                lastRecord = ip.getKey();
                if (choosePingIp){
                    pingIP(lastRecord);
                }
            }else{
                logger.info("当前命中目标"+ipGroup.size()+"个,按优先级排列.");
                for (Object ips : ipGroup) {
                    Map.Entry<String, Integer> ip = (Map.Entry<String, Integer>) ips;
                    logger.info(ip.getKey()+" HITS:"+ip.getValue());
                    String defaultChooseFirstAddress = IPResolver.config.getProperty("Default-Choose-First-Address");
                    if (Boolean.parseBoolean(defaultChooseFirstAddress)){
                        logger.info("当前公网IP为["+ip.getKey()+"]");
                        lastRecord = ip.getKey();
                    }
                }
            }
        }else{
            logger.error("当前未发现公网IP,请检查网络或配置文件.");
        }
    }

    public static String getLastRecord() {
        return lastRecord;
    }


    private static void pingIP(String lastRecord){
        logger.info("WAITING PING URL:"+lastRecord);
        try {
            Process process = Runtime.getRuntime().exec("ping "+lastRecord);
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(), Charset.forName("GBK")));
            String line = br.readLine();
            while(line!= null){
                logger.info(line);
                line = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
