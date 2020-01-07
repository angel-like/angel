package com.xmsy.server.zxyy.manager.modules.manager.giftbagconfig.dao;

import com.xmsy.server.zxyy.manager.modules.manager.giftbagconfig.entity.ExcelModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description: 为了方便扩展, 这里直接使用注解的形式进行绑定sql语句,对应的实体类：com.ydc.excel_to_db.domain.ExcelModel
 * @Author: 杨东川【http://blog.csdn.net/yangdongchuan1995】
 * @Date: Created in  2018-2-6
 */
@Mapper
public interface ExcelModelMapper {
    /**
     * @Description: 通过“IGNORE”关键字，使插入数据的主键“已存在”时也不会报异常
     * @Param: [excelModel]
     * @Retrun: long 插入成功，返回 1，插入失败，返回 0；
     */
    // 达梦数据库sql
    //    @Insert("insert   into CQRECTIFY.excelmodel(A, B, C, D, E, F, G,H,I,J,K,L,M, N,O,P,Q)values("
    //           + "#{col1}, #{col2}, #{col3}, #{col4}, #{col5},#{col6},#{col7},#{col8} ,#{col9} ,#{col10} ,#{col11} ,#{col12} ,#{col13} ,#{col14} ,#{col15},#{col16},#{col7} )")
    // mysql数据库sql
    @Insert("insert  ignore into gift_bag_config(exchange_code, acount_money, password, exchange_total_num, exchange_account,limit_times_user, period, receive_times,start_time,end_time,create_time,exchange_num)values("
            + "#{exchangeCode}, #{acountMoney}, #{password}, #{exchangeTotalNum}, #{exchangeAccount},#{limitTimesUser},#{period},#{receiveTimes} ,#{startTime} ,#{endTime},#{createTime},#{exchangeNum})")
    long insert(ExcelModel excelModel);
}
