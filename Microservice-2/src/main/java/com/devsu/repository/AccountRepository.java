package com.devsu.repository;

import com.devsu.entity.AccountEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    List<AccountEntity> findAllByStateTrue();

    Optional<AccountEntity> findByIdAndStateTrue(Long id);

    // Consulta para desactivar un cliente por su ID
    @Modifying
    @Query("UPDATE AccountEntity p SET p.state = false WHERE p.id = :id")
    void desactivarAccount(@Param("id") Long id);
    List<AccountEntity> findByClientId(Long clientId);
}
