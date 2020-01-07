package com.xmsy.server.zxyy.manager.modules.app.message;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.manager.noticemanagement.service.NoticeManagementService;

/**
 * 公告
 *
 * @author adu
 * @email xxxxx
 * @date 2019-02-18 11:30:25
 */
@RestController
@RequestMapping("V1.0/App")
public class AppNoticeController {
	@Resource
	private NoticeManagementService noticeManagementService;
	
	
	
	/**
	 * 公告列表
	 */
	@GetMapping("/noticeList")
	public R noticeList(HttpServletRequest httpServletRequest) {
		String token=httpServletRequest.getHeader("token");
		return R.ok().put("data", noticeManagementService.findMemberNoticeListByEffect(token));
	}
	
	

}
