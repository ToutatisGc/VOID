package cn.toutatis.xvoid.toolkit.number

import java.math.BigDecimal

/**
 * 数字工具类
 * @author Toutatis_Gc
 */
object NumberToolkit {

    /**
     * 判断一个BigDecimal数字是否为整数
     * @param number BigDecimal
     * @return Boolean
     */
    @JvmStatic
    fun isInteger(number: BigDecimal): Boolean {
        // 使用 remainder() 方法检查是否有非零的小数部分
        return number.remainder(BigDecimal.ONE).compareTo(BigDecimal.ZERO) == 0
    }

}

/**
 * 扩展函数，判断一个BigDecimal数字是否为整数
 */
fun BigDecimal.isInteger(): Boolean {
    return NumberToolkit.isInteger(this)
}