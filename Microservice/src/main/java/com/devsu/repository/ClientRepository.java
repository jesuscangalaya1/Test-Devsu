package com.devsu.repository;

import com.devsu.entity.ClientEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {


    List<ClientEntity> findAllByStateTrue();

    Optional<ClientEntity> findByClientIdAndStateTrue(Long id);

    // Consulta para desactivar un cliente por su ID
    @Modifying
    @Query("UPDATE ClientEntity p SET p.state = false WHERE p.clientId = :clientId")
    void desactivarClient(@Param("clientId") Long clientId);
}
