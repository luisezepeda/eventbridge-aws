package com.mdp.eventbridg.consumer.model.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventDetail {

  @JsonProperty("eventName")
  private String eventName;

  @Valid
  @JsonProperty("headers")
  private EventHeaders headers;

  @Valid
  @JsonProperty("payload")
  private EventPayload payload;
}
