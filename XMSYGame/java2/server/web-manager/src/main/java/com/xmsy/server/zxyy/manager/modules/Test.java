package com.xmsy.server.zxyy.manager.modules;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xmsy.network.redis.utils.RedisLockUtil;
import com.xmsy.server.zxyy.manager.common.annotation.Limit;
import com.xmsy.server.zxyy.manager.common.utils.R;
import com.xmsy.server.zxyy.manager.modules.app.event.PushService;
import com.xmsy.server.zxyy.manager.modules.app.event.userinfo.UserInfoMessage;
import com.xmsy.server.zxyy.manager.modules.manager.cashbank.entity.CashBankEntity;
import com.xmsy.server.zxyy.manager.modules.manager.cashbank.service.CashBankService;
import com.xmsy.server.zxyy.manager.modules.manager.orderpreferential.entity.OrderPreferentialEntity;
import com.xmsy.server.zxyy.manager.modules.manager.orderpreferential.service.OrderPreferentialService;
import com.xmsy.server.zxyy.manager.modules.manager.user.dao.UserDao;
import com.xmsy.server.zxyy.manager.modules.manager.user.entity.UserEntity;
import com.xmsy.server.zxyy.manager.modules.manager.useragenthierarchy.service.UserAgentHierarchyService;
import com.xmsy.server.zxyy.manager.modules.manager.userhierarchy.service.UserHierarchyService;

@RestController
@RequestMapping("webhome/public/test")
public class Test {

	@Autowired
	private OrderPreferentialService orderPreferentialService;

	@Autowired
	private CashBankService cashBankService;

	@Autowired
	private UserHierarchyService userHierarchyService;

	@Autowired
	private CacheManager cacheCacheManager;

	@Autowired
	private UserAgentHierarchyService userAgentHierarchyService;

	@Autowired
	private RedisLockUtil redisLockUtil;

	@Autowired
	private UserDao userDao;

	@Autowired
	private PushService pushService;

	/**
	 * web端充值 获取二维码 返回一个带有二维码的实体
	 * 
	 * @throws Exception
	 */
	@GetMapping("/test")
	public R test() throws Exception {
		List<OrderPreferentialEntity> preferentials = orderPreferentialService.getPreferentialsByHierarchyId(29L);
		CashBankEntity cashBankEntity = cashBankService.selectById(45L);
		System.out.println("preferentials:" + preferentials);
		System.out.println("cashBankEntity:" + cashBankEntity);
		return R.ok().put("preferentials", preferentials);
	}

	/**
	 * web端充值 获取二维码 返回一个带有二维码的实体
	 * 
	 * @throws Exception
	 */
	@PutMapping("/test/user/{id}")
	public R testUserUpdate(@PathVariable Long id, @RequestParam("coin") Long coin) throws Exception {
		UserEntity userEntity = new UserEntity();
		userEntity.setId(id);
		userEntity.setCoin(coin);
		Integer result = userDao.updateUserWalletByUserId(userEntity);
		System.out.println("userDao.updateUserWalletByUserId: result :" + result);
		return R.ok().put("result", result);
	}

	/**
	 * 获取缓存列表
	 * 
	 * @throws Exception
	 */
	@GetMapping("/caches")
	public R cacheCacheManager() throws Exception {
		return R.ok().put("caches", cacheCacheManager.getCacheNames());
	}

	/**
	 * 获取缓存列表
	 * 
	 * @throws Exception
	 */
	@GetMapping("/cache")
	public R cacheCacheManager(@RequestParam("name") String name, @RequestParam("key") String key) throws Exception {
		// EhCacheCache cache= (EhCacheCache) cacheCacheManager.getCache(name);
		// CacheManager manager = cacheCacheManager.GET
		// Cache cache = manager.getCache(name);
		// for (Object obj : cache.getKeys()) {
		// System.out.println(obj.toString());
		// }
		return R.ok().put("value", cacheCacheManager.getCache(name).get(key).get());
	}

	/**
	 * 用户充值推送测试
	 * 
	 * @throws Exception
	 */
	@PostMapping("/pushExchange")
	public R userExchange(UserInfoMessage userInfoMessage) throws Exception {
		pushService.pushExchange(userInfoMessage);
		return R.ok();
	}

	/**
	 * 用户充值推送测试
	 * 
	 * @throws Exception
	 */
	@PostMapping("/pushInfo")
	public R userInfoPush(UserInfoMessage userInfoMessage) throws Exception {
		pushService.pushUserInfo(userInfoMessage);
		return R.ok();
	}

	/**
	 * 获取用户默认层级
	 * 
	 * @throws Exception
	 */
	@GetMapping("/userHierarchy")
	public R userHierarchy() throws Exception {
		return R.ok().put("value", userHierarchyService.getDefaultHierarchy());
	}

	/**
	 * 获取代理层级
	 * 
	 * @throws Exception
	 */
	@GetMapping("/userAgentHierarchy")
	public R userAgentHierarchy() throws Exception {
		return R.ok().put("value", userAgentHierarchyService.getDefaultHierarchy());
	}

	/**
	 * 获取代理层级
	 * 
	 * @throws Exception
	 */
	@GetMapping("/userAgentHierarchy/{id}")
	public R userAgentHierarchyById(@PathVariable Long id) throws Exception {
		return R.ok().put("value", userAgentHierarchyService.selectById(id));
	}

	/**
	 * 获取代理层级
	 * 
	 * @throws Exception
	 */
	@GetMapping("/redis/test/{id}")
	public R redisLockTest(@PathVariable Long id, @RequestParam("name") String name) throws Exception {
		if (redisLockUtil.setLock(String.valueOf(id), name)) {
			System.out.println("获取到锁了");
			Thread.sleep(1000L);
			boolean result = redisLockUtil.releaseLock(String.valueOf(id), name);
			System.out.println(result);
			return R.ok("获取到锁了");
		} else {
			System.out.println("获待锁");
			return R.ok("获待锁");
		}
	}

	public static Domain ping(String domian) {
		long start = System.currentTimeMillis();
		Domain result = new Domain();
		try {
			InetAddress address = InetAddress.getByName(domian);
			result.ip = address.getHostAddress();
			result.host = address.getHostName();
			long end = System.currentTimeMillis();
			result.time = (end - start);
		} catch (UnknownHostException e) {
			result.ip = "0.0.0.0";
			result.host = "UNKONW";
		}
		return result;
	}

	public static class Domain {
		String ip;
		String host;
		long time;

		@Override
		public String toString() {
			return String.format("host=%s, ip=%s, time=%s", host, ip, time);
		}
	}

	private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger();

	@Limit(key = "test", period = 1000, count = 10, name = "resource", prefix = "limit")
	@GetMapping("/testLimt")
	public R testLimiter() {
		// 意味着100S内最多可以访问10次
		return R.ok(String.valueOf(ATOMIC_INTEGER.incrementAndGet()));
	}

	public static void main(String[] args) throws Exception {
		//
		//
		//// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//// Date d1=sdf.parse("2018-12-27 15:50:33");
		//// Date d3=new Date();
		////
		////// System.out.println("d1在当前时间之后？"+d1.after(new Date()));
		////// System.out.println("d1在当前时间之前？"+d1.before(new Date()));
		////// System.out.println("d1等于当前时间？"+d1.equals(new Date()));
		//// Double ass=0.1;
		//// Double b=0.06;
		//// BigDecimal a=new BigDecimal(ass.toString());
		// System.out.println(Constant.RechargeType.BANK.getValue());
		////// System.out.println(new BigDecimal(0.0332222).floatValue());
		//// IpUtil.getAddress();
		//// //System.out.println(IpUtil.getAddress());
		//
		//// String cardNum="a3456";
		//// String regex="^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,10}$";
		//// Matcher m=Pattern.compile(regex).matcher(cardNum);
		//// System.out.println(m.matches());
		// //System.out.println(Dictionary.BANK.getValue());
		// Long aa=123L;
		// Domain domain = ping("945459.com");
		// System.out.println(domain.time);
		int number = 8437768;
		// 00000000 10000000 11000000 00001011
		// 100000001100000000001011
		int bn = 0b000000001;
		int bn1 = 0b000000011;
		int bn2 = 0b00000000001000;
		String bnStr = toBin(number);
		String bn1Str = bnStr.substring(0, 9);
		String bn2Str = bnStr.substring(9, 18);
		String bn3Str = "00" + bnStr.substring(18, 32);
		// BigDecimal aa1 = new BigDecimal(1010).divide(new BigDecimal(100)).setScale(2,
		// BigDecimal.ROUND_DOWN);
		System.out.println(toBin(number));
		System.out.println(bn);
		System.out.println(bn1);
		System.out.println(bn2);
		// System.out.println("bnStr:"+(bnStr));
		// System.out.println("bn1Str:"+(bn1Str));
		// System.out.println("bn2Str:"+(bn2Str));
		// System.out.println("bn3Str:"+(bn3Str));
		System.out.println(Integer.parseInt((bn1Str), 2));
		System.out.println(Integer.parseInt((bn2Str), 2));
		System.out.println(Integer.parseInt((bn3Str), 2));
		System.out.println("=======================");
		System.out.println(toBin(1).substring(23));
		System.out.println(toBin(3).substring(23));
		System.out.println(toBin(8).substring(18));
		System.out
				.println(Integer.parseInt(toBin(1).substring(23) + toBin(3).substring(23) + toBin(8).substring(18), 2));

	}

	public static String toBin(int num) {
		char[] chs = new char[Integer.SIZE];
		for (int i = 0; i < Integer.SIZE; i++) {
			chs[Integer.SIZE - 1 - i] = (char) ((num >> i & 1) + '0');
		}
		return new String(chs);
	}
}
