package com.roommanagement.dto.response.lodger;

import lombok.Data;

import java.util.List;

@Data
public class LodgerResponse {
  private Integer id;
  private String name;
  private String email;
  private String phoneNumber;
  private List<Image> images;

  @Data
  private static class Image {
    private String imageFileName;
    private String url;
    private String title;
  }
}
