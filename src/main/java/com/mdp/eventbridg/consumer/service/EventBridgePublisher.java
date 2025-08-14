package com.mdp.eventbridg.consumer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdp.eventbridg.consumer.config.EventBridgeProperties;
import com.mdp.eventbridg.consumer.model.event.EventRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.eventbridge.EventBridgeClient;
import software.amazon.awssdk.services.eventbridge.model.PutEventsRequest;
import software.amazon.awssdk.services.eventbridge.model.PutEventsRequestEntry;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventBridgePublisher {

  private final EventBridgeClient client;
  private final EventBridgeProperties props;
  private final ObjectMapper objectMapper = new ObjectMapper();

  public void publish(EventRequest event) {
    try {
      String detail = objectMapper.writeValueAsString(event.getDetail());

      String detailType = event.getDetailType() != null && !event.getDetailType().isEmpty() ? event.getDetailType() : "unknown";
      String source = event.getSource() != null && !event.getSource().isEmpty() ? event.getSource() : "unknown";

      PutEventsRequestEntry entry = PutEventsRequestEntry.builder()
          .eventBusName(props.getDestinationBus())
          .detailType(detailType)
          .source(source)
          .detail(detail)
          .build();

      PutEventsRequest req = PutEventsRequest.builder()
          .entries(entry)
          .build();

      var resp = client.putEvents(req);
      if (resp.failedEntryCount() > 0) {
        log.warn("Some EventBridge entries failed: {}", resp.failedEntryCount());
      } else {
        log.info("Event published to bus {}", props.getDestinationBus());
      }
    } catch (Exception e) {
      log.error("Error publishing to EventBridge: {}", e.getMessage(), e);
    }
  }
}
