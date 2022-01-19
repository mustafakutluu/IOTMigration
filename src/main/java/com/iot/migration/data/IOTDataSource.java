package com.iot.migration.data;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IOTDataSource {

    @Bean
    @ConfigurationProperties("spring.datasource.iot")
    public DataSourceProperties iotDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.iot.configuration")
    public HikariDataSource iotDataSource(DataSourceProperties iotDataSourceProperties) {
        return iotDataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.i2i")
    public DataSourceProperties i2iDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.i2i.configuration")
    public HikariDataSource i2iDataSource(DataSourceProperties i2iDataSourceProperties) {
        return i2iDataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }
}
