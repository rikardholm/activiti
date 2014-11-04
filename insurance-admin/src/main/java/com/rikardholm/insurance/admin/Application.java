package com.rikardholm.insurance.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import rikardholm.insurance.infrastructure.fake.FakeSparService;

import javax.annotation.PostConstruct;

@Configuration
@ImportResource("classpath:META-INF/insurance/spring/admin/insurance-admin-context.xml")
@ComponentScan
@EnableAutoConfiguration
public class Application {

    @Autowired
    private FakeSparService fakeSparService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @PostConstruct
    public void makeSparUnavailable() {
        fakeSparService.makeUnavailable();
    }
}
