package com.tempo.challenge_backend.config;

import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig {

    @Bean
    public Cache<String,String> cacheCaffeine() {
        return Caffeine.newBuilder()
                .expireAfterWrite(Duration.ofSeconds(30))
                .build();
    }


}