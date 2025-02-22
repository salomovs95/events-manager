package com.salomovs95.event.generator.dto;

public record EventTopFiveItem(
  Integer id,
  String title,
  String prettyName,
  String location,
  Long subscriptions
) {}
