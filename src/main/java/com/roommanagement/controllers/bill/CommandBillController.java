package com.roommanagement.controllers.bill;

import com.roommanagement.dto.request.bill.BillRequest;
import com.roommanagement.dto.response.BaseResponseDto;
import com.roommanagement.service.bill.CommandBillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1")
public class CommandBillController {
  private final CommandBillService billService;

  @PostMapping("bills")
  public ResponseEntity<BaseResponseDto<?>> createBill(@RequestBody BillRequest billRequest) {
    return ResponseEntity.ok(billService.createBill(billRequest));
  }
}
