# [VOID-SPRING]使用说明

## based by SpringBoot

##### Author@Toutatis_Gc

### # 基础说明

​		该框架基于Springboot 2.7.6 开发,在原本框架的基础上整合了多方依赖,使其具备了更完善的功能和增强.

​		<font color="red">**在运行之前，请先浏览下列说明【非常重要】**</font>

### # Step 1 环境要求

请先准备好相关环境

JDK 17是未来发展大趋势，实际在本框架开发中遇到了不少小毛病，但是可稳定运行，大部分问题已经解决，之前开发一直使用的是JDK8，JDK17相较于JDK8在使用上并没有什么大的变化，可以放心使用。新特性可以查阅官网更新说明。

| 依赖                            | 版本         | 用途                                                         |
| ------------------------------- | ------------ | ------------------------------------------------------------ |
| IDEA                            | 2021.1+      | 开发IDE，最低需要此版本，再低有些特性不支持会报错            |
| Kotlin                          | 1.9.20       | 主要转型Kotlin,底层使用Kotlin,业务层混编Java                 |
| JDK[可选GraalVM]                | 17           | 主开发平台，可选择[GraalVM]，但是可能该VM自身缺陷导致反射等问题，效率比原生JDK高 |
| Node.js[非必要]                 | 18.12.1+     | 前端开发和后台部分样式需要支持，如果需要修改css下的scss文件，那么就需要添加相关环境，不需要则无需关注,已经上传一版CSS文件 |
| ┗━ sass预处理器[非必要]         | 1.56.1       | 后台样式文件使用SCSS编写,需要编译成CSS                       |
| ┗━ IDEA插件 FileWatcher[非必要] | 插件市场搜索 | [IDEA插件]可以监控SCSS变动,时刻编译SCSS文件为CSS文件         |
| ┗━ npm包管理器[非必要]          | 8.19.2+      | 需要npm下载sass [命令行: npm install -g sass]                |
| IDEA插件 Lombok                 | 插件市场搜索 | [IDEA插件]序列化对象插件[可能和Kotlin兼容性有问题]           |

### # Step 2 最低可运行说明

该项目强制依赖中间件,请先配置好相关参数和运行环境。

开发使用Docker运行相关中间件，可选可不选，可自行下载中间件部署。

| 依赖             | 版本         | 说明                |
| ---------------- | ------------ | ------------------- |
| Docker[可选]     | 20.21.10+    | 应用容器引擎        |
| ┗━ MySQL/MariaDB | 8.0.0/10.0.1 | 关系型数据库        |
| ┗━ Redis         | 5.0.3        | 非关系型数据库/缓存 |
| ┗━ RabbitMQ      | 3.9.26       | 消息队列            |



### # 重要文件说明

| 文件/目录名称                    | 简介                                                         |
| -------------------------------- | ------------------------------------------------------------ |
| resources/config                 | spring配置文件,多模块配置分解为小的配置文件,清晰明了         |
| resources/templates              | 公共页面模板,例如404,500等页面                               |
| resources/favicon.ico            | 页面图标                                                     |
| resources/logback-spring.xml     | 日志配置文件 Logback官方文档 [[Logback官方文档]](https://logback.qos.ch/documentation.html) |
| resources/openMapping.properties | 强访问权限,清单不存在条目则禁止访问                          |

------

### # 常见问题

##### 1.ERROR com.alibaba.druid.pool.DruidDataSource - testWhileIdle is true, validationQuery not set

该问题是引入了p6spy监控SQL所引发的，在开发模式下不影响使用，**线上生产应当将jdbc连接的p6spy移除**

------

##### 2.Configuration problem: @Bean method 'XXX' must not be private or final; change the method's modifiers to continue

该问题是因Kotlin默认class为final类所引起，kotlin编译时已apply spring plugin无需关注，但可能有时出现编译时问题，**解决方法为重新编译或手动添加open关键字**

------

