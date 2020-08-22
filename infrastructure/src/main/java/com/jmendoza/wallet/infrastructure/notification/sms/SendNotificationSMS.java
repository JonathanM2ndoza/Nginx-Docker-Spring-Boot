package com.jmendoza.wallet.infrastructure.notification.sms;

import com.jmendoza.wallet.domain.model.notification.DataNotification;
import com.jmendoza.wallet.infrastructure.notification.SendNotification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("SendNotificationSMS")
public class SendNotificationSMS implements SendNotification {

    private static final Logger loggerException = LogManager.getLogger(SendNotificationSMS.class);

    @Override
    public void sendNotification(DataNotification dataNotification) {
        loggerException.debug(dataNotification);
    }

    @Override
    public String getTypeNotification() {
        return "sms";
    }
}
