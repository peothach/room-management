package com.roommanagement.controllers.lodger;

import com.roommanagement.dto.request.lodger.LodgerRequest;
import com.roommanagement.dto.response.BaseResponseDto;
import com.roommanagement.service.lodger.CommandLodgerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1")
public class CommandLodgerController {
  private final CommandLodgerService lodgerService;

  @PostMapping("rooms/{roomId}/lodgers")
  public ResponseEntity<BaseResponseDto<?>> createLodger(
      @PathVariable("roomId") Integer roomId,
      @RequestParam("front") MultipartFile front,
      @RequestParam("back") MultipartFile back,
      @RequestParam("name") String name,
      @RequestParam("email") String email,
      @RequestParam("phoneNumber") String phoneNumber
  ) {
    LodgerRequest lodgerRequest = new LodgerRequest(front, back, name, email, phoneNumber);
    return ResponseEntity.ok(lodgerService.save(roomId, lodgerRequest));
  }

  @PatchMapping("rooms/{roomId}/lodgers/{lodgerId}")
  public ResponseEntity<BaseResponseDto<?>> updateLodger(
      @PathVariable("lodgerId") Integer lodgerId,
      @RequestParam("front") MultipartFile front,
      @RequestParam("back") MultipartFile back,
      @RequestParam("name") String name,
      @RequestParam("email") String email,
      @RequestParam("phoneNumber") String phoneNumber
  ) {
    LodgerRequest lodgerRequest = new LodgerRequest(front, back, name, email, phoneNumber);
    return ResponseEntity.ok(lodgerService.update(lodgerId, lodgerRequest));
  }

  @DeleteMapping("rooms/{roomId}/lodgers/{lodgerId}")
  public ResponseEntity<BaseResponseDto<?>> deleteLodger(@PathVariable("lodgerId") Integer lodgerId) {
    return ResponseEntity.ok(lodgerService.delete(lodgerId));
  }
}
