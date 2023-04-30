package com.roommanagement.controllers.image;

import com.roommanagement.entity.Image;
import com.roommanagement.service.s3.S3Service;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/v1/cmnd")
@AllArgsConstructor
public class ImageController {
  private final S3Service s3Service;

  @GetMapping
  public ResponseEntity<List<Image>> getTodos() {
    return new ResponseEntity<>(s3Service.getAllTodos(), HttpStatus.OK);
  }

  @PostMapping(
      path = "",
      consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<List<Image>> saveTodo(@RequestParam("title") String title,
                                              @RequestParam("description") String description,
                                              @RequestParam("file") MultipartFile[] files,
                                              @RequestParam("front_cmnd") MultipartFile front) {
    return new ResponseEntity<>(s3Service.save(title, description, files), HttpStatus.OK);
  }

  @GetMapping(value = "{id}/image/download")
  public byte[] downloadTodoImage(@PathVariable("id") Integer id) {
    return s3Service.downloadTodoImage(id);
  }
}
