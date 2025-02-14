package com.example.ecommerce_platform.notification_service.service;

import com.example.ecommerce_platform.notification_service.config.TwilioConfig;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;

@Service
public class SmsService {

    private static final Logger logger = LoggerFactory.getLogger(SmsService.class);
    private final TwilioConfig twilioConfig;

    public SmsService(TwilioConfig twilioConfig) {
        this.twilioConfig = twilioConfig;
    }

    @PostConstruct
    public void init() {
        if (twilioConfig.getAccountSid() == null ||
                twilioConfig.getAuthToken() == null ||
                twilioConfig.getFromPhone() == null) {
            logger.error("Twilio configuration properties are not set properly!");
            throw new IllegalStateException("Twilio configuration properties are not set properly!");
        }
        logger.info("Twilio configuration loaded successfully.");
    }

    public void sendSms(String to, String text) {
        try {
            Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());
            System.out.println(twilioConfig.getAccountSid());
            Message message = Message.creator(
                    new PhoneNumber(to),
                    new PhoneNumber(twilioConfig.getFromPhone()),
                    text
            ).create();
            logger.info("SMS sent successfully, SID: {}", message.getSid());
        } catch (Exception ex) {
            logger.error("Error sending SMS to {}: {}", to, ex.getMessage(), ex);
            throw ex;
        }
    }
}
