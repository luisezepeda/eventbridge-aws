package com.mdp.eventbridg.consumer.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "aws.credentials")
public class AwsCredentialsProperties {
  private String accessKey;
  private String secretKey;
  private String sessionToken; // opcional
}
