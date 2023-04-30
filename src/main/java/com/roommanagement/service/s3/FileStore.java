package com.roommanagement.service.s3;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Service
public class FileStore {
  private final AmazonS3 amazonS3;

  public void upload(String path,
                     String fileName,
                     Optional<Map<String, String>> optionalMetaData,
                     InputStream inputStream) {
    ObjectMetadata objectMetadata = new ObjectMetadata();
    optionalMetaData.ifPresent(map -> {
      if (!map.isEmpty()) {
        map.forEach(objectMetadata::addUserMetadata);
      }
    });
    try {
      PutObjectRequest request  = new PutObjectRequest(path, fileName, inputStream, objectMetadata)
          .withCannedAcl(CannedAccessControlList.PublicRead);
      amazonS3.putObject(request);
    } catch (AmazonServiceException e) {
      throw new IllegalStateException("Failed to upload the file", e);
    }
  }

  public byte[] download(String path, String key) {
    try {
      S3Object object = amazonS3.getObject(path, key);
      S3ObjectInputStream objectContent = object.getObjectContent();
      return IOUtils.toByteArray(objectContent);
    } catch (AmazonServiceException | IOException e) {
      throw new IllegalStateException("Failed to download the file", e);
    }
  }

}
