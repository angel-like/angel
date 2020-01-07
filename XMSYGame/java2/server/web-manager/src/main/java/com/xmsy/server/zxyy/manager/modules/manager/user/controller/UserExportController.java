package com.xmsy.server.zxyy.manager.modules.manager.user.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.xmsy.server.zxyy.manager.common.annotation.SysLog;
import com.xmsy.server.zxyy.manager.common.utils.Constant;
import com.xmsy.server.zxyy.manager.common.utils.ExportCSVUtils;
import com.xmsy.server.zxyy.manager.common.utils.MathUtil;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecord.controller.GameInfoComponent;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecord.dao.GameRecordDao;
import com.xmsy.server.zxyy.manager.modules.manager.gamerecord.entity.GameRecordEntity;
import com.xmsy.server.zxyy.manager.modules.manager.sys.controller.AbstractController;
import com.xmsy.server.zxyy.manager.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.manager.modules.manager.user.param.UserParam;
import com.xmsy.server.zxyy.manager.modules.manager.user.service.UserService;
import com.xmsy.server.zxyy.manager.modules.manager.userhierarchy.service.UserHierarchyService;
import com.xmsy.server.zxyy.manager.modules.manager.uservip.service.UserVipService;

import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.core.text.csv.CsvWriter;
import cn.hutool.core.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("user/user")
public class UserExportController extends AbstractController {
	@Autowired
	private UserService userService;
	@Autowired
	private UserVipService userVipService;
	@Autowired
	private UserHierarchyService userHierarchyService;
	@Autowired
	private GameRecordDao gameRecordDao;
	@Autowired
    private GameInfoComponent gameInfo;
	private Map<Long, String> vipMap = new HashMap<Long, String>();
	private Map<Long, String> hierarchyMap = new HashMap<Long, String>();

	/**
	 * 导出csv的数据
	 */
	@SysLog("导出csv的数据")
	@RequestMapping("/exportCSVData")
	@RequiresPermissions("user:user:list")
	public R exportCSVData(UserParam user, HttpServletResponse response) {
		log.info("[exportCSVData] params {}", user);
		long s = System.currentTimeMillis();
		List<Map<String, Object>> dataList = userService.findUserList(user);
		long et = System.currentTimeMillis() - s;
		System.out.println(et);
		System.out.println(dataList.size());
		return R.ok().put("dataList", dataList);
	}

	/**
	 *
	 */
	@SysLog("导出数据")
	@RequestMapping("/exportCSV")
	@RequiresPermissions("user:user:list")
	public void exportCSV(UserParam userParam, HttpServletResponse response) {
		// long s=System.currentTimeMillis();
		// System.out.println("开始");
		List<Map<String, Object>> dataList = userService.findUserListForCsv(userParam);
		// long et=System.currentTimeMillis()-s;
		// System.out.println("查询时间："+(et));
		// System.out.println("size："+(dataList.size()));
		// 指定路径和编码
		String fileName = "user_" + getUserId() + "_" + System.currentTimeMillis() + ".csv";
//		String path = getDownloadFile(fileName);
		String path = ExportCSVUtils.getDownloadFile(fileName);
		// s=System.currentTimeMillis();
		CsvWriter writer = CsvUtil.getWriter(path, CharsetUtil.CHARSET_GBK);
		vipMap = userVipService.getVipMap();
		hierarchyMap = userHierarchyService.getHierarchyMap();
		// 按行写出
		writer.write(getHeadRowStr());
		for (Map<String, Object> user : dataList) {
			writer.write(getRowStr(user));
		}
		writer.close();
		// et=System.currentTimeMillis()-s;
		// System.out.println("组装时间"+(et));
		ExportCSVUtils.exportCSVData(response, fileName, path);
	}

	private String[] getRowStr(Map<String, Object> user) {
		String[] rowData = new String[18];
		rowData[0] = getString(user.get("id"));
		rowData[1] = getString(user.get("account"));
		rowData[2] = getString(user.get("userName"));
		rowData[3] = getString(user.get("phone"));
		if (!"".equals(rowData[3])) {
			rowData[3] = "+86 " + getString(user.get("phone"));
		}
		rowData[4] = vipMap.get(getLong(user.get("vipId")));
		rowData[5] = hierarchyMap.get(getLong(user.get("hierarchyId")));
		rowData[6] = hierarchyMap.get(getLong(user.get("riskHierarchyId")));
		if ("USER".equals(getString(user.get("userType")))) {
			rowData[7] = "普通会员";
		} else {
			rowData[7] = "测试会员";
		}
		rowData[8] = getStatus(user);
		rowData[9] = getString(user.get("registerIp"));
		rowData[10] = MathUtil.getBigDecimal(user.get("yybMoney")).add(MathUtil.getBigDecimal(user.get("unyybMoney")))
				.divide(MathUtil.getBigDecimal(100)).toString();
		rowData[11] = MathUtil.getBigDecimal(user.get("coin"))
				.divide(new BigDecimal(Constant.DB_COIN_RATE), 2, BigDecimal.ROUND_DOWN).toString();
		rowData[12] = getString(user.get("commission"));
		rowData[13] = getString(user.get("roomCard"));
		rowData[14] = getString(user.get("remake"));
		rowData[15] = getString(user.get("rechargeAmount"));
		rowData[16] = getString(user.get("discountAmount"));
		rowData[17] = getString(user.get("takeAmount"));
		return rowData;
	}

//	private String getDownloadFile(String fileName) {
//		try {
//			ApplicationHome home = new ApplicationHome(getClass());
//			log.info("home path:{}",home.getSource().getAbsolutePath());
//			log.info("path:{}",ResourceUtils.getURL("classpath:").getPath());
//			String fileStr = home.getSource().getAbsolutePath() + "/download/csv";
//			fileStr=fileStr.replace("/web-manager.jar", "");
//			File download = new File(fileStr);
//			if (!download.exists()) {
//				download.mkdirs();
//			}
//			 System.out.println("upload url:"+download.getAbsolutePath());
//			return fileStr + "/" + fileName;
//		} catch (FileNotFoundException e2) {
//			e2.printStackTrace();
//		}
//		return null;
//	}

	private String[] getHeadRowStr() {
		String[] rowData = new String[18];
		rowData[0] = "会员ID";
		rowData[1] = "会员账号";
		rowData[2] = "真实姓名";
		rowData[3] = "手机号码";
		rowData[4] = "会员VIP等级";
		rowData[5] = "推广层级";
		rowData[6] = "风控层级";
		rowData[7] = "用户类型";
		rowData[8] = "会员状态";
		rowData[9] = "注册IP";
		rowData[10] = "余额宝";
		rowData[11] = "金币";
		rowData[12] = "佣金余额";
		rowData[13] = "房卡数量";
		rowData[14] = "备注";
		rowData[15] = "总存款";
		rowData[16] = "总优惠";
		rowData[17] = "总取款";
		return rowData;
	}

	private String getString(Object obj) {
		if (obj != null) {
			return obj.toString().trim();
		}
		return "";
	}

	private String getStatus(Map<String, Object> data) {
		boolean forbiddenEnable = getBoolean(data.get("forbiddenEnable"));
		boolean nobetEnable = getBoolean(data.get("nobetEnable"));
		boolean abnormalEnable = getBoolean(data.get("abnormalEnable"));
		boolean frozenEnable = getBoolean(data.get("frozenEnable"));
		String status = "";
		if (forbiddenEnable) {
			status += "拉黑 ";
		}
		if (nobetEnable) {
			status += "停压 ";
		}
		if (abnormalEnable) {
			status += "风控 ";
		}
		if (frozenEnable) {
			status += "冻结 ";
		}
		return "".equals(status) ? "正常" : status;
	}

	private boolean getBoolean(Object obj) {
		if (obj != null && "true".equals(obj.toString())) {
			return true;
		}
		return false;
	}

	private Long getLong(Object obj) {
		if (obj != null) {
			Long r = 0l;
			try {
				r = Long.parseLong(obj.toString());
			} catch (Exception e) {
				// TODO: handle exception
			}
			return r;
		}
		return null;
	}

	// =================================导出试玩账号CSV数据===========================
	/**
	 * 导出试玩账号csv的数据
	 */
	@SysLog("导出csv的数据")
	@RequestMapping("/exportTrialCSVData")
	@RequiresPermissions("user:user:list")
	public void exportTrialCSVData(UserParam user, HttpServletResponse response) {

		List<Map<String, Object>> list = userService.findUserListForCsv(user);
		String fileName = "user_" + getUserId() + "_" + System.currentTimeMillis() + ".csv";
		String path = ExportCSVUtils.getDownloadFile(fileName);
		CsvWriter writer = CsvUtil.getWriter(path, CharsetUtil.CHARSET_GBK);
		// 按行写出
		writer.write(getTrialHeadRowStr()); // 头信息
		for (Map<String, Object> map : list) {
			writer.write(getTrialRowStr(map)); // 行信息
		}
		writer.close();
		ExportCSVUtils.exportCSVData(response, fileName, path);
	}

	// 获取行信息
	private String[] getTrialRowStr(Map<String, Object> map) {
		String[] rowData = new String[8];
		rowData[0] = ExportCSVUtils.getString(map.get("id"));
		rowData[1] = ExportCSVUtils.getString(map.get("account"));
		rowData[2] = ExportCSVUtils.getString(map.get("createTime"));
		rowData[3] = getTrialStatus(map);;
		rowData[4] = ExportCSVUtils.getString(map.get("registerIp"));
		rowData[5] = ExportCSVUtils.getString(map.get("money"));
		rowData[6] = ExportCSVUtils.getString(MathUtil.getBigDecimal(map.get("coin")).divide(MathUtil.getBigDecimal(100)));
		rowData[7] = ExportCSVUtils.getString(map.get("remake"));
		return rowData;
	}
	//试玩账号账号状态
	private String getTrialStatus(Map<String, Object> data) {
		boolean forbiddenEnable = getBoolean(data.get("forbiddenEnable"));
		boolean nobetEnable = getBoolean(data.get("nobetEnable"));
		boolean frozenEnable = getBoolean(data.get("frozenEnable"));
		String status = "";
		if (forbiddenEnable) {
			status += "停用 ";
		}else {
			status += "正常 ";
		}
		if (nobetEnable) {
			status += "停压 ";
		}else {
			status += "未停压 ";
		}
		if (frozenEnable) {
			status += "冻结 ";
		}else {
			status += "未冻结 ";
		}
		return status;
	}
	// 获取头信息
	private String[] getTrialHeadRowStr() {
		String[] rowData = new String[8];
		rowData[0] = "会员ID";
		rowData[1] = "会员账号";
		rowData[2] = "创建时间";
		rowData[3] = "账号状态";
		rowData[4] = "注册IP";
		rowData[5] = "余额";
		rowData[6] = "金币";
		rowData[7] = "备注";
		return rowData;
	}
	// =================================导出会员注单CSV数据===========================
	/**
	 * 导出试玩账号csv的数据
	 */
	@SysLog("导出csv的数据")
	@RequestMapping("/userGemeRecord")
	@RequiresPermissions("user:user:list")
	public void exportTrialCSVData(@RequestParam(required = false, value = "startTime") String startTime,
			@RequestParam(required = false, value = "endTime") String endTime, GameRecordEntity gameRecordEntity,
			HttpServletResponse response) {
		gameRecordEntity.setRobot(false);
		//把账号转为id去查询      游客升级为会员使用的
		if(StringUtils.isNotBlank(gameRecordEntity.getUserAccount())) {
			List<UserEntity> ul = userService.findUserListByAccount(new String[]{gameRecordEntity.getUserAccount()});
			if (ul!=null && ul.size() >0){
				gameRecordEntity.setUserId(ul.get(0).getId());
				gameRecordEntity.setUserAccount("");
			}
		}
		List<Map<String, Object>> list = gameRecordDao.getGameRecordsCSV(gameRecordEntity, startTime,
				endTime);
		BigDecimal totalPrizeCoins=BigDecimal.ZERO;
		BigDecimal totalValidBet=BigDecimal.ZERO;
		totalValidBet=gameRecordDao.sumValidBet(gameRecordEntity,startTime,
				endTime);
		totalPrizeCoins = gameRecordDao.sumPrizeCoinsByParam(gameRecordEntity, startTime, endTime);
		totalPrizeCoins=totalPrizeCoins==null?BigDecimal.ZERO:totalPrizeCoins;
		Map<String, Object> totalMap = new HashMap<String, Object>();
		totalMap.put("userId", "总有效投注:");
		totalMap.put("userAccount", totalValidBet.divide(new BigDecimal(100)));
		totalMap.put("gameRoundNo", " 盈亏总计:");
		totalMap.put("round", totalPrizeCoins.divide(new BigDecimal(100)));
		list.add(totalMap);
		String fileName = "user_" + getUserId() + "_" + System.currentTimeMillis() + ".csv";
		String path = ExportCSVUtils.getDownloadFile(fileName);
		CsvWriter writer = CsvUtil.getWriter(path, CharsetUtil.CHARSET_GBK);
		// 按行写出
		writer.write(getGemeRecordHeadRowStr()); // 头信息
		Map<Long, String> gameMap = gameInfo.getGameMap();
		Map<Long, String> roomMap = gameInfo.getRoomMap();
		Map<Long, String> gradeMap = gameInfo.getGradeMap();
		for (Map<String, Object> map : list) {
			map.put("gameName", gameMap.get(map.get("gameId")));
			map.put("roomName", roomMap.get(map.get("roomId")));
			map.put("gradeName", gradeMap.get(map.get("gradeId")));
			writer.write(getGemeRecordRowStr(map)); // 行信息
		}
		writer.close();
		ExportCSVUtils.exportCSVData(response, fileName, path);
	}
	// 获取头信息
	private String[] getGemeRecordHeadRowStr() {
		String[] rowData = new String[12];
		rowData[0] = "会员Id";
		rowData[1] = "会员账号";
		rowData[2] = "游戏时间";
		rowData[3] = "游戏名称";
		rowData[4] = "场次名称";
		rowData[5] = "房间名称";
		rowData[6] = "游戏局号";
		rowData[7] = "游戏局数";
		rowData[8] = "有效投注金币";
		rowData[9] = "中奖金币";
		rowData[10] = "是否小游戏";
		rowData[11] = "抽水金币";
		return rowData;
	}
	// 获取行信息
	private String[] getGemeRecordRowStr(Map<String, Object> map) {
		String[] rowData = new String[12];
		rowData[0] = ExportCSVUtils.getString(map.get("userId"));
		rowData[1] = ExportCSVUtils.getString(map.get("userAccount"));
		rowData[2] = ExportCSVUtils.getString(map.get("createTime"));
		rowData[3] = ExportCSVUtils.getString(map.get("gameName"));
		rowData[4] = ExportCSVUtils.getString(map.get("gradeName"));
		rowData[5] = ExportCSVUtils.getString(map.get("roomName"));
		rowData[6] = ExportCSVUtils.getString(map.get("gameRoundNo"));
		rowData[7] = ExportCSVUtils.getString(map.get("round"));
		if(map.get("validBet")!=null) {
			rowData[8] = ExportCSVUtils.getString(new BigDecimal(map.get("validBet").toString()).divide(new BigDecimal(100)));
			rowData[9] = ExportCSVUtils.getString(new BigDecimal(map.get("prizeCoins").toString()).divide(new BigDecimal(100)));
			rowData[10] = getGemeRecordMini(map);
			rowData[11] = ExportCSVUtils.getString(new BigDecimal(map.get("waterProfit").toString()).divide(new BigDecimal(100)));
		}
		return rowData;
	}
	//是否小游戏
	private String getGemeRecordMini(Map<String, Object> data) {
		if ("true".equals(data.get("mini").toString())) {
			return "是";
		}
		return "否";
	}
}
