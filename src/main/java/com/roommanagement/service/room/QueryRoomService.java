package com.roommanagement.service.room;

import com.roommanagement.dto.response.BaseResponseDto;
import com.roommanagement.dto.response.expense.ExpenseResponse;
import com.roommanagement.dto.response.room.RoomResponse;
import com.roommanagement.dto.response.room.SummaryRoomByStatusResponse;

import java.util.List;

public interface QueryRoomService {
  BaseResponseDto<SummaryRoomByStatusResponse> getTotalRoomByStatus();

  BaseResponseDto<List<RoomResponse>> getRooms();

  BaseResponseDto<List<RoomResponse>> getRooms(String status);

  BaseResponseDto<List<ExpenseResponse>> getExpensesByRoom(Integer roomId);
}
