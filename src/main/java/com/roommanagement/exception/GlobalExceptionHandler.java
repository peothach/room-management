package com.roommanagement.exception;

import com.roommanagement.dto.response.BaseResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = Exception.class)
  public ResponseEntity<BaseResponseDto<?>> handleInternalException(Exception ex, WebRequest request) {
    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(new BaseResponseDto<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()));
  }

  @ExceptionHandler(value = TokenRefreshException.class)
  public ResponseEntity<BaseResponseDto<?>> handleTokenRefreshException(TokenRefreshException ex, WebRequest request) {
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(new BaseResponseDto<>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase()));
  }

  @ExceptionHandler(value = ExistingUsernameException.class)
  public ResponseEntity<BaseResponseDto<?>> handleExistingUsernameException(ExistingUsernameException ex, WebRequest request) {
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(new BaseResponseDto<>(HttpStatus.BAD_REQUEST.value(), ex.getLocalizedMessage()));
  }

  @ExceptionHandler(value = ExistingEmailException.class)
  public ResponseEntity<BaseResponseDto<?>> handleExistingEmailException(ExistingEmailException ex, WebRequest request) {
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(new BaseResponseDto<>(HttpStatus.BAD_REQUEST.value(), ex.getLocalizedMessage()));
  }

  @ExceptionHandler(value = RoleNotFoundException.class)
  public ResponseEntity<BaseResponseDto<?>> handleRoleNotFoundException(RoleNotFoundException ex, WebRequest request) {
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(new BaseResponseDto<>(HttpStatus.BAD_REQUEST.value(), ex.getLocalizedMessage()));
  }

  @ExceptionHandler(value = BadCredentialsException.class)
  public ResponseEntity<BaseResponseDto<?>> handleBadCredentialsException(BadCredentialsException ex, WebRequest request) {
    return ResponseEntity
        .status(HttpStatus.UNAUTHORIZED)
        .body(new BaseResponseDto<>(HttpStatus.UNAUTHORIZED.value(), ex.getLocalizedMessage()));
  }
}
