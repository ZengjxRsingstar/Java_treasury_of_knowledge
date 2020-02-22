# 一、语言基础



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

# 2、集合类

### 每次必问必考HashMap

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

Java7在多线程操作HashMap时可能引起死循环，原因是扩容转移后前后链表顺序倒置，在转移过程中修改了原来链表中节点的引用关系。

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





# 3、JVM



## 1.请问GC Root可以是哪些？

书上：可达性分析算法，通过一系列称为GCRoot的节点来判断对象是否存活，搜索所走的路径叫做引用链（Reference chain） 当一个对象到GCRoot 没有任何引用链的时候（图论上不可达），曾该对象不可用。

可以作为GCRoot 的对象：

**虚拟机栈（栈帧的本地变量表）中引用的对象**

**方法区中类静态属性引用的对象**

**方法区中常量引用的对象**

**本地方法栈中JNI(本地Native方法) 引用的对象**