package com.roommanagement.converter;

import com.roommanagement.valueoject.UserStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class UserStatusConverter implements AttributeConverter<UserStatus, String> {

  @Override
  public String convertToDatabaseColumn(UserStatus userStatus) {
    if (userStatus == null) {
      return null;
    }
    return userStatus.getValue();
  }

  @Override
  public UserStatus convertToEntityAttribute(String value) {
    return UserStatus.fromValue(value);
  }
}
