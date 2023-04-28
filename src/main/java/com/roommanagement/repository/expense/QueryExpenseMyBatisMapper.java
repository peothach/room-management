package com.roommanagement.repository.expense;

import com.roommanagement.dto.response.expense.ExpenseResponse;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QueryExpenseMyBatisMapper {
  @Select("SELECT e.expense_id,\n" +
      "       e.name expense_name,\n" +
      "       e.price,\n" +
      "       e.unit_price_flag,\n" +
      "       e.apply_all_flag,\n" +
      "       u.unit,\n" +
      "       r.id as room_id,\n" +
      "       r.name as room_name\n" +
      "FROM expense e\n" +
      "JOIN room_expense re ON e.expense_id = re.expense_id\n" +
      "JOIN room r ON r.id = re.room_id\n" +
      "LEFT JOIN unit_price u ON u.unit_price_id = e.unit_price_id\n" +
      "JOIN users on users.id = e.user_id\n" +
      "WHERE users.id = #{userId}\n" +
      "AND r.status <> 'Phòng đã xóa'")
  @Results(id = "expensesResultMap", value = {
      @Result(property = "expenseId", column = "expense_id"),
      @Result(property = "expenseName", column = "expense_name"),
      @Result(property = "price", column = "price"),
      @Result(property = "unitPriceFlag", column = "unit_price_flag"),
      @Result(property = "unit", column = "unit"),
      @Result(property = "roomId", column = "room_id"),
      @Result(property = "roomName", column = "room_name"),
      @Result(property = "isApplyAll", column = "apply_all_flag")
  })
  List<ExpenseResponse.QueryExpense> retrieveExpense(@Param("userId") Long userId);

  @Select("SELECT e.expense_id,\n" +
      "       e.name expense_name,\n" +
      "       e.price,\n" +
      "       e.unit_price_flag,\n" +
      "       e.apply_all_flag,\n" +
      "       u.unit,\n" +
      "       r.id as room_id,\n" +
      "       r.name as room_name\n" +
      "FROM expense e\n" +
      "JOIN room_expense re ON e.expense_id = re.expense_id\n" +
      "JOIN room r ON r.id = re.room_id\n" +
      "LEFT JOIN unit_price u ON u.unit_price_id = e.unit_price_id\n" +
      "JOIN users on users.id = e.user_id\n" +
      "WHERE users.id = #{userId}\n" +
      "AND e.expense_id = #{expenseId}\n" +
      "AND r.status <> 'Phòng đã xóa'" )
  @Results(id = "particularExpenseResultMap", value = {
      @Result(property = "expenseId", column = "expense_id"),
      @Result(property = "expenseName", column = "expense_name"),
      @Result(property = "price", column = "price"),
      @Result(property = "unitPriceFlag", column = "unit_price_flag"),
      @Result(property = "unit", column = "unit"),
      @Result(property = "roomId", column = "room_id"),
      @Result(property = "roomName", column = "room_name"),
      @Result(property = "isApplyAll", column = "apply_all_flag")
  })
  List<ExpenseResponse.QueryExpense> retrieveParticularExpense(@Param("userId") Long userId, @Param("expenseId") Integer expenseId);
}
