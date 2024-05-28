/*
 *  Created by - Priyobrato.Das {priyo91@outlook.com}
 *  Date: 28/05/24, 6:45 pm
 *  Project: springboot-s3
 */

package io.spring.cloud.springboot_s3.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.services.sts.StsClient;

@Configuration
public class AwsConfig {

  @Bean
  public StsClient stsClient() {
    return StsClient.builder()
        .credentialsProvider(DefaultCredentialsProvider.create())
        .build();
  }
}
