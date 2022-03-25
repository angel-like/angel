package com.itheima.springboot.demo;

public class ReturnTest {
    private static int see(){
        int s = 0;
        try{
            s +=2;
            System.out.println("执行完try");
            return s+2;// 如果 try当中有return 的话， 先执行完return中的语句，再执行finally中的语句，最后 返回 try 中的 return
            //如果是引用类型  则finally中也会影响引用结果
        }catch(Exception e){
            return s+1;
        }finally{
            System.out.println("执行完finally");
            s += 3;
            //return s+3; //try与 finally都有return 这种情况下，不执行 try当中的 return ，最后返回的是 finally 中的值 即为5。

        }
    }
    public static void main(String[] args) {
        int a = see();
        System.out.println("a : "+ a );
    }
}
