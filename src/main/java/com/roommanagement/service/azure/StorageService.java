package com.roommanagement.service.azure;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class StorageService {
  private final BlobServiceClient blobServiceClient;
  private static final String containerName = "images";

  public String uploadImage(InputStream imageStream, String imageName, long imageSize) {
    BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
    BlobClient blobClient = containerClient.getBlobClient(imageName);
    blobClient.upload(imageStream, imageSize, true);
    return blobClient.getBlobUrl();
  }
}
