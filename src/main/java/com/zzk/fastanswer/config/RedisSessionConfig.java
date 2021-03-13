package com.zzk.fastanswer.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * Redis 会话管理
 *
 * @author zzk
 * @create 2021-03-13 21:53
 */
@Configuration
@EnableRedisHttpSession(redisNamespace = "redis:session")
public class RedisSessionConfig {
}
