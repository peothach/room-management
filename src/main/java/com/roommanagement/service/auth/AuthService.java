package com.roommanagement.service.auth;

import com.roommanagement.dto.request.LoginRequest;
import com.roommanagement.dto.request.SignupRequest;
import com.roommanagement.dto.request.TokenRefreshRequest;
import com.roommanagement.dto.response.BaseResponseDto;
import com.roommanagement.dto.response.JwtResponse;
import com.roommanagement.dto.response.TokenRefreshResponse;

public interface AuthService {
  BaseResponseDto<JwtResponse> authenticateUser(LoginRequest loginRequest);
  void registerUser(SignupRequest signUpRequest);
  BaseResponseDto<TokenRefreshResponse> refreshToken(TokenRefreshRequest request);
  void logoutUser();

}
