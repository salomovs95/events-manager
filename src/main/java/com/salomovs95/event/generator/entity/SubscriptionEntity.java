package com.salomovs95.event.generator.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity @Table(name="tbl_subscriptions")
public class SubscriptionEntity {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) @Column(name="subscription_number")
  private Integer subscriptionNumber;

  @ManyToOne @JoinColumn(referencedColumnName="id", name="event_id")
  private EventEntity event;

  @ManyToOne @JoinColumn(referencedColumnName="id", name="subscriber_id")
  private UserEntity subscriber;

  @ManyToOne @JoinColumn(referencedColumnName="id", name="referral_id")
  private UserEntity referral;

  public EventEntity getEvent() { return event; }
  public UserEntity getSubscriber() { return subscriber; }
  public UserEntity getReferral() { return referral; }
  public Integer getSubscriptionNumber() { return subscriptionNumber; }

  public void setEvent(EventEntity event) { this.event = event; }
  public void setReferral(UserEntity referral) { this.referral = referral; }
  public void setSubscriber(UserEntity subscriber) { this.subscriber = subscriber; }
  public void setSubscriptionNumber(Integer subscriptionNumber) { this.subscriptionNumber = subscriptionNumber; }

  public SubscriptionEntity() {}

  public SubscriptionEntity(Integer subscriptionNumber, UserEntity subscriber, UserEntity referral, EventEntity event) {
    this.subscriptionNumber = subscriptionNumber;
    this.subscriber = subscriber;
    this.referral = referral;
    this.event = event;
  }
}
