package com.roommanagement.dto.response.room;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.roommanagement.valueoject.RoomStatus;
import lombok.Data;
import org.springframework.util.StringUtils;

@Data
public class SummaryRoomByStatusResponse {
  private int currentlyRenting;
  private int soonToBeReturn;
  private int roomAvailable;

  @Data
  public static class SummaryRoomByStatusQuery {
    @JsonIgnore
    private String status;
    private RoomStatus roomStatus;
    private int total;

    public RoomStatus getRoomStatus() {
      if (StringUtils.isEmpty(this.status)) {
        return null;
      }
      return RoomStatus.fromValue(this.status);
    }
  }
}
