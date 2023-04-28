package com.roommanagement.repository.room;

import com.roommanagement.dto.response.BaseResponseDto;
import com.roommanagement.dto.response.expense.ExpenseResponse;
import com.roommanagement.dto.response.room.RoomResponse;
import com.roommanagement.dto.response.room.SummaryRoomByStatusResponse;
import com.roommanagement.entity.User;
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
    User user = userUtils.getCurrentUser().orElseThrow(RuntimeException::new);
    return new BaseResponseDto<>(HttpStatus.OK.value(),
        HttpStatus.OK.getReasonPhrase(),
        combineSummaryRoom(queryRoomMybatisMapper.getTotalRoomByStatus(user.getId()))
    );
  }

  @Override
  public BaseResponseDto<List<RoomResponse>> getRooms() {
    User user = userUtils.getCurrentUser().orElseThrow(RuntimeException::new);
    return new BaseResponseDto<>(HttpStatus.OK.value(),
        HttpStatus.OK.getReasonPhrase(),
        queryRoomMybatisMapper.retrieveRooms(user.getId())
    );
  }

  @Override
  public BaseResponseDto<List<RoomResponse>> getRooms(String status) {
    User user = userUtils.getCurrentUser().orElseThrow(RuntimeException::new);
    return new BaseResponseDto<>(HttpStatus.OK.value(),
        HttpStatus.OK.getReasonPhrase(),
        queryRoomMybatisMapper.retrieveRoomsByStatus(user.getId(), status)
    );
  }

  @Override
  public BaseResponseDto<List<ExpenseResponse>> getExpensesByRoom(Integer roomId) {
    User user = userUtils.getCurrentUser().orElseThrow(RuntimeException::new);
    return new BaseResponseDto<>(HttpStatus.OK.value(),
        HttpStatus.OK.getReasonPhrase(),
        queryRoomMybatisMapper.findExpenseByRoomId(user.getId(), roomId)
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
