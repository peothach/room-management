package com.roommanagement.valueoject;

import lombok.Data;

import java.util.Arrays;

public enum RoomStatus {
  CurrentlyRenting("Đang cho thuê","C"),
  SoonToBeReturn("Sắp trả","S"),
  RoomAvailable("Phòng trống","R"),
  Inactive("Phòng đã xóa","I");

  private final String value;
  private final String description;

  RoomStatus(String value, String description) {
    this.value = value;
    this.description = description;
  }

  public String getValue() {
    return value;
  }

  public String getDescription() {
    return description;
  }

  public static RoomStatus fromValue(String value) {
    return Arrays.stream(RoomStatus.values())
        .filter(i -> i.getValue().equals(value))
        .findFirst()
        .orElse(null);
  }
}
