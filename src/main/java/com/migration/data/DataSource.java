package com.migration.data;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSource {

    @Bean
    @ConfigurationProperties("spring.datasource.first")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.first.configuration")
    public HikariDataSource dataSource(DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    /*@Bean
    @ConfigurationProperties("spring.datasource.second")
    public DataSourceProperties secondDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.second.configuration")
    public HikariDataSource secondDataSource(DataSourceProperties secondDataSourceProperties) {
        return secondDataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }*/
}
