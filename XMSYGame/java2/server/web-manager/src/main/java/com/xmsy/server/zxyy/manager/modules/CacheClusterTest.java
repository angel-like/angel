package com.xmsy.server.zxyy.manager.modules;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xmsy.server.zxyy.manager.common.utils.R;

@RestController
@RequestMapping("webhome/public/testcache")
public class CacheClusterTest {

	public static final String KEY_CACHE = "testCacheCluster";

	/**
	 * 缓存集群 put|update
	 * 
	 * @throws Exception
	 */
	@GetMapping("/put")
	public R testCacheCluster(@RequestParam("val") String val) throws Exception {
		// cacheCacheManager.getCache(EhCacheName.USER_HIERARCHY_CACHE).put(KEY_CACHE,
		// val);
		return R.ok();
	}

	/**
	 * 缓存集群 remove
	 * 
	 * @throws Exception
	 */
	@GetMapping("/remove")
	public R remove() throws Exception {
		// cacheCacheManager.getCache(EhCacheName.USER_HIERARCHY_CACHE).evict(KEY_CACHE);
		return R.ok();
	}

	/**
	 * 缓存集群 get
	 * 
	 * @throws Exception
	 */
	@GetMapping("/get")
	public R get() throws Exception {
		Object value = "";
		// if(cacheCacheManager.getCache(EhCacheName.USER_HIERARCHY_CACHE).get(KEY_CACHE)!=null)
		// {
		// value=cacheCacheManager.getCache(EhCacheName.USER_HIERARCHY_CACHE).get(KEY_CACHE).get();
		// }
		return R.ok().put("value", value);
	}

}
