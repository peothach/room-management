package com.roommanagement.exception;

import com.roommanagement.dto.response.BaseResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = Exception.class)
  public ResponseEntity<BaseResponseDto<?>> handleInternalException(Exception ex, WebRequest request) {
    return ResponseEntity.ok(
        new BaseResponseDto<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
    );
  }

  @ExceptionHandler(value = TokenRefreshException.class)
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<BaseResponseDto<?>> handleTokenRefreshException(TokenRefreshException ex, WebRequest request) {
    return ResponseEntity.ok(
        new BaseResponseDto<>(HttpStatus.FORBIDDEN.value(), ex.getLocalizedMessage())
    );
  }

  @ExceptionHandler(value = ExistingUsernameException.class)
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<BaseResponseDto<?>> handleExistingUsernameException(ExistingUsernameException ex, WebRequest request) {
    return ResponseEntity.ok(
        new BaseResponseDto<>(HttpStatus.BAD_REQUEST.value(), ex.getLocalizedMessage())
    );
  }

  @ExceptionHandler(value = ExistingEmailException.class)
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<BaseResponseDto<?>> handleExistingEmailException(ExistingEmailException ex, WebRequest request) {
    return ResponseEntity.ok(
        new BaseResponseDto<>(HttpStatus.BAD_REQUEST.value(), ex.getLocalizedMessage())
    );
  }

  @ExceptionHandler(value = RoleNotFoundException.class)
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<BaseResponseDto<?>> handleRoleNotFoundException(RoleNotFoundException ex, WebRequest request) {
    return ResponseEntity.ok(
        new BaseResponseDto<>(HttpStatus.BAD_REQUEST.value(), ex.getLocalizedMessage())
    );
  }

  @ExceptionHandler(value = BadCredentialsException.class)
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<BaseResponseDto<?>> handleBadCredentialsException(BadCredentialsException ex, WebRequest request) {
    return ResponseEntity.ok(
        new BaseResponseDto<>(HttpStatus.BAD_REQUEST.value(), ex.getLocalizedMessage())
    );
  }
}
