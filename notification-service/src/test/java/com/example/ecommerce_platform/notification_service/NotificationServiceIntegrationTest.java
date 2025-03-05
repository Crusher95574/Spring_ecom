//package com.example.ecommerce_platform.notification_service;
//
//import com.example.ecommerce_platform.notification_service.model.Notification;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.http.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class NotificationServiceIntegrationTest {
//
//    @Autowired
//    private TestRestTemplate restTemplate;
//
//    private Notification notification;
//
//    @BeforeEach
//    public void setUp(){
//        notification = new Notification();
//        notification.setType("EMAIL");
//        notification.setRecipient("user@example.com");
//        notification.setSubject("Test Notification");
//        notification.setMessage("This is a test notification.");
//        notification.setStatus("PENDING");
//    }
//
//    @Test
//    public void testSendNotification() {
//
//        // POST request to send notification
//        ResponseEntity<Notification> response = restTemplate.postForEntity("/notifications", notification, Notification.class);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        Notification sentNotification = response.getBody();
//        assertNotNull(sentNotification);
//        // Check if status was updated to SENT (if processing was successful)
//    }
//
//    @Test
//    public void testGetNotificationById() {
//        // Assume a notification with id "notif-123" exists
//        ResponseEntity<Notification> response = restTemplate.getForEntity("/notifications/notif-123", Notification.class);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        Notification notification = response.getBody();
//        assertNotNull(notification);
//    }
//}
