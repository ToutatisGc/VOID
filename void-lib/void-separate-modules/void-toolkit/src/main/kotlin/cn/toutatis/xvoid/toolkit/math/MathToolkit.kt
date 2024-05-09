package cn.toutatis.xvoid.toolkit.math

import kotlin.math.abs

/**
 * 数学工具
 * @author Toutatis_Gc
 */
object MathToolkit {

    /**
     * 判断一个数是否为偶数
     */
    @JvmStatic
    fun numberIsEven(number: Int): Boolean {
        return number % 2 != 0
    }

    /**
     * 判断一个数是否为奇数
     */
    @JvmStatic
    fun numberIsOdd(number: Int): Boolean {
        return !numberIsEven(number)
    }

    /**
     * 计算两个整数的最大公约数
     */
    @JvmStatic
    fun gcd(a: Int, b: Int): Int {
        return if (b == 0) a else gcd(b, a % b)
    }

    /**
     * 计算两个整数的最小公倍数
     */
    @JvmStatic
    fun lcm(a: Int, b: Int): Int {
        return abs(a * b) / gcd(a, b)
    }

    /**
     * 判断一个数是否为素数
     * @param n 数字
     */
    @JvmStatic
    fun isPrime(n: Int): Boolean {
        if (n <= 1) return false
        if (n <= 3) return true
        if (n % 2 == 0 || n % 3 == 0) return false
        var i = 5
        while (i * i <= n) {
            if (n % i == 0 || n % (i + 2) == 0) return false
            i += 6
        }
        return true
    }

    /**
     * 计算一个数的阶乘
     * @param n 数字
     */
    @JvmStatic
    fun factorial(n: Int): Long {
        var result: Long = 1
        for (i in 2..n) {
            result *= i.toLong()
        }
        return result
    }

    /**
     * 计算两个数的乘方
     * @param base 基数
     * @param exponent 指数
     */
    @JvmStatic
    fun power(base: Double, exponent: Int): Double {
        return if (exponent == 0) {
            1.0
        } else if (exponent % 2 == 0) {
            val half = power(base, exponent / 2)
            half * half
        } else {
            base * power(base, exponent - 1)
        }
    }

    /**
     * 计算一个数的平方根
     * @param n 数
     */
    @JvmStatic
    fun sqrt(n: Double): Double {
        return sqrt(n)
    }

    /**
     * 计算正弦值
     * @param degrees 角度
     */
    @JvmStatic
    fun sin(degrees: Double): Double {
        return sin(Math.toRadians(degrees))
    }

    /**
     * 计算余弦值
     * @param degrees 角度
     */
    @JvmStatic
    fun cos(degrees: Double): Double {
        return cos(Math.toRadians(degrees))
    }

    /**
     * 计算正切值
     * @param degrees 角度
     */
    @JvmStatic
    fun tan(degrees: Double): Double {
        return tan(Math.toRadians(degrees))
    }

    /**
     * 线性回归数据点
     */
    data class DataPoint(val x: Double, val y: Double)

    /**
     * 线性回归
     * 根据一组数据点计算出最佳拟合
     * @param data 数据
     */
    fun simpleLinearRegression(data: List<DataPoint>): Pair<Double, Double> {
        val dataSize = data.size
        val sumX = data.sumOf { it.x }
        val sumY = data.sumOf { it.y }
        val sumXY = data.sumOf { it.x * it.y }
        val sumXSquare = data.sumOf { it.x * it.x }

        val slope = (dataSize * sumXY - sumX * sumY) / (dataSize * sumXSquare - sumX * sumX)
        val intercept = (sumY - slope * sumX) / dataSize

        return Pair(slope, intercept)
    }
}