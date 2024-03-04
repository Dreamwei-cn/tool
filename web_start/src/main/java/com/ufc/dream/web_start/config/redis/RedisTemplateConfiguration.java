package com.ufc.dream.web_start.config.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.ufc.dream.web_start.config.redisession.LockerUtil;
import com.ufc.dream.web_start.config.redisession.RedissonLocker;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;

/**
 * @description: redis配置类
 * @author: mengw
 **/
@EnableCaching
@Configuration
@AutoConfigureBefore({RedisAutoConfiguration.class})
public class RedisTemplateConfiguration {


    /**
     *  @description: 获取redis 相关的配置信息
     */
    @Autowired
    private RedisProperties redisProperties;

    public RedisTemplateConfiguration() {
    }

    @Bean
    @ConditionalOnMissingBean({RedisSerializer.class})
    public StringRedisSerializer redisSerializer() {
        return new StringRedisSerializer();
    }

    @Bean(name = {"redisTemplate"})
    @ConditionalOnMissingBean({RedisTemplate.class})
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);

        FastJson2JsonRedisSerializer serializer = new FastJson2JsonRedisSerializer(Object.class);

        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        serializer.setObjectMapper(mapper);
        template.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        template.setValueSerializer(serializer);
        // 使用StringRedisSerializer来序列化和反序列化redis的key值
        template.setKeySerializer(new StringRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }


    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory) {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1L));
        return RedisCacheManager.builder(RedisCacheWriter.nonLockingRedisCacheWriter(factory)).cacheDefaults(redisCacheConfiguration).build();
    }

    /**
     * 注册 RedisConnectionFactory
     * @param jedisPoolConfig
     * @param redisStandaloneConfiguration
     * @return
     */
    @Bean
    public RedisConnectionFactory factory(JedisPoolConfig jedisPoolConfig, RedisStandaloneConfiguration  redisStandaloneConfiguration) {
        JedisConnectionFactory connectionFactory = new JedisConnectionFactory(redisStandaloneConfiguration);
        connectionFactory.setPoolConfig(jedisPoolConfig);
        return connectionFactory;

    }
    @Bean
    public RedisStandaloneConfiguration redisStandaloneConfiguration() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(redisProperties.getHost());
        config.setPort(redisProperties.getPort());
        config.setDatabase(redisProperties.getDatabase());
        config.setPassword(RedisPassword.of(redisProperties.getPassword()));
        return config;
    }



    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        RedisProperties.Pool pool = redisProperties.getJedis().getPool();
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(pool.getMaxActive());
        jedisPoolConfig.setMaxIdle(pool.getMaxIdle());
        jedisPoolConfig.setMinIdle(pool.getMinIdle());
        jedisPoolConfig.setMaxWait(pool.getMaxWait());
        return jedisPoolConfig;
    }

    @Bean
    public JedisPool jedisPool(JedisPoolConfig jedisPoolConfig) {
        return new JedisPool(jedisPoolConfig, redisProperties.getHost(),
                redisProperties.getPort(), 0, redisProperties.getPassword(), redisProperties.getDatabase());
    }


    /**
     *  注册RedisUtil
     * @param redisTemplate
     * @return
     */
    @Bean(name = {"redisUtil"})
    @ConditionalOnBean({RedisTemplate.class})
    public RedisUtil redisUtils(RedisTemplate<String, Object> redisTemplate) {
        return new RedisUtil(redisTemplate);
    }


    @Bean
    public RedissonLocker redissonLocker(RedissonClient redissonClient) {
        RedissonLocker redissonLocker = new RedissonLocker(redissonClient);
        // LockerUtils locker 实例化
        LockerUtil.setLocker(redissonLocker);
        return redissonLocker;
    }

    /**
     * @description  注册 RedissonClient  RedissonLocker类注册时 LockerUtils locker 实例化
     * @return
     */
    @Bean
    public RedissonClient redissonClient() {
        Config cfg = new Config();
        String redisUrl = String.format("redis://%s:%s", redisProperties.getHost() + "", redisProperties.getPort() + "");
        cfg.useSingleServer().setAddress(redisUrl);
//                .setPassword(redisProperties.getPassword());
        cfg.useSingleServer().setDatabase(redisProperties.getDatabase());
        cfg.useSingleServer().setConnectTimeout(3000)
                .setTimeout(3000)
                .setRetryAttempts(3)
                .setRetryInterval(1500)
                .setPingConnectionInterval(3000)//**此项务必设置为redisson解决之前bug的timeout问题关键*****
                ;
        return Redisson.create(cfg);
    }

}
