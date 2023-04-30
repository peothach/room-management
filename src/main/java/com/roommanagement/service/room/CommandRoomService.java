package com.roommanagement.service.room;

import com.roommanagement.dto.request.room.CreateRoomRequestDto;
import com.roommanagement.dto.request.room.UpdateExpenseRequest;

public interface CommandRoomService {
  void createRoom(CreateRoomRequestDto roomRequestDto, int quantity);
  void updateRoom(CreateRoomRequestDto roomRequestDto, Integer roomId);

  void deleteRoom(Integer roomId);
  void rollbackRoom(Integer roomId);
  void updateExpense(Integer roomId, Integer expenseId, UpdateExpenseRequest updateExpenseRequest);
}
