package com.roommanagement.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BucketName {
  CMND_IMAGE("room-management-image");
  private final String bucketName;
}
