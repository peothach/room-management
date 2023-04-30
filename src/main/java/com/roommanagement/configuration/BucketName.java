package com.roommanagement.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BucketName {
  CMND_IMAGE("cmnd-room-mangement");
  private final String bucketName;
}
