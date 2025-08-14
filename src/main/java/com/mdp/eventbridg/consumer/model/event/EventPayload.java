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
public class EventPayload {

  @Valid
  @JsonProperty("client")
  private EventPayloadClient client;

  @Valid
  @JsonProperty("contact")
  private EventPayloadContact contact;
}
