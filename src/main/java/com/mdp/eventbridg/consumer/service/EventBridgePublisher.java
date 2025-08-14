package com.mdp.eventbridg.consumer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdp.eventbridg.consumer.config.EventBridgeProperties;
import com.mdp.eventbridg.consumer.model.EventRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.eventbridge.EventBridgeClient;
import software.amazon.awssdk.services.eventbridge.model.PutEventsRequest;
import software.amazon.awssdk.services.eventbridge.model.PutEventsRequestEntry;
import software.amazon.awssdk.services.eventbridge.model.PutEventsResponse;

/**
 * Publishes events to AWS EventBridge using the configured destination bus.
 * <p>
 * Serializes the incoming {@code EventRequest.detail} to JSON and sends it with
 * the provided {@code detail-type} and {@code source}. Falls back to "unknown"
 * if those fields are empty. Logs failures and successes.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EventBridgePublisher {

    /** The EventBridge client used to send events. */
    private final EventBridgeClient eventBridgeClient;

    /** The properties containing configuration for EventBridge. */
    private final EventBridgeProperties eventBridgeProperties;

    /**
     * Publishes a single event to EventBridge.
     * <p>
     * Input contract:
     * - event.detail: required, will be serialized as the event "detail" JSON body
     * - event.detailType: optional, defaults to "unknown" if blank
     * - event.source: optional, defaults to "unknown" if blank
     * <p>
     * Behavior:
     * - Builds a PutEventsRequest targeting {@code props.destinationBus}.
     * - Invokes EventBridge {@code putEvents} and logs the result.
     *
     * @param event event envelope to publish
     */
    public void publish(EventRequest event) {

        final ObjectMapper objectMapper = new ObjectMapper();

        try {

            String eventDetail = objectMapper.writeValueAsString(event.getDetail());
            String eventDetailType = event.getDetailType();
            String eventSource = event.getSource();

            String detailType = !StringUtils.isBlank(eventDetailType) ? eventDetailType : "unknown";
            String source = !StringUtils.isBlank(eventSource) ? eventSource : "unknown";

            PutEventsRequestEntry putEventsRequestEntry = PutEventsRequestEntry.builder()
                    .eventBusName(eventBridgeProperties.getDestinationBus())
                    .detailType(detailType)
                    .source(source)
                    .detail(eventDetail)
                    .build();

            PutEventsRequest putEventsRequest = PutEventsRequest.builder()
                    .entries(putEventsRequestEntry)
                    .build();

            PutEventsResponse putEventsResponse = eventBridgeClient.putEvents(putEventsRequest);

            if (putEventsResponse.failedEntryCount() > 0) {
                log.warn("Some EventBridge entries failed: {}", putEventsResponse.failedEntryCount());
            } else {
                log.info("Event published to bus {}", eventBridgeProperties.getDestinationBus());
            }

        } catch (Exception e) {
            log.error("Error publishing to EventBridge: {}", e.getMessage(), e);
        }
    }
}
