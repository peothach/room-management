package com.roommanagement.dto.request.lodger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LodgerRequest {
  private MultipartFile front;
  private MultipartFile back;
  private String name;
  private String email;
  private String phoneNumber;
}
