## 6-2

### 1. 为什么需要多线程？

>关于为什么需要多线程，参考《java并发编程实战》第一章

* 应用程序为单进程，如何充分利用多核CPU，让这个进程运行速度更快？
* CPU与内存、磁盘等多级缓存之有性能差距过大，利用多线程充分利用CPU资源。

* 单CPU如何让多个进程在人类看起来是在同时运行：多线程



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

wait方法所在的线程是消费者。notify方法所在的线程是生产者。构成了整个等待/通知模式。wait的线程进入等待队列，被notify之后回到阻塞队列，等待锁可获取时竞争获取锁。

#### join

join底层使用wait实现。

#### 线程的中断和异常处理

1、线程内部异常不溢出到父线程。

2、interrupt调用打断异常机制

3、如果是计算密集型的操作？

​		分段处理，每个片段检查一下状态，是不是要终止。  

### 3、线程安全

#### 线程安全问题发生的原因

同一个进程内的多个线程共享资源，对资源的访问顺序敏感，存在竞态条件。

#### 并发性质

* 原子性

  个人理解：除了CPU原子性指令之外，synchronized等锁保证的原子性可以理解为一组操作的中间过程的数据不会被其他线程插入篡改。

* 可见性

  在CPU缓存存在的前提下，主存对线程执行的可见性。

* 有序性

  过程中指令的有序性。

#### happen-before原则

1. 程序次序规则：一个线程内，按照代码先后顺序
2. 锁定规则：一个 unLock 操作先行发生于后面对同一个锁的 lock 操作
3. Volatile 变量规则：对一个变量的写操作先行发生于后面对这个变量的读操作
4. 传递规则：如果操作 A 先行发生于操作 B，而操作 B 又先行发生于操作 C，则可以得出 A 先于 C
5. 线程启动规则：Thread 对象的 start() 方法先行发生于此线程的每一个动作
6. 线程中断规则：对线程 interrupt() 方法的调用先行发生于被中断线程的代码检测到中断事件的发生
7. 线程终结规则：线程中所有的操作都先行发生于线程的终止检测，我们可以通过 Thread.join() 方法结束、
   Thread.isAlive() 的返回值手段检测到线程已经终止执行
8. 对象终结规则：一个对象的初始化完成先行发生于他的 finalize() 方法的开始  

#### final 

对于成员变量内存可见性的影响：构造函数结束返回后，final域最新的值保证对其他线程可见。

### 4、线程池的原理与应用

#### 为什么需要线程池？

多线程带来的问题：1、线程过多时上下文切换开销可能很大，需要控制线程数。线程之间需要协作、统一管理，所以通过线程池控制数量和管理运行策略。2、线程创建和销毁较为耗资源，所以用线程池减少这一过程的开销。3、将线程中的业务逻辑与线程调度逻辑分离，职责分明，让程序员专注业务处理。



#### 线程池

![image-20210201143520976](image/image-20210201143520976.png)

* Executor  执行者-顶层接口

  提供执行任务无返回值的接口。

* ExcutorService: 接口 API

  提供线程池的关闭、有返回值的任务处理接口

* ThreadFactory  ：线程工厂

* Executors: 线程池工具类


#### 线程池核心类 ThreadPoolExecutor

* 核心参数

  * 核心线程数

  * 最大线程数

  * 缓冲队列

    * ArrayBlockingQueue   规定大小的队列。数组实现
    * LinkedBlockingQueue   规定大小的队列。链表实现。
    * PriorityBlockingQueue 无界队列。数组实现。不是FIFO，有优先级
    * SynchronousQueue  同步队列。入队和出队有同步锁

  * 拒绝策略

    * AbortPolicy   丢弃任务并抛出 RejectedExecutionException
      异常  
    * DiscardPolicy：丢弃任务，但是不抛出异常。  
    * DiscardOldestPolicy：丢弃队列最前面的任务，然后重新提
      交被拒绝的任务  
    * CallerRunsPolicy：由调用线程（提交任务的线程）处理该任
      务  

  * 线程回收时间

  * ThreadFactory 

    * 重写newThread方法，给线程设置统一的名字和其他属性。

    ![image-20210201161409027](image/image-20210201161409027.png)

