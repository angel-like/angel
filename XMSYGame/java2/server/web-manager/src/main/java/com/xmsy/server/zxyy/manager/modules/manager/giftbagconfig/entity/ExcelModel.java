package com.xmsy.server.zxyy.manager.modules.manager.giftbagconfig.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @Description: 为了方便扩展，这里的字段命名规则为col[列]+1[列数]
 * 例如：col1代表的就是第一列的数据，通过@Excel(name=?)注解进行区分
 * @Author: 杨东川【http://blog.csdn.net/yangdongchuan1995】
 * @Date: Created in  2018-2-6
 */
@Data
public class ExcelModel {
    // 官方文档 http://easypoi.mydoc.io/
    @Excel(name = "兑换码(数字及字母，且输入的字符的个数为10个字符)")
    @NotNull(message = "兑换码不能为空")
//    @Max(value = 1000)
    @Pattern(regexp ="^[0-9a-zA-Z]{10}$", message = "兑换码:输入数字及字母,且输入的字符的个数为10个字符;")
    private String exchangeCode;
    @Excel(name = "兑换额度(最多包含两位小数的正数)")
//    @NotBlank(message = "该字段不能为空")
    @NotNull(message = "兑换额度不能为空")
//    @Pattern(regexp ="(^[1-9](\\d+)?(\\.\\d{1,2})?$)|(^\\d\\.\\d{1,2}$)", message = "最多包含两位小数的正数")
    private BigDecimal acountMoney;
    @Excel(name = "兑换密码(6到20位的数字或字母)")
    @Pattern(regexp = "^[a-zA-Z\\d]{6,20}$", message = "兑换密码:请输入6到20位的数字或字母")
    private String password;
    @Excel(name = "总兑换次数")
    private Integer exchangeTotalNum;
    private  Integer exchangeNum;
    @Excel(name = "指定兑换者账号（不填写则为不限制）")
    private String exchangeAccount;
    @Excel(name = "单个用户限制次数（0:单次，1:多次)")
    private Integer  limitTimes;
    private Boolean limitTimesUser;
    @Excel(name = "周期")
    private  Integer period;

    @Excel(name = "可以领取次数")
    private Integer receiveTimes;

    @Excel(name = "生效时间(必填)", format = "yyyy/MM/dd")
    @NotNull(message = "生效时间不能为空")
    @Past(message = "生效时间有误")
//    @NotBlank(message = "该字段不能为空")
    private Date startTime;
//    @NotNull
    @Excel(name = "结束时间", format = "yyyy/MM/dd")
    @Past(message = "结束时间有误")
    private Date endTime;
    private Date createTime;

}
