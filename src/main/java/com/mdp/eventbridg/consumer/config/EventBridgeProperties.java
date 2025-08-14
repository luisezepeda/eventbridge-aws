package com.mdp.eventbridg.consumer.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "eventbridge")
public class EventBridgeProperties {
  private String region;
  private String sourceBus;
  private String destinationBus;
  private String destinationBusArn; // opcional para auto-target
  private String ruleName;
  private boolean createRule = false;
  private String eventPattern; // JSON
}
