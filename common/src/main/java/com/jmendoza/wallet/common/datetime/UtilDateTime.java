package com.jmendoza.wallet.common.datetime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.ZoneId;

@Component
public class UtilDateTime {

    @Autowired
    private Environment env;

    public OffsetDateTime getDateTimeByZoneId() {
        return OffsetDateTime.now(ZoneId.of(env.getRequiredProperty("db.postgres.zone.id")));
    }
}
