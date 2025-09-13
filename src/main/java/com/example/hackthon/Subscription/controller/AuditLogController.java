package com.example.hackthon.Subscription.controller;

import com.example.hackthon.Subscription.model.AuditLog;
import com.example.hackthon.Subscription.service.AuditLogService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit")
public class AuditLogController {

    private final AuditLogService auditLogService;

    public AuditLogController(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    @GetMapping("/logs")
    public List<AuditLog> getAllAuditLogs() {
        return auditLogService.getAllLogs();
    }

    @PostMapping("/logs")
    public AuditLog createAuditLog(@RequestBody AuditLog auditLog) {
        return auditLogService.saveLog(auditLog);
    }
}
