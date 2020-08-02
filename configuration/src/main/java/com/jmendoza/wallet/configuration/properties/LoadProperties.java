package com.jmendoza.wallet.configuration.properties;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
@PropertySource("classpath:customer_message.properties")
public class LoadProperties {
}
