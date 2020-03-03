# 数学计算



## [50. Pow(x, n)](https://leetcode-cn.com/problems/powx-n/)



#  第 178 场力扣周赛

### [5344. 有多少小于当前数字的数字](https://leetcode-cn.com/problems/how-many-numbers-are-smaller-than-the-current-number/)

给你一个数组 nums，对于其中每个元素 nums[i]，请你统计数组中比它小的所有数字的数目。

换而言之，对于每个 nums[i] 你必须计算出有效的 j 的数量，其中 j 满足 j != i 且 nums[j] < nums[i] 。

以数组形式返回答案。

示例 1：

输入：nums = [8,1,2,2,3]
输出：[4,0,1,1,3]
解释： 
对于 nums[0]=8 存在四个比它小的数字：（1，2，2 和 3）。 
对于 nums[1]=1 不存在比它小的数字。
对于 nums[2]=2 存在一个比它小的数字：（1）。 
对于 nums[3]=2 存在一个比它小的数字：（1）。 
对于 nums[4]=3 存在三个比它小的数字：（1，2 和 2）。


示例 2：

输入：nums = [6,5,4,8]
输出：[2,1,0,3]


示例 3：

输入：nums = [7,7,7,7]
输出：[0,0,0,0]




提示：


	2 <= nums.length <= 500
	0 <= nums[i] <= 100

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/how-many-numbers-are-smaller-than-the-current-number
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

#### 暴力解法：

定义一个返回数组ret[]用来保存最终结果，每次遍历除了当前数字的，并且小于当前数字则当前计数加1.

```
class Solution {
    public int[] smallerNumbersThanCurrent(int[] nums) {
        //1.
        if(nums==null|| nums.length==0){
            return null;
        }
        int[] ret =new int[nums.length];
        for(int i=0;i<nums.length;i++){
            int  count =0;
            for(int j=0;j<nums.length;j++){
                if(j!=i && nums[j]<nums[i]){
                    
                  count++;
                }
                ret[i]=count;
            }
        }
        return ret;
    }
}
```

### [5345. 通过投票对团队排名](https://leetcode-cn.com/problems/rank-teams-by-votes/)

现在有一个特殊的排名系统，依据参赛团队在投票人心中的次序进行排名，每个投票者都需要按从高到低的顺序对参与排名的所有团队进行排位。

排名规则如下：


	参赛团队的排名次序依照其所获「排位第一」的票的多少决定。如果存在多个团队并列的情况，将继续考虑其「排位第二」的票的数量。以此类推，直到不再存在并列的情况。
	如果在考虑完所有投票情况后仍然出现并列现象，则根据团队字母的字母顺序进行排名。


给你一个字符串数组 votes 代表全体投票者给出的排位情况，请你根据上述排名规则对所有参赛团队进行排名。

请你返回能表示按排名系统 排序后 的所有团队排名的字符串。

 

示例 1：

输入：votes = ["ABC","ACB","ABC","ACB","ACB"]
输出："ACB"
解释：A 队获得五票「排位第一」，没有其他队获得「排位第一」，所以 A 队排名第一。
B 队获得两票「排位第二」，三票「排位第三」。
C 队获得三票「排位第二」，两票「排位第三」。
由于 C 队「排位第二」的票数较多，所以 C 队排第二，B 队排第三。


示例 2：

输入：votes = ["WXYZ","XYZW"]
输出："XWYZ"
解释：X 队在并列僵局打破后成为排名第一的团队。X 队和 W 队的「排位第一」票数一样，但是 X 队有一票「排位第二」，而 W 没有获得「排位第二」。 


示例 3：

输入：votes = ["ZMNAGUEDSJYLBOPHRQICWFXTVK"]
输出："ZMNAGUEDSJYLBOPHRQICWFXTVK"
解释：只有一个投票者，所以排名完全按照他的意愿。


示例 4：

输入：votes = ["BCA","CAB","CBA","ABC","ACB","BAC"]
输出："ABC"
解释： 
A 队获得两票「排位第一」，两票「排位第二」，两票「排位第三」。
B 队获得两票「排位第一」，两票「排位第二」，两票「排位第三」。
C 队获得两票「排位第一」，两票「排位第二」，两票「排位第三」。
完全并列，所以我们需要按照字母升序排名。


示例 5：

输入：votes = ["M","M","M","M"]
输出："M"
解释：只有 M 队参赛，所以它排名第一。




提示：


	1 <= votes.length <= 1000
	1 <= votes[i].length <= 26
	votes[i].length == votes[j].length for 0 <= i, j < votes.length
	votes[i][j] 是英文 大写 字母
	votes[i] 中的所有字母都是唯一的
	votes[0] 中出现的所有字母 同样也 出现在 votes[j] 中，其中 1 <= j < votes.length

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/rank-teams-by-votes
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

题目解析： 就是统计每个字母的排名

```
class Solution {
    public String rankTeams(String[] votes) {
        int n = votes[0].length();//获得投票数组的长度
        Map<Character, Integer> map = new HashMap<>();//定义HashMap 以字母为key ,
        //出现在String 的位置作为value 值  i=0  ,value=0
        for(int i = 0 ; i < n; i++){//读取第一个数组，
            map.put(votes[0].charAt(i), i);
        }
        // votes = ["ABC","ACB","ABC","ACB","ACB"]
        
        //定义一个二维数组
        int[][] voteCnt = new int[n][n + 1];
        for(int i = 0 ; i < n ; i++){
            voteCnt[i][n] = i;
        }
        for(String vote: votes){
            for(int i = 0; i < n ; i++){
                voteCnt[map.get(vote.charAt(i))][i]++;
                //获得当前字符串的
            }
        }
        //排序
        Arrays.sort(voteCnt, new Comparator<int[]>(){
           @Override
            public int compare(int[] a, int[] b){
                for(int i = 0 ; i < n ; i++){
                    if(a[i] != b[i]){
                        return b[i] - a[i];
                    }
                }
                return votes[0].charAt(a[n]) - votes[0].charAt(b[n]);
            }
        });
        StringBuilder sb = new StringBuilder();
        for(int i = 0 ; i < n ; i++){
            sb.append(votes[0].charAt(voteCnt[i][n]));
        }
        return sb.toString();
    }
}
```

