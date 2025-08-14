package com.mdp.eventbridg.consumer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Detail {
  @NotEmpty
  @JsonProperty("eventName")
  private List<String> eventName;

  @NotNull
  @Valid
  @JsonProperty("headers")
  private Headers headers;

  @NotNull
  @Valid
  @JsonProperty("payload")
  private Payload payload;
}
