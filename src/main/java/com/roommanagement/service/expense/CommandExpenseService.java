package com.roommanagement.service.expense;

import com.roommanagement.dto.request.expense.ExpenseCreateRequest;
import com.roommanagement.dto.request.expense.ExpenseUpdateRequest;
import com.roommanagement.dto.response.BaseResponseDto;

public interface CommandExpenseService {
  BaseResponseDto<?> createExpense(ExpenseCreateRequest createRequest);
  BaseResponseDto<?> updateExpense(Integer expenseId, ExpenseUpdateRequest updateRequest);
  BaseResponseDto<?> deactivateExpense(Integer expenseId);
}
