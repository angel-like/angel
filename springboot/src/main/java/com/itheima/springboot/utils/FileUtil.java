package com.itheima.springboot.utils;

import cn.hutool.core.text.StrBuilder;
import org.springframework.util.StringUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 文件操作工具类
 */
public class FileUtil {
    /**
     * 读取文件信息
     * @param filePath  文件路径（本地的文件路径）
     * @param charset 字符编码格式
     * @return
     */
    public static String readTextFile(String filePath, Charset charset){
        if(StringUtils.isEmpty(filePath)){
            return null;
        }
        File file=new File(filePath);
        StringBuilder sb=new StringBuilder();
        BufferedReader reader= null;
        String line;
        try {
            reader=new BufferedReader(new InputStreamReader(new FileInputStream(file),charset));
            while ((line=reader.readLine())!=null){
                //读取时，每一行拆分就用 System.lineSeparator()标识来区分
                sb.append(line).append(System.lineSeparator());
            }
        }catch (Exception e){
            System.out.println("读取文本异常"+e);
        }finally {
            try {
                if(reader!=null){
                    reader.close();
                }
            }catch (Exception e){
                System.out.println("流关闭异常"+e);
            }
        }
        return sb.toString();
    }

    /**
     *
     * @param file   要拆分的文件对象
     * @param charset  编码格式
     * @param splitFileLineCount  多少行拆分成一个文件  100000行
     * @param localTmpPath  本地的文件保存路径
     * @return
     */
    public static FileSplitInfo splitTextFile(File file,Charset charset,Integer splitFileLineCount,String localTmpPath){
        StringBuilder sb=new StringBuilder();
        BufferedReader reader= null;
        try {
            String filePath=localTmpPath+"tmpDir"+file.getName()+"/";
            reader=new BufferedReader(new InputStreamReader(new FileInputStream(file),charset));
            String line;
            AtomicLong lineCount=new AtomicLong();
            AtomicLong atomicLong=new AtomicLong(splitFileLineCount);
            Integer fileName=0;
            while ((line=reader.readLine())!=null){
                sb.append(line).append(System.lineSeparator());
                lineCount.incrementAndGet();
                if(atomicLong.getAndDecrement()>=1){
                    continue;
                }
                atomicLong=new AtomicLong(splitFileLineCount);
                //创建一个新文件用来写入
                String txtFileName=fileName+".txt";
                createNewFile(filePath,txtFileName);
                if(!StringUtils.isEmpty(sb.toString())){
                    //数据写入文本文件中
                    FileWriter fileWriter=new FileWriter(filePath+txtFileName,true);
                    BufferedWriter bufferedWriter=new BufferedWriter(fileWriter);
                    bufferedWriter.write(sb.toString());
                    bufferedWriter.flush();
                    bufferedWriter.close();
                }
                atomicLong.set(splitFileLineCount);
                //重置下一个文件数据
                sb =new StringBuilder();
                fileName += 1;
            }
            //将最后的数据写入文本文件中
            if(!StringUtils.isEmpty(sb.toString())){
                String txtFileName=fileName+".txt";
                createNewFile(filePath,txtFileName);
                //数据写入文本文件中
                FileWriter fileWriter=new FileWriter(filePath+txtFileName,true);
                BufferedWriter bufferedWriter=new BufferedWriter(fileWriter);
                bufferedWriter.write(sb.toString());
                bufferedWriter.flush();
                bufferedWriter.close();
            }
            atomicLong.set(splitFileLineCount);
            //重置下一个文件数据
            sb =new StringBuilder();
            fileName += 1;
            FileSplitInfo fileSplitInfo=new FileSplitInfo();
            fileSplitInfo.setFileCount(fileName);
            fileSplitInfo.setFilePath(filePath);
            System.out.println("大小分割完成，总行数："+lineCount.intValue()+"，分割文件数量："+fileName);
            return fileSplitInfo;
        }catch (Exception e){
            System.out.println("拆分文本异常"+e);
        }finally {
            try {
                if(reader!=null){
                    reader.close();
                }
            }catch (Exception e){
                System.out.println("流关闭异常"+e);
            }
            if(file.exists()){
                file.deleteOnExit();
                System.out.println("删除临时文件");
            }
        }
        return null;
    }

    /**
     * 创建新的文件或者文件夹
     * @param filePath
     * @param txtFileName
     * @throws IOException
     */
    private static void createNewFile(String filePath,String txtFileName) throws IOException{
        File tmpFile=new File(filePath);
        if(!tmpFile.exists()){
            tmpFile.mkdirs();
        }
        File txtFile=new File(filePath+txtFileName);
        if(!txtFile.exists()){
            txtFile.createNewFile();
            System.out.println("创建新的文件，路径:"+filePath+"   文件名:"+txtFileName);
        }
    }

    /**
     * 压缩文件
     * @param filePath 要压缩的文件路径
     * @param zipPath  zip文件存放路径
     * @param zipName zip文件名
     */
    public static void zipCompress(String filePath,String zipPath,String zipName){
        File file=new File(filePath);
        try {
            //zip文件夹路径 要存在
            File path =new File(zipPath);
            if(!path.exists()){
                path.mkdirs();
            }
            //zip文件 要存在
            File zipFile=new File(zipPath+zipName);
            if(!zipFile.exists()){
                zipFile.createNewFile();
            }
            FileOutputStream outputStream=new FileOutputStream(zipFile);
            CheckedOutputStream checkedOutputStream=new CheckedOutputStream(outputStream,new CRC32());
            ZipOutputStream zipOutputStream=new ZipOutputStream(checkedOutputStream);
            String baseDir="";
            compress(file,zipOutputStream,baseDir);
            zipOutputStream.finish();
            zipOutputStream.close();
        }catch (Exception e){
            System.out.println("压缩文件异常"+e);
        }
    }

    /**
     *
     * @param file  文件路径对象
     * @param zipOutputStream zip流信息
     * @param baseDir 基本路径
     */
    private static void compress(File file, ZipOutputStream zipOutputStream, String baseDir) {
        if(file.isDirectory()){
            //是文件夹
            compressDir(file,zipOutputStream,baseDir);
        }else {
            //是文件
            compressFile(file,zipOutputStream,baseDir);
        }
    }

    /**
     * 压缩文件夹
     * @param folder
     * @param zipOutputStream
     * @param baseDir
     */
    private static void compressDir(File folder, ZipOutputStream zipOutputStream, String baseDir) {
        File[] files=folder.listFiles();
        if(files!=null &&files.length>0){
            for(File file:files){
                compress(file,zipOutputStream,baseDir+folder.getName()+File.separator);
            }
        }
    }

    /**
     * 压缩文件
     * @param file
     * @param zipOutputStream
     * @param baseDir
     */
    private static void compressFile(File file, ZipOutputStream zipOutputStream, String baseDir) {
        if (!file.exists()){
            System.out.println("文件为空，无法执行压缩");
            return;
        }
        FileInputStream fileInputStream=null;
        BufferedInputStream bis=null;
        try {
            fileInputStream=new FileInputStream(file);
            bis=new BufferedInputStream(fileInputStream);
            ZipEntry zipEntry=new ZipEntry(baseDir+file.getName());
            zipOutputStream.putNextEntry(zipEntry);
            int count;
            byte[] data=new byte[1024];
            while((count=bis.read(data,0,1024))!=-1){
                zipOutputStream.write(data,0,count);
            }
            zipOutputStream.closeEntry();
            bis.close();
        }catch (Exception e){
            System.out.println("压缩文件异常"+e);
        }
    }


    /**
     * 获取两个时间差天数
     * @param startDate
     * @param endDate
     * @return
     */
    public static Integer dateDiff(Date startDate, Date endDate) {
        try {
            long millisecond=endDate.getTime()-startDate.getTime();
            Long days=millisecond/(1000*3600*24);
            return days.intValue();
        }catch (Exception e){
            System.out.println("");
            return null;
        }

    }
}
