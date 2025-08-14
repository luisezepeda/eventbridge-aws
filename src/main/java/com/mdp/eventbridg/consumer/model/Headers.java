package com.mdp.eventbridg.consumer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Headers {
  @NotBlank
  @JsonProperty("applicationId")
  private String applicationId;

  @NotNull
  @Valid
  @JsonProperty("identifiers")
  private List<Identifier> identifiers;
}
