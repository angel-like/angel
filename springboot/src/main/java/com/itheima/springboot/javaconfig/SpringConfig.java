package com.itheima.springboot.javaconfig;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.jolbox.bonecp.BoneCPDataSource;

@Configuration// 通过该注解来表明该类是一个Spring的配置，相当于一个xml文件
@ComponentScan(basePackages = "com.itheima.springboot.javaconfig")// 配置扫描包
@PropertySource(value = { "classpath:jdbc.properties","classpath:no.exit" }, ignoreResourceNotFound = true)//忽略不存在的文件
public class SpringConfig {

    @Bean  //这也就表示该类已经交给spring控制反转了
    // 通过该注解来表明是一个Bean对象，相当于xml中的<bean id="getUserDAO"  class="">
    public UserDAO getUserDAO() {//方法名就是bean中的id，因为service中使用的是对象注入，无关id
        return new UserDAO(); // 直接new对象做演示
    }

    @Value("${jdbc.url}")
    private String jdbcUrl;

    @Value("${jdbc.driverClassName}")
    private String jdbcDriverClassName;

    @Value("${jdbc.username}")
    private String jdbcUsername;

    @Value("${jdbc.password}")
    private String jdbcPassword;

    @Bean(destroyMethod = "close")//spring里的bean：IOC（控制反转）     然后里面的属性就是注入 ID（属性注入）
    public DataSource dataSource() {
        BoneCPDataSource boneCPDataSource = new BoneCPDataSource();
        // 数据库驱动
        boneCPDataSource.setDriverClass(jdbcDriverClassName);
        // 相应驱动的jdbcUrl
        boneCPDataSource.setJdbcUrl(jdbcUrl);
        // 数据库的用户名
        boneCPDataSource.setUsername(jdbcUsername);
        // 数据库的密码
        boneCPDataSource.setPassword(jdbcPassword);
        // 检查数据库连接池中空闲连接的间隔时间，单位是分，默认值：240，如果要取消则设置为0
        boneCPDataSource.setIdleConnectionTestPeriodInMinutes(60);
        // 连接池中未使用的链接最大存活时间，单位是分，默认值：60，如果要永远存活设置为0
        boneCPDataSource.setIdleMaxAgeInMinutes(30);
        // 每个分区最大的连接数
        boneCPDataSource.setMaxConnectionsPerPartition(100);
        // 每个分区最小的连接数
        boneCPDataSource.setMinConnectionsPerPartition(5);
        return boneCPDataSource;
    }

}
