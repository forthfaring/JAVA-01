# 第八课

## 常用线程安全类型 

### 常见数据结构

![image-20210228220923318](image/image-20210228220923318.png)

为什么arraylist是线程不安全？多线程操作有什么问题？

如何解决arraylist线程不安全的问题？

1、读写没有并发要求的场景：vector/collections.syn..(collection)/Arrays.asList/Collections.unmodifiedCollection(c)

2、读多写少的场景：CopyOnWriteArrayList

### List线程安全的简单办法

- 1.ArrayList的方法都加上synchronized -> Vector
- 2.Collections.synchronizedList，强制将List的操作加上同步 
- 3.Arrays.asList，不允许添加删除，但是可以set替换元素
- 4.Collections.unmodifiableList，不允许修改内容，包括添加删除和set

### CopyOnWriteArrayList

核心改进原理: 

1、写加锁，保证不会写混乱
2、写在一个Copy副本上，而不是原始数据上 (GC young区用复制，old区用本区内的移动)
 读写分离 最终一致

### HashMap
基本特点:空间换时间，哈希冲突不大的情况下查找数据性能很高 

用途:存放指定key的对象，缓存对象 

原理:使用hash原理，存k-v数据，初始容量16，扩容x2，负载因子0.75 JDK8以后，在链表长度到8 & 数组长度到64时，使用红黑树。

安全问题:

1、写冲突， 2、读写问题，可能会死循环 3、keys()无序问题

### LinkedHashMap
基本特点:继承自HashMap，对Entry集合添加了一个双向链表 

用途:保证有序，特别是Java8 stream操作的toMap时使用 

原理:同LinkedList，包括插入顺序和访问顺序

安全问题: 同HashMap

> 线程安全和带过期时间的LRU：https://zhuanlan.zhihu.com/p/135936339

### ConcurrentHashMap-Java7分段锁

![image-20210228225459540](image/image-20210228225459540.png)

![image-20210228225519724](image/image-20210228225519724.png)

### ConcurrentHashMap-Java8

![image-20210228225924554](image/image-20210228225924554.png)



## 并发线程相关内容

### ThreadLocal

每个Thread都有自己的Map。

注意：用于默认传参，但是要注意及时清理。(PageHelper的坑)

### 并行Stream

```
list.stream().parallel()
```

### 伪并发问题

比如浏览器端，表单的重复提交问题。解决方案：
1、客户端控制(调用方)，**点击后按钮不可用**，跳转到其他页

2、服务器端控制(处理端)，给每个表单生成一个编号，提交时判断重复

### 分布式下的锁和计数器

- 分布式环境下，多个机器的操作，超出了线程的协作机制，一定是并行的 

- 例如某个任务只能由一个应用处理，部署了多个机器，怎么控制

- 例如针对用户的限流是每分钟60次计数，API服务器有3台，用户可能随机访问到任何 一台，怎么控制?(秒杀场景是不是很像?库存固定且有限。)

    分布式缓存会讲。

## 并发编程经验总结 

### 加锁需要考虑的问题

1. 粒度：锁的代码范围
2. 性能：平衡性能和线程安全。
3. 重入:一般都用可重入的
4. 公平：是否需要公平锁，一般用公平锁
5. 自旋锁(spinlock)：是否可以用CAS
6. 场景: 脱离业务场景谈性能都是耍流氓

### 线程间协作与通信

1. 线程间共享:
  • static/实例变量(堆内存)
  • Lock
  • synchronized

2. 线程间协作:
  • Thread#join()
  • Object#wait/notify/notifyAll 

  • Future/Callable
  • CountdownLatch
  • CyclicBarrier



## 并发编程常见面试题



