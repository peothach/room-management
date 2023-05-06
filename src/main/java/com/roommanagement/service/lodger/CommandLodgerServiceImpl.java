package com.roommanagement.service.lodger;

import com.roommanagement.dto.request.lodger.LodgerRequest;
import com.roommanagement.dto.response.BaseResponseDto;
import com.roommanagement.entity.Image;
import com.roommanagement.entity.Lodger;
import com.roommanagement.entity.Room;
import com.roommanagement.repository.image.ImageRepository;
import com.roommanagement.repository.lodger.LodgerRepository;
import com.roommanagement.repository.room.RoomRepository;
import com.roommanagement.service.azure.StorageService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import static org.apache.http.entity.ContentType.*;

@Service
@RequiredArgsConstructor
public class CommandLodgerServiceImpl implements CommandLodgerService {
  private final ImageRepository imageRepository;
  private final LodgerRepository lodgerRepository;
  private final RoomRepository roomRepository;
  private final StorageService storageService;
  private final ModelMapper mapper;

  @Override
  public BaseResponseDto<?> save(Integer roomId, LodgerRequest lodgerRequest) {
    Room room = roomRepository.findById(roomId).orElseThrow(RuntimeException::new);
    Lodger lodger = mapper.map(lodgerRequest, Lodger.class);
    lodger.setActive(true);
    lodger.setRoom(room);
    Lodger persistedLodger = lodgerRepository.saveAndFlush(lodger);

    storeImage(lodgerRequest.getFront(), "front", persistedLodger);
    storeImage(lodgerRequest.getBack(), "back", persistedLodger);
    return new BaseResponseDto<>(HttpStatus.NO_CONTENT.value(), HttpStatus.NO_CONTENT.getReasonPhrase());
  }

  @Override
  public BaseResponseDto<?> update(Integer lodgerId, LodgerRequest lodgerRequest) {
    Lodger lodger = lodgerRepository.findById(lodgerId).orElseThrow(RuntimeException::new);
    lodger.setName(lodgerRequest.getName());
    lodger.setEmail(lodgerRequest.getEmail());
    lodger.setPhoneNumber(lodgerRequest.getPhoneNumber());

    Optional<Image> imageFront = imageRepository.findByLodgerIdAndTitle(lodgerId, "front");
    Optional<Image> imageBack = imageRepository.findByLodgerIdAndTitle(lodgerId, "back");
    if (imageFront.isEmpty()) {
      storeImage(lodgerRequest.getFront(), "front", lodger);
    } else {
      storeImage(lodgerRequest.getFront(), imageFront.get());
    }

    if (imageBack.isEmpty()) {
      storeImage(lodgerRequest.getBack(), "back", lodger);
    } else {
      storeImage(lodgerRequest.getBack(), imageBack.get());
    }

    return new BaseResponseDto<>(HttpStatus.NO_CONTENT.value(), HttpStatus.NO_CONTENT.getReasonPhrase());
  }

  @Override
  public BaseResponseDto<?> delete(Integer lodgerId) {
    Lodger lodger = lodgerRepository.findById(lodgerId).orElseThrow(RuntimeException::new);
    lodger.setActive(false);
    lodgerRepository.save(lodger);

    return new BaseResponseDto<>(HttpStatus.NO_CONTENT.value(), HttpStatus.NO_CONTENT.getReasonPhrase());
  }

  private void storeImage(MultipartFile file, String title, Lodger lodger) {
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
    //Upload image in Azure Blob Storage and then save
    try {
      String url = storageService.uploadImage(file.getInputStream(), file.getOriginalFilename(), file.getSize());
      Image image = Image.builder()
          .title(title)
          .imageFileName(file.getOriginalFilename())
          .lodger(lodger)
          .url(url)
          .build();
      imageRepository.save(image);
    } catch (IOException e) {
      throw new IllegalStateException("Failed to upload file", e);
    }
  }

  private void storeImage(MultipartFile file, Image image) {
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

    //Upload image in Azure Blob Storage and then save
    try {
      String url = storageService.uploadImage(file.getInputStream(), file.getOriginalFilename(), file.getSize());
      image.setImageFileName(file.getOriginalFilename());
      image.setUrl(url);
      imageRepository.save(image);
    } catch (IOException e) {
      throw new IllegalStateException("Failed to upload file", e);
    }
  }
}
