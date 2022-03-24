package com.itheima.springboot.jvm.classload;

public class JavapTest {
    public static void main(String[] args) {
        int a=1;
        int b=2;
        int c=a+b;
        System.out.println(c);
        String s="s";
        System.out.println(s);
        JavapTest test=new JavapTest();
        test.test1(a,b);
        test.test2();

    }

    public Integer test1(int a ,int b){
        return  a*b;
    }
    public void test2(){
        double d=0.001;//double占两个slot 所以int e序号为3
        int e=1;
        {
            int f=1;
            f=f+1;
        }
        int g=2;//g会占用f的序号槽 ，重复利用
        System.out.println(5);
    }
    private static  int st=545;//有static，才会调用clinit

    interface fun{
        String inMethod();
        default String deMethod(){
            return "aaa";
        }
    }
}
