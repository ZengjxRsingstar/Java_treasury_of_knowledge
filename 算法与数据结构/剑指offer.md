滑动窗口   单调队列

```
* 队列   O(N)
       *     1.滑动窗口[2]插入第一个数2 存入队列
       *     2.滑动窗口[2,3]第二个数字3 由于比第一个数字大因此2不可能是
       *     滑动窗口最大值先把2 移除队列存入3.
       *     3.滑动窗口[2,3,4]插入第三个数字4 ：此时4大于3 ，所以3不可能是滑动窗口最大值移除  此时滑动窗口最大值4
       *    队列中的3插入4，此时队列： 2(4);
       *     4.滑动窗口[3,4,2] 插入第四个数字2 ： 此时2 有可能在后面是最大值     此时滑动窗口最大值4
       *        所以2 插入队列此时队列 2(4)---3(2) 
       *     5.滑动窗口[4,2,6]第五个数6 ：此时6 大于队列中的两个数2  ,4 ;  此时滑动窗口最大值6
       *     所以删除队列中的2 , 4 插入6的下标, 此时队列   4(6)
       *     6.滑动窗口[2,6,2] 第6个数字2，有可能后面最大值则插入到队列  此时队列 : 4(6),5(2)此时滑动窗口最大值6
       *     7.滑动窗口[6,2,5] 第7个数字5，有可能最大值插入队列中，2不可能是最大值了删除2，此时队列 4(6)---6(5) 此时滑动窗口最大值6
       *     8.滑动串口[2,5,1]  第8个数字1:此时6已经滑出范围了删除(判断条件 ：队列中的元素 下标是5)此时index=7-4>=3 
       *        此时队列是 6(5) ----7(1)    最大值 5
       *       此时已经到达边界结束       *    
       * 
       *    步骤  --- 插入数---滑动窗口--队列下标--    最大值
       *    1           2        2          0(2)       N
       *    2           3        2,3        1(3)       N
       *    3           4        2,3,4       2(4)  4
       *    4           2         3,4, 2     2(4),3(2)  4
       *    5           6        4,6  去掉3           
       * 
       * 解法 
       *   1.定义一个队列保存有可可能是最大值的数的下标
       *   2.当前元素下标index <窗口大小-2 存入第一个
       *   3. 第二个元素开始判断是否大于前一个。如果大于第一个则删除上一个元素插入当前元素
       *   4 .index< 窗口大小则插入第3个如果大于前一个则继续删除前一个。
       *   5.  下标到达窗口值？index=N-1 ,如果此时判断队列中下标小于index的下标对应的元素是否小于滑出窗口滑出则删除判断
       *   是否小于index 的下标是否对应值小于index对应的值小于则删除。
       *  6. index<数组大小 则index++指向下一个。
       *    
       * 
       * 
       * 
       * /
```

   确定最多有几个滑动窗口最大值：

   1.返回数组长度length-k+1个滑动窗口最大值；

   2.判断队列是否为空，如果为空将当前下标存入队列，当做最大值。

   3.start 用检测当前队列中最大值是否已经离开滑动窗口，当 i>=k-1 即一开始start=i-k+1,如果start大于队列中最大值的    下标，则弹出最大值

  4.判断队列是否为空，并且从队列尾与当前值比较，来更新队列中的最大值；

 5.当start。=0时，说明滑动窗口已经完全进入数组，可以开始记录最大值了

```
int[]  res = new int[nums.lengt-k+1];
if(k==0){
 return res;
}
int start=0;
int index=0;
ArrayDeque<Integer> max =new ArrayDeque<Integer>();
for(int   i=0;i<nums.length;i++   ){
  start =i-k+1;
  if(max.isEmpty()){//插入当做最大值
    max.add(i);
  }else if(start<max.peekFirst){//超出范围的最大值移除
    max.pollFirst();
  }
   while(!max.isEmpty() && nums.peekLast()<=nums[i])//小于当前元素的移除
   {
     max.pollFirst();
   }
   if(start>=0){//开始记录最大值
      res[index++]=max.peekFirst();
   }
   return res;

}
```

 C++:

![](.\img\滑动窗口.png)

```
vector<int>   maxInWindows<const vector<int> & num,unsingned int size)
{
   vector<int> maxInWindows;
   if(num.size()>=size &&size>=1){
     deque<int>  index;
     // 在存入一个数字的时候需要判断队列里面的数字，是否小于待存入的数字，如果小于那么这些数字都要删除。
     for(unsigned int i=0;i<size;i++){//当前下标还没达到 窗口值，如果窗口不为空
     
        while(!index.empty() && num[i]>num[index.back()])//与单调队列队尾比较，小于当前数字就删除队尾
         index.pop.back();      
     
     }
   // 次数窗口开始滑动了  index =size=3
   for(int i=size;i<num.size();i++){
   //开始保存滑动窗口最大值了---取出队头的最大值下标
    maxInwindows.push_back(num[index.front()]);
    //判断当前的值是否大于队列的值，大于的话删除。
    while(!index.empty()&&num[i]>=num[index.back()])
    {
      index.pop_back();
    }
    //判断最大值是否是否滑出窗口了
    if(!index.empty() && index.fron()<=(int)(size-1))//似乎有问题 这个条件？
    {
     index.pop_front();//移除队头最大值
    }
    // 当前值可能是最大值插入队列
      index.push_back(i);
   }
   //最后一个取出
   maxInWindws.push_back[num[index.pop_front()];
   
   
   
   
   }



}
```

 acwing 通过代码：

```
class Solution {
    public int[] maxInWindows(int[] nums, int k) {
        int[] arr=null;
        if(nums==null){
            return arr;
        }
        if(nums.length==0){
            return arr;
        }
        if(k>nums.length){
            return arr;
        }
        
      //定义一个队列保存可能最大值的下标
     
      int[] max=  new int[nums.length-k+1];//最多有nums.length-k+1 个
        LinkedList<Integer>  list = new  LinkedList<Integer>();
      int count =0;
     // 还没到窗口
       
     for(int i=0;i<k;i++){
         // 如果队列不为空并且队尾的元素小于当前则删除
        while( list.size()>0 &&nums[i] >nums[list.peekLast()] ){
             list.removeLast();    
        }
        list.addLast(i);   
     }
     // 已经遍历到窗口大小开始滑动窗口
      for(int i=k; i<nums.length;i++){
          
        //记录最大值
       
        max[count++] =nums[list.getFirst()];//
         // 如果队列不为空并且队尾的元素小于当前则删除
        while( list.size()>0 &&nums[i] >nums[list.peekLast()] ){
             list.removeLast();    
        }
        //对队头最大值超出窗口 移除
        
        if((list.size()>0 )&&((i-list.getFirst())>=k)){
            
         list.removeFirst();        
            
        }
        //当前元素添加到队尾
        list.addLast(i);
     //  System.out.println(list);
      }
      //System.out.println(list);
      if(list.size()>0)
      max[count++]=nums[list.getFirst()];//最后一个最大值
       
       
       return max;
        
    }
}
```

