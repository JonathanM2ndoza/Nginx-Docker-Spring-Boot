package com.jmendoza.wallet.common.datetime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.TimeZone;

@Component
public class UtilDateTime {

    @Autowired
    private Environment env;

    private DateTimeFormatter formatterTimestamp = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Timestamp getCurrentTimestamp() {
        return Timestamp.valueOf(LocalDateTime.now(TimeZone.getTimeZone(env.getProperty("db.postgres.zone.id")).toZoneId()).format(formatterTimestamp));
    }

    public Timestamp localDateTimeToTimestamp(LocalDateTime localDateTime) {
        return Timestamp.valueOf(localDateTime);
    }

    public Calendar getTimeZone() {
        return Calendar.getInstance(TimeZone.getTimeZone(env.getProperty("db.postgres.zone.id")));
    }

    public LocalDateTime timestampToLocalDateTime(Timestamp timestamp) {
        return timestamp.toLocalDateTime();
    }
}
