package com.kcc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kcc.service.CacheService;

@RestController
public class DemoController {

	@Autowired
	private CacheService cacheService;

	@GetMapping(value = "/api/getValue")
	public ResponseEntity<Integer> getValue(@RequestParam(required = true) String key) {
		Integer value = cacheService.getValue(key);
		return new ResponseEntity<>(value, HttpStatus.OK);
	}
}
