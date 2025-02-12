package com.example.ecommerce_platform.notification_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Notification {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @NotBlank(message = "Notification type is required")
    private String type; // e.g., EMAIL, SMS, PUSH

    // For EMAIL notifications, this can be an email address; for SMS, a phone number
    @NotBlank(message = "Recipient is required")
    private String recipient;

    @NotBlank(message = "Subject is required")
    private String subject;

    @NotBlank(message = "Message is required")
    private String message;

    // Status of the notification (e.g., SENT, FAILED)
    @NotBlank(message = "Status is required")
    private String status;

    @NotNull(message = "Notification date is required")
    private LocalDateTime createdDate;

    // Optionally, add a field for error message if sending fails
    private String errorMessage;
}
