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

该项目旨在通过提供一组实用类、扩展和与其他框架的集成，简化和增强 Kotlin 应用程序的开发。
该库旨在轻松集成到 Kotlin 项目中，为使用 Kotlin 进行开发的开发人员提供各种实用工具和改进。
请注意，该项目是 Void Feature Support 生态系统的一部分，该生态系统提供了一套全面的库和工具，以增强基于 Kotlin 的应用程序。
