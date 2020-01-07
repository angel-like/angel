/**
 * Copyright 2018 人人开源 http://www.renren.io
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.xmsy.server.zxyy.payment.service.utils;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 支付配置Redis
 *
 * @author aleng
 * @email xxxxxx
 * @date 2017/7/18 21:08
 */
@Component
public class PayConfigRedis {
    @Autowired
    private RedisUtils redisUtils;

    public void saveOrUpdate(Map<String,String> map) {
        if(map == null){
            return ;
        }
        String key = RedisKeys.getPayConfigKey("callbackIp");
        redisUtils.set(key, map);
    }

    public void delete(String configKey) {
        String key = RedisKeys.getPayConfigKey(configKey);
        redisUtils.delete(key);
    }

    public Map<String,String> get(String configKey){
        String key = RedisKeys.getPayConfigKey(configKey);
        return redisUtils.get(key, Map.class);
    }
}
