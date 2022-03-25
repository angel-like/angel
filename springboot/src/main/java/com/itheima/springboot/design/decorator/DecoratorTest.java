package com.itheima.springboot.design.decorator;

import org.springframework.boot.ApplicationHome;

import java.io.*;

/**
 * 装饰者的基本测试
 *
 *
 */
public class DecoratorTest {
    public static void main(String[] args) throws IOException {
        // 基本是鸡腿堡
        Humburger chickenBurger = new ChickenBurger();
        System.out.println(chickenBurger.getName()+"  价钱："+chickenBurger.getPrice());
        // 第一层是加生菜  （要放入鸡腿堡）
        Humburger lettuce = new Lettuce(chickenBurger);
        System.out.println(lettuce.getName()+"  价钱："+lettuce.getPrice());
        // 第二层是辣椒  放入生菜的对象
        Humburger chilli = new Chilli(lettuce);
        System.out.println(chilli.getName()+"  价钱："+chilli.getPrice());

        //  也可以鸡腿堡直接加辣椒 但是传入的就是     鸡腿堡
        Humburger a = new Chilli(chickenBurger);
        System.out.println(a.getName()+"  价钱："+a.getPrice());
        /**
         * IO流这就是装饰者模式
         */
        String classPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        //ApplicationHome home = new ApplicationHome(DecoratorTest.class);
        //String fileStr = home.getSource().getAbsolutePath()+ "/download/csv";
        File file=new File(classPath+"/text.txt");
        if(!file.exists()){
            file.createNewFile();
        }
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(file)));
        bufferedReader = new BufferedReader(new FileReader(file));
        try {
            int len ;
            while((len=bufferedReader.read()) != -1){
                System.out.print((char)len);
            }
            System.out.println("");
           /*String str ;
            while((str=bufferedReader.readLine())!=null){
                System.out.println(str);
            }*/

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
