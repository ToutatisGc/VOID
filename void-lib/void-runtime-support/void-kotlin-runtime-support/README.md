如果子模块使用kotlin编写,应当在POM文件中引入该模块.

如果引入在parent标签中,build标签下应当配置为

`  
<plugins>
    <plugin>
            <groupId>org.jetbrains.kotlin</groupId>
            <artifactId>kotlin-maven-plugin</artifactId>
    <plugin>
<plugins>
`

如果作为dependency引入,应当在build标签下配置为完整的kotlin插件配置
可以参考该模块下的**kotlin-maven-plugin插件配置**
