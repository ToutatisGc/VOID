package cn.toutatis.xvoid.toolkit.validator;

public class XvoidWords {

    private static volatile SensitiveWordFilter sensitiveWordFilter;

    /**
     * 获取离线敏感词过滤器
     * 本工具资源内置敏感词字典
     * @return 敏感词过滤器
     */
    public static SensitiveWordFilter getBuiltInSensitiveWordFilter() {
        if (sensitiveWordFilter == null) {
            synchronized (XvoidWords.class) {
                if (sensitiveWordFilter == null) {
                    sensitiveWordFilter = new SensitiveWordFilter();
                    // TODO 添加敏感词
                }
            }
        }
        return sensitiveWordFilter;
    }

}
