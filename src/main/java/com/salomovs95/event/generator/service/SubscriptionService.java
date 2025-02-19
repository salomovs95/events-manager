package com.salomovs95.event.generator.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.salomovs95.event.generator.dto.SubscriptionRankingByUser;
import com.salomovs95.event.generator.dto.SubscriptionRankingItem;
import com.salomovs95.event.generator.dto.SubscriptionResponse;
import com.salomovs95.event.generator.repository.SubscriptionRepository;
import com.salomovs95.event.generator.entity.EventEntity;
import com.salomovs95.event.generator.entity.SubscriptionEntity;
import com.salomovs95.event.generator.entity.UserEntity;
import com.salomovs95.event.generator.exception.EventNotFoundException;
import com.salomovs95.event.generator.exception.SubscriptionNotFoundException;
import com.salomovs95.event.generator.exception.SubscriptionAlreadyExistsException;
import com.salomovs95.event.generator.repository.EventRepository;
import com.salomovs95.event.generator.repository.UserRepository;

@Service
public class SubscriptionService {
  private final SubscriptionRepository subscriptionRepo;
  private final UserRepository userRepo;
  private final EventRepository eventRepo;

  public SubscriptionService(
      final SubscriptionRepository sRepository,
      final UserRepository uRepository,
      final EventRepository eRepository
  ) {
    this.userRepo = uRepository;
    this.eventRepo = eRepository;
    this.subscriptionRepo = sRepository;
  }

  public SubscriptionResponse subscribe(String eventPrettyName, Integer referralId, String subscriberName, String subscriberEmail) throws Exception {
    EventEntity event = eventRepo.findByPrettyName(eventPrettyName).orElseThrow(()->new EventNotFoundException(String.format("Event not found with pretty name: %s", eventPrettyName)));
    
    UserEntity subscriber = userRepo.findByEmail(subscriberEmail)
      .orElse(userRepo.save(new UserEntity(null, subscriberName, subscriberEmail)));

    if (subscriptionRepo.findByEventAndSubscriber(event, subscriber).isPresent()) {
      throw new SubscriptionAlreadyExistsException(String.format("User '%s' already subscribed for event '%s'", subscriberName, event.getTitle()));
    }

    UserEntity referral = userRepo.findById(referralId).orElse(null);
    SubscriptionEntity subscription = subscriptionRepo.save(new SubscriptionEntity(null, subscriber, referral, event));

    String referralLink = String.format(
      "%s/subscriptions/%s/%d",
      ServletUriComponentsBuilder.fromCurrentContextPath().toUriString(),
      event.getPrettyName(),
      subscriber.getId()
    );

    return new SubscriptionResponse(subscription.getSubscriptionNumber(), referralLink);
  }

  public List<SubscriptionRankingItem> retrieveRanking(String prettyName) throws Exception {
    EventEntity event = eventRepo.findByPrettyName(prettyName)
      .orElseThrow(()->new EventNotFoundException(String.format("Event %s not found", prettyName)));
    
    return subscriptionRepo.generateRanking(event.getId());
  }

  public SubscriptionRankingByUser retrieveRankingByUser(String prettyName, Integer userId) throws Exception {
    SubscriptionRankingItem ranking = retrieveRanking(prettyName)
      .stream()
      .filter(sub->sub.userId().equals(userId))
      .findFirst()
      .orElseThrow(()->new SubscriptionNotFoundException(String.format("No subscription in event %s was found for user %d", prettyName, userId)));
    
    return new SubscriptionRankingByUser(1, ranking);
  }
}
