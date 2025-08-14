package com.mdp.eventbridg.consumer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payload {
  @NotNull
  @Valid
  @JsonProperty("client")
  private Client client;

  @NotNull
  @Valid
  @JsonProperty("contact")
  private Contact contact;
}
