package com.roommanagement.service.s3;

import com.roommanagement.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface S3Service {
  List<Image> save(String title, String description, MultipartFile[] files);

  byte[] downloadTodoImage(Integer id);

  List<Image> getAllTodos();
}
