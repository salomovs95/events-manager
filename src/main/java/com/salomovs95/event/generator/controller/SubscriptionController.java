package com.salomovs95.event.generator.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.salomovs95.event.generator.dto.CreateUserDto;
import com.salomovs95.event.generator.dto.SubscriptionRankingByUser;
import com.salomovs95.event.generator.dto.SubscriptionRankingItem;
import com.salomovs95.event.generator.dto.SubscriptionResponse;
import com.salomovs95.event.generator.service.SubscriptionService;

@RestController @RequestMapping("/subscriptions")
public class SubscriptionController {
  private final SubscriptionService subscriptionService;

  public SubscriptionController(final SubscriptionService service) {
    this.subscriptionService = service;
  }

  @PostMapping({"/{prettyName}", "/{prettyName}/{referralId}"})
  public ResponseEntity<?> getHostUrl(@PathVariable String prettyName, @PathVariable Optional<Integer> referralId, @RequestBody CreateUserDto body) throws Exception {
    if (referralId.isEmpty()) {
      referralId = Optional.of(-1);
    }

    SubscriptionResponse response = subscriptionService.subscribe(
      prettyName,
      referralId.get(),
      body.username(),
      body.email()
    );

    return ResponseEntity.status(201).body(response);
  }

  @GetMapping("/{prettyName}/ranking")
  public ResponseEntity<?> retrieveRanking(@PathVariable String prettyName) throws Exception {
    List<SubscriptionRankingItem> ranking = subscriptionService.retrieveRanking(prettyName);
    return ResponseEntity.status(200).body(ranking);
  }

  @GetMapping("/{prettyName}/ranking/{referralId}")
  public ResponseEntity<?> retrieveRankingPosition(@PathVariable String prettyName, @PathVariable Integer referralId) throws Exception {
    SubscriptionRankingByUser ranking = subscriptionService.retrieveRankingByUser(prettyName, referralId);
    return ResponseEntity.status(200).body(ranking);
  }
}
