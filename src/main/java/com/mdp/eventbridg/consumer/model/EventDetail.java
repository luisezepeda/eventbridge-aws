package com.mdp.eventbridg.consumer.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Detail section of the EventBridge event, containing business fields.
 * Includes a semantic {@code eventName}, {@code headers} with metadata, and the {@code payload}.
 */
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
