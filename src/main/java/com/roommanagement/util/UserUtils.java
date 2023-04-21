package com.roommanagement.util;

import com.roommanagement.security.services.UserDetailsImpl;
import lombok.Data;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Data
@Component
public class UserUtils {
  private final AuthenticationManager authenticationManager;

  public Optional<UserDetailsImpl> getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    return Optional.ofNullable(userDetails);
  }
}
