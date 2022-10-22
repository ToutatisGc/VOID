package cn.toutatis.xvoid.toolkit.formatting

import org.yaml.snakeyaml.Yaml
import java.io.File

/**
 * @author Toutatis_Gc
 * @date 2022/10/21 21:56
 *
 */
object YamlToolkit {

    @JvmStatic
    fun formatFile(file:File): Map<String,Any> {
        val yaml = Yaml()
        return yaml.load(file.inputStream())
    }

}