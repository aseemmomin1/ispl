package com.infyvaritaas.ispl.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.PreDestroy;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DataSourceConfig {

    private static final Logger log = LoggerFactory.getLogger(DataSourceConfig.class);
    private HikariDataSource created;

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username:}")
    private String dbUsername;

    @Value("${spring.datasource.password:}")
    private String dbPassword;

    @Value("${spring.datasource.driver-class-name:}")
    private String dbDriver;

    @Bean
    @Primary
    public DataSource dataSource() {
        // If DB URL points to MySQL, try to connect; if it fails, fallback to H2 in-memory so the app can run for development.
        if (dbUrl != null && dbUrl.startsWith("jdbc:mysql:")) {
            try {
                HikariConfig cfg = new HikariConfig();
                cfg.setJdbcUrl(dbUrl);
                if (dbUsername != null && !dbUsername.isBlank()) cfg.setUsername(dbUsername);
                if (dbPassword != null && !dbPassword.isBlank()) cfg.setPassword(dbPassword);
                if (dbDriver != null && !dbDriver.isBlank()) cfg.setDriverClassName(dbDriver);
                cfg.setMaximumPoolSize(5);
                HikariDataSource ds = new HikariDataSource(cfg);
                // test connection
                try (Connection c = ds.getConnection()) {
                    log.info("Connected to MySQL datasource: {}", dbUrl);
                    created = ds;
                    return ds;
                }
            } catch (Exception ex) {
                log.warn("Failed to connect to MySQL at {}. Falling back to embedded H2. Cause: {}", dbUrl, ex.getMessage());
            }
        }

        // Fallback: H2 in-memory
        try {
            HikariConfig cfg = new HikariConfig();
            cfg.setJdbcUrl("jdbc:h2:mem:ispl;DB_CLOSE_DELAY=-1;MODE=MySQL");
            cfg.setUsername("sa");
            cfg.setPassword("");
            cfg.setDriverClassName("org.h2.Driver");
            cfg.setMaximumPoolSize(3);
            HikariDataSource ds = new HikariDataSource(cfg);
            created = ds;
            log.info("Using fallback H2 in-memory datasource (MODE=MySQL)");
            return ds;
        } catch (Exception e) {
            log.error("Unable to create fallback H2 datasource", e);
            throw new RuntimeException(e);
        }
    }

    @PreDestroy
    public void close() {
        if (created != null) {
            created.close();
        }
    }
}
