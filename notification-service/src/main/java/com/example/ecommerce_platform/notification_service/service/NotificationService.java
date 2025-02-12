package com.example.ecommerce_platform.notification_service.service;

import com.example.ecommerce_platform.notification_service.model.Notification;
import com.example.ecommerce_platform.notification_service.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
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
