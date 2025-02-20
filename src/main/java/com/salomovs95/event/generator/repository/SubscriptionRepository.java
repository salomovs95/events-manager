package com.salomovs95.event.generator.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.salomovs95.event.generator.dto.SubscriptionRankingItem;
import com.salomovs95.event.generator.entity.EventEntity;
import com.salomovs95.event.generator.entity.SubscriptionEntity;
import com.salomovs95.event.generator.entity.UserEntity;

public interface SubscriptionRepository extends CrudRepository<SubscriptionEntity, Integer> {
  Optional<SubscriptionEntity> findByEventAndSubscriber(EventEntity event, UserEntity subscriber);

  @Query(value = """
    SELECT referral_id as userId, username, COUNT(subscription_number) as referrals 
    FROM tbl_subscriptions s 
    INNER JOIN tbl_users u 
    ON s.referral_id = u.id 
    WHERE referral_id IS NOT NULL 
    AND event_id = :event_id 
    GROUP BY referral_id 
    ORDER BY referrals DESC 
    LIMIT 5;
  """, nativeQuery = true)
  public List<SubscriptionRankingItem> generateRanking(@Param("event_id") Integer eventId);
}
