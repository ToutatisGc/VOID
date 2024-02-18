package cn.toutatis.xvoid.ddns.ip.commands.commandLib.support

interface BaseCommand {

    companion object{

        fun execute(target:String?,args:Any?): Any?{
            System.err.println("cmd")
            return null
        }
    }

}