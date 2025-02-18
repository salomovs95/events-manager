package com.salomovs95.event.generator.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Table(name="tbl_events")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class EventEntity {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable=false, unique=true)
  private String title;

  @Column(name="pretty_name", nullable=false, unique=true)
  private String prettyName;

  @Column(name="start_date", nullable=false)
  private LocalDate startDate;

  @Column(name="end_date", nullable=false)
  private LocalDate endDate;

  @Column(name="start_time", nullable=false)
  private LocalTime startTime;

  @Column(name="end_time", nullable=false)
  private LocalTime endTime;
}
