package com.salomovs95.event.generator.dto;

public record SubscriptionRankingByUser(
  Integer rankingPosition,
  SubscriptionRankingItem referrer
) {}
