package com.itheima.springboot.io;



import com.itheima.springboot.callback.CallBackTest;
import com.itheima.springboot.callback.Student;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class ServerSocketChannelTest {
    public static void main(String[] args) throws Exception {
        //Class的newInstance跟 new一样能获取对象
        Student s = Student.class.newInstance();
        //s.callHelp(5,5);
        ByteBuffer byteBuffer = ByteBuffer.wrap("输出：".getBytes());
        //创建ServerSocketChannel监听通道  去监听
        ServerSocketChannel ssc = ServerSocketChannel.open();
        //绑定8888端口
        ssc.bind(new InetSocketAddress(8888));
        //非阻塞模式
        ssc.configureBlocking(false);
        //ssc.close();
        /*while (true) {
            //监听获取到的通道信息  调用127.0.0.1:8888 这边就有sc数据了
            SocketChannel sc = ssc.accept();
            if (sc == null) {
                System.out.println("当前监听的通道无数据进来");
                Thread.sleep(2000);
            } else {
                System.out.println("incoming地址：" + sc.socket().getRemoteSocketAddress());
                sc.write(byteBuffer);
                byteBuffer.flip();
                sc.close();
            }
        }*/
        //对比上面，加了个 Selector多路复用器，来判断
        //SelectorTest(ssc);
        //管道测试
        pipeTest();
    }

    /**
     * Selector的代码实例示例
     * ssc 为非阻塞模式
     * @param ssc
     * @throws Exception
     * 可参考： https://www.136.la/tech/show-312677.html
     */
    public static void SelectorTest(ServerSocketChannel ssc) throws Exception {
        //1. 创建Selector的代码
        Selector selector = Selector.open();
        //2.  通道注册上来
        ssc.register(selector, SelectionKey.OP_ACCEPT);
        while(true) {
            // 3. 监听通道
            int readyChannels = selector.select();
            if(readyChannels == 0){
                continue;
            }
            //4. 有就绪通道进来  就获取并遍历
            Set<SelectionKey> SelectionKeys = selector.selectedKeys();
            //遍历该 SelectionKeys
            Iterator<SelectionKey> keyIterator = SelectionKeys.iterator();
            while(keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                //5. 判断是哪种状态的通道进来，获取对应通道信息
                if(key.isAcceptable()) {//接收
                    //获取连接
                    SocketChannel sc = ssc.accept();
                    sc.configureBlocking(false);
                    //注册到selector 为读
                    sc.register(selector,SelectionKey.OP_READ);
                    // a connection was accepted by a ServerSocketChannel.
                } else if (key.isConnectable()) { // 连接
                    // a connection was established with a remote server.
                } else if (key.isReadable()) { //可读
                    //上面注册到读  这边也会接收
                    SocketChannel sc = (SocketChannel)key.channel();
                    ByteBuffer byteBuffer =ByteBuffer.allocate(1024);
                    int len=0;
                    if((len=sc.read(byteBuffer))>0){
                        byteBuffer.flip();
                        System.out.println(new java.lang.String(byteBuffer.array(), 0,len));
                        byteBuffer.clear();
                    }
                    // a channel is ready for reading
                } else if (key.isWritable()) { // 可写
                    // a channel is ready for writing
                }
                //每次迭代末尾的keyIterator.remove()调用。Selector不会自己从已选择键集中移除SelectionKey实例。
                // 必须在处理完通道时自己移除。下次该通道变成就绪时，Selector会再次将其放入已选择键集中
                keyIterator.remove();
            }
        }
    }

    /**
     * 管道pipe测试
     * @throws IOException
     */
    public static void pipeTest() throws IOException {
        Pipe pipe = Pipe.open();
        new Thread(() -> {
            Pipe.SourceChannel sourceChannel = pipe.source();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            try {
                int read = sourceChannel.read(byteBuffer);
                byteBuffer.flip();
                System.out.println("data: " + new String(byteBuffer.array(),0,read));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            Pipe.SinkChannel sinkChannel = pipe.sink();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            byteBuffer.put("this is first!".getBytes());
            byteBuffer.flip();
            try {
                sinkChannel.configureBlocking(true);
                sinkChannel.write(byteBuffer);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }).start();
    }
}
