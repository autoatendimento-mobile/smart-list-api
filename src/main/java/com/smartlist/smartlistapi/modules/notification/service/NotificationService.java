package com.smartlist.smartlistapi.modules.notification.service;

import com.smartlist.smartlistapi.modules.geofence.dto.GeofenceCheckRequestDTO;
import com.smartlist.smartlistapi.modules.geofence.dto.GeofenceCheckResponseDTO;
import com.smartlist.smartlistapi.modules.geofence.service.ResidenceService;
import com.smartlist.smartlistapi.modules.notification.domain.SnoozeEntity;
import com.smartlist.smartlistapi.modules.notification.domain.SnoozeOptionEnum;
import com.smartlist.smartlistapi.modules.notification.dto.*;
import com.smartlist.smartlistapi.modules.notification.repository.SnoozeRepository;
import com.smartlist.smartlistapi.modules.shoppinglist.dto.ShoppingListGroupedDTO;
import com.smartlist.smartlistapi.modules.shoppinglist.service.ShoppingItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final SnoozeRepository snoozeRepository;
    private final ResidenceService residenceService;
    private final ShoppingItemService shoppingItemService;

    @Transactional
    public SnoozeResponseDTO applySnooze(SnoozeRequestDTO dto) {
        SnoozeEntity snooze = snoozeRepository.findFirstByOrderByIdAsc()
                .orElseGet(SnoozeEntity::new);

        snooze.setActive(true);
        snooze.setOption(dto.option());
        snooze.setSnoozedUntil(calculateSnoozeUntil(dto.option()));

        snoozeRepository.save(snooze);

        String message = dto.option() == SnoozeOptionEnum.INDEFINITE
                ? "Lembretes pausados até reativação manual."
                : "Lembretes pausados temporariamente.";

        return SnoozeResponseDTO.fromEntity(snooze, message);
    }

    @Transactional
    public SnoozeResponseDTO cancelSnooze() {
        SnoozeEntity snooze = snoozeRepository.findFirstByOrderByIdAsc()
                .orElseGet(SnoozeEntity::new);

        snooze.setActive(false);
        snooze.setOption(null);
        snooze.setSnoozedUntil(null);

        snoozeRepository.save(snooze);

        return SnoozeResponseDTO.fromEntity(snooze, "Monitoramento de lembretes reativado.");
    }

    @Transactional(readOnly = true)
    public NotificationTriggerResponseDTO evaluateNotificationTrigger(NotificationTriggerRequestDTO dto) {
        // 1. Checa itens pendentes na lista
        ShoppingListGroupedDTO groupedItems = shoppingItemService.getGroupedItems();
        if (groupedItems.pendingCount() == 0) {
            return new NotificationTriggerResponseDTO(
                    false,
                    "Nenhum item pendente na lista de compras.",
                    0,
                    List.of()
            );
        }

        // 2. Checa estado de Soneca
        SnoozeEntity snooze = snoozeRepository.findFirstByOrderByIdAsc().orElse(null);
        if (snooze != null && snooze.isActive()) {
            if (snooze.getOption() == SnoozeOptionEnum.INDEFINITE) {
                return new NotificationTriggerResponseDTO(
                        false,
                        "Notificações estão pausadas manualmente (Modo Soneca Indefinido).",
                        groupedItems.pendingCount(),
                        List.of()
                );
            }

            if (snooze.getSnoozedUntil() != null && LocalDateTime.now().isBefore(snooze.getSnoozedUntil())) {
                return new NotificationTriggerResponseDTO(
                        false,
                        "Modo soneca ativo até: " + snooze.getSnoozedUntil(),
                        groupedItems.pendingCount(),
                        List.of()
                );
            }
        }

        // 3. Checa Geofence (posição atual vs residência)
        GeofenceCheckResponseDTO geofence = residenceService.checkGeofenceStatus(
                new GeofenceCheckRequestDTO(dto.currentLatitude(), dto.currentLongitude())
        );

        if (geofence.insidePerimeter()) {
            return new NotificationTriggerResponseDTO(
                    false,
                    "Dispositivo ainda está dentro do raio residencial.",
                    groupedItems.pendingCount(),
                    List.of()
            );
        }

        // 4. Tudo certo -> Disparar Notificação!
        return new NotificationTriggerResponseDTO(
                true,
                "Afastamento detectado com itens pendentes na lista!",
                groupedItems.pendingCount(),
                groupedItems.pendingItems()
        );
    }

    private LocalDateTime calculateSnoozeUntil(SnoozeOptionEnum option) {
        LocalDateTime now = LocalDateTime.now();
        return switch (option) {
            case MINUTES_30 -> now.plusMinutes(30);
            case HOUR_1 -> now.plusHours(1);
            case TOMORROW_MORNING -> LocalDate.now().plusDays(1).atTime(8, 0);
            case INDEFINITE -> null;
        };
    }
}