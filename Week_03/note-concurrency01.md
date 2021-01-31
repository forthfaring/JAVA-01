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



* interrupt

  ​	interrupt方法并不是用来中断线程，而是告知线程该中断了。具体是继续运行还是中断，需要由线程自己决定。

  1.一个线程不应该被其他线程真正的中断

  2.Thread.interrupt()的作用不是中断某个线程，而是通知一个线程应该中断了。

  3.被通知中断的线程具体是中断还是选择继续运行由该线程自己决定。

  4.具体来说，当对一个线程调用interrupt()方法时，

  1）当线程处于sleep()、wait()、join()等状态时，该线程将立即退出阻塞状态，然后抛出一个InterruptedException异常

  2）当线程处于正常状态时，该线程的中断标志位将被置为true，仅此而已。线程将继续正常运行。

  5.interrupt()方法并不会中断线程，需要线程本身配合才行。

  也就是说，一个线程如果有中断的需求，可以这样做：

  1）在运行时，经常检查自己的中断标志位，如果被设置了中断标志，就自动停止运行

  2）在调用阻塞方法[sleep()、wait()、join()]时，正确处理InterruptedException异常（如catch异常后，主动退出）

  6.Thread.isInterrupted()的作用是判断是否被中断。

  7.Thread.interrupted()的作用是判断是否被中断，并清除标志位。目的是下次继续检测标志位。如果一个线程被设置中断标志后，选择结束线程那么自然不存在下次的问题，而如果一个线程被设置中断标识后，进行了一些处理后选择继续进行任务，而且这个任务也是需要被中断的，那么当然需要清除标志位。

  标准用法：

  ```
  Thread thread = new Thread(() -> {
      while (!Thread.interrupted()) {
          // do more work.
      }
  });
  thread.start();
  
  // 一段时间以后
  thread.interrupt();
  ```

  

#### volatile

作用：强制线程总是从共享内存中获取变量值

字节码层面：ACC_VOLATILE 成员变量修饰符



#### synchronized

作用：保证synchronized修饰的代码块同一时刻只有一个线程在执行

字节码层面：monitorenter、monitorexit、ACC_SYNCHRONIZED（方法修饰符）

#### wait/notify/notifyAll

wait方法所在的线程是消费者。notify方法所在的线程是生产者。构成了整个等待/通知模式。







