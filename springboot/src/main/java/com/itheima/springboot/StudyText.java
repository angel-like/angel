package com.itheima.springboot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StudyText {
	public static void main(String[] args) {
		String s="C:\\n盘";
		String replaceAll = s.replaceAll("\\\\n","\\\n");
		System.out.println(replaceAll);
		
		String ss="恒越一切%s,世界犹如%s,万物%s寂静";
		String format = String.format(ss, "鸿沟","深渊","归于");
		System.out.println(format);
		
		System.out.printf("视频流：a = %d, b = %d, c= %d", 1, 2, 3);
		System.out.println();
		System.out.println("======================数组=============================");
		int areaSize=4;
		int[]  valArray=new int[5];
		valArray[0]=1;
		valArray[1]=2;
		valArray[2]=3;
		valArray[3]=4;
		valArray[4]=5;
		
		int[]  valArrayback=new int[areaSize];
		for(int i=0;i<areaSize;i++){
			valArrayback[i]=valArray[i];
		}
		valArray=valArrayback;
		
		for(int j=0;j<valArray.length;j++){
			
			if(j==valArray.length-1) {
				System.out.print(valArray[j]);
			}else {
				System.out.print(valArray[j]+",");
			}
		}
		
		System.out.println();
		System.out.println("=====================List.asList()=============================");
		
		String[] s1 = {"aa","bb","cc"};
        List<String> strlist = Arrays.asList(s1);
        for(String str:strlist){
            System.out.println(str);
        }
        System.out.println("------------------------1");
        //基本数据类型结果打印为一个元素（byte,short,int,long,float,double,boolean,char）
        int[] i ={11,22,33}; 
        List intlist = Arrays.asList(i);
        for(Object o:intlist){//asList不适合基本数据类型
            System.out.println(o.toString());//[I@1b6d3586
        }
        System.out.println("------------------------2");
        Integer[] ob = {11,22,33};
        List<Integer> oblist = Arrays.asList(ob);
        for(int a:oblist){
            System.out.println(a);
        }
        System.out.println("------------------------3");
        
        List<Integer> list = new ArrayList<>();
        list.add(11);
        list.add(11);
        list.add(11);
        for(int a:list){
            System.out.println(a);
        }
		
		
	}
	
	
}
