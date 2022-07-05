package cn.toutatis.xvoid.toolkit.objects;


/**
 * @author Toutatis_Gc
 * 对于object相关的工具类
 */
public class ObjectToolkit {

    private ObjectToolkit(){}
    private volatile static ObjectToolkit instance;
    public static ObjectToolkit getInstance() {
        if (instance == null){
            synchronized (ObjectToolkit.class){
                if (instance == null){
                    instance = new ObjectToolkit();
                }
            }
        }
        return instance;
    }

    /**
     * @param o 可转String的object
     * @return 返回是否为空
     */
    public boolean strNotBlank(Object o){
        String s = String.valueOf(o);
        return s != null && !"".equals(s) && !"null".equals(s);
    }

    public boolean strIsBlank(Object o){
        String s = String.valueOf(o);
        if (s == null){
            return true;
        }
        String trim = s.trim().toLowerCase();
        return trim.length() == 0 || "null".equals(trim) || "undefined".equals(trim);
    }

    /**
     * 此方法会去除左右空格
     * @param o 可转String的object
     * @return 返回是否为空
     */
    public boolean strNotBlankTrim(Object o){
        String s = String.valueOf(o);
        return o != null && !"".equals(s.trim());
    }

    /**
     * @param o 实体类
     * @return 对象是否为空
     */
    public boolean objectIsNull(Object o){
        return o == null;
    }

    /**
     * @param o 实体类
     * @return 对象是否为空
     */
    public boolean objectNotNull(Object o){
        return !objectIsNull(o);
    }

    /**
     * @param parameter 判断参数
     * @return 查询参数中是否有Null值或者空白
     */
    public boolean parametersHaveNull(Object... parameter){
        boolean b = false;
        for (Object o : parameter) {
            if (!this.strNotBlank(o)){
                b = true;
                break;
            }
        }
        return b;
    }

}
