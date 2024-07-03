package com.jobportal.configuration;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching
public class CacheConfig {

	@Value("${spring.data.redis.host}")
	private String redisHost;

	@Value("${spring.data.redis.port}")
	private int redisPort;

	@Bean
	public LettuceConnectionFactory redisConnectionFactory() {

		return new LettuceConnectionFactory(redisHost, redisPort);
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate() {

		RedisTemplate<String, Object> template = new RedisTemplate<>();
		RedisSerializer<String> stringSerializer = new StringRedisSerializer();
		JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
		LettuceConnectionFactory lcf = this.redisConnectionFactory();
		lcf.afterPropertiesSet();
		template.setConnectionFactory(lcf);
		template.setKeySerializer(stringSerializer);
		template.setHashKeySerializer(stringSerializer);
		template.setValueSerializer(jdkSerializationRedisSerializer);
		template.setHashValueSerializer(jdkSerializationRedisSerializer);
		template.setEnableTransactionSupport(true);
		template.afterPropertiesSet();

		return template;
	}

	@Bean
	public RedisCacheConfiguration cacheConfiguration() {
		RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig()
				.entryTtl(Duration.ofSeconds(15)).disableCachingNullValues();
		return cacheConfig;
	}

	@Bean
	public RedisCacheManager cacheManager() {
		RedisCacheManager rcm = RedisCacheManager.builder(redisConnectionFactory()).cacheDefaults(cacheConfiguration())
				.transactionAware().build();
		return rcm;
	}
}
