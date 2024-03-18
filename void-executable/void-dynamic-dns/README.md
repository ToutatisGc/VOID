# DYNAMIC-DNS 动态IP解析

<font>**GitHub:[CN-XVOID] Author: Toutatis_Gc**</font>

![image-20240318114935097](./README.assets/image-20240318114935097.png)

------

## Part.1 简介:

​	宽带运营商如(电信/联通等)个人可以申请动态公网IP,此公网IP会随着路由器等接入设备的重启或运营商固定时间随机分配地址,此模块的作用是定时向外网获取请求IP并解析到公有云的DNS解析地址中。



📖使用步骤：

1. 阿里云[📂官网地址](https://www.aliyun.com/activity/new?userCode=6g4ylfua)购买域名

2. 购买的域名中添加解析记录并备注**[\_CROSS\_]**（不包含方括号和转义符），如下图所示:
    ![DNS解析示例](./README.assets/image-20240318140445187.png)

3. 启动程序，并配置参数文件**[config.properties]**，首次启动会在运行目录中解压release文件夹，配置后重新启动。

4. help获取帮助或查看本文档详细使用。

    

📖Maven依赖：

```xml
<dependency>
    <groupId>cn.toutatis</groupId>
    <artifactId>void-dynamic-dns</artifactId>
    <version>0.0.0-ALPHA</version>
</dependency>
```

## Part.2 使用说明:

### 1. 编译

1. maven编译Jar包（程序集成可跳过此步骤，可直接引入依赖）

```basic
mvn clean compile package
```

### 2. 编写启动脚本

​	**Jar包位置请自行指定。**

🐧Linux示例**（start-linux.sh）**:

```bash
#!/bin/bash
nohup java -server -Xms16M -Xmx32M -XX:+UseParallelGC -jar void-dynamic-dns-fat.jar true > resolve.log &
```

执行：

```bash
chmod +x <脚本>.sh
./<脚本>.sh
```

🪟Windows示例**（start-windows.bat）**:

```bat
@echo off
start /B java -server -Xms16M -Xmx32M -XX:+UseParallelGC -jar void-dynamic-dns-fat.jar true > resolve.log
```

### 3.执行成功后ping解析地址并查看是否成功



## Part.3 配置说明:

​	首次运行后,会在运行目录释放**release**文件夹,请配置**config.properties**缺失配置。

| 键                           | 值                                                           | 默认值 |
| ---------------------------- | ------------------------------------------------------------ | ------ |
| Ali-Access-Key-Id            | 阿里云RAM访问Key<br />https://ram.console.aliyun.com/manage/ak | 无     |
| ALi-Access-Key-Secret        | Access-Key-Id对应Secret                                      | 无     |
| Resolve-Domain               | 解析域名(目前只支持单个域名)                                 | 无     |
| Request-Time-Out             | 请求URL超时时间                                              | 3      |
| Default-Choose-First-Address | 默认选择靶数最多的地址<br />因请求地址的原因个别网址所获取的IP地址不是真实IP地址所以加此判断,在多个地址获取地址一致时可不用修改 | true   |

## Part.4 剧本使用：

