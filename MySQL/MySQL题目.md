#### 1.MySQL相关存储引擎

#### 2.MySQL索引原理

#### 3.MySQL数据库锁

乐观锁 悲观锁



#### 4.MySQL分库分表全局ID

#### 5.B树和B+树区别 (2020探探 & 知乎后端社招面经https://www.nowcoder.com/discuss/351092)

#### 6.聚簇索引和非聚簇索引的区别(2020探探 & 知乎后端社招面经

**聚簇索引：**将数据存储与索引放到了一块，**索引结构的叶子节点保存了行数据**

 

**非聚簇索引：**将数据与索引分开存储，**索引结构的叶子节点指向了数据对应的位置**



## 浅谈聚簇索引和非聚簇索引的区别                                                                                                                                                                                              

​                    

https://my.oschina.net/xiaoyoung/blog/3046779#comments)                                                          

​                                [MyISAM](https://my.oschina.net/xiaoyoung?q=MyISAM)[Oracle](https://my.oschina.net/xiaoyoung?q=Oracle)[InnoDB](https://my.oschina.net/xiaoyoung?q=InnoDB)                                                                                      

**聚簇索引：**将数据存储与索引放到了一块，索引结构的叶子节点保存了行数据

**非聚簇索引：**将数据与索引分开存储，索引结构的叶子节点指向了数据对应的位置

 

**在innodb中**，在聚簇索引之上创建的索引称之为辅助索引，非聚簇索引都是辅助索引，像复合索引、前缀索引、唯一索引。**辅助索引叶子节点存储的不再是行的物理位置，而是主键值，辅助索引访问数据总是需要二次查找**。

![img](https://oscimg.oschina.net/oscnet/ce9bedd0dc9013e14e5f450e2149704bef5.jpg)

1. InnoDB使用的是聚簇索引，将主键组织到一棵B+树中，而行数据就储存在叶子节点上，若使用"where id = 14"这样的条件查找主键，则按照B+树的检索算法即可查找到对应的叶节点，之后获得行数据。
2. 若对Name列进行条件搜索，则需要两个步骤：第一步在辅助索引B+树中检索Name，到达其叶子节点获取对应的主键。第二步使用主键在主索引B+树种再执行一次B+树检索操作，最终到达叶子节点即可获取整行数据。（重点在于通过其他键需要建立辅助索引）

 

**聚簇索引具有唯一性**，由于聚簇索引是将数据跟索引结构放到一块，因此一个表仅有一个聚簇索引。

**表中行的物理顺序和索引中行的物理顺序是相同的**，**在创建任何非聚簇索引之前创建聚簇索引**，这是因为聚簇索引改变了表中行的物理顺序，数据行 按照一定的顺序排列，并且自动维护这个顺序；

**聚簇索引默认是主键**，如果表中没有定义主键，InnoDB 会选择一个**唯一且非空的索引**代替。如果没有这样的索引，InnoDB 会**隐式定义一个主键（类似oracle中的RowId）**来作为聚簇索引。如果已经设置了主键为聚簇索引又希望再单独设置聚簇索引，必须先删除主键，然后添加我们想要的聚簇索引，最后恢复设置主键即可。

 

MyISAM使用的是非聚簇索引，**非聚簇索引的两棵B+树看上去没什么不同**，节点的结构完全一致只是存储的内容不同而已，主键索引B+树的节点存储了主键，辅助键索引B+树存储了辅助键。表数据存储在独立的地方，这两颗B+树的叶子节点都使用一个地址指向真正的表数据，对于表数据来说，这两个键没有任何差别。由于**索引树是独立的，通过辅助键检索无需访问主键的索引树**。

![img](https://oscimg.oschina.net/oscnet/59066cb190ec7579c34e2cd77a1f47e8b68.jpg)

**使用聚簇索引的优势：**

**每次使用辅助索引检索都要经过两次B+树查找，**看上去聚簇索引的效率明显要低于非聚簇索引，这不是多此一举吗？聚簇索引的优势在哪？

1.由于行数据和聚簇索引的叶子节点存储在一起，同一页中会有多条行数据，访问同一数据页不同行记录时，已经把页加载到了Buffer中（缓存器），再次访问时，会在内存中完成访问，不必访问磁盘。这样主键和行数据是一起被载入内存的，找到叶子节点就可以立刻将行数据返回了，如果按照主键Id来组织数据，获得数据更快。

2.辅助索引的叶子节点，存储主键值，而不是数据的存放地址。好处是当行数据放生变化时，索引树的节点也需要分裂变化；或者是我们需要查找的数据，在上一次IO读写的缓存中没有，需要发生一次新的IO操作时，可以避免对辅助索引的维护工作，只需要维护聚簇索引树就好了。另一个好处是，因为辅助索引存放的是主键值，减少了辅助索引占用的存储空间大小。

注：我们知道一次io读写，可以获取到16K大小的资源，我们称之为读取到的数据区域为Page。而我们的B树，B+树的索引结构，叶子节点上存放好多个关键字（索引值）和对应的数据，都会在一次IO操作中被读取到缓存中，所以在访问同一个页中的不同记录时，会在内存里操作，而不用再次进行IO操作了。除非发生了页的分裂，即要查询的行数据不在上次IO操作的换村里，才会触发新的IO操作。

3.因为MyISAM的主索引并非聚簇索引，那么他的数据的物理地址必然是凌乱的，拿到这些物理地址，按照合适的算法进行I/O读取，于是开始不停的寻道不停的旋转。聚簇索引则只需一次I/O。（强烈的对比）

4.不过，如果涉及到大数据量的排序、全表扫描、count之类的操作的话，还是MyISAM占优势些，因为索引所占空间小，这些操作是需要在内存中完成的。

 

**聚簇索引需要注意的地方**

当使用主键为聚簇索引时，主键最好不要使用uuid，因为uuid的值太过离散，不适合排序且可能出线新增加记录的uuid，会插入在索引树中间的位置，导致索引树调整复杂度变大，消耗更多的时间和资源。

建议使用int类型的自增，方便排序并且默认会在索引树的末尾增加主键值，对索引树的结构影响最小。而且，主键值占用的存储空间越大，辅助索引中保存的主键值也会跟着变大，占用存储空间，也会影响到IO操作读取到的数据量。

**为什么主键通常建议使用自增id**

**聚簇索引的数据的物理存放顺序与索引顺序是一致的**，即：**只要索引是相邻的，那么对应的数据一定也是相邻地存放在磁盘上的**。如果主键不是自增id，那么可以想 象，它会干些什么，不断地调整数据的物理地址、分页，当然也有其他一些措施来减少这些操作，但却无法彻底避免。但，如果是自增的，那就简单了，它只需要一 页一页地写，索引结构相对紧凑，磁盘碎片少，效率也高。

 

转载：

https://www.jianshu.com/p/fa8192853184



#### 7.MySQL主从同步如何实现(2020探探 & 知乎后端社招面经)

### MVCC 是什么？

https://blog.csdn.net/yajie_12/article/details/80453863

![](.\img\MVCC的insert插入后.png)

一句话讲： MVCC 就是用同一份数据临时保留多版本的方式实现并发控制。

视频解释：

https://www.bilibili.com/video/av83518115?from=search&seid=2607299252365348489

**MVCC 的两个关键点**

 这里留意到 MVCC 关键的两个点： 
 在读写并发的过程中如何实现多版本； 
 在读写并发之后，如何实现旧版本的删除（毕竟很多时候只需要一份最新版的数据就够了）；

MVCC 最通俗的解释：

SELECT

InnoDB会根据以下两个条件检查每行记录: 
a.InnoDB只会查找版本早于当前事务版本的数据行(也就是,行的系统版本号小于或等于事务的系统版本号)，这样可以确保事务读取的行，要么是在事务开始前已经存在的，要么是事务自身插入或者修改过的. 
b.行的删除版本要么未定义,要么大于当前事务版本号,这可以确保事务读取到的行，在事务开始之前未被删除. 
只有a,b同时满足的记录，才能返回作为查询结果. 
————————————————
版权声明：本文为CSDN博主「杨龙飞的博客」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/whoamiyang/article/details/51901888

#### DELETE



InnoDB会为删除的每一行保存当前系统的版本号(事务的ID)作为删除标识. 

#### UPDATE



InnoDB执行UPDATE，实际上是新插入了一行记录，并保存其创建时间为当前事务的ID，同时保存当前事务ID到要UPDATE的行的删除时间. 



#### 如何解决的幻读？

（MVCC&next-key）

#### 8.MVCC有什么作用(2020探探 & 知乎后端社招面经)

锁机制可以控制并发操作,但是其系统开销较大,而MVCC可以在大多数情况下代替行级锁,使用MVCC,能降低其系统开销. 

MVCC是怎么实现的？

MCVV是通过保存数据某个时间点的快照来实现的，不同存储引擎的MVCC实现不同，典型的右乐观锁并发控制和悲观锁并发控制。

InnnoDB的MVCC怎么实现的？

InnoDB 是通过在每行记录后面保存两个隐藏的列来实现，分被保存这个行的创建时间，一个保存删除行的时间。这里的存储并不是时间值，二十四系统版本号可以理解为（事务的ID）,开始一个新的事务，系统版本号会自动创建，事务开始时刻的系统版本号会作为事务的ID。

REPEATABLE READ 隔离级别下的MVCC的操作？

参考看**轻松理解MYSQL MVCC 实现机制**

https://blog.csdn.net/whoamiyang/article/details/51901888?depth_1-utm_source=distribute.pc_relevant.none-task&utm_source=distribute.pc_relevant.none-task

#### 9.数据库组合索引，最左匹配原则，组合索引B,C,D 查询D，where条件使用B是否会回表.(2020探探 & 知乎后端社招面经)

10.mysql 的隔离级别



#### 11.MySQL 优化

#### 11.1MySQL 单表优化

面试题：单表数据较大怎么处理？2020.2.24

https://blog.csdn.net/yjn1995/article/details/98472759

#### 11.2什么是单表优化

当MySQL单表记录数过大时，增删改查性能都会急剧下降，可以在以下几个方面进行优化
除非单表数据未来会一直不断上涨，否则不要一开始就考虑拆分，拆分会带来逻辑、部署、运维的各种复杂度，
一般以整型值为主的表在千万级以下，字符串为主的表在五百万以下是没有太大问题的。
而事实上很多时候MySQL单表的性能依然有不少优化空间，甚至能正常支撑千万级以上的数据量

#### 11.3什么是字段优化

1.尽量使用**TINYINT、SAMLLINT、MEDIUM_INT、MEDIUM_INT**走位整数类型而非INT ，如果非负则加上UNSIGNED   VARCHAR的长度只分配真正需要的空间。

2.使用枚举或整数代替字符串类型

3.尽量使用TIMESTAMP而非DATETIME.

4.单表不要太多字段，建议20以内。

5.避免使用NULL字段，很难查询优化占用额外索引空间

6.用整型来存IP



#### 11.4什么是索引优化

1.**索引并不是越多越好**，要根据查询针对性创建，在where和order by 命令上设计的列，建立索引，

可以根据expalin 来查看是否用 了索引还是全表扫描。

2.**应该尽量避免在where 子句中对字段进行NULL值的判断**，否则引擎将放弃使用索引进行全表扫描。

3.**值分布很稀少**的字段不适合做索引。例如性别只有两个值。

4.**字符字段**只建立前缀索引。

5.**字符字段最好不要做主键**

6.**不要外键**，由程序保证约束。

7.**尽量不要用unique** ，由程序保证。

8.**使用多列索引注意顺序和查询条件保持一致**，同时删除不必要的单列索引。

#### 11.5什么是SQL 优化

1.通过慢查询日志找到比较慢大的SQL

2.**不做列运算** select  id  where  age +1=10,任何对列的操作都将导致全表

3.sql语句尽可能简单：一条sql只能在一个cpu运算；大语句拆小语句，减少锁时间；一条大sql可以堵死整个库
4.不用SELECT *
5.OR改写成IN：OR的效率是n级别，IN的效率是log(n)级别，in的个数建议控制在200以内
6.不用函数和触发器，在应用程序实现
7.避免%xxx式查询
8.少用JOIN
9**.使用同类型进行比较**，比如用’123’和’123’比，123和123比
10.**尽量避免在WHERE子句中使用!=或<>操作符**，否则将引擎放弃使用索引而进行全表扫描
11.**对于连续数值**，使用BETWEEN不用IN：SELECT id FROM t WHERE num BETWEEN 1 AND 5
列表数据不要拿全表，要使用**LIMIT来分页**，每页数量也不要太大

#### 11.6什么引擎优化

MyISAM 适合select 密集型的表，而InnoDB适合 update 和insert 密集型。



#### 11.7系统调优参数

基准测试：

sysbench：一个模块化，跨平台以及多线程的性能测试工具
 iibench-mysql：基于 Java 的 MySQL/Percona/MariaDB 索引进行插入性能测试工具
 tpcc-mysql：Percona开发的TPC-C测试工具

具体的调优参数内容较多，具体可参考官方文档，这里介绍一些比较重要的参数：

**back_log:**

指出在MYSQL暂时停止回答新请求之前的短时间内多少个请求可以被存在堆栈中，也就是说如果MYSQL 的连接数达到max_connections时，新来的请求将会被存在堆栈中，以等待某一个连接释放资源，该堆栈的数量即 back_log ,如果等待连接的数量唱过back_log,将不被授予连接资源。

**wait_timeout**:数据库的限制时间，闲置的连接会占用内存资源，可以从默认的8小时减到半小时。

**max_user_connection:**最大连接数，默认为0无上限，最好设一个合理上限

**thread_concurency:**并发线程数，设为CPU核数的两倍

**skip_name_redolve:**禁止对外部连接进行DNS解析，消除DNS解析时间，但需要所有远程主机用IP访问

**key_buffer_size:**索引块的缓存大小，增加会提升索引处理速度，对MyISAM表性能影响最大。

innodb_buffer_pool_size:缓存数据库和索引块，对InnodB表的影响最大。

innodb_addtional_mem_pool_size:InnoDB存储引擎用来存放数据字典信息以及一些内部数据结构的内存空间大小，当数据库对象非常多的时候，适当调整该参数的大小以确保所有数据都能存放在内存中提高访问效率，当过小的时候，MySQL会记录Warning信息到数据库的错误日志中，这时就需要该调整这个参数大小

innodb_log_buffer_size:InnoDB存储引擎的事务日志所使用的缓冲区，一般来说不建议超过32MB

query_cache_size:缓存MYSQL中的ResultSet,也就是一条SQL语句的结果集，所以仅仅只能针对select语句。

read_buffer_size:**MySql读入缓冲区大小**。对表进行顺序扫描的请求将分配一个读入缓冲区，MySql会为它分配一段内存缓冲区。如果对表的顺序扫描请求非常频繁，可以通过增加该变量值以及内存缓冲区大小提高其性能

sort_buffer_size:**MySql执行排序使用的缓冲大小**。如果想要增加ORDER BY的速度，首先看是否可以让MySQL使用索引而不是额外的排序阶段。如果不能，可以尝试增加sort_buffer_size变量的大小

read_rnd_buffer_size:MySQL的随机读缓冲区大小。当按任意顺序读取行时(例如，按照排序顺序)，将分配一个随机读缓存区。进行排序查询时，MySql会首先扫描一遍该缓冲，以避免磁盘搜索，提高查询速度，如果需要排序大量数据，可适当调高该值。但MySql会为每个客户连接发放该缓冲空间，所以应尽量适当设置该值，以避免内存开销过大。

record_buffer:每个进行一个顺序扫描的线程为其扫描的每张表分配这个大小的一个缓冲区。如果你做很多顺序扫描，可能想要增加该值

thread_cache_size:保存当前没有与连接关联但是准备为后面新的连接服务的线程，可以快速响应连接的线程请求而无需创新的。

table_cache:类似于thread_cache_size,但是用来缓存表文件，对InnoDB效果不大。主要用于MyISAM

#### 升级硬件

根据MySQL 是CPU密集型

#### 11.8 数据库优化案例

**某医院HIS系统的优化过程**

1.**系统整体调研：**和科室用户沟通慢的情况，系统最近变更情况。

2.**常规优化：**调整数据库参数配置，添加索引，解决阻塞。

3**.再次调研：**系统慢功能，慢语句。

4.**针对语句优化**：写法不足，是否缺失索引，是否能加提示、计划向导等。

**5.整体压力是否缓解：**如果仍然压力很大找到瓶颈，是否可以解决？如果不能解决才考虑添加硬件或选用分离、分离等方案。

11.9  mysql  性能优化

#### 11.10 在一个千万级的数据库查询中，如何提高查询效率？

数据库设计方面

1、对查询进行优化，应尽量避免全表扫描，首先应考虑在 where 及 order by 涉及的列上建立索引；

2、应尽量避免在 where 子句中对字段进行 null 值判断，否则将导致引擎放弃使用索引而进行全表扫描，如： select id from t where num is null 可以在num上设置默认值0，确保表中num列没有null值，然后这样查询： select id from t where num = 0；

3、并不是所有索引对查询都有效，SQL是根据表中数据来进行查询优化的，**当索引列有大量数据重复时，查询可能不会去利用索引**，如一表中有字段sex，male、female几乎各一半，那么即使在sex上建了索引也对查询效率起不了作用；

4、索引并不是越多越好，索引固然可以提高相应的 select 的效率，但同时也降低了 insert 及 update 的效率，因为 insert 或 update 时有可能会重建索引，所以怎样建索引需要慎重考虑，视具体情况而定。**一个表的索引数最好不要超过6个，若太多则应考虑一些不常使用到的列上建的索引是否有必要**；

5、应尽可能的**避免更新索引数据列**，因为索引数据列的顺序就是表记录的物理存储顺序，一旦该列值改变将导致整个表记录的顺序的调整，会耗费相当大的资源。若应用系统需要频繁更新索引数据列，那么需要考虑是否应将该索引建为索引；

6、**尽量使用数字型字段，若只含数值信息的字段尽量不要设计为字符型，这会降低查询和连接的性能，并会增加存储开销**。这是因为引擎在处理查询和连接时会逐个比较字符串中每一个字符，而对于数字型而言只需要比较一次就够了；

7、**尽可能的使用 varchar/nvarchar 代替 char/nchar** ，因为首先变长字段存储空间小，可以节省存储空间，其次对于查询来说，在一个相对较小的字段内搜索效率显然要高些；

8、**尽量使用表变量来代替临时表**。如果表变量包含大量数据，请注意索引非常有限（只有主键索引）；

9、**避免频繁创建和删除临时表**，以减少系统表资源的消耗；

10、临时表并不是不可使用，适当地使用它们可以使某些例程更有效，例如，当需要重复引用大型表或常用表中的某个数据集时。但是，对于一次性事件，最好使用导出表;

11、在新建临时表时，如果一次性插入数据量很大，那么可以使用 select into 代替 create table，避免造成大量 log ，以提高速度；如果数据量不大，为了缓和系统表的资源，应先create table，然后insert；

12、如果使用到了临时表，在存储过程的最后务必将所有的临时表显式删除，先 truncate table ，然后 drop table ，这样可以避免系统表的较长时间锁定。

SQL 语句方面

1、应尽量避免在 where 子句中使用!=或<>操作符，**否则将引擎放弃使用索引而进行全表扫描；**

2、应尽量避免在 where 子句中使用 or 来连接条件，**否则将导致引擎放弃使用索引而进行全表扫描**，如： select id from t where num=10 or num=20 可以这样查询： select id from t where num=10 union all select id from t where num=20;

3、**in 和 not in 也要慎用，否则会导致全表扫描，如： select id from t where num in(1,2,3) 对于连续的数值，能用 between 就不要用 in 了： select id from t where num between 1 and 3**；

4、模糊查询 ;下面的查询也将导致全表扫描：

```sql
select id from t where name like ‘%abc%’
```

Java方面

# 三、Java方面（重点内容）

1、尽可能的少造对象；

2、合理摆正系统设计的位置。大量数据操作，和少量数据操作一定是分开的。大量的数据操作，肯定不是ORM框架搞定的；

3、使用JDBC链接数据库操作数据；

4、控制好内存，让数据流起来，而不是全部读到内存再处理，而是边读取边处理；

5、合理利用内存，有的数据要缓存；

# 四、如何优化数据库，如何提高数据库的性能?



1、硬件调整性能

​    最有可能影响性能的是磁盘和网络吞吐量,解决办法扩大虚拟内存，并保证有足够可以扩充的空间；把数据库服务器上的不必要服务关闭掉；把数据库服务器和主域服务器分开；把SQL数据库服务器的吞吐量调为最大；在具有一个以上处理器的机器上运行SQL。

2、调整数据库

​    若对该表的查询频率比较高，则建立索引；建立索引时，想尽对该表的所有查询搜索操作， 按照where选择条件建立索引，尽量为整型键建立为有且只有一个簇集索引，数据在物理上按顺序在数据页上，缩短查找范围，为在查询经常使用的全部列建立非簇集索引，能最大地覆盖查询；但是索引不可太多，执行UPDATE DELETE INSERT语句需要用于维护这些索引的开销量急剧增加；避免在索引中有太多的索引键；避免使用大型数据类型的列为索引；保证每个索引键值有少数行。

3、使用存储过程（注意：阿里巴巴开发规范中已经明确禁止使用存储过程了，这里只是列出，不作为优化方法！）



  应用程序的实现过程中，能够采用存储过程实现的对数据库的操作尽量通过存储过程来实现，因为存储过程是存放在数据库服务器上的一次性被设计、编码、测试，并被再次使用，需要执行该任务的应用可以简单地执行存储过程，并且只返回结果集或者数值，这样不仅可以使程序模块化，同时提高响应速度，减少网络流量，并且通过输入参数接受输入，使得在应用中完成逻辑的一致性实现。

4、应用程序结构和算法

  建立查询条件索引仅仅是提高速度的前提条件，响应速度的提高还依赖于对索引的使用。因为人们在使用SQL时往往会陷入一个误区，即太关注于所得的结果是否正确，特别是对数据量不是特别大的数据库操作时，是否建立索引和使用索引的好坏对程序的响应速度并不大，因此程序员在书写程序时就忽略了不同的实现方法之间可能存在的性能差异，这种性能差异在数据量特别大时或者大型的或是复杂的数据库环境中（如联机事务处理OLTP或决策支持系统DSS）中表现得尤为明显。在工作实践中发现，不良的SQL往往来自于不恰当的索引设计、不充份的连接条件和不可优化的where子句。在对它们进行适当的优化后，其运行速度有了明显地提高！

出处：https://blog.csdn.net/xlgen157387/article/details/44156679

## 12.存储过程

《MYSQL必知必会》

什么是存储过程？

版本：MSQL5 添加了对存储过程的支持

使用情景： 使用大多数SQL语句都是针对一个或者多个表单条语句。

如果需要多条语句：需要对多个表多条语句，具体语句不固定它可以根据不同物品在库存中变化。

**存储过程：就是为以后的使用而保存的一条或者多条MySQL语句的集合。可以视为批文件，虽然它们的作用不仅仅是批处理。**

为什么要使用存储过程？

通过把处理封装在容易使用的单元中，**简化复杂的操作**（正如前
面例子所述）。
 **由于不要求反复建立一系列处理步骤，这保证了数据的完整性**。
如果所有开发人员和应用程序都使用同一（试验和测试）存储过
程，则所使用的代码都是相同的。
这一点的延伸就是防止错误。需要执行的步骤越多，出错的可能
性就越大。防止错误保证了数据的一致性。
 **简化对变动的管理。****如果表名、列名或业务逻辑（或别的内容）**
**有变化，只需要更改存储过程的代码**。使用它的人员甚至不需要
知道这些变化。  

这一点的延伸就是安全性。通过存储过程限制对基础数据的访问减
少了数据讹误（无意识的或别的原因所导致的数据讹误）的机会。
** 提高性能。因为使用存储过程比使用单独的SQL语句要快。**
 **存在一些只能用在单个请求中的MySQL元素和特性**，存储过程可
以使用它们来编写功能更强更灵活的代码（在下一章的例子中可
以看到。）
换句话说，使用存储过程有3个主要的好处，即简单、安全、高性能  

**执行存储过程**  

MySQL称存储过程的执行为调用，因此MySQL执行存储过程的语句
为CALL。 CALL接受存储过程的名字以及需要传递给它的任意参数。请看
以下例子：  

**输入CALL  productprincing(@pricelow,@pricehigh**

**@priceaverage);**

分析：其中，执行名为productpricing的存储过程，它计算并返回产
品的最低、最高和平均价格。  

存储过程可以显示结果，也可以不显示结果  

**创建存储过程**

输入：begin  end 限定存储过程体

```
create procedure  productpricing()
begin
select avg(prod_price) as for  products
end ;
```

mysql命令行客户机的分隔符 如果你使用的是mysql命令行
实用程序，应该仔细阅读此说明。
默认的MySQL语句分隔符为;（正如你已经在迄今为止所使用
的MySQL语句中所看到的那样）。 mysql命令行实用程序也使
用;作为语句分隔符。如果命令行实用程序要解释存储过程自
身内的;字符，则它们最终不会成为存储过程的成分，这会使
存储过程中的SQL出现句法错误。
解决办法是临时更改命令行实用程序的语句分隔符，如下所示：
其中， DELIMITER //告诉命令行实用程序使用//作为新的语
句结束分隔符，可以看到标志存储过程结束的END定义为END
//而不是END;。这样，存储过程体内的;仍然保持不动，并且
正确地传递给数据库引擎。最后，为恢复为原来的语句分隔符，
输入

```
DELIMITER //
CREATE  PROCEDURE  productpricing4()
BEGIN
SELECT AVG(prod_price) AS  priceaverage3
FROM  products ;
END //
```

```
DELIMITER //
CREATE  PROCEDURE  productpricing88(
OUT pavg  DECIMAL(8,2)
)
BEGIN
SELECT AVG(prod_price) 
FROM  products  INTO pavg;
END //

CALL productpricing88(@avg)//

SELECT @avg //
```

```
DELIMITER &&  
CREATE   PROCEDURE  productpricing7(  
OUT   plow  DECIMAL(8,2),
OUT  pheight DECIMAL(8,2),
OUT   pavg  DECIMAL(8,2)   ) 
BEGIN   SELECT    MIN(prod_price)   
FROM    products   INTO     plow;   
SELECT    MAX(prod_price)  FROM   products    INTO     pheight;
SELECT    AVG(prod_price)   FROM   products   INTO     pavg; 
END   &&  
CALL  productpricing7(@pricelow,@pricehegiht,@priceavrage)&&

SELECT @pricelow,@pricehegiht,@priceavrage && 

```

```

DELIMITER //  
CREATE   PROCEDURE  productpricing7(  
OUT   plow  DECIMAL(8,2),
OUT  pheight DECIMAL(8,2),
OUT   pavg  DECIMAL(8,2)   ) 
BEGIN   SELECT    MIN(prod_price)   
FROM    products   INTO     plow;   
SELECT    MAX(prod_price)  FROM   products    INTO     pheight;
SELECT    AVG(prod_price)   FROM   products   INTO     pavg; 
END   // 
CALL  productpricing7(@pricelow,@pricehegiht,@priceavrage)//

SELECT @pricelow,@pricehegiht,@priceavrage //
```

使用存储过程**

**call productpricing()**

带参数的存储过程：

**变量名： 所有MySQL 的变量都需要@开头**

```
\# 使用参数
DELIMITER  //  CREATE   PROCEDURE  productpricing6(  
OUT   p1ow  DECIMAL(8,2),
OUT  pheight DECIMAL(8,2),
OUT   pavg  DECIMAL(8,2)
) 
BEGIN   
SELECT    MIN(prod_price)   FROM    products   INTO     plow  ; 
SELECT    MAX(prod_price)  FROM   products    INTO     pheight;
SELECT    AVG(prod_price)   FROM   products   INTO     pavg;
END//  
DELIMITER &&  CREATE   PROCEDURE  productpricing7(  
OUT   plow  DECIMAL(8,2),
OUT  pheight DECIMAL(8,2),
OUT   pavg  DECIMAL(8,2)   ) 
BEGIN   SELECT    MIN(prod_price)   
FROM    products   INTO     plow;   
SELECT    MAX(prod_price)  FROM   products    INTO     pheight;
SELECT    AVG(prod_price)   FROM   products   INTO     pavg; 
END   &&  CALL  productpricing7(@pricelow,@pricehegiht,@priceavrage); 
SELECT   @pricelow; SELECT  @pricehegiht; SELECT   @priceavrage;
 调用
SELECT    @pricelow  ,@pricehegiht,@priceavrage  &&
```

![](.\img\存储过程调用.png)

**显示平均价格**:

```
select @priceavarage &&
```

为了获得三个值 ：

```
SELECT    @pricelow  ,@pricehegiht,@priceavrage  &&
```

![](.\img\存储过程调用.png)

删除存储过程**：不需要（）

```
drop  procedure productpricing  

DROP PROCEDURE     IF   EXISTS  ordertotal   ;
```

使用IN 和OUT 参数：

```



DELIMITER //  
DROP  PROCEDURE  IF EXISTS ordertotal //
create  procedure ordertotal(
in  onumber int ,
out ototal decimal(8,2)
)
begin 
select sum(item_price*quantity)
from orderitems
where order_num=onumber
into ototal;
end //

call ordertotal(20005,@ototal) ;

select  @ototal;

```

onumber 定义为in,因为订单号被传入存储过程。

ototal 定义为out返回合计结果。

```
第一参数为订单号第二个为计算结果
call  ordertotal(20005,@total)
select @total
```

存储过程中包含业务规则

你需要获得与以前一样的订单合计，但需要对合计
增加营业税，不过只针对某些顾客（或许是你所在州中那些顾客）。那么，
你需要做下面几件事情：
 获得合计（与以前一样）；
 **把营业税有条件地添加到合计**；
 **返回合计（带或不带税）。**
存储过程的完整工作如下：
输入

```
---Name :ordertotal
-- Parameters:onumber=order number
-- taxable =0 if not taxable,1 if taxable
-- ototal=order total  variable
create proidde
```

输入参数为onumber订单号

输入参数：taxable  是否需要缴税

输出参数：ototal  合计结果

```
DELIMITER &&
CREATE  PROCEDURE  ordertotal3(
-- 订单号
IN  onumber  INT ,
-- 是否添加增值税
IN   taxable BOOLEAN,
--  输出结果
OUT ototal DECIMAL(8,2)
)COMMENT 'Obtian order  total  ,oprionally adding tax'
BEGIN 
--  Declare  varible  for  total

DECLARE  total DECIMAL(8,2);
-- Declare tax  precentage  
DECLARE  taxrate  INT  DEFAULT 6;
--  Get  the  order total

SELECT  SUM(item_price*quantity)
FROM  orderitems
WHERE order_num = onumber
INTO  total ;
IF  taxable  THEN  
SELECT  total+(total/100*taxrate)  INTO total;
END   IF ;
--  and   finally  save  to  out   varible 
SELECT  total INTO ototal;
END &&
```

调用：

 CALL  ordertotal3(20005,0,@total); SELECT  @total;

 CALL  ordertotal3(20005,1,@total); SELECT  @total;

\- 列出所有存储过程 过滤

SHOW  CREATE  PROCEDURE  ordertotal;

\- 列出所有存储过程 过滤

SHOW  PROCEDURE  STATUS  LIKE 'ordertotal%';

## 13.什么是游标

参考：https://blog.csdn.net/weixin_42981419/article/details/86162179

https://www.bilibili.com/video/av83415867?from=search&seid=7702268493263996744

##### 概念：**

**游标（Cursor） 它使用户逐行访问SQL Server 返回的结果集**

游标类似于指针

**使用游标的一个主要原因是把集合操作转换为单个记录处理方式。**

用SQL语言从数据库中检索数据后，结果放在内存的一块区域中，且结果往往是一个含有多个记录的集合。

游标机制允许用户在SQL server内逐行地访问这些记录，按照用户自己的意愿来显示和处理这些记录。



##### **优点：** 

1.允许程序对长须语句select 返回的结果集中的每一行执行相同或不同的操作，而不是对

整个行集合执行同一个操作。

2.提供对基于游标文机制的表行进行删除和更新的能力。

3.游标实际上作为面向集合的数据库管理系统（RDBMS）和面向行的程序设计之间的桥梁，使这两种处理方式通过游标沟通起来。

##### **原理是什么：**

游标就是把数据按照指定要求提前出相应的数据集，然后逐条进行数据处理。



 在能够使用游标前，必须声明（定义）它。这个过程实际上没有
检索数据，它只是定义要使用的SELECT语句。
 一旦声明后，必须打开游标以供使用。这个过程用前面定义的
SELECT语句把数据实际检索出来。
 对于填有数据的游标，根据需要取出（检索）各行。
 在结束游标使用时，必须关闭游标。
在声明游标后，可根据需要频繁地打开和关闭游标。在游标打开后，
可根据需要频繁地执行取操作。  

##### 创建游标  

游标用DECLARE语句创建（参见第23章）。 DECLARE命名游标，并定义
相应的SELECT语句，根据需要带WHERE和其他子句。例如，下面的语句定
义了名为ordernumbers的游标，使用了可以检索所有订单的SELECT语句  

```
# 创建游标
DELIMITER //
CREATE  PROCEDURE processorders()
BEGIN 
DECLARE ordernumbers CURSOR
FOR  
SELECT order_num  FROM orders;
END //
```

这个存储过程并没有做很多事情， DECLARE语句用来定义和命
名游标，这里为ordernumbers。 存储过程处理完成后，游标就
消失（因为它局限于存储过程）。  

在定义游标之后，可以打开它。  

##### 打开和关闭游标  

游标用OPEN CURSOR语句来打开  

```
open  ordernumbers;
```

在处理OPEN语句时执行查询，存储检索出的数据以供浏览和滚
动。
游标处理完成后，应当使用如下语句关闭游标：  

```
close ordernumbers
```

LOSE释放游标使用的所有内部内存和资源，因此在每个游标
不再需要时都应该关闭。  

在一个游标关闭后，如果没有重新打开，则不能使用它。但是，使
用声明过的游标不需要再次声明，用OPEN语句打开它就可以了。  

**隐含关闭**：如果你不明确关闭游标， MySQL将会在到达END语
句时自动关闭它。  

```

create  procedure processorders()
begin 
-- declare the cursor
declare ordernumbers cursor
for
select order_num from orders
-- open  the  cursor
open  ordersnumber;
-- clsoe the  cursor
close ordersnumber;
end ;
```

这个存储过程声明、打开和关闭一个游标。但对检索出的数据
什么也没做  

##### **使用游标数据**  

在一个游标被打开后，可以使用FETCH语句分别访问它的每一行。
FETCH指定检索什么数据（所需的列），检索出来的数据存储在什么地方。  

它还向前移动游标中的内部行指针，使下一条FETCH语句检索下一行（不
重复读取同一行）。  

第一个例子从游标中检索单个行（第一行）：  

```

# 创建游标
delimiter //
create  procedure processorders2()
begin 
--  declare local variables 
declare  o  int ;
declare ordernumbers cursor
for  
select order_num  from orders;
-- open
OPEN ordernumbers ;
--   get order number
fetch ordernumbers into o;
--  close 
close ordernumbers;
end //
```

```
循环检索数据从第一行到最后一行
DELIMITER //
CREATE  PROCEDURE processorders3()
BEGIN 
--  declare local variables 
DECLARE  o  INT ;
DECLARE done BOOLEAN DEFAULT 0 ;
DECLARE ordernumbers CURSOR
FOR  
SELECT order_num  FROM orders;
-- decalre  continue handler
-- 定义了一个continue handler 是在条件条件出现时执行的代码
-- 这里指出当 SQLSTATE ‘02000’s时，set  done=1
-- SQLATATE '02000'是一个为找的条件，当reapeat由于没有更多条件的时候出现这个
DECLARE CONTINUE HANDLER FOR SQLSTATE '02000'  SET  done = 1;
-- open  the  cursor
OPEN ordernumbers ;
--  Loop througth all rows 
REPEAT

--   get order number
-- 通过fetch 检索当前order_number 到o 变量，这个fetch 是在repeate 中
-- 因此它反复执行到done 为真
FETCH ordernumbers INTO o;
-- end of loop 
UNTIL done END REPEAT;
--  close 
CLOSE ordernumbers;
END //

```

游标fetch 语句

fetch  游标名称

fetch  cursor_name into var_name [,var_name]... 这个语句用指定的打开游标读取下一行（如果有下一行的话）

并且前进游标指针。

特性：

1.只读的不能更新

2.不能滚动

3.不敏感的：服务器可以或者不可以复制它的结果集

游标必须在声明处理程序之前被声明，并且变量变量和条件必须在声明或者出入程序之前被声明。

练习：

```

DELIMITER //
 CREATE PROCEDURE procedure4()
 BEGIN 
 -- 声明变量
 DECLARE i1 INT ;
 DECLARE i2 INT ;
 --  声明游标
 DECLARE  pp CURSOR FOR  SELECT order_num ,cust_id FROM orders;
 -- 打开游标
 OPEN  pp;
 loop1:LOOP
 FETCH pp INTO  i1,i2 ;
 INSERT INTO yu(number1,number2)VALUES(i1,i2);
 END LOOP;
 CLOSE pp;
 END //
 -- 调用游标
 CALL  procedure4 //
 --  错误提示：
-- 错误代码： 1329
-- No data - zero rows fetched, selected, or processed
-- 但是 查找与表已经插入了数据
 SELECT  *  FROM yu//
 
 怎么解决？https://blog.csdn.net/u013399093/article/details/56484885是因为FETCH没有跳出循环导致的。重新编写存储过程如下，顺利解决问题。
 添加变量： DECLARE done INT DEFAULT 0;
 先清空 yu 表但是不删除表
DELIMITER //
DROP PROCEDURE procedure4//
 CREATE PROCEDURE procedure4()
 BEGIN 
 DECLARE done INT DEFAULT 0;
 DECLARE i1 INT ;
 DECLARE i2 INT ;
 --  声明游标
 DECLARE  pp CURSOR FOR  SELECT order_num ,cust_id FROM orders;
 -- 
 DECLARE CONTINUE HANDLER FOR NOT FOUND SET done=1;
 -- 打开游标
 OPEN  pp;
 loop1:LOOP
 FETCH pp INTO  i1,i2 ;
 IF done=1
 THEN
 -- 跳出循环
 LEAVE loop1;
 ELSE
 
 INSERT INTO yu(number1,number2)VALUES(i1,i2);
 END IF;
 END LOOP;
 CLOSE pp;
 END //
 -- 调用游标
 CALL  procedure4 //
 --  错误提示：
-- 错误代码： 1329
-- No data - zero rows fetched, selected, or processed
 SELECT  *  FROM yu//
```

#### 练习：统计每个order_num的总数

```
DELIMITER //
DROP  PROCEDURE  IF EXISTS ordertotal //
CREATE  PROCEDURE  ordertotal(
-- 订单号
IN  onumber  INT ,
-- 是否添加增值税
IN   taxable BOOLEAN,
--  输出结果
OUT ototal DECIMAL(8,2)
)COMMENT 'Obtian order  total  ,oprionally adding tax'
BEGIN 
--  Declare  varible  for  total

DECLARE  total DECIMAL(8,2);
-- Declare tax  precentage  
DECLARE  taxrate  INT  DEFAULT 6;
--  Get  the  order total

SELECT  SUM(item_price*quantity)
FROM  orderitems
WHERE order_num = onumber
INTO  total ;
IF  taxable  THEN  
SELECT  total+(total/100*taxrate)  INTO total;
END   IF ;
--  and   finally  save  to  out   varible 
SELECT  total INTO ototal;
END //
```



```
DELIMITER //
CREATE PROCEDURE  processorders5()
BEGIN
--  声明变量
DECLARE done BOOLEAN DEFAULT 0;
-- 保存订单号
DECLARE o  INT ;
-- 保存合计
DECLARE t DECIMAL(8,2);

DECLARE ordernumbers CURSOR 
FOR
SELECT order_num FROM orders;
-- 声明 declare  continue handler
DECLARE CONTINUE HANDLER FOR SQLSTATE '02000'  SET done =1;
-- 创建表保存结果
CREATE TABLE IF NOT EXISTS ordertotals
(order_num INT ,total DECIMAL(8,2));
--  打开 游标
OPEN ordernumbers ;
-- loop through all rows
REPEAT 
--  get the  all  total  order number

FETCH ordernumbers INTO o;
--  get the  total for  this  order 
CALL  ordertotal(o,1,t);
-- insert order and  total into ordertotals
INSERT INTO ordertotals(order_num,total)
VALUES(o,t);
-- end loop
UNTIL done END REPEAT;
-- 关闭游标
CLOSE  ordernumbers ;
END //
```

1.增加变量名t 表示订单的合计

2.创建一个新表如果不存在的话 ordertotals   保存存储过程的结果

3.fetch  取每一个order_num

4.用call执行另外一个存储过程ordertotal 计算每个订单的带税合计，结果保存在t

5.用insert 保存每个订单的订单号和合计

6. 查看结果：select * from ordertotal

```


DELIMITER //
DROP PROCEDURE  processorders5 //
CREATE PROCEDURE  processorders5()
BEGIN
--  声明变量
DECLARE done BOOLEAN DEFAULT 0;
-- 保存订单号
DECLARE o  INT ;
-- 保存合计
DECLARE t DECIMAL(8,2);
--  声明游标
DECLARE ordernumbers CURSOR 
FOR
SELECT order_num FROM orders;
-- 声明 declare  continue handler
DECLARE CONTINUE HANDLER FOR SQLSTATE '02000'  SET done =1;
-- 创建表保存结果
CREATE TABLE IF NOT EXISTS ordertotals
 (order_num INT ,total DECIMAL(8,2));
--  打开 游标
OPEN ordernumbers ;
-- loop through all rows
REPEAT 
--  get the  all  total  order number

FETCH ordernumbers INTO o;
--  get the  total for  this  order 
CALL  ordertotal(o,0,t);
-- insert order and  total into ordertotals
INSERT INTO ordertotals(order_num,total)
VALUES(o,t);
-- end loop
UNTIL done END REPEAT;
-- 关闭游标
CLOSE  ordernumbers ;
END //

SELECT  *  FROM ordertotals//
实测问题：查询为空

```

### 14.mysql bin-log 



### 15. mysql 集群

## 16.mysql  主从复制

说一下mysql主从复制过程

## 17.读写分离，主从数据库，怎么保证强一致性？

降低从数据库和主数据库的同步延迟

1. ## 索引

   https://tech.meituan.com/2014/06/30/mysql-index.html

## 18.select语句的执行顺序

## 19.MySQL 的存储引擎

**innoDB 是事务型数据库**，支持事务安全表（ACID）,支持行锁和外键，InnoDB 默认是MYSQL 的引擎。

MyISAM 基于ISAM 存储引擎，是web 数据仓库和其他应用环境下最常使用的存储引擎。具有比较高的插入，查询速度但是**不支持事务。**

## 20 .MySQL的事务

### MySQL事务怎么查看

查看：

```
show  variables  like  'autocommit'
修改自动提交模式：
set autocommit=OFF 或者 
set autocomit=0
```

### ACID事务的四大特性 （面试前背）

 原子性， 一致性，隔离性，持久性

**1、原子性（Atomicity）：事务开始后所有操作，要么全部做完，要么全部不做，不可能停滞在中间环节。事务执行过程中出错，会回滚到事务开始前的状态，所有的操作就像没有发生一样。也就是说事务是一个不可分割的整体，就像化学中学过的原子，是物质构成的基本单位。**

　　 **2、一致性（Consistency）：事务开始前和结束后，数据库的完整性约束没有被破坏 。比如A向B转账，不可能A扣了钱，B却没收到。**

　　 **3、隔离性（Isolation）：同一时间，只允许一个事务请求同一数据，不同的事务之间彼此没有任何干扰。比如A正在从一张银行卡中取钱，在A取钱的过程结束前，B不能向这张卡转账。**

　　 **4、持久性（Durability）：事务完成后，事务对数据库的所有更新将被保存到数据库，不能回滚。**

### 事务的并发问题

关键字： 脏读 不可重复读  幻读 

1.脏读： 事务A读取事务B更新的数据，**然后B回滚操作，那么A读取到的数据是脏数据**

2.不可重复读：事务A多次读取同一数据，**事务 B 在事务A多次读取的过程中，对数据作了更新并提交，导致事务A多次读取同一数据时，结果 不一致。**

3.**系统管理员A将数据库中所有学生的成绩从具体分数改为ABCDE等级，但是系统管理员B就在这个时候插入了一条具体分数的记录，当系统管理员A改结束后发现还有一条记录没有改过来，就好像发生了幻觉一样，这就叫幻读。**

4.**小结：不可重复读的和幻读很容易混淆，不可重复读侧重于修改，幻读侧重于新增或删除。解决不可重复读的问题只需锁住满足条件的行，解决幻读需要锁表**

### MySQL事务隔离级别

https://www.cnblogs.com/wyaokai/p/10921323.html 用例子说明各个隔离级别的情况

| 事务隔离级别                 | 脏读 | 不可重复读 | 幻读 |
| ---------------------------- | ---- | ---------- | ---- |
| 读未提交（read-uncommitted） | 是   | 是         | 是   |
| 不可重复读（read-committed） | 否   | 是         | 是   |
| 可重复读（repeatable-read）  | 否   | 否         | 是   |
| 串行化（serializable）       | 否   | 否         | 否   |

1.事务隔离级别为读提交的时候，写数据会锁住相应的行。

2.事务隔离级别为可重复读时，如果检索条件有索引（包括主键索引）***\*默认加锁方式是next-key 锁；如果\*\*\*\*检索条件\*\*\*\*没有索引，更新数据时会锁住整张表。一个间隙被事务加了锁，其他事务是不能在这个间隙插入记录的，这样可以防止幻读。\****

　　**3、事务隔离级别为串行化时，读写数据都会锁住整张表**

　　 **4\**、隔离级别越高，越能保证数据的完整性和一致性，但是对并发性能的影响也越大。\****

### MYSQL的几种索引类型的区别



## MYSQL触发器怎么写？

<mySql必知必会>

如果你想要某条语句（或某些语句）**在事件（执行delete insert update  增删改，其他语句不支持触发器）发生时自动执行**，怎么办呢？  

应用场景：

- 每当**增加**一个顾客到某个数据库表时，都检查其电话号码格式是
  否正确，州的缩写是否为大写
- 每当**订购**一个产品时，都从库存数量中减去订购的数量；
- 无论何时**删除**一行，都在某个存档表中保留一个副本  

保持每个数据库的触发器名唯一，在MySQL5中，触发器必须每个表唯一但不是在每个数据库中唯一。这表示同一
数据库中的两个表可具有相同名字的触发器。这在其他每个数
据库触发器名必须唯一的DBMS中是不允许的，而且以后的
MySQL版本很可能会使命名规则更为严格。因此，现在最好
是在数据库范围内使用唯一的触发器名。  

```
创建触发器
create trigger newproduct after insert on products 
for each row select 'product added';
CREATE TRIGGER用来创建名为newproduct的新触发器。触发器
可在一个操作发生之前或之后执行，这里给出了AFTER INSERT，
所以此触发器将在INSERT语句成功执行后执行。这个触发器还指定FOR
EACH ROW，因此代码对每个插入行执行。在这个例子中，文本Product
added将对每个插入的行显示一次。
```

仅支持表 只有表才支持触发器，视图不支持（临时表也不
支持）。  

触发器按每个表每个事件每次地定义，每个表每个事件每次只允许  

一个触发器。因此，每个表最多支持6个触发器（每条INSERT、 UPDATE
和DELETE的之前和之后）。****单一触发器不能与多个事件或多个表关联，所**
**以，如果你需要一个对INSERT和UPDATE操作执行的触发器，则应该定义**
两个触发器**  

**触发器失败:**

 如果BEFORE触发器失败，则MySQL将不执行请
求的操作。此外，如果BEFORE触发器或语句本身失败， MySQL
将不执行AFTER触发器（如果有的话）。  

**删除触发器**

```
drop  trigger  newproduct;
```

触发器不能更新或者修改，必须先删除，再重新创建。

insert 触发器

INSERT触发器在INSERT语句执行之前或之后执行。需要知道以下几
点：
 在INSERT触发器代码内，可引用一个名为NEW的虚拟表，访问被
插入的行；
 在BEFORE INSERT触发器中， NEW中的值也可以被更新（允许更改
被插入的值）；
 对于AUTO_INCREMENT列， NEW在INSERT执行之前包含0，在INSERT
执行之后包含新的自动生成值。  

create trigger neworder after  insert on orders 

for each row select new.order_num;

问题解决：

[触发器报错“Not allowed to return a result set from a trigger”的解决方案](https://www.cnblogs.com/liuliu3/p/10212248.html)

```
创建触发器语句如下：
CREATE TRIGGER newproduct AFTER INSERT ON products
FOR EACH ROW SELECT 'Product added' ;
此时报错：
Not allowed to return a result set from a trigger
解决方法：加上 into @ee，因为从MySQL5以后不支持触发器返回结果集
CREATE TRIGGER newproduct AFTER INSERT ON products
FOR EACH ROW SELECT 'Product added' INTO @ee;
```

![](.\img\触发器插入后1.png)

```
CREATE TRIGGER neworder AFTER  INSERT ON orders  FOR EACH ROW SELECT new.order_num   INTO @tt ;

INSERT INTO `mysql_crash_course`.`orders` (

  `order_date`,
  `cust_id`
) 
VALUES
  (
 
    NOW(),
    10001
  ) ;
  
  SELECT @tt ;
```

![](.\img\插入orders.png)

 此代码创建一个名为neworder的触发器，它按照AFTER INSERT
 ON orders执行。在插入一个新订单到orders表时， MySQL生
成一个新订单号并保存到order_num中。触发器从NEW. order_num取得
这个值并返回它。此触发器必须按照AFTER INSERT执行，因为在BEFORE
INSERT语句执行之前，新order_num还没有生成。对于orders的每次插
入使用这个触发器将总是返回新的订单号。  

**BEFORE或AFTER？** 

通常，将BEFORE用于数据验证和净化（目
的是保证插入表中的数据确实是需要的数据）。本提示也适用
于UPDATE触发器。  

**DELETE触发器**  

**DELETE触发器在DELETE语句执行之前或之后执行。**需要知道以下两
点：
 在DELETE触发器代码内，你可以引用一个名为OLD的虚拟表，访
问被删除的行；  

 OLD中的值全都是只读的，不能更新。  

```
#创建表archiver_order



DELIMITER //
CREATE TRIGGER  deleteorder BEFORE DELETE ON 
orders FOR EACH ROW 
BEGIN 
INSERT INTO archiver_order(order_num,order_date,cust_id)VALUES(OLD.order_num,OLD.order_date,OLD.cust_id);
END //

#删除 订单
DELETE FROM orders WHERE order_num =20009 


```

多语句触发器 正如所见，触发器deleteorder使用BEGIN和
END语句标记触发器体。这在此例子中并不是必需的，不过也
没有害处。使用BEGIN END块的好处是触发器能容纳多条SQL
语句（在BEGIN END块中一条挨着一条）。  

```

CREATE TABLE `archiver_order` (
  `order_num` INT(11)   PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `order_date` DATETIME NOT NULL,
  `cust_id` INT(11) NOT NULL
 
) ;


DELIMITER //
CREATE TRIGGER  deleteorder BEFORE DELETE ON 
orders FOR EACH ROW 
BEGIN 
INSERT INTO archiver_order(order_num,order_date,cust_id)VALUES(OLD.order_num,OLD.order_date,OLD.cust_id);
END //

#删除 订单
DELETE FROM orders WHERE order_num =11111 ;
```

![](.\img\删除触发器.png)

```
UPDATE触发器在UPDATE语句执行之前或之后执行。需要知道以下几
点：
 在UPDATE触发器代码中，你可以引用一个名为OLD的虚拟表访问
以前（UPDATE语句前）的值，引用一个名为NEW的虚拟表访问新
更新的值；
 在BEFORE UPDATE触发器中， NEW中的值可能也被更新（允许更改
将要用于UPDATE语句中的值）；
 OLD中的值全都是只读的，不能更新。
下面的例子保证州名缩写总是大写（不管UPDATE语句中给出的是大
写还是小写）：
```

```
create trigger ypdateevendor before update on venders
for each row set NEW.vend_state=Upper(NEW.vend_state);
```

```
显然， 任 何数据净化都需要在UPDATE语句之前进行，就像这
个 例 子 中一样。每次更新一个行时， NEW.vend_state中的
值（将用来更新表行的值）都用Upper(NEW.vend_state)替换。
 
```

关于触发器的进一步介绍
在结束本章之前，我们再介绍一些使用触发器时需要记住的重点。
 与其他DBMS相比， MySQL 5中支持的触发器相当初级。未来的
MySQL版本中有一些改进和增强触发器支持的计划。
 创建触发器可能需要特殊的安全访问权限，但是，触发器的执行
是自动的。如果INSERT、 UPDATE或DELETE语句能够执行，则相关
的触发器也能执行。
 应该用触发器来保证数据的一致性（大小写、格式等）。在触发器
中执行这种类型的处理的优点是它总是进行这种处理，而且是透
明地进行，与客户机应用无关。
** 触发器的一种非常有意义的使用是创建审计跟踪。使用触发器，**
**把更改（如果需要，甚至还有之前和之后的状态）记录到另一个**
**表非常容易。**
 遗憾的是， MySQL触发器中不支持CALL语句。这表示不能从触发
器内调用存储过程。所需的存储过程代码需要复制到触发器内

### 21.SQL 注入

举例：
select admin from user where username='admin' or 'a'='a' and passwd=''or 'a'='a'
防止 SQL 注入， 使用预编译语句是预防 SQL 注入的最佳方式， 如
select admin from user where username=？ And password=?
使用预编译的 SQL 语句语义不会发生改变， 在 SQL 语句中， 变量用问号？ 表示。 像上面例子中， u
'a'='a' 参数， 也只会当作 username 字符串来解释查询， 从根本上杜绝了 SQL 注入攻击的发生。
注意： 使用 mybaits 时 mapper 中#方式能够很大程度防止 sql 注入， $方式无法防止 sql 注入  

### 22.MSQL 分库分表

mysql 分库分表：  

分库分表有垂直切分和水平切分两种。
**垂直切分**： 即将表按照功能模块、 关系密切程度划分出来， 部署到不同的库上。 例如， 我们会建立定义数据库 workDB、 商品数
据库 payDB、 用户数据库 userDB、 日志数据库 logDB 等， 分别用于存储项目数据定义表、 商品定义表、 用户数据表、 日志数据表
等。
**水平切分**： 当一个表中的数据量过大时， 我们可以把该表的数据按照某种规则， 例如 userID 散列， 进行划分， 然后存储到多个
结构相同的表， 和不同的库上。 例如， 我们的 userDB 中的用户数据表中， 每一个表的数据量都很大， 就可以把 userDB 切分为结
构相同的多个 userDB： part0DB、 part1DB 等， 再将 userDB 上的用户数据表 userTable， 切分为很多 userTable： userTable0、
userTable1 等， 然后将这些表按照一定的规则存储到多个 userDB 上。
3.3 应该使用哪一种方式来实施数据库分库分表， 这要看数据库中数据量的瓶颈所在， 并综合项目的业务类型进行考虑。
如果数据库是因为表太多而造成海量数据， 并且项目的各项业务逻辑划分清晰、 低耦合， 那么规则简单明了、 容易实施的垂直切
分必是首选。  

而如果数据库中的表并不多， 但单表的数据量很大、 或数据热度很高， 这种情况之下就应该选择水平切分， 水平切分比垂直切分
要复杂一些， 它将原本逻辑上属于一体的数据进行了物理分割， 除了在分割时要对分割的粒度做好评估， 考虑数据平均和负载平
均， 后期也将对项目人员及应用程序产生额外的数据管理负担。  

在现实项目中， 往往是这两种情况兼而有之， 这就需要做出权衡， 甚至既需要垂直切分， 又需要水平切分。 我们的游戏项目便综
合使用了垂直与水平切分， 我们首先对数据库进行垂直切分， 然后， 再针对一部分表， 通常是用户数据表， 进行水平切分。
**单库多表 ：**
随着用户数量的增加， user 表的数据量会越来越大， 当数据量达到一定程度的时候对 user 表的查询会渐渐的变慢， 从而影
响整个 DB 的性能。 **如果使用 mysql, 还有一个更严重的问题是， 当需要添加一列的时候， mysql 会锁表， 期间所有的读写操作只**
**能等待。**
可以将 user 进行水平的切分， 产生两个表结构完全一样的 user_0000,user_0001 等表， user_0000 + user_0001 + …的数据刚好是
一份完整的数据。
**多库多表 ：**
随着数据量增加也许单台 DB 的存储空间不够， 随着查询量的增加单台数据库服务器已经没办法支撑。 这个时候可以再对数据库
进行水平区分。
分库分表规则举例：
通过分库分表规则查找到对应的表和库的过程。 如分库分表的规则是 user_id 除以 4 的方式， 当用户新注册了一个账号， 账
号 id 的 123,我们可以通过 id 除以 4 的方式确定此账号应该保存到 User_0003 表中。 当用户 123 登录的时候， 我们通过 123 除
以 4 后确定记录在 User_0003 中。
**mysql 读写分离**：
在实际的应用中， 绝大部分情况都是读远大于写。 Mysql 提供了读写分离的机制， 所有的写操作都必须对应到 Master， 读操作
可以在 Master 和 Slave 机器上进行， Slave 与 Master 的结构完全一样， 一个 Master 可以有多个 Slave,甚至 Slave 下还可以挂
Slave,通过此方式可以有效的提高 DB 集群的每秒查询率.
所有的写操作都是先在 Master 上操作， 然后同步更新到 Slave 上， 所以从 Master 同步到 Slave 机器有一定的延迟， 当系统很
繁忙的时候， 延迟问题会更加严重， Slave 机器数量的增加也会使这个问题更加严重。
此外， 可以看出 Master 是集群的瓶颈， 当写操作过多， 会严重影响到 Master 的稳定性， 如果 Master 挂掉， 整个集群都将不能
正常工作。
所以， 1. 当读压力很大的时候， 可以考虑添加 Slave 机器的分式解决， 但是当 Slave 机器达到一定的数量就得考虑分库了。 2.
当写压力很大的时候， 就必须得进行分库操作。  

### 22.数据库的悲观锁和乐观锁   【重要】

#### **乐观锁解释**：

每次去拿数据都任务不会被修改，所以不上锁，但是在更新的时候会判断别人有没有更新，可以使用版本号等机制。乐观锁适用于多读的应用类型，这样可以提高吞吐量，像数据库提供的类型

write_condition机制，其实都是乐观锁，在java.util.concurrent.atomic包下面的原子比那辆类就是使用了乐观锁的一种实现形式。

#### 悲观锁解释：

总是假设数据被别人修改，每次在拿数据的时候都会上锁，这样别人拿数据就会被阻塞直到拿到锁，传统的关系型数据库就是用了很多这种锁机制比如行锁、表锁、读锁、写锁都是在操作之前上锁，比如java 的sychronized 也是悲观锁



https://mp.weixin.qq.com/s?__biz=MzA4NzQ0Njc4Ng==&mid=2247484968&amp;idx=1&amp;sn=fb7a6ec361cd53a61a9855a54e506930&source=41#wechat_redirect

下面业务代码一般是这样的
开启一个事务
第一步，把第一次查询出来的version值获取可能是1也可能是10
其他DB操作完毕后
最后一步把第一步查询到的version值，赋值到where version=version值
然后update如果成功，committ提交事务
如果update数据影响行为0，事务失败必须rollback
特别注意在同一个事务内操作的

![](.\img\乐观锁.png)

```

```

#### 乐观锁的要点;

1.表设计version 字段

2.加事务，在事务内操作version=version+1;

3.选择合适场景使用乐观锁。

4.业务例子工单提交，防止其他人并发提交，使用乐观锁。

乐观锁作用？  

**乐观锁是用来防并发**（多个线程同时执行）的，

上面一个线程进来执行中另外一个线程刚好也进来执行中，提交时版本号已经发生改变，另外一个版本号更新会失败。（svn  git  版本控制工具）

- 乐观锁防并发会异常提示，悲观锁会等待除非超时才会异常
- 乐观锁是非阻塞算法，悲观锁是阻塞算法
- **悲观锁用的不好容易死锁**，乐观锁用的不好结果就不可预期
- 悲观锁的并发访问性不好
- 乐观锁加锁的时间要比悲观锁短，性能完胜悲观锁（加锁时间看）
- 乐观锁看作是关于**冲突检测**的，那么悲观锁就是**冲突避免**
- 冲突很少，或冲突**后果不会很严重**，那么通常应该选择乐观锁，因为它能得到更好并发性，而且更容易实现
- 冲突结果对于用户来说**痛苦的**，那么就需要使用悲观策略，如转账业务
- 发现**失败太迟的代价会很大**，就不要用乐观锁

#### 悲观锁机制存在的问题：

1.多线程环境下加锁，释放锁会导致比较多的**上下文切换和调度**时，引起性能问题。

2.一个线程持有锁会导致其他所有需要此锁的**线程被挂起**。

3.如果一个优先级高的线程等待一个优先级低的线程释放锁，**会导致线程优先级倒置**，引起性能风险。

23.**如何快速的向数据库中插入已知的1000万条数据**     （ 2020 3 3 ）

https://blog.csdn.net/Hero_shine/article/details/99687706