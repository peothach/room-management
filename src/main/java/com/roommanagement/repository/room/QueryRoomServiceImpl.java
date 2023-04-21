package com.roommanagement.repository.room;

import com.roommanagement.dto.response.BaseResponseDto;
import com.roommanagement.dto.response.room.RoomResponse;
import com.roommanagement.dto.response.room.SummaryRoomByStatusResponse;
import com.roommanagement.security.services.UserDetailsImpl;
import com.roommanagement.service.room.QueryRoomService;
import com.roommanagement.util.UserUtils;
import com.roommanagement.valueoject.RoomStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class QueryRoomServiceImpl implements QueryRoomService {
  private final QueryRoomMyBatisMapper queryRoomMybatisMapper;
  private final UserUtils userUtils;

  @Override
  public BaseResponseDto<SummaryRoomByStatusResponse> getTotalRoomByStatus() {
    UserDetailsImpl userDetails = userUtils.getCurrentUser().orElseThrow(RuntimeException::new);
    return new BaseResponseDto<>(HttpStatus.OK.value(),
        HttpStatus.OK.getReasonPhrase(),
        combineSummaryRoom(queryRoomMybatisMapper.getTotalRoomByStatus(userDetails.getId()))
    );
  }

  @Override
  public BaseResponseDto<List<RoomResponse>> getRooms() {
    UserDetailsImpl userDetails = userUtils.getCurrentUser().orElseThrow(RuntimeException::new);
    return new BaseResponseDto<>(HttpStatus.OK.value(),
        HttpStatus.OK.getReasonPhrase(),
        queryRoomMybatisMapper.retrieveRooms(userDetails.getId())
    );
  }

  @Override
  public BaseResponseDto<List<RoomResponse>> getRooms(String status) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    return new BaseResponseDto<>(HttpStatus.OK.value(),
        HttpStatus.OK.getReasonPhrase(),
        queryRoomMybatisMapper.retrieveRoomsByStatus(userDetails.getId(), status)
    );
  }

  private SummaryRoomByStatusResponse combineSummaryRoom(List<SummaryRoomByStatusResponse.SummaryRoomByStatusQuery> roomByStatusQueries) {
    SummaryRoomByStatusResponse summaryRoom = new SummaryRoomByStatusResponse();
    for (SummaryRoomByStatusResponse.SummaryRoomByStatusQuery query : roomByStatusQueries) {
      RoomStatus roomStatus = query.getRoomStatus();
      int total = query.getTotal();
      setTotalForStatus(summaryRoom, roomStatus, total);
    }
    return summaryRoom;
  }

  private void setTotalForStatus(SummaryRoomByStatusResponse summaryRoom, RoomStatus status, int total) {
    switch (status) {
      case RoomAvailable:
        summaryRoom.setRoomAvailable(total);
        break;
      case SoonToBeReturn:
        summaryRoom.setSoonToBeReturn(total);
        break;
      default:
        summaryRoom.setCurrentlyRenting(total);
        break;
    }
  }
}
