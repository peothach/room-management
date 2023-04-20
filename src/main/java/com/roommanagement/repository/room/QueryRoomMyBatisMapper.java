package com.roommanagement.repository.room;

import com.roommanagement.dto.response.room.RoomResponse;
import com.roommanagement.dto.response.room.SummaryRoomByStatusResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QueryRoomMyBatisMapper {
  @Select("SELECT r.status, COUNT(*) AS total\n" +
      "            FROM room r\n" +
      "            JOIN users u ON r.user_id = u.id\n" +
      "            WHERE r.user_id = #{userId} AND r.status <> 'Phòng đã xóa'\n" +
      "            GROUP BY r.status")
  List<SummaryRoomByStatusResponse.SummaryRoomByStatusQuery> getTotalRoomByStatus(@Param("userId") Long userId);

  @Select("SELECT id, name, status FROM room WHERE user_id = #{userId} and status <> 'Phòng đã xóa'")
  List<RoomResponse> retrieveRooms(@Param("userId") Long userId);

  @Select("SELECT id, name, status FROM room WHERE user_id = #{userId} and status = #{status}")
  List<RoomResponse> retrieveRoomsByStatus(@Param("userId") Long userId, @Param("status") String status);
}
