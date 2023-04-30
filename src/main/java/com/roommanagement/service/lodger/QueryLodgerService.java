package com.roommanagement.service.lodger;

import com.roommanagement.dto.response.BaseResponseDto;
import com.roommanagement.dto.response.lodger.LodgerResponse;

import java.util.List;

public interface QueryLodgerService {
  BaseResponseDto<List<LodgerResponse>> getLodgersByRoom(Integer roomId);
}
