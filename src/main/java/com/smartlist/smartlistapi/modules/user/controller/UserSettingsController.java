package com.smartlist.smartlistapi.modules.user.controller;

import com.smartlist.smartlistapi.modules.user.dto.SystemDiagnosticResponseDTO;
import com.smartlist.smartlistapi.modules.user.dto.UserSettingsRequestDTO;
import com.smartlist.smartlistapi.modules.user.dto.UserSettingsResponseDTO;
import com.smartlist.smartlistapi.modules.user.service.UserSettingsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/settings")
@RequiredArgsConstructor
public class UserSettingsController {

    private final UserSettingsService service;

    @GetMapping
    public ResponseEntity<UserSettingsResponseDTO> getSettings() {
        return ResponseEntity.ok(service.getSettings());
    }

    @PutMapping
    public ResponseEntity<UserSettingsResponseDTO> updateSettings(@Valid @RequestBody UserSettingsRequestDTO dto) {
        return ResponseEntity.ok(service.updateSettings(dto));
    }

    @GetMapping("/diagnostic")
    public ResponseEntity<SystemDiagnosticResponseDTO> getDiagnostic() {
        return ResponseEntity.ok(service.getDiagnostic());
    }
}