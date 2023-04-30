package com.roommanagement.controllers.lodger;

import com.roommanagement.dto.response.BaseResponseDto;
import com.roommanagement.dto.response.lodger.LodgerResponse;
import com.roommanagement.service.lodger.QueryLodgerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1")
public class QueryLodgerController {
  private final QueryLodgerService lodgerService;

  @GetMapping("/rooms/{roomId}/lodgers")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<BaseResponseDto<List<LodgerResponse>>> getLodgersByRoom(@PathVariable("roomId") Integer roomId) {
    return ResponseEntity.ok(lodgerService.getLodgersByRoom(roomId));
  }
}
