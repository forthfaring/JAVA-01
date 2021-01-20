## 第二次课

### 1、本机使用 G1 GC 启动一个程序， 仿照课上案例分析一下 JVM 情况
可以使用gateway-server-0.0.1-SNAPSHOT.jar
注意关闭自适应参数： -XX:-UseAdaptiveSizePolicy

```
java -Xmx1g -Xms1g -XX:-UseAdaptiveSizePolicy -XX:+UseSerialGC -jar target/gateway-server-0.0.1-SNAPSHOT.jar

java -Xmx1g -Xms1g -XX:-UseAdaptiveSizePolicy -XX:+UseParallelGC -jar target/gateway-server-0.0.1-SNAPSHOT.jar

java -Xmx1g -Xms1g -XX:-UseAdaptiveSizePolicy -XX:+UseConcMarkSweepGC -jar
target/gateway-server-0.0.1-SNAPSHOT.jar

java -Xmx1g -Xms1g -XX:-UseAdaptiveSizePolicy -XX:+UseG1GC -XX:MaxGCPauseMillis=50 -jar
target/gateway-server-0.0.1-SNAPSHOT.jar
```

使用jmap， jstat， jstack， 以及可视化工具， 查看jvm情况。
mac上可以用wrk， windows上可以按照superbenchmark压测 http://localhost:8088/api/hello 查看jvm



#### 1、 使用串行GC：java -Xms1g -Xmx1g -XX:+UseSerialGC -jar gateway-server-0.0.1-SNAPSHOT.jar

* 使用 ab压测 100个并发，10W次请求

```
ab -c 100 -n 100000  http://localhost:8088/api/hello
```

```
This is ApacheBench, Version 2.3 <$Revision: 1879490 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking localhost (be patient)
Completed 10000 requests
Completed 20000 requests
Completed 30000 requests
Completed 40000 requests
Completed 50000 requests
Completed 60000 requests
Completed 70000 requests
Completed 80000 requests
Completed 90000 requests
Completed 100000 requests
Finished 100000 requests


Server Software:
Server Hostname:        localhost
Server Port:            8088

Document Path:          /api/hello
Document Length:        11 bytes

Concurrency Level:      100
Time taken for tests:   15.361 seconds
Complete requests:      100000
Failed requests:        0
Total transferred:      14400000 bytes
HTML transferred:       1100000 bytes
Requests per second:    6510.18 [#/sec] (mean)
Time per request:       15.361 [ms] (mean)
Time per request:       0.154 [ms] (mean, across all concurrent requests)
Transfer rate:          915.49 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0    0   0.3      0       2
Processing:     3   15   1.7     15      25
Waiting:        1   11   2.8     11      23
Total:          3   15   1.7     15      25

Percentage of the requests served within a certain time (ms)
  50%     15
  66%     15
  75%     16
  80%     16
  90%     18
  95%     19
  98%     20
  99%     21
 100%     25 (longest request)
```



* jmap -heap 116604

```
C:\Users\wfy>jmap -heap 116604
Attaching to process ID 116604, please wait...
Debugger attached successfully.
Server compiler detected.
JVM version is 25.181-b13

using thread-local object allocation.
Mark Sweep Compact GC

Heap Configuration:
   MinHeapFreeRatio         = 40
   MaxHeapFreeRatio         = 70
   MaxHeapSize              = 536870912 (512.0MB)
   NewSize                  = 178913280 (170.625MB)
   MaxNewSize               = 178913280 (170.625MB)
   OldSize                  = 357957632 (341.375MB)
   NewRatio                 = 2
   SurvivorRatio            = 8
   MetaspaceSize            = 21807104 (20.796875MB)
   CompressedClassSpaceSize = 1073741824 (1024.0MB)
   MaxMetaspaceSize         = 17592186044415 MB
   G1HeapRegionSize         = 0 (0.0MB)

Heap Usage:
New Generation (Eden + 1 Survivor Space):
   capacity = 161021952 (153.5625MB)
   used     = 83576424 (79.7046890258789MB)
   free     = 77445528 (73.8578109741211MB)
   51.90374539739774% used
Eden Space:
   capacity = 143130624 (136.5MB)
   used     = 83535936 (79.66607666015625MB)
   free     = 59594688 (56.83392333984375MB)
   58.36342612465659% used
From Space:
   capacity = 17891328 (17.0625MB)
   used     = 40488 (0.03861236572265625MB)
   free     = 17850840 (17.023887634277344MB)
   0.22629957932692307% used
To Space:
   capacity = 17891328 (17.0625MB)
   used     = 0 (0.0MB)
   free     = 17891328 (17.0625MB)
   0.0% used
tenured generation:
   capacity = 357957632 (341.375MB)
   used     = 29727368 (28.35022735595703MB)
   free     = 328230264 (313.02477264404297MB)
   8.30471691130195% used

17332 interned Strings occupying 2248112 bytes.
```

可以看出eden:from = 2:1，young:old = 1:2。

* jstat -gc 116604 1000

```
 S0C    S1C    S0U    S1U      EC       EU        OC         OU       MC     MU    CCSC   CCSU   YGC     YGCT    FGC    FGCT     GCT
17472.0 17472.0  0.0    0.0   139776.0  8212.8   349568.0   25267.9   35496.0 33936.1 4656.0 4361.5      3    0.058   2      0.136    0.195
17472.0 17472.0  0.0    0.0   139776.0  8678.8   349568.0   25267.9   35496.0 33936.1 4656.0 4361.5      3    0.058   2      0.136    0.195
17472.0 17472.0  0.0    0.0   139776.0  8678.8   349568.0   25267.9   35496.0 33936.1 4656.0 4361.5      3    0.058   2      0.136    0.195
17472.0 17472.0  0.0    0.0   139776.0  8678.9   349568.0   25267.9   35496.0 33936.1 4656.0 4361.5      3    0.058   2      0.136    0.195
17472.0 17472.0  0.0    0.0   139776.0  8679.0   349568.0   25267.9   35496.0 33936.1 4656.0 4361.5      3    0.058   2      0.136    0.195
17472.0 17472.0 3804.5  0.0   139776.0 24215.2   349568.0   25267.9   38192.0 36461.1 4912.0 4537.0     16    0.135   2      0.136    0.271
17472.0 17472.0  0.0   3801.8 139776.0 62506.6   349568.0   25267.9   38192.0 36461.1 4912.0 4537.0     17    0.140   2      0.136    0.276
17472.0 17472.0 3801.5  0.0   139776.0 114444.5  349568.0   25267.9   38192.0 36461.1 4912.0 4537.0     18    0.144   2      0.136    0.281
17472.0 17472.0  78.8   0.0   139776.0 25276.3   349568.0   28992.6   38192.0 36461.1 4912.0 4537.0     20    0.153   2      0.136    0.289
17472.0 17472.0  0.0    67.5  139776.0 62843.0   349568.0   29001.0   38192.0 36461.4 4912.0 4537.0     21    0.155   2      0.136    0.292

```



#### 2、使用g1gc：java -Xms512m -Xmx512m -XX:+UseG1GC -jar gateway-server-0.0.1-SNAPSHOT.jar

```
C:\Users\wfy>jmap -heap 89828
Attaching to process ID 89828, please wait...
Debugger attached successfully.
Server compiler detected.
JVM version is 25.181-b13

using thread-local object allocation.
Garbage-First (G1) GC with 8 thread(s)

Heap Configuration:
   MinHeapFreeRatio         = 40
   MaxHeapFreeRatio         = 70
   MaxHeapSize              = 536870912 (512.0MB)
   NewSize                  = 1363144 (1.2999954223632812MB)
   MaxNewSize               = 321912832 (307.0MB)
   OldSize                  = 5452592 (5.1999969482421875MB)
   NewRatio                 = 2
   SurvivorRatio            = 8
   MetaspaceSize            = 21807104 (20.796875MB)
   CompressedClassSpaceSize = 1073741824 (1024.0MB)
   MaxMetaspaceSize         = 17592186044415 MB
   G1HeapRegionSize         = 1048576 (1.0MB)

Heap Usage:
G1 Heap:
   regions  = 512
   capacity = 536870912 (512.0MB)
   used     = 279445496 (266.49999237060547MB)
   free     = 257425416 (245.50000762939453MB)
   52.05077975988388% used
G1 Young Generation:
Eden Space:
   regions  = 244
   capacity = 314572800 (300.0MB)
   used     = 255852544 (244.0MB)
   free     = 58720256 (56.0MB)
   81.33333333333333% used
Survivor Space:
   regions  = 23
   capacity = 24117248 (23.0MB)
   used     = 24117248 (23.0MB)
   free     = 0 (0.0MB)
   100.0% used
G1 Old Generation:
   regions  = 0
   capacity = 198180864 (189.0MB)
   used     = 0 (0.0MB)
   free     = 198180864 (189.0MB)
   0.0% used

17762 interned Strings occupying 2322928 bytes.
```



```
 S0C    S1C    S0U    S1U      EC       EU        OC         OU       MC     MU    CCSC   CCSU   YGC     YGCT    FGC    FGCT     GCT
 0.0   25600.0  0.0   25600.0 305152.0 114688.0  193536.0     0.0     33584.0 31972.5 4400.0 4101.7      4    0.045   0      0.000    0.045
 0.0   25600.0  0.0   25600.0 305152.0 114688.0  193536.0     0.0     33584.0 31972.5 4400.0 4101.7      4    0.045   0      0.000    0.045
 0.0   25600.0  0.0   25600.0 305152.0 114688.0  193536.0     0.0     33584.0 31972.5 4400.0 4101.7      4    0.045   0      0.000    0.045
 0.0   25600.0  0.0   25600.0 305152.0 114688.0  193536.0     0.0     33584.0 31972.5 4400.0 4101.7      4    0.045   0      0.000    0.045
 0.0   25600.0  0.0   25600.0 305152.0 114688.0  193536.0     0.0     33584.0 31972.5 4400.0 4101.7      4    0.045   0      0.000    0.045
 0.0   21504.0  0.0   21504.0 309248.0 37888.0   193536.0     0.0     36176.0 34350.8 4784.0 4410.8      5    0.060   0      0.000    0.060
 0.0   21504.0  0.0   21504.0 309248.0 93184.0   193536.0     0.0     36176.0 34350.8 4784.0 4410.8      5    0.060   0      0.000    0.060
 0.0   21504.0  0.0   21504.0 309248.0 216064.0  193536.0     0.0     36176.0 34350.8 4784.0 4410.8      5    0.060   0      0.000    0.060
 0.0   22528.0  0.0   22528.0 308224.0 35840.0   193536.0     0.0     38192.0 36448.5 4912.0 4527.3      6    0.077   0      0.000    0.077
 0.0   22528.0  0.0   22528.0 308224.0 142336.0  193536.0     0.0     38192.0 36448.5 4912.0 4527.3      6    0.077   0      0.000    0.077
 0.0   22528.0  0.0   22528.0 308224.0 262144.0  193536.0     0.0     38192.0 36448.5 4912.0 4527.3      6    0.077   0      0.000    0.077
 0.0   22528.0  0.0   22528.0 308224.0 128000.0  193536.0     0.0     38192.0 36469.5 4912.0 4532.7      7    0.093   0      0.000    0.093
 0.0   22528.0  0.0   22528.0 308224.0 291840.0  193536.0     0.0     38192.0 36469.5 4912.0 4532.7      8    0.093   0      0.000    0.093
 0.0   23552.0  0.0   23552.0 307200.0 181248.0  193536.0     0.0     38192.0 36486.8 4912.0 4535.7      8    0.107   0      0.000    0.107
 0.0   23552.0  0.0   23552.0 307200.0 71680.0   193536.0     0.0     38192.0 36486.8 4912.0 4535.7      9    0.122   0      0.000    0.122
 0.0   23552.0  0.0   23552.0 307200.0 250880.0  193536.0     0.0     38192.0 36486.8 4912.0 4535.7      9    0.122   0      0.000    0.122
 0.0   23552.0  0.0   23552.0 307200.0 142336.0  193536.0     0.0     38192.0 36490.6 4912.0 4535.7     10    0.136   0      0.000    0.136
 0.0   22528.0  0.0   22528.0 308224.0 33792.0   193536.0     0.0     38192.0 36490.6 4912.0 4535.7     11    0.151   0      0.000    0.151
 0.0   22528.0  0.0   22528.0 308224.0 211968.0  193536.0     0.0     38192.0 36490.6 4912.0 4535.7     11    0.151   0      0.000    0.151
 0.0   22528.0  0.0   22528.0 308224.0 100352.0  193536.0     0.0     38192.0 36495.5 4912.0 4535.7     12    0.168   0      0.000    0.168
 0.0   22528.0  0.0   22528.0 308224.0 282624.0  193536.0     0.0     38192.0 36495.5 4912.0 4535.7     12    0.168   0      0.000    0.168
 0.0   21504.0  0.0   21504.0 309248.0 175104.0  193536.0     0.0     38192.0 36495.5 4912.0 4535.7     13    0.184   0      0.000    0.184
 0.0   23552.0  0.0   23552.0 307200.0 65536.0   193536.0     0.0     38192.0 36495.8 4912.0 4535.7     14    0.199   0      0.000    0.199
 0.0   23552.0  0.0   23552.0 307200.0 247808.0  193536.0     0.0     38192.0 36495.8 4912.0 4535.7     14    0.199   0      0.000    0.199
 0.0   23552.0  0.0   23552.0 307200.0 248832.0  193536.0     0.0     38192.0 36495.8 4912.0 4535.7     14    0.199   0      0.000    0.199
 0.0   23552.0  0.0   23552.0 307200.0 248832.0  193536.0     0.0     38192.0 36495.8 4912.0 4535.7     14    0.199   0      0.000    0.199
```



#### 3、 并行gc java -Xms512m -Xmx512m -jar gateway-server-0.0.1-SNAPSHOT.jar

```
C:\Users\wfy>jmap -heap 118792
Attaching to process ID 118792, please wait...
Debugger attached successfully.
Server compiler detected.
JVM version is 25.181-b13

using thread-local object allocation.
Parallel GC with 8 thread(s)

Heap Configuration:
   MinHeapFreeRatio         = 0
   MaxHeapFreeRatio         = 100
   MaxHeapSize              = 536870912 (512.0MB)
   NewSize                  = 178782208 (170.5MB)
   MaxNewSize               = 178782208 (170.5MB)
   OldSize                  = 358088704 (341.5MB)
   NewRatio                 = 2
   SurvivorRatio            = 8
   MetaspaceSize            = 21807104 (20.796875MB)
   CompressedClassSpaceSize = 1073741824 (1024.0MB)
   MaxMetaspaceSize         = 17592186044415 MB
   G1HeapRegionSize         = 0 (0.0MB)

Heap Usage:
PS Young Generation
Eden Space:
   capacity = 167772160 (160.0MB)
   used     = 10353936 (9.874282836914062MB)
   free     = 157418224 (150.12571716308594MB)
   6.171426773071289% used
From Space:
   capacity = 5242880 (5.0MB)
   used     = 229376 (0.21875MB)
   free     = 5013504 (4.78125MB)
   4.375% used
To Space:
   capacity = 4718592 (4.5MB)
   used     = 0 (0.0MB)
   free     = 4718592 (4.5MB)
   0.0% used
PS Old Generation
   capacity = 358088704 (341.5MB)
   used     = 24733776 (23.587966918945312MB)
   free     = 333354928 (317.9120330810547MB)
   6.90716454434709% used

17351 interned Strings occupying 2249776 bytes.
```

这里可以看到，from=5m,to=4.5m, eden:from = 160:5  ,猜测可能就是JDK默认开启了自适应参数。



```
C:\Users\wfy>jstat -gc 118792 1000
 S0C    S1C    S0U    S1U      EC       EU        OC         OU       MC     MU    CCSC   CCSU   YGC     YGCT    FGC    FGCT     GCT
13312.0 12800.0  0.0   288.0  148480.0   0.0     349696.0   23810.1   38192.0 36468.1 4912.0 4538.0     21    0.116   2      0.097    0.213
12800.0 13312.0 224.0   0.0   147968.0 35150.9   349696.0   23850.1   38192.0 36468.1 4912.0 4538.0     22    0.118   2      0.097    0.215
12800.0 13312.0  0.0   256.0  147968.0 71486.5   349696.0   23906.1   38192.0 36468.4 4912.0 4538.0     23    0.119   2      0.097    0.216
12800.0 12800.0 256.0   0.0   148992.0 105895.9  349696.0   23970.1   38192.0 36468.4 4912.0 4538.0     24    0.121   2      0.097    0.218
12288.0 12800.0  0.0   256.0  148992.0 35580.2   349696.0   24034.1   38192.0 36469.2 4912.0 4538.0     25    0.122   2      0.097    0.219
12288.0 12800.0  0.0   256.0  148992.0 35580.2   349696.0   24034.1   38192.0 36469.2 4912.0 4538.0     25    0.122   2      0.097    0.219
12288.0 12800.0  0.0   256.0  148992.0 35580.2   349696.0   24034.1   38192.0 36469.2 4912.0 4538.0     25    0.122   2      0.097    0.219
12288.0 12800.0  0.0   256.0  148992.0 35580.2   349696.0   24034.1   38192.0 36469.2 4912.0 4538.0     25    0.122   2      0.097    0.219
12288.0 12288.0 256.0   0.0   150016.0 68444.6   349696.0   24050.1   38192.0 36469.2 4912.0 4538.0     26    0.124   2      0.097    0.221
11776.0 12288.0  0.0   256.0  150016.0 104578.6  349696.0   24050.1   38192.0 36469.7 4912.0 4538.0     27    0.125   2      0.097    0.222
11776.0 11264.0 256.0   0.0   151552.0 135632.6  349696.0   24050.1   38192.0 36469.7 4912.0 4538.0     28    0.127   2      0.097    0.224
10752.0 10240.0 256.0   0.0   153600.0 21553.8   349696.0   24050.1   38192.0 36469.7 4912.0 4538.0     30    0.130   2      0.097    0.227
9728.0 10240.0  0.0   256.0  153600.0 56071.6   349696.0   24050.1   38192.0 36469.7 4912.0 4538.0     31    0.132   2      0.097    0.229
9728.0 9216.0 256.0   0.0   155648.0 83633.0   349696.0   24050.1   38192.0 36469.7 4912.0 4538.0     32    0.133   2      0.097    0.230
8704.0 9216.0  0.0   256.0  155648.0 116816.4  349696.0   24058.1   38192.0 36469.7 4912.0 4538.0     33    0.135   2      0.097    0.232
8704.0 8192.0 256.0   0.0   157696.0 150457.1  349696.0   24058.1   38192.0 36470.0 4912.0 4538.0     34    0.137   2      0.097    0.233
7680.0 7680.0 256.0   0.0   159232.0 22876.0   349696.0   24090.1   38192.0 36470.0 4912.0 4538.0     36    0.139   2      0.097    0.236
7168.0 7680.0  0.0   256.0  159232.0 52013.3   349696.0   24090.1   38192.0 36470.3 4912.0 4538.0     37    0.141   2      0.097    0.238
7168.0 6656.0 256.0   0.0   160768.0 81183.6   349696.0   24098.1   38192.0 36485.6 4912.0 4538.0     38    0.142   2      0.097    0.239
6144.0 6656.0  0.0   256.0  160768.0 97010.7   349696.0   24138.1   38192.0 36485.6 4912.0 4538.0     39    0.144   2      0.097    0.240
6144.0 5632.0 256.0   0.0   162816.0 125651.8  349696.0   24146.1   38192.0 36485.6 4912.0 4538.0     40    0.145   2      0.097    0.242
5632.0 5632.0  0.0   256.0  162816.0 138377.4  349696.0   24154.1   38192.0 36485.6 4912.0 4538.0     41    0.147   2      0.097    0.244
4608.0 5120.0  0.0   224.0  163840.0  3351.6   349696.0   24154.1   38192.0 36486.2 4912.0 4538.0     43    0.150   2      0.097    0.247
```

#### 4、 并行关闭自适应gc java -Xms512m -Xmx512m -jar gateway-server-0.0.1-SNAPSHOT.jar

```
C:\Users\wfy>jmap -heap 128672
Attaching to process ID 128672, please wait...
Debugger attached successfully.
Server compiler detected.
JVM version is 25.181-b13

using thread-local object allocation.
Parallel GC with 8 thread(s)

Heap Configuration:
   MinHeapFreeRatio         = 40
   MaxHeapFreeRatio         = 70
   MaxHeapSize              = 536870912 (512.0MB)
   NewSize                  = 178782208 (170.5MB)
   MaxNewSize               = 178782208 (170.5MB)
   OldSize                  = 358088704 (341.5MB)
   NewRatio                 = 2
   SurvivorRatio            = 8
   MetaspaceSize            = 21807104 (20.796875MB)
   CompressedClassSpaceSize = 1073741824 (1024.0MB)
   MaxMetaspaceSize         = 17592186044415 MB
   G1HeapRegionSize         = 0 (0.0MB)

Heap Usage:
PS Young Generation
Eden Space:
   capacity = 134742016 (128.5MB)
   used     = 9056968 (8.637397766113281MB)
   free     = 125685048 (119.86260223388672MB)
   6.721710323823565% used
From Space:
   capacity = 22020096 (21.0MB)
   used     = 0 (0.0MB)
   free     = 22020096 (21.0MB)
   0.0% used
To Space:
   capacity = 22020096 (21.0MB)
   used     = 0 (0.0MB)
   free     = 22020096 (21.0MB)
   0.0% used
PS Old Generation
   capacity = 358088704 (341.5MB)
   used     = 19346624 (18.45037841796875MB)
   free     = 338742080 (323.04962158203125MB)
   5.402746242450585% used

15748 interned Strings occupying 2097792 bytes.
```

