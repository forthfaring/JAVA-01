## 1 GC日志解读与分析

###  GCLogAnalysis.java 

启动参数，打印gc日志：

```
java -Xloggc:gc.demo.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis
```



```
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

/*
演示GC日志生成与解读
*/
public class GCLogAnalysis {
    private static Random random = new Random();

    public static void main(String[] args) {
        // 当前毫秒时间戳
        long startMillis = System.currentTimeMillis();
        // 持续运行毫秒数; 可根据需要进行修改   在1秒内不断生成对象
        long timeoutMillis = TimeUnit.SECONDS.toMillis(1);
        // 结束时间戳
        long endMillis = startMillis + timeoutMillis;
        LongAdder counter = new LongAdder();
        System.out.println("正在执行...");
        // 缓存一部分对象; 进入老年代
        int cacheSize = 2000;
        Object[] cachedGarbage = new Object[cacheSize];
        // 在此时间范围内,持续循环
        while (System.currentTimeMillis() < endMillis) {
            // 生成垃圾对象
            Object garbage = generateGarbage(100 * 1024);
            counter.increment();
            //生成的对象有50%的几率被放入数组，也就是被gcroots引用
            int randomIndex = random.nextInt(2 * cacheSize);
            if (randomIndex < cacheSize) {
                cachedGarbage[randomIndex] = garbage;
            }
        }
        System.out.println("执行结束!共生成对象次数:" + counter.longValue());
        System.out.println(cachedGarbage[0]);
        System.out.println(cachedGarbage[1]);
        System.out.println(cachedGarbage[2]);
        System.out.println(cachedGarbage[3]);
    }

    // 生成对象
    private static Object generateGarbage(int max) {
        int randomSize = random.nextInt(max);
        int type = randomSize % 4;
        Object result = null;
        switch (type) {
            case 0:
                result = new int[randomSize];
                break;
            case 1:
                result = new byte[randomSize];
                break;
            case 2:
                result = new double[randomSize];
                break;
            default:
                StringBuilder builder = new StringBuilder();
                String randomString = "randomString-Anything";
                while (builder.length() < randomSize) {
                    builder.append(randomString);
                    builder.append(max);
                    builder.append(randomSize);
                }
                result = builder.toString();
                break;
        }
        return result;
    }
}
```



GCLogAnalysis程序分析：

​	模拟业务系统，在1s内不断生成各种类型的对象（基本类型数组和字符串类型，长度都是10w）， 生成的对象有50%的几率被放入数组（模拟一半的对象是短生命周期对象，一半长生命周期，当然数组同一位置被覆盖的对象引用也是短生命周期），也就是被gcroots引用（生成对象的局部变量表引用garbage变量始终只指向当前生成的对象，则先前生成的对象如果没有被数组放入数组或在数组的位置被覆盖，就可以被gc）。

​	由于没有指定-XX:PretenureSizeThreshold ,默认都会在eden区分配空间。预计年轻代会不断增加，发生young gc时，会把survivor区达到岁数的对象（大部分是我们随机生成的但没有被放入数组的对象）移入old区。如果

​	

---

实验开始

#### -Xmx128m -Xms128m -XX:+PrintGCDetails -XX:+PrintGCDateStamps

```
正在执行...
2021-01-21T14:56:24.985+0800: [GC (Allocation Failure) [PSYoungGen: 33280K->5102K(38400K)] 33280K->12018K(125952K), 0.0100642 secs] [Times: user=0.02 sys=0.00, real=0.01 secs] 
2021-01-21T14:56:25.015+0800: [GC (Allocation Failure) [PSYoungGen: 38347K->5117K(38400K)] 45263K->21489K(125952K), 0.0089902 secs] [Times: user=0.06 sys=0.03, real=0.01 secs] 
2021-01-21T14:56:25.042+0800: [GC (Allocation Failure) [PSYoungGen: 38397K->5108K(38400K)] 54769K->30514K(125952K), 0.0045955 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2021-01-21T14:56:25.062+0800: [GC (Allocation Failure) [PSYoungGen: 38355K->5107K(38400K)] 63760K->43053K(125952K), 0.0066549 secs] [Times: user=0.03 sys=0.03, real=0.01 secs] 
2021-01-21T14:56:25.077+0800: [GC (Allocation Failure) [PSYoungGen: 38356K->5111K(38400K)] 76302K->55028K(125952K), 0.0060325 secs] [Times: user=0.05 sys=0.03, real=0.01 secs] 
2021-01-21T14:56:25.092+0800: [GC (Allocation Failure) [PSYoungGen: 38391K->5116K(19968K)] 88308K->66029K(107520K), 0.0052065 secs] [Times: user=0.05 sys=0.03, real=0.00 secs] 
2021-01-21T14:56:25.101+0800: [GC (Allocation Failure) [PSYoungGen: 19930K->8765K(29184K)] 80843K->70446K(116736K), 0.0038736 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2021-01-21T14:56:25.108+0800: [GC (Allocation Failure) [PSYoungGen: 23613K->11419K(29184K)] 85294K->75652K(116736K), 0.0031727 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2021-01-21T14:56:25.114+0800: [GC (Allocation Failure) [PSYoungGen: 26188K->12535K(29184K)] 90421K->78465K(116736K), 0.0037257 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2021-01-21T14:56:25.123+0800: [GC (Allocation Failure) [PSYoungGen: 27383K->8874K(29184K)] 93313K->84349K(116736K), 0.0046865 secs] [Times: user=0.05 sys=0.03, real=0.01 secs] 
2021-01-21T14:56:25.128+0800: [Full GC (Ergonomics) [PSYoungGen: 8874K->0K(29184K)] [ParOldGen: 75474K->76352K(87552K)] 84349K->76352K(116736K), [Metaspace: 3505K->3505K(1056768K)], 0.0241459 secs] [Times: user=0.06 sys=0.00, real=0.02 secs] 
2021-01-21T14:56:25.157+0800: [Full GC (Ergonomics) [PSYoungGen: 14697K->0K(29184K)] [ParOldGen: 76352K->81613K(87552K)] 91050K->81613K(116736K), [Metaspace: 3505K->3505K(1056768K)], 0.0346010 secs] [Times: user=0.06 sys=0.00, real=0.03 secs] 
2021-01-21T14:56:25.196+0800: [Full GC (Ergonomics) [PSYoungGen: 14848K->714K(29184K)] [ParOldGen: 81613K->86920K(87552K)] 96461K->87634K(116736K), [Metaspace: 3505K->3505K(1056768K)], 0.0326548 secs] [Times: user=0.09 sys=0.00, real=0.03 secs] 
2021-01-21T14:56:25.231+0800: [Full GC (Ergonomics) [PSYoungGen: 14440K->5525K(29184K)] [ParOldGen: 86920K->87414K(87552K)] 101361K->92939K(116736K), [Metaspace: 3505K->3505K(1056768K)], 0.0304657 secs] [Times: user=0.11 sys=0.00, real=0.03 secs] 
2021-01-21T14:56:25.264+0800: [Full GC (Ergonomics) [PSYoungGen: 14826K->9393K(29184K)] [ParOldGen: 87414K->86693K(87552K)] 102240K->96086K(116736K), [Metaspace: 3505K->3505K(1056768K)], 0.0134093 secs] [Times: user=0.09 sys=0.00, real=0.01 secs] 
2021-01-21T14:56:25.279+0800: [Full GC (Ergonomics) [PSYoungGen: 14788K->10216K(29184K)] [ParOldGen: 86693K->87470K(87552K)] 101482K->97686K(116736K), [Metaspace: 3505K->3505K(1056768K)], 0.0194987 secs] [Times: user=0.09 sys=0.00, real=0.02 secs] 
2021-01-21T14:56:25.300+0800: [Full GC (Ergonomics) [PSYoungGen: 14758K->11302K(29184K)] [ParOldGen: 87470K->87470K(87552K)] 102229K->98772K(116736K), [Metaspace: 3505K->3505K(1056768K)], 0.0027779 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2021-01-21T14:56:25.304+0800: [Full GC (Ergonomics) [PSYoungGen: 14806K->12626K(29184K)] [ParOldGen: 87470K->87089K(87552K)] 102276K->99716K(116736K), [Metaspace: 3505K->3505K(1056768K)], 0.0107065 secs] [Times: user=0.02 sys=0.00, real=0.01 secs] 
2021-01-21T14:56:25.315+0800: [Full GC (Ergonomics) [PSYoungGen: 14291K->12663K(29184K)] [ParOldGen: 87089K->87089K(87552K)] 101381K->99753K(116736K), [Metaspace: 3505K->3505K(1056768K)], 0.0025395 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2021-01-21T14:56:25.318+0800: [Full GC (Ergonomics) [PSYoungGen: 14793K->14084K(29184K)] [ParOldGen: 87089K->87062K(87552K)] 101883K->101146K(116736K), [Metaspace: 3505K->3505K(1056768K)], 0.0164102 secs] [Times: user=0.09 sys=0.00, real=0.02 secs] 
2021-01-21T14:56:25.335+0800: [Full GC (Ergonomics) [PSYoungGen: 14755K->13742K(29184K)] [ParOldGen: 87062K->87432K(87552K)] 101817K->101174K(116736K), [Metaspace: 3505K->3505K(1056768K)], 0.0086374 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
2021-01-21T14:56:25.344+0800: [Full GC (Ergonomics) [PSYoungGen: 14786K->14543K(29184K)] [ParOldGen: 87432K->86990K(87552K)] 102219K->101533K(116736K), [Metaspace: 3505K->3505K(1056768K)], 0.0215160 secs] [Times: user=0.11 sys=0.03, real=0.02 secs] 
2021-01-21T14:56:25.366+0800: [Full GC (Ergonomics) [PSYoungGen: 14627K->14399K(29184K)] [ParOldGen: 86990K->86990K(87552K)] 101618K->101389K(116736K), [Metaspace: 3505K->3505K(1056768K)], 0.0086801 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
2021-01-21T14:56:25.375+0800: [Full GC (Ergonomics) [PSYoungGen: 14777K->14735K(29184K)] [ParOldGen: 86990K->86990K(87552K)] 101767K->101725K(116736K), [Metaspace: 3505K->3505K(1056768K)], 0.0052565 secs] [Times: user=0.02 sys=0.00, real=0.01 secs] 
2021-01-21T14:56:25.381+0800: [Full GC (Ergonomics) [PSYoungGen: 14807K->14771K(29184K)] [ParOldGen: 86990K->86990K(87552K)] 101797K->101761K(116736K), [Metaspace: 3505K->3505K(1056768K)], 0.0034321 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2021-01-21T14:56:25.384+0800: [Full GC (Ergonomics) [PSYoungGen: 14800K->14699K(29184K)] [ParOldGen: 87422K->87278K(87552K)] 102223K->101977K(116736K), [Metaspace: 3505K->3505K(1056768K)], 0.0061628 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
2021-01-21T14:56:25.391+0800: [Full GC (Ergonomics) [PSYoungGen: 14845K->14845K(29184K)] [ParOldGen: 87278K->86977K(87552K)] 102124K->101823K(116736K), [Metaspace: 3505K->3505K(1056768K)], 0.0167367 secs] [Times: user=0.01 sys=0.00, real=0.02 secs] 
2021-01-21T14:56:25.408+0800: [Full GC (Ergonomics) [PSYoungGen: 14848K->14845K(29184K)] [ParOldGen: 87471K->86977K(87552K)] 102319K->101823K(116736K), [Metaspace: 3505K->3505K(1056768K)], 0.0034082 secs] [Times: user=0.05 sys=0.00, real=0.00 secs] 
2021-01-21T14:56:25.412+0800: [Full GC (Ergonomics) [PSYoungGen: 14845K->14845K(29184K)] [ParOldGen: 87360K->86977K(87552K)] 102206K->101823K(116736K), [Metaspace: 3505K->3505K(1056768K)], 0.0034429 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2021-01-21T14:56:25.416+0800: [Full GC (Ergonomics) [PSYoungGen: 14848K->14845K(29184K)] [ParOldGen: 87514K->87514K(87552K)] 102362K->102360K(116736K), [Metaspace: 3505K->3505K(1056768K)], 0.0074763 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
2021-01-21T14:56:25.424+0800: [Full GC (Allocation Failure) [PSYoungGen: 14845K->14845K(29184K)] [ParOldGen: 87514K->87488K(87552K)] 102360K->102334K(116736K), [Metaspace: 3505K->3505K(1056768K)], 0.0389934 secs] [Times: user=0.13 sys=0.00, real=0.04 secs] 
2021-01-21T14:56:25.465+0800: [Full GC (Ergonomics) [PSYoungGen: 14848K->0K(29184K)] [ParOldGen: 87503K->631K(87552K)] 102351K->631K(116736K), [Metaspace: 3530K->3530K(1056768K)], 0.0088513 secs] [Times: user=0.05 sys=0.00, real=0.01 secs] 
Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
	at GCLogAnalysis.generateGarbage(GCLogAnalysis.java:49)
	at GCLogAnalysis.main(GCLogAnalysis.java:26)
Heap
 PSYoungGen      total 29184K, used 307K [0x00000000fd580000, 0x0000000100000000, 0x0000000100000000)
  eden space 14848K, 2% used [0x00000000fd580000,0x00000000fd5ccde8,0x00000000fe400000)
  from space 14336K, 0% used [0x00000000ff200000,0x00000000ff200000,0x0000000100000000)
  to   space 14336K, 0% used [0x00000000fe400000,0x00000000fe400000,0x00000000ff200000)
 ParOldGen       total 87552K, used 631K [0x00000000f8000000, 0x00000000fd580000, 0x00000000fd580000)
  object space 87552K, 0% used [0x00000000f8000000,0x00000000f809dd10,0x00000000fd580000)
 Metaspace       used 3536K, capacity 4502K, committed 4864K, reserved 1056768K
  class space    used 388K, capacity 390K, committed 512K, reserved 1048576K

Process finished with exit code 1

```

日志解读：

```
2021-01-21T14:56:24.985+0800: [GC (Allocation Failure) [PSYoungGen: 33280K->5102K(38400K)] 33280K->12018K(125952K), 0.0100642 secs] [Times: user=0.02 sys=0.00, real=0.01 secs] 
```

**[GC (Allocation Failure)**：GC发生了原因是分配内存失败

**[PSYoungGen: 33280K->5102K(38400K)] 33280K->12018K(125952K), 0.0100642 secs]**：发生了younggc,使用的是并行垃圾收集器，young区内存回收结果和当前容量 33280K->5102K(38400K)。堆内存当前内存变化和容量。此次younggc时间 0.0100642 secs。

**[Times: user=0.02 sys=0.00, real=0.01 secs] **：用户进程的CPU占用时长、内核进程的CPU占用时长、实际运行时间。



>[user/sys/real的详细解释]: https://my.oschina.net/dabird/blog/714569
>
>简单概括：user：所有gc线程此次GC花费的总时间，sys是操作系统花费在这次GC上的CPU时间，real实际执行时间。因为user+sys对应的是多个线程的时间和，所以user+sys可能大于real。
>
>**例1：**
>
>```
>[Times: user=11.53 sys=1.38, real=1.03 secs]
>```
>
>​	在这个例子中，`user` + `sys` 时间的和比 `real` 时间要大，这主要是因为日志时间是从 JVM 中获得的，而这个 JVM 在多核的处理器上被配置了多个 GC 线程，由于多个线程并行地执行 GC，因此整个 GC 工作被这些线程共享，最终导致实际的时钟时间（real）小于总的 CPU 时间（user + sys）。
>
>**例2：**
>
>```
>[Times: user=0.09 sys=0.00, real=0.09 secs]
>```
>
>​	上面的例子中的 GC 时间是从 Serial 垃圾收集器 （串行垃圾收集器）中获得的。由于 Serial 垃圾收集器是使用单线程进行垃圾收集的，因此 `real` 时间等于 `user` 和 `sys` 时间之和。
>
>​	在做性能优化时，我们一般采用 `real` 时间来优化程序。因为最终用户只关心点击页面发出请求到页面上展示出内容所花的时间，也就是响应时间，而不关心你到底使用了多少个 GC 线程或者处理器。但并不是说 `sys` 和 `user` 两个时间不重要，当我们想通过增加 GC 线程或者 CPU 数量来减少 GC 停顿时间时，可以参考这两个时间。
>



### 2 JVM线程堆栈数据分析



### 3 内存分析与相关工具*



### 4 JVM 问题分析调优经验*



### 5 GC 疑难情况问题分析



### 6 JVM 常见面试问题汇总*  

