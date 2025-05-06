package com.itheima.springboot;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class otherTest {
    public static void main(String[] args) {
        try {
            int a=1/1;
            System.out.println(a);
        }finally {
            System.out.println("测试");
        }

        System.out.println("其他信息");
        log.debug("其他信息");
        Object obh=null;
        if(obh instanceof StudyText) {
            System.out.println("q2");
        }
    }
}
