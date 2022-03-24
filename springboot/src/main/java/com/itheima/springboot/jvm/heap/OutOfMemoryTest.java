package com.itheima.springboot.jvm.heap;

import java.util.ArrayList;
import java.util.Random;

/**
 * -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:+UseSerialGC -Xloggc:./logs/gc.log
 *         GC产生原因                年轻代                           堆大小                     时间
 * [GC (Allocation Failure) [PSYoungGen: 17857K->2555K(17920K)] 27791K->27873K(58880K), 0.0040476 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
 *                                 年轻代                           老年代                                堆大小                     元空间
 * [Full GC (Ergonomics) [PSYoungGen: 15360K->15216K(17920K)] [ParOldGen: 40936K->40936K(40960K)] 56296K->56153K(58880K), [Metaspace: 3378K->3378K(1056768K)], 0.0186844 secs] [Times: user=0.20 sys=0.00, real=0.02 secs]
 *  Ergonomics翻译成中文，一般是“人体工程学”。
 *  在JVM中的垃圾收集器中的Ergonomics就是负责自动的调解gc暂停时间和吞吐量之间的平衡，然后你的虚拟机性能更好的一种做法
 *
 * [Full GC (Allocation Failure) [PSYoungGen: 15216K->15216K(17920K)] [ParOldGen: 40936K->40936K(40960K)] 56153K->56153K(58880K), [Metaspace: 3378K->3378K(1056768K)], 0.0127999 secs] [Times: user=0.20 sys=0.00, real=0.01 secs]
 */
public class OutOfMemoryTest {
    private static Integer count =0;
    public static void main(String[] args) {
        /**
         *  1. 堆OOM分析
         *  2. 方法区则是不动加载类 才会报OOM
         *  3. 栈则是递归调用 报OOM 或栈溢出
         */
        ArrayList<Picture> list=new ArrayList();
        while (true){
            try {
                Thread.sleep(1000);
                list.add(new Picture(new Random().nextInt(100*1024)));
                System.out.println(++count);
            }catch (Exception e){
                System.out.println("异常结束"+count+"："+e.getMessage());
            }
        }
    }
}
class Picture {
    private Byte[] bytes;
    public  Picture(int l){
        this.bytes=new Byte[l];
    }
}