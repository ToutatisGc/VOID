## [VOID-IP-RESOLVE]使用说明

##### Author: Toutatis_Gc

------

### 1.简介:

​		宽带运营商如(电信/联通等)可以申请动态公网IP,此公网IP会随着路由器等接入设备的重启或运营商固定时间随机分配地址,此模块的作用是定时向外网获取请求IP并解析到地址池中



------

### 2.使用说明:

1.maven编译jar包void-ip-resolve

```
mvn clean compile package
```

2.编写脚本(该范例是linux centos)

```
#!/bin/bash
java -server -Xms16M -Xmx32M -XX:+UseParallelGC -jar void_ip_resolve-jar-with-dependencies.jar true & 
```

3.将jar文件和脚本放进同一文件夹并运行脚本

```
chmod +x <脚本>.sh
./<脚本>.sh
```

4.ping 解析地址并查看是否ping通



#### 额外说明:

​		首次运行后,会在运行目录释放release文件夹,请配置config.properties缺失配置

##### 配置说明:

| 键                           | 值                                                           | 默认值 |
| ---------------------------- | ------------------------------------------------------------ | ------ |
| Ali-Access-Key-Id            | 阿里云RAM访问Key<br />https://ram.console.aliyun.com/manage/ak | 无     |
| ALi-Access-Key-Secret        | Access-Key-Id对应Secret                                      | 无     |
| Resolve-Domain               | 解析域名(目前只支持单个域名)                                 | 无     |
| Request-Time-Out             | 请求URL超时时间                                              | 3      |
| Default-Choose-First-Address | 默认选择靶数最多的地址<br />因请求地址的原因个别网址所获取的IP地址不是真实IP地址所以加此判断,在多个地址获取地址一致时可不用修改 | true   |
| Circles-Time                 | 定时循环时间间隔(单位:分钟)                                  | 120    |

