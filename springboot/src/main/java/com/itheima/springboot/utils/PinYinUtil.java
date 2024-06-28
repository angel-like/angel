package com.itheima.springboot.utils;

import java.util.ArrayList;

import java.util.List;

import net.sourceforge.pinyin4j.PinyinHelper;

import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;

import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;

import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;

public class PinYinUtil {

    private static HanyuPinyinOutputFormat format = null;

    static {

        format = new HanyuPinyinOutputFormat();

//拼音小写

        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);

//无音标方式；WITH_TONE_NUMBER：1-4数字表示英标；WITH_TONE_MARK：直接用音标符(必须WITH_U_UNICODE否则异常

        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

//用v表示ü

        format.setVCharType(HanyuPinyinVCharType.WITH_V);

    }

    /**
     * @param str
     * @return
     * @Description: 返回字符串的拼音
     */

    public static String[] getCharPinYinString(String str) {

        if (str == null || str.length() < 1) {

            return null;

        }

        List result = new ArrayList();

//对字符串中的记录逐个分析

        for (int i = 0; i < str.length(); i++) {

            result = getCharPinYinString(str.charAt(i), result);

        }

        return (String[]) result.toArray(new String[result.size()]);

    }

    /**
     * @param c
     * @param list
     * @return
     * @Description: 将字符c的拼音拼接到list中的记录中
     */

    private static List getCharPinYinString(char c, List list) {
        String[] strs = getCharPinYinString(c);
        List result = new ArrayList();
        //如果解析出的拼音为空，判断字符C是否为英文字母，如果是英文字母则添加值拼音结果中
        if (strs == null) {
            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                c = c <= 91 ? (char) (c + 32) : c;
                if (list == null || list.size() == 0) {
                    result.add(c + "");
                } else {
                    for (Object s : list) {
                        result.add(s.toString() + c);
                    }
                }
                return result;
            }
            return list;
        }
        //将字符C的拼音首和已存在的拼音首组合成新的记录
        for (String str : strs) {
            if (list == null || list.size() == 0) {
                result.add(str);
            } else {
                for (Object s : list) {
                    result.add(s + str);
                }
            }
        }
        return result;
    }

    /**
     * @param c
     * @return
     * @Description: 返回汉字的拼音
     */

    public static String[] getCharPinYinString(char c) {
        try {
            //返回字符C的拼音
            return PinyinHelper.toHanyuPinyinStringArray(c, format);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        String[] a = getCharPinYinString('曾');
        System.out.println(a);
    }
    /**
     * @param str
     * @return
     * @Description: 返回字符串的拼音的首字母
     */

    public static String[] getCharPinYinChar(String str) {

        if (str == null || str.length() < 1) {

            return null;

        }

        List result = new ArrayList();

//对字符串中的记录逐个分析

        for (int i = 0; i < str.length(); i++) {

            result = getCharPinYinChar(str.charAt(i), result);

        }

        return (String[]) result.toArray(new String[result.size()]);

    }

    /**
     * @param c
     * @param list
     * @return
     * @Description: 将字符c的拼音首字母拼接到list中的记录中
     */

    private static List getCharPinYinChar(char c, List list) {
        String[] chars = getCharPinYinChar(String.valueOf(c));
        List result = new ArrayList();
        //如果解析出的拼音为空，判断字符C是否为英文字母，如果是英文字母则添加值拼音结果中
        if (chars == null) {
            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                c = c < 91 ? (char) (c + 32) : c;
                if (list == null || list.size() == 0) {
                    result.add(c + "");
                } else {
                    for (Object s : list) {
                        result.add(s.toString() + c);
                    }
                }
                return result;
            }
            return list;
        }
        return null;
    }
}