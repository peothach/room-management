package com.roommanagement.repository.expense;

import com.roommanagement.dto.response.BaseResponseDto;
import com.roommanagement.dto.response.expense.ExpenseResponse;
import com.roommanagement.entity.User;
import com.roommanagement.security.services.UserDetailsImpl;
import com.roommanagement.service.expense.QueryExpenseService;
import com.roommanagement.util.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
public class QueryExpenseServiceImpl implements QueryExpenseService {
  private final QueryExpenseMyBatisMapper query;
  private final UserUtils userUtils;

  @Override
  public BaseResponseDto<List<ExpenseResponse>> retrieveExpenses() {
    User user = userUtils.getCurrentUser().orElseThrow(RuntimeException::new);
    List<ExpenseResponse.QueryExpense> queryExpenses = query.retrieveExpense(user.getId());
    LinkedHashMap<Integer, List<ExpenseResponse.QueryExpense>> expenseQueryPerIds = queryExpenses.stream().collect(groupingBy(ExpenseResponse.QueryExpense::getExpenseId, LinkedHashMap::new, toList()));

    List<ExpenseResponse> expenses = new ArrayList<>();

    expenseQueryPerIds.forEach((key, value) -> {
      ExpenseResponse.QueryExpense queryExpense = value.get(0);
      List<ExpenseResponse.Room> rooms = new ArrayList<>();
      value.forEach(i -> {
        ExpenseResponse.Room room = new ExpenseResponse.Room();
        room.setId(i.getRoomId());
        room.setName(i.getRoomName());
        rooms.add(room);
      });

      ExpenseResponse expenseResponse = new ExpenseResponse();
      expenseResponse.setId(key);
      expenseResponse.setName(queryExpense.getExpenseName());
      expenseResponse.setPrice(queryExpense.getPrice());
      expenseResponse.setUnitPriceFlag(queryExpense.isUnitPriceFlag());
      expenseResponse.setUnit(queryExpense.getUnit());
      expenseResponse.setRooms(rooms);
      expenseResponse.setApplyAll(queryExpense.isApplyAll());

      expenses.add(expenseResponse);
    });

    return new BaseResponseDto(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), expenses);
  }

  @Override
  public BaseResponseDto<?> retrieveParticularExpense(Integer expenseId) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    List<ExpenseResponse.QueryExpense> queryExpenses = query.retrieveParticularExpense(userDetails.getId(), expenseId);

    ExpenseResponse expense = new ExpenseResponse();
    expense.setId(queryExpenses.get(0).getExpenseId());
    expense.setName(queryExpenses.get(0).getExpenseName());
    expense.setPrice(queryExpenses.get(0).getPrice());
    expense.setUnitPriceFlag(queryExpenses.get(0).isUnitPriceFlag());
    expense.setUnit(queryExpenses.get(0).getUnit());
    expense.setApplyAll(queryExpenses.get(0).isApplyAll());

    List<ExpenseResponse.Room> rooms = new ArrayList<>();
    queryExpenses.forEach(i -> {
      ExpenseResponse.Room room = new ExpenseResponse.Room();
      room.setId(i.getRoomId());
      room.setName(i.getRoomName());

      rooms.add(room);
    });

    expense.setRooms(rooms);
    return new BaseResponseDto(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), expense);
  }
}
