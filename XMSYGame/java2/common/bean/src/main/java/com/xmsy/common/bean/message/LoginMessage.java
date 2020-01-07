package com.xmsy.common.bean.message;

import lombok.Data;

@Data
public class LoginMessage extends BaseMessage{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Long userId;
    /**
     * 用户账号
     */
    private String userAccount;
}
