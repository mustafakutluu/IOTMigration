package com.migration.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncConfiguration.class);

    @Value("${spring.migrate.poolSize}")
    private int poolSize;

    @Value("${spring.migrate.queueCapacity}")
    private int queueCapacity;

    @Value("${spring.migrate.threadNamePrefix}")
    private String threadNamePrefix;

    private ThreadPoolTaskExecutor executor;


    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        LOGGER.debug("Creating Async Task Executor");
        executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(poolSize);
        executor.setMaxPoolSize(poolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix(threadNamePrefix);
        executor.initialize();
        return executor;
    }

    public Integer getActiveThreadCount() {
        return executor.getActiveCount();
    }

}
