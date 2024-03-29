# VOID  ![Hex.pm](https://img.shields.io/hexpm/l/blue?style=flat-square) ![GitHub commit activity](https://img.shields.io/github/commit-activity/w/ToutatisGc/VOID?style=flat-square) ![GitHub last commit](https://img.shields.io/github/last-commit/ToutatisGc/VOID?style=flat-square)

### Author:Toutatis_Gc

![banner](docs/asserts/images/banner.jpg)

VOID帮助您创建以Spring Boot/Vert.X为基础驱动的生产级Web服务，它对Spring Boot/Vert.X平台采取了支持和增强的功能,以便新用户和现有用户可以快速搭建服务.



您可以使用Spring Boot/Vert.x作为原生平台创建独立的Java Web服务器或WebSocket流服务器,也可以使用该包下所支持的增强功能.

**请注意：**
    **目前开发遵守MRU（最小可运行单元）概念，即最低使用原则，功能并不完善且有些功能和效率并不合理，但实现大于概念，在可运行的状态下不断完善。**



我们的主要目标是:

- 为所有Spring Boot/Vert.x平台开发支持更广泛的,并且可访问的入门服务.

- 对于原生Spring Boot/Vert.x平台对于Web请求和响应的标准化支持.

- 提供一系列非功能性的支持,这些特性是所有Web项目所共有的特性(例如: OOM,安全,指标,标准化等)

- 大部分情况下拆箱即用,免去了部分yml,properties或者是XML的配置.

  

## # 安装和开始

每一个子包下都有对于该包的说明,具体可以参考每一个包下的README.md文件.



1.引入依赖

您可以引入Maven仓库下的依赖或者下载本仓库的项目自行Install至本地.

Git clone方式:

```bash
git clone https://github.com/ToutatisGc/VOID.git
```

仓库镜像方式:

```
暂无
```

2.编写启动类

[SpringBoot]

```
暂无
```

[Vert.X]

```
暂无
```



## # 帮助

​		您在VOID遇到麻烦了吗?我们可以提供以下帮助:

- 请查阅参考文档,使用说明或者使用实例,它们为最常见的问题提供了解决方案.
- 学习Spring/Vert.x的知识,VOID支持建立在Java，Kotlin语言，Stream及reactiveb编程/Spring/Vert.x的基础上,如果碰到了 package 以非 cn.toutatis 域下的实例问题,请查阅 相关的官网 以获取相关框架的帮助.
- 如果您正在升级,请查阅更新说明,了解新的特性以及功能变动.
- 在本项目的git下的issues发布问题,我会第一时间查看并提供解决方案.



## # 报告问题

​		VOID使用GitHub的Issues集成问题.如果您想提出问题,请遵循以下建议:

- 在您记录错误之前,请搜索问题,看看是否有人已经报告了该问题.
- 如果这个问题还不存在,那么创建一个新的问题.请在问题报告中提供尽可能多的信息.我们想知道你使用的Spring Boot/Vert.x版本/VOID版本,操作系统和JVM版本.如果需要粘贴代码或包含堆栈跟踪，请使用Markdown.如果可能,尝试创建一个测试用例或项目来复制问题,并将其与问题联系起来.



## # 从源代码开始构建

​		使用VOID不需要从源代码开始构建,如果您想尝试或改进版本,可以使用Maven 3+构建并发布到本地Maven缓存中.

​		您还需要JDK 1.8或以上版本

```bash
#void-lib目录下
mvn clean install -Dgpg.skip
```



## # 模块

​		VOID中的模块在此快速的概述.

|     模块名称      | 完成情况    | 用途                                                         |
| :---------------: | :---------- | :----------------------------------------------------------- |
|       docs        | ♻️[资源目录] | [文档资源]文档资源以及使用说明                               |
|  void-ip-resolve  | ✅[可运行]   | [私网转公网地址解析] 向运营商申请公网IP后云解析地址 [[文档地址]](./void-ip-resolve/README.md) |
| void-compress-jar | ✅[可运行]   | [压缩工具]目前只提供图片压缩                                 |
|   void-creater    | ❗[进行中]   | [框架生成器]已经完成了后端类的生成,前端应当可生成多种模板    |
|  void-front-end   | ❗🔽[进行中]  | [前端部分]正在完善后台 [[文档地址]](./void-front-end/README.md) |
|     void-lib      | ❗🔽[进行中]  | [依赖以及库支持]库根目录,包含了所有底层模块 [[文档地址]](./void-lib/README.md) |
|    void-spring    | ❗[进行中]   | [基础框架]SpringBoot基础框架,拆箱即用                        |
|    void-vertx     | ❗[进行中]   | [基础框架]Vert.X基础框架,拆箱即用                            |



### # 设计规范

| 规范名称       | 文档说明                                                     |
| -------------- | ------------------------------------------------------------ |
| 前端设计文档   | 此文档包含颜色,字体,阴影,图标,请求等规范 [[参考文档]](docs/reference-docs/front-end-side/Index.md) |
| 后端设计文档   | 此文档包含结构树,语法表,字段说明等 [[参考文档]](docs/reference-docs/server-side/Index.md) |
| 数据库设计文档 | 此文档包括命名规则,语法糖等说明 [[参考文档]](docs/reference-docs/databases-side/Index.md) |



## # 示例[暂无]

docs 提供了相关功能的示例,要运行示例,请下载相关文件夹并且导入IDE 查阅相关使用和查看依赖关系.

## # 许可证

VOID is Open Source software released under the [Apache 2.0 license](https://www.apache.org/licenses/LICENSE-2.0.html).