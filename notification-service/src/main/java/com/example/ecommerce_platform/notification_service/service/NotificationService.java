package com.example.ecommerce_platform.notification_service.service;

import com.example.ecommerce_platform.notification_service.model.Notification;
import com.example.ecommerce_platform.notification_service.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private EmailService emailService;
    private SmsService smsService;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository, EmailService emailService, SmsService smsService) {
        this.notificationRepository = notificationRepository;
        this.emailService = emailService;
        this.smsService = smsService;
    }

    // Simulate sending a notification and save the record
    public Notification sendNotification(Notification notification){
        if (notification.getCreatedDate() == null) {
            notification.setCreatedDate(LocalDateTime.now());
        }
        try {
            if ("EMAIL".equalsIgnoreCase(notification.getType())) {
                emailService.sendSimpleEmail(notification.getRecipient(), notification.getSubject(), notification.getMessage());
            }else if ("SMS".equalsIgnoreCase(notification.getType())) {
                smsService.sendSms(notification.getRecipient(), notification.getMessage());
            }
            // You can integrate SMS logic below if needed.
            notification.setStatus("SENT");
            notification.setErrorMessage(null);
        } catch (Exception ex) {
            notification.setStatus("FAILED");
            notification.setErrorMessage(ex.getMessage());
        }
        return notificationRepository.save(notification);
    }

    //Retrieve a notification by ID
    public Optional<Notification> getNotificationById(String id){
        return notificationRepository.findById(id);
    }

    // Retrieve all notifications for a recipient
    public List<Notification> getNotificationsByRecipient(String recipient) {
        return notificationRepository.findByRecipient(recipient);
    }

    // List all notifications (with optional filtering logic if needed)
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    // Resend a failed notification
    public Notification resendNotification(String id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found with id " + id));
        if (!"FAILED".equalsIgnoreCase(notification.getStatus())) {
            throw new RuntimeException("Only notifications with status FAILED can be resent");
        }
        // Attempt to resend notification based on its type
        try {
            if ("EMAIL".equalsIgnoreCase(notification.getType())) {
                emailService.sendSimpleEmail(notification.getRecipient(), notification.getSubject(), notification.getMessage());
            } else if ("SMS".equalsIgnoreCase(notification.getType())) {
                smsService.sendSms(notification.getRecipient(), notification.getMessage());
            }
            notification.setStatus("SENT");
            notification.setErrorMessage(null);
        } catch (Exception ex) {
            notification.setStatus("FAILED");
            notification.setErrorMessage(ex.getMessage());
        }
        return notificationRepository.save(notification);
    }

    // Delete a notification
    public void deleteNotification(String id) {
        notificationRepository.deleteById(id);
    }

}
