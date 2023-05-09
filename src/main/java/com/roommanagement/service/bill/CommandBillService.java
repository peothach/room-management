package com.roommanagement.service.bill;

import com.roommanagement.dto.request.bill.BillRequest;
import com.roommanagement.dto.response.BaseResponseDto;

import java.time.LocalDate;

public interface CommandBillService {
  BaseResponseDto<?> createBill(BillRequest billRequest);
}
