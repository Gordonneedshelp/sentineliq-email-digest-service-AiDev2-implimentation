package com.internship.tool.controller;

import com.internship.tool.entity.EmailLog;
import com.internship.tool.repository.EmailLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/email/logs")
@RequiredArgsConstructor
public class EmailLogController {

    private final EmailLogRepository repository;

    @GetMapping
    public List<EmailLog> getAllLogs() {
        return repository.findAll();
    }
}
