package com.roommanagement.repository.room;

import com.roommanagement.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
//  @Query("SELECT COUNT(u) FROM User u WHERE u.role = :role")
  long countByNameLike(String pattern);
}
