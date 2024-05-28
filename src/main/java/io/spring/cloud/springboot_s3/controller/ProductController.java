/*
 *  Created by - Priyobrato.Das {priyo91@outlook.com}
 *  Date: 28/05/24, 6:46 pm
 *  Project: springboot-s3
 */

package io.spring.cloud.springboot_s3.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.spring.cloud.springboot_s3.model.S3ProductRequest;
import io.spring.cloud.springboot_s3.model.S3ProductResponse;
import io.spring.cloud.springboot_s3.service.ProductService;

@RestController
@RequestMapping("/api/v2/")
public class ProductController {

  @Autowired
  private ProductService productService;

  @PostMapping("/product")
  public ResponseEntity<S3ProductResponse> archiveProduct(@RequestBody S3ProductRequest productRequest){
    return ResponseEntity.ok(productService.archiveProduct(productRequest));
  }
}
