# VOID-TOOLKIT 工具集

**Author:Toutatis_Gc**

![banner](../../../docs/assets/images/banner.jpg)

## Part.1 简介：

​	该项目旨在汇集常用的实用工具类，以便快速开发功能，降低重复编写方法的频率。在快节奏的软件工程世界中，效率和生产力至关重要。通过将经常使用的工具和功能集中到一个统一的库中，该项目旨在简化开发流程，促进代码的可重用性、可维护性，并最终加速软件开发的速度。

​	通过提供一套健壮的实用工具类，开发人员可以利用预先存在的解决方案，而不是为常见任务重新造轮子。这不仅节省了时间和精力，还促进了项目间的一致性。无论是处理数据结构、执行数学运算、操作字符串还是管理文件操作，该项目致力于提供全面的工具套件，以满足各种开发需求。

​	此外，通过认真的文档编写和遵循最佳实践，该项目旨在成为所有级别开发人员的可靠资源，促进无缝集成，并赋予开发人员专注于应用程序核心逻辑的能力，而不是被重复的实现细节所困扰。随着软件开发的不断演进，该项目致力于调整和扩展其工具集，以满足现代开发工作流的不断增长的需求。

## Part.2 模块：

| 序号 | Package包  | 说明                                                       |
| :--: | :--------: | :--------------------------------------------------------- |
|  1   |   clazz    | 通常是对类相关的操作，例如获取类的信息，反射，注解相关等。 |
|  2   |  constant  | 常量包，包含常见使用和共识内容。                           |
|  3   |    data    | 数据操作相关工具集。                                       |
|  4   |   digest   | 加密相关工具集。                                           |
|  5   |    file    | 文件操作相关工具集。                                       |
|  6   | formatting | 格式化相关。                                               |
|  7   |    http    | HTTP请求相关。                                             |
|  8   |   locale   | 语言本地化支持。                                           |
|  9   | validator  | 校验相关。                                                 |

## Part.3 详细说明：

### 9. 校验 Validator

#### 📚词汇集合 XvoidWords

​	其中实现了一些词汇相关的二次包装，方便拆箱使用。



##### 📖获取内置敏感词过滤器 getBuiltInSensitiveWordFilter

​	内置敏感词过滤器使用toolkit包中的内置敏感词字典，包含80000+词汇。

```java
SensitiveWordFilter swf = XvoidWords.getBuiltInSensitiveWordFilter();
```



#### 📚 敏感词过滤器 SensitiveWordFilter

​	构建一个空的敏感词过滤器。

```java
SensitiveWordFilter swf = new SensitiveWordFilter();
```



##### 📖添加敏感词 addSearchWord

​	添加词汇到敏感词过滤器的词库中。

**传入参数：**

| 序号 | 参数 | 说明     |
| ---- | ---- | -------- |
| 0    | word | 敏感词。 |

**示例代码：**

```java
sensitiveWordFilter.addSearchWord("敏感词");
```



##### 📖搜索敏感词 search

​	从字符串中搜索敏感词集合。

**传入参数：**

| 序号 | 类型   | 参数 | 说明             |
| ---- | ------ | ---- | ---------------- |
| 0    | String | text | 需要搜索的文本。 |

**返回值：**

<div style='border:2px solid green;border-radius:4px;background-color:white'>
    <div style='text-align:center;font-weight:bold;background-color:green'>
        <p style='color:white'>词汇查找结果 WordSearchResult</p>
    </div>
    <div>
        <table style='text-align:center;color:black;border:1px solid green'>
          <caption style='font-weight:bolder'>实体字段</caption>
          <thead>
            <tr><th>序号</th><th>类型</th><th>字段</th><th>说明</th></tr>
          </thead>
          <tbody>
            <tr>
                <td>0</td>
                <td>String</td>
                <td>word</td>
                <td>词汇</td>
            </tr>
            <tr>
              	<td>1</td>
                <td>List&lt;Integer&gt;</td>
                <td>position</td>
                <td>词汇位置</td>
            </tr>
          </tbody>
        </table>
        <table style='text-align:center;color:black;border:1px solid green'>
          <caption style='font-weight:bolder'>可调用方法</caption>
          <thead>
            <tr><th>返回类型</th><th>方法</th><th>说明</th></tr>
          </thead>
          <tbody>
            <tr>
                <td>-</td>
                <td>字段Getter</td>
                <td>获取字段赋值</td>
            </tr>
            <tr>
              	<td>int</td>
                <td>getFrequency</td>
                <td>获取词频</td>
            </tr>
          </tbody>
        </table>
    </div>
</div>

**示例代码：**

```java
 List<WordSearchResult> wsr = sensitiveWordFilter.search("一大段包含敏感词字符串");
```



##### 📖排除敏感词 exclude

​	从搜索结果移除排除词汇。

**传入参数：**

| 序号 | 类型                                   | 参数  | 说明       |
| ---- | -------------------------------------- | ----- | ---------- |
| 0    | <b style='color:red'>vararg</b> String | words | 排除词汇。 |

**示例代码：**

```java
sensitiveWordFilter.exclude("敏感词1","敏感词2","敏感词3");
```

