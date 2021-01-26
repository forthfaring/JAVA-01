## 6-2

### 1. 为什么需要多线程？

>关于为什么需要多线程，参考《java并发编程实战》第一章





#### java Thread与os Thead的启动交互过程

![image-20210125105455563](image/image-20210125105455563.png)

>关于java线程与os线程的关系和实现原理，具体需要参考《深入JVM虚拟机》的12.4章节



### 2. Java 多线程*  

#### Thread

创建、守护线程、sleep

#### Runnable

#### Thread的常用方法

* wait：

  释放当前的锁，直到其他线程调用notify或notifyAll。必须使用monitor的object调用，且在monitor内调用。

* wait(timeout):释放当前的锁，直到其他线程调用notify或notifyAll，或者达到time时间。必须使用monitor的object调用，且在monitor内调用。

* notify：通知1个线程。

* notifyAll:通知所有等待线程

> wait/notify详解: https://www.jianshu.com/p/b8df805825b5









