package com.xmsy.server.zxyy.game.listener;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApplicationInitBean implements InitializingBean {
	@Autowired
    private ReloadInit reloadInit;

    @Override
    public void afterPropertiesSet() throws Exception {
    	reloadInit.reloadInit();
    }
    

    

}
