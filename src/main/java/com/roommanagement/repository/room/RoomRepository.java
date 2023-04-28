package com.roommanagement.repository.room;

import com.roommanagement.entity.Room;
import com.roommanagement.entity.User;
import com.roommanagement.valueoject.RoomStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
//  @Query("SELECT COUNT(u) FROM User u WHERE u.role = :role")
  long countByNameLikeAndUserId(String pattern, Long userId);
  List<Room> findAllByStatusIsNotAndUserId(RoomStatus status, Long userId);
}
