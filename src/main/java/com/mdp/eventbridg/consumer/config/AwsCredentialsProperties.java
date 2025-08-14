package com.mdp.eventbridg.consumer.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * AWS credential properties bound from configuration.
 * <p>
 * Prefix: {@code aws.credentials}
 * <br>
 * Supported properties:
 * <ul>
 *   <li>{@code aws.credentials.access-key} – AWS Access Key ID</li>
 *   <li>{@code aws.credentials.secret-key} – AWS Secret Access Key</li>
 *   <li>{@code aws.credentials.session-token} – AWS Session Token (only for temporary creds)</li>
 * </ul>
 *
 * Binding sources (in order of typical usage):
 * <ul>
 *   <li>application.yml / application.properties</li>
 *   <li>Environment variables (recommended): map to
 *     {@code AWS_ACCESS_KEY_ID}, {@code AWS_SECRET_ACCESS_KEY}, {@code AWS_SESSION_TOKEN}
 *     and reference them from config, e.g. {@code ${AWS_ACCESS_KEY_ID:}}</li>
 *   <li>Command-line args and Spring profiles</li>
 * </ul>
 *
 * Notes:
 * <ul>
 *   <li>If no values are provided, the application falls back to AWS SDK v2
 *       {@code DefaultCredentialsProvider} (EC2/ECS/EKS/Lambda role, SSO, or ~/.aws/credentials).</li>
 *   <li>Do not commit real secrets in source control. Prefer environment variables or AWS SSO/roles.</li>
 *   <li>{@code sessionToken} is required when using temporary credentials (STS/SSO/assumed roles).</li>
 * </ul>
 */
@Data
@ConfigurationProperties(prefix = "aws.credentials")
public class AwsCredentialsProperties {

    /** AWS Access Key ID. Example: AKIA... */
    private String accessKey;

    /** AWS Secret Access Key. Keep secret. */
    private String secretKey;

    /** Optional AWS Session Token for temporary credentials (STS/SSO). */
    private String sessionToken;
}
