package com.mdp.eventbridg.consumer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Identifier {
  @NotBlank
  @JsonProperty("code")
  private String code;

  @NotBlank
  @JsonProperty("description")
  private String description;

  @NotBlank
  @JsonProperty("value")
  private String value;
}
