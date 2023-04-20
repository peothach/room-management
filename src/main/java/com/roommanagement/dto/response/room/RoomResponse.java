package com.roommanagement.dto.response.room;

import lombok.Data;

@Data
public class RoomResponse {
  private long id;
  private String name;
  private String status;
}
