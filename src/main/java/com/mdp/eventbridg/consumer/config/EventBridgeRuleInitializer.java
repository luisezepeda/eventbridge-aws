package com.mdp.eventbridg.consumer.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.eventbridge.EventBridgeClient;
import software.amazon.awssdk.services.eventbridge.model.*;

import java.util.List;

/**
 * Initializes (optionally) an EventBridge rule and target on application startup.
 * <p>
 * Behavior:
 * <ul>
 *   <li>Runs once after bean construction ({@link PostConstruct}).</li>
 *   <li>Only executes if {@code eventbridge.create-rule=true}.</li>
 *   <li>Creates/updates a rule on {@code eventbridge.source-bus} with the configured JSON pattern.</li>
 *   <li>If {@code eventbridge.destination-bus-arn} is provided, attaches it as a target to forward events.</li>
 * </ul>
 * Required configuration (see {@link EventBridgeProperties}): region, sourceBus, ruleName, eventPattern; destinationBusArn optional.
 * <p>
 * Required IAM permissions (identity-based on the principal running the app):
 * <ul>
 *   <li>events:PutRule on arn:aws:events:{region}:{account}:rule/{source-bus}/{rule-name}</li>
 *   <li>events:PutTargets on the same rule ARN (if attaching targets)</li>
 *   <li>Optionally events:DescribeRule for diagnostics</li>
 * </ul>
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class EventBridgeRuleInitializer {

    /** AWS EventBridge client. */
    private final EventBridgeClient eventBridgeClient;

    /** Configuration properties for EventBridge integration. */
    private final EventBridgeProperties eventBridgeProperties;

    /**
     * Lifecycle hook executed once after bean construction.
     * <p>
     * If {@code eventbridge.create-rule=true}, this method:
     * <ol>
     *   <li>Calls {@code PutRule} on {@code eventbridge.source-bus} with the provided {@code event-pattern}
     *       and {@code rule-name}, enabling the rule.</li>
     *   <li>If {@code eventbridge.destination-bus-arn} is set, calls {@code PutTargets} to forward
     *       matched events to that destination bus.</li>
     * </ol>
     * Required IAM: {@code events:PutRule} and (optionally) {@code events:PutTargets} on the rule ARN.
     * If {@code eventbridge.create-rule=false}, the method returns without changes.
     */
    @PostConstruct
    public void init() {

        if (Boolean.FALSE.equals(eventBridgeProperties.getCreateRule())) {
            log.info("EventBridge rule creation is disabled (eventbridge.create-rule=false)");
            return;
        }

        try {

            PutRuleRequest putRuleRequest = PutRuleRequest.builder()
                    .name(eventBridgeProperties.getRuleName())
                    .eventBusName(eventBridgeProperties.getSourceBus())
                    .eventPattern(eventBridgeProperties.getEventPattern())
                    .state(RuleState.ENABLED)
                    .build();

            PutRuleResponse putRuleResponse = eventBridgeClient.putRule(putRuleRequest);
            log.debug("PutRule success. RuleArn={}", putRuleResponse.ruleArn());

            final String destinationBusArn = eventBridgeProperties.getDestinationBusArn();

            if (!StringUtils.isBlank(destinationBusArn)) {

                Target target = Target.builder()
                        .id("forward-to-destination-bus")
                        .arn(destinationBusArn)
                        .build();

                PutTargetsRequest putTargetsRequest = PutTargetsRequest.builder()
                        .eventBusName(eventBridgeProperties.getSourceBus())
                        .rule(eventBridgeProperties.getRuleName())
                        .targets(List.of(target))
                        .build();

                PutTargetsResponse putTargetsResponse = eventBridgeClient.putTargets(putTargetsRequest);

                if (putTargetsResponse.failedEntryCount() != 0) {
                    log.warn("PutTargets had failures: {}", putTargetsResponse.failedEntryCount());
                } else {
                    log.info("PutTargets success: forwarding to {}", destinationBusArn);
                }

            } else {
                log.info("No destinationBusArn configured; skipping PutTargets.");
            }

        } catch (EventBridgeException ex) {
            log.error("Error initializing EventBridge rule/targets: {}", ex.getMessage(), ex);
        }
    }
}
