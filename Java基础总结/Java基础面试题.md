# 一、语言基础

### Object 类方法

**clone()**创建并且返回对象的副本

**equals** （Object  obj ） 对象是否相等

**finalize()**  当垃圾收集确定不再有该对象引用，垃圾收集器在对象上调用该对象 。

 **getClass()** 
返回此 Object的运行时类。  
int **hashCode()** 
返回对象的哈希码值。  
void **notify()** 
唤醒正在等待对象监视器的单个线程。  
void **notifyAll()** 
唤醒正在等待对象监视器的所有线程。  
String **toString()** 
返回对象的字符串表示形式。  
void **wait()** 
导致当前线程等待，直到另一个线程调用该对象的 notify()方法或 notifyAll()方法。  
void **wait(long timeout)** 
导致当前线程等待，直到另一个线程调用 notify()方法或该对象的 notifyAll()方法，或者指定的时间已过。  
void **wait(long timeout, int nanos)** 
导致当前线程等待，直到另一个线程调用该对象的 notify()方法或 notifyAll()方法，或者某些其他线程中断当前线程，或一定量的实时时间。  

## 1.请说明String是最基本的数据类型吗?

是引用数据类型

**八大基本数据类型**

| 基本类型 |   大小   |  封装类   |
| :------: | :------: | :-------: |
| boolean  | 未知字节 |  Boolean  |
|   byte   |  1字节   |   Byte    |
|   char   |  2字节   | Charactor |
|  short   |  2字节   |   Short   |
|   int    |  4字节   |  Integer  |
|   long   |  8字节   |   Long    |
|  float   |  4字节   |   Float   |
|  double  |  8字节   |  Double   |

### 在main函数执行前，可能有函数可以执行吗？写一下



## Java 语言特性

封装继承多态

1、**封装：** 通常认为封装是把数据和操作数据的方法封装起来，对数据的访问只能通过已
定义的接口。  

**2、继承：** 继承是从已有类得到继承信息创建新类的过程。提供继承信息的类被称为父类
（超类/基类），得到继承信息的被称为子类（派生类）。  

关于继承的几点补充：
1、**子类拥有父类对象所有的属性和方法**（包括私有属性和私有方法），但是父类中
的私有属性和方法子类是无法访问，只是拥有。因为在一个子类被创建的时候，首先 会在内存中创建一个父类对象，然后在父类对象外部放上子类独有的属性，两者合起
来形成一个子类的对象；   

2、子类可以拥有自己属性和方法  。

3、子类可以用自己的方式实现父类的方法。（重写） 。

多态： 分为编译时多态（方法重载）和运行时多态（方法重写）

要实现多态需要做两件事，一是子类继承服务的方法，而是用父类型引用子类对象，这样调用同样方法会根据子类的不同表现不同的行为。



### 重载（Overload）和重写（Override）的区别？【重点】  

重载：编译时多态、同一个类中、**同名的方法具有不同的参数列表、不能根据返回类**
**型进行区分**【因为：函数调用时不能指定类型信息，编译器不知道你要调哪个函数】；
重写（又名覆盖）：运行时多态、子类与父类之间、子类重写父类的方法具有相同的
返回类型、更好的访问权限。  

**静态的方法可以被继承**，但是不能重写。如果父类和子类中存在同样名称和参数的静
态方法，那么该子类的方法会把原来继承过来的父类的方法隐藏，而不是重写。通俗
的讲就是父类的方法和子类的方法是两个没有关系的方法，具体调用哪一个方法是看
是哪个对象的引用；这种父子类方法也不在存在多态的性质 【即 Father father =
new child(); father 这个引用调用的是父类里的静态方法。如果是多态，那么 father
应该调用的子类的方法】  

**Java 中不可以覆盖private的方法**，因为private修饰的变量和方法在当前类中使用，如果其他的类继承当前类是不能访问private变量或方法的，当然也不能覆盖。

### Java 中创建对象的几种方式？

  1.使用new 关键字。

2使用Class 类的newInstance方法，该方法调用无参构造（反射）。

Class.forName.newInstance()；  

3.使用clone(); 

4.反序列化，比如ObjectInputStream类的readObject()



### Java 中是否可以重写（Override）一个 private 或者  static 方法？  

Java中static 方法不能被覆盖，因为方法覆盖基于运动时绑定，

而static 是编译时静态绑定，static 方法跟类的任何实例都不相关。

#### 抽象类和接口有什么不同？

关键字 是否有构造

1、抽象类中可以定义构造函数，接口不能定义构造函数；
2、抽象类中可以有**抽象方法和具体方法**，而接口中只能有**抽象方法**（public abstract）；
3、抽象类中的成员权限可以是 public、默认、protected（抽象类中抽象方法就是为了重
写，所以不能被 private 修饰），而接口中的成员只可以是 public（方法默认：public
abstrat、成员变量默认：public static final）；  

**在 Java8 中，允许在接口中包含带有具体实现的方法，使用 default 修饰，这类方法**
**就是默认方法。**  

**抽象类中可以包含静态方法，而接口中不可以包含静态方法；**  

抽象类中可以包含静态方法，在 JDK1.8 之前接口中不能包含静态方法，JDK1.8 以后
可以包含。之前不能包含是因为，接口不可以实现方法，只可以定义方法，所以不能
使用静态方法（因为静态方法必须实现）。现在可以包含了，只能直接用接口调用静
态方法。1.8 仍然不可以包含静态代码块  。

接口的成员比那辆默认是public  static final .static是为了保证变量只有一份。因为一个类可以实现多个接口，定义static 之后，如果出现重名，那么存储在静态存储区的时候会报错，因为静态存储区中已经有已一份。

final 是因为接口的东西大家公用，不能随便修改。

**相同点：**

1**.都不能被实例化**。

2.抽象类和接口作为**引用类型**

3.一个类如果继承或一个抽象类或者实现某个接口，就必须对其中的抽象方法全部实现，否则该类需要被声明为抽象类。

#### 普通类和抽象类有哪些区别？

  普通类：不能包含抽象方法，抽象类可以包含抽象方法
   抽象类：不能直接实例化，普通类可以直接实例化  

### 抽象类必须要有抽象方法吗？  

不需要抽象类不一定非要抽象方法。

抽象类能使用final 修饰吗？

不能，定义抽象类就是让其他类继承的，如果定义为 final 该类就不能被继承，这样彼
此就会产生矛盾，所以 final 不能修饰抽象类。  

### 静态变量和实例变量的区别？2020-2-28 

**静态变量：** 是被static 修饰也称为类变量，它属于类，不管创建多少对象，静态变量在内存够中只有一份拷贝。

静态变量可以实现多个对象共享内存。

**实例变量**： 属于某一个实例，需要先创建对象，然后对象才可以访问。

#### 成员变量与局部变量的区别有那些？

1、从语法形式上看：**成员变量是属于类的**，而**局部变量是在方法中定义的变量或是方法的**
**参数；成员变量可以被 public、private、static 等修饰符所修饰，而局部变量不能被访问**
**控制修饰符及 static 所修饰**；但是，**成员变量和局部变量都能被 final 所修饰；**
2、从变量在内存中**存储方式**来看：如果成员变量是使用 static 修饰的，那么这个成员变
量是属于类的，如果没有使用 static 修饰，这个成员变量是属于实例的。而**对象存在于堆**
**内存，局部变量则存在于栈内存。**
3、从变量在内存中的生存时间上看：成员变量是对象的一部分，它随着对象的创建而存
在，而局部变量随着方法的调用而自动消失。
4、成员变量如果没有被赋初值：则会**自动以类型的默认值而赋值**（一种情况例外：被
final 修饰的成员变量也必须显式地赋值），而局部变量则不会自动赋值。    

### 构造方法有哪些特性？

  1.**名字与类名相同。**

2.**没有返回值**，但是不能用void声明构造函数。

3.成类的对象时自动执行，无需调用。

#### 在 Java 中定义一个不做事且没有参数的构造方法的作用？

 Java 程序在执行子类的构造方法之前，如果没有用 super() 来调用父类特定的构造
方法，则会调用父类中“没有参数的构造方法”。因此，如果父类中只定义了有参数的构
造方法，而在子类的构造方法中又没有用 super() 来调用父类中特定的构造方法，则编译
时将发生错误，因为 Java 程序在父类中找不到没有参数的构造方法可供执行。解决办法
是：在父类里加上一个不做事且没有参数的构造方法。  

### Java 八种基本数据类型

Java 是一种强类型语言。变量的值占据一定的内存空间。不同类型的变量占据不同的
大小。Java 中共有 8 种基本数据类型：包括 4 种整型（int 4个字节 short  2个字节  long 8个字节 f是）、2 种浮点型(float  double )、1 种字符型  char  2个字节和 1 种布 boolean  1个字节
尔型。  

#### short s1 = 1；s1 = s1 + 1；有什么错？那么 short s1 =

1; s1 += 1；呢？有没有错误？
对于 short s1 = 1; s1 = s1 + 1; 来说，在 s1 + 1 运算时会自动提升表达式的类型为
int ，那么将 int 型值赋值给 short 型变量，s1 会出现类型转换错误。
对于 short s1 = 1; s1 += 1; 来说，+= 是 Java 语言规定的运算符，Java 编译器会
对它进行特殊处理，因此可以正确编译。 

#### Integer 和 int 的区别？

1、int 是 Java 的八种基本数据类型之一，而 Integer 是 Java 为 int 类型提供的封装类；
2、int 型变量的默认值是 0，Integer 变量的默认值是 null，这一点说明 Integer 可以区
分出未赋值和值为 0 的区分；

3、Integer 变量必须实例化后才可以使用，而 int 不需要 。

2.1 延申：关于 Integer 和 int 的比较：
1、由于 Integer 变量实际上是对一个 Integer 对象的引用，所以两个通过 new 生成
的 Integer 变量永远是不相等的，因为其内存地址是不同的；
2、Integer 变量和 int 变量比较时，只要两个变量的值是相等的，则结果为 true。因
为包装类 Integer 和基本数据类型 int 类型进行比较时，Java 会自动拆包装类为 int，然后
进行比较，实际上就是两个 int 型变量在进行比较；
3、非 new 生成的 Integer 变量和 new Integer() 生成的变量进行比较时，结果为
false。因为非 new 生成的 Integer 变量指向的是 Java 常量池中的对象，而 new
Integer() 生成的变量指向堆中新建的对象，两者在内存中的地址不同；
4、对于两个非 new 生成的 Integer 对象进行比较时，如果两个变量的值在区间
[-128, 127] 之间，则比较结果为 true，否则为 false。
这里解释下第 4 条的原因：Java 在编译 Integer i = 100 时，会编译成 Integer i =
Integer.valueOf(100)，而 Integer 类型的 valueOf 的源码如下所示：
public static Integer valueOf(int var0) {
return var0 >= -128 && var0 <= Integer.IntegerCache.high ?
Integer.IntegerCache.cache[var0 + 128] : new Integer(var0);
}
从上面的代码中可以看出：Java 对于 [-128, 127] 之间的数会进行缓存，比如：
Integer i = 127，会将 127 进行缓存，下次再写 Integer j = 127 的时候，就会直接从缓
存中取出，而对于这个区间之外的数就需要 new 了。
https://www.cnblogs.com/javatech/p/3650460.html
包装类的缓存：
Boolean：全部缓存
Byte：全部缓存
Character：<= 127 缓存
Short：-128 — 127 缓存
Long：-128 — 127 缓存
Integer：-128 — 127 缓存
Float：没有缓存
Doulbe：没有缓存  

#### 装箱和拆箱

自动装箱是 Java编译器在基本数据类型和对应得包装类之间做的一个转化。比如：把
int 转化成 Integer，double 转化成 Double 等等。反之就是自动拆箱。
原始类型：boolean、char、byte、short、int、long、float、double
封装类型：Boolean、Character、Byte、Short、Integer、Long、Float、Double  

char 类型变量能不能存储一个中文的汉字，为什么？
char 类型变量是用来存储 Unicode 编码的字符的，Unicode 字符集包含了中文，所
以 char 类型变量当然可以存储汉字。但是如果某个生僻字没有包含在 Unicode 编码的字
符集中，那么 char 就不能存储该生僻字。
补充：常用中文字符用 utf-8 编码占用 3 个字节（大约 2 万多字），但超大字符集中
的更大多数汉字要占 4 个字节。GBK、GB2312 收编的汉字占 2 个字节。  

#### switch 语句能否作用在 byte 上，能否作用在 long 上，能

否作用在 String 上？  

在 switch(expr 1) 中，**expr1 只能是一个整数表达式或者枚举常量**。而整数表达式可
以是 int 基本数据类型或者 Integer 包装类型。由于，**byte、short、char** **都可以隐式转**
**换为 int**，所以，这些类型以及这些类型的包装类型也都是可以的。而 long 和 String 类
型都不符合 switch 的语法规定，并且不能被隐式的转换为 int 类型，所以，它们不能作用
于 switch 语句中。不过，****需要注意的是在 JDK1.7 版本之后 switch 就可以作用在**
String 上了。**  

#### 字节和字符的区别？  

字节是存储容量的基本单位；
字符是数字、字母、汉字以及其他语言的各种符号；
1 字节 = 8 个二进制单位，一个字符由一个字节或多个字节的二进制单位组成。  

#### Java 基本类型和引用类型的区别？  

基本类型保存原始值，引用类型保存的是引用值（引用值就是指对象在堆中所处的位
）。        

### 2020.3.3  华为 java如何判断字符串非空才不会抛出空指针异常？

https://blog.csdn.net/weixin_34406086/article/details/93212977

起因：我要判断一个字符串非空，代码是这样写的:**!str.isEmpty()**,当字符串的值等于null的时候，运行代码就会抛出空指针异常，**因为字符串为null的时候不能调用它的任何方法**。

```
public class TestStringNull {
    public static void main(String[] args) {
        String   str1=null;
        if(str1.isEmpty()){

            System.out.println(str1);//Exception in thread "main" java.lang.NullPointerException
        }

    }
}

```

```
public class TestStringNull {
    public static void main(String[] args) {
        String   str1=null;
        if(str1.isEmpty()){//异常代码
            System.out.println(str1);//Exception in thread "main" java.lang.NullPointerException
        }
     String    str2=null;
     //先判断是否为空
     if(str2!=null &&str2.equals("")){// 
         System.out.println(str2);
     }
        if(str2!=null &&str2.isEmpty()){
            System.out.println(str2);
        }
    }
}
```



## String 概述  

String 被声明为 final，因此它不可被继承。
String 在 Java8 中，String 内部使用 char 数组存储数据。

```
public final class String implements java.io.Serializable,
Comparable<String>, CharSequence {
/** The value is used for character storage. */
private final char value[ ];
}
```

String 在 Java 9 之后，String 类的实现改用 byte 数组存储字符串，同时使
用 coder 来标识使用了哪种编码。

String 在 Java 9 之后，String 类的实现改用 byte 数组存储字符串，同时使
用 coder 来标识使用了哪种编码。

```
public final class String implements java.io.Serializable,
Comparable<String>, CharSequence {
/** The value is used for character storage. */
private final byte[] value;
/** The identifier of the encoding used to encode the bytes in {@code
value}. */
private final byte coder;
}
```

value 数组被声明为 final，这意味着 value 数组初始化之后就不能再引用其它数组。
并且 String 内部没有改变 value 数组的方法，因此可以保证 String 不可变。
而 StringBuilder 与 StringBuffer 都继承自 AbstractStringBuilder 类，在
AbstractStringBuilder 中的字符数组 char[] value 没有用 final 关键字修饰，所以这两种
对象都是可变的。

value 数组被声明为 final，这意味着 value 数组初始化之后就不能再引用其它数组。
并且 String 内部没有改变 value 数组的方法，因此可以保证 String 不可变。
而 StringBuilder 与 StringBuffer 都继承自 AbstractStringBuilder 类，在
AbstractStringBuilder 中的字符数组 char[] value 没有用 final 关键字修饰，所以这两种
对象都是可变的。

```
abstract class AbstractStringBuilder implements Appendable, CharSequence {
char[] value;
int count;
AbstractStringBuilder() {
} A
bstractStringBuilder(int capacity) {
value = new char[capacity];  
```

String、StringBuilder、StringBuffer的区别【重点】？  

1、String：用于字符串操作，属于不可变类；【补充：String 不是基本数据类型，是引用
类型，底层用 char 数组实现的】
2、StringBuffer：也用于字符串操作，不同之处是 StringBuffer 属于可变类，对方法加了
同步锁，线程安全；
说明：StringBuffer 中并不是所有方法都使用了 Synchronized 修饰来实现同步：  

说明：StringBuffer 中并不是所有方法都使用了 Synchronized 修饰来实现同步：
上诉方法通过调用超类 AbstractStringBuilder 中的方法，将 s 转换为 String。
3、StringBuilder：与 StringBuffer 类似，都是字符串缓冲区，但线程不安全。
执行效率：StringBuilder > StringBuffer > String
String 字符串修改实现的原理：当用 String 类型来对字符串进行修改时，其实现方
法是首先创建一个 StringBuffer，其次调用 StringBuffer 的 append() 方法，最后调用
StringBuffer 的 toString() 方法把结果返回。

#### String 与基本数据类型直接的转换？

字符串转换为基本数据类型：调用基本数据类型对应包装类中的方法
parseXXX(String) 或者 valueOf(String) 即可返回相应的基本数据类型；
基本数据类型转换为字符串：一种方法是将基本数据类型与空字符串（””）连接
（+）即可获得其对应的字符串；另一种方法是调用 String 类中的 vauleOf() 方法返回相
应字符串。

#### 4、String str = "i" 与 String str = new String("i") 一样

吗？
不一样，因为内存的分配方式不一样。String str = "i" 的方式，Java 虚拟机会将其分
配到常量池中；而 String str = new String("i") 则会被分到堆内存中。
详细讲解：https://blog.csdn.net/pcwl1206/article/details/81985828  

#### 字符串比较

```
String  str1="abc";
String  str2 ="abc";
String str3= new String ("abc");
String str4 =new String ("abc");
System.out.pringln(str1==str2);//true
System.out.pringln(str1==str3);//false
System.out.println(str3==str4);// flase
System.out.println(str3.equals(str4));//true
```

![](.\img\字符串比较.png)

在执行 String str1 = "abc" 的时候，JVM 会首先**检查字符串常量池**中是否已经存在
该字符串对象，**如果已经存在，那么就不会再创建了，直接返回该字符串在字符串常量池**
**中的内存地址**；如果该字符串还不存在字符串常量池中，那么就会在**字符串常量池中创建**
**该字符串对象，然后再返回**。所以在执行 String str2 = "abc" 的时候，因为字符串常量池
中已经存在“abc”字符串对象了，就不会在字符串常量池中再次创建了，**所以栈内存中**
**str1 和 str2 的内存地址都是指向 "abc" 在字符串常量池中的位置**，所以 str1 = str2 的运
行结果为 true。
**而在执行 String str3 = new String("abc") 的时候，JVM 会首先检查字符串常量池**
**中是否已经存在“abc”字符串，如果已经存在，则不会在字符串常量池中再创建了；如果**
**不存在，则就会在字符串常量池中创建"abc"字符串对象，然后再到堆内存中再创建一份**
**字符串对象，把字符串常量池中的"abc"字符串内容拷贝到内存中的字符串对象中，然后**
**返回堆内存中该字符串的内存地址，即栈内存中存储的地址是堆内存中对象的内存地址。**
String str4 = new String("abc")是**在堆内存中又创建了一个对象**，所以 str 3 == str4
运行的结果是 false。str1、str2、str3、str4 在内存中的存储状况如下图所示：

  ![](.\img\字符串比较.png)

#### transient --> Java 序列化中如果有些字段不想进行序列

化，怎么办？
对于不想进行序列化的变量，使用 transient 关键字修饰。
transient 关键字的作用是：阻止实例中那些用此关键字修饰的的变量序列化。当对象
被反序列化时，被 transient 修饰的变量值不会被持久化和恢复。 transient 只能修饰变
量，不能修饰类和方法。    

#### 如何将字符串反转？  

使用 StringBuilder 或者 stringBuffer 的 **reverse()** 方法。  

### String类的常用方法

indexOf():返回指定字符的索引

charAt();返回指定索引处的字符

replace();字符串替换

trim();去除字符串两端空白

split();分割字符串，返回一个分割后字符串数组

getBytes();返回字符串的byte类型数组

length();返回字符串长度

toLowCase();转为小写字母

toUpperCase();转为大写字母

substring();截取字符串

equals();字符串比较

### final 修饰 StringBuffer 后还可以 append 吗？  

**可以。final 修饰的是一个引用变量，那么这个引用始终只能指向这个对象，但是这个**
**对象内部的属性是可以变化的。**
once a final variable has been assigned, it always contains the same value. If a
final variable holds a reference to an object, then the state of the object may
be changed by operations on the object, but the variable will always refer to
the same object.  

```
public class StringBufferTest {
    public static void main(String[] args) {
        final  StringBuffer  sb =new StringBuffer();
        sb.append("aaaaa");
        sb.append("dddddd");
        System.out.println(sb.toString());

    }
}
输出：aaaaadddddd

```

## 关键字

**final**
\1. 数据
声明数据为常量，可以是编译时常量，也可以是在运行时被初始化后不能被改变的常
量。
**对于基本类型： final 使数值不变**；
**对于引用类型： final 使引用不变**，也就不能引用其它对象，但是被引用的对象本身是可以
修改的。
final int x = 1;
// x = 2; // cannot assign value to final variable 'x'final A y = new A();
y.a = 1;  

**方法声明**

**方法不能被子类重写**

private 方法隐式的定义为final,如果在子类中定义的方法和基类中一个private方法相同， 此时子类的方法不是重写基类方法，而是在子类中定义了一个新的方法。

**类**

声明的类不允许被继承

  **final**、**finally**、**finalize** 的区别？  

用于声明属性、方法和类，分别表示属性不可变、方法不可覆盖、被其修饰的类不
可继承；

**finally**

  异常处理语句结构的一部分，表示总是执行；  

**finallize**  

Object类的一个方法，在垃圾回收时会调用被回收对象的finalize。 

super



##  运算 

#### == 和 equals 的区别？   



**两个对象的** hashCode() 相同，则 equals() 也一定为
true 吗？  



#### 为什么重写 equals() 就一定要重写 hashCode() 方法？(重点)  

这个问题应该是有个前提，就是你需要用到 HashMap、HashSet 等 Java 集合。用
不到哈希表的话，其实仅仅重写 equals() 方法也可以吧。而工作中的场景是常常用
到 Java 集合，所以 Java 官方建议重写 equals() 就一定要重写 hashCode() 方法  



## 金额货币BigDecimal的计算

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
金额的计算BigDecimal类

double d = 9.84;
double d2 = 1.22;
//注意需要使用BigDecimal(String val)构造方法
BigDecimal bigDecimal = new BigDecimal(Double.toString(d));
BigDecimal bigDecimal2 = new BigDecimal(Double.toString(d2));

//加法
BigDecimal bigDecimalAdd = bigDecimal.add(bigDecimal2);
double add = bigDecimalAdd.doubleValue();

//减法
BigDecimal bigDecimalSubtract = bigDecimal.subtract(bigDecimal2);
double subtract = bigDecimalSubtract.doubleValue();

//乘法
BigDecimal bigDecimalMultiply = bigDecimal.multiply(bigDecimal2);
double multiply = bigDecimalMultiply.doubleValue();

//除法
int scale = 2;//保留2位小数
BigDecimal bigDecimalDivide = bigDecimal.divide(bigDecimal2, scale, BigDecimal.ROUND_HALF_UP);
double divide = bigDecimalDivide.doubleValue();

//格式化
double format = 12343171.6;

//获取常规数值格式
NumberFormat number = NumberFormat.getNumberInstance();
String str = number.format(format);//12,343,171.6

//获取整数数值格式
NumberFormat integer = NumberFormat.getIntegerInstance();
str = integer.format(format);//如果带小数会四舍五入到整数12,343,172

//获取货币数值格式
NumberFormat currency = NumberFormat.getCurrencyInstance();
currency.setMinimumFractionDigits(2);//设置数的小数部分所允许的最小位数(如果不足后面补0)
currency.setMaximumFractionDigits(4);//设置数的小数部分所允许的最大位数(如果超过会四舍五入)
str = currency.format(format);//￥12,343,171.60

//获取显示百分比的格式
NumberFormat percent = NumberFormat.getPercentInstance();
percent.setMinimumFractionDigits(2);//设置数的小数部分所允许的最小位数(如果不足后面补0)
percent.setMaximumFractionDigits(3);//设置数的小数部分所允许的最大位数(如果超过会四舍五入)
str = percent.format(format);//1,234,317,160.00% 
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

# 2、集合类

![](.\img\Java集合框架.gif)

https://www.runoob.com/java/java-collections.html

![](.\img\java-coll.png)

## HashSet<E>

- 此类实现`Set`接口，由哈希表（**实际为`HashMap`实例**）支持。  对集合的迭代次序不作任何保证; 特别是，它不能保证订单在一段时间内保持不变。  这个类允许`null`元素。 

#### 集合实现类

标准集合类汇总于下表：



| 序号   | 类描述                                                       |
| :----- | :----------------------------------------------------------- |
| 1      | **AbstractCollection**   实现了大部分的集合接口。            |
| 2      | **AbstractList**   继承于AbstractCollection 并且实现了大部分List接口。 |
| 3      | **AbstractSequentialList**   继承于 AbstractList ，提供了对数据元素的链式访问而不是随机访问。 |
| 4      | LinkedList  该类实现了List接口，允许有null（空）元素。主要用于创建链表数据结构，该类没有同步方法，如果多个线程同时访问一个List，则必须自己实现访问同步，解决方法就是在创建List时候构造一个同步的List。例如：   `List list=Collections.synchronizedList(newLinkedList(...));` LinkedList 查找效率低。 |
| 5      | ArrayList  该类也是实现了List的接口，实现了可变大小的数组，随机访问和遍历元素时，提供更好的性能。该类也是非同步的,在多线程的情况下不要使用。ArrayList 增长当前长度的50%，插入删除效率低。 |
| 6      | **AbstractSet**   继承于AbstractCollection 并且实现了大部分Set接口。 |
| 7      | HashSet  该类实现了Set接口，不允许出现重复元素，不保证集合中元素的顺序，允许包含值为null的元素，但最多只能一个。 |
| 8      | LinkedHashSet  具有可预知迭代顺序的 `Set` 接口的哈希表和链接列表实现。 |
| 9      | TreeSet  该类实现了Set接口，可以实现排序等功能。             |
| 10     | **AbstractMap**   实现了大部分的Map接口。                    |
| 11重要 | **HashMap   HashMap 是一个散列表，它存储的内容是键值对(key-value)映射。  该类实现了Map接口，根据键的HashCode值存储数据，具有很快的访问速度，最多允许一条记录的键为null，不支持线程同步。** |
| 12     | TreeMap   继承了AbstractMap，并且使用一颗树。                |
| 13     | WeakHashMap   继承AbstractMap类，使用弱密钥的哈希表。        |
| 14     | LinkedHashMap   继承于HashMap，使用元素的自然顺序对元素进行排序. |
| 15     | IdentityHashMap   继承AbstractMap类，比较文档时使用引用相等。 |



### Set 和List的区别？

关键词：有序 无序   插入效率对比

1.Set接口存储的是无序，不需要重复数据List接口存储存储是有序的可以重复。

2.Set检索效率低下，删除和插入效率高，插入和删除不会引起元素位置改变 **<实现类有HashSet,TreeSet>**。

3. List和数组类似，可以动态增长，根据实际存储的数据的长度自动增长List的长度。查找元素效率高，插入删除效率低，因为会引起其他元素位置改变 **<实现类有ArrayList,LinkedList,Vector>** 。





#### 两个对象的 hashCode() 相同，则 equals() 也一定为

true 吗？  

两个对象的 hashCode() 相同，equals() 不一定为 true。因为在散列表中，
hashCode() 相等即两个键值对的哈希值相等，然而哈希值相等，并不一定能得出键值对相
等。【散列冲突】  

对于对象集合的判重，如果一个集合含有 10000 个对象实例，仅仅使用 equals() 方
法的话，那么对于一个对象判重就需要比较 10000 次，随着集合规模的增大，时间开销是
很大的。但是同时使用哈希表的话，就能快速定位到对象的大概存储位置，并且在定位到
大概存储位置后，后续比较过程中，如果两个对象的 hashCode 不相同，也不再需要调用
equals() 方法，从而大大减少了 equals() 比较次数。
所以从程序实现原理上来讲的话，既需要 equals() 方法，也需要 hashCode() 方法。
那么既然重写了 equals()，那么也要重写 hashCode() 方法，以保证两者之间的配合关
系。  

#### & 和 && 的区别？  

Java中 && 和 & 都是表示与的逻辑运算符，都表示逻辑运输符 and，当两边的表达
式都为 true 的时候，整个运算结果才为 true，否则为 false。
&&： 有短路功能，当第一个表达式的值为 false 的时候，则不再计算第二个表达式；
&： 不管第一个表达式结果是否为 true，第二个都会执行。除此之外，& 还可以用作位运
算符：当 & 两边的表达式不是 Boolean 类型的时候，& 表示按位操作。  

#### hashCode（）与equals（）的相关规定：

1、如果两个对象相等，则 hashCode 一定也是相同的；
2、两个对象相等，对两个对象分别调用 equals 方法都返回 true；
3、两个对象有相同的 hashCode 值，它们也不一定是相等的；
4、因此，equals 方法被覆盖过，则 hashCode 方法也必须被覆盖；
5、hashCode() 的默认行为是对堆上的对象产生独特值。如果没有重写 hashCode()，则
该 class 的两个对象无论如何都不会相等（即使这两个对象指向相同的数据）  

参数传递
https://blog.csdn.net/pcwl1206/article/details/86550268
Java 的参数是以值传递的形式传入方法中，而不是引用传递。
当传递方法参数类型为基本数据类型（数字以及布尔值）时，一个方法是不可能修改
一个基本数据类型的参数。
当传递方法参数类型为引用数据类型时，一个方法将修改一个引用数据类型的参数所
指向对象的值。
即使 Java 函数在传递引用数据类型时，也只是拷贝了引用的值罢了，之所以能修改引
用数据是因为它们同时指向了一个对象，但这仍然是按值调用而不是引用调用。  

## ArrayList 相关问题

### 1.如何在遍历ArrayList的时候安全删除其中一个元素。（重要）

快速失败机制

不要在foreach 循环进行元素remobve、add操作，reomove元素使用Iterator方式如果并发操作，需要对Iterator加锁。

原因是：普通for循环遍历删除元素，**list集合的大小会变小，而索引也会发生改变**（关键），所以利用f   for循环遍历删除元素会漏调某些元素。

```
public class ArrayListTest {

    public static void main(String[] args) {

        ArrayList<Integer> list =new ArrayList<>();
        for (int i = 0; i <10 ; i++) {
          list.add(i);
        }
        for (int i = 0; i < list.size(); i++) {//list的长度会变
            if(i%2==0){
                System.out.println("remove "+i+" "+list.size());
                list.remove(i);
            }
        }

        System.out.println(list);
        /**
         * remove 0 10
         * remove 2 9
         * remove 4 8
         * remove 6 7
         * [1, 2, 4, 5, 7, 8]
         *
         */

        ArrayList<Integer> list2 =new ArrayList<>();
        for (int i = 0; i <10 ; i++) {
            list2.add(i);
        }
       //使用迭代器
        Iterator<Integer> iterator = list2.iterator();

        while (iterator.hasNext()){

            Integer i= iterator.next();
            System.out.println(i);
            if(i%2==0){
                iterator.remove();//注意不是list.remove
                System.out.println("remove "+i+list2.size());
            }

        }

        System.out.println(list2);
        /***
         * 0
         * remove 09
         * 1
         * 2
         * remove 28
         * 3
         * 4
         * remove 47
         * 5
         * 6
         * remove 66
         * 7
         * 8
         * remove 85
         * 9
         * [1, 3, 5, 7, 9]
         */

    }

}

```

1、遍历ArrayList集合有三种方式
    (1)for循环
    (2)增强for循环，也就是foreach
    (3)迭代器iterator
2、普通for循环遍历删除元素，list集合的大小会变小，而索引也会发生改变，所以利用f
   for循环遍历删除元素会漏调某些元素。
   例如我for循环遍历删除第一个元素，接着按照索引去寻找第二个元素，由于删除的关系
   后面所有的元素都会往前面移动一位，就会导致按照索引得到的是第三个元素。
   解决方法：将list集合反过来遍历，循环删除其中的元素

   当我们使用增强for循环删除第一个元素后，再去遍历list集合，此时就会报并发修改错
   （concurrentModificationException异常）。
   通过查看list的remove方法的源码，我们可以看到，remove方法中有一个modCount++操作，
   然后再list集合的迭代器中有一个check操作，也就是检查modCount是否改变，如果改变
   就会抛出并发修改错误。
   解决方法：增强for循环遍历删除第一个元素后就break跳出。

   使用迭代器循环遍历删除某些元素，不会出现问题，但是我们要注意的是，使用的是
   iteraror.remove()方法，而不是list.remove()方法；如果使用的是list的remove方法，
   同样会报conCurrentModificationbException异常
3、总结
   如果是遍历删除list集合中某个特定的元素，使用这三个遍历方式都可以。
   如果要循环遍历删除多个元素，最好使用迭代器。其次使用普通for循环反过来遍历list集合

https://blog.csdn.net/pyf1903047190/article/details/102535086

2. ArrayList 的源码

   

​    

```
/**
 * Resizable-array implementation of the <tt>List</tt> interface.  Implements
 * all optional list operations, and permits all elements, including
 * <tt>null</tt>.  In addition to implementing the <tt>List</tt> interface,
 * this class provides methods to manipulate the size of the array that is
 * used internally to store the list.  (This class is roughly equivalent to
 * <tt>Vector</tt>, except that it is unsynchronized.)
 *
 * <p>The <tt>size</tt>, <tt>isEmpty</tt>, <tt>get</tt>, <tt>set</tt>,
 * <tt>iterator</tt>, and <tt>listIterator</tt> operations run in constant
 * time.  The <tt>add</tt> operation runs in <i>amortized constant time</i>,
 * that is, adding n elements requires O(n) time.  All of the other operations
 * run in linear time (roughly speaking).  The constant factor is low compared
 * to that for the <tt>LinkedList</tt> implementation.
 *
 * <p>Each <tt>ArrayList</tt> instance has a <i>capacity</i>.  The capacity is
 * the size of the array used to store the elements in the list.  It is always
 * at least as large as the list size.  As elements are added to an ArrayList,
 * its capacity grows automatically.  The details of the growth policy are not
 * specified beyond the fact that adding an element has constant amortized
 * time cost.
 *
 * <p>An application can increase the capacity of an <tt>ArrayList</tt> instance
 * before adding a large number of elements using the <tt>ensureCapacity</tt>
 * operation.  This may reduce the amount of incremental reallocation.
 *
 * <p><strong>Note that this implementation is not synchronized.</strong>
 * If multiple threads access an <tt>ArrayList</tt> instance concurrently,
 * and at least one of the threads modifies the list structurally, it
 * <i>must</i> be synchronized externally.  (A structural modification is
 * any operation that adds or deletes one or more elements, or explicitly
 * resizes the backing array; merely setting the value of an element is not
 * a structural modification.)  This is typically accomplished by
 * synchronizing on some object that naturally encapsulates the list.
 *
 * If no such object exists, the list should be "wrapped" using the
 * {@link Collections#synchronizedList Collections.synchronizedList}
 * method.  This is best done at creation time, to prevent accidental
 * unsynchronized access to the list:<pre>
 *   List list = Collections.synchronizedList(new ArrayList(...));</pre>
 *
 * <p><a name="fail-fast">
 * The iterators returned by this class's {@link #iterator() iterator} and
 * {@link #listIterator(int) listIterator} methods are <em>fail-fast</em>:</a>
 * if the list is structurally modified at any time after the iterator is
 * created, in any way except through the iterator's own
 * {@link ListIterator#remove() remove} or
 * {@link ListIterator#add(Object) add} methods, the iterator will throw a
 * {@link ConcurrentModificationException}.  Thus, in the face of
 * concurrent modification, the iterator fails quickly and cleanly, rather
 * than risking arbitrary, non-deterministic behavior at an undetermined
 * time in the future.
 *
 * <p>Note that the fail-fast behavior of an iterator cannot be guaranteed
 * as it is, generally speaking, impossible to make any hard guarantees in the
 * presence of unsynchronized concurrent modification.  Fail-fast iterators
 * throw {@code ConcurrentModificationException} on a best-effort basis.
 * Therefore, it would be wrong to write a program that depended on this
 * exception for its correctness:  <i>the fail-fast behavior of iterators
 * should be used only to detect bugs.</i>
 *
 * <p>This class is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * @author  Josh Bloch
 * @author  Neal Gafter
 * @see     Collection
 * @see     List
 * @see     LinkedList
 * @see     Vector
 * @since   1.2
 */

public class ArrayList<E> extends AbstractList<E>
        implements List<E>, RandomAccess, Cloneable, java.io.Serializable
{
    private static final long serialVersionUID = 8683452581122892189L;

    /**
     * Default initial capacity.
     */
    private static final int DEFAULT_CAPACITY = 10;//初始化容量

    /**
     * Shared empty array instance used for empty instances.
     */
    private static final Object[] EMPTY_ELEMENTDATA = {};

    /**
     * Shared empty array instance used for default sized empty instances. We
     * distinguish this from EMPTY_ELEMENTDATA to know how much to inflate when
     * first element is added.
     */
    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};

    /**
     * The array buffer into which the elements of the ArrayList are stored.
     * The capacity of the ArrayList is the length of this array buffer. Any
     * empty ArrayList with elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA
     * will be expanded to DEFAULT_CAPACITY when the first element is added.
     */
    transient Object[] elementData; // non-private to simplify nested class access

    /**
     * The size of the ArrayList (the number of elements it contains).
     *
     * @serial
     */
    private int size;

    /**
     * Constructs an empty list with the specified initial capacity.
     *
     * @param  initialCapacity  the initial capacity of the list
     * @throws IllegalArgumentException if the specified initial capacity
     *         is negative
     */
    public ArrayList(int initialCapacity) {
        if (initialCapacity > 0) {
            this.elementData = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            this.elementData = EMPTY_ELEMENTDATA;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: "+
                                               initialCapacity);
        }
    }

    /**
     * Constructs an empty list with an initial capacity of ten.
     */
    public ArrayList() {
        this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }

    /**
     * Constructs a list containing the elements of the specified
     * collection, in the order they are returned by the collection's
     * iterator.
     *
     * @param c the collection whose elements are to be placed into this list
     * @throws NullPointerException if the specified collection is null
     */
    public ArrayList(Collection<? extends E> c) {
        elementData = c.toArray();
        if ((size = elementData.length) != 0) {
            // c.toArray might (incorrectly) not return Object[] (see 6260652)
            if (elementData.getClass() != Object[].class)
                elementData = Arrays.copyOf(elementData, size, Object[].class);
        } else {
            // replace with empty array.
            this.elementData = EMPTY_ELEMENTDATA;
        }
    }

    /**
     * Trims the capacity of this <tt>ArrayList</tt> instance to be the
     * list's current size.  An application can use this operation to minimize
     * the storage of an <tt>ArrayList</tt> instance.
     */
    public void trimToSize() {
        modCount++;
        if (size < elementData.length) {
            elementData = (size == 0)
              ? EMPTY_ELEMENTDATA
              : Arrays.copyOf(elementData, size);
        }
    }

    /**
     * Increases the capacity of this <tt>ArrayList</tt> instance, if
     * necessary, to ensure that it can hold at least the number of elements
     * specified by the minimum capacity argument.
     *
     * @param   minCapacity   the desired minimum capacity
     */
    public void ensureCapacity(int minCapacity) {
        int minExpand = (elementData != DEFAULTCAPACITY_EMPTY_ELEMENTDATA)
            // any size if not default element table
            ? 0
            // larger than default for default empty table. It's already
            // supposed to be at default size.
            : DEFAULT_CAPACITY;

        if (minCapacity > minExpand) {
            ensureExplicitCapacity(minCapacity);
        }
    }

    private static int calculateCapacity(Object[] elementData, int minCapacity) {
        if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
            return Math.max(DEFAULT_CAPACITY, minCapacity);
        }
        return minCapacity;
        }

    private void ensureCapacityInternal(int minCapacity) {
        ensureExplicitCapacity(calculateCapacity(elementData, minCapacity));
    }

    private void ensureExplicitCapacity(int minCapacity) {
        modCount++;

        // overflow-conscious code
        if (minCapacity - elementData.length > 0)
            grow(minCapacity);
    }

    /**
     * The maximum size of array to allocate.
     * Some VMs reserve some header words in an array.
     * Attempts to allocate larger arrays may result in
     * OutOfMemoryError: Requested array size exceeds VM limit
     */
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    /**
     * Increases the capacity to ensure that it can hold at least the
     * number of elements specified by the minimum capacity argument.
     *
     * @param minCapacity the desired minimum capacity
     */
    private void grow(int minCapacity) {
        // overflow-conscious code
        int oldCapacity = elementData.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if (newCapacity - minCapacity < 0)
            newCapacity = minCapacity;
        if (newCapacity - MAX_ARRAY_SIZE > 0)
            newCapacity = hugeCapacity(minCapacity);
        // minCapacity is usually close to size, so this is a win:
        elementData = Arrays.copyOf(elementData, newCapacity);
    }

    private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0) // overflow
            throw new OutOfMemoryError();
        return (minCapacity > MAX_ARRAY_SIZE) ?
            Integer.MAX_VALUE :
            MAX_ARRAY_SIZE;
    }

    /**
     * Returns the number of elements in this list.
     *
     * @return the number of elements in this list
     */
    public int size() {
        return size;
    }

    /**
     * Returns <tt>true</tt> if this list contains no elements.
     *
     * @return <tt>true</tt> if this list contains no elements
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns <tt>true</tt> if this list contains the specified element.
     * More formally, returns <tt>true</tt> if and only if this list contains
     * at least one element <tt>e</tt> such that
     * <tt>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e))</tt>.
     *
     * @param o element whose presence in this list is to be tested
     * @return <tt>true</tt> if this list contains the specified element
     */
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    /**
     * Returns the index of the first occurrence of the specified element
     * in this list, or -1 if this list does not contain the element.
     * More formally, returns the lowest index <tt>i</tt> such that
     * <tt>(o==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;o.equals(get(i)))</tt>,
     * or -1 if there is no such index.
     */
    public int indexOf(Object o) {
        if (o == null) {
            for (int i = 0; i < size; i++)
                if (elementData[i]==null)
                    return i;
        } else {
            for (int i = 0; i < size; i++)
                if (o.equals(elementData[i]))
                    return i;
        }
        return -1;
    }

    /**
     * Returns the index of the last occurrence of the specified element
     * in this list, or -1 if this list does not contain the element.
     * More formally, returns the highest index <tt>i</tt> such that
     * <tt>(o==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;o.equals(get(i)))</tt>,
     * or -1 if there is no such index.
     */
    public int lastIndexOf(Object o) {
        if (o == null) {
            for (int i = size-1; i >= 0; i--)
                if (elementData[i]==null)
                    return i;
        } else {
            for (int i = size-1; i >= 0; i--)
                if (o.equals(elementData[i]))
                    return i;
        }
        return -1;
    }

    /**
     * Returns a shallow copy of this <tt>ArrayList</tt> instance.  (The
     * elements themselves are not copied.)
     *
     * @return a clone of this <tt>ArrayList</tt> instance
     */
    public Object clone() {
        try {
            ArrayList<?> v = (ArrayList<?>) super.clone();
            v.elementData = Arrays.copyOf(elementData, size);
            v.modCount = 0;
            return v;
        } catch (CloneNotSupportedException e) {
            // this shouldn't happen, since we are Cloneable
            throw new InternalError(e);
        }
    }

    /**
     * Returns an array containing all of the elements in this list
     * in proper sequence (from first to last element).
     *
     * <p>The returned array will be "safe" in that no references to it are
     * maintained by this list.  (In other words, this method must allocate
     * a new array).  The caller is thus free to modify the returned array.
     *
     * <p>This method acts as bridge between array-based and collection-based
     * APIs.
     *
     * @return an array containing all of the elements in this list in
     *         proper sequence
     */
    public Object[] toArray() {
        return Arrays.copyOf(elementData, size);
    }

    /**
     * Returns an array containing all of the elements in this list in proper
     * sequence (from first to last element); the runtime type of the returned
     * array is that of the specified array.  If the list fits in the
     * specified array, it is returned therein.  Otherwise, a new array is
     * allocated with the runtime type of the specified array and the size of
     * this list.
     *
     * <p>If the list fits in the specified array with room to spare
     * (i.e., the array has more elements than the list), the element in
     * the array immediately following the end of the collection is set to
     * <tt>null</tt>.  (This is useful in determining the length of the
     * list <i>only</i> if the caller knows that the list does not contain
     * any null elements.)
     *
     * @param a the array into which the elements of the list are to
     *          be stored, if it is big enough; otherwise, a new array of the
     *          same runtime type is allocated for this purpose.
     * @return an array containing the elements of the list
     * @throws ArrayStoreException if the runtime type of the specified array
     *         is not a supertype of the runtime type of every element in
     *         this list
     * @throws NullPointerException if the specified array is null
     */
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        if (a.length < size)
            // Make a new array of a's runtime type, but my contents:
            return (T[]) Arrays.copyOf(elementData, size, a.getClass());
        System.arraycopy(elementData, 0, a, 0, size);
        if (a.length > size)
            a[size] = null;
        return a;
    }

    // Positional Access Operations

    @SuppressWarnings("unchecked")
    E elementData(int index) {
        return (E) elementData[index];
    }

    /**
     * Returns the element at the specified position in this list.
     *
     * @param  index index of the element to return
     * @return the element at the specified position in this list
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    public E get(int index) {
        rangeCheck(index);

        return elementData(index);
    }

    /**
     * Replaces the element at the specified position in this list with
     * the specified element.
     *
     * @param index index of the element to replace
     * @param element element to be stored at the specified position
     * @return the element previously at the specified position
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    public E set(int index, E element) {
        rangeCheck(index);

        E oldValue = elementData(index);
        elementData[index] = element;
        return oldValue;
    }

    /**
     * Appends the specified element to the end of this list.
     *
     * @param e element to be appended to this list
     * @return <tt>true</tt> (as specified by {@link Collection#add})
     */
    public boolean add(E e) {
        ensureCapacityInternal(size + 1);  // Increments modCount!!
        elementData[size++] = e;
        return true;
    }

    /**
     * Inserts the specified element at the specified position in this
     * list. Shifts the element currently at that position (if any) and
     * any subsequent elements to the right (adds one to their indices).
     *
     * @param index index at which the specified element is to be inserted
     * @param element element to be inserted
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    public void add(int index, E element) {
        rangeCheckForAdd(index);

        ensureCapacityInternal(size + 1);  // Increments modCount!!
        System.arraycopy(elementData, index, elementData, index + 1,
                         size - index);
        elementData[index] = element;
        size++;
    }

    /**
     * Removes the element at the specified position in this list.
     * Shifts any subsequent elements to the left (subtracts one from their
     * indices).
     *
     * @param index the index of the element to be removed
     * @return the element that was removed from the list
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    public E remove(int index) {
        rangeCheck(index);

        modCount++;
        E oldValue = elementData(index);

        int numMoved = size - index - 1;
        if (numMoved > 0)
            System.arraycopy(elementData, index+1, elementData, index,
                             numMoved);
        elementData[--size] = null; // clear to let GC do its work

        return oldValue;
    }

    /**
     * Removes the first occurrence of the specified element from this list,
     * if it is present.  If the list does not contain the element, it is
     * unchanged.  More formally, removes the element with the lowest index
     * <tt>i</tt> such that
     * <tt>(o==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;o.equals(get(i)))</tt>
     * (if such an element exists).  Returns <tt>true</tt> if this list
     * contained the specified element (or equivalently, if this list
     * changed as a result of the call).
     *
     * @param o element to be removed from this list, if present
     * @return <tt>true</tt> if this list contained the specified element
     */
    public boolean remove(Object o) {
        if (o == null) {
            for (int index = 0; index < size; index++)
                if (elementData[index] == null) {
                    fastRemove(index);
                    return true;
                }
        } else {
            for (int index = 0; index < size; index++)
                if (o.equals(elementData[index])) {
                    fastRemove(index);
                    return true;
                }
        }
        return false;
    }

    /*
     * Private remove method that skips bounds checking and does not
     * return the value removed.
     */
    private void fastRemove(int index) {
        modCount++;
        int numMoved = size - index - 1;
        if (numMoved > 0)
            System.arraycopy(elementData, index+1, elementData, index,
                             numMoved);
        elementData[--size] = null; // clear to let GC do its work
    }

    /**
     * Removes all of the elements from this list.  The list will
     * be empty after this call returns.
     */
    public void clear() {
        modCount++;

        // clear to let GC do its work
        for (int i = 0; i < size; i++)
            elementData[i] = null;

        size = 0;
    }

    /**
     * Appends all of the elements in the specified collection to the end of
     * this list, in the order that they are returned by the
     * specified collection's Iterator.  The behavior of this operation is
     * undefined if the specified collection is modified while the operation
     * is in progress.  (This implies that the behavior of this call is
     * undefined if the specified collection is this list, and this
     * list is nonempty.)
     *
     * @param c collection containing elements to be added to this list
     * @return <tt>true</tt> if this list changed as a result of the call
     * @throws NullPointerException if the specified collection is null
     */
    public boolean addAll(Collection<? extends E> c) {
        Object[] a = c.toArray();
        int numNew = a.length;
        ensureCapacityInternal(size + numNew);  // Increments modCount
        System.arraycopy(a, 0, elementData, size, numNew);
        size += numNew;
        return numNew != 0;
    }

    /**
     * Inserts all of the elements in the specified collection into this
     * list, starting at the specified position.  Shifts the element
     * currently at that position (if any) and any subsequent elements to
     * the right (increases their indices).  The new elements will appear
     * in the list in the order that they are returned by the
     * specified collection's iterator.
     *
     * @param index index at which to insert the first element from the
     *              specified collection
     * @param c collection containing elements to be added to this list
     * @return <tt>true</tt> if this list changed as a result of the call
     * @throws IndexOutOfBoundsException {@inheritDoc}
     * @throws NullPointerException if the specified collection is null
     */
    public boolean addAll(int index, Collection<? extends E> c) {
        rangeCheckForAdd(index);

        Object[] a = c.toArray();
        int numNew = a.length;
        ensureCapacityInternal(size + numNew);  // Increments modCount

        int numMoved = size - index;
        if (numMoved > 0)
            System.arraycopy(elementData, index, elementData, index + numNew,
                             numMoved);

        System.arraycopy(a, 0, elementData, index, numNew);
        size += numNew;
        return numNew != 0;
    }

    /**
     * Removes from this list all of the elements whose index is between
     * {@code fromIndex}, inclusive, and {@code toIndex}, exclusive.
     * Shifts any succeeding elements to the left (reduces their index).
     * This call shortens the list by {@code (toIndex - fromIndex)} elements.
     * (If {@code toIndex==fromIndex}, this operation has no effect.)
     *
     * @throws IndexOutOfBoundsException if {@code fromIndex} or
     *         {@code toIndex} is out of range
     *         ({@code fromIndex < 0 ||
     *          fromIndex >= size() ||
     *          toIndex > size() ||
     *          toIndex < fromIndex})
     */
    protected void removeRange(int fromIndex, int toIndex) {
        modCount++;
        int numMoved = size - toIndex;
        System.arraycopy(elementData, toIndex, elementData, fromIndex,
                         numMoved);

        // clear to let GC do its work
        int newSize = size - (toIndex-fromIndex);
        for (int i = newSize; i < size; i++) {
            elementData[i] = null;
        }
        size = newSize;
    }

    /**
     * Checks if the given index is in range.  If not, throws an appropriate
     * runtime exception.  This method does *not* check if the index is
     * negative: It is always used immediately prior to an array access,
     * which throws an ArrayIndexOutOfBoundsException if index is negative.
     */
    private void rangeCheck(int index) {
        if (index >= size)
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    /**
     * A version of rangeCheck used by add and addAll.
     */
    private void rangeCheckForAdd(int index) {
        if (index > size || index < 0)
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    /**
     * Constructs an IndexOutOfBoundsException detail message.
     * Of the many possible refactorings of the error handling code,
     * this "outlining" performs best with both server and client VMs.
     */
    private String outOfBoundsMsg(int index) {
        return "Index: "+index+", Size: "+size;
    }

    /**
     * Removes from this list all of its elements that are contained in the
     * specified collection.
     *
     * @param c collection containing elements to be removed from this list
     * @return {@code true} if this list changed as a result of the call
     * @throws ClassCastException if the class of an element of this list
     *         is incompatible with the specified collection
     * (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if this list contains a null element and the
     *         specified collection does not permit null elements
     * (<a href="Collection.html#optional-restrictions">optional</a>),
     *         or if the specified collection is null
     * @see Collection#contains(Object)
     */
    public boolean removeAll(Collection<?> c) {
        Objects.requireNonNull(c);
        return batchRemove(c, false);
    }

    /**
     * Retains only the elements in this list that are contained in the
     * specified collection.  In other words, removes from this list all
     * of its elements that are not contained in the specified collection.
     *
     * @param c collection containing elements to be retained in this list
     * @return {@code true} if this list changed as a result of the call
     * @throws ClassCastException if the class of an element of this list
     *         is incompatible with the specified collection
     * (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if this list contains a null element and the
     *         specified collection does not permit null elements
     * (<a href="Collection.html#optional-restrictions">optional</a>),
     *         or if the specified collection is null
     * @see Collection#contains(Object)
     */
    public boolean retainAll(Collection<?> c) {
        Objects.requireNonNull(c);
        return batchRemove(c, true);
    }

    private boolean batchRemove(Collection<?> c, boolean complement) {
        final Object[] elementData = this.elementData;
        int r = 0, w = 0;
        boolean modified = false;
        try {
            for (; r < size; r++)
                if (c.contains(elementData[r]) == complement)
                    elementData[w++] = elementData[r];
        } finally {
            // Preserve behavioral compatibility with AbstractCollection,
            // even if c.contains() throws.
            if (r != size) {
                System.arraycopy(elementData, r,
                                 elementData, w,
                                 size - r);
                w += size - r;
            }
            if (w != size) {
                // clear to let GC do its work
                for (int i = w; i < size; i++)
                    elementData[i] = null;
                modCount += size - w;
                size = w;
                modified = true;
            }
        }
        return modified;
    }

    /**
     * Save the state of the <tt>ArrayList</tt> instance to a stream (that
     * is, serialize it).
     *
     * @serialData The length of the array backing the <tt>ArrayList</tt>
     *             instance is emitted (int), followed by all of its elements
     *             (each an <tt>Object</tt>) in the proper order.
     */
    private void writeObject(java.io.ObjectOutputStream s)
        throws java.io.IOException{
        // Write out element count, and any hidden stuff
        int expectedModCount = modCount;
        s.defaultWriteObject();

        // Write out size as capacity for behavioural compatibility with clone()
        s.writeInt(size);

        // Write out all elements in the proper order.
        for (int i=0; i<size; i++) {
            s.writeObject(elementData[i]);
        }

        if (modCount != expectedModCount) {
            throw new ConcurrentModificationException();
        }
    }

    /**
     * Reconstitute the <tt>ArrayList</tt> instance from a stream (that is,
     * deserialize it).
     */
    private void readObject(java.io.ObjectInputStream s)
        throws java.io.IOException, ClassNotFoundException {
        elementData = EMPTY_ELEMENTDATA;

        // Read in size, and any hidden stuff
        s.defaultReadObject();

        // Read in capacity
        s.readInt(); // ignored

        if (size > 0) {
            // be like clone(), allocate array based upon size not capacity
            int capacity = calculateCapacity(elementData, size);
            SharedSecrets.getJavaOISAccess().checkArray(s, Object[].class, capacity);
            ensureCapacityInternal(size);

            Object[] a = elementData;
            // Read in all elements in the proper order.
            for (int i=0; i<size; i++) {
                a[i] = s.readObject();
            }
        }
    }

    /**
     * Returns a list iterator over the elements in this list (in proper
     * sequence), starting at the specified position in the list.
     * The specified index indicates the first element that would be
     * returned by an initial call to {@link ListIterator#next next}.
     * An initial call to {@link ListIterator#previous previous} would
     * return the element with the specified index minus one.
     *
     * <p>The returned list iterator is <a href="#fail-fast"><i>fail-fast</i></a>.
     *
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    public ListIterator<E> listIterator(int index) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException("Index: "+index);
        return new ListItr(index);
    }

    /**
     * Returns a list iterator over the elements in this list (in proper
     * sequence).
     *
     * <p>The returned list iterator is <a href="#fail-fast"><i>fail-fast</i></a>.
     *
     * @see #listIterator(int)
     */
    public ListIterator<E> listIterator() {
        return new ListItr(0);
    }

    /**
     * Returns an iterator over the elements in this list in proper sequence.
     *
     * <p>The returned iterator is <a href="#fail-fast"><i>fail-fast</i></a>.
     *
     * @return an iterator over the elements in this list in proper sequence
     */
    public Iterator<E> iterator() {
        return new Itr();
    }

    /**
     * An optimized version of AbstractList.Itr
     */
    private class Itr implements Iterator<E> {
        int cursor;       // index of next element to return
        int lastRet = -1; // index of last element returned; -1 if no such
        int expectedModCount = modCount;

        public boolean hasNext() {
            return cursor != size;
        }

        @SuppressWarnings("unchecked")
        public E next() {
            checkForComodification();
            int i = cursor;
            if (i >= size)
                throw new NoSuchElementException();
            Object[] elementData = ArrayList.this.elementData;
            if (i >= elementData.length)
                throw new ConcurrentModificationException();
            cursor = i + 1;
            return (E) elementData[lastRet = i];
        }

        public void remove() {
            if (lastRet < 0)
                throw new IllegalStateException();
            checkForComodification();

            try {
                ArrayList.this.remove(lastRet);
                cursor = lastRet;
                lastRet = -1;
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        @SuppressWarnings("unchecked")
        public void forEachRemaining(Consumer<? super E> consumer) {
            Objects.requireNonNull(consumer);
            final int size = ArrayList.this.size;
            int i = cursor;
            if (i >= size) {
                return;
            }
            final Object[] elementData = ArrayList.this.elementData;
            if (i >= elementData.length) {
                throw new ConcurrentModificationException();
            }
            while (i != size && modCount == expectedModCount) {
                consumer.accept((E) elementData[i++]);
            }
            // update once at end of iteration to reduce heap write traffic
            cursor = i;
            lastRet = i - 1;
            checkForComodification();
        }

        final void checkForComodification() {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
        }
    }

    /**
     * An optimized version of AbstractList.ListItr
     */
    private class ListItr extends Itr implements ListIterator<E> {
        ListItr(int index) {
            super();
            cursor = index;
        }

        public boolean hasPrevious() {
            return cursor != 0;
        }

        public int nextIndex() {
            return cursor;
        }

        public int previousIndex() {
            return cursor - 1;
        }

        @SuppressWarnings("unchecked")
        public E previous() {
            checkForComodification();
            int i = cursor - 1;
            if (i < 0)
                throw new NoSuchElementException();
            Object[] elementData = ArrayList.this.elementData;
            if (i >= elementData.length)
                throw new ConcurrentModificationException();
            cursor = i;
            return (E) elementData[lastRet = i];
        }

        public void set(E e) {
            if (lastRet < 0)
                throw new IllegalStateException();
            checkForComodification();

            try {
                ArrayList.this.set(lastRet, e);
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        public void add(E e) {
            checkForComodification();

            try {
                int i = cursor;
                ArrayList.this.add(i, e);
                cursor = i + 1;
                lastRet = -1;
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }
    }

    /**
     * Returns a view of the portion of this list between the specified
     * {@code fromIndex}, inclusive, and {@code toIndex}, exclusive.  (If
     * {@code fromIndex} and {@code toIndex} are equal, the returned list is
     * empty.)  The returned list is backed by this list, so non-structural
     * changes in the returned list are reflected in this list, and vice-versa.
     * The returned list supports all of the optional list operations.
     *
     * <p>This method eliminates the need for explicit range operations (of
     * the sort that commonly exist for arrays).  Any operation that expects
     * a list can be used as a range operation by passing a subList view
     * instead of a whole list.  For example, the following idiom
     * removes a range of elements from a list:
     * <pre>
     *      list.subList(from, to).clear();
     * </pre>
     * Similar idioms may be constructed for {@link #indexOf(Object)} and
     * {@link #lastIndexOf(Object)}, and all of the algorithms in the
     * {@link Collections} class can be applied to a subList.
     *
     * <p>The semantics of the list returned by this method become undefined if
     * the backing list (i.e., this list) is <i>structurally modified</i> in
     * any way other than via the returned list.  (Structural modifications are
     * those that change the size of this list, or otherwise perturb it in such
     * a fashion that iterations in progress may yield incorrect results.)
     *
     * @throws IndexOutOfBoundsException {@inheritDoc}
     * @throws IllegalArgumentException {@inheritDoc}
     */
    public List<E> subList(int fromIndex, int toIndex) {
        subListRangeCheck(fromIndex, toIndex, size);
        return new SubList(this, 0, fromIndex, toIndex);
    }

    static void subListRangeCheck(int fromIndex, int toIndex, int size) {
        if (fromIndex < 0)
            throw new IndexOutOfBoundsException("fromIndex = " + fromIndex);
        if (toIndex > size)
            throw new IndexOutOfBoundsException("toIndex = " + toIndex);
        if (fromIndex > toIndex)
            throw new IllegalArgumentException("fromIndex(" + fromIndex +
                                               ") > toIndex(" + toIndex + ")");
    }

    private class SubList extends AbstractList<E> implements RandomAccess {
        private final AbstractList<E> parent;
        private final int parentOffset;
        private final int offset;
        int size;

        SubList(AbstractList<E> parent,
                int offset, int fromIndex, int toIndex) {
            this.parent = parent;
            this.parentOffset = fromIndex;
            this.offset = offset + fromIndex;
            this.size = toIndex - fromIndex;
            this.modCount = ArrayList.this.modCount;
        }

        public E set(int index, E e) {
            rangeCheck(index);
            checkForComodification();
            E oldValue = ArrayList.this.elementData(offset + index);
            ArrayList.this.elementData[offset + index] = e;
            return oldValue;
        }

        public E get(int index) {
            rangeCheck(index);
            checkForComodification();
            return ArrayList.this.elementData(offset + index);
        }

        public int size() {
            checkForComodification();
            return this.size;
        }

        public void add(int index, E e) {
            rangeCheckForAdd(index);
            checkForComodification();
            parent.add(parentOffset + index, e);
            this.modCount = parent.modCount;
            this.size++;
        }

        public E remove(int index) {
            rangeCheck(index);
            checkForComodification();
            E result = parent.remove(parentOffset + index);
            this.modCount = parent.modCount;
            this.size--;
            return result;
        }

        protected void removeRange(int fromIndex, int toIndex) {
            checkForComodification();
            parent.removeRange(parentOffset + fromIndex,
                               parentOffset + toIndex);
            this.modCount = parent.modCount;
            this.size -= toIndex - fromIndex;
        }

        public boolean addAll(Collection<? extends E> c) {
            return addAll(this.size, c);
        }

        public boolean addAll(int index, Collection<? extends E> c) {
            rangeCheckForAdd(index);
            int cSize = c.size();
            if (cSize==0)
                return false;

            checkForComodification();
            parent.addAll(parentOffset + index, c);
            this.modCount = parent.modCount;
            this.size += cSize;
            return true;
        }

        public Iterator<E> iterator() {
            return listIterator();
        }

        public ListIterator<E> listIterator(final int index) {
            checkForComodification();
            rangeCheckForAdd(index);
            final int offset = this.offset;

            return new ListIterator<E>() {
                int cursor = index;
                int lastRet = -1;
                int expectedModCount = ArrayList.this.modCount;

                public boolean hasNext() {
                    return cursor != SubList.this.size;
                }

                @SuppressWarnings("unchecked")
                public E next() {
                    checkForComodification();
                    int i = cursor;
                    if (i >= SubList.this.size)
                        throw new NoSuchElementException();
                    Object[] elementData = ArrayList.this.elementData;
                    if (offset + i >= elementData.length)
                        throw new ConcurrentModificationException();
                    cursor = i + 1;
                    return (E) elementData[offset + (lastRet = i)];
                }

                public boolean hasPrevious() {
                    return cursor != 0;
                }

                @SuppressWarnings("unchecked")
                public E previous() {
                    checkForComodification();
                    int i = cursor - 1;
                    if (i < 0)
                        throw new NoSuchElementException();
                    Object[] elementData = ArrayList.this.elementData;
                    if (offset + i >= elementData.length)
                        throw new ConcurrentModificationException();
                    cursor = i;
                    return (E) elementData[offset + (lastRet = i)];
                }

                @SuppressWarnings("unchecked")
                public void forEachRemaining(Consumer<? super E> consumer) {
                    Objects.requireNonNull(consumer);
                    final int size = SubList.this.size;
                    int i = cursor;
                    if (i >= size) {
                        return;
                    }
                    final Object[] elementData = ArrayList.this.elementData;
                    if (offset + i >= elementData.length) {
                        throw new ConcurrentModificationException();
                    }
                    while (i != size && modCount == expectedModCount) {
                        consumer.accept((E) elementData[offset + (i++)]);
                    }
                    // update once at end of iteration to reduce heap write traffic
                    lastRet = cursor = i;
                    checkForComodification();
                }

                public int nextIndex() {
                    return cursor;
                }

                public int previousIndex() {
                    return cursor - 1;
                }

                public void remove() {
                    if (lastRet < 0)
                        throw new IllegalStateException();
                    checkForComodification();

                    try {
                        SubList.this.remove(lastRet);
                        cursor = lastRet;
                        lastRet = -1;
                        expectedModCount = ArrayList.this.modCount;
                    } catch (IndexOutOfBoundsException ex) {
                        throw new ConcurrentModificationException();
                    }
                }

                public void set(E e) {
                    if (lastRet < 0)
                        throw new IllegalStateException();
                    checkForComodification();

                    try {
                        ArrayList.this.set(offset + lastRet, e);
                    } catch (IndexOutOfBoundsException ex) {
                        throw new ConcurrentModificationException();
                    }
                }

                public void add(E e) {
                    checkForComodification();

                    try {
                        int i = cursor;
                        SubList.this.add(i, e);
                        cursor = i + 1;
                        lastRet = -1;
                        expectedModCount = ArrayList.this.modCount;
                    } catch (IndexOutOfBoundsException ex) {
                        throw new ConcurrentModificationException();
                    }
                }

                final void checkForComodification() {
                    if (expectedModCount != ArrayList.this.modCount)
                        throw new ConcurrentModificationException();
                }
            };
        }

        public List<E> subList(int fromIndex, int toIndex) {
            subListRangeCheck(fromIndex, toIndex, size);
            return new SubList(this, offset, fromIndex, toIndex);
        }

        private void rangeCheck(int index) {
            if (index < 0 || index >= this.size)
                throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }

        private void rangeCheckForAdd(int index) {
            if (index < 0 || index > this.size)
                throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }

        private String outOfBoundsMsg(int index) {
            return "Index: "+index+", Size: "+this.size;
        }

        private void checkForComodification() {
            if (ArrayList.this.modCount != this.modCount)
                throw new ConcurrentModificationException();
        }

        public Spliterator<E> spliterator() {
            checkForComodification();
            return new ArrayListSpliterator<E>(ArrayList.this, offset,
                                               offset + this.size, this.modCount);
        }
    }

    @Override
    public void forEach(Consumer<? super E> action) {
        Objects.requireNonNull(action);
        final int expectedModCount = modCount;
        @SuppressWarnings("unchecked")
        final E[] elementData = (E[]) this.elementData;
        final int size = this.size;
        for (int i=0; modCount == expectedModCount && i < size; i++) {
            action.accept(elementData[i]);
        }
        if (modCount != expectedModCount) {
            throw new ConcurrentModificationException();
        }
    }

    /**
     * Creates a <em><a href="Spliterator.html#binding">late-binding</a></em>
     * and <em>fail-fast</em> {@link Spliterator} over the elements in this
     * list.
     *
     * <p>The {@code Spliterator} reports {@link Spliterator#SIZED},
     * {@link Spliterator#SUBSIZED}, and {@link Spliterator#ORDERED}.
     * Overriding implementations should document the reporting of additional
     * characteristic values.
     *
     * @return a {@code Spliterator} over the elements in this list
     * @since 1.8
     */
    @Override
    public Spliterator<E> spliterator() {
        return new ArrayListSpliterator<>(this, 0, -1, 0);
    }

    /** Index-based split-by-two, lazily initialized Spliterator */
    static final class ArrayListSpliterator<E> implements Spliterator<E> {

        /*
         * If ArrayLists were immutable, or structurally immutable (no
         * adds, removes, etc), we could implement their spliterators
         * with Arrays.spliterator. Instead we detect as much
         * interference during traversal as practical without
         * sacrificing much performance. We rely primarily on
         * modCounts. These are not guaranteed to detect concurrency
         * violations, and are sometimes overly conservative about
         * within-thread interference, but detect enough problems to
         * be worthwhile in practice. To carry this out, we (1) lazily
         * initialize fence and expectedModCount until the latest
         * point that we need to commit to the state we are checking
         * against; thus improving precision.  (This doesn't apply to
         * SubLists, that create spliterators with current non-lazy
         * values).  (2) We perform only a single
         * ConcurrentModificationException check at the end of forEach
         * (the most performance-sensitive method). When using forEach
         * (as opposed to iterators), we can normally only detect
         * interference after actions, not before. Further
         * CME-triggering checks apply to all other possible
         * violations of assumptions for example null or too-small
         * elementData array given its size(), that could only have
         * occurred due to interference.  This allows the inner loop
         * of forEach to run without any further checks, and
         * simplifies lambda-resolution. While this does entail a
         * number of checks, note that in the common case of
         * list.stream().forEach(a), no checks or other computation
         * occur anywhere other than inside forEach itself.  The other
         * less-often-used methods cannot take advantage of most of
         * these streamlinings.
         */

        private final ArrayList<E> list;
        private int index; // current index, modified on advance/split
        private int fence; // -1 until used; then one past last index
        private int expectedModCount; // initialized when fence set

        /** Create new spliterator covering the given  range */
        ArrayListSpliterator(ArrayList<E> list, int origin, int fence,
                             int expectedModCount) {
            this.list = list; // OK if null unless traversed
            this.index = origin;
            this.fence = fence;
            this.expectedModCount = expectedModCount;
        }

        private int getFence() { // initialize fence to size on first use
            int hi; // (a specialized variant appears in method forEach)
            ArrayList<E> lst;
            if ((hi = fence) < 0) {
                if ((lst = list) == null)
                    hi = fence = 0;
                else {
                    expectedModCount = lst.modCount;
                    hi = fence = lst.size;
                }
            }
            return hi;
        }

        public ArrayListSpliterator<E> trySplit() {
            int hi = getFence(), lo = index, mid = (lo + hi) >>> 1;
            return (lo >= mid) ? null : // divide range in half unless too small
                new ArrayListSpliterator<E>(list, lo, index = mid,
                                            expectedModCount);
        }

        public boolean tryAdvance(Consumer<? super E> action) {
            if (action == null)
                throw new NullPointerException();
            int hi = getFence(), i = index;
            if (i < hi) {
                index = i + 1;
                @SuppressWarnings("unchecked") E e = (E)list.elementData[i];
                action.accept(e);
                if (list.modCount != expectedModCount)
                    throw new ConcurrentModificationException();
                return true;
            }
            return false;
        }

        public void forEachRemaining(Consumer<? super E> action) {
            int i, hi, mc; // hoist accesses and checks from loop
            ArrayList<E> lst; Object[] a;
            if (action == null)
                throw new NullPointerException();
            if ((lst = list) != null && (a = lst.elementData) != null) {
                if ((hi = fence) < 0) {
                    mc = lst.modCount;
                    hi = lst.size;
                }
                else
                    mc = expectedModCount;
                if ((i = index) >= 0 && (index = hi) <= a.length) {
                    for (; i < hi; ++i) {
                        @SuppressWarnings("unchecked") E e = (E) a[i];
                        action.accept(e);
                    }
                    if (lst.modCount == mc)
                        return;
                }
            }
            throw new ConcurrentModificationException();
        }

        public long estimateSize() {
            return (long) (getFence() - index);
        }

        public int characteristics() {
            return Spliterator.ORDERED | Spliterator.SIZED | Spliterator.SUBSIZED;
        }
    }

    @Override
    public boolean removeIf(Predicate<? super E> filter) {
        Objects.requireNonNull(filter);
        // figure out which elements are to be removed
        // any exception thrown from the filter predicate at this stage
        // will leave the collection unmodified
        int removeCount = 0;
        final BitSet removeSet = new BitSet(size);
        final int expectedModCount = modCount;
        final int size = this.size;
        for (int i=0; modCount == expectedModCount && i < size; i++) {
            @SuppressWarnings("unchecked")
            final E element = (E) elementData[i];
            if (filter.test(element)) {
                removeSet.set(i);
                removeCount++;
            }
        }
        if (modCount != expectedModCount) {
            throw new ConcurrentModificationException();
        }

        // shift surviving elements left over the spaces left by removed elements
        final boolean anyToRemove = removeCount > 0;
        if (anyToRemove) {
            final int newSize = size - removeCount;
            for (int i=0, j=0; (i < size) && (j < newSize); i++, j++) {
                i = removeSet.nextClearBit(i);
                elementData[j] = elementData[i];
            }
            for (int k=newSize; k < size; k++) {
                elementData[k] = null;  // Let gc do its work
            }
            this.size = newSize;
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
            modCount++;
        }

        return anyToRemove;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void replaceAll(UnaryOperator<E> operator) {
        Objects.requireNonNull(operator);
        final int expectedModCount = modCount;
        final int size = this.size;
        for (int i=0; modCount == expectedModCount && i < size; i++) {
            elementData[i] = operator.apply((E) elementData[i]);
        }
        if (modCount != expectedModCount) {
            throw new ConcurrentModificationException();
        }
        modCount++;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void sort(Comparator<? super E> c) {
        final int expectedModCount = modCount;
        Arrays.sort((E[]) elementData, 0, size, c);
        if (modCount != expectedModCount) {
            throw new ConcurrentModificationException();
        }
        modCount++;
    }
}

```

##### ArrayList  的数据结构是什么？

```

```

ArrayList的底层数据结构就是一个数组，数组元素的类型为Object类型，对ArrayList的所有操作底层都是基于数组的。

#### ArrayList 的线程安全特性？

```
public void add(E e) {
        checkForComodification();

        try {
            int i = cursor;
            ArrayList.this.add(i, e);
            cursor = i + 1;
            lastRet = -1;
            expectedModCount = modCount;
        } catch (IndexOutOfBoundsException ex) {
            throw new ConcurrentModificationException();
        }
    }
}
```

**对ArrayList进行添加元素的操作的时候是分两个步骤进行的，即第一步先在object[size]的位置上存放需要添加的元素；第二步将size的值增加1。由于这个过程在多线程的环境下是不能保证具有原子性的，因此ArrayList在多线程的环境下是线程不安全的。**

具体举例说明：在单线程运行的情况下，如果Size = 0，添加一个元素后，此元素在位置 0，而且Size=1；而如果是在多线程情况下，比如有两个线程，线程 A 先将元素存放在位置0。但是此时 CPU 调度线程A暂停，线程 B 得到运行的机会。线程B也向此ArrayList 添加元素，因为此时 Size 仍然等于 0 （注意哦，我们假设的是添加一个元素是要两个步骤哦，而线程A仅仅完成了步骤1），所以线程B也将元素存放在位置0。然后线程A和线程B都继续运行，都增 加 Size 的值。  那好，现在我们来看看 ArrayList 的情况，元素实际上只有一个，存放在位置 0，而Size却等于 2。这就是“线程不安全”了。

如果非要在多线程的环境下使用ArrayList，就需要保证它的线程安全性，通常有两种解决办法：第一，使用synchronized关键字；第二，可以用Collections类中的静态方法synchronizedList();对ArrayList进行调用即可。
————————————————
版权声明：本文为CSDN博主「王Z峰」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。csdn.net/weixin_36378917/article/details/81812210
原文链接：https://blog.csdn.net/weixin_36378917/article/details/81812210

#### ArrayList**的继承关系**

ArrayList继承AbstractList抽象父类，实现了List接口（规定了List的操作规范）、RandomAccess（可随机访问）、Cloneable（可拷贝）、Serializable（可序列化）。

#### ArrayList的成员变量

```

public class ArrayList<E> extends AbstractList<E>

        implements List<E>, RandomAccess, Cloneable, java.io.Serializable

{

    // 版本号
    private static final long serialVersionUID = 8683452581122892189L;
    // 缺省容量
    private static final int DEFAULT_CAPACITY = 10;
    // 空对象数组
    private static final Object[] EMPTY_ELEMENTDATA = {};
    // 缺省空对象数组
    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};
    // 元素数组
    transient Object[] elementData;
    // 实际元素大小，默认为0
    private int size;
    // 最大数组容量
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
}
```

csdn.net/weixin_36378917/article/details/81812210

ArrayList 底层是对象数组。默认初始长度10 ，

### 重要，ArrayList**的扩容机制**

1.add() : ensureCapacityIternal(size+1) 确定添加元素成功的最小容量。

2.ensureExplicityCapacity (minCapacity) 方法确定集合是否需要扩容。

3.如果需要扩容则调用grow方法 ，数组长度扩大为原来1.5倍。指定新容量旧数组的内容拷贝到新数组。





ArrayList**的indexOf(Object o)方法**源码：

indexOf(Object o)方法的作用是从头开始查找与指定元素相等的元素，如果找到，则返回找到的元素在元素数组中的下标，如果没有找到返回-1。与该方法类似的是lastIndexOf(Object o)方法，该方法的作用是从尾部开始查找与指定元素相等的元素。

查看该方法的源码可知，该方法从需要查找的元素是否为空的角度分为两种情况分别讨论。这也意味着该方法的参数可以是null元素，也意味着ArrayList集合中能够保存null元素。方法实现的逻辑也比较简单，直接循环遍历元素数组，通过equals方法来判断对象是否相同，相同就返回下标，找不到就返回-1。这也解释了为什么要把情况分为需要查找的对象是否为空两种情况讨论，不然的话空对象调用equals方法则会产生空指针异常。

```
// 从首开始查找数组里面是否存在指定元素
public int indexOf(Object o) {
    if (o == null) { // 查找的元素为空
        for (int i = 0; i < size; i++) // 遍历数组，找到第一个为空的元素，返回下标
            if (elementData[i]==null)
                return i;
    } else { // 查找的元素不为空
        for (int i = 0; i < size; i++) // 遍历数组，找到第一个和指定元素相等的元素，返回下标
            if (o.equals(elementData[i]))
                    return i;
    } 
    // 没有找到，返回空
    return -1;
}
```

#### ArrayList**的get(int index)方法**源码

get(int index)方法是返回指定下标处的元素的值。get函数会检查索引值是否合法（只检查是否大于size，而没有检查是否小于0）。如果所引致合法，则调用elementData(int index)方法获取值。在elementData(int index)方法中返回元素数组中指定下标的元素，并且对其进行了向下转型。

```
public E get(int index) {
    // 检验索引是否合法  
    rangeCheck(index);
    return elementData(index);
}
E elementData(int index) {

    return (E) elementData[index];

}
```



#### 笔试题： 实现一个ArrayLsit

#### ArrayList的三种遍历方法

```
import java.util.*;
 
public class Test{
 public static void main(String[] args) {
     List<String> list=new ArrayList<String>();
     list.add("Hello");
     list.add("World");
     list.add("HAHAHAHA");
     //第一种遍历方法使用 For-Each 遍历 List
     for (String str : list) {            //也可以改写 for(int i=0;i<list.size();i++) 这种形式
        System.out.println(str);
     }
 
     //第二种遍历，把链表变为数组相关的内容进行遍历
     String[] strArray=new String[list.size()];
     list.toArray(strArray);
     for(int i=0;i<strArray.length;i++) //这里也可以改写为  for(String str:strArray) 这种形式
     {
        System.out.println(strArray[i]);
     }
     
    //第三种遍历 使用迭代器进行相关遍历
     
     Iterator<String> ite=list.iterator();
     while(ite.hasNext())//判断下一个元素之后有值
     {
         System.out.println(ite.next());
     }
 }
}
```

### Java 处理日期时间常用方法  （2020.3.3）

使用什么可以方便做日期拆分 ；

答使用Calender类

```
Calendar c1 =Calendar.getInstance();
c1.set(2020,2,20);
int year=c1.get(Calendar.YEAR);
int  month=c1.get(Calendar.MONTH);
int  day =c1.get(Calendar.DAY_OF_MONTH);
System.out.println (" year" +year+ "month "+month+" day "+day  );
```

**GregorianCalendar类**

https://www.runoob.com/java/java-date-time.html

Calendar类实现了公历日历，GregorianCalendar是Calendar类的一个具体实现。

Calendar 的getInstance（）方法返回一个默认用当前的语言环境和时区初始化的GregorianCalendar对象。GregorianCalendar定义了两个字段：AD和BC。这是代表公历定义的两个时代

## 异常  

### 1、error 和 exception 的区别？

Error 类和 Exception 类的父类都是 Throwable 类。主要区别如下：
Error类： **一般是指与虚拟机相关的问题**，如：**系统崩溃，虚拟机错误，内存空间不足，**
**方法调用栈溢出等。这类错误将会导致应用程序中断，仅靠程序本身无法恢复和预防；**
Exception 类：**分为运行时异常和受检查的异常**。
**运行时异常：【如空指针异常、指定的类找不到、数组越界、方法传递参数错误、数**
**据类型转换错误】可以编译通过，但是一运行就停止了**，程序不会自己处理；
受检查异常：要么用 try … catch… 捕获，要么用 throws 声明抛出，交给父类处理。  

### 2、throw 和 throws 的区别？

throw：
throw **在方法体内部，表示抛出异常**，由方法体内部的语句处理；
throw 是具体向外抛出异常的动作，所以它抛出的是一个异常实例；
throws：
throws **在方法声明后面，表示如果抛出异常**，由该方法的调用者来进行异常的处理；
表示出现异常的可能性，并不一定会发生这种异常。  

### 常见的异常类有哪些？

NullPointerException：当应用程序试图访问空对象时，则抛出该异常。
SQLException：提供关于数据库访问错误或其他错误信息的异常。
IndexOutOfBoundsException：指示某排序索引（例如对数组、字符串或向量的排序）
超出范围时抛出。
FileNotFoundException：当试图打开指定路径名表示的文件失败时，抛出此异常。
IOException：当发生某种 I/O 异常时，抛出此异常。此类是失败或中断的 I/O 操作生成
的异常的通用类。
ClassCastException：当试图将对象强制转换为不是实例的子类时，抛出该异常。
IllegalArgumentException：抛出的异常表明向方法传递了一个不合法或不正确的参
数  

#### 主线程可以捕获到子线程的异常吗？

正常情况下，如果不做特殊的处理，在主线程中是不能够捕获到子线程中的异常的。
如果想要在主线程中捕获子线程的异常，我们可以用如下的方式进行处理，使用 Thread
的静态方法。
Thread.setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandle());  

### 泛型   2020-2-29

泛型提供了编译期的类型安全，确保你只能把正确类型的对象放入集合中，避免了在
运行时出现 **ClassCastException**。
线程设计的理念：“ 线程的问题应该线程自己本身来解决，而不要委托到外部 ”。
1、Java 的泛型是如何工作的 ? 什么是类型擦除 ?
泛型是通过类型擦除来实现的，编译器在编译时擦除了所有类型相关的信息，所以在
运行时不存在任何类型相关的信息。例如：List<String> 在运行时仅用一个 List 来表示。
这样做的目的，是确保能和 Java 5 之前的版本开发二进制类库进行兼容。
2、类型擦除
类型擦除：泛型信息只存在于代码编译阶段，在进入 JVM 之前，与泛型相关的信息
会被擦除掉，专业术语叫做类型擦除。
在泛型类被类型擦除的时候，之前泛型类中的类型参数部分如果没有指定上限，如 <T> 则
会被转译成普通的 Object 类型，如果指定了上限如 <T extends String> 则类型参数就被
替换成类型上限。
List<String> list = new ArrayList<String>();
1、两个 String 其实只有第一个起作用，后面一个没什么卵用，只不过 JDK7 才开始支持
List<String>list = new ArrayList<>; 这种写法。
2、第一个 String 就是告诉编译器，List 中存储的是 String 对象，也就是起类型检查的作
用，之后编译器会擦除泛型占位符，以保证兼容以前的代码。
3、什么是泛型中的限定通配符和非限定通配符 ?
限定通配符对类型进行了限制。有两种限定通配符，一种是<? extends T> 它通过确
保类型必须是 T 的子类来设定类型的上界，另一种是<? super T>它通过确保类型必须是
T 的父类来设定类型的下界。泛型类型必须用限定内的类型来进行初始化，否则会导致编
译错误。另一方面 <?> 表示了非限定通配符，因为 <?> 可以用任意类型来替代。
4、List<? extends T> 和 List <? super T> 之间有什么区别
?
这两个 List 的声明都是限定通配符的例子，List<? extends T> 可以接受任何继承自
T 的类型的 List，而List <? super T> 可以接受任何 T 的父类构成的 List。例如 List<?
extends Number> 可以接受 List<Integer> 或 List<Float>。
Array 不支持泛型，要用 List 代替 Array，因为 List 可以提供编译器的类型安全保证，
而 Array却不能。  

## 克隆和序列化  

### 1、如何实现对象的克隆？

两种方式：
1、实现 Cloneable 接口并重写 Object 类中的 clone() 方法；
2、实现 Serializable 接口，通过对象的序列化和反序列化实现克隆，可以实现真正的深克
隆。
注意：深克隆和浅克隆的区别：
1、浅克隆：拷贝对象和原始对象的引用类型引用同一个对象。浅克隆只是复制了对象的引
用地址，两个对象指向同一个内存地址，所以修改其中任意的值，另一个值都会随之变
化，这就是浅克隆（例：assign()）。
2、深克隆：拷贝对象和原始对象的引用类型引用不同对象。深拷贝是将对象及值复制过
来，两个对象修改其中任意的值另一个值不会改变，这就是深拷贝（例：JSON.parse() 和
JSON.stringify()，但是此方法无法复制函数类型）。
深克隆的实现就是在引用类型所在的类实现 Cloneable 接口，并使用 public 访问修
饰符重写 clone 方法；
Java 中定义的 clone 没有深浅之分，都是统一的调用 Object 的 clone 方法。为什
么会有深克隆的概念？是由于我们在实现的过程中刻意的嵌套了 clone 方法的调用。
也就是说深克隆就是在需要克隆的对象类型的类中重新实现克隆方法 clone()。

##### 2、什么是 Java 的序列化，如何实现 Java 的序列化？

对象序列化是一个用于将对象状态转换为字节流的过程，可以将其保存到磁盘文件中
或通过网络发送到任何其他程序。从字节流创建对象的相反的过程称为反序列化。而创建
的字节流是与平台无关的，在一个平台上序列化的对象可以在不同的平台上反序列化。序
列化是为了解决在对象流进行读写操作时所引发的问题。
序列化的实现：将需要被序列化的类实现 Serializable 接口，该接口没有需要实现的
方法，只是用于标注该对象是可被序列化的，然后使用一个输出流（如：
FileOutputStream）来构造一个 ObjectOutputStream 对象，接着使用
ObjectOutputStream 对象的 writeObject(Object obj) 方法可以将参数为 obj 的对象写
出，要恢复的话则使用输入流。
a）当你想把的内存中的对象状态保存到一个文件中或者数据库中时候；
b）当你想用套接字在网络上传送对象的时候；
c）当你想通过 RMI 传输对象的时候。  

#### Map的四种遍历方式

1.通过map.Set 获得key,用这个key去取值map.get(key);

2.通过map.entrySet.iterator 迭代器。

3.通过Map.Entry<String,String>  大容量时候使用。

4.Map.values遍历所有 values

https://www.runoob.com/java/java-collections.html

### 重要！每次必问必考HashMap

1.HashMap原理 与Hashtable  (2020bilibi 高频)

2.想做一个Map结构描述一下。（2020bilibi）

3.HashMap实现，扩容机制（2020探探 & 知乎后端社招面经）

4.ConcurrentHashMap 如何实现线程安全（2020探探 & 知乎后端社招面经）

以下：来源原创 三太子敖丙 [三太子敖丙](javascript:void(0);)

HashMap 是非常常用的数据结构，由数组和链表组合。的数据结构。

数组的每个地方都存在了key-value 在Java7 叫做Entry数组，在Java 8叫做Node

![](.\img\hashmap-key-value结构.png)

执行put 方法插入值的时候会通过Hash函数计算插入的位置，数组的长度是有限的，hash值一样就需要用链表保存hash值相同的插入值。

Node:每个节点都会保存自身的hash key  value :

```
    static class Node<K,V> implements Map.Entry<K,V> {
        final int hash;
        final K key;
        V value;
        Node<K,V> next;
        }
```

Entry 节点在插入链表的时候是怎样插入的？

java8之前是头插法，就是新来的值会取代原有的值，原有的值就顺推到链表中去，就像上面的例子一样，因为写这个代码的作者认为后来的值被查找的可能性更大一点，提升查找的效率。但是，**在java8之后，都是所用尾部插入了。**

为什幺尾插？

数据达到一定容量之后会进行扩容，就是rsize：

两个因素：

- Capacity：HashMap当前长度。
- LoadFactor：负载因子，默认值0.75f。

怎么扩容：

创建一个新的Entry数组，长度是原数组的2倍.

ReHash:遍历原来数组,把所有的Entry重新Hash到新数组。

是因为长度扩大以后，Hash的规则也随之改变。

Hash的公式---> index = HashCode（Key） & （Length - 1）

原来长度（Length）是8你位运算出来的值是2 ，新的长度是16你位运算出来的值明显不一样了。

说完扩容机制我们言归正传，为啥之前用头插法，java8之后改成尾插了呢？

现在往一个容量大小为2的put两个值，负载因子是0.75是不是我们在put第二个的时候就会进行resize？

2*0.75 = 1 所以插入第二个就要resize。

现在要在容量为2 的容器里面用不同线程插入ABC,假如我们在resize 之前打了端点，意味着数据都插入了但是还没有resize 那扩容前：链表指向 A--->B---C,单链表使用头插法，同一位置新元素总会被放到链表的头部。在旧的数组中同一条Entry链上的元素，通过重新计算索引位置后，可能被放到了数组的不同位置上。

![](.\img\环形链表.png)

几个线程调用完成后可能出现B--->A ,A-->B,环形链表。

JDK 1.7 头插，JDK1.8尾插。

java 1.8 之后链表有红黑树部分。红黑树的引入将原本的O(n)的时间复杂度降低到O(logN).使用头插会改变链表的顺序，但是使用尾插法，在扩容时会保持链表额的元素原本的顺序，就不会出现环的问题。

**Java7在多线程操作HashMap时可能引起死循环**，原因是扩容转移后前后链表顺序倒置，在转移过程中修改了原来链表中节点的引用关系。

Java8在同样的前提下并不会引起死循环，原因是扩容转移后前后链表顺序不变，保持之前节点的引用关系。

那是不是意味着Java8就可以把HashMap用在多线程中呢？

我认为即使不会出现死循环，但是通过源码看到put/get方法都没有加同步锁，多线程情况最容易出现的就是：无法保证上一秒put的值，下一秒get的时候还是原值，所以线程安全还是无法保证。

HashMap的默认初始长度是多少？

16

为什么是16

```
/**
 * The default initial capacity - MUST be a power of two.
 */
static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // aka 16
```

**JDK1.8的 1<<4 就是16** 

面试官您好，我们在创建HashMap的时候，阿里巴巴规范插件会提醒我们最好赋初值，而且最好是2的幂。

这样是为了位运算的方便，**位与运算比算数计算的效率高了很多**，之所以选择16，是为了服务将Key映射到index的算法。

我前面说了所有的key我们都会拿到他的hash，但是我们怎么尽可能的得到一个均匀分布的hash呢？

index的计算公式：index = HashCode（Key） & （Length- 1）

15的的二进制是1111，那10111011000010110100 &1111 十进制就是4

**那为啥用16不用别的呢？**

因为在使用不是2的幂的数字的时候，Length-1的值是所有二进制位全为1，这种情况下，index的结果等同于HashCode后几位的值。

只要输入的HashCode本身分布均匀，Hash算法的结果就是均匀的。

这是为了**实现均匀分布**。

那为啥重新equals 的时候需要重写hashCode 方法？

能用HashMap举例吗？

Object类中有两个方法，hashcode() ,equals()

其中equals 比较是内存地址，显然我们new了2个对象内存地址肯定不一样。

对于**值对象**，== 比较的是两个对象的值。

对于引用对象比较是对象地址。

HashMap是通过key的hashCode去寻找index的，那index一样就形成链表了，也就是“AB”和“BA”的index 可能都是2，在一个链表上。

去get的时候根据key 去计算hash 然后找到index为2，那怎么找到具体的“AB”还是“BA”.

**equals: 如果对equals重写，建议一定要对hashCode方法重写，以保证相同的对象返回相同的hash值，不同的对象返回不同的hash值。不然一个链表的对象，你哪里知道你要找的是哪个，到时候发现hashCode都一样**，这不是完犊子嘛。

上面说过他是**线程不安全的，那你能跟我聊聊你们是怎么处理HashMap在线程安全的场景么**？

#### **（1）美团面试题：Hashmap的结构，1.7和1.8有哪些区别，史上最深入的分析**

https://blog.csdn.net/qq_36520235/article/details/82417949?depth_1-utm_source=distribute.pc_relevant.none-task&utm_source=distribute.pc_relevant.none-task

**（1）.JDK 1.7 用的是头插法，而JDK1.8 及之后的使用的是尾插法，为什么这么做？**

**原因是 JDK1.7 使用的是单链表进行纵向延伸，多线程情况下容易出现环形链表循环问题，但是JDK1.8之后应为加入红黑树使用尾插法，能够避免出现逆序并且链表死循环的问题。**

（2）扩容后数据存储位置的计算方式也不一样：

 1.7 时候直接使用hash 值和需要扩容的二进制数进行&（这里就是为什么扩容必须是2的幂次，（hash）&(length-1)） 减少hash碰撞。

**2、而在JDK1.8的时候直接用了JDK1.7的时候计算的规律，也就是扩容前的原始位置+扩容的大小值=JDK1.8的计算方式，而不再是JDK1.7的那种异或的方法。但是这种方式就相当于只需要判断Hash值的新增参与运算的位是0还是1就直接迅速计算出了扩容后的储存方式。**

3.扩容对比：

![](.\img\扩容对比.png)

**（3）JDK1.7的时候使用的是数组+ 单链表的数据结构。但是在JDK1.8及之后时，使用的是数组+链表+红黑树的数据结构（当链表的深度达到8的时候，也就是默认阈值，就会自动扩容把链表转成红黑树的数据结构来把时间复杂度从O（n）变成O（logN）提高了效率）**

蘑菇街：为什么JDK1.8进行HashMap 优化的时候把链表转为红黑树的阈值是8不是7或者其他20？

如果选择6和8（如果链表小于等于6树还原转为链表，大于等于8转为树），**中间有个差值7可以有效防止链表和树频繁转换。**假设一下，如果设计成链表个数超过8则链表转换成树结构，链表个数小于8则树结构转换成链表，如果一个HashMap不停的插入、删除元素，链表个数在8左右徘徊，就会频繁的发生树转链表、链表转树，效率会很低。

**为什么在JDK1.7的时候是先进行扩容后进行插入，而在JDK1.8的时候则是先插入后进行扩容的呢？**

由于treeNodes的大小是常规的两倍，因此我们仅在容器包含足够的节点以保证使用是才使用它们，当它们变的太小（由于移除和调整）时，它们会被转换会普通节点node节点，容器中节点分布在hash桶中频率遵循泊松分布。桶的长度超过8的概率非常小，所以作者应该是根据概率统计选择8 作为阀值。

```
	//Java中解释的原因
   * Because TreeNodes are about twice the size of regular nodes, we
     * use them only when bins contain enough nodes to warrant use
     * (see TREEIFY_THRESHOLD). And when they become too small (due to
     * removal or resizing) they are converted back to plain bins.  In
     * usages with well-distributed user hashCodes, tree bins are
     * rarely used.  Ideally, under random hashCodes, the frequency of
     * nodes in bins follows a Poisson distribution
     * (http://en.wikipedia.org/wiki/Poisson_distribution) with a
     * parameter of about 0.5 on average for the default resizing
     * threshold of 0.75, although with a large variance because of
     * resizing granularity. Ignoring variance, the expected
     * occurrences of list size k are (exp(-0.5) * pow(0.5, k) /
     * factorial(k)). The first values are:
     *
     * 0:    0.60653066
     * 1:    0.30326533
     * 2:    0.07581633
     * 3:    0.01263606
     * 4:    0.00157952
     * 5:    0.00015795
     * 6:    0.00001316
     * 7:    0.00000094
     * 8:    0.00000006
     * more: less than 1 in ten million

```

![](.\img\翻译.png)

### Hashtable 

Hashtable  是线程安全，适合多线程，但是效率可不太乐观。看过他的源码，他在对数据操作的时候都会上锁，所以效率比较低下。

Hashtable 是不允许键或则会值为null,HashMap的键和值都可以为null.

Hashtable插入null 直接报出异常：

```
 public synchronized V put(K key, V value) {
        // Make sure the value is not null
        if (value == null) {
            throw new NullPointerException();
        }
```

HashMap做了特殊处理：

```
  public V put(K key, V value) {
        return putVal(hash(key), key, value, false, true);
    }

```

```
  static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

```

**但是你还是没说为啥Hashtable 是不允许键或值为 null 的，HashMap 的键值则都可以为 null？**

这是因为Hashtable使用的是**安全失败机制（fail-safe）**，这种机制会使你此次读到的数据不一定是最新的数据。

如果你使用null值，就会使得其无法判断对应的key是不存在还是为空，因为你无法再调用一次contain(key）来对key是否存在进行判断，ConcurrentHashMap同理

#### 你能说说他效率低的原因么？

我看过他的源码，**他在对数据操作的时候都会上锁**，所以效率比较低下。

#### 除了这个你还能说出一些Hashtable 跟HashMap不一样点么？

Hashtable 是不允许键或值为 null 的，HashMap 的键值则都可以为 null。因为Hashtable在我们put 空值的时候会直接抛空指针异常，但是HashMap却做了特殊处理。

```
   */
    public V get(Object key) {
        Node<K,V> e;
        return (e = getNode(hash(key), key)) == null ? null : e.value;
    }
```



在这种场景下我们一般会使用Hashtable或则CurrentHashMap但是因为前者的**并发度**的原因基本上没啥使用场景了，所以存在线程不安全的场景我们都使用的是

**实现方式不同**：Hashtable 继承了 Dictionary类，而 HashMap 继承的是 AbstractMap 类。

Dictionary 是 JDK 1.0 添加的，貌似没人用过这个，我也没用过。

- **初始化容量不同**：HashMap 的初始容量为：16，Hashtable 初始容量为：11，两者的负载因子默认都是：0.75。

- **扩容机制不同**：当现有容量大于总容量 * 负载因子时，HashMap 扩容规则为当前容量翻倍，Hashtable 扩容规则为当前容量翻倍 + 1。

- **迭代器不同**：HashMap 中的 Iterator 迭代器是 fail-fast 的，而 Hashtable 的 Enumerator 不是 fail-fast 的。

- 所以，当其他线程改变了HashMap 的结构，如：增加、删除元素，将会抛出ConcurrentModificationExc

- eption 异常，而 Hashtable 则不会。

- #### fail-fast是啥？

- **快速失败（fail—fast）**是java集合中的一种机制， 在用迭代器遍历一个集合对象时，如果遍历过程中对集合对象的内容进行了修改（增加、删除、修改），则会抛出Concurrent Modification Exception。

  ### 代码题：手写HashMap 多线程实现 

  

#### CorruentHashMap。

hashtable 直接在方法上加锁但是因为前者的**并发度**的原因基本上没啥使用场景了，所以存在线程不安全的场景我们都使用的是CorruentHashMap。

```
public synchronized V get(Object key) {
        Entry<?,?> tab[] = table;
        int hash = key.hashCode();
        int index = (hash & 0x7FFFFFFF) % tab.length;
        for (Entry<?,?> e = tab[index] ; e != null ; e = e.next) {
            if ((e.hash == hash) && e.key.equals(key)) {
                return (V)e.value;
            }
        }
        return null;
    }
```



**HashMap绝对是最常问的集合之一**，基本上所有点都要**烂熟于心**的那种，篇幅和时间的关系，我就不多介绍了，核心的点我基本上都讲到了，不过像红黑树这样的就没怎么聊了，但是不代表不重要。

篇幅和精力的原因我就介绍到了一部分的主要知识点，我总结了一些关于HashMap常见的面试题，大家问下自己能不能回答上来，不能的话要去查清楚哟。

**HashMap的key存对象的时候，注意什么**?

**如果键是自定义对象，那么就需要重写，hashcode,equals**

hashcode的作用是什么？：

返回对象的哈希码值就是散列码，用来支持哈希表例如HashMap，可以提高哈希表的性能。

Object的源码：

哈希代码值可以提高性能

```
public  native   int  hashCode();
```

实现了hashCode一定要实现equals，因为底层HashMap就是通过这2个方法判断重复对象的。

# 绕口令式hashCode与equals

- [ ] `如果两对象equals()是true,那么它们的hashCode()值一定相等`
- [ ] `如果两对象的hashCode()值相等，它们的equals不一定相等（hash冲突啦）`

#### HashSet 去重是怎么做到的？

```
public class Student {
    int  age;
    String  name ;

    public Student(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        //***  Object 的equals 默认比较地址值
        if (this == o) return true;
        //  null 或者判断类型是否相同
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return age == student.age &&
                Objects.equals(name, student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(age, name.toUpperCase());//都改为大写字母再比
    }

    @Override
    public String toString() {
        return "Student{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}

```



```
public class HashSetTest {


    public static void main(String[] args) {

     Student  student1=new Student(12,"zengjx");
     Student  student2=new Student(12,"zengjx");
     Student  student3=new Student(11,"zengjx");
        Student  student4=new Student(12,"ZENGJX");//大写字母
        System.out.println(student1.equals(student2));//true
        System.out.println(student1.equals(student3));//false

        HashSet<Student> students =new HashSet<>();
        students.add(student1);
        students.add(student2);
        students.add(student3);
        Iterator<Student> iterator = students.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
      // 结论1: 如果两个对象相等equals 相等 那么hash codde一定相等
        System.out.println(student1.equals(student2));//true
        System.out.println(student1.hashCode()==student2.hashCode());//true

        // 结论2：如果两个对象的hashcode(hash 冲突) 相等 ，equals不一定相等 
        System.out.println(student1.hashCode()==student4.hashCode());//true
        System.out.println(student1.equals(student4));//flase

    }
}

```

如果缺少hashCode或equals方法输出结果（不会去重）

##### Hash性能分析

HashSet是不允许有重复元素的

场景：HashSet存入10万数据，当add第10万零1个元素的时候，

底层源码可不是一个个遍历去判断重复元素，

是通过hash值去判断的

源码

HashSet源码：其实就是 HashMap 的value 为固定值，key保存数据。

```
public class HashSet<E>
    extends AbstractSet<E>
    implements Set<E>, Cloneable, java.io.Serializable
{
    static final long serialVersionUID = -5024744406713321676L;

    private transient HashMap<E,Object> map;

    // Dummy value to associate with an Object in the backing Map
    private static final Object PRESENT = new Object();

    /**
     * Constructs a new, empty set; the backing <tt>HashMap</tt> instance has
     * default initial capacity (16) and load factor (0.75).
     */
    public HashSet() {
        map = new HashMap<>();
    }

    /**
     * Constructs a new set containing the elements in the specified
     * collection.  The <tt>HashMap</tt> is created with default load factor
     * (0.75) and an initial capacity sufficient to contain the elements in
     * the specified collection.
     *
     * @param c the collection whose elements are to be placed into this set
     * @throws NullPointerException if the specified collection is null
     */
    public HashSet(Collection<? extends E> c) {
        map = new HashMap<>(Math.max((int) (c.size()/.75f) + 1, 16));
        addAll(c);
    }

    /**
     * Constructs a new, empty set; the backing <tt>HashMap</tt> instance has
     * the specified initial capacity and the specified load factor.
     *
     * @param      initialCapacity   the initial capacity of the hash map
     * @param      loadFactor        the load factor of the hash map
     * @throws     IllegalArgumentException if the initial capacity is less
     *             than zero, or if the load factor is nonpositive
     */
    public HashSet(int initialCapacity, float loadFactor) {
        map = new HashMap<>(initialCapacity, loadFactor);
    }

    /**
     * Constructs a new, empty set; the backing <tt>HashMap</tt> instance has
     * the specified initial capacity and default load factor (0.75).
     *
     * @param      initialCapacity   the initial capacity of the hash table
     * @throws     IllegalArgumentException if the initial capacity is less
     *             than zero
     */
    public HashSet(int initialCapacity) {
        map = new HashMap<>(initialCapacity);
    }

    /**
     * Constructs a new, empty linked hash set.  (This package private
     * constructor is only used by LinkedHashSet.) The backing
     * HashMap instance is a LinkedHashMap with the specified initial
     * capacity and the specified load factor.
     *
     * @param      initialCapacity   the initial capacity of the hash map
     * @param      loadFactor        the load factor of the hash map
     * @param      dummy             ignored (distinguishes this
     *             constructor from other int, float constructor.)
     * @throws     IllegalArgumentException if the initial capacity is less
     *             than zero, or if the load factor is nonpositive
     */
    HashSet(int initialCapacity, float loadFactor, boolean dummy) {
        map = new LinkedHashMap<>(initialCapacity, loadFactor);
    }

    /**
     * Returns an iterator over the elements in this set.  The elements
     * are returned in no particular order.
     *
     * @return an Iterator over the elements in this set
     * @see ConcurrentModificationException
     */
    public Iterator<E> iterator() {
        return map.keySet().iterator();
    }

    /**
     * Returns the number of elements in this set (its cardinality).
     *
     * @return the number of elements in this set (its cardinality)
     */
    public int size() {
        return map.size();
    }

    /**
     * Returns <tt>true</tt> if this set contains no elements.
     *
     * @return <tt>true</tt> if this set contains no elements
     */
    public boolean isEmpty() {
        return map.isEmpty();
    }

    /**
     * Returns <tt>true</tt> if this set contains the specified element.
     * More formally, returns <tt>true</tt> if and only if this set
     * contains an element <tt>e</tt> such that
     * <tt>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e))</tt>.
     *
     * @param o element whose presence in this set is to be tested
     * @return <tt>true</tt> if this set contains the specified element
     */
    public boolean contains(Object o) {
        return map.containsKey(o);
    }

    /**
     * Adds the specified element to this set if it is not already present.
     * More formally, adds the specified element <tt>e</tt> to this set if
     * this set contains no element <tt>e2</tt> such that
     * <tt>(e==null&nbsp;?&nbsp;e2==null&nbsp;:&nbsp;e.equals(e2))</tt>.
     * If this set already contains the element, the call leaves the set
     * unchanged and returns <tt>false</tt>.
     *
     * @param e element to be added to this set
     * @return <tt>true</tt> if this set did not already contain the specified
     * element
     */
    public boolean add(E e) {
        return map.put(e, PRESENT)==null;
    }

    /**
     * Removes the specified element from this set if it is present.
     * More formally, removes an element <tt>e</tt> such that
     * <tt>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e))</tt>,
     * if this set contains such an element.  Returns <tt>true</tt> if
     * this set contained the element (or equivalently, if this set
     * changed as a result of the call).  (This set will not contain the
     * element once the call returns.)
     *
     * @param o object to be removed from this set, if present
     * @return <tt>true</tt> if the set contained the specified element
     */
    public boolean remove(Object o) {
        return map.remove(o)==PRESENT;
    }

    /**
     * Removes all of the elements from this set.
     * The set will be empty after this call returns.
     */
    public void clear() {
        map.clear();
    }

    /**
     * Returns a shallow copy of this <tt>HashSet</tt> instance: the elements
     * themselves are not cloned.
     *
     * @return a shallow copy of this set
     */
    @SuppressWarnings("unchecked")
    public Object clone() {
        try {
            HashSet<E> newSet = (HashSet<E>) super.clone();
            newSet.map = (HashMap<E, Object>) map.clone();
            return newSet;
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }

    /**
     * Save the state of this <tt>HashSet</tt> instance to a stream (that is,
     * serialize it).
     *
     * @serialData The capacity of the backing <tt>HashMap</tt> instance
     *             (int), and its load factor (float) are emitted, followed by
     *             the size of the set (the number of elements it contains)
     *             (int), followed by all of its elements (each an Object) in
     *             no particular order.
     */
    private void writeObject(java.io.ObjectOutputStream s)
        throws java.io.IOException {
        // Write out any hidden serialization magic
        s.defaultWriteObject();

        // Write out HashMap capacity and load factor
        s.writeInt(map.capacity());
        s.writeFloat(map.loadFactor());

        // Write out size
        s.writeInt(map.size());

        // Write out all elements in the proper order.
        for (E e : map.keySet())
            s.writeObject(e);
    }

    /**
     * Reconstitute the <tt>HashSet</tt> instance from a stream (that is,
     * deserialize it).
     */
    private void readObject(java.io.ObjectInputStream s)
        throws java.io.IOException, ClassNotFoundException {
        // Read in any hidden serialization magic
        s.defaultReadObject();

        // Read capacity and verify non-negative.
        int capacity = s.readInt();
        if (capacity < 0) {
            throw new InvalidObjectException("Illegal capacity: " +
                                             capacity);
        }

        // Read load factor and verify positive and non NaN.
        float loadFactor = s.readFloat();
        if (loadFactor <= 0 || Float.isNaN(loadFactor)) {
            throw new InvalidObjectException("Illegal load factor: " +
                                             loadFactor);
        }

        // Read size and verify non-negative.
        int size = s.readInt();
        if (size < 0) {
            throw new InvalidObjectException("Illegal size: " +
                                             size);
        }
        // Set the capacity according to the size and load factor ensuring that
        // the HashMap is at least 25% full but clamping to maximum capacity.
        capacity = (int) Math.min(size * Math.min(1 / loadFactor, 4.0f),
                HashMap.MAXIMUM_CAPACITY);

        // Constructing the backing map will lazily create an array when the first element is
        // added, so check it before construction. Call HashMap.tableSizeFor to compute the
        // actual allocation size. Check Map.Entry[].class since it's the nearest public type to
        // what is actually created.

        SharedSecrets.getJavaOISAccess()
                     .checkArray(s, Map.Entry[].class, HashMap.tableSizeFor(capacity));

        // Create backing HashMap
        map = (((HashSet<?>)this) instanceof LinkedHashSet ?
               new LinkedHashMap<E,Object>(capacity, loadFactor) :
               new HashMap<E,Object>(capacity, loadFactor));

        // Read in all elements in the proper order.
        for (int i=0; i<size; i++) {
            @SuppressWarnings("unchecked")
                E e = (E) s.readObject();
            map.put(e, PRESENT);
        }
    }

    /**
     * Creates a <em><a href="Spliterator.html#binding">late-binding</a></em>
     * and <em>fail-fast</em> {@link Spliterator} over the elements in this
     * set.
     *
     * <p>The {@code Spliterator} reports {@link Spliterator#SIZED} and
     * {@link Spliterator#DISTINCT}.  Overriding implementations should document
     * the reporting of additional characteristic values.
     *
     * @return a {@code Spliterator} over the elements in this set
     * @since 1.8
     */
    public Spliterator<E> spliterator() {
        return new HashMap.KeySpliterator<E,Object>(map, 0, -1, 0, 0);
    }
}

```

HashSet的add 方法源码：

```
 public boolean add(E e) {
        return map.put(e, PRESENT)==null;//调用了HashMap
    }
```

HashMap的put 方法：

```
  public V put(K key, V value) {
        return putVal(hash(key), key, value, false, true);
    }
```

```
  static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }
```



```
  final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
                   boolean evict) {
        Node<K,V>[] tab; Node<K,V> p; int n, i;
        if ((tab = table) == null || (n = tab.length) == 0)
            n = (tab = resize()).length;
        if ((p = tab[i = (n - 1) & hash]) == null)// p 冲突元素
            tab[i] = newNode(hash, key, value, null);
        else {
            Node<K,V> e; K k;
            if (p.hash == hash &&// p 是冲突元素，用冲突元素和入参key 比较
               //核心代码：hash 相等或者两个对象== 或者euqals 相等
                ((k = p.key) == key || (key != null && key.equals(k))))
                e = p;
            else if (p instanceof TreeNode)//
                e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
            else {
                for (int binCount = 0; ; ++binCount) {
                    if ((e = p.next) == null) {
                        p.next = newNode(hash, key, value, null);
                        if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
                            treeifyBin(tab, hash);
                        break;
                    }
                    if (e.hash == hash &&
                        ((k = e.key) == key || (key != null && key.equals(k))))
                        break;
                    p = e;
                }
            }
            if (e != null) { // existing mapping for key
                V oldValue = e.value;
                if (!onlyIfAbsent || oldValue == null)
                    e.value = value;
                afterNodeAccess(e);
                return oldValue;
            }
        }
        ++modCount;
        if (++size > threshold)
            resize();
        afterNodeInsertion(evict);
        return null;
    }
```

![image-20200301081039932](.\img\hash冲突1.png)

![](.\img\hash冲突2.png)

总结：

1.hashCode 在散列表才有用，在其他情况下没有用。

2.哈希冲突场景：hash相等但是equals不等

3.hashcode ：计算的hashcode 作为存储信息的数组下标用于查找对象的存储位置。

4.equals :HashMap使用equals 判断当前键是与表中存在的键相同。

HashMap常见面试题：

- HashMap的底层数据结构？

- HashMap的存取原理？

- Java7和Java8的区别？

- 为啥会线程不安全？

- 有什么线程安全的类代替么?

- 默认初始化大小是多少？为啥是这么多？为啥大小都是2的幂？

- HashMap的扩容方式？负载因子是多少？为什是这么多？

- HashMap的主要参数都有哪些？

- HashMap是怎么处理hash碰撞的？

- hash的计算规则？

  16是2的幂，8也是，32也是，为啥偏偏选了16？

  我觉得就是一个经验值，定义16没有很特别的原因，只要是2次幂，其实用 8 和 32 都差不多。

  用16只是因为作者认为16这个初始容量是能符合常用而已。

  Hashmap中的链表大小超过八个时会自动转化为红黑树，当删除小于六时重新变为链表，为啥呢？

  根据泊松分布，在负载因子默认为0.75的时候，单个hash槽内元素个数为8的概率小于百万分之一，所以将7作为一个分水岭，等于7的时候不转换，大于等于8的时候才进行转换，小于等于6的时候就化为链表。

### 每次必问必考ConcurentHashMap

Collections.synchronizedMap是怎么实现线程安全的你有了解过么？

在SynchronizedMap内部维护了一个普通对象Map，还有排斥锁mutex，

```
  public static <K,V> Map<K,V> synchronizedMap(Map<K,V> m) {
        return new SynchronizedMap<>(m);
    }

    /**
     * @serial include
     */
    private static class SynchronizedMap<K,V>
        implements Map<K,V>, Serializable {
        private static final long serialVersionUID = 1978198479659022715L;

        private final Map<K,V> m;     // Backing Map
        final Object      mutex;        // Object on which to synchronize

        SynchronizedMap(Map<K,V> m) {
            this.m = Objects.requireNonNull(m);
            mutex = this;
        }

        SynchronizedMap(Map<K,V> m, Object mutex) {
            this.m = m;
            this.mutex = mutex;
        }
```

我们在调用这个方法的时候就需要传入一个Map，可以看到有两个构造器，如果你传入了mutex参数，则将对象排斥锁赋值为传入的对象。

如果没有，则将对象排斥锁赋值为this，即调用synchronizedMap的对象，就是上面的Map。

如果没有，则将对象排斥锁赋值为this，即调用synchronizedMap的对象，就是上面的Map。

```
  public int size() {
            synchronized (mutex) {return m.size();}
        }
        public boolean isEmpty() {
            synchronized (mutex) {return m.isEmpty();}
        }
        public boolean containsKey(Object key) {
            synchronized (mutex) {return m.containsKey(key);}
        }
        public boolean containsValue(Object value) {
            synchronized (mutex) {return m.containsValue(value);}
        }
        public V get(Object key) {
            synchronized (mutex) {return m.get(key);}
        }

        public V put(K key, V value) {
            synchronized (mutex) {return m.put(key, value);}
        }
        public V remove(Object key) {
            synchronized (mutex) {return m.remove(key);}
        }
        public void putAll(Map<? extends K, ? extends V> map) {
            synchronized (mutex) {m.putAll(map);}
        }
        public void clear() {
            synchronized (mutex) {m.clear();}
        }
```

### ConcurrentHashMap1.7和1.8 的不同实现

在多线程环境下，使用`HashMap`进行`put`操作时存在丢失数据的情况，为了避免这种bug的隐患，强烈建议使用`ConcurrentHashMap`代替`HashMap`，为了对`ConcurrentHashMap`有更深入的了解，本文将对`ConcurrentHashMap`1.7和1.8的不同实现进行分析。https://blog.csdn.net/shadow_zed/article/details/82079579

1.7的实现是：sgment+HashEntry的方式

初始化的时候计算Segment数组的大小ssize和每个人Segment中的HashEntry数组的大小cap,并初始化Segement数组的第一个元素；其中ssize大小为2的幂次方，默认是16,cap大小也是2的幂次方，最小值是2，最终结果根据初始容量initialCapacity 进行计算。

```

if (c * ssize < initialCapacity)
    ++c;
int cap = MIN_SEGMENT_TABLE_CAPACITY;
while (cap < c)
    cap <<= 1;
```

其中Segment 在实现上继承了ReentankLock，这样就自带了锁的功能。

#### **put的实现：**

当执行put方法的时候插入数据，根据key的hash值，在Segment数组找到相应位置如果相应位置的Segment 还没有初始化，则通过CAS进行赋值，接着执行Segment对象的put方法通过加锁机制插入数据。

**场景：线程A和线程B同时执行相同`Segment`对象的`put`方法**

1.线程A执行**tryLock（）**方法成功获得锁，则把**HashEntry** 对象插入到相应位置；

2.线程B获得锁失败，则执行**scanAndLockForPut()**，会通过**重复执行`tryLock()`方法尝试获取锁,**在多处理器环境下，重复次数为64，单处理器重复次数为1，当执行**tryLock()**方法的次数超过上限时，则执行lock()方法的次数超过上限时，则执行lock（）方法挂起线程B;

3.当线程A 执行完插入操作时，会通过**unlock()方法释放锁**，接着唤起线程B继续执行。

#### size的实现：

CocurrentHashMap可以并发的插入数据，准确计算元素存在一定难度，一般思路是统计每个Sgement对象的元素个数，然后在进行累加，但是这种方式计算的不一定准确。**因为计算后面几个Segment元素个数的时候，已经计算的Segment同时可能有数据的额插入或者删除，**在1.7的实现：

```
try{
  for(;;){
  //先采用不加锁的方式计算，最多计算三次
  if (retries++ == RETRIES_BEFORE_LOCK) {
            for (int j = 0; j < segments.length; ++j)
                ensureSegment(j).lock(); // force creation
        }
  }
  sum=0L;//统计长度
  size=0;
  overflow =false;
  for(int j =0;j<segments.length;j++){
  Segment<K,V> seg=segmentAt(segments,j);//获得每个segments[i] 的
  if(seg!=null){
  sum+=seg.modCount;
  int c=seg.count;
  if(c<0;||(size+=c)<0){
    overflow=true;
  }
  }
  if(sum==last)
     break;
  last=sum;
  }
}finally{
  if(retries>RETRIES_BEFORE_LOCK){
    for(int j=0;j<segments.length;){
      segmentAt(segments,j).unlock();
    }
  }


}
```

**实现过程：** 

1.先采用不加锁的方式连续计算元素个数，最多计算三次。

2.如果前后两次计算结果相同则说明计算的元素个数是正确的。

3.如果前后两次计算的结果不同，则给每个Segment进行加锁，在进行一次计算元素个数。

#### 1.8实现

1.8 改进Segment的臃肿设计，使用Node+CAS+Synchronized 来保证并发安全进行实现。

只有在执行第一次`put`方法时才会调用`initTable()`初始化`Node`数组，

```
private final Node<K,V>[] initTable() {
    Node<K,V>[] tab; int sc;
    while ((tab = table) == null || tab.length == 0) {
        if ((sc = sizeCtl) < 0)
            Thread.yield(); // lost initialization race; just spin
        else if (U.compareAndSwapInt(this, SIZECTL, sc, -1)) {
            try {
                if ((tab = table) == null || tab.length == 0) {
                    int n = (sc > 0) ? sc : DEFAULT_CAPACITY;
                    @SuppressWarnings("unchecked")
                    Node<K,V>[] nt = (Node<K,V>[])new Node<?,?>[n];
                    table = tab = nt;
                    sc = n - (n >>> 2);
                }
            } finally {
                sizeCtl = sc;
            }
            break;
        }
    }
    return tab;
}
```

put 实现：

当执行put插入时候，根据key的hash 值**，Node数组中找到相应位置**。

1.如果相应位置还未初始化，则同步CAS插入相应的数据；

```
else if ((f = tabAt(tab, i = (n - 1) & hash)) == null) {
if(casTabAt(tab,i,null,new Node<K,V>) (hash,key,value,null))
{
 break;                   // no lock when adding to empty bin
}
```

**如果相应位置的Node不为空，并且该节点不处于移动状态，则对该节点加上synchronized锁**，如果该节点的hash不小于0，则遍历链表更新节点或者插入新节点。

```
if(fh>=0){
binCount=1;
for(Node<K,V> e=f;;++binCount){
  K  ek;
  if(e.hash==hash &&(ek=e.key)==key)||(ek!=null && key.equals(ek)))){
    oldVal=e.val;
    if(!pnlyIfAbsent)
    e.val=value;
    break;
  }
  Node<K,V> e=pred=e;
  if((e=e.e.next)==null){
   pred.next=new Node<K,V> (hash,key,value,null);
   break;
  }

}

}
```





```
else  if(  f instance TreeBin){

Node<K,V> p;

binCount=2;

if(p=(TreeBin<K,V> f).putTreeVal(hash,key,value)!=null){

oldVal=p.val;

if(!onlyIFAbsent){

p.val=value;

}

}

}
```

4.如果bingCount不为0，说明put操作对数据产生了影响，如果当前链表的个数达到了8个，则通过

treeifyBin 方法转化为红黑树，如果`oldVal`不为空，说明是一次更新操作，没有对元素个数产生影响，则直接返回旧值；

```
if (binCount != 0) {
    if (binCount >= TREEIFY_THRESHOLD)
        treeifyBin(tab, i);
    if (oldVal != null)
        return oldVal;
    break;
}
```

5、如果插入的是一个新节点，则执行`addCount()`方法尝试更新元素个数`baseCount`；

size 实现

1.8 中使用volatile类型的变量baseCount,当插入新数据或删除数据的时候会通过addCount()方法更新baseCount.

```

if ((as = counterCells) != null ||
    !U.compareAndSwapLong(this, BASECOUNT, b = baseCount, s = b + x)) {
    CounterCell a; long v; int m;
    boolean uncontended = true;
    if (as == null || (m = as.length - 1) < 0 ||
        (a = as[ThreadLocalRandom.getProbe() & m]) == null ||
        !(uncontended =
          U.compareAndSwapLong(a, CELLVALUE, v = a.value, v + x))) {
        fullAddCount(x, uncontended);
        return;
    }
    if (check <= 1)
        return;
    s = sumCount();
}

```

1.初始化时counterCells为空，如果存在两个线程同时执行CAS修改baseCount,则失败线程会继续执行方法体中的逻辑，使用CountCells记录元素个数的变化。

2.如果CounterCell数组**counterCells**为空，调用**fullAddCount()**方法进行初始化，并且插入对应的记录数，通过CAS设置cellsBusy字段，只是设置成功的线程才能初始化**CounterCell**数组：



```

else if (cellsBusy == 0 && counterCells == as &&
         U.compareAndSwapInt(this, CELLSBUSY, 0, 1)) {
    boolean init = false;
    try {                           // Initialize table
        if (counterCells == as) {
            CounterCell[] rs = new CounterCell[2];
            rs[h & 1] = new CounterCell(x);
            counterCells = rs;
            init = true;
        }
    } finally {
        cellsBusy = 0;
    }
    if (init)
        break;
}
```

3、如果通过`CAS`设置cellsBusy字段失败的话，则继续尝试通过`CAS`修改`baseCount`字段，如果修改`baseCount`字段成功的话，就退出循环，否则继续循环插入`CounterCell`对象；

```
else if (U.compareAndSwapLong(this, BASECOUNT, v = baseCount, v + x))
    break;
```

1.8 的size比1.7 实现简单的多，因为元素保存baseCount 中，部分元素的变化个数保存在CounterCell数组中：

```

public int size() {
    long n = sumCount();
    return ((n < 0L) ? 0 :
            (n > (long)Integer.MAX_VALUE) ? Integer.MAX_VALUE :
            (int)n);
}
final long sumCount() {
    CounterCell[] as = counterCells; CounterCell a;
    long sum = baseCount;
    if (as != null) {
        for (int i = 0; i < as.length; ++i) {
            if ((a = as[i]) != null)
                sum += a.value;
        }
    }
    return sum;
}

```

通过累加basseCount和CounterCell数组中的数量，即可得到元素的个数。

美团面试题：







1. 3.反射

3.1 反射是什么



# 3、JVM



## 1.请问GC Root可以是哪些？

书上：可达性分析算法，通过一系列称为GCRoot的节点来判断对象是否存活，搜索所走的路径叫做引用链（Reference chain） 当一个对象到GCRoot 没有任何引用链的时候（图论上不可达），曾该对象不可用。

可以作为GCRoot 的对象：

**虚拟机栈（栈帧的本地变量表）中引用的对象**

**方法区中类静态属性引用的对象**

**方法区中常量引用的对象**

**本地方法栈中JNI(本地Native方法) 引用的对象**