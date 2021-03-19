package com.kcc.cache;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.cache.CacheLoader;
import com.google.common.cache.CacheStats;
import com.google.common.cache.LoadingCache;
import com.kcc.factory.LoadingCacheFactory;

public abstract class BasicCacheLoader<K, V> extends CacheLoader<K, V> {

	private static final Logger LOGGER = LoggerFactory.getLogger(BasicCacheLoader.class);

	protected String name;
	protected LoadingCache<K, V> loadingCache;

	protected abstract void putAllData();

	public BasicCacheLoader(String name, long expired, long refresh, TimeUnit unit, long size) {
		this.name = name;
		this.loadingCache = LoadingCacheFactory.build(expired, refresh, unit, size, this);
		putAllData();
	}

	public V get(K key) throws ExecutionException {
		return loadingCache.get(key);
	}

	public LoadingCache<K, V> getCache() {
		return loadingCache;
	}

	public ConcurrentMap<K, V> getMap() {
		return loadingCache.asMap();
	}

	@Override
	public synchronized V load(K key) throws Exception {
		// 辨別是真的沒資料還是過期, size可能會不准
		if (loadingCache.getIfPresent(key) == null) {
			// Log先放這，cache命中率
			CacheStats stas = loadingCache.stats();
			LOGGER.info("LoadingCache info, name={}, hitcount={}", name, stas.hitCount());
			putAllData();
		}
		return loadingCache.getIfPresent(key);
	}

}
