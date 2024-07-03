package com.jobportal.util;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Service;

import com.jobportal.configuration.CacheConfig;

@Service
public class CacheOperations {

	@Autowired
	private CacheConfig cacheConfig;

	public void expireKey(String key) {
		cacheConfig.redisTemplate().expire(key, 0, null);
	}

	public void putValueInHashWithExpiration(String key1, Object key2, Object value, long timeout, TimeUnit timeUnit) {
		HashOperations<String, Object, Object> opsForHash = cacheConfig.redisTemplate().opsForHash();
		opsForHash.put(key1, key2, value);
		cacheConfig.redisTemplate().expire(key1, timeout, timeUnit);
	}

	public Boolean isKeyExist(String key1, Object key2) {

		return cacheConfig.redisTemplate().opsForHash().hasKey(key1, key2);
	}

	public void addInCache(String key1, Object key2, Object val) {
		cacheConfig.redisTemplate().opsForHash().put(key1, key2, val);
	}

	public boolean addInCacheIfAbsent(String key1, Object key2, Object val) {
		return cacheConfig.redisTemplate().opsForHash().putIfAbsent(key1, key2, val);
	}

	public Object getFromCache(String key1, Object key2) {
		return cacheConfig.redisTemplate().opsForHash().get(key1, key2);
	}

	public void removeKeyFromCache(String key) {
		cacheConfig.redisTemplate().delete(key);

	}

	public void getAllData() {
		// Get all keys in Redis
		Set<String> keys = cacheConfig.redisTemplate().keys("*");

		// Get values for all keys
		List<Object> values = cacheConfig.redisTemplate().opsForValue().multiGet(keys);

		// Print key-value pairs
		for (int i = 0; i < keys.size(); i++) {
			System.err.println("key=" + keys.toArray()[i] + ", value=" + values.get(i));
		}
	}

	public void deleteAllData() {
		// Get all keys in Redis
		Set<String> keys = cacheConfig.redisTemplate().keys("*");

		// Delete all keys
		cacheConfig.redisTemplate().delete(keys);
	}

}
