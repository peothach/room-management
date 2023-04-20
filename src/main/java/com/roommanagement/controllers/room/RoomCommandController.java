package com.roommanagement.controllers.room;

import com.roommanagement.dto.request.room.CreateRoomRequestDto;
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
  public ResponseEntity<Void> createRoom(@RequestBody CreateRoomRequestDto roomRequestDto, @RequestHeader(value = "quantity") int quantity) {
    commandRoomService.createRoom(roomRequestDto, quantity);
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .build();
  }

  @PatchMapping("/rooms/{id}")
  public ResponseEntity<Void> updateRoom(@RequestBody CreateRoomRequestDto roomRequestDto, @PathVariable("id") long roomId) {
    commandRoomService.updateRoom(roomRequestDto, roomId);
    return ResponseEntity
        .status(HttpStatus.NO_CONTENT)
        .build();
  }

  @DeleteMapping("/rooms/{id}")
  public ResponseEntity<Void> deleteRoom(@PathVariable("id") long id) {
    commandRoomService.deleteRoom(id);
    return ResponseEntity
        .status(HttpStatus.NO_CONTENT)
        .build();
  }
}
