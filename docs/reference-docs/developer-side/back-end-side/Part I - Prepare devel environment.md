运行前请检查
mvn版本,jdk版本..地址..说明

安装依赖
服务依赖于void-lib包
1.install void-feature-support
2.install void-lib

命名规则
功能的二次包装为Shell

找不到注入的类不要慌,因为把各种scan集合到一个注解中
idea无法探测注解配置
如果需要显示注入可以把@VoidApplication注解中的scan拆分出来
或者把IDEA警告检查设置为弱警告

实时模板设置
@Serial
private static final long serialVersionUID = 1L;
/**

* 数据库表名以及业务类型
    */
    public static final String TABLE = "";
    {this.setBusinessType(BusinessType.);}

项目启动失败?

中间件端口占用
例如redis
netstat -ano | findstr 6379

TCP    0.0.0.0:6379           0.0.0.0:0              LISTENING       11684
TCP    127.0.0.1:6379         127.0.0.1:13128        ESTABLISHED     11684

停止占用
taskkill /PID 11684