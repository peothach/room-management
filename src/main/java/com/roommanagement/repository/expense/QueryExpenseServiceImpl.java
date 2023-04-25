package com.roommanagement.repository.expense;

import com.roommanagement.dto.response.BaseResponseDto;
import com.roommanagement.dto.response.expense.QueryExpenseResponse;
import com.roommanagement.security.services.UserDetailsImpl;
import com.roommanagement.service.expense.QueryExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
public class QueryExpenseServiceImpl implements QueryExpenseService {
  private final QueryExpenseMyBatisMapper query;
  @Override
  public BaseResponseDto<List<QueryExpenseResponse>> retrieveExpenses() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    List<QueryExpenseResponse.QueryExpense> queryExpenses = query.retrieveExpense(userDetails.getId());
    LinkedHashMap<Integer, List<QueryExpenseResponse.QueryExpense>> expenseQueryPerIds = queryExpenses
        .stream()
        .collect(groupingBy(QueryExpenseResponse.QueryExpense::getExpenseId, LinkedHashMap::new, toList()));

    List<QueryExpenseResponse> expenses = new ArrayList<>();

    expenseQueryPerIds.forEach((key, value) -> {
      QueryExpenseResponse.QueryExpense queryExpense = value.get(0);
      List<QueryExpenseResponse.Room> rooms = new ArrayList<>();
      value.forEach(i -> {
        QueryExpenseResponse.Room room = new QueryExpenseResponse.Room();
        room.setId(i.getRoomId());
        room.setName(i.getRoomName());
        rooms.add(room);
      });

      QueryExpenseResponse queryExpenseResponse = new QueryExpenseResponse();
      queryExpenseResponse.setId(key);
      queryExpenseResponse.setName(queryExpense.getExpenseName());
      queryExpenseResponse.setPrice(queryExpense.getPrice());
      queryExpenseResponse.setUnitPriceFlag(queryExpense.isUnitPriceFlag());
      queryExpenseResponse.setUnit(queryExpense.getUnit());
      queryExpenseResponse.setRooms(rooms);

      expenses.add(queryExpenseResponse);
    });

    return new BaseResponseDto(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), expenses);
  }
}
