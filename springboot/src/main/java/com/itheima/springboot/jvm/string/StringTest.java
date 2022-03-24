package com.itheima.springboot.jvm.string;

import org.junit.Test;

/**
 * 1.  常量与常量的拼接结果在常量池，原理是编译期优化
 * 2.  常量池中不会存在相同内容的变量
 * 3.  拼接前后，只要其中有一个是变量，结果就在堆中(该变量被final修饰除外)。变量拼接的原理是StringBuilder
 * 4.  如果拼接的结果调用intern()方法，根据该字符串是否在常量池中存在，分为：
 */
public class StringTest {
    public static void main(String[] args) {
        String s1="a";
        String s2="a"+"b";
        String s3="ab";
         /*
        如下的s1 + “b” 的执行细节：(变量s是我临时定义的）
        ① StringBuilder s = new StringBuilder();
        ② s.append("a")
        ③ s.append("b")
        ④ s.toString()  --> 约等于 new String("ab")，但不等价

        补充：在jdk5.0之后使用的是StringBuilder,在jdk5.0之前使用的是StringBuffer
         */
        String s4=s1+"b";
        String s5=s1+"b";
        System.out.println(s3==s2);//true
        System.out.println(s3==s4);//false
        System.out.println(s3==s5);//false
        System.out.println(s4==s5);//false
        String s6=s4.intern();
        System.out.println(s6==s2);//true
        System.out.println("---------------------final修饰的为常量---------------------");//true
        /*
         1. 字符串拼接操作不一定使用的是StringBuilder!
       如果拼接符号左右两边都是字符串常量或常量引用，则仍然使用编译期优化，即非StringBuilder的方式。
        2. 针对于final修饰类、方法、基本数据类型、引用数据类型的量的结构时，能使用上final的时候建议使用上。
        */
        final String s7="a";
        String s8=s7+"b";
        System.out.println(s8==s3);//true
    }
    /**
     * 1.  体会执行效率：通过StringBuilder的append()的方式添加字符串的效率要远高于使用String的字符串拼接方式！
     * 2.  原因：
     *     1.  StringBuilder的append()的方式：
     *         *   自始至终中只创建过一个StringBuilder的对象
     *     2.  使用String的字符串拼接方式：
     *         *   创建过多个StringBuilder和String（调的toString方法）的对象，内存占用更大；
     *         *   如果进行GC，需要花费额外的时间（在拼接的过程中产生的一些中间字符串可能永远也用不到，会产生大量垃圾字符串）。
     * 3.  改进的空间：
     *     *   在实际开发中，如果基本确定要前前后后添加的字符串长度不高于某个限定值highLevel的情况下，建议使用构造器实例化：
     *     *   `StringBuilder s = new StringBuilder(highLevel); //new char[highLevel]` 默认16  -》 跟map类似，
     *     *   这样可以避免频繁扩容
     */

    /**
     * 题目：
     * new String("ab")会创建几个对象？看字节码，就知道是两个。
     *     一个对象是：new关键字在堆空间创建的
     *     另一个对象是：字符串常量池中的对象"ab"。 字节码指令：ldc  为什么这个算一个对象 ，这个我认为 不算
     * new String("a") + new String("b")呢？
     *  对象1：new StringBuilder()
     *  对象2： new String("a")
     *  对象3： 常量池中的"a"
     *  对象4： new String("b")
     *  对象5： 常量池中的"b"
     *
     *  深入剖析： StringBuilder的toString():
     *      对象6 ：new String("ab")
     *       强调一下，toString()的调用，在字符串常量池中，没有生成"ab"
     */
    @Test
    public void StringNewtest(){
        String s = new String("1");
        s.intern();//调用此方法之前，字符串常量池中已经存在了"1"
        String s2 = "1";
        System.out.println(s == s2);//jdk6：false   jdk7/8：false

        /*
         1、s3变量记录的地址为：new String("11")
         2、经过上面的分析，我们已经知道执行完pos_1的代码，在堆中有了一个new String("11")
         这样的String对象。但是在字符串常量池中没有"11"
         3、接着执行s3.intern()，在字符串常量池中生成"11"
           3-1、在JDK6的版本中，字符串常量池还在永久代，所以直接在永久代生成"11",也就有了新的地址
           3-2、而在JDK7的后续版本中，字符串常量池被移动到了堆中，此时堆里已经有new String（"11"）了
           出于节省空间的目的，直接将堆中的那个字符串的引用地址储存在字符串常量池中。没错，字符串常量池
           中存的是new String（"11"）在堆中的地址
         4、所以在JDK7后续版本中，s3和s4指向的完全是同一个地址。
        */
        String s3 = new String("1") + new String("1");/**不会在常量池中生成11，则*/
        s3.intern();/**不会在常量池中生成11，则该方法会使常量池中的存放new String（"11"）堆中的地址  */
        String s4 = "11";//s4变量记录的地址：使用的是上一行代码代码执行时，在常量池中生成的"11"的地址
        System.out.println(s3 == s4);//jdk6：false  jdk7/8：true  我这边写在test里为false，放在main里为true
    }
    /**】
        Java堆中存活的数据集合差不多25%是String对象。更进一步，这里面差不多一半String对象是重复的，重复的意思是说：`str1.equals(str2)= true`。
        堆上存在重复的String对象必然是一种内存的浪费
     * **String 去重的的实现**
     *
     * 1.  当垃圾收集器工作的时候，会访问堆上存活的对象。对每一个访问的对象都会检查是否是候选的要去重的String对象。
     * 2.  如果是，把这个对象的一个引用插入到队列中等待后续的处理。一个去重的线程在后台运行，处理这个队列。处理队列的一个元素意味着从队列删除这个元素，然后尝试去重它引用的String对象。
     * 3.  使用一个Hashtable来记录所有的被String对象使用的不重复的char数组。当去重的时候，会查这个Hashtable，来看堆上是否已经存在一个一模一样的char数组。
     * 4.  如果存在，String对象会被调整引用那个数组，释放对原来的数组的引用，最终会被垃圾收集器回收掉。
     * 5.  如果查找失败，char数组会被插入到Hashtable，这样以后的时候就可以共享这个数组了。
     *
     *  **命令行选项**
     * 1.  UseStringDeduplication(bool) ：开启String去重，默认是不开启的，需要手动开启。
     * 2.  PrintStringDeduplicationStatistics(bool) ：打印详细的去重统计信息
     * 3.  stringDeduplicationAgeThreshold(uintx) ：达到这个年龄的String对象被认为是去重的候选对象
     * */
}
