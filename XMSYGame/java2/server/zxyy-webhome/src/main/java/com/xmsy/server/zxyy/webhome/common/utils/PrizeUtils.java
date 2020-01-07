package com.xmsy.server.zxyy.webhome.common.utils;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.xmsy.server.zxyy.webhome.modules.manager.luckyconfig.entity.LuckyConfigEntity;
@Component
public class PrizeUtils {

    public static int getPrizeIndex(List<LuckyConfigEntity> prizes,Long integral) {
//        DecimalFormat df = new DecimalFormat("######0.0000000");
        int random = -1;
         double max =0;
        try{
            //计算总权重
          double sumWeight = 0;
            LuckyConfigEntity luckyConfigEntity = prizes.get(0);
            if(luckyConfigEntity.getPropId()==2)
            {
              max = luckyConfigEntity.getPropNum()*3;
            } else {
                 max = luckyConfigEntity.getPropNum();
            }
            for (LuckyConfigEntity prize : prizes) {
               double num  =0;
                if(prize.getPropId()==1) {
                    if(num==0){
                        num=1;
                    }
                    num =prize.getPropNum();
                    sumWeight += max/num;
                    prize.setWeight(max/num);
                }
               else if(prize.getPropId()==2){
                    if(num==0){
                        num=1;
                    }
                       num =prize.getPropNum()*3;
                    sumWeight+= max/num;
                    prize.setWeight (max/num);

                }else if(prize.getPropId()==0){
                    sumWeight+=max/1;
                    prize.setWeight(max/1);

                }
            }
//            for(sys : prizes){
//                sumWeight += p.getPrize_weight();
//            }

            //产生随机数
            double randomNumber;
            randomNumber = Math.random();

            //根据随机数在所有奖品分布的区域并确定所抽奖品
            double d1 = 0; 
            double d2 = 0;
            for(int i=0;i<prizes.size();i++){

            d2 += Double.parseDouble(String.valueOf(prizes.get(i).getWeight()))/sumWeight;
           if(i==0){
                  d1 = 0;
                       }else{
                    d1 +=Double.parseDouble(String.valueOf(prizes.get(i-1).getWeight()))/sumWeight;
                       }
                if(randomNumber >= d1 && randomNumber <= d2){
                    random = i;
                    break;
                }
            }
        }catch(Exception e){
            System.out.println("生成抽奖随机数出错，出错原因：" +e.getMessage());
        }
        return random;
    }




    public static void main(String[] agrs) {
        int i = 0;
//        int b =10;
        int[] result=new int[8];
        List<LuckyConfigEntity> settings =new ArrayList<>();
        LuckyConfigEntity p1 = new LuckyConfigEntity();
        p1.setName("一等奖");
        p1.setPropId(1l);//奖品的权重设置成1
        p1.setPropNum(288l);
        p1.setPropName("金币");
        settings.add(p1);
        LuckyConfigEntity p2 = new LuckyConfigEntity();
        p2.setName("二等奖");
        p2.setPropId(1l);//奖品的权重设置成1
        p2.setPropNum(50l);
        p2.setPropName("金币");
        settings.add(p2);
        LuckyConfigEntity p3 = new LuckyConfigEntity();
        p3.setName("三等奖");
        p3.setPropId(1l);//奖品的权重设置成1
        p3.setPropNum(30l);
        p3.setPropName("金币");
        settings.add(p3);

        LuckyConfigEntity p4 = new LuckyConfigEntity();
        p4.setName("四等奖");
        p4.setPropId(-1l);//奖品的权重设置成1
        p4.setPropNum(3l);
        p4.setPropName("再来一次");
        settings.add(p4);
        LuckyConfigEntity p5 = new LuckyConfigEntity();
        p5.setName("五等奖");
        p5.setPropId(1l);//奖品的权重设置成1
        p5.setPropNum(5l);
        p5.setPropName("金币");
        settings.add(p5);
        LuckyConfigEntity p6 = new LuckyConfigEntity();
        p6.setName("六等奖");
        p6.setPropId(1l);//奖品的权重设置成1
        p6.setPropNum(2l);
        p6.setPropName("金币");
        settings.add(p6);
        LuckyConfigEntity p7 = new LuckyConfigEntity();
        p7.setName("七等奖");
        p7.setPropId(1l);//奖品的权重设置成1
        p7.setPropNum(1l);
        p7.setPropName("金币");
        settings.add(p7);
        LuckyConfigEntity p8 = new LuckyConfigEntity();
        p8.setName("八等奖");
        p8.setPropId(0l);//奖品的权重设置成1
        p8.setPropNum(0l);
        p8.setPropName("谢谢参与");
        settings.add(p8);
        Long integral = 20l;
        System.out.println("抽奖开始");
        PrizeUtils prizeUtils = new PrizeUtils();
        for (i = 0; i < 10000; i++)// 打印100个测试概率的准确性
        {
            @SuppressWarnings("static-access")
			int selected= prizeUtils.getPrizeIndex(settings,integral);
            System.out.println("第"+i+"次抽中的奖品为："+settings.get(selected).getName());
            result[selected]++;
            System.out.println("--------------------------------");
        }
        System.out.println("抽奖结束");
        System.out.println("每种奖品抽到的数量为：");
        System.out.println("一等奖："+result[0]);
        System.out.println("二等奖："+result[1]);
        System.out.println("三等奖："+result[2]);
        System.out.println("四等奖："+result[3]);
        System.out.println("五等奖："+result[4]);
        System.out.println("六等奖："+result[5]);
        System.out.println("七等奖："+result[6]);
        System.out.println("八等奖："+result[7]);
    }


}
