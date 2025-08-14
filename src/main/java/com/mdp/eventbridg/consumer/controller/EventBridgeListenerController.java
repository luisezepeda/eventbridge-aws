package com.mdp.eventbridg.consumer.controller;

import com.mdp.eventbridg.consumer.model.event.EventRequest;
import com.mdp.eventbridg.consumer.service.EventBridgePublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventBridgeListenerController {

  private final EventBridgePublisher publisher;

  @PostMapping
  public ResponseEntity<Void> receive(@RequestBody EventRequest event) {
    log.debug("Evento recibido: {}", event);
    return ResponseEntity.accepted().build();
  }
}
