homework

1、（选做）实现简单的Protocol Buffer/Thrift/gRPC(选任一个)远程调用demo。

2、（选做）实现简单的WebService-Axis2/CXF远程调用demo。

3、（必做）改造自定义RPC的程序，提交到github：
1）尝试将服务端写死查找接口实现类变成泛型和反射
2）尝试将客户端动态代理改成AOP，添加异常处理
3）尝试使用Netty+HTTP作为client端传输方式



4、（选做☆☆）升级自定义RPC的程序：
1）尝试使用压测并分析优化RPC性能
2）尝试使用Netty+TCP作为两端传输方式
3）尝试自定义二进制序列化
4）尝试压测改进后的RPC并分析优化，有问题欢迎群里讨论
5）尝试将fastjson改成xstream
6）尝试使用字节码生成方式代替服务端反射  