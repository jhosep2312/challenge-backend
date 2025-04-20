package com.tempo.challenge_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;

@Configuration
public class WebConfig {

    @Bean
    public WebFilter exchangeContextFilter() {
        return (exchange, chain) ->
                chain.filter(exchange).contextWrite(ctx -> ctx.put(ServerWebExchange.class, exchange));
    }
}
