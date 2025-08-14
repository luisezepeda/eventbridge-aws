package com.mdp.eventbridg.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@Slf4j
@SpringBootApplication
@ConfigurationPropertiesScan
public class MdpEventbridgeServiceApplication {
  public static void main(String[] args) {
    SpringApplication.run(MdpEventbridgeServiceApplication.class, args);
    log.info("MDP EventBridge Service started");
  }
}
