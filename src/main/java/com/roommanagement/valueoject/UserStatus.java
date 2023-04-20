package com.roommanagement.valueoject;

public enum UserStatus {
  Active("A", "Active"),
  Inactive("I", "Inactive");

  private String value;
  private String description;

  UserStatus(String value, String description) {
    this.value = value;
    this.description = description;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public static UserStatus fromValue(String value) {
    for (UserStatus billStatus : UserStatus.values()) {
      if (billStatus.value.equals(value)) {
        return billStatus;
      }
    }
    throw new IllegalArgumentException("Invalid value: " + value);
  }
}
