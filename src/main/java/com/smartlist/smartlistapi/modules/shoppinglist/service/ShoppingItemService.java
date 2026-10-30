package com.smartlist.smartlistapi.modules.shoppinglist.service;

import com.smartlist.smartlistapi.common.exception.ResourceNotFoundException;
import com.smartlist.smartlistapi.modules.shoppinglist.domain.ShoppingItemEntity;
import com.smartlist.smartlistapi.modules.shoppinglist.dto.ShoppingItemRequestDTO;
import com.smartlist.smartlistapi.modules.shoppinglist.dto.ShoppingItemResponseDTO;
import com.smartlist.smartlistapi.modules.shoppinglist.dto.ShoppingListGroupedDTO;
import com.smartlist.smartlistapi.modules.shoppinglist.repository.ShoppingItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShoppingItemService {

    private final ShoppingItemRepository repository;

    @Transactional(readOnly = true)
    public ShoppingListGroupedDTO getGroupedItems() {
        List<ShoppingItemResponseDTO> pending = repository.findByCompletedFalseOrderByCreatedAtDesc()
                .stream()
                .map(ShoppingItemResponseDTO::fromEntity)
                .toList();

        List<ShoppingItemResponseDTO> completed = repository.findByCompletedTrueOrderByUpdatedAtDesc()
                .stream()
                .map(ShoppingItemResponseDTO::fromEntity)
                .toList();

        return new ShoppingListGroupedDTO(
                pending.size(),
                pending,
                completed.size(),
                completed
        );
    }

    @Transactional
    public ShoppingItemResponseDTO createItem(ShoppingItemRequestDTO dto) {
        ShoppingItemEntity item = ShoppingItemEntity.builder()
                .name(dto.name())
                .quantity(dto.quantity())
                .category(dto.category())
                .build();

        return ShoppingItemResponseDTO.fromEntity(repository.save(item));
    }

    @Transactional
    public ShoppingItemResponseDTO updateItem(Long id, ShoppingItemRequestDTO dto) {
        ShoppingItemEntity item = findEntityById(id);
        item.setName(dto.name());
        item.setQuantity(dto.quantity());
        item.setCategory(dto.category());

        return ShoppingItemResponseDTO.fromEntity(repository.save(item));
    }

    @Transactional
    public ShoppingItemResponseDTO toggleItemCompletion(Long id) {
        ShoppingItemEntity item = findEntityById(id);
        item.setCompleted(!item.isCompleted());

        return ShoppingItemResponseDTO.fromEntity(repository.save(item));
    }

    @Transactional
    public void deleteItem(Long id) {
        ShoppingItemEntity item = findEntityById(id);
        repository.delete(item);
    }

    @Transactional
    public void clearCompletedItems() {
        repository.deleteByCompletedTrue();
    }

    private ShoppingItemEntity findEntityById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item não encontrado com o ID: " + id));
    }
}