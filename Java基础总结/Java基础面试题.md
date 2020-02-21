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

# 二、JVM



## 1.请问GC Root可以是哪些？

书上：可达性分析算法，通过一系列称为GCRoot的节点来判断对象是否存活，搜索所走的路径叫做引用链（Reference chain） 当一个对象到GCRoot 没有任何引用链的时候（图论上不可达），曾该对象不可用。

可以作为GCRoot 的对象：

**虚拟机栈（栈帧的本地变量表）中引用的对象**

**方法区中类静态属性引用的对象**

**方法区中常量引用的对象**

**本地方法栈中JNI(本地Native方法) 引用的对象**