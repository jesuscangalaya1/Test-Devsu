package com.devsu.repository;

import com.devsu.entity.MotionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MotionRepository extends JpaRepository<MotionEntity, Long> {
}
