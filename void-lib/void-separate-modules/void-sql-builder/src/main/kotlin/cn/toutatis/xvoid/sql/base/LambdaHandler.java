package cn.toutatis.xvoid.sql.base;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.util.Map;

public class LambdaHandler {

    public static final String GET_FIELD_LAMBDA = "get";

    public static <T, R> SerializedLambda serialize(XFunc<?, ?> func) throws Exception {
        Method writeReplace = func.getClass().getDeclaredMethod("writeReplace");
        writeReplace.setAccessible(true);
        Object sl = writeReplace.invoke(func);
        return (SerializedLambda) sl;
    }

    public static String getFieldName(SerializedLambda serializedLambda){
        String implMethodName = serializedLambda.getImplMethodName();
        if (!serializedLambda.getImplMethodName().startsWith(GET_FIELD_LAMBDA)){
            throw new IllegalArgumentException("非GET参数");
        }
        String UppercaseName = implMethodName.substring(3);
        char firstChar = Character.toLowerCase(UppercaseName.charAt(0));
        return firstChar + UppercaseName.substring(1);
    }

//    public static Map<String,Object> getValue(){
//
//    }

}
