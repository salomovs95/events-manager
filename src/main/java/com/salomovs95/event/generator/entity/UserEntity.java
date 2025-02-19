package com.salomovs95.event.generator.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity @Table(name="tbl_users")
public class UserEntity {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable=false, length=255)
  private String username;

  @Column(nullable=false, length=255)
  private String email;

  public Integer getId() { return id; }
  public String getEmail() { return email; }
  public String getUsername() { return username; }

  public void setId(Integer id) { this.id = id; }
  public void setEmail(String email) { this.email = email; }
  public void setUsername(String username) { this.username = username; }

  public UserEntity() {}

  public UserEntity(Integer id, String username, String email) {
    this.id = id;
    this.username = username;
    this.email = email;
  }
}
