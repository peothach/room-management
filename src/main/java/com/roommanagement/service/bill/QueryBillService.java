package com.roommanagement.service.bill;

import com.roommanagement.dto.response.BaseResponseDto;
import com.roommanagement.dto.response.billdetail.BillDetailResponse;

import java.time.Month;
import java.util.List;

public interface QueryBillService {
  BaseResponseDto<List<BillDetailResponse>> retrieveBillsByMonth(int month, int year);
}
