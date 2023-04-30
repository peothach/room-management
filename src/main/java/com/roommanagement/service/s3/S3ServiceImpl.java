package com.roommanagement.service.s3;

import com.roommanagement.configuration.BucketName;
import com.roommanagement.entity.Image;
import com.roommanagement.repository.image.ImageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static org.apache.http.entity.ContentType.*;

@Service
@AllArgsConstructor
public class S3ServiceImpl implements S3Service {
  private final FileStore fileStore;
  private final ImageRepository repository;

  @Override
  public List<Image> save(String title, String description, MultipartFile[] files) {
    List<Image> images = new ArrayList<>();
    Arrays.stream(files).forEach(file -> {
      //check if the file is empty
      if (file.isEmpty()) {
        throw new IllegalStateException("Cannot upload empty file");
      }
      //Check if the file is an image
      if (!Arrays.asList(IMAGE_PNG.getMimeType(),
          IMAGE_BMP.getMimeType(),
          IMAGE_GIF.getMimeType(),
          IMAGE_JPEG.getMimeType()).contains(file.getContentType())) {
        throw new IllegalStateException("FIle uploaded is not an image");
      }
      //get file metadata
      Map<String, String> metadata = new HashMap<>();
      metadata.put("Content-Type", file.getContentType());
      metadata.put("Content-Length", String.valueOf(file.getSize()));
      //Save Image in S3 and then save Todo in the database
      String path = String.format("%s/%s", BucketName.CMND_IMAGE.getBucketName(), "static");
      String fileName = String.format("%s", file.getOriginalFilename());
      try {
        fileStore.upload(path, fileName, Optional.of(metadata), file.getInputStream());
      } catch (IOException e) {
        throw new IllegalStateException("Failed to upload file", e);
      }
      Image todo = Image.builder()
          .description(description)
          .title(title)
          .imagePath(path)
          .imageFileName(fileName)
          .url("https://cmnd-room-mangement.s3.ap-southeast-1.amazonaws.com/".concat("static/").concat(fileName))
          .build();
      Image persistedImage = repository.saveAndFlush(todo);
      images.add(persistedImage);
    });
    return images;
  }

  @Override
  public byte[] downloadTodoImage(Integer id) {
    Image image = repository.findById(id).get();
    return fileStore.download(image.getImagePath(), image.getImageFileName());
  }

  @Override
  public List<Image> getAllTodos() {
    List<Image> todos = new ArrayList<>();
    repository.findAll().forEach(todos::add);
    return todos;
  }
}
