package com.jmendoza.wallet.infrastructure.notification.email;

import com.jmendoza.wallet.common.exception.GlobalException;
import com.jmendoza.wallet.domain.model.notification.DataNotification;
import com.jmendoza.wallet.infrastructure.notification.SendNotification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

@Component
@Qualifier("SendNotificationEmail")
@Primary
public class SendNotificationEmail implements SendNotification {

    @Autowired
    private JavaMailSender javaMailSender;
    private static final Logger loggerException = LogManager.getLogger(SendNotificationEmail.class);

    @Override
    @Async("emailExecutor")
    public void sendNotification(DataNotification dataNotification) throws GlobalException {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setTo(dataNotification.getDestination());
            helper.setText(dataNotification.getPayload(), true);
            helper.setSubject(dataNotification.getTitle());
            javaMailSender.send(message);
        } catch (Exception e) {
            loggerException.error(e);
            throw new GlobalException("sendNotification: " + e.getMessage());
        }
    }

    @Override
    public String getTypeNotification() {
        return "email";
    }
}
