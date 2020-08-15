package com.jmendoza.wallet.domain.model.notification;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Builder
@Accessors(chain = true)
public class DataNotification {
    private String destination;
    private String title;
    private String payload;
}
