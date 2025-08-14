package com.mdp.eventbridg.consumer.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Business payload containing customer-related data sections.
 * Currently includes {@code client} and {@code contact} objects.
 */
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
