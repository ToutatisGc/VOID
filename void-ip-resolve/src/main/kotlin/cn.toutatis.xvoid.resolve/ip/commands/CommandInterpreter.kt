package cn.toutatis.xvoid.resolve.ip.commands

import cn.toutatis.xvoid.resolve.ip.IPResolver
import cn.toutatis.xvoid.resolve.ip.PkgInfo.MODULE_NAME
import cn.toutatis.xvoid.toolkit.validator.Validator
import com.alibaba.fastjson.JSONObject
import com.alibaba.fastjson.parser.Feature
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.lang.reflect.Method
import java.util.stream.Collectors

/**
 * 命令解释器
 */
class CommandInterpreter(private val commandTable:JSONObject) {

    companion object{

        val logger: Logger = LoggerFactory.getLogger(CommandInterpreter::class.java)

        private const val HEAD = "HEAD"

        private const val ARGS = "ARGS"

        private const val TARGET = "TARGET"

    }

    /**
     * @param command 命令行
     * 拆解命令并分解命令行结构
     */
    fun execute(command:String): Unit {
        if (command.isNotEmpty()){
            val commandHash = HashMap<String, Any>(3)
            var commandFields = command.split(" ").toList()
            if (commandFields.isEmpty()){
                logger.info("请输入命令")
                return
            }
            commandFields = commandFields.stream().filter { it.isNotEmpty() }.collect(Collectors.toList())
            commandHash[HEAD] = commandFields[0]
            when(commandFields.size){
                2 ->{
                    if (commandFields[1].startsWith("-")){
                        commandHash[ARGS] = commandFields[1]
                    }else{
                        commandHash[TARGET] = commandFields[1]
                    }
                }
                else ->{
                    commandHash[ARGS] = commandFields.subList(1,commandFields.lastIndex+1)
                    if (commandFields.size >= 3){
                        commandHash[TARGET] = commandFields[commandFields.lastIndex]
                    }
                }
            }
            this.analysis(commandHash)
        }
    }
    
    private fun analysis(command: HashMap<String , Any>): Unit {
        val head = (command[HEAD] as String).uppercase()
        val args = command[ARGS]
        val target = command[TARGET] as String?

        val containKey = commandTable.contains(head)
        if (containKey){
            var commandStruct = commandTable.getJSONObject(head)
            if ("alias" == commandStruct.getString("type")){
                commandStruct = commandTable.getJSONObject(commandStruct.getString("value"))
            }
            val clazz = Class.forName(commandStruct.getString("class"))
            val methodName = commandStruct.getOrDefault("method", "execute") as String
            val declaredMethod : Method = clazz.getDeclaredMethod(methodName,String::class.java,Any::class.java)
            declaredMethod.invoke(null,target,args)
        }else{
            finalPrintOut("未实现的命令 $head")
        }
    }
//
//    fun getNext(command: String){
//
//    }

    /**
     *
     */
    fun play(playbook: String) {
        logger.info("[${MODULE_NAME}]加载剧本:${playbook}")
        val playbook = JSONObject.parseObject(IPResolver.getFile("/playbook/$playbook").readText(Charsets.UTF_8), Feature.OrderedField)
        playbook.forEach { k, v ->
            v as JSONObject
            val args = v.getString("args")
            var cmd = k
            if (Validator.strNotBlank(args)){
                cmd+= " $args"
            }
            IPResolver.commandInterpreter.execute(cmd)
        }
    }

    /**
     * 获取命令集
     */
    fun getCommandTable():JSONObject{
        return commandTable
    }

    private fun finalPrintOut(detail:String){
        logger.info("$detail.")
    }



}