//package com.example.ecommerce_platform.notification_service.service;
//
//import com.example.ecommerce_platform.notification_service.model.Notification;
//import com.example.ecommerce_platform.notification_service.repository.NotificationRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//import static org.junit.jupiter.api.Assertions.*;
//
//public class NotificationServiceTest {
//
//    @Mock
//    private NotificationRepository notificationRepository;
//
//    @Mock
//    private EmailService emailService;
//
//    @Mock
//    private SmsService smsService;
//
//    @InjectMocks
//    private NotificationService notificationService;
//
//    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void testSendEmailNotification() {
//        // Arrange: Create a sample email notification
//        Notification notification = new Notification();
//        notification.setType("EMAIL");
//        notification.setRecipient("user@example.com");
//        notification.setSubject("Test Email");
//        notification.setMessage("This is a test email notification.");
//        notification.setStatus("PENDING");
//
//        // Simulate email sending (if no exception thrown, assume success)
//        // Optionally, you can verify emailService.sendSimpleEmail(...) is called
//
//        when(notificationRepository.save(any(Notification.class))).thenAnswer(invocation -> {
//            Notification saved = invocation.getArgument(0);
//            saved.setId("notif-001");
//            return saved;
//        });
//
//        // Act: Send the notification via service
//        Notification sentNotification = notificationService.sendNotification(notification);
//
//        // Assert: Verify that the notification status is updated to SENT
//        assertNotNull(sentNotification.getId());
//        assertEquals("SENT", sentNotification.getStatus());
//    }
//
//    @Test
//    public void testSendSmsNotification_Failure() {
//        // Arrange: Create a sample SMS notification
//        Notification notification = new Notification();
//        notification.setType("SMS");
//        notification.setRecipient("+1234567890");
//        notification.setSubject("Test SMS");
//        notification.setMessage("This is a test SMS notification.");
//        notification.setStatus("PENDING");
//
//        // Simulate SMS sending failure by throwing an exception in smsService.sendSms()
//        when(smsService.sendSms(notification.getRecipient(), notification.getMessage()))
//                .thenThrow(new RuntimeException("SMS sending failed"));
//
//        when(notificationRepository.save(any(Notification.class))).thenAnswer(invocation -> {
//            Notification saved = invocation.getArgument(0);
//            saved.setId("generate-id");
//            return saved;
//        });
//
//        // Act: Send the notification via service and catch the error in the notification status
//        Notification sentNotification = notificationService.sendNotification(notification);
//
//        // Assert: Verify that the notification status is updated to FAILED and errorMessage is set
//        assertNotNull(sentNotification.getId());
//        assertEquals("FAILED", sentNotification.getStatus());
//        assertEquals("SMS sending failed", sentNotification.getErrorMessage());
//    }
//}
