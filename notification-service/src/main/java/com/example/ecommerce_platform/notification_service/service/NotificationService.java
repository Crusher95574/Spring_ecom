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

    @Autowired
    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    // Simulate sending a notification and save the record
    public Notification sendNotification(Notification notification){
        // Set the creation date if not provided
        if (notification.getCreatedDate() == null) {
            notification.setCreatedDate(LocalDateTime.now());
        }

        // Simulate sending logic
        // For example, if it's an email, you might integrate with SendGrid, etc.
        // Here, we simply set the status to SENT. In case of failure, set status to FAILED and record the error.
        try {
            // Simulate sending logic (this is where you integrate with an external service)
            // For demonstration, assume sending is always successful.
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

}
