package com.mdp.eventbridg.consumer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Client {
  @NotBlank
  @JsonProperty("name")
  private String name;
}
