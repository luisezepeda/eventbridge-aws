package com.mdp.eventbridg.consumer.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.eventbridge.EventBridgeClient;

@Configuration
@RequiredArgsConstructor
public class EventBridgeConfig {

  private final EventBridgeProperties ebProps;
  private final AwsCredentialsProperties creds;

  @Bean
  public EventBridgeClient eventBridgeClient() {
    AwsCredentialsProvider provider = resolveCredentials(creds);
    return EventBridgeClient.builder()
        .region(Region.of(ebProps.getRegion()))
        .credentialsProvider(provider)
        .build();
  }

  private AwsCredentialsProvider resolveCredentials(AwsCredentialsProperties p) {
    if (p.getAccessKey() != null && !p.getAccessKey().isBlank()
        && p.getSecretKey() != null && !p.getSecretKey().isBlank()) {
      if (p.getSessionToken() != null && !p.getSessionToken().isBlank()) {
        return StaticCredentialsProvider.create(
            AwsSessionCredentials.create(p.getAccessKey(), p.getSecretKey(), p.getSessionToken()));
      }
      return StaticCredentialsProvider.create(
          AwsBasicCredentials.create(p.getAccessKey(), p.getSecretKey()));
    }
    return DefaultCredentialsProvider.create();
  }
}
