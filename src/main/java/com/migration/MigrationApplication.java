package com.migration;

import com.migration.service.MigrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class MigrationApplication implements ApplicationRunner {

    @Autowired
    private MigrationService service;

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(MigrationApplication.class, args);
        exitApplication(ctx);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        service.migrate();
    }

    private static void exitApplication(ConfigurableApplicationContext ctx) {
        int exitCode = SpringApplication.exit(ctx, () -> 0);
        System.out.println("Exit Spring Boot");
        System.exit(exitCode);
    }
}
