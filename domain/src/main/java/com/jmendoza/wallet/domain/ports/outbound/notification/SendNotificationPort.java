package com.jmendoza.wallet.domain.ports.outbound.notification;

import com.jmendoza.wallet.common.exception.GlobalException;
import com.jmendoza.wallet.domain.model.notification.DataNotification;

public interface SendNotificationPort {
    void sendNotification(DataNotification dataNotification) throws GlobalException;
}
