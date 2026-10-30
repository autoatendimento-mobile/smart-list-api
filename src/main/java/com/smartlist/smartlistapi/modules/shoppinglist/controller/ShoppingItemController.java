package com.smartlist.smartlistapi.modules.shoppinglist.controller;

import com.smartlist.smartlistapi.modules.shoppinglist.dto.ShoppingItemRequestDTO;
import com.smartlist.smartlistapi.modules.shoppinglist.dto.ShoppingItemResponseDTO;
import com.smartlist.smartlistapi.modules.shoppinglist.dto.ShoppingListGroupedDTO;
import com.smartlist.smartlistapi.modules.shoppinglist.service.ShoppingItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/shopping-items")
@RequiredArgsConstructor
public class ShoppingItemController {

    private final ShoppingItemService service;

    @GetMapping
    public ResponseEntity<ShoppingListGroupedDTO> getAllItemsGrouped() {
        return ResponseEntity.ok(service.getGroupedItems());
    }

    @PostMapping
    public ResponseEntity<ShoppingItemResponseDTO> createItem(@Valid @RequestBody ShoppingItemRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createItem(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShoppingItemResponseDTO> updateItem(@PathVariable Long id, @Valid @RequestBody ShoppingItemRequestDTO dto) {
        return ResponseEntity.ok(service.updateItem(id, dto));
    }

    @PatchMapping("/{id}/toggle")
    public ResponseEntity<ShoppingItemResponseDTO> toggleItem(@PathVariable Long id) {
        return ResponseEntity.ok(service.toggleItemCompletion(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        service.deleteItem(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/completed")
    public ResponseEntity<Void> clearCompleted() {
        service.clearCompletedItems();
        return ResponseEntity.noContent().build();
    }
}