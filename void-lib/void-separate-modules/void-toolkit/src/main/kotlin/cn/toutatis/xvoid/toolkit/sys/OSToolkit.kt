package cn.toutatis.ip.tools

import java.util.*

enum class OSToolkit {

    INSTANCE;

    fun getOSType(): String {
        val systemProperties = System.getProperties()
        return systemProperties.getProperty("os.name", "null")
    }

    fun isWindowsSystem(): Boolean {
        return this.getOSType().uppercase(Locale.getDefault()).indexOf("WINDOWS") > -1
    }

    fun isLinuxSystem(): Boolean {
        return this.getOSType().uppercase(Locale.getDefault()).indexOf("LINUX") > -1
    }
}