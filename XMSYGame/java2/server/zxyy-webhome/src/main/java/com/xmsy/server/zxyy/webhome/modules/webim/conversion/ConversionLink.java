package com.xmsy.server.zxyy.webhome.modules.webim.conversion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xmsy.network.sina.SinaUtils;
import com.xmsy.server.zxyy.webhome.common.exception.RRException;
import com.xmsy.server.zxyy.webhome.common.utils.ErrorCode;
import com.xmsy.server.zxyy.webhome.common.utils.R;
import com.xmsy.server.zxyy.webhome.constant.ThirdPartyDef;
import com.xmsy.server.zxyy.webhome.modules.manager.sysconfig.service.SysConfigService;

import cn.jiguang.common.utils.StringUtils;

@RestController
@RequestMapping("webim/v1/sina")
public class ConversionLink {
	@Autowired
	private SysConfigService SysConfigService;
	/**
	 * 
	 * @param LongUrl  要转换的长链接
	 * @return
	 */
	@GetMapping("/conversion-link")
	public R conversionLink(@RequestParam String longUrl) {//
		
		String enable=SysConfigService.selectByParamKey(ThirdPartyDef.SINA, ThirdPartyDef.ENABLE);
		if(!Boolean.valueOf(enable).booleanValue()) {
			throw new RRException(ErrorCode.SinaCode.EXCHANGE_ENABLE_ERROR.getErrMsg(),
					ErrorCode.SinaCode.EXCHANGE_ENABLE_ERROR.getCode());
		}
		String requestUrl=SysConfigService.selectByParamKey(ThirdPartyDef.SINA, ThirdPartyDef.REQUEST_URL);
		String appkey=SysConfigService.selectByParamKey(ThirdPartyDef.SINA, ThirdPartyDef.APPKEY);
		String links = SinaUtils.conversionLinks(requestUrl, appkey, longUrl);
		if(StringUtils.isEmpty(links)) {
			throw new RRException(ErrorCode.SinaCode.SINA_EXCHANGE_ERROR.getErrMsg(),
					ErrorCode.SinaCode.SINA_EXCHANGE_ERROR.getCode());
		}
		return R.ok().put("shortUrl", links);
	}

}
