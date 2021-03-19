package com.kcc.factory;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;

public final class LoadingCacheFactory {

	private LoadingCacheFactory() {
	}

	public static <K, V> LoadingCache<K, V> build(long expired, long refresh, TimeUnit unit, long size,
			CacheLoader<K, V> loader) {
		return build(expired, refresh, unit, size, null, loader);
	}

	private static <K, V> LoadingCache<K, V> build(long expired, long refresh, TimeUnit unit, long size,
			RemovalListener<K, V> listener, CacheLoader<K, V> loader) {
		CacheBuilder<Object, Object> builder = CacheBuilder.newBuilder().maximumSize(size).recordStats();

		if (expired > 0) {
			builder.expireAfterWrite(expired, unit);
		}

		if (refresh > 0) {
			builder.refreshAfterWrite(refresh, unit);
		}

		if (listener != null) {
			builder.removalListener(listener);
		}

		return builder.build(loader);
	}

}
