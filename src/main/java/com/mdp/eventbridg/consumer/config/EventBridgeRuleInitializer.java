package com.mdp.eventbridg.consumer.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.eventbridge.EventBridgeClient;
import software.amazon.awssdk.services.eventbridge.model.*;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventBridgeRuleInitializer {

  private final EventBridgeClient client;
  private final EventBridgeProperties props;

  @PostConstruct
  public void init() {
    if (!props.isCreateRule()) {
      log.info("EventBridge rule creation is disabled (eventbridge.create-rule=false)");
      return;
    }
    try {
      PutRuleRequest putRule = PutRuleRequest.builder()
          .name(props.getRuleName())
          .eventBusName(props.getSourceBus())
          .eventPattern(props.getEventPattern())
          .state(RuleState.ENABLED)
          .build();
      PutRuleResponse pr = client.putRule(putRule);
      log.info("PutRule ok. RuleArn={}", pr.ruleArn());

      if (props.getDestinationBusArn() != null && !props.getDestinationBusArn().isBlank()) {
        Target target = Target.builder()
            .id("forward-to-destination-bus")
            .arn(props.getDestinationBusArn())
            .build();

        PutTargetsRequest putTargets = PutTargetsRequest.builder()
            .eventBusName(props.getSourceBus())
            .rule(props.getRuleName())
            .targets(List.of(target))
            .build();
        PutTargetsResponse tr = client.putTargets(putTargets);
        if (tr.failedEntryCount() != 0) {
          log.warn("PutTargets had failures: {}", tr.failedEntryCount());
        } else {
          log.info("PutTargets ok: forwarding to {}", props.getDestinationBusArn());
        }
      } else {
        log.info("No destinationBusArn configured; skipping PutTargets.");
      }
    } catch (EventBridgeException ex) {
      log.error("Error initializing EventBridge rule/targets: {}", ex.getMessage(), ex);
    }
  }
}
