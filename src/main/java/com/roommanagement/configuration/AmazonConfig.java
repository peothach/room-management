package com.roommanagement.configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonConfig {
  @Bean
  public AmazonS3 s3() {
    AWSCredentials awsCredentials =
        new BasicAWSCredentials("AKIAQ7IAYY2QSN6KFWAJ", "II4qiXWCRwomoLu+qy8rTGpWLqz81jsqhC7hK8NW");
    return AmazonS3ClientBuilder
        .standard()
        .withRegion("ap-northeast-2")
        .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
        .build();

  }
}
