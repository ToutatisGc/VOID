package cn.toutatis.xvoid.creator.tools;

import java.io.Serializable;

/**
 * @author Toutatis_Gc
 */

public enum ConfigurationTable implements Serializable {

//    项目信息
    APP_NAME("VOID开发工具"),
    COPY_RIGHT_INFO("开发基于Apache 2.0 协议"),
    COPY_RIGHT_INFO_DETAILS(COPY_RIGHT_INFO.info),
    AUTHOR_NAME("Toutatis_Gc"),
//    框体颜色
    GREY_COLOR_BRUNET("100"),
    GREY_COLOR_TINT("160"),
//    窗体文字
    ERROR_WINDOW_TITLE("错误信息"),
    NOTIFY_WINDOW_TITLE("提示信息"),
    LEFT_PANEL_DATABASE_LIST_TITLE("数据库表名(双击查看表详情信息)"),
    GENERATE_PACKAGE_INFO_LABEL("生成package路径"),
    SELECTED_DATABASE_LIST_LABEL("当前选择数据表"),
    LEFT_PANEL_DATABASE_CONFIGURATION_BUTTON("数据源配置"),
    MAIN_PANEL_CHOOSE_TITLE("项目路径"),
    MAIN_PANEL_CHOOSE_BUTTON("选择项目路径"),
    MAIN_PANEL_CHOOSE_PATH_BLANK("请选择项目根目录"),
    DATABASE_CONFIGURATION_SOURCE_LABEL("数据库"),
    DATABASE_CONFIGURATION_USERNAME_LABEL("用户名"),
    DATABASE_CONFIGURATION_PASSWORD_LABEL("密  码"),
    DATA_SOURCE_LABEL("请选择数据库"),
    DATA_USERNAME_LABEL("请输入用户名"),
    DATA_PASSWORD_LABEL("请输入密码"),
//    提示信息
    DATABASE_ERROR_MESSAGE("数据库连接错误"),
    DELETE_CONFIG_WARNING_INFO_HINT_EN("DON'T DELETE OR MODIFY THIS FILE!"),
    DELETE_CONFIG_WARNING_INFO_HINT_CN("请勿随意删除配置内文件"),
    BLANK_PATH_HINT("首次使用,请选择项目路径."),
    IS_APP_DIR_HINT("当前选择为项目路径,仅生成ORM文件."),
    MISSING_CONFIG_FILE_HINT("配置文件丢失."),
    NULL_SELECT_TABLE_HINT("未选择数据表."),
    NOT_APP_DIR_HINT("当前选择为空文件夹,在此生成项目基础结构.[警告:会清除文件夹所有文件!]"),
    DEV_LANG_LABEL("开发语言:"),
    // 状态信息
    SUCCESSFUL_SIGN("1"),
    FAILURE_SIGN("0"),
    ;




    private String info;
    public String getInfo() { return info; }
    public Integer getInt(){ return Integer.valueOf(this.info); }
    ConfigurationTable(String info) { this.info = info; }
    private ConfigurationTable(){}
}
