package cn.toutatis.xvoid.resolve.ip.commands.commandLib.support

interface BaseCommand {

    companion object{

        fun execute(target:String?,args:Any?): Any?{
            System.err.println("cmd")
            return null
        }
    }

}