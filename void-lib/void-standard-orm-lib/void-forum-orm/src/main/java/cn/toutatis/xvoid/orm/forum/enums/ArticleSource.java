package cn.toutatis.xvoid.orm.forum.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * 文章来源
 * @author Toutatis_Gc
 */
public enum ArticleSource {

    /**
     * 原创
     */
    ORIGINAL("ORIGINAL","原创"),

    /**
     * 转载
     */
    REPRINT("REPRINT","转载"),

    /**
     * 翻译版本
     */
    TRANSLATED_EDITION("TRANSLATED_EDITION","翻译版本");

    @EnumValue
    private final String name;

    private final String description;

    ArticleSource(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
