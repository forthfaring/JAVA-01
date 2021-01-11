### 1、自己写一个简单的 Hello.java，里面需要涉及基本类型，四则运行，if 和 for，然后自己分析一下对应的字节码

#### 源代码：
```
public class Hello {

    public static void main(String[] args) {
        Hello h = new Hello();
        int x = 10;
        int y = 5;
        System.out.println(h.add(x, y));
        System.out.println(sub(x, y));
        System.out.println(mul(x, y));
        int d = div(x, y);
        System.out.println(div(x, y));
        if (d == 2) {
            System.out.println(true);
        }
        for (int i = 0; i < x; i++) {
            System.out.println(i);
        }
    }

    private int add(int x, int y) {
        return x + y;
    }

    private static int sub(int x, int y) {
        return x - y;
    }

    private static int mul(int x, int y) {
        return x * y;
    }

    private static int div(int x, int y) {
        return x / y;
    }
}
```

#### 字节码分析：
```
Classfile /Users/wfy/work/study/grow/target/classes/org/wfy/grow/geektime/java/jvm/Hello.class
  Last modified 2021-1-11; size 1288 bytes   
  MD5 checksum ca94c8f4231fa66b4b7734bcef70cf02
  Compiled from "Hello.java"
public class org.wfy.grow.geektime.java.jvm.Hello
  minor version: 0   
  major version: 52   版本
  flags: ACC_PUBLIC, ACC_SUPER	类访问权限是public，ACC_SUPER是兼容旧版本
Constant pool:      常量池
   #1 = Methodref          #11.#40        // java/lang/Object."<init>":()V
   #2 = Class              #41            // org/wfy/grow/geektime/java/jvm/Hello
   #3 = Methodref          #2.#40         // org/wfy/grow/geektime/java/jvm/Hello."<init>":()V
   #4 = Fieldref           #42.#43        // java/lang/System.out:Ljava/io/PrintStream;
   #5 = Methodref          #2.#44         // org/wfy/grow/geektime/java/jvm/Hello.add:(II)I
   #6 = Methodref          #45.#46        // java/io/PrintStream.println:(I)V
   #7 = Methodref          #2.#47         // org/wfy/grow/geektime/java/jvm/Hello.sub:(II)I
   #8 = Methodref          #2.#48         // org/wfy/grow/geektime/java/jvm/Hello.mul:(II)I
   #9 = Methodref          #2.#49         // org/wfy/grow/geektime/java/jvm/Hello.div:(II)I
  #10 = Methodref          #45.#50        // java/io/PrintStream.println:(Z)V
  #11 = Class              #51            // java/lang/Object
  #12 = Utf8               <init>
  #13 = Utf8               ()V
  #14 = Utf8               Code
  #15 = Utf8               LineNumberTable
  #16 = Utf8               LocalVariableTable
  #17 = Utf8               this
  #18 = Utf8               Lorg/wfy/grow/geektime/java/jvm/Hello;
  #19 = Utf8               main
  #20 = Utf8               ([Ljava/lang/String;)V
  #21 = Utf8               i
  #22 = Utf8               I
  #23 = Utf8               args
  #24 = Utf8               [Ljava/lang/String;
  #25 = Utf8               h
  #26 = Utf8               x
  #27 = Utf8               y
  #28 = Utf8               d
  #29 = Utf8               StackMapTable
  #30 = Class              #24            // "[Ljava/lang/String;"
  #31 = Class              #41            // org/wfy/grow/geektime/java/jvm/Hello
  #32 = Utf8               MethodParameters
  #33 = Utf8               add
  #34 = Utf8               (II)I
  #35 = Utf8               sub
  #36 = Utf8               mul
  #37 = Utf8               div
  #38 = Utf8               SourceFile
  #39 = Utf8               Hello.java
  #40 = NameAndType        #12:#13        // "<init>":()V
  #41 = Utf8               org/wfy/grow/geektime/java/jvm/Hello
  #42 = Class              #52            // java/lang/System
  #43 = NameAndType        #53:#54        // out:Ljava/io/PrintStream;
  #44 = NameAndType        #33:#34        // add:(II)I
  #45 = Class              #55            // java/io/PrintStream
  #46 = NameAndType        #56:#57        // println:(I)V
  #47 = NameAndType        #35:#34        // sub:(II)I
  #48 = NameAndType        #36:#34        // mul:(II)I
  #49 = NameAndType        #37:#34        // div:(II)I
  #50 = NameAndType        #56:#58        // println:(Z)V
  #51 = Utf8               java/lang/Object
  #52 = Utf8               java/lang/System
  #53 = Utf8               out
  #54 = Utf8               Ljava/io/PrintStream;
  #55 = Utf8               java/io/PrintStream
  #56 = Utf8               println
  #57 = Utf8               (I)V
  #58 = Utf8               (Z)V
{
  public org.wfy.grow.geektime.java.jvm.Hello();  类的默认构造方法
    descriptor: ()V    无参、返回值为void
    flags: ACC_PUBLIC   public
    Code:
      stack=1, locals=1, args_size=1  
         0: aload_0
         1: invokespecial #1                  // Method java/lang/Object."<init>":()V
         4: return
      LineNumberTable:
        line 9: 0
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0       5     0  this   Lorg/wfy/grow/geektime/java/jvm/Hello;

  public static void main(java.lang.String[]);   									main方法
    descriptor: ([Ljava/lang/String;)V											参数为string数组，返回值为void
    flags: ACC_PUBLIC, ACC_STATIC    										public static
    Code:    																main方法的Code属性（即方法体内容）
      stack=4, locals=6, args_size=1     											依次为main方法栈深度，局部变量表的变量数，形参数
         0: new           #2                  // class org/wfy/grow/geektime/java/jvm/Hello    		执行new指令，创建Hello对象，将对象引用压入操作数栈顶
         3: dup																复制栈顶的对象引用，并压入栈顶
         4: invokespecial #3                  // Method "<init>":()V						栈顶引用出栈，调用引用的Hello对象的初始化方法
         7: astore_1															将栈顶剩下的那个Hello引用变量出栈，并存入局部变量表1号槽，槽位0放的是args形参的引用。
         8: bipush        10														将常量10压入栈顶						
        10: istore_2															将10出栈存入槽位2
        11: iconst_5															将常量5压入栈顶		
        12: istore_3															将5出栈存入槽位3
        13: getstatic     #4                  // Field java/lang/System.out:Ljava/io/PrintStream;	获取PrintStream的静态域，压入栈顶
        16: aload_1															加载hello对象入栈
        17: iload_2															10入栈
        18: iload_3															5入栈
        19: invokespecial #5                  // Method add:(II)I							调用add方法，入参和hello出栈
        22: invokevirtual #6                  // Method java/io/PrintStream.println:(I)V		调用println
        25: getstatic     #4                  // Field java/lang/System.out:Ljava/io/PrintStream;	获取PrintStream的静态域，压入栈顶
        28: iload_2															10入栈
        29: iload_3															5入栈
        30: invokestatic  #7                  // Method sub:(II)I							调用sub
        33: invokevirtual #6                  // Method java/io/PrintStream.println:(I)V		调用println
        36: getstatic     #4                  // Field java/lang/System.out:Ljava/io/PrintStream;	//这里开始是mul调用，与sub相同
        39: iload_2
        40: iload_3
        41: invokestatic  #8                  // Method mul:(II)I
        44: invokevirtual #6                  // Method java/io/PrintStream.println:(I)V
        47: iload_2
        48: iload_3
        49: invokestatic  #9                  // Method div:(II)I							//这里开始是div调用，与sub相同
        52: istore        4
        54: getstatic     #4                  // Field java/lang/System.out:Ljava/io/PrintStream;
        57: iload_2
        58: iload_3
        59: invokestatic  #9                  // Method div:(II)I
        62: invokevirtual #6                  // Method java/io/PrintStream.println:(I)V
        65: iload         4				
        67: iconst_2
        68: if_icmpne     78													//比较栈顶2元素，如果不等，则程序计数器直接指向78
        71: getstatic     #4                  // Field java/lang/System.out:Ljava/io/PrintStream;
        74: iconst_1															//true
        75: invokevirtual #10                 // Method java/io/PrintStream.println:(Z)V
        78: iconst_0															//init i=0
        79: istore        5
        81: iload         5
        83: iload_2
        84: if_icmpge     101													//i>=10   跳出循环
        87: getstatic     #4                  // Field java/lang/System.out:Ljava/io/PrintStream;
        90: iload         5
        92: invokevirtual #6                  // Method java/io/PrintStream.println:(I)V
        95: iinc          5, 1														//i++
        98: goto          81														//goto指令控制loop
       101: return
      LineNumberTable:														//字节码行号表
        line 12: 0
        line 13: 8
        line 14: 11
        line 15: 13
        line 16: 25
        line 17: 36
        line 18: 47
        line 19: 54
        line 20: 65
        line 21: 71
        line 23: 78
        line 24: 87
        line 23: 95
        line 26: 101
      LocalVariableTable:													//本地变量表      
        Start  Length  Slot  Name   Signature
           81      20     5     i   I												//依次是：变量开始作用的字节指令数据偏移量、作用的范围、占用的槽位、变量名、变量类型
            0     102     0  args   [Ljava/lang/String;
            8      94     1     h   Lorg/wfy/grow/geektime/java/jvm/Hello;
           11      91     2     x   I
           13      89     3     y   I
           54      48     4     d   I
      StackMapTable: number_of_entries = 3
        frame_type = 255 /* full_frame */
          offset_delta = 78
          locals = [ class "[Ljava/lang/String;", class org/wfy/grow/geektime/java/jvm/Hello, int, int, int ]
          stack = []
        frame_type = 252 /* append */
          offset_delta = 2
          locals = [ int ]
        frame_type = 250 /* chop */
          offset_delta = 19
    MethodParameters:
      Name                           Flags
      args
}
SourceFile: "Hello.java"
```

### 2、自定义一个 Classloader，加载一个 Hello.xlass 文件，执行 hello 方法，此文件内 容是一个 Hello.class 文件所有字节(x=255-x)处理后的文件。文件群里提供。
见[自定义类加载器](XClassLoader.java)

### 3、画一张图，展示 Xmx、Xms、Xmn、Meta、DirectMemory、Xss 这些内存参数的 关系。
