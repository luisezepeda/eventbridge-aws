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
public class KafkaMessage {
  @NotEmpty
  @JsonProperty("detail-type")
  private List<String> detailType;

  @NotEmpty
  @JsonProperty("source")
  private List<String> source;

  @NotNull
  @Valid
  @JsonProperty("detail")
  private Detail detail;
}
