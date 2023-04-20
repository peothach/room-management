package com.roommanagement.service.room;

import com.roommanagement.dto.response.room.RoomResponse;
import com.roommanagement.dto.response.room.SummaryRoomByStatusResponse;

import java.util.List;

public interface QueryRoomService {
  SummaryRoomByStatusResponse getTotalRoomByStatus();

  List<RoomResponse> getRooms();

  List<RoomResponse> getRooms(String status);
}
