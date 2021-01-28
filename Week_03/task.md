



## 02

### 01 

* daemon

  设置新建的线程是当前线程的守护线程。当前线程运行结束，如果守护线程还在运行，则立刻结束，连finally也无法执行。

* interupt

  调用的对象线程如果在运行，并不会真的打断，而是置interupt标识为true，供这个对象自己判断作为通信信号，执行对应逻辑。一般不使用。

  用法：

  ```
  thread t1 = new Thread(()->{
  	while (!Thread.interupted()) {
  	}
  	//进行后续业务操作
  });
  t1.start;
  makeConditionSatified();
  t1.interupt();
  ```

  

* thread.join

  ```
  main(){
  	thread t1...
  	t1.join();
  	//do other things
  }
  ```

  main线程wait，等待t1线程执行结束后，唤醒main线程。底层原理：适用join方法适用t1对象作为锁，然后让调用者main线程wait，t1执行结束后再JVM原理里会调用notifyAll唤醒main的等待。所以join传参数等同于wait传时间参数的效果。

* wait/notify

  使用方法：wait/notify必须在同一把同步锁内。
  
  等待通知机制用于线程通信。t1不满足条件就wait。满足条件时在t2中notify t1。
  
  