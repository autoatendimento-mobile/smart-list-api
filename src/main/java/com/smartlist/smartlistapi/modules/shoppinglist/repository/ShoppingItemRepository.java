package com.smartlist.smartlistapi.modules.shoppinglist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.smartlist.smartlistapi.modules.shoppinglist.domain.ShoppingItemEntity;

import java.util.List;

public interface ShoppingItemRepository extends JpaRepository<ShoppingItemEntity, Long> {

    List<ShoppingItemEntity> findByCompletedFalseOrderByCreatedAtDesc();

    List<ShoppingItemEntity> findByCompletedTrueOrderByUpdatedAtDesc();

    void deleteByCompletedTrue();
}