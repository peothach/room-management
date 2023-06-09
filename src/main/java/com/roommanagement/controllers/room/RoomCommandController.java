package com.roommanagement.controllers.room;

import com.roommanagement.dto.request.room.CreateRoomRequestDto;
import com.roommanagement.dto.request.room.UpdateExpenseRequest;
import com.roommanagement.dto.response.BaseResponseDto;
import com.roommanagement.service.room.CommandRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class RoomCommandController {

  private final CommandRoomService commandRoomService;

  @PostMapping("/rooms")
  public ResponseEntity<BaseResponseDto<?>> createRoom(@RequestBody CreateRoomRequestDto roomRequestDto, @RequestHeader(value = "quantity") int quantity) {
    commandRoomService.createRoom(roomRequestDto, quantity);
    return ResponseEntity.ok(
        new BaseResponseDto<>(HttpStatus.CREATED.value(), HttpStatus.CREATED.getReasonPhrase())
    );
  }

  @PatchMapping("/rooms/{id}")
  public ResponseEntity<BaseResponseDto<?>> updateRoom(@RequestBody CreateRoomRequestDto roomRequestDto, @PathVariable("id") Integer roomId) {
    commandRoomService.updateRoom(roomRequestDto, roomId);
    return ResponseEntity.ok(
        new BaseResponseDto<>(HttpStatus.NO_CONTENT.value(), HttpStatus.NO_CONTENT.getReasonPhrase())
    );
  }

  @PatchMapping("/rooms/{id}/rollback")
  public ResponseEntity<BaseResponseDto<?>> rollbackRoom(@PathVariable("id") Integer roomId) {
    commandRoomService.rollbackRoom(roomId);
    return ResponseEntity.ok(
        new BaseResponseDto<>(HttpStatus.NO_CONTENT.value(), HttpStatus.NO_CONTENT.getReasonPhrase())
    );
  }

  @DeleteMapping("/rooms/{id}")
  public ResponseEntity<BaseResponseDto<?>> deleteRoom(@PathVariable("id") Integer id) {
    commandRoomService.deleteRoom(id);
    return ResponseEntity.ok(
        new BaseResponseDto<>(HttpStatus.NO_CONTENT.value(), HttpStatus.NO_CONTENT.getReasonPhrase())
    );
  }

  @PatchMapping("/rooms/{roomId}/expenses/{expenseId}")
  public ResponseEntity<BaseResponseDto<?>> updateExpense(
      @PathVariable("roomId") Integer roomId,
      @PathVariable("expenseId") Integer expenseId,
      @RequestBody UpdateExpenseRequest updateExpenseRequest
      ) {
    commandRoomService.updateExpense(roomId, expenseId, updateExpenseRequest);
    return ResponseEntity.ok(
        new BaseResponseDto<>(HttpStatus.NO_CONTENT.value(), HttpStatus.NO_CONTENT.getReasonPhrase())
    );
  }
}
