package com.roommanagement.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roommanagement.dto.response.BaseResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
      throws IOException, ServletException {
    log.error("Unauthorized error: {}", authException.getMessage());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setStatus(HttpServletResponse.SC_OK);

    BaseResponseDto<?> errorMessage = new BaseResponseDto();
    errorMessage.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    errorMessage.setMessage("Unauthorized");
    errorMessage.setData(null);

    final ObjectMapper mapper = new ObjectMapper();
    mapper.writeValue(response.getOutputStream(), errorMessage);
  }

}
