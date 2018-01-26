package com.example.demo.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;  
import com.fasterxml.jackson.annotation.PropertyAccessor;  
import com.fasterxml.jackson.databind.ObjectMapper;  
import org.apache.log4j.Logger;  
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.boot.context.properties.ConfigurationProperties;  
import org.springframework.cache.CacheManager;  
import org.springframework.cache.annotation.CachingConfigurerSupport;  
import org.springframework.cache.annotation.EnableCaching;  
import org.springframework.cache.interceptor.KeyGenerator;  
import org.springframework.context.annotation.Bean;  
import org.springframework.context.annotation.Configuration;  
import org.springframework.context.annotation.Primary;  
import org.springframework.data.redis.cache.RedisCacheManager;  
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;  
import org.springframework.data.redis.core.RedisTemplate;  
import org.springframework.data.redis.core.StringRedisTemplate;  
import org.springframework.data.redis.core.ValueOperations;  
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;  
import redis.clients.jedis.JedisPoolConfig;  
  
import java.lang.reflect.Method;  
  
/** 
 * Created by rick on 2017/12/16. 
 */  
@Configuration  
@EnableCaching  
public class RedisConfig extends CachingConfigurerSupport {  
    private static Logger logger = Logger.getLogger(RedisConfig.class);  
    @Bean  
    public KeyGenerator keyGenerator() {  
        return new KeyGenerator() {  
            @Override  
            public Object generate(Object target, Method method, Object... params) {  
                StringBuilder sb = new StringBuilder();  
                sb.append(target.getClass().getName());  
                sb.append(method.getName());  
                for (Object obj : params) {  
                    sb.append(obj.toString());  
                }  
                return sb.toString();  
            }  
        };  
    }  
    // @SuppressWarnings("rawtypes")  
    @Bean  
    public CacheManager cacheManager(RedisTemplate redisTemplate) {  
        RedisCacheManager rcm = new RedisCacheManager(redisTemplate);  
        //设置缓存过期时间  
        //rcm.setDefaultExpiration(60);//秒  
        return rcm;  
    }  
    @Autowired  
    RedisTemplate redisTemplate;  
    @Bean  
    public ValueOperations<String,Object> vos(){  
        return redisTemplate.opsForValue();  
    }  

}