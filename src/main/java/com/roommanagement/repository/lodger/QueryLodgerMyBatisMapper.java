package com.roommanagement.repository.lodger;

import com.roommanagement.entity.Image;
import com.roommanagement.entity.Lodger;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QueryLodgerMyBatisMapper {
  @Select("SELECT l.lodger_id,\n" +
      "       l.name,\n" +
      "       l.email,\n" +
      "       l.phone_number\n" +
      "FROM room r\n" +
      "JOIN lodger l ON r.room_id = l.room_id\n" +
      "WHERE r.user_id = #{userId} AND r.room_id = #{roomId} AND l.active IS TRUE")
  @Results(id = "lodgersResultMap", value = {
      @Result(property = "id", column = "lodger_id"),
      @Result(property = "name", column = "name"),
      @Result(property = "email", column = "email"),
      @Result(property = "phoneNumber", column = "phone_number")
  })
  List<Lodger> findAllLodgerByRoomId(@Param("userId") Integer userId, @Param("roomId") Integer roomId);
  @Select("SELECT i.image_file_name,\n" +
      "       i.url,\n" +
      "       i.title\n" +
      "FROM lodger l\n" +
      "JOIN image i ON i.lodger_id = l.lodger_id\n" +
      "WHERE l.lodger_id = #{lodgerId}")
  @Results(id = "imagesResultMap", value = {
      @Result(property = "imageFileName", column = "image_file_name"),
      @Result(property = "url", column = "url"),
      @Result(property = "title", column = "title")
  })
  List<Image> findAllImageByLodgerId(@Param("lodgerId") Integer lodgerId);
}
