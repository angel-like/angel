package com.itheima.springboot.utils;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.List;

public class EasyExcelUtils {
    /**
     * 通用的导出 (注意导出时，linux时服务器上会使用/tmp/poifiles文件夹)
     *  如果/tmp/poifiles文件夹为root权限，java服务用guest用户启动，则会因为无权限报错，导出一个空字符串，完全无数据
     *  java服务用什么用户启动，就只有启动用户 的权限。
     * @param response
     * @param fileName 文件名称
     * @param list     导出的数据集合
     * @param T  要导出的class对象
     * @param <T>   泛型类对象
     * @throws Exception
     */
    public static<T>  void writeLine(HttpServletResponse response, String fileName, List<T> list, Class T) throws Exception {
        //1.设置响应头
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        response.setContentType("application/csv; charset= UTF-8");
        //2.输出流信息
        //EasyExcel.write(response.getOutputStream(), T).sheet("群侠表").sheetNo(0).doWrite(list);
    }

}
