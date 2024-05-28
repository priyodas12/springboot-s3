/*
 *  Created by - Priyobrato.Das {priyo91@outlook.com}
 *  Date: 28/05/24, 6:49 pm
 *  Project: springboot-s3
 */

package io.spring.cloud.springboot_s3.service;

import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.CreateBucketResponse;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

@Log4j2
@Service
public class S3Service {


  private String awsDefaultRoleArn="assume_role_arn";

  private final AwsStsService awsStsService;
  private S3Client s3Client;

  @Autowired
  public S3Service(AwsStsService awsStsService) {
    this.awsStsService = awsStsService;
    initializeS3Client();
    //createBucket("test-bucket");
    listBuckets();
  }

  private void initializeS3Client() {

    String roleSessionName = "TestSession_"+ UUID.randomUUID().toString();

    var credentials = awsStsService.assumeRole(awsDefaultRoleArn, roleSessionName);

    log.debug("credentials:: {}", credentials);

    AwsSessionCredentials awsCreds = AwsSessionCredentials.create(
        credentials.accessKeyId(),
        credentials.secretAccessKey(),
        credentials.sessionToken());

    this.s3Client = S3Client.builder()
        .credentialsProvider(() -> awsCreds)
        .build();
  }

  public ListBucketsResponse listBuckets() {
    ListBucketsResponse listBucketsResponse= s3Client.listBuckets();
    log.info("listBucketsResponse: {}",listBucketsResponse);
    return listBucketsResponse;
  }

  public PutObjectResponse uploadObject(String bucketName, String key, String filePath) {
    PutObjectRequest putObjectRequest = PutObjectRequest.builder()
        .bucket(bucketName)
        .key(key)
        .build();

    PutObjectResponse putObjectResponse = s3Client.putObject(putObjectRequest, Paths.get(filePath));
    log.info("PutObjectResponse: " + putObjectResponse);
    return putObjectResponse;
  }

  public String createBucket(String bucketName) {


    CreateBucketRequest createBucketRequest = CreateBucketRequest.builder()
        .bucket(bucketName)
        .build();

    CreateBucketResponse createBucketResponse = s3Client.createBucket(createBucketRequest);
    log.info("createBucketResponse:: {}", createBucketResponse);
    return createBucketResponse.location();
  }

}
