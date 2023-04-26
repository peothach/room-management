package com.roommanagement.service.unitprice;

import com.roommanagement.dto.response.BaseResponseDto;
import com.roommanagement.dto.response.unitprice.UnitPriceResponse;

import java.util.List;

public interface QueryUnitPriceService {
  BaseResponseDto<List<UnitPriceResponse>> getUnitPrices();
}
