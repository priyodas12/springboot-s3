/*
 *  Created by - Priyobrato.Das {priyo91@outlook.com}
 *  Date: 28/05/24, 6:46 pm
 *  Project: springboot-s3
 */

package io.spring.cloud.springboot_s3.service;


import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;
import software.amazon.awssdk.services.sts.StsClient;
import software.amazon.awssdk.services.sts.model.AssumeRoleRequest;
import software.amazon.awssdk.services.sts.model.AssumeRoleResponse;
import software.amazon.awssdk.services.sts.model.Credentials;

@Log4j2
@Service
public class AwsStsService {

  private final StsClient stsClient;

  public AwsStsService(StsClient stsClient) {
    this.stsClient = stsClient;
  }

  public Credentials assumeRole(String roleArn, String roleSessionName) {
    AssumeRoleRequest assumeRoleRequest = AssumeRoleRequest.builder()
        .roleArn(roleArn)
        .roleSessionName(roleSessionName)
        .build();

    AssumeRoleResponse assumeRoleResponse = stsClient.assumeRole(assumeRoleRequest);
    log.debug("AssumeRoleResponse:: {}", assumeRoleResponse);
    return assumeRoleResponse.credentials();
  }
}
