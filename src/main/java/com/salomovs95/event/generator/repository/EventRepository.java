package com.salomovs95.event.generator.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salomovs95.event.generator.entity.EventEntity;

public interface EventRepository extends JpaRepository<EventEntity, Integer> {}
