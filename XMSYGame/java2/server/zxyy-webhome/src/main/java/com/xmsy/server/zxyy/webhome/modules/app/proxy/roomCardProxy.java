package com.xmsy.server.zxyy.webhome.modules.app.proxy;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.modules.manager.roomcardproxy.dao.RoomCardProxyDao;
/**
 * 
 * 房卡代理说明接口
 * 
 * @author axiong
 * @email xxxxx
 * @date 2019-07-02 16:00:25
 */
@RestController
@RequestMapping("V1.0/App")
public class roomCardProxy {
	@Autowired
	private RoomCardProxyDao roomCardProxyDao;
	/**
	 * 房卡代理说明接口
	 * @return
	 */
	@GetMapping("/getRoomCardProxy")
	public R getRoomCardProxyData() {
		List<Map<String, Object>> list = roomCardProxyDao.selectRoomCardProxy();
		return R.ok().put("data", list);
	}
}
