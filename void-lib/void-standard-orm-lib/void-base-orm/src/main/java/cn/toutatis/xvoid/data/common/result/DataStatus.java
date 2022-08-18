package cn.toutatis.xvoid.data.common.result;

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
    SYS_OPEN_0000(0,"SYS_OPEN_0000","数据正常"),
    SYS_DELETED_0000(1,"SYS_DELETED_0000","逻辑删除"),
    ;

    @EnumValue
    private final Integer index;

    private final String code;

    private final String content;

    DataStatus(Integer index, String code, String content) {
        this.index = index;
        this.code = code;
        this.content = content;
    }

    public String getCode() {
        return code;
    }

    public String getContent() {
        return content;
    }

    public Integer getIndex() {
        return index;
    }
}
