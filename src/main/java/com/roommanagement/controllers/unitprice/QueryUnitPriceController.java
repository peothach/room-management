package com.roommanagement.controllers.unitprice;

import com.roommanagement.dto.response.BaseResponseDto;
import com.roommanagement.dto.response.unitprice.UnitPriceResponse;
import com.roommanagement.service.unitprice.QueryUnitPriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1")
public class QueryUnitPriceController {
  private final QueryUnitPriceService unitPriceService;

  @GetMapping("/unit-prices")
  public ResponseEntity<BaseResponseDto<List<UnitPriceResponse>>> getUnitPrices() {
    return ResponseEntity.ok(unitPriceService.getUnitPrices());
  }
}
