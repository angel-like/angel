package com.xmsy.server.zxyy.robot.cache;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Component;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

@Component
public class LocalContentCache {

	@Autowired
	private EhCacheCacheManager cacheCacheManager;

	private Cache cache;

	@PostConstruct
	public void init() {
		CacheManager manager = (net.sf.ehcache.CacheManager) cacheCacheManager.getCacheManager();
		cache = manager.getCache(EhCacheName.TOKEN_CACHE);
	}

	public void put(String key, Object value) {
		Element element = new Element(key, value);
		cache.put(element);
	}

	public Object get(String key) {
		Element element = cache.get(key);
		return element == null ? null : element.getObjectValue();
	}

	public void remove(String key) {
		cache.remove(key);
	}
}
