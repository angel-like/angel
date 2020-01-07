package com.xmsy.server.zxyy.webhome.modules.app.login.param;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class OnlineInfo {
    private Long  gameId;

    private  String  url;

}
