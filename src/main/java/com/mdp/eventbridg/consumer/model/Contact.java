package com.mdp.eventbridg.consumer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contact {
  @NotBlank
  @Pattern(regexp = "^\\d{10,15}$")
  @JsonProperty("number")
  private String number;
}
