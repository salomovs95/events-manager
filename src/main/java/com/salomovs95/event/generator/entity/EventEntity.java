package com.salomovs95.event.generator.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity @Table(name="tbl_events")
public class EventEntity {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable=false, unique=true)
  private String title;

  @Column(name="pretty_name", nullable=false, unique=true)
  private String prettyName;

  @Column(nullable=false)
  private Integer price;

  @Column(nullable=false)
  private String location;

  @Column(name="start_date", nullable=false)
  private LocalDate startDate;

  @Column(name="end_date", nullable=false)
  private LocalDate endDate;

  @Column(name="start_time", nullable=false)
  private LocalTime startTime;

  @Column(name="end_time", nullable=false)
  private LocalTime endTime;

  public Integer getId() { return id; }
  public String getTitle() { return title; }
  public String getPrettyName() { return prettyName; }
  public LocalDate getStartDate() {return startDate; }
  public LocalDate getEndDate() { return endDate; }
  public LocalTime getStartTime() { return startTime; }
  public LocalTime getEndTime() { return endTime; }
  public Integer getPrice() { return price; }
  public String getLocation() { return location; }

  public void setId(Integer id) { this.id = id; }
  public void setTitle(String title) { this.title = title; }
  public void setPrettyName(String prettyName) { this.prettyName = prettyName; }
  public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
  public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
  public void setStartTime(LocalTime startTime) { this.startTime = startTime; }
  public void setEndTime(LocalTime endTime) { this.endTime = endTime; }
  public void setPrice(Integer price) { this.price = price; }
  public void setLocation(String location) { this.location = location; }

  public EventEntity() {}

  public EventEntity(Integer id, String title, String prettyName, Integer price, String location, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
    this.id = id;
    this.title = title;
    this.prettyName = prettyName;
    this.price = price;
    this.location = location;
    this.startDate = startDate;
    this.endDate = endDate;
    this.startTime = startTime;
    this.endTime = endTime;
  }
}
