package com.roommanagement.service.room;

import com.roommanagement.dto.request.room.CreateRoomRequestDto;
import com.roommanagement.entity.Room;
import com.roommanagement.entity.User;
import com.roommanagement.repository.user.UserRepository;
import com.roommanagement.repository.room.RoomRepository;
import com.roommanagement.security.services.UserDetailsImpl;
import com.roommanagement.valueoject.RoomStatus;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

@RequiredArgsConstructor
@Service
public class CommandRoomServiceImpl implements CommandRoomService {
  private final RoomRepository roomRepository;
  private final ModelMapper mapper;
  private final UserRepository userRepository;
  @Override
  public void createRoom(CreateRoomRequestDto roomRequestDto, int quantity) {
    LinkedList<Room> rooms = new LinkedList<>();
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    User user = userRepository.findById(userDetails.getId()).orElseThrow(RuntimeException::new);
    long totalExistingName = roomRepository.countByNameLike("%" + roomRequestDto.getName() + "%");
    for (int i = 1; i <= quantity; i++) {
      Room room = mapper.map(roomRequestDto, Room.class);
      room.setUser(user);
      room.setName(room.getName() + " " + ++totalExistingName);
      room.setStatus(RoomStatus.RoomAvailable);
      rooms.add(room);
    }
    roomRepository.saveAll(rooms);
  }

  @Override
  public void updateRoom(CreateRoomRequestDto roomRequestDto, long roomId) {
    Room room = roomRepository.findById(roomId).orElseThrow(RuntimeException::new);
    room.setName(roomRequestDto.getName());
    roomRepository.save(room);
  }

  @Override
  public void deleteRoom(long roomId) {
    Room room = roomRepository.findById(roomId).orElseThrow(RuntimeException::new);
    room.setStatus(RoomStatus.Inactive);
    roomRepository.save(room);
  }
}
