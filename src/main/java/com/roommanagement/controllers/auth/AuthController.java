package com.roommanagement.controllers.auth;

import com.roommanagement.dto.request.LoginRequest;
import com.roommanagement.dto.request.SignupRequest;
import com.roommanagement.dto.request.TokenRefreshRequest;
import com.roommanagement.dto.response.BaseResponseDto;
import com.roommanagement.dto.response.JwtResponse;
import com.roommanagement.dto.response.TokenRefreshResponse;
import com.roommanagement.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  private final AuthService authService;

  @PostMapping("/sign-in")
  public ResponseEntity<BaseResponseDto<JwtResponse>> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
    return ResponseEntity.ok(authService.authenticateUser(loginRequest));
  }

  @PostMapping("/sign-up")
  public ResponseEntity<BaseResponseDto<?>> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    authService.registerUser(signUpRequest);
    return ResponseEntity.ok(
        new BaseResponseDto<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase())
    );
  }

  @PostMapping("/refresh-token")
  public ResponseEntity<BaseResponseDto<TokenRefreshResponse>> refreshToken(@Valid @RequestBody TokenRefreshRequest tokenRefreshRequest) {
    return ResponseEntity.ok(authService.refreshToken(tokenRefreshRequest));
  }

  @PostMapping("/sign-out")
  public ResponseEntity<BaseResponseDto<?>> logoutUser() {
    authService.logoutUser();
    return ResponseEntity.ok(
        new BaseResponseDto<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase())
    );
  }

}
