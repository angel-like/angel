package com.atguigu.springcloud.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
// 链式风格访问
@Accessors(chain = true)
public class Dept implements Serializable{
    private static final long serialVersionUID = -1;
    private Long deptNo;
    private String dName;
    private String dbSource;
}
