package com.li.zixue;

public class StudyText {
	public static void main(String[] args) {
		String s="C:\\n盘";
		String replaceAll = s.replaceAll("\\\\n","\\\n");
		System.out.println(replaceAll);
		
		String ss="恒越一切%s,世界犹如%s,万物%s寂静";
		String format = String.format(ss, "鸿沟","深渊","归于");
		System.out.println(format);
		
		
		
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
	}
	
	
}
