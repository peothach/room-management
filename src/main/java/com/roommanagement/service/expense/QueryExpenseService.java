package com.roommanagement.service.expense;

import com.roommanagement.dto.response.BaseResponseDto;
import com.roommanagement.dto.response.expense.QueryExpenseResponse;

import java.util.List;

public interface QueryExpenseService {
  BaseResponseDto<List<QueryExpenseResponse>> retrieveExpenses();
  BaseResponseDto<?> retrieveParticularExpense(Integer expenseId);
}
