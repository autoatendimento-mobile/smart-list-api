package com.smartlist.smartlistapi.modules.geofence.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ResidenceRequestDTO(
    @NotBlank(message = "O endereço é obrigatório")
    String address,

    String complement,

    @NotNull(message = "A latitude é obrigatória")
    Double latitude,

    @NotNull(message = "A longitude é obrigatória")
    Double longitude,

    @NotNull(message = "O raio de monitoramento é obrigatório")
    @Min(value = 50, message = "O raio mínimo é de 50 metros")
    @Max(value = 1000, message = "O raio máximo é de 1000 metros")
    Integer radiusMeters,

    Boolean geofenceActive
) {}