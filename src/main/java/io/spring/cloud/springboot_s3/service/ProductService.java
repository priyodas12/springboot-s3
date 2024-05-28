/*
 *  Created by - Priyobrato.Das {priyo91@outlook.com}
 *  Date: 28/05/24, 6:48 pm
 *  Project: springboot-s3
 */

package io.spring.cloud.springboot_s3.service;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import org.springframework.stereotype.Service;

import io.spring.cloud.springboot_s3.model.Product;
import io.spring.cloud.springboot_s3.model.S3ProductRequest;
import io.spring.cloud.springboot_s3.model.S3ProductResponse;
import lombok.extern.log4j.Log4j2;
import software.amazon.awssdk.http.HttpStatusCode;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

@Log4j2
@Service
public class ProductService {

 File fileNameWithPath;
 String fileName;

 private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");


 private final S3Service s3Service;

 public ProductService(S3Service s3Service) {
  this.s3Service = s3Service;

 }

 private void createFilePath() {
  String timeStamp=LocalDateTime.now().format(dateTimeFormatter);
  log.info("timeStamp : {}",timeStamp);
  fileName = "product_report_" + timeStamp + ".csv";
  fileNameWithPath = new File(fileName);
 }


 private void writeCsv(List<Product> products) throws IOException {

  // create mapper and schema
  CsvMapper mapper = new CsvMapper();
  CsvSchema schema = mapper.schemaFor(Product.class).withHeader();
  schema = schema.withColumnSeparator('\t');

  // output writer
  ObjectWriter myObjectWriter = mapper.writer(schema);
  File tempFile = new File(String.valueOf(fileNameWithPath));
  FileOutputStream tempFileOutputStream = new FileOutputStream(tempFile);
  BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(tempFileOutputStream, 1024);
  OutputStreamWriter writerOutputStream = new OutputStreamWriter(bufferedOutputStream, "UTF-8");
  myObjectWriter.writeValue(writerOutputStream, products);
 }


 public S3ProductResponse archiveProduct(S3ProductRequest s3ProductRequest) {
  createFilePath();
  String s3BucketFileName = s3ProductRequest.getFilePath() + fileName;
  log.info("s3BucketFileName : {}, localfileNameWithPath; {}",s3BucketFileName,fileNameWithPath);
  try {
   writeCsv(s3ProductRequest.getProducts());
  } catch (IOException e) {
   throw new RuntimeException(e);
  }

  PutObjectResponse putObjectResponse = s3Service.uploadObject(s3ProductRequest.getBucketName(),
      s3BucketFileName,
      String.valueOf(fileNameWithPath));

  log.info("putObjectResponse : {}",putObjectResponse);
  return new S3ProductResponse(HttpStatusCode.CREATED, LocalDateTime.now(),
      s3ProductRequest.getProducts());
 }
}
