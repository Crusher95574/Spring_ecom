package com.example.ecommerce_platform.notification_service.repository;

import com.example.ecommerce_platform.notification_service.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification,String> {
    List<Notification> findByRecipient(String recipient);
}
