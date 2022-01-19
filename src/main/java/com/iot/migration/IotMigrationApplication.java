package com.iot.migration;

import com.iot.migration.service.MigrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class IotMigrationApplication {

    @Autowired
    MigrationService service;

    public static void main(String[] args) {
        SpringApplication.run(IotMigrationApplication.class, args);
    }

    @PostConstruct
    public void init() {
        service.migrate();
    }

}
