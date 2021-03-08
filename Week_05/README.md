### 1、（选做）使 Java 里的动态代理，实现一个简单的 AOP。

JDK:

```
@Data
@AllArgsConstructor
public class JdkDynamicProxy<T> implements InvocationHandler {

    private Object target;


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("begin log");
        Object ret = method.invoke(target, args);
        System.out.println("end log");
        return ret;
    }

    public IBussiness getProxy(Class<IBussiness> clazz) {
        return (IBussiness) Proxy.newProxyInstance
                (clazz.getClassLoader(), new Class<?>[]{clazz}, this);
    }

    public static void main(String[] args) {
        IBussiness b = new Bussiness();
        JdkDynamicProxy<IBussiness> jdkDynamicProxy = new JdkDynamicProxy<>(b);
        b = jdkDynamicProxy.getProxy(IBussiness.class);
        b.work();
    }
}
```

CGLIB:

```
public class CglibProxy implements MethodInterceptor {
    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();//创建一个代理工具类
        enhancer.setSuperclass(Bussiness.class);//设置一个模拟的父类
        enhancer.setCallback(new CglibProxy());
        IBussiness b = (IBussiness) enhancer.create();
        System.out.println(b.work());
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy proxy) throws Throwable {
        System.out.println("Before Method Invoke");
        Object ret = proxy.invokeSuper(o, objects);
        System.out.println("After Method Invoke");
        return ret;
    }
}
```



### 2、（必做）写代码实现 Spring Bean 的装配，方式越多越好（XML、Annotation 都可以）,提交到 Github。

* 使用ClassPathXmlApplicationContext+ xml bean配置
* 使用ClassPathXmlApplicationContext+<context:component-scan
* FileSystemXmlApplicationContext
* AnnotationConfigApplicationContext+@Configuration里的@Bean类
* AnnotationConfigApplicationContext+@Configuration类上的@ComponentScan注解
* 自定义实现FactoryBean，通过getObject创建bean

详细可以参考spring01中的代码

### 3、（选做）实现一个 Spring XML 自定义配置，配置一组 Bean，例如：Student/Klass/School。

略

## 4、（选做，会添加到高手附加题）

### 4.1 （挑战）讲网关的 frontend/backend/filter/router 线程池都改造成 Spring 配置方式；

week03的nio02

### 4.2 （挑战）基于 AOP 改造 Netty 网关，filter 和 router 使用 AOP 方式实现；



### 4.3 （中级挑战）基于前述改造，将网关请求前后端分离，中级使用 JMS 传递消息；



### 4.4 （中级挑战）尝试使用 ByteBuddy 实现一个简单的基于类的 AOP；

```

```



### 4.5 （超级挑战）尝试使用 ByteBuddy 与 Instrument 实现一个简单 JavaAgent 实现无侵入下的 AOP。  









