package com.mdp.eventbridg.consumer.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * EventBridge related application properties.
 * <p>
 * Prefix: {@code eventbridge}
 * <br>
 * These properties configure the region, event buses, and optional rule initialization.
 *
 * <pre>
 * eventbridge:
 *   region: us-east-2
 *   source-bus: source-bus
 *   destination-bus: destination-bus
 *   destination-bus-arn: arn:aws:events:us-east-2:nnnnnnnnnnnn:event-bus/visit-bus
 *   rule-name: rule-events
 *   create-rule: false
 *   event-pattern: |
 *     { "source": ["my-app"], "detail-type": ["VisitRequest"] }
 * </pre>
 */
@Data
@ConfigurationProperties(prefix = "eventbridge")
public class EventBridgeProperties {

  /** AWS region where EventBridge resources live, e.g. "us-east-2". */
  private String region;

  /** Name of the source event bus to attach rules to (when createRule = true). */
  private String sourceBus;

  /** Name of the destination bus where events will be published by the service. */
  private String destinationBus;

  /**
   * Optional ARN of the destination bus to be configured as a rule target
   * when initializing via {@code createRule=true}.
   */
  private String destinationBusArn;

  /** Name of the EventBridge rule to create/update when createRule is enabled. */
  private String ruleName;

  /** Whether to auto-create/update the EventBridge rule on startup. Default false. */
  private Boolean createRule = false;

  /** JSON event pattern for the rule (only used if createRule = true). */
  private String eventPattern;
}
