package cn.toutatis.xvoid.ddns.commands

import cn.toutatis.xvoid.ddns.DynamicDNSResolver
import cn.toutatis.xvoid.ddns.constance.CommonConstance.MODULE_NAME
import cn.toutatis.xvoid.ddns.constance.LibKeys
import cn.toutatis.xvoid.ddns.constance.LibKeys.COMMAND
import cn.toutatis.xvoid.ddns.constance.LibKeys.PLAYBOOK
import cn.toutatis.xvoid.ddns.constance.LibKeys.TASK_NAME
import cn.toutatis.xvoid.toolkit.constant.Time
import cn.toutatis.xvoid.toolkit.log.errorWithModule
import cn.toutatis.xvoid.toolkit.log.infoWithModule
import cn.toutatis.xvoid.toolkit.log.warnWithModule
import cn.toutatis.xvoid.toolkit.validator.Validator
import com.alibaba.fastjson.JSONException
import com.alibaba.fastjson.JSONObject
import com.alibaba.fastjson.parser.Feature
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.FileNotFoundException
import java.lang.reflect.Method
import java.time.LocalDateTime
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
    fun execute(command:String): Int {
        if (command.isNotEmpty()){
            val commandHash = HashMap<String, Any>(3)
            var commandFields = command.split(" ").toList()
            if (commandFields.isEmpty()){
                logger.info("请输入命令")
                return 0
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
        return 1
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

    /**
     *
     */
    fun play(playbookName: String,playbookInfo: JSONObject) {
        logger.infoWithModule(MODULE_NAME,"加载剧本:${playbookName}")
        val playbookTasks = playbookInfo.getJSONArray(LibKeys.PLAYBOOK_TASK)
        if (playbookTasks == null || playbookTasks.isEmpty()){
            logger.warnWithModule(MODULE_NAME,"剧本[$playbookName]任务内容为空.")
            return
        }
        val name = playbookInfo.getOrDefault(TASK_NAME, "未命名剧本")
        logger.infoWithModule(MODULE_NAME,"开始执行[$name]于:${Time.currentTime},共计${playbookTasks.size}个任务.")
        var executionCount = 0
        for (playbookTask in playbookTasks) {
            playbookTask as JSONObject
            val taskName = playbookTask.getOrDefault(TASK_NAME,"未命名子任务")
            logger.infoWithModule(MODULE_NAME,"开始执行[$taskName]于:${Time.currentTime}.")
            var cmd = playbookTask.getString(COMMAND)
            val args = playbookTask.getString(ARGS)
            if (Validator.strNotBlank(args)){
                cmd+= " $args"
            }
            executionCount += DynamicDNSResolver.COMMAND_INTERPRETER.execute(cmd)
        }
        val interval = playbookInfo.getJSONObject(LibKeys.PLAYBOOK_TIMER).getIntValue(LibKeys.PLAYBOOK_TIMER_INTERVAL)
        val nextInterval = LocalDateTime.now().plusMinutes(interval.toLong())
        val nextIntervalRegexTime = Time.regexTime(Time.SIMPLE_DATE_FORMAT_REGEX, nextInterval)
        logger.infoWithModule(MODULE_NAME,"执行[$name]完毕于:${Time.currentTime},共计${executionCount}个任务,预计将在${nextIntervalRegexTime}执行下次任务.")
    }

    /**
     * Get playbook info
     * 获取剧本信息
     * @param playbook 剧本名
     * @return 剧本信息
     */
    fun getPlaybookInfo(playbook: String):JSONObject? = try {
        val resourceFile = DynamicDNSResolver.getResourceFile("/$PLAYBOOK/$playbook")
        JSONObject.parseObject(resourceFile.readText(Charsets.UTF_8), Feature.OrderedField)
    }catch (e : FileNotFoundException){
        logger.errorWithModule(MODULE_NAME,"未找到剧本[$playbook]")
        null
    }catch (e: JSONException){
        logger.errorWithModule(MODULE_NAME,"剧本[$playbook]格式错误,请检查格式")
        null
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