package com.salomovs95.event.generator.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.salomovs95.event.generator.entity.EventEntity;
import com.salomovs95.event.generator.entity.SubscriptionEntity;
import com.salomovs95.event.generator.entity.UserEntity;

public interface SubscriptionRepository extends CrudRepository<SubscriptionEntity, Integer> {
  Optional<SubscriptionEntity> findByEventAndSubscriber(EventEntity event, UserEntity subscriber);
}
