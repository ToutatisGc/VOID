package cn.toutatis.xvoid.ddns.commands.support

class PlayBook {

    /**
     * Schedule 定时信息
     */
    class Schedule {

        /**
         * Delay 延迟(单位：毫秒)
         */
        var delay: Int = 0

        /**
         * Period 循环周期(单位：分钟)
         */
        var period: Int = 60

        /**
         * Cycle 循环次数
         */
        var cycle: Int = -1
    }

}