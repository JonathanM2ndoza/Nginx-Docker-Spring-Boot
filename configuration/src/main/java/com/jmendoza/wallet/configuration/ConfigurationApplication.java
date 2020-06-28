package com.jmendoza.wallet.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.jmendoza.wallet.*"})
@EnableJpaRepositories("com.jmendoza.wallet.infrastructure.databases.*")
@EntityScan("com.jmendoza.wallet.infrastructure.databases.*")
@EnableJpaAuditing
public class ConfigurationApplication extends SpringBootServletInitializer {

    @Override
    public SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ConfigurationApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(ConfigurationApplication.class, args);
    }

}
