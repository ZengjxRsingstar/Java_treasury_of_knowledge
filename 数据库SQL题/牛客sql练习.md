### 1过题：查找所有员工的last_name和first_name以及对应部门编号dept_no

查找所有员工的last_name和first_name以及对应部门编号dept_no，也包括展示没有分配具体部门的员工
 CREATE TABLE `dept_emp` (
 `emp_no` int(11) NOT NULL,
 `dept_no` char(4) NOT NULL,
 `from_date` date NOT NULL,
 `to_date` date NOT NULL,
 PRIMARY KEY (`emp_no`,`dept_no`));
 CREATE TABLE `employees` (
 `emp_no` int(11) NOT NULL,
 `birth_date` date NOT NULL,
 `first_name` varchar(14) NOT NULL,
 `last_name` varchar(16) NOT NULL,
 `gender` char(1) NOT NULL,
 `hire_date` date NOT NULL,
 PRIMARY KEY (`emp_no`));

```
/*  dept_emp  主键 emp_no   deptno 
  employees 主键  emp_no    左外连接*/

select e.last_name ,e.first_name,d.dept_no  from  employees e left join dept_emp  d on 
e.emp_no =d.emp_no;
```

### 2.错题：查找所有员工入职时候的薪水情况

查找所有员工入职时候的薪水情况，给出emp_no以及salary， 并按照emp_no进行逆序
 CREATE TABLE `employees` (
 `emp_no` int(11) NOT NULL,
 `birth_date` date NOT NULL,
 `first_name` varchar(14) NOT NULL,
 `last_name` varchar(16) NOT NULL,
 `gender` char(1) NOT NULL,
 `hire_date` date NOT NULL,
 PRIMARY KEY (`emp_no`));
 CREATE TABLE `salaries` (
 `emp_no` int(11) NOT NULL,
 `salary` int(11) NOT NULL,
 `from_date` date NOT NULL,
 `to_date` date NOT NULL,
 PRIMARY KEY (`emp_no`,`from_date`));

分析：

1.测试数据saaries.emp_no 不唯一，一个员工可能有多次涨薪，employees.emp_no 唯一，salaries的数据会多于employees的数据，因此先找到employees.emp

2.salaries.from_date 和 employees.hire_date 的值应该要相等，因此有限制条件 e.hire_date  入职时间= s.from_date 入职时间

3.

```
/**
empployees表  hir_date 
salaries表  入职时间from_date    to_date 
主键 emp_no,from_date   入职时间
**/
/*左连接
select e.emp_no,s.salary  from employees e  left join salaries s  on 
e.emp_no =s.emp_no and e.hire_date=s.from_date order by e.emp_no desc ;
*/
/**内连接
select e.emp_no,s.salary  from employees e  inner join salaries s  on 
e.emp_no =s.emp_no and e.hire_date=s.from_date order by e.emp_no desc ;*/
/** where */

select e.emp_no,s.salary  from employees e  ,salaries s where
e.emp_no =s.emp_no and e.hire_date=s.from_date order by e.emp_no desc ;
```

### 3.查找薪水涨幅超过15次的员工号



查找薪水涨幅超过15次的员工号emp_no以及其对应的涨幅次数t
 CREATE TABLE `salaries` (
 `emp_no` int(11) NOT NULL,
 `salary` int(11) NOT NULL,
 `from_date` date NOT NULL,
 `to_date` date NOT NULL,
 PRIMARY KEY (`emp_no`,`from_date`));

```


select  * from (select  emp_no ,  count(*) t from     salaries   group by emp_no) 
where   t>15;
```

### 错题 ：4.找出相同的薪水，按照逆序。

找出所有员工当前(to_date='9999-01-01')具体的薪水salary情况，对于相同的薪水只显示一次,并按照逆序显示
 CREATE TABLE `salaries` (
 `emp_no` int(11) NOT NULL,
 `salary` int(11) NOT NULL,
 `from_date` date NOT NULL,
 `to_date` date NOT NULL,
 PRIMARY KEY (`emp_no`,`from_date`));

对于distinct与group by的使用: 1、当对系统的性能高并数据量大时使用group by 2、当对系统的性能不高时使用数据量少时两者皆可 3、尽量使用group by

group  by

```
select salary from  salaries  where to_date='9999-01-01' group by salary  order by 
salary desc;
```

```
distinct
select  distinct(salary) from salaries  where to_date='9999-01-01'  order by salary  desc;
```

### 5.获取所有部门当前manager的当前薪水情况

获取所有部门当前manager的当前薪水情况，给出dept_no, emp_no以及salary，当前表示to_date='9999-01-01'
CREATE TABLE `dept_manager` (
`dept_no` char(4) NOT NULL,
`emp_no` int(11) NOT NULL,
`from_date` date NOT NULL,
`to_date` date NOT NULL,
PRIMARY KEY (`emp_no`,`dept_no`));
CREATE TABLE `salaries` (
`emp_no` int(11) NOT NULL,
`salary` int(11) NOT NULL,
`from_date` date NOT NULL,
`to_date` date NOT NULL,
PRIMARY KEY (`emp_no`,`from_date`));

链接：https://www.nowcoder.com/questionTerminal/4c8b4a10ca5b44189e411107e1d8bec1?f=discussion
来源：牛客网

限制条件d.to_date   = '9999-01-01' AND s.to_date =   '9999-01-01'即可（因为同一emp_no在salaries表中对应多条涨薪记录，而当s.to_date =   '9999-01-01'时是该员工当前的薪水记录） 

```
select  d.dept_no , d.emp_no ,s.salary  from dept_manager  d,salaries  s
where d.to_date='9999-01-01' and
s.to_date='9999-01-01'and d.emp_no=s.emp_no
group by d.dept_no;
```

### 6.获取所有非manager的员工emp_no

 CREATE TABLE `dept_manager` (
 `dept_no` char(4) NOT NULL,
 `emp_no` int(11) NOT NULL,
 `from_date` date NOT NULL,
 `to_date` date NOT NULL,
 PRIMARY KEY (`emp_no`,`dept_no`));
 CREATE TABLE `employees` (
 `emp_no` int(11) NOT NULL,
 `birth_date` date NOT NULL,
 `first_name` varchar(14) NOT NULL,
 `last_name` varchar(16) NOT NULL,
 `gender` char(1) NOT NULL,
 `hire_date` date NOT NULL,
 PRIMARY KEY (`emp_no`));

使用左外连接,然后选出 dept_no 为空的。

```

select e.emp_no from employees as e left join 
dept_manager as d on 
e.emp_no = d.emp_no where d.dept_no is null
```

 先选择出所有manager 的emp_no，再排除

```
SELECT emp_no FROM employees
WHERE emp_no NOT IN (SELECT emp_no FROM dept_manager)
```



### 7.错题：获取所有员工的manager 

获取所有员工当前的manager，如果当前的manager是自己的话结果不显示，当前表示to_date='9999-01-01'。
 结果第一列给出当前员工的emp_no,第二列给出其manager对应的manager_no。
 CREATE TABLE `dept_emp` (
 `emp_no` int(11) NOT NULL,
 `dept_no` char(4) NOT NULL,
 `from_date` date NOT NULL,
 `to_date` date NOT NULL,
 PRIMARY KEY (`emp_no`,`dept_no`));
 CREATE TABLE `dept_manager` (
 `dept_no` char(4) NOT NULL,
 `emp_no` int(11) NOT NULL,
 `from_date` date NOT NULL,
 `to_date` date NOT NULL,
 PRIMARY KEY (`emp_no`,`dept_no`));

| emp_no | manager_no |
| :----- | :--------- |
| 10001  | 10002      |
| 10003  | 10004      |
| 10009  | 10010      |

```
未通过的：
/***
1.获取所有员工当前manager 
2.如果当前的manager 是自己则不显示当前表示 to_date
3.结果第一列 emp_no 第二列 manager_no
4.部门manager 表  dept_manager 
  dept_no 部门号
  emp_no 员工号
  1. 查询出员工的manager   dept_no 相同
   select  *  from dept_emp 
   select   de.emp_no ,dm.m  from  dept_emp  de  ,dept_manager   dm  where 
  de.dept_no =dm.dept_no  and  dem; 
   
  2. 排除 两张表 员工号相同的就是当前的manager 就是自己
***/
/*
  select   de.emp_no ,dm.m  from  dept_emp  de  ,dept_manager   dm  where 
  de.dept_no =dm.dept_no  and  de.emp_no !=dm.emp_no; 
*/
  select   de.emp_no  emp_no  ,dm.emp_no manager_no   from  dept_emp  de  ,dept_manager   dm  where 
  de.dept_no =dm.dept_no   and de.emp_no !=dm.emp_no  and de.to_date='9999-01-01';
  漏了：代替后输出 and  dm.to_date='9999-01-01'

```

答案分析：

```
链接：https://www.nowcoder.com/questionTerminal/e50d92b8673a440ebdf3a517b5b37d62?f=discussion
来源：牛客网

1、用 INNER JOIN 连接两张表，因为要输出自己的经理，得知自己与经理的部门要相同，故有限制条件 de.dept_no =   dm.dept_no    2、再用 WHERE 限制当前员工与当前经理的条件，即 dm.to_date 等于 '9999-01-01' 、de.to_date 等于 '9999-01-01' 、 de.emp_no 不等于 dm.emp_no    3、为了增强代码可读性，将 dept_emp 用别名 de 代替，dept_manager 用 dm 代替，最后根据题意将 de.emp_no 用别名 manager_no 
```

```
  select   de.emp_no  emp_no  ,dm.emp_no manager_no   from  dept_emp  de  ,dept_manager   dm  where 
  de.dept_no =dm.dept_no   and de.emp_no !=dm.emp_no  and de.to_date='9999-01-01'

  and  dm.to_date='9999-01-01';

```



