package com.kcc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CacheService {

	@Autowired
	private CacheLoaderService cacheLoaderService;

	public Integer getValue(String key) {
		return cacheLoaderService.getDemoCache().getMap().getOrDefault(key, 0);
	}
}
