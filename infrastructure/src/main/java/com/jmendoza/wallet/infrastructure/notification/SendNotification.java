package com.jmendoza.wallet.infrastructure.notification;

import com.jmendoza.wallet.common.exception.GlobalException;
import com.jmendoza.wallet.domain.model.notification.DataNotification;

public interface SendNotification {
    void sendNotification(DataNotification dataNotification) throws GlobalException;

    String getTypeNotification();
}
