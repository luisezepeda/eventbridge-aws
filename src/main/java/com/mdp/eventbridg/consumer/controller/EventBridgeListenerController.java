package com.mdp.eventbridg.consumer.controller;

import com.mdp.eventbridg.consumer.model.EventRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * HTTP endpoint intended to receive callbacks from EventBridge API destinations
 * or other producers. Currently logs the received event and acknowledges.
 * <p>
 * POST /events — accepts an {@code EventRequest} and returns 202 Accepted.
 */
@Slf4j
@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventBridgeListenerController {

    /**
     * Receives an event and acknowledges receipt.
     *
     * @param event incoming event payload
     * @return 202 Accepted to signal successful receipt
     */
    @PostMapping
    public ResponseEntity<Void> receive(@RequestBody EventRequest event) {
        log.debug("event received: {}", event);
        return ResponseEntity.accepted().build();
    }
}
