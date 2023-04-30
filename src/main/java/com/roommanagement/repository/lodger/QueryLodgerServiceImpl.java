package com.roommanagement.repository.lodger;

import com.roommanagement.dto.response.BaseResponseDto;
import com.roommanagement.dto.response.lodger.LodgerResponse;
import com.roommanagement.entity.Image;
import com.roommanagement.entity.Lodger;
import com.roommanagement.entity.User;
import com.roommanagement.service.lodger.QueryLodgerService;
import com.roommanagement.util.UserUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class QueryLodgerServiceImpl implements QueryLodgerService {
  private final QueryLodgerMyBatisMapper queryLodgerMyBatisMapper;
  private final UserUtils userUtils;
  private final ModelMapper modelMapper;
  @Override
  public BaseResponseDto<List<LodgerResponse>> getLodgersByRoom(Integer roomId) {
    User currentUser = userUtils.getCurrentUser().orElseThrow(RuntimeException::new);
    List<Lodger> lodgers = queryLodgerMyBatisMapper.findAllLodgerByRoomId(currentUser.getId(), roomId);
    lodgers.forEach(lodger -> {
      List<Image> images = queryLodgerMyBatisMapper.findAllImageByLodgerId(lodger.getId());
      lodger.setImages(images);
    });

    List<LodgerResponse> lodgerResponse = lodgers.stream()
        .map(lodger -> modelMapper.map(lodger, LodgerResponse.class))
        .collect(Collectors.toList());

    return new BaseResponseDto<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), lodgerResponse);
  }
}
