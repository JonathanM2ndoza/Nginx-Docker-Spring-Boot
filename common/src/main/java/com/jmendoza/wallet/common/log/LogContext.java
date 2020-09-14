package com.jmendoza.wallet.common.log;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LogContext {
    private String ipAddress;
    private String description;
    private String user;
    private String service;
    private String wildcard1;
    private String wildcard2;
    private String wildcard3;

    public LogContext(String ipAddress, String description, String user, String service) {
        this.ipAddress = ipAddress;
        this.description = description;
        this.user = user;
        this.service = service;
    }
}
