package com.roommanagement.service.room;

import com.roommanagement.dto.request.room.CreateRoomRequestDto;

public interface CommandRoomService {
  void createRoom(CreateRoomRequestDto roomRequestDto, int quantity);
  void updateRoom(CreateRoomRequestDto roomRequestDto, long roomId);

  void deleteRoom(long roomId);
  void rollbackRoom(long roomId);
}
