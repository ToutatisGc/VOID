package cn.toutatis.ip.commands.commandLib.support

interface BaseCommand {

    fun execute(target:String?,args:Any?): Any?

    fun execute(): Any?
}