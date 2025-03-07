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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;

@RestController @RequestMapping("/subscriptions") @Tags({
  @Tag(name="Subscriptions Handling")
})
public class SubscriptionController {
  private final SubscriptionService subscriptionService;

  public SubscriptionController(final SubscriptionService service) {
    this.subscriptionService = service;
  }

  @PostMapping({"/{prettyName}", "/{prettyName}/{referralId}"}) @Operation(summary="Subscribe", description="Creates a new subscription")
  @ApiResponses({
    @ApiResponse(
      responseCode="201",
      description="Subscription successfull",
      content={
        @Content(
          schema=@Schema(
            type="application/json",
            implementation=ResponseEntity.class,
            example="{}"
          )
        )
      }
    ),
    @ApiResponse(
      responseCode="400",
      description="Subscription Not Successfull : Bad Request",
      content={
        @Content(
          schema=@Schema(
            type="application/json",
            implementation=ResponseEntity.class,
            example="{\"error\":\"Invalid data provided\",\"cause\":\"SubscriptionAlreadyExistsException\"}"
          )
        )
      }
    ),
    @ApiResponse(
      responseCode="404",
      description="Event Not Found",
      content={
        @Content(
          schema=@Schema(
            type="application/json",
            implementation=ResponseEntity.class,
            example="{\"error\":\"Event not found\",\"cause\":\"EventNotFoundException\"}"
          )
        )
      }
    ),
    @ApiResponse(
      responseCode="500",
      description="Subscription Not Successfull : Internal Error",
      content={
        @Content(
          schema=@Schema(
            type="application/json",
            implementation=ResponseEntity.class,
            example="{\"error\":\"Can't subscribe\",\"cause\":\"EventCreationException\"}"
          )
        )
      }
    )
  })
  public ResponseEntity<SubscriptionResponse> handleSubscribe(@PathVariable String prettyName, @PathVariable Optional<Integer> referralId, @RequestBody CreateUserDto body) throws Exception {
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

  @GetMapping("/{prettyName}/ranking") @Operation(summary="Event Ranking", description="Retrieve top subscriptions")
  @ApiResponses({
    @ApiResponse(
      responseCode="200",
      description="A list of top subscriptions for a event",
      content={
        @Content(
          schema=@Schema(
            type="application/json",
            implementation=ResponseEntity.class,
            example="[]"
          )
        )
      }
    ),
    @ApiResponse(
      responseCode="404",
      description="Subscription Not Found :: Event Not Found",
      content={
        @Content(
          schema=@Schema(
            type="application/json",
            implementation=ResponseEntity.class,
            example="{\"error\":\"Not subscribes\",\"cause\":\"EventNotFoundException\"}"
          )
        )
      }
    )
  })
  public ResponseEntity<List<SubscriptionRankingItem>> retrieveRanking(@PathVariable String prettyName) throws Exception {
    List<SubscriptionRankingItem> ranking = subscriptionService.retrieveRanking(prettyName);
    return ResponseEntity.status(200).body(ranking);
  }

  @GetMapping("/{prettyName}/ranking/{referralId}") @Operation(summary="Individual ranking", description="Retrieve individual ranking position")
  @ApiResponses({
    @ApiResponse(
      responseCode="200",
      description="A object containing an individual ranking in an event",
      content=@Content(
        schema=@Schema(
          type="application/json",
          implementation=ResponseEntity.class,
          example="{}"
        )
      )
    ),
    @ApiResponse(
      responseCode="404",
      description="No data could be found for params",
      content=@Content(
        schema=@Schema(
          type="application/json",
          implementation=ResponseEntity.class,
          example="{\"error\":\"No data was found\",\"cause\":\"EventNotFoundException||SubscriptionNotFoundException\"}"
        )
      )
    )
  })
  public ResponseEntity<SubscriptionRankingByUser> retrieveRankingPosition(@PathVariable String prettyName, @PathVariable Integer referralId) throws Exception {
    SubscriptionRankingByUser ranking = subscriptionService.retrieveRankingByUser(prettyName, referralId);
    return ResponseEntity.status(200).body(ranking);
  }
}
