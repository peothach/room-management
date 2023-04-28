package com.roommanagement.controllers.room;

import com.roommanagement.dto.response.BaseResponseDto;
import com.roommanagement.dto.response.expense.ExpenseResponse;
import com.roommanagement.dto.response.room.RoomResponse;
import com.roommanagement.dto.response.room.SummaryRoomByStatusResponse;
import com.roommanagement.service.room.QueryRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class RoomQueryController {
  private final QueryRoomService queryService;

  @GetMapping("/rooms/summary-room")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<BaseResponseDto<SummaryRoomByStatusResponse>> getSummaryRoom() {
    return ResponseEntity.ok(queryService.getTotalRoomByStatus());
  }

  @GetMapping("/rooms")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<BaseResponseDto<List<RoomResponse>>> getRooms(@RequestParam(value = "status", required = false) Optional<String> roomStatus) {
    return roomStatus
        .map(status -> ResponseEntity.ok(queryService.getRooms(status)))
        .orElseGet(() -> ResponseEntity.ok(queryService.getRooms()));
  }

  @GetMapping("/rooms/{roomId}/expenses")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<BaseResponseDto<List<ExpenseResponse>>> getExpensesByRoom(@PathVariable("roomId") Integer roomId) {
    return ResponseEntity.ok(queryService.getExpensesByRoom(roomId));
  }
}
