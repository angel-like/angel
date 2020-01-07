package com.xmsy.server.zxyy.manager.modules.manager.userotp.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xmsy.server.zxyy.manager.modules.manager.userotp.dao.UserOtpDao;
import com.xmsy.server.zxyy.manager.modules.manager.userotp.entity.UserOtpEntity;
import com.xmsy.server.zxyy.manager.modules.manager.userotp.service.UserOtpService;

@Transactional
@Service("userOtpService")
public class UserOtpServiceImpl extends ServiceImpl<UserOtpDao, UserOtpEntity> implements UserOtpService {

}
