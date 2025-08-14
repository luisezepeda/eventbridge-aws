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
@RequestMapping("/events/put")
@RequiredArgsConstructor
public class EventBridgePutController {

  private final EventBridgePublisher publisher;

  @PostMapping
  public ResponseEntity<Void> publish(@RequestBody EventRequest event) {

    log.debug("Evento a publicar: {}  ", event);

    publisher.publish(event);

    return ResponseEntity.accepted().build();
  }
}
