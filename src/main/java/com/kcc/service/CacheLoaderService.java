package com.kcc.service;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.kcc.cache.BasicCacheLoader;

@Service
public class CacheLoaderService {

	private BasicCacheLoader<String, Integer> demoCache;

	@PostConstruct
	protected void initCachceLoader() {
		setDemoCache(1, TimeUnit.DAYS);
	}

	public BasicCacheLoader<String, Integer> getDemoCache() {
		return demoCache;
	}

	private void setDemoCache(long expired, TimeUnit unit) {
		this.demoCache = new BasicCacheLoader<String, Integer>("DemoCache", expired, 0, unit, 100) {
			@Override
			protected void putAllData() {
				// TODO 從DB拿資料塞到loadingCache(guava)
				loadingCache.put("first", 1);
				System.out.println(1);
				loadingCache.put("second", 2);
				System.out.println(2);
				loadingCache.put("third", 3);
				System.out.println(3);
				loadingCache.put("forth", 4);
				System.out.println(4);
				loadingCache.put("fifth", 5);
				System.out.println(5);
			}
		};
	}
}
