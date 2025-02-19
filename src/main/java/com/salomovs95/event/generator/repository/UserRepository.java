package com.salomovs95.event.generator.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.salomovs95.event.generator.entity.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Integer> {
  Optional<UserEntity> findByEmail(String email);
}
