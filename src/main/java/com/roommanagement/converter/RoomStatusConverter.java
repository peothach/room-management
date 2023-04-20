package com.roommanagement.converter;

import com.roommanagement.valueoject.RoomStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class RoomStatusConverter implements AttributeConverter<RoomStatus, String> {

  @Override
  public String convertToDatabaseColumn(RoomStatus roomStatus) {
    if (roomStatus == null) {
      return null;
    }
    return roomStatus.getValue();
  }

  @Override
  public RoomStatus convertToEntityAttribute(String value) {
    return RoomStatus.fromValue(value);
  }
}
