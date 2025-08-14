package com.mdp.eventbridg.consumer.controller;

import com.mdp.eventbridg.consumer.model.EventRequest;
import com.mdp.eventbridg.consumer.service.EventBridgePublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * HTTP endpoint to publish events to AWS EventBridge.
 * <p>
 * POST /events/put — accepts an {@code EventRequest} payload and enqueues it to the
 * configured destination EventBridge bus. Returns 202 Accepted on success.
 */
@Slf4j
@RestController
@RequestMapping("/events/put")
@RequiredArgsConstructor
public class EventBridgePutController {

    /** Service to publish events to EventBridge. */
    private final EventBridgePublisher eventBridgePublisher;

    /**
     * Publishes the received event to EventBridge.
     *
     * @param event the event payload to forward
     * @return 202 Accepted if the request was queued for publishing
     */
    @PostMapping
    public ResponseEntity<Void> publish(@RequestBody EventRequest event) {

        log.debug("Event to publish: {}  ", event);

        eventBridgePublisher.publish(event);

        return ResponseEntity.accepted().build();
    }
}
