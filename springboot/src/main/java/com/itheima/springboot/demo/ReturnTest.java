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
        lingxing(3);
        lingxing2();
    }

    /**
     * 实心菱形
     */
    private static void lingxing(int len){
        if(len%2==0){ //菱形要为奇数才行
            len++;
        }
        for (int i = 1; i <= len; i++) {//打印上半部分空格
            for (int j = 1; j <= len - i; j++) {  //len - i表示每行要打印的" "号个数
                System.out.print(" ");
            }
            //打印上半部分*
            for (int j = 1; j <= 2 * i - 1; j++) { //( 2 * i - 1)表示每行要打印的*号个数
                System.out.print("*");
            }
            System.out.println();
        }
        //下面就反着来  (但是注意i 初始值为 len-1)
        for (int i = len-1; i >= 1; i--) {
            for (int j = 1; j <= len - i; j++) {
                System.out.print(" ");
            }
            for (int j = 1; j <= 2 * i - 1; j++) {
                System.out.print("*");
            }
            System.out.println();
        }
        System.out.println("-----------------");
    }
    /**
     * 空心菱形
     */
    private static void lingxing2(){
        for (int i = 1; i <= 5; i++) {
            // 打印上半部分空格
            for (int j = 1; j <= 5 - i; j++) {
                System.out.print(" ");
            }
            // 打印原本实心的*部分
            for (int j = 1; j <= 2 * i - 1; j++) {
                // 仅在一行的开头和末尾打印*
                if (j == 1 || j == 2 * i - 1) {
                    System.out.print("*");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println(" ");
        }
        for (int i = 4; i >= 1; i--) {
            // 打印下半部分空格
            for (int j = 1; j <= 5 - i; j++) {
                System.out.print(" ");
            }
            // 打印原本实心的*部分
            for (int j = 1; j <= 2 * i - 1; j++) {
                // 仅在一行的开头和末尾打印*
                if (j == 1 || j == 2 * i - 1) {
                    System.out.print("*");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println(" ");
        }
    }

}
