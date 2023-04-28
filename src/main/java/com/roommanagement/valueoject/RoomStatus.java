package com.roommanagement.valueoject;

import lombok.Data;

import java.util.Arrays;

public enum RoomStatus {
  CurrentlyRenting("Đang cho thuê"),
  SoonToBeReturn("Sắp trả"),
  RoomAvailable("Phòng trống"),
  Inactive("Phòng đã xóa");

  private final String value;

  RoomStatus(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public static RoomStatus fromValue(String value) {
    return Arrays.stream(RoomStatus.values())
        .filter(i -> i.getValue().equals(value))
        .findFirst()
        .orElse(null);
  }
}
