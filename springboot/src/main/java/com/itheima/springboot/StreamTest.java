package com.itheima.springboot;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <p>
 * Created by ljx on 2020-03-24.
 */
public class StreamTest {
    public static void main(String[] args) throws IOException {
        /**java8中的stream使用*/
        List<Map<String, Object>> mapList = getMapList();
        System.out.println(mapList);
        //1.过滤函数 List.stream().filter()的使用（filter()里返回true或者false）
        List<Map<String, Object>> mapListFilter = mapList.stream().filter(stu -> stu.get("sex").equals("男")).collect(Collectors.toList());
        System.out.println(mapListFilter);
        List<Map<String, Object>> mapListFilter2 = mapList.stream().filter(e -> {
            if (Integer.parseInt(e.get("age").toString()) > 20) {
                return true;
            } else {
                return false;
            }
        }).collect(Collectors.toList());
        System.out.println(mapListFilter2);
        System.out.println("1.------------------------------------------------------------------------------------------------");
        //2.map函数List.stream().map()的使用(map()里返回对象Object)
        List<Map<String, Object>> mapListMAP = mapList.stream().map(e -> {
            e.put("score", 90);
            return e;
        }).collect(Collectors.toList());
        //2.1注意一点List.stream().map()方法，当返回的是原对象本身的时候，就不会创建新的List对象，原来的list会改变，正常用的时候也就不需要接了
        System.out.println(mapList);
        System.out.println(mapListMAP);
        //2.2而如果返回的不是原对象的话，就会创建新list对象了，就需要接一下
        List<String> stringList = mapList.stream().map(e -> {
            return e.get("name").toString();
        }).collect(Collectors.toList());
        System.out.println(stringList);
        System.out.println("2.------------------------------------------------------------------------------------------------");
        //3. List.stream().flatMap（）的使用（flatMap（）返回参数为Stream） 可以将list中的元素拆分为多个list。
        List<String> mapListFlatMap = mapListMAP.stream().flatMap(e -> {
            String[] names = e.get("name").toString().split("");
            return Arrays.stream(names);//把字符数组转为集合
        }).collect(Collectors.toList());
        System.out.println(mapListFlatMap);
        System.out.println("3.------------------------------------------------------------------------------------------------");
        //4.打印图片
        printPIC();


    }

    //打印图片
    private static void printPIC() throws IOException {
        /*FileInputStream fos = new FileInputStream("C:\\Users\\Lenovo\\Pictures\\Saved Pictures\\1.jpg");
        FileOutputStream fis=new FileOutputStream("d:\\2.jpg");
        int len;
        byte[] b=new byte[1024];
        while((len=fos.read(b))!=-1){
            fis.write(b,0,len);
        }
        fos.close();
        fis.close();*/
        FileOutputStream fis2 = new FileOutputStream("d:\\2.png");//jpg //bmp //png
        String s = "sdsadasdsaddsujsaikdhusiagjbbnmcbjxhzgchsaghjgsda";
        byte[] bytes = s.getBytes();
        fis2.write(bytes);
        fis2.close();

    }

    public static List<Map<String, Object>> getMapList() {
        Map<String, Object> map1 = new HashMap<>();
        map1.put("id", "1");
        map1.put("name", "小张");
        map1.put("age", "10");
        map1.put("sex", "男");
        Map<String, Object> map2 = new HashMap<>();
        map2.put("id", "2");
        map2.put("name", "小-李");
        map2.put("age", "15");
        map2.put("sex", "女");
        Map<String, Object> map3 = new HashMap<>();
        map3.put("id", "3");
        map3.put("name", "小王");
        map3.put("age", "30");
        map3.put("sex", "男");
        List<Map<String, Object>> list = new ArrayList<>();
        list = Arrays.asList(map1, map2, map3);
        return list;
    }

    interface Fun{
        String interfaceMethod(String s);
        default String deMethod(String a,String b){
            return a+b;
        }
    }
    public void funMethod(Fun fun){
        fun.interfaceMethod("aaa");
    }
}
