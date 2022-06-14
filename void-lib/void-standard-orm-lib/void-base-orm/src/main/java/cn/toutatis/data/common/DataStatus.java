package cn.toutatis.data.common;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * @author Toutatis_Gc
 * @date 2022/6/13 20:49
 * 数据状态
 */
public enum DataStatus {

    /**
     * 数据状态枚举
     */
    SYS_OPEN("SYS_OPEN_0000","数据正常"),
    SYS_DELETED("SYS_DELETED_0000","逻辑删除"),
    ;

    @EnumValue
    private String code;

    private String content;

    DataStatus(String code, String content) {
        this.code = code;
        this.content = content;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
