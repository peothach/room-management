package com.roommanagement.controllers.expense;

import com.roommanagement.dto.request.expense.ExpenseCreateRequest;
import com.roommanagement.dto.request.expense.ExpenseUpdateRequest;
import com.roommanagement.dto.response.BaseResponseDto;
import com.roommanagement.service.expense.CommandExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1")
public class CommandExpenseController {
  private final CommandExpenseService commandExpenseService;

  @PostMapping("expenses")
  public ResponseEntity<BaseResponseDto<?>> createExpense(@RequestBody ExpenseCreateRequest createRequest) {
    return ResponseEntity.ok(commandExpenseService.createExpense(createRequest));
  }

  @PatchMapping("expenses/{id}")
  public ResponseEntity<BaseResponseDto<?>> updateExpense(@PathVariable("id") Integer expenseId, @RequestBody ExpenseUpdateRequest updateRequest) {
    return ResponseEntity.ok(commandExpenseService.updateExpense(expenseId, updateRequest));
  }

  @DeleteMapping("expenses/{id}")
  public ResponseEntity<BaseResponseDto<?>> deactivateExpense(@PathVariable("id") Integer expenseId) {
    return ResponseEntity.ok(commandExpenseService.deactivateExpense(expenseId));
  }
}
