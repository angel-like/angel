package com.itheima.springboot.demo;

public class GuangZhouBankTest {

    public static void main(String[] args) throws Exception{

        System.out.println(-5+8*6);
        System.out.println((55+9)%9);
        System.out.println(20+-3*5/8);
        System.out.println(5+15/3*2-8%3);
        int amount=0;
        for (int i = 1; i <=4; i++) {
            for (int j = 1; j <=4; j++) {
                for (int k = 1; k <=4; k++) {
                    if(k!=i && k!=j && i!=j){
                        amount++;
                    }
                }
            }
        }
        System.out.println("总的： "+amount);
        //3.
        int a=19;
        int b=7;
        int divided=a/b;//2
        System.out.println(a-(divided*b));//5
        //4. n=25
        int ctr=0,n=25;
        String bn=Integer.toBinaryString(n);
        for(char ch:bn.toCharArray()){
            ctr += ch =='0'?1:0;
        }
        System.out.println("第四题"+ctr);
        //5.  <<表示二进制下，位数左移   1<<7 为2^7=128
        int n5=8;
        int sult=(n5*(n5+1)/2)*(1<<(n5-1));
        System.out.println(sult);//36*128=4608

        //ps -ef | grep java
        //netstat -tunpl |grep 8080
        //df -h  /etc
        //cp -r /var/mysql/. /home/gzcb
        //find /home/gzcb/ -name '*.log' |xargs rm -rf
    }
}
