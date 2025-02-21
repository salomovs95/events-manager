package com.salomovs95.event.generator.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.salomovs95.event.generator.dto.SubscriptionRankingItem;
import com.salomovs95.event.generator.entity.EventEntity;
import com.salomovs95.event.generator.exception.EventNotFoundException;
import com.salomovs95.event.generator.exception.SubscriptionNotFoundException;
import com.salomovs95.event.generator.repository.EventRepository;
import com.salomovs95.event.generator.repository.SubscriptionRepository;
import com.salomovs95.event.generator.repository.UserRepository;

@Tag("SUBSCRIPTION_RANK_TESTS")
@SpringBootTest @TestInstance(Lifecycle.PER_CLASS)
public class SubscriptionRankingTests {
  @MockitoBean
  private SubscriptionRepository mSubsRepo;

  @MockitoBean
  private EventRepository mEventRepo;

  @MockitoBean
  private UserRepository mUserRepo;

  private SubscriptionService subService;

  @BeforeAll
  void SETUP() {
    subService = new SubscriptionService(mSubsRepo, mUserRepo, mEventRepo);
  }

  @Test
  void RETRIEVE_OVERALL_RANKING() {
    when(mEventRepo.findByPrettyName(anyString())).thenReturn(Optional.of(
      new EventEntity(0, "Pretty Name", "pretty-name", 0, null, null, null)
    ));

    when(mSubsRepo.generateRanking(anyInt())).thenReturn(
      new ArrayList<SubscriptionRankingItem>()
    );

    assertDoesNotThrow(()->subService.retrieveRanking("pretty-name"));
  }

  @Test
  void RETRIEVE_OVERALL_RANKING_NO_EVENT() {
    when(mEventRepo.findByPrettyName(anyString())).thenReturn(Optional.empty());
    EventNotFoundException ex = assertThrows(EventNotFoundException.class, ()->subService.retrieveRanking("pretty-name"));
    assertEquals("Event pretty-name not found", ex.getMessage());
  }

  @Test
  void RETRIEVE_RANKING_BY_USER() {
    when(mEventRepo.findByPrettyName(anyString())).thenReturn(Optional.of(
      new EventEntity(0, "Pretty Name", "pretty-name", 0, null, null, null)
    ));

    when(mSubsRepo.generateRanking(anyInt())).thenReturn(
      List.of(new SubscriptionRankingItem(0, "username", 1l))
    );

    assertDoesNotThrow(()->subService.retrieveRankingByUser("pretty-name", 0));
  }

  @Test
  void RETRIEVE_RANKING_BY_USER_NO_SUBSCRIPTION() {
    when(mEventRepo.findByPrettyName(anyString())).thenReturn(Optional.of(
      new EventEntity(0, "Pretty Name", "pretty-name", 0, null, null, null)
    ));

    when(mSubsRepo.generateRanking(anyInt())).thenReturn(
      List.of(new SubscriptionRankingItem(0, "username", 1l))
    );

    SubscriptionNotFoundException ex = assertThrows(SubscriptionNotFoundException.class, ()->subService.retrieveRankingByUser("pretty-name", 1));
    assertEquals("No subscription in event pretty-name was found for user 1", ex.getMessage());
  }

  @Test
  void RETRIEVE_RANKING_BY_USER_NO_EVENT() {
    when(mEventRepo.findByPrettyName(anyString())).thenReturn(Optional.empty());
    
    EventNotFoundException ex = assertThrows(EventNotFoundException.class, ()->subService.retrieveRankingByUser("pretty-name", 0));
    assertEquals("Event pretty-name not found", ex.getMessage());
  }
}
