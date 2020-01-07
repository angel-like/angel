package com.xmsy.common.define.page;

import com.xmsy.common.define.exception.ParamInvalidException;

/**
 * 
 * .分页参数校验
 *
 * @author chenjisi
 * @date 2018年1月10日 下午3:50:54
 * @Description: TODO
 */
public class PageParamValidator {

	public static void pageParamValidate(PageParam pageParam) throws ParamInvalidException {
		if (pageParam.getPage() < 1) {
			throw new ParamInvalidException("当前页数必须大于0,分页从第一页查起");
		}
		if (pageParam.getLimit() < 1) {
			throw new ParamInvalidException("每页数量必须大于0");
		}
	}
}
