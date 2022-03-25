package com.itheima.springboot.demo;


import org.springframework.util.NumberUtils;

import java.util.*;
import java.util.concurrent.*;

public class InterviewTest {
    private final static ExecutorService pool = Executors.newFixedThreadPool(10);
    public static void main(String[] args) throws Exception {
        /*while(true){
            Scanner sc=new Scanner(System.in);
            String str = sc.next();
            try {
                int n=Integer.valueOf(str);
                if(n<=0){
                    System.out.println("请输入一个大于1的数字");
                    continue;
                }
                //广州银行
                fetch(n);
                fetch2(n);
                break;
            }catch (Exception e){
                System.out.println("请输入一个大于1的数字");
                continue;
            }
        }*/
        //int[] cost={10,15,20};
        int[] cost={1,100,1,1,1,100,1,1,100,10001};
        int mincost = mincost(cost);
        System.out.println(mincost);
        //广发
        /*int[] aa={1,4,122,52,2,11,11,2,1,1,2};
        System.out.println(noRepeat(aa));
        // 易方达面试 -多线程
        moreThread();
        // 易方达面试 -压缩
        String s = commStr("aaacccddeff");
        System.out.println(s);*/

    }

    /**
     *给定一个数组cost，其中cost[i]的值就代表着你爬第[i]阶的台阶的开销。一旦你付了这个开销，
     * 你就可以继续往上爬一阶或者两阶，直到你达到最顶层（数组的结尾元素再多一层）。
     * 同时你可以选择从第0阶开始或者第一阶开始。
     * 我们的目标是找出你爬到最顶层所付出的最小的开销
     * @param cost
     * @return
     */
    public static int mincost(int[] cost){
        int[] minCostArr=new int[cost.length+1];
        minCostArr[0]=cost[0];
        minCostArr[1]=cost[1];
        for(int i=2;i<=cost.length;i++){
            //终点阶梯时。不需要再加上当前阶梯需要复出的代驾  所以costV=0  ，总最小代码为倒一或者倒二的代码的最小值
            int costV= (i==cost.length) ?0:cost[i];
            //每个阶梯的代码为 前一/二阶梯中最小的 +自身代码。。。最后一阶没有自身代价
            minCostArr[i]=Math.min(minCostArr[i-1]+costV,minCostArr[i-2]+costV);
        }
        return minCostArr[cost.length];
    }

    /**
     * 有一个无限长的数字序列:1,2,2,3,3,3,4,4,4,4,5,5,5,5,5,……(数字k在序列中正好出现k次）。
     * 想知道这个数字序列的第n项是多少？
     * @param n
     * @return
     */
    public static int fetch2(int n) {
        int k=1;
        while (n>0){
            n-=k;
            k++;
        }
        System.out.println(k-1);
        return k-1;
    }
    /**
     * 有一个无限长的数字序列:1,2,2,3,3,3,4,4,4,4,5,5,5,5,5,……(数字k在序列中正好出现k次）。
     * 想知道这个数字序列的第n项是多少？
     * @param n
     * @return
     */
    public static int fetch(int n) {
        int sum = 0;
        int result=0;
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= n && sum < n; i++) {
            for (int j = 0; j < i; j++) {
                list.add(i);
            }
            sum = sum + i;
            if (sum == n) {
                break;
            }
        }
        for (int i = 0; i < list.size(); i++) {
            if (i == n - 1) {
                result=list.get(i);
                System.out.println(list.get(i));
            }
        }
        return result;
    }

    /**
     * 广发 获取不重复的数组
     * @param arrSrc
     * @return
     */
    public static int noRepeat(int[] arrSrc) {
        Set<Integer> set=new HashSet<>();
        for(int i=0;i<arrSrc.length;i++){
            set.add(arrSrc[i]);
        }
        int result=set.size(); //这个set用完就会被回收，应该不算复杂度吧
        //空间复杂度为O(1) ,时间复杂度为O(n)  ??=做不出来
        //String arrStr = Arrays.toString(arrSrc);
        /*for(int i=0;i<arrSrc.length;i++){
        }*/
        return  result;
    }

    /**
     * 易方达多线程 +泡面排序
     * @param
     * @return
     */
    public static void moreThread() throws InterruptedException, ExecutionException {
        Scanner scanner=new Scanner(System.in);
        //开枪指令
        int a=scanner.nextInt();
        if(a!=1){
            return;
        }
        List<Sports> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Sports sports = new Sports();
            list.add(sports);
        }
        List<Future<Float>> futures = pool.invokeAll(list,15000, TimeUnit.SECONDS);
        Float[] sportArray = new Float[10];
        for (int i = 0; i < 10; i++) {
            Future<Float> sport= futures.get(i);
            sportArray[i]=sport.get();
        }
        pool.shutdown();

        for (int i = 0; i < sportArray.length-1; i++) {
            boolean flag=true;
            for (int j = 0; j < sportArray.length-i-1; j++) {
                float temp;
                if (sportArray[j] > sportArray[j + 1]) {
                    temp=sportArray[j + 1];
                    sportArray[j+1]=sportArray[j];
                    sportArray[j]=temp;
                    flag=false;
                }
            }
            if(flag){
                break;
            }
        }
        System.out.println(Arrays.toString(sportArray));
    }
    /**
     * 易方达 压缩方法
     * @param strSrc
     * @return
     */
    public static String commStr(String strSrc) {
        StringBuilder result = new StringBuilder();
        String[] srtingSrt = strSrc.split("");
        int length = 1;//相邻相同字符串的长度
        for (int i = 0; i < srtingSrt.length; i++) {
            //最后一次时
            if (i == srtingSrt.length - 1) {
                //倒一与倒二相等
                if (srtingSrt[i].equals(srtingSrt[i - 1])) {
                    result.append(srtingSrt[i]);
                    result.append(length);
                } else {
                    result.append(srtingSrt[i]);
                }
                break;
            }
            if (srtingSrt[i].equals(srtingSrt[i + 1])) {
                length +=  1;
            } else {
                result.append(srtingSrt[i]);
                if (length != 1) {
                    result.append(length);
                }
                //遇到不相同的重置为1
                length = 1;
            }
        }
        return result.toString();

    }
}
