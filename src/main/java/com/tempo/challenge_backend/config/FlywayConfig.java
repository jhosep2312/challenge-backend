package com.tempo.challenge_backend.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlywayConfig {

    @Value("${spring.flyway.url}")
    private String url;

    @Value("${spring.flyway.user}")
    private String user;

    @Value("${spring.flyway.password}")
    private String password;

    @Bean(initMethod = "migrate")
    public Flyway flyway() {
        Flyway fly = new Flyway(Flyway.configure()
                .baselineVersion("1.0")
                .baselineOnMigrate(true)
                .table("flyway_backend_schema_history")
                .dataSource(url, user, password));
        return fly;
    }
}
