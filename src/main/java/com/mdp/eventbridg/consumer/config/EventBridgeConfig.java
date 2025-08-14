package com.mdp.eventbridg.consumer.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.eventbridge.EventBridgeClient;

/**
 * Spring configuration for AWS EventBridge client.
 * <p>
 * Responsibilities:
 * <ul>
 *   <li>Creates a singleton {@link EventBridgeClient} bean configured with the desired AWS region.</li>
 *   <li>Resolves AWS credentials in the following order:
 *     <ol>
 *       <li>If {@link AwsCredentialsProperties} includes accessKey/secretKey, uses a static provider.</li>
 *       <li>If a sessionToken is also present, uses session credentials (temporary creds).</li>
 *       <li>Otherwise falls back to {@link DefaultCredentialsProvider} (instance role, SSO, profiles, env).</li>
 *     </ol>
 *   </li>
 * </ul>
 * Best practices:
 * <ul>
 *   <li>Prefer DefaultCredentialsProvider in production (roles/SSO) instead of static keys.</li>
 *   <li>Do not commit real secrets; supply via environment variables or AWS profiles.</li>
 *   <li>Ensure {@code eventbridge.region} is aligned with your EventBridge bus/targets.</li>
 * </ul>
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class EventBridgeConfig {

    /**
     * Properties for configuring the EventBridge client.
     */
    private final EventBridgeProperties eventBridgeProperties;

    /**
     * Properties for AWS credentials, bound from configuration.
     */
    private final AwsCredentialsProperties awsCredentialsProperties;

    /**
    * Builds the AWS {@link EventBridgeClient} with region and credentials provider.
    */
    @Bean
    public EventBridgeClient eventBridgeClient() {
    AwsCredentialsProvider provider = resolveCredentials(awsCredentialsProperties);
    return EventBridgeClient.builder()
        .region(Region.of(eventBridgeProperties.getRegion()))
        .credentialsProvider(provider)
        .build();
    }

    /**
    * Resolves the credentials provider based on configured static keys or default provider chain.
    *
    * @param awsCredentialsProperties properties bound from configuration (may be empty)
    * @return credentials provider for the EventBridge client
    */
    private AwsCredentialsProvider resolveCredentials(AwsCredentialsProperties awsCredentialsProperties) {

    log.debug("AWS Credentials :: {}", awsCredentialsProperties);

    if (awsCredentialsProperties.getAccessKey() != null && !awsCredentialsProperties.getAccessKey().isBlank()
        && awsCredentialsProperties.getSecretKey() != null && !awsCredentialsProperties.getSecretKey().isBlank()) {
      if (awsCredentialsProperties.getSessionToken() != null && !awsCredentialsProperties.getSessionToken().isBlank()) {
        return StaticCredentialsProvider.create(
            AwsSessionCredentials.create(awsCredentialsProperties.getAccessKey(), awsCredentialsProperties.getSecretKey(), awsCredentialsProperties.getSessionToken()));
      }
      return StaticCredentialsProvider.create(
          AwsBasicCredentials.create(awsCredentialsProperties.getAccessKey(), awsCredentialsProperties.getSecretKey()));
    }
    return DefaultCredentialsProvider.create();
    }
}
