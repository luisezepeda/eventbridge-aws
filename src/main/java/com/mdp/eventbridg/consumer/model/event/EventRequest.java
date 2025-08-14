package com.mdp.eventbridg.consumer.model.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventRequest {

  @JsonProperty("version")
  private String version;

  @JsonProperty("id")
  private String id;

  @JsonProperty("detail-type")
  private String detailType;

  @JsonProperty("source")
  private String source;

  @JsonProperty("account")
  private String account;

  @JsonProperty("time")
  private String time;

  @JsonProperty("region")
  private String region;

  @JsonProperty("resources")
  private List<String> resources;

  @Valid
  @JsonProperty("detail")
  private EventDetail detail;
}
