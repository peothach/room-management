package com.roommanagement.controllers.expense;

import com.roommanagement.dto.response.BaseResponseDto;
import com.roommanagement.service.expense.QueryExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1")
public class QueryExpenseController {
  private final QueryExpenseService queryExpenseService;

  @GetMapping("expenses")
  public ResponseEntity<BaseResponseDto<?>> retrieveExpenses() {
    return ResponseEntity.ok(queryExpenseService.retrieveExpenses());
  }

  @GetMapping("expenses/{id}")
  public ResponseEntity<BaseResponseDto<?>> retrieveParticularExpense(@PathVariable("id") Integer id) {
    return ResponseEntity.ok(queryExpenseService.retrieveParticularExpense(id));
  }
}
