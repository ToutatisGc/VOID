package cn.toutatis.xvoid.spring.business.open.constant;

/**
 * 微信消息回调类型
 * @author Toutatis_Gc
 */
public enum WechatMessageType {
    /**
     * 文本
     */
    TEXT("text","文本事件"),
    /**
     * 事件
     */
    EVENT("event","上传事件")
    ;

    WechatMessageType(String type, String description) {
        this.type = type;
        this.description = description;
    }

    private final String type;

    private final String description;

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }
}
