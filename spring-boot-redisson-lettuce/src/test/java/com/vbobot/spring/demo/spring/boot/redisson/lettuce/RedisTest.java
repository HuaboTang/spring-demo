package com.vbobot.spring.demo.spring.boot.redisson.lettuce;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.SocketOptions;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import jodd.util.concurrent.ThreadFactoryBuilder;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.redisson.Redisson;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author Bobo
 * @date 2021/7/26
 */
public class RedisTest {

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
        final RAtomicLong atomicLong = redissonClient.getAtomicLong("performance-redisson");

        runCost(() -> {
            final long val = atomicLong.incrementAndGet();
            if (val % 100 == 0) {
                System.out.println(val);
            }
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
        final RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redissonConnectionFactory);
        redisTemplate.afterPropertiesSet();

        runCost(() -> {
            final long aLong = Optional.ofNullable(redisTemplate.opsForValue().increment("performance-redisson"))
                    .orElse(0L);
            if (aLong % 50 == 0) {
                System.out.println(aLong);
            }
        });
    }

    @RepeatedTest(10)
    @DisplayName(("Lettuce"))
    public void testLettuce() throws Exception {
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

        final RedisStringCommands redisStringCommands = lettuceConnectionFactory
                .getConnection().stringCommands();

        runCost(() -> {
            final Long incr = redisStringCommands.incr(
                    "performance-lettuce".getBytes(StandardCharsets.UTF_8));
            if (Optional.ofNullable(incr).orElse(0L) % 100 == 0) {
                System.out.println(incr);
            }
        });
    }

    @RepeatedTest(10)
    @DisplayName(("LettuceShareNativeConnectionIsFalse"))
    public void testLettuceShareNativeConnectionIsFalse() throws Exception {
        GenericObjectPoolConfig<?> genericObjectPoolConfig = new GenericObjectPoolConfig<>();
        genericObjectPoolConfig.setMaxWaitMillis(1000);
        final ClientOptions clientOptions = ClientOptions.builder()
                .socketOptions(SocketOptions.builder().connectTimeout(Duration.ofMillis(2000))
                        .keepAlive(true).build())
                .build();
        LettuceClientConfiguration clientConfig = LettucePoolingClientConfiguration.builder()
                .commandTimeout(Duration.ofMillis(1000))
                .poolConfig(genericObjectPoolConfig)
                .clientOptions(clientOptions)
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

        final RedisStringCommands redisStringCommands = lettuceConnectionFactory
                .getConnection().stringCommands();

        runCost(() -> {
            final Long incr = redisStringCommands.incr(
                    "performance-lettuce".getBytes(StandardCharsets.UTF_8));
            if (Optional.ofNullable(incr).orElse(0L) % 100 == 0) {
                System.out.println(incr);
            }
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

        final RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        redisTemplate.afterPropertiesSet();

        runCost(() -> redisTemplate.opsForValue()
                .increment("performance-redistemplate-lettuce"));
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

        final RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        redisTemplate.afterPropertiesSet();

        runCost(() -> redisTemplate.opsForValue()
                .increment("performance-redistemplate-lettuce"));
    }

    private void runCost(Runnable eachExec) throws InterruptedException {
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
                    eachExec.run();
                }
            });
        }

        threadPoolExecutor.shutdown();
        if (threadPoolExecutor.awaitTermination(1, TimeUnit.HOURS)) {
            System.out.println("Cost:" + (System.currentTimeMillis() - begin));
        }
    }
}
