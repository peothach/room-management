package com.roommanagement.repository.image;

import com.roommanagement.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Integer> {
  Optional<Image> findByLodgerIdAndTitle(Integer lodgerId, String title);
}
