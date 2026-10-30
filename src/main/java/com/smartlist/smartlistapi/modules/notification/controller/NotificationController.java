package com.smartlist.smartlistapi.modules.notification.controller;

import com.smartlist.smartlistapi.modules.notification.dto.*;
import com.smartlist.smartlistapi.modules.notification.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService service;

    @PostMapping("/snooze")
    public ResponseEntity<SnoozeResponseDTO> applySnooze(@Valid @RequestBody SnoozeRequestDTO dto) {
        return ResponseEntity.ok(service.applySnooze(dto));
    }

    @DeleteMapping("/snooze")
    public ResponseEntity<SnoozeResponseDTO> cancelSnooze() {
        return ResponseEntity.ok(service.cancelSnooze());
    }

    @PostMapping("/trigger-check")
    public ResponseEntity<NotificationTriggerResponseDTO> checkTrigger(@Valid @RequestBody NotificationTriggerRequestDTO dto) {
        return ResponseEntity.ok(service.evaluateNotificationTrigger(dto));
    }
}