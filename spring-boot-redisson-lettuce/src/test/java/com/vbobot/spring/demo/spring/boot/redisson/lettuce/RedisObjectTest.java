package com.vbobot.spring.demo.spring.boot.redisson.lettuce;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.SocketOptions;
import java.time.Duration;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import jodd.util.concurrent.ThreadFactoryBuilder;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RFuture;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author Bobo
 * @date 2021/7/26
 */
public class RedisObjectTest {

    public static final String REDIS_HOST = "redis.test.com";
    private static final int REDIS_PORT = 6379;
    private static final String PASSWORD = "aliredis";

    @BeforeAll
    static void beforeAll() {
        System.setProperty("logging.level.org", "INFO");
        System.setProperty("logging.level.io", "INFO");
    }

    @RepeatedTest(value = 10)
    @DisplayName("Redisson")
    public void testRedisson() throws Exception {
        final Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://" + REDIS_HOST + ":" + REDIS_PORT)
                .setConnectTimeout(10000)
                .setDatabase(0)
                .setPassword(PASSWORD);
        final RedissonClient redissonClient = Redisson.create(config);
        final String name = "performance-redisson-obj";
        final RBucket<CacheEntity> bucket = redissonClient.getBucket(name,
                JsonJacksonCodec.INSTANCE);
        runCost((index) -> {
            final RFuture<CacheEntity> async = bucket.getAsync();
            try {
                async.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            bucket.set(CacheEntity.randomEntity());
        });
    }

    @RepeatedTest(value = 10)
    @DisplayName("RedisTemplateWithRedisson")
    public void testRedisTemplateWithRedisson() throws Exception {
        final Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://" + REDIS_HOST + ":" + REDIS_PORT)
                .setConnectTimeout(10000)
                .setDatabase(0)
                .setPassword(PASSWORD);
        final RedissonClient redissonClient = Redisson.create(config);

        final RedissonConnectionFactory redissonConnectionFactory = new RedissonConnectionFactory(
                redissonClient);
        redissonConnectionFactory.afterPropertiesSet();
        final RedisTemplate<String, CacheEntity> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redissonConnectionFactory);
        redisTemplate.setKeySerializer(StringRedisSerializer.UTF_8);
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.afterPropertiesSet();
        final String key = "performance-redisson-obj";

        runCost(index -> {
            final ValueOperations<String, CacheEntity> stringObjectValueOperations = redisTemplate
                    .opsForValue();
            stringObjectValueOperations.get(key);
            stringObjectValueOperations.set(key, CacheEntity.randomEntity());
        });
    }

    @RepeatedTest(10)
    @DisplayName("RedisTemplateWithLettuce")
    public void testRedisTemplateWithLettuce() throws Exception {
        GenericObjectPoolConfig<?> genericObjectPoolConfig = new GenericObjectPoolConfig<>();
        genericObjectPoolConfig.setTimeBetweenEvictionRunsMillis(5000);
        LettuceClientConfiguration clientConfig = LettucePoolingClientConfiguration.builder()
                .commandTimeout(Duration.ofMillis(2000))
                .poolConfig(genericObjectPoolConfig)
                .clientOptions(ClientOptions
                        .builder().socketOptions(SocketOptions.builder().keepAlive(true).build())
                        .build())
                .build();

        final RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(REDIS_HOST);
        redisStandaloneConfiguration.setPort(REDIS_PORT);
        redisStandaloneConfiguration.setPassword(PASSWORD);

        final LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(
                redisStandaloneConfiguration, clientConfig);
        lettuceConnectionFactory.setShareNativeConnection(true);
        lettuceConnectionFactory.afterPropertiesSet();

        final RedisTemplate<String, CacheEntity> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        redisTemplate.setKeySerializer(StringRedisSerializer.UTF_8);
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.afterPropertiesSet();

        final ValueOperations<String, CacheEntity> ops = redisTemplate.opsForValue();
        final String key = "performance-redistemplate-lettuce-obj";
        runCost((integer) -> {
            ops.get(key);
            ops.set(key, CacheEntity.randomEntity());
        });
    }

    @RepeatedTest(10)
    @DisplayName("RedisTemplateWithLettuceShareNativeFalse")
    public void testRedisTemplateWithLettuceShareNativeFalse() throws Exception {
        GenericObjectPoolConfig<?> genericObjectPoolConfig = new GenericObjectPoolConfig<>();
        genericObjectPoolConfig.setTimeBetweenEvictionRunsMillis(5000);
        LettuceClientConfiguration clientConfig = LettucePoolingClientConfiguration.builder()
                .commandTimeout(Duration.ofMillis(2000))
                .poolConfig(genericObjectPoolConfig)
                .clientOptions(ClientOptions
                        .builder().socketOptions(SocketOptions.builder().keepAlive(true).build())
                        .build())
                .build();

        final RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(REDIS_HOST);
        redisStandaloneConfiguration.setPort(REDIS_PORT);
        redisStandaloneConfiguration.setPassword(PASSWORD);

        final LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(
                redisStandaloneConfiguration, clientConfig);
        lettuceConnectionFactory.setShareNativeConnection(false);
        lettuceConnectionFactory.setValidateConnection(true);
        lettuceConnectionFactory.afterPropertiesSet();

        final RedisTemplate<String, CacheEntity> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        redisTemplate.setKeySerializer(StringRedisSerializer.UTF_8);
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.afterPropertiesSet();

        final ValueOperations<String, CacheEntity> ops = redisTemplate.opsForValue();
        final String key = "RedisTemplateWithLettuceShareNativeFalse";
        runCost((integer) -> {
            ops.get(key);
            ops.set(key, CacheEntity.randomEntity());
        });
    }

    private void runCost(Consumer<Integer> eachExec) throws InterruptedException {
        final long begin = System.currentTimeMillis();

        final ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setNameFormat("redisson-task-%d")
                .setDaemon(true)
                .get();
        final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(200, 600, 0,
                TimeUnit.SECONDS,
                new SynchronousQueue<>(),
                threadFactory);

        for (int i = 0; i < 10; i++) {
            threadPoolExecutor.execute(() -> {
                for (int j = 0; j < 500; j++) {
                    eachExec.accept(j);
                }
            });
        }

        threadPoolExecutor.shutdown();
        if (threadPoolExecutor.awaitTermination(1, TimeUnit.HOURS)) {
            System.out.println("Cost:" + (System.currentTimeMillis() - begin));
        }
    }

}
