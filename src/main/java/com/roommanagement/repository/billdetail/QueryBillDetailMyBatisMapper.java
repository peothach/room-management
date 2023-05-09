package com.roommanagement.repository.billdetail;

import com.roommanagement.dto.response.billdetail.BillDetailResponse;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QueryBillDetailMyBatisMapper {
  @Select("SELECT r.room_id, r.name\n" +
      "FROM bill b\n" +
      "JOIN room r ON r.room_id = b.room_id\n" +
      "WHERE MONTH(b.create_date) = #{month}\n" +
      "AND YEAR(b.create_date) = #{year} AND r.user_id = #{userId}")
  @Results(id = "retrieveRoomsInBillResultMap", value = {
      @Result(property = "roomId", column = "room_id"),
      @Result(property = "name", column = "name")
  })
  List<BillDetailResponse> retrieveRoomsInBill(@Param("month") int month, @Param("year") int year, @Param("userId") int userId);

  @Select("select \n" +
      "  DISTINCT e.expense_id, \n" +
      "  e.name, \n" +
      "  e.price, \n" +
      "  up.unit, \n" +
      "  bd.old_number, \n" +
      "  bd.new_number, \n" +
      "  bd.quantity \n" +
      "from \n" +
      "  bill_detail bd \n" +
      "  join expense e on bd.expense_id = e.expense_id \n" +
      "  JOIN room r on r.room_id = bd.room_id \n" +
      "  LEFT join unit_price up on up.unit_price_id = e.expense_id\n" +
      "WHERE \n" +
      "  r.room_id = #{roomId} \n" +
      "  AND MONTH(bd.create_date) = #{month} \n" +
      "  AND YEAR(bd.create_date) = #{year}")
  @Results(id = "retrieveExpensesResultMap", value = {
      @Result(property = "id", column = "expense_id"),
      @Result(property = "name", column = "name"),
      @Result(property = "price", column = "price"),
      @Result(property = "unit", column = "unit"),
      @Result(property = "oldNumber", column = "old_number"),
      @Result(property = "newNumber", column = "new_number"),
      @Result(property = "quantity", column = "quantity")
  })
  List<BillDetailResponse.ExpenseQuery> retrieveExpenses(
      @Param("roomId") int roomId,
      @Param("month") int month,
      @Param("year") int year
  );
}
