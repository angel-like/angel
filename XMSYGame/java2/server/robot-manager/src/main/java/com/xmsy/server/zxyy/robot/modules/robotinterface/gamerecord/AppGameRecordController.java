package com.xmsy.server.zxyy.robot.modules.robotinterface.gamerecord;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xmsy.server.zxyy.robot.common.utils.R;
import com.xmsy.server.zxyy.robot.modules.manager.gamerecord.entity.GameRecordParams;
import com.xmsy.server.zxyy.robot.modules.manager.gamerecord.service.GameRecordService;

import lombok.extern.slf4j.Slf4j;

/**
 * 游戏记录
 *
 * @author adu
 * @email xxxxx
 * @date 2019-02-20 16:00:25
 */
@Slf4j
@RestController
@RequestMapping("V1.0/robotGame")
public class AppGameRecordController {
	@Autowired
	private GameRecordService gameRecordService;
	

	/**
	 * 总游戏记录
	 */
	@PostMapping("/GameRecord")
	public R gameRecord(@RequestBody @Valid GameRecordParams gameRecordParams) {
		//log.info("[GameRecord] 游戏记录请求参数 {}", gameRecordParams);
		try {
			gameRecordService.appSaveGameRecord(gameRecordParams);
		} catch (Exception e) {
			log.error("save GameRecord entity {}",gameRecordParams);
			log.error("save GameRecord",e);
		}
		
		return R.ok();
	}

	

}
