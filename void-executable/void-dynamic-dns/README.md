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

​	**Jar包位置和Java环境请自行指定。**

🐧Linux示例：

启动脚本示例**（start-linux.sh）**：

```bash
#!/bin/bash

nohup java -server -Xms16M -Xmx32M -XX:+UseParallelGC -jar void-ddns-fat-jar-with-dependencies.jar true simple-circle-dns.playbook > resolve.log &
```

关闭脚本示例**（close-linux.sh）**：

```bash
#!/bin/bash

PROCESS_NAME="void-ddns-fat-jar-with-dependencies.jar"
PID=$(pgrep -f "$PROCESS_NAME")

if [ -n "$PID" ]; then
    kill -9 "$PID"
    echo "PROCESS $PROCESS_NAME CLOSED."
else
    echo "NOT FOUND PROCESS $PROCESS_NAME."
fi
```

执行：

```bash
chmod +x <脚本>.sh
./<脚本>.sh
```

🪟Windows示例：

启动脚本**（start-windows.bat）**：

```bat
@echo off
start /B java -server -Xms16M -Xmx32M -XX:+UseParallelGC -jar void-ddns-fat-jar-with-dependencies.jar true simple-circle-dns.playbook >> ddns_info.log 2>&1
```

关闭脚本示例**（close-windows.bat）**：

```bat
@echo off
setlocal

set PROCESS_NAME=void-ddns-fat-jar-with-dependencies.jar

for /f "tokens=1" %%i in ('jps -l ^| find "%PROCESS_NAME%"') do (
    set PID=%%i
)

if defined PID (
    taskkill /F /PID %PID%
    echo PROCESS %PROCESS_NAME% CLOSED.
) else (
    echo NOT FOUND PROCESS %PROCESS_NAME%.
)
```

### 3. 创建文件目录

```
└─dynamic-dns
	├─ release(文件夹，第一次启动脚本后生成。)
	│   ├─ commands
	│   └─ playbook
	├─ void-ddns-fat-jar-with-dependencies.jar
	├─ start-windows.bat
	├─ close-windows.bat
	└─ ddns-info.log(日志文件，启动后生成。)
```

### 4. 启动并查看日志

```bat
D:\SOFT\ddns>start-windows.bat
# 目录下ddns-info.log日志内容
# [VOID-DYNAMIC-DNS]执行[自动执行解析域名到公有云]完毕于:2024-03-18 21:51:24,共计3个任务,预计将在2024-03-18 22:51:24执行下次任务.
```

## Part.3 配置说明:

### 📖程序实参说明：

​	运行Jar包或者程序集成时，可以指定一些参数：

| 索引<font color='red'>*</font> |    键    | 值类型  | 类型 | 默认值 |
| :----------------------------: | :------: | :-----: | ---- | :----: |
|               0                | runType  | Boolean | 通用 | false  |
|               1                | playbook | String  | 通用 |   无   |

> (索引<font color='red'>*</font>为main方法args实参索引)

### 📖config.properties配置说明：

​	首次运行后,会在运行目录释放**release**文件夹,请配置**config.properties**缺失配置。

| 键                           | 值                                                           | 默认值 |
| ---------------------------- | ------------------------------------------------------------ | ------ |
| Ali-Access-Key-Id            | 阿里云RAM访问Key<br />https://ram.console.aliyun.com/manage/ak | 无     |
| ALi-Access-Key-Secret        | Access-Key-Id对应Secret                                      | 无     |
| Resolve-Domain               | 解析域名(目前只支持单个域名)                                 | 无     |
| Request-Time-Out             | 请求URL超时时间                                              | 3      |
| Default-Choose-First-Address | 默认选择靶数最多的地址<br />因请求地址的原因个别网址所获取的IP地址不是真实IP地址所以加此判断,在多个地址获取地址一致时可不用修改 | true   |

## Part.4 剧本使用：

​	剧本一般使用为自动化运行。

📖 [simple-circle-dns.playbook] 默认示例：

```
{
    "NAME":"自动执行解析域名到公有云",
    "TIMER": {
        "INTERVAL": 60,
        "DELAY": 0,
    },
    "TASKS":[
        {
            "NAME":"解析公网IP到缓存",
            "COMMAND":"SCAN"
        },
        {
            "NAME":"获取DNS解析列表",
            "COMMAND":"DNS"
        },
        {
            "NAME":"解析动态IP到DNS云解析",
            "COMMAND":"UPDNS",
            "ARGS": "-s 0"
        }
    ]
}
```

📖键值说明：

| 键名           | 说明                           |
| -------------- | ------------------------------ |
| NAME           | 剧本名称，运行时显示名称。     |
| TIMER.INTERVAL | 定时器运行间隔。（单位：分钟） |
| TIMER.DELAY    | 定时器启动延时。（单位：毫秒） |
| TASKS数组      | 任务集合。                     |
| TASKS.NAME     | 任务名称，日志显示。           |
| TASKS.COMMAND  | 任务执行命令。                 |
| TASKS.ARGS     | 命令所需参数。                 |

**<font color='red'>注意！所有键值皆为英文大写。</font>**
