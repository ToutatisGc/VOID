认证流程

1.开启预检的情况下,先通过SecurityAuthController.java预检用户名接口

2.进入UsernamePasswordAuthenticationJsonFilter分配参数

3.根据Security所配置的服务类进入认证阶段

4.进入VoidSecurityAuthenticationService

5.认证成功或者失败都将进入SecurityHandler

下次一定要坚决抛弃security...太多黑盒状态了,security的本质就是一套访问过滤器链,可以仿照security写一套,但是不能直接用了,需要适配的代码太多了

不要使用request.getRequestSessionId,似乎和spring session或者是security集成有问题,在系统初始化时第一次请求使用该方法会获得不一致的session