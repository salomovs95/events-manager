package com.salomovs95.event.generator.dto;

public record SubscriptionRankingItem(
  Integer userId,
  String username,
  Long referrals
) {}
