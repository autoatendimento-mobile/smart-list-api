package com.smartlist.smartlistapi.modules.geofence.controller;

import com.smartlist.smartlistapi.modules.geofence.dto.GeofenceCheckRequestDTO;
import com.smartlist.smartlistapi.modules.geofence.dto.GeofenceCheckResponseDTO;
import com.smartlist.smartlistapi.modules.geofence.dto.ResidenceRequestDTO;
import com.smartlist.smartlistapi.modules.geofence.dto.ResidenceResponseDTO;
import com.smartlist.smartlistapi.modules.geofence.service.ResidenceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/residence")
@RequiredArgsConstructor
public class ResidenceController {

    private final ResidenceService service;

    @GetMapping
    public ResponseEntity<ResidenceResponseDTO> getResidence() {
        return ResponseEntity.ok(service.getResidence());
    }

    @PostMapping
    public ResponseEntity<ResidenceResponseDTO> saveOrUpdateResidence(@Valid @RequestBody ResidenceRequestDTO dto) {
        return ResponseEntity.ok(service.saveOrUpdateResidence(dto));
    }

    @PostMapping("/check-geofence")
    public ResponseEntity<GeofenceCheckResponseDTO> checkGeofence(@Valid @RequestBody GeofenceCheckRequestDTO dto) {
        return ResponseEntity.ok(service.checkGeofenceStatus(dto));
    }
}