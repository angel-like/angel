package com.xmsy.server.zxyy.game.utils;

import com.xmsy.common.define.constant.ResultDef;
import com.xmsy.common.define.result.GlobalResult;
import com.xmsy.server.zxyy.game.common.utils.R;

/**
 * 返回处理
 * 
 * @author Administrator
 *
 */
public class ResultUtil {

	/**
	 * 是否操作成功
	 * 
	 * @param globalResult
	 * @return
	 */
	public static boolean isSuccess(GlobalResult<?> globalResult) {
		if (null == globalResult) {
			return false;
		} else if (ResultDef.SUCCESS == globalResult.getCode()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 添加，修改，删除操作返回成功或失败对象
	 * 
	 * @param globalResult
	 * @return
	 */
	public static R getModifyResult(GlobalResult<?> globalResult) {
		if (null == globalResult) {
			return R.error();
		} else if (ResultDef.SUCCESS == globalResult.getCode()) {
			return R.ok();
		} else {
			return R.error(globalResult.getErrorMsg());
		}
	}

	/**
	 * 查询操作返回成功或失败对象
	 * 
	 * @param globalResult
	 * @return
	 */
	public static R getQueryResult(String paramName, GlobalResult<?> globalResult) {
		if (null == globalResult) {
			return R.error();
		} else if (ResultDef.SUCCESS == globalResult.getCode()) {
			return R.ok().put(paramName, globalResult.getData());
		} else {
			return R.error(globalResult.getErrorMsg());
		}
	}
}
