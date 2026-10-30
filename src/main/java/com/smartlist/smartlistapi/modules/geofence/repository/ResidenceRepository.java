package com.smartlist.smartlistapi.modules.geofence.repository;

import com.smartlist.smartlistapi.modules.geofence.domain.ResidenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ResidenceRepository extends JpaRepository<ResidenceEntity, Long> {
    
    // Como a aplicação é mono-usuário/local, pegamos a primeira residência cadastrada
    Optional<ResidenceEntity> findFirstByOrderByIdAsc();
}