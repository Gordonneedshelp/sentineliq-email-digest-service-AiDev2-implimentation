package com.internship.tool.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailScheduler {
	private final EmailService emailService;
	//for production
    @Scheduled(cron = "0 0 9 * * ?")
    public void sendDailyDigest() {

        emailService.sendEmailToAllUsers(
                "Daily Digest 🚀",
                "Hello! This is your automated email digest."
        );

        System.out.println("Email sent to all users!");
    }
}
