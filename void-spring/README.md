# [VOID-SPRING]使用说明

## Module based by SpringBoot

##### Author@Toutatis_Gc

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

