package cn.toutatis.xvoid.toolkit.clazz

import java.io.Serializable
import java.lang.FunctionalInterface
import java.util.function.Function

/**
 * @see LambdaToolkit 供LambdaToolkit使用
 * @author Toutatis_Gc
 */
@FunctionalInterface
interface XFunc<T, R> : Function<T, R>, Serializable