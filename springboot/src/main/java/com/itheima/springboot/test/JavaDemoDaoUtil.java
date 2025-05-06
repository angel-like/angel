package com.itheima.springboot.test;

public class JavaDemoDaoUtil {
    private JavaDemoDao javaDemoDao;

    public JavaDemoDao getJavaDemoDao() {
        if (this.javaDemoDao == null) {
            javaDemoDao = new JavaDemoDao();
        }
        return javaDemoDao;
    }
}
