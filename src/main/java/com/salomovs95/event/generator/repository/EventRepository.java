package com.salomovs95.event.generator.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salomovs95.event.generator.entity.EventEntity;

public interface EventRepository extends JpaRepository<EventEntity, Integer> {
  Optional<EventEntity> findByPrettyName(String prettyName);
}
