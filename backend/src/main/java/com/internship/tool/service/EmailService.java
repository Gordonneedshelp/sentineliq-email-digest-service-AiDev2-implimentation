package com.internship.tool.service;

import com.internship.tool.entity.User;
import com.internship.tool.entity.EmailLog;
import com.internship.tool.repository.UserRepository;
import com.internship.tool.repository.EmailLogRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final UserRepository userRepository;
    private final EmailLogRepository emailLogRepository;

    //  Send single email + log
    public void sendEmail(String to, String subject, String body) {

        EmailLog log = EmailLog.builder()
                .toEmail(to)
                .subject(subject)
                .body(body)
                .build(); 

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);

            mailSender.send(message);

            log.setStatus("SUCCESS");

        } catch (Exception e) {
            log.setStatus("FAILED");
        }

        emailLogRepository.save(log);
    }

    // Send to all active users
    public void sendEmailToAllUsers(String subject, String body) {

        List<User> users = userRepository.findByActiveTrue();

        for (User user : users) {
            sendEmail(user.getEmail(), subject, body);
        }
    }
}
