package com.itheima.springboot.demo;

import com.alibaba.fastjson.JSON;
import com.sun.deploy.net.HttpUtils;
import lombok.Data;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class GongShangTest {
    public static void main(String[] args) {
        String detailStr = "[{\"order_no\":\"20051800000390\",\"sku\":\"2236191440\",\"num\":2}," +
                "{\"order_no\":\"20051800000390\",\"sku\":\"2236191448\",\"num\":2}," +
                "{\"order_no\":\"20051800000451\",\"sku\":\"2236191441\",\"num\":2}," +
                "{\"order_no\":\"20051800000451\",\"sku\":\"SKU003\",\"num\":2}," +
                "{\"order_no\":\"20051800000549\",\"sku\":\"2236191447\",\"num\":1}," +
                "{\"order_no\":\"20051800000549\",\"sku\":\"SKU002\",\"num\":1}," +
                "{\"order_no\":\"20051800000576\",\"sku\":\"2236191440\",\"num\":1}]";
        List<HashMap> detailList = JSON.parseArray(detailStr, HashMap.class);
                // 这里定义3个规则条件 matchRule1、matchRule2、matchRule3

        // 这里定义3个规则条件 matchRule1、matchRule2、matchRule3
        MatchRule matchRule1 = new MatchRule();
        matchRule1.setSkuTail("1448");
        MatchRule matchRule2 = new MatchRule();
        matchRule2.setNum(1);
        MatchRule matchRule3 = new MatchRule();
        matchRule3.setNum(1);
        matchRule3.setOrderNoTail("576");

        System.out.println("找出订单明细sku尾数是1448的数据");
        System.out.println(findDetails(detailList, matchRule1));
        System.out.println("找出订单明细num为1的数据");
        System.out.println(findDetails(detailList, matchRule2));
        System.out.println("找出订单明细num为1并且单号尾数是576的数据");
        System.out.println(findDetails(detailList, matchRule3));

    }

    public static List<HashMap> findDetails(List<HashMap> detailList, MatchRule matchRule) {
        // 这里是具体的实现代码
        List<HashMap> resList = detailList.stream().filter(e -> {
            //匹配sku尾数的规则
            String skuTailRule = matchRule.getSkuTail();
            Boolean skuTailFlag = true;
            if (!StringUtils.isEmpty(skuTailRule)) {
                String sku = String.valueOf(e.get("sku"));
                String skuTail = "";
                if (sku.length() >= skuTailRule.length()) {
                    skuTail = sku.substring(sku.length() - skuTailRule.length());
                }
                if (!skuTail.equals(skuTailRule)) {
                    skuTailFlag = false;
                }
            }
            //匹配num的规则
            Integer numRule = matchRule.getNum();
            Boolean numFlag = true;
            if (numRule != null) {
                Integer num = Integer.valueOf(String.valueOf(e.get("num")));
                if (numRule.compareTo(num) != 0) {
                    numFlag = false;
                }
            }
            //匹配订单尾号规则
            String orderNoTailRule = matchRule.getOrderNoTail();
            Boolean orderNoTailFlag = true;
            if (!StringUtils.isEmpty(orderNoTailRule)) {
                String orderNo = String.valueOf(e.get("order_no"));
                String orderNoTail = "";
                if (orderNo.length() >= orderNoTailRule.length()) {
                    orderNoTail = orderNo.substring(orderNo.length() - orderNoTailRule.length());
                }
                if (!orderNoTail.equals(orderNoTailRule)) {
                    orderNoTailFlag = false;
                }
            }
            return skuTailFlag && numFlag && orderNoTailFlag;
        }).collect(Collectors.toList());
        return resList;
    }

    @Data
    public static class MatchRule {
        /**
         * 匹配尾数
         */
        private String skuTail;
        /**
         * 匹配num
         */
        private Integer num;
        /**
         * 匹配订单号尾数的规则
         */
        private String orderNoTail;
    }


   /* @Test
    @Transactional
    public void addOrderList() {
        // 获取订单编号
        String orderNo = orderDao.selectOne().getOrderNo();
        if (StringUtils.isEmpty(orderNo)) {
            //根据需求做对应处理
            return;
        }
        // 获取第三方订单数据
        List<Order> orderList = HttpUtils.getThirdOrderList();
        if (orderList == null || orderList.isEmpty()) {
            return;
        }
        // 匹配过滤
        List<Order> addOrderList = orderList.stream().filter(o -> orderNo.equals(o.getOrderNo())).collect(Collectors.toList());
        if (addOrderList == null || addOrderList.isEmpty()) {
            return;
        }
        // 批量保存过滤后的数据
        int start = 0;
        int end = 1000;
        int size = addOrderList.size();
        while (start + end < size) {
            orderDao.saveAll(addOrderList.subList(start, start + end));
            start += end;
        }
        //插入剩余还未插入的
        orderDa.saveAll(addOrderList.subList(start, size));
    }*/

    @Data
    public static class Order{
        /**
         * 匹配尾数
         */
        private String orderNo;
        /**
         * 匹配num
         */
        private Integer b;
        /**
         * 匹配订单号尾数的规则
         */
        private String c;
    }

}
