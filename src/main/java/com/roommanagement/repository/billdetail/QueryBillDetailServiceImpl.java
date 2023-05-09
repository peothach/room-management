package com.roommanagement.repository.billdetail;

import com.roommanagement.dto.response.BaseResponseDto;
import com.roommanagement.dto.response.billdetail.BillDetailResponse;
import com.roommanagement.entity.User;
import com.roommanagement.service.bill.QueryBillService;
import com.roommanagement.util.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryBillDetailServiceImpl implements QueryBillService {
  private final QueryBillDetailMyBatisMapper queryMapper;
  private final UserUtils userUtils;
  @Override
  public BaseResponseDto<List<BillDetailResponse>> retrieveBillsByMonth(int month, int year) {
    // Get all room which is available in bill
    User user = userUtils.getCurrentUser().orElseThrow(RuntimeException::new);
    List<BillDetailResponse> roomsOnBill = queryMapper.retrieveRoomsInBill(month, year, user.getId());

    // Get all expense by room
    roomsOnBill.forEach(room -> {
      List<BillDetailResponse.ExpenseQuery> expenses = queryMapper.retrieveExpenses(room.getRoomId(), month, year);
      room.setExpenses(expenses);
    });

    BaseResponseDto<List<BillDetailResponse>> responseDto = new BaseResponseDto<>();
    responseDto.setStatus(HttpStatus.OK.value());
    responseDto.setMessage(HttpStatus.OK.getReasonPhrase());
    responseDto.setData(roomsOnBill);

    return responseDto;
  }
}
