package com.roommanagement.repository.room;

import com.roommanagement.dto.response.room.RoomResponse;
import com.roommanagement.dto.response.room.SummaryRoomByStatusResponse;
import com.roommanagement.security.services.UserDetailsImpl;
import com.roommanagement.service.room.QueryRoomService;
import com.roommanagement.valueoject.RoomStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class QueryRoomServiceImpl implements QueryRoomService {
  private final QueryRoomMyBatisMapper queryRoomMybatisMapper;
  @Override
  public SummaryRoomByStatusResponse getTotalRoomByStatus() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    return combineSummaryRoom(queryRoomMybatisMapper.getTotalRoomByStatus(userDetails.getId()));
  }

  @Override
  public List<RoomResponse> getRooms() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    return queryRoomMybatisMapper.retrieveRooms(userDetails.getId());
  }

  @Override
  public List<RoomResponse> getRooms(String status) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    return queryRoomMybatisMapper.retrieveRoomsByStatus(userDetails.getId(), status);
  }

  private SummaryRoomByStatusResponse combineSummaryRoom(List<SummaryRoomByStatusResponse.SummaryRoomByStatusQuery> roomByStatusQueries) {
    SummaryRoomByStatusResponse summaryRoom = new SummaryRoomByStatusResponse();
    roomByStatusQueries.forEach(i -> {
      RoomStatus roomStatus = i.getRoomStatus();
      if (roomStatus == RoomStatus.RoomAvailable) {
        summaryRoom.setRoomAvailable(i.getTotal());
      } else if (roomStatus == RoomStatus.SoonToBeReturn) {
        summaryRoom.setSoonToBeReturn(i.getTotal());
      } else {
        summaryRoom.setCurrentlyRenting(i.getTotal());
      }
    });
    return summaryRoom;
  }
}
