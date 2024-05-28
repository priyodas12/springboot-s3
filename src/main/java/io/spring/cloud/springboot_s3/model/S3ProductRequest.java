/*
 *  Created by - Priyobrato.Das {priyo91@outlook.com}
 *  Date: 28/05/24, 6:46 pm
 *  Project: springboot-s3
 */

package io.spring.cloud.springboot_s3.model;


import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class S3ProductRequest {

  private String bucketName;

  private String filePath;

  private List<Product> products;
}
