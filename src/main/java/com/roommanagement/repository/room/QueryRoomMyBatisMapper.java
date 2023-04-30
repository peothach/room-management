package com.roommanagement.repository.room;

import com.roommanagement.dto.response.expense.ExpenseResponse;
import com.roommanagement.dto.response.room.RoomResponse;
import com.roommanagement.dto.response.room.SummaryRoomByStatusResponse;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QueryRoomMyBatisMapper {
  @Select("SELECT r.status, COUNT(*) AS total \n" +
      "                  FROM room r \n" +
      "                  JOIN users u ON r.user_id = u.user_id \n" +
      "                  WHERE r.user_id = #{userId} AND r.status <> 'Phòng đã xóa' \n" +
      "                  GROUP BY r.status")
  @Results(id = "getTotalRoomByStatusResultMap", value = {
      @Result(property = "status", column = "status"),
      @Result(property = "total", column = "total")
  })
  List<SummaryRoomByStatusResponse.SummaryRoomByStatusQuery> getTotalRoomByStatus(@Param("userId") Integer userId);

  @Select("SELECT room_id, name, status FROM room WHERE user_id = #{userId} and status <> 'Phòng đã xóa' ORDER BY room_id")
  @Results(id = "retrieveRoomsResultMap", value = {
      @Result(property = "id", column = "room_id"),
      @Result(property = "name", column = "name"),
      @Result(property = "status", column = "status")
  })
  List<RoomResponse> retrieveRooms(@Param("userId") Integer userId);

  @Select("SELECT room_id, name, status FROM room WHERE user_id = #{userId} and status = #{status} ORDER BY room_id")
  @Results(id = "retrieveRoomsByStatusResultMap", value = {
      @Result(property = "id", column = "room_id"),
      @Result(property = "name", column = "name"),
      @Result(property = "status", column = "status")
  })
  List<RoomResponse> retrieveRoomsByStatus(@Param("userId") Integer userId, @Param("status") String status);

  @Select("SELECT e.expense_id,  \n" +
      "                   e.name expense_name, \n" +
      "                   CASE  \n" +
      "                   WHEN re.override_price_flag IS TRUE THEN re.price \n" +
      "                   ELSE e.price \n" +
      "                   END as price, \n" +
      "                   e.unit_price_flag,  \n" +
      "                   e.apply_all_flag,  \n" +
      "                   u.unit,  \n" +
      "                   r.room_id as room_id,  \n" +
      "                   r.name as room_name  \n" +
      "            FROM expense e  \n" +
      "            JOIN room_expense re ON e.expense_id = re.expense_id  \n" +
      "            JOIN room r ON r.room_id = re.room_id  \n" +
      "            LEFT JOIN unit_price u ON u.unit_price_id = e.unit_price_id  \n" +
      "            JOIN users on users.user_id = e.user_id \n" +
      "            WHERE users.user_id = #{userId} \n" +
      "            AND r.room_id = #{roomId} \n" +
      "            AND r.status <> 'Phòng đã xóa'")
  @Results(id = "expensesResultMap", value = {
      @Result(property = "id", column = "expense_id"),
      @Result(property = "name", column = "expense_name"),
      @Result(property = "price", column = "price"),
      @Result(property = "unitPriceFlag", column = "unit_price_flag"),
      @Result(property = "unit", column = "unit")
  })
  List<ExpenseResponse> findExpenseByRoomId(@Param("userId") Integer userId, @Param("roomId") Integer roomId);
}
