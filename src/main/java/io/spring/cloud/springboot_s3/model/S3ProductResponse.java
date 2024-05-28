/*
 *  Created by - Priyobrato.Das {priyo91@outlook.com}
 *  Date: 28/05/24, 6:46 pm
 *  Project: springboot-s3
 */

package io.spring.cloud.springboot_s3.model;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;


@Data
@ToString
@AllArgsConstructor
public class S3ProductResponse {

  private int httpStatusCode;

  private LocalDateTime localDateTime;

  private List<Product> products;

}
