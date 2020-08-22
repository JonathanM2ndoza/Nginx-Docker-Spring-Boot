package com.jmendoza.wallet.infrastructure.notification;

import com.jmendoza.wallet.common.exception.GlobalException;
import com.jmendoza.wallet.domain.model.notification.DataNotification;
import com.jmendoza.wallet.domain.ports.outbound.notification.SendNotificationPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class SendNotificationAdapter implements SendNotificationPort {

    @Autowired
    private Environment env;
    private SendNotification sendNotification;
    private Map<String, SendNotification> sendNotificationMap;

    @Autowired
    public void SendNotificationAdapter(List<SendNotification> sendNotifications) {
        sendNotificationMap = sendNotifications.stream()
                .collect(Collectors.toMap(SendNotification::getTypeNotification, Function.identity()));
    }

    public void sendNotification(DataNotification dataNotification) throws GlobalException {
        if (Boolean.parseBoolean(env.getRequiredProperty("notification.email.enabled")))
            sendNotification = sendNotificationMap.get("email");
        else if (Boolean.parseBoolean(env.getRequiredProperty("notification.sms.enabled")))
            sendNotification = sendNotificationMap.get("sms");
        else {
            //TODO: default
        }
        sendNotification.sendNotification(dataNotification);
    }
}
