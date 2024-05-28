/*
 *  Created by - Priyobrato.Das {priyo91@outlook.com}
 *  Date: 28/05/24, 6:46 pm
 *  Project: springboot-s3
 */

package io.spring.cloud.springboot_s3.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
@JsonPropertyOrder({"productId", "productName", "origin","price","quantity"})
public class Product implements Serializable {

  private UUID productId;
  private String productName;
  private String origin;
  private BigDecimal price;
  private Long quantity;
}
