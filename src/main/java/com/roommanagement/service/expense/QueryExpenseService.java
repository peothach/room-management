package com.roommanagement.service.expense;

import com.roommanagement.dto.response.BaseResponseDto;
import com.roommanagement.dto.response.expense.ExpenseResponse;

import java.util.List;

public interface QueryExpenseService {
  BaseResponseDto<List<ExpenseResponse>> retrieveExpenses();
  BaseResponseDto<?> retrieveParticularExpense(Integer expenseId);
}
