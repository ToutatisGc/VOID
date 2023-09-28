package cn.toutatis.xvoid.sql.base;

import java.io.Serializable;
import java.util.function.Function;

/**
 * @author Toutatis_Gc
 */
@FunctionalInterface
public interface XFunc<T,R> extends Function<T,R>, Serializable {
}
