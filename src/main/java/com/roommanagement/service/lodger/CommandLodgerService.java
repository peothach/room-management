package com.roommanagement.service.lodger;

import com.roommanagement.dto.request.lodger.LodgerRequest;
import com.roommanagement.dto.response.BaseResponseDto;

public interface CommandLodgerService {
  BaseResponseDto<?> save(Integer roomId, LodgerRequest lodgerRequest);
  BaseResponseDto<?> update(Integer lodgerId, LodgerRequest lodgerRequest);
  BaseResponseDto<?> delete(Integer lodgerId);
}
