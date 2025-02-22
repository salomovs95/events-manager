package com.salomovs95.event.generator.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.salomovs95.event.generator.dto.EventTopFiveItem;
import com.salomovs95.event.generator.entity.EventEntity;

public interface EventRepository extends JpaRepository<EventEntity, Integer> {
  Optional<EventEntity> findByPrettyName(String prettyName);

  @Query(value="""
    SELECT e.id AS id, e.title AS title, e.pretty_name AS prettyName, e.location AS location,
    COUNT(s.event_id) AS subscriptions
    FROM tbl_events e
    LEFT JOIN tbl_subscriptions s ON e.id = s.event_id
    GROUP BY e.id, e.title
    ORDER BY subscriptions DESC
    LIMIT 5;
  """, nativeQuery=true)
  List<EventTopFiveItem> getTopFiveEvents();
}
