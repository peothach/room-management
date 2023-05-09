package com.roommanagement.controllers.bill;

import com.roommanagement.dto.response.BaseResponseDto;
import com.roommanagement.service.bill.QueryBillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Month;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1")
public class QueryBillController {
  private final QueryBillService queryService;

  @GetMapping("/bills")
  public ResponseEntity<BaseResponseDto<?>> retrieveBillsByMonth(@RequestParam("month") int month, @RequestParam("year") int year) {
    return ResponseEntity.ok(queryService.retrieveBillsByMonth(month, year));
  }
}
