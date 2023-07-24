package cn.toutatis.xvoid;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * 定义业务类型
 * @author Toutatis_Gc
 */
public enum BusinessType {

    /**
     * 业务标志
     */
    XVOID_SYSTEM(0,"XVOID_SYSTEM","XVOID系统","系统模块业务"),
    XVOID_FORUM(1,"XVOID_FORUM","论坛系统","博客/论文模块业务"),
    ;

    private final Integer index;

    @EnumValue
    private final String code;

    private final String name;

    private final String description;

    BusinessType(Integer index, String code, String name, String description) {
        this.index = index;
        this.code = code;
        this.name = name;
        this.description = description;
    }

    public Integer getIndex() {
        return index;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
