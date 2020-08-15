package com.jmendoza.wallet.infrastructure.notification.sms;

import com.jmendoza.wallet.domain.model.notification.DataNotification;
import com.jmendoza.wallet.domain.ports.outbound.notification.SendNotificationPort;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("sendNotificationSMS")
public class SendNotificationSMSAdapter implements SendNotificationPort {

    private static final Logger loggerException = LogManager.getLogger(SendNotificationSMSAdapter.class);

    @Override
    public void sendNotification(DataNotification dataNotification) {
        loggerException.debug(dataNotification);
    }
}
