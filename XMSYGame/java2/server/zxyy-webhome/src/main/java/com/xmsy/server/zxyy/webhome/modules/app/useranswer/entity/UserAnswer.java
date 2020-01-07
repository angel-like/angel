package com.xmsy.server.zxyy.webhome.modules.app.useranswer.entity;

import lombok.Data;

@Data
public class UserAnswer {
    private String userAccount;// 用户账号
    private Long questionId;  //问题id
    private String answer;    //答案
}
