package com.roommanagement.configuration;

import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AzureBlobStorageConfig {

  @Value("${azure.storage.account-name}")
  private String accountName;
  @Value("${azure.storage.account-key}")
  private String accountKey;
  @Value("${azure.storage.connection-string}")
  private String connectionString;

  @Bean
  public BlobServiceClient blobServiceClient() {
//    String connectionString = String.format("DefaultEndpointsProtocol=https;AccountName=%s;AccountKey=%s", accountName, accountKey);
    return new BlobServiceClientBuilder().connectionString(connectionString).buildClient();
  }
}
