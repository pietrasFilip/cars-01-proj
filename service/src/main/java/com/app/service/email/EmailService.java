package com.app.service.email;


public interface EmailService {
    void send(String recipient, String subject, String content);
}
