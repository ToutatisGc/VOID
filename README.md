# VOID  ![Hex.pm](https://img.shields.io/hexpm/l/blue?style=flat-square) ![GitHub commit activity](https://img.shields.io/github/commit-activity/w/ToutatisGc/VOID?style=flat-square) ![GitHub last commit](https://img.shields.io/github/last-commit/ToutatisGc/VOID?style=flat-square)

**Author:Toutatis_Gc**

![banner](docs/assets/images/banner.jpg)

## Part.1 简介

VOID集成多种框架和工具，帮助您快速完成开发。

1. VOID帮助您创建以**Spring Boot/Vert.X**为基础驱动的生产级Web服务，它对对应框架平台采取了支持和增强的功能，以便新用户和现有用户可以快速搭建服务。
2. 您也可以使用**Spring Boot/Vert.X**作为原生平台创建独立的Java Web服务器或React响应式服务应用，也可以使用该包下所支持的增强功能。
3. 集成多种适应本框架的预制件，引入即用。减轻了依赖集成的选择和适配，增强对各中间件的适应性。
4. 提供多种工具类，相同风格的API使得其他应用开发变得更加轻松。

> ⚠️<font color='orange'>**注意：**</font>
>
> ​	**目前开发遵守MRU（最小可运行单元）概念，即最低使用原则，功能并不完善且有些功能和效率并不合理，但实现大于概念，在可运行的状态下不断完善。**

**我们的主要目标是:**

- 为所有Spring Boot/Vert.X平台开发支持更广泛的，并且可访问的入门服务。
- 对于原生Spring Boot/Vert.X平台对于Web请求和响应的标准化支持。
- 提供一系列非功能性的支持，这些特性是所有Web项目所共有的特性(例如: ORM,安全,指标,标准化等)。
- 大部分情况下拆箱即用，免去了部分yml,properties或者是XML的配置，增强开发体验。


## Part.2 环境支持<font color='red'>*</font>

- 框架全面拥抱<font color='red'>**Java17**</font>，抛弃Java8支持，如一些工具类（例如：void-toolkit）因旧项目支持了Java8,其余编译版本默认皆为Java17，如有需要可自行编译Java8版本，仅需修改部分Java17语法为Java8版本并重新编译。
- 框架全系支持<font color='blue'>**Kotlin[当前版本:1.9.20]**</font>，支持Java环境和Kotlin环境混合编译。将在未来版本中底层全面转移为Kotlin环境。

## Part.3 下载&安装

该文档为VOID框架概述文档，下载源码并查看子项目下README文件可了解子项目。

### Step.1 Git 克隆下载项目

```bash
git clone https://github.com/ToutatisGc/VOID.git
```

### Step.2 构建源代码

必要的环境为**void-feature-support**和**void-lib**模块，必须引入到本地。

```bash
# 使用Maven安装,切换到对应的目录安装到本地仓库。
clean install -DskipTests -Dgpg.skip=true
```

### Step.3 使用

**模块概述：**

| 模块名称                                       |            当前阶段             | 优先级 | 用途                                                         |
| :--------------------------------------------- | :-----------------------------: | :----: | :----------------------------------------------------------- |
| docs                                           |                -                |        | [文档资源]文档资源以及使用说明。                             |
| [void-feature-support](void-feature-support)   | <font color='green'>完成</font> |   ✅    | [[依赖支持](void-feature-support)]项目所需依赖和插件的版本控制。 |
| [void-lib](void-lib)                           | <font color='green'>迭代</font> |   🔁    | [[库支持](void-lib)]包含独立工具和项目依赖的具体实现。       |
| [void-hardware](void-hardware)                 | <font color='grey'>立项</font>  |   ⏹️    | [[硬件开发](void-hardware)]仅立项阶段。                      |
| [void-incubator](void-incubator)               | <font color='green'>迭代</font> |   🔁    | [[孵化器](void-incubator)]独立运行包，包含动态解析，项目生成器，图片压缩工具等。 |
| [void-front-end](void-front-end)               |             开发中              |   ⏬    | [[前端页面](void-front-end)]配合Web项目的前端开发页面。      |
| [void-spring-framework](void-spring-framework) |             开发中              |   ⏫    | [[春野VoidSpring支持](void-spring-framework)]SpringBoot集成支持。 |
| [void-vertx](void-vertx)                       |             开发中              |   ⏬    | [[Vert.X支持](void-vertx)]框架Vert.X集成支持。               |
| [void-tutorial](void-tutorial)                 | <font color='grey'>立项</font>  |   ⏹️    | [[教程示例](void-tutorial)]各种教程示例。                    |

#### 1. 可执行项目

可执行项目中为可独立运行的项目集，包含各种工具等。

 [❇️点击跳转至项目页面](void-incubator\README.md) 

------

#### 2. 库模块使用

Lib库中包含了依赖和代码集合，请参考框架库模块说明。

 [❇️点击跳转至项目页面](void-lib\README.md) 

------

#### 3. 春野SpringBoot使用

VOID框架深度融合了SpringBoot各项组件组成了VoidSpring春野框架，可以轻松开发Web应用。

 [❇️点击跳转至项目页面](void-spring-framework\README.md) 

------

#### 4. Vert.X使用

```
暂未支持
```

## Part.4 帮助

您在使用VOID框架遇到麻烦了吗?我们可以提供以下帮助:

- 请查阅参考文档，使用说明或者使用实例，它们为最常见的问题提供了解决方案。
- 学习Spring/Vert.X的知识，VOID支持建立在Java，Kotlin语言，Stream及响应式编程/Spring/Vert.X的基础上，如果碰到了 package 以非 cn.toutatis 域下的实例问题，请查阅相关官网以获取相关框架的帮助。
- 如果您正在升级，请查阅更新说明，了解新的特性以及功能变动。
- 在本项目的Git下的Issues发布问题，我们会第一时间查看并提供解决方案。

## Part.5 报告问题

VOID使用GitHub的Issues集成问题。如果您想提出问题,请遵循以下建议:

- 在您记录错误之前，请搜索问题，看看是否有人已经报告了该问题。
- 如果这个问题还不存在，那么创建一个新的问题。请在问题报告中提供尽可能详细的信息。我们想知道你使用的Spring Boot/Vert.x版本/VOID版本，操作系统和JVM版本。如果需要粘贴代码或包含堆栈跟踪，请使用Markdown格式上传。如果可能，尝试创建一个测试用例或项目来复制问题，并将其与问题联系起来。

## Part.6 设计规范

| 规范名称       | 文档说明                                                     |
| -------------- | ------------------------------------------------------------ |
| 前端设计文档   | 此文档包含颜色,字体,阴影,图标,请求等规范 [[参考文档]](docs/reference-docs/front-end-side/Index.md) |
| 后端设计文档   | 此文档包含结构树,语法表,字段说明等 [[参考文档]](docs/reference-docs/server-side/Index.md) |
| 数据库设计文档 | 此文档包括命名规则,语法糖等说明 [[参考文档]](docs/reference-docs/databases-side/Index.md) |

## Part.7 相关链接

| 外部链接                                                     |                                                  |                                                              |
| :----------------------------------------------------------- | :----------------------------------------------- | :----------------------------------------------------------- |
| [📂GitHub VOID框架支持库](https://github.com/ToutatisGc/VOID) | [📂Apache POI官方网站](https://poi.apache.org/)   | [📂Hibernate Validator官方网站](https://docs.jboss.org/hibernate/stable/validator/reference/en-US/html_single) |
| [📂Apache PDFBox官方网站](https://pdfbox.apache.org/)         | [📂Apache Tika官方网站](https://tika.apache.org/) |                                                              |
|                                                              |                                                  |                                                              |
|                                                              |                                                  |                                                              |
|                                                              |                                                  |                                                              |
|                                                              |                                                  |                                                              |
|                                                              |                                                  |                                                              |

## Part.8 许可证

VOID is Open Source software released under the [Apache 2.0 license](https://www.apache.org/licenses/LICENSE-2.0.html).