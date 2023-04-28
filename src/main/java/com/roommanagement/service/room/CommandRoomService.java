package com.roommanagement.service.room;

import com.roommanagement.dto.request.room.CreateRoomRequestDto;
import com.roommanagement.dto.request.room.UpdateExpenseRequest;

public interface CommandRoomService {
  void createRoom(CreateRoomRequestDto roomRequestDto, int quantity);
  void updateRoom(CreateRoomRequestDto roomRequestDto, long roomId);

  void deleteRoom(long roomId);
  void rollbackRoom(long roomId);
  void updateExpense(Long roomId, Integer expenseId, UpdateExpenseRequest updateExpenseRequest);
}
