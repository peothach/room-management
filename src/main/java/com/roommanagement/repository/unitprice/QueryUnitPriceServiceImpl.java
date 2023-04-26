package com.roommanagement.repository.unitprice;

import com.roommanagement.dto.response.BaseResponseDto;
import com.roommanagement.dto.response.unitprice.UnitPriceResponse;
import com.roommanagement.entity.UnitPrice;
import com.roommanagement.service.unitprice.QueryUnitPriceService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class QueryUnitPriceServiceImpl implements QueryUnitPriceService {
  private final UnitPriceRepository unitPriceRepository;
  private final ModelMapper mapper;

  @Override
  public BaseResponseDto<List<UnitPriceResponse>> getUnitPrices() {
    List<UnitPrice> unitPrices = unitPriceRepository.findAll();
    List<UnitPriceResponse> unitPriceResponses = new ArrayList<>();
    unitPrices.forEach(i -> unitPriceResponses.add(mapper.map(i, UnitPriceResponse.class)));
    return new BaseResponseDto<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), unitPriceResponses);
  }
}
