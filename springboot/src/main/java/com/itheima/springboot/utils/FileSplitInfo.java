package com.itheima.springboot.utils;

import lombok.Data;

@Data
public class FileSplitInfo {
    //拆分文件路径
    private String filePath;
    //拆分文件个数
    private Integer fileCount;
}
