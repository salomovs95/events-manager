package com.salomovs95.event.generator.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.salomovs95.event.generator.dto.SubscriptionResponse;
import com.salomovs95.event.generator.entity.EventEntity;
import com.salomovs95.event.generator.entity.SubscriptionEntity;
import com.salomovs95.event.generator.entity.UserEntity;
import com.salomovs95.event.generator.repository.EventRepository;
import com.salomovs95.event.generator.repository.SubscriptionRepository;
import com.salomovs95.event.generator.repository.UserRepository;

@Tag("SUBSCRIPTION_TESTS")
@SpringBootTest @TestInstance(Lifecycle.PER_CLASS)
public class SubscriptionServiceTests {
  @MockitoBean
  EventRepository mEventRepository;

  @MockitoBean
  SubscriptionRepository mSubscriptionRepository;

  @MockitoBean
  UserRepository mUserRepository;

  SubscriptionService subscriptionService;

  @BeforeAll
  void SETUP() {
    subscriptionService = new SubscriptionService(mSubscriptionRepository, mUserRepository, mEventRepository);
  }

  @Test
  void SUBSCRIBE_FAILS_EVENT_DOESNT_EXISTS() {
    when(mEventRepository.findByPrettyName(anyString()))
      .thenReturn(Optional.empty());
    Exception res = assertThrows(Exception.class, ()->subscriptionService.subscribe("event-1", 123, "Salomov95", "salomovs95@nlw.com"));
    assertEquals("Event not found with pretty name: event-1", res.getMessage());
  }

  @Test
  void SUBSCRIBE_FAILS_USER_ALREADY_JOINED_EVENT() {
    UserEntity mockSubscriber = new UserEntity();
    mockSubscriber.setId(1);
    mockSubscriber.setUsername("subscriber001");

    EventEntity mockEvent = new EventEntity();
    mockEvent.setPrettyName("pretty-name");
    mockEvent.setTitle("Pretty Name");

    SubscriptionEntity mockSubscription = new SubscriptionEntity();
    mockSubscription.setSubscriptionNumber(1);
    mockSubscription.setEvent(mockEvent);
    mockSubscription.setSubscriber(mockSubscriber);

    when(mEventRepository.findByPrettyName(anyString()))
      .thenReturn(Optional.of(mockEvent));

    when(mSubscriptionRepository.findByEventAndSubscriber(any(), any()))
      .thenReturn(Optional.of(mockSubscription));

    Exception res = assertThrows(Exception.class, ()->subscriptionService.subscribe("pretty-name", null, "subscriber001", "subscriberEmail"));
    assertEquals("User 'subscriber001' already subscribed for event 'Pretty Name'", res.getMessage());
  }
  
  @Test
  void SUBSCRIBE_SUCCEEDS() throws Exception {
    UserEntity mockSubscriber = new UserEntity();
    mockSubscriber.setId(2);
    mockSubscriber.setUsername("subscriber002");

    EventEntity mockEvent = new EventEntity();
    mockEvent.setPrettyName("pretty-name-2");
    mockEvent.setTitle("Pretty Name 2");

    SubscriptionEntity mSubscription = new SubscriptionEntity();
    mSubscription.setSubscriptionNumber(2);
    mSubscription.setEvent(mockEvent);
    mSubscription.setSubscriber(mockSubscriber);

    when(mUserRepository.findByEmail(anyString()))
      .thenReturn(Optional.of(mockSubscriber));

    when(mEventRepository.findByPrettyName(anyString()))
      .thenReturn(Optional.of(mockEvent));

    when(mSubscriptionRepository.findByEventAndSubscriber(any(), any()))
      .thenReturn(Optional.empty());

    when(mSubscriptionRepository.save(any(SubscriptionEntity.class)))
      .thenReturn(mSubscription);

    SubscriptionResponse res = subscriptionService.subscribe("pretty-name-2", null, "subscriber002", "subscriber002@nlw.com");
    assertTrue(res.referralLink().endsWith("/subscriptions/pretty-name-2/2"));
  }
}
