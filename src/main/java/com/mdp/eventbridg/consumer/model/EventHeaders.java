package com.mdp.eventbridg.consumer.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Header metadata inside event detail.
 * Contains the application identifier and a list of reference identifiers.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventHeaders {

    @JsonProperty("applicationId")
    private String applicationId;

    @Valid
    @JsonProperty("identifiers")
    private List<EventIdentifier> identifiers;
}
