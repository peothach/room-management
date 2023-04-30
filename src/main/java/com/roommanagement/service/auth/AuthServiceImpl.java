package com.roommanagement.service.auth;

import com.roommanagement.dto.request.LoginRequest;
import com.roommanagement.dto.request.SignupRequest;
import com.roommanagement.dto.request.TokenRefreshRequest;
import com.roommanagement.dto.response.BaseResponseDto;
import com.roommanagement.dto.response.JwtResponse;
import com.roommanagement.dto.response.TokenRefreshResponse;
import com.roommanagement.entity.RefreshToken;
import com.roommanagement.entity.Role;
import com.roommanagement.entity.User;
import com.roommanagement.exception.ExistingEmailException;
import com.roommanagement.exception.ExistingUsernameException;
import com.roommanagement.exception.RoleNotFoundException;
import com.roommanagement.exception.TokenRefreshException;
import com.roommanagement.repository.RoleRepository;
import com.roommanagement.repository.user.UserRepository;
import com.roommanagement.security.jwt.JwtUtils;
import com.roommanagement.security.services.RefreshTokenService;
import com.roommanagement.security.services.UserDetailsImpl;
import com.roommanagement.valueoject.ERole;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
  private final AuthenticationManager authenticationManager;
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder encoder;
  private final JwtUtils jwtUtils;
  private final RefreshTokenService refreshTokenService;
  private final String EXISTING_USERNAME = "Username is already taken!";
  private final String EXISTING_EMAIL = "Email is already in use!";
  private final String ROLE_NOT_FOUND = "Role is not found!";
  private final String REFRESH_TOKEN_INVALID = "Refresh token is invalid";
  private final String MOD = "mod";

  private static List<String> getRoles(UserDetailsImpl userDetails) {
    return userDetails.getAuthorities()
        .stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.toList());
  }

  @Override
  public BaseResponseDto<JwtResponse> authenticateUser(LoginRequest loginRequest) {
    Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

    String jwt = jwtUtils.generateJwtToken(userDetails);

    List<String> roles = getRoles(userDetails);

    RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

    return new BaseResponseDto<>(
        HttpStatus.OK.value(),
        HttpStatus.OK.getReasonPhrase(),
        new JwtResponse(jwt, refreshToken.getToken(), userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles)
    );
  }

  @Override
  public void registerUser(SignupRequest signUpRequest) {
    String username = signUpRequest.getUsername();
    String email = signUpRequest.getEmail();

    validateUsernameAndEmail(username, email);

    User user = createUser(username, email, signUpRequest.getPassword());

    Set<String> rolesIsGranted = signUpRequest.getRole();
    Set<Role> roles = getRoles(rolesIsGranted);

    user.setRoles(roles);
    userRepository.save(user);
  }

  @Override
  public BaseResponseDto<TokenRefreshResponse> refreshToken(TokenRefreshRequest request) {
    String requestRefreshToken = request.getRefreshToken();

    TokenRefreshResponse tokenRefreshResponse = refreshTokenService.findByToken(requestRefreshToken)
        .map(refreshTokenService::verifyExpiration)
        .map(RefreshToken::getUser)
        .map(user -> {
          String token = jwtUtils.generateTokenFromUsername(user.getUsername());
          return new TokenRefreshResponse(token, requestRefreshToken);
        })
        .orElseThrow(() -> new TokenRefreshException(REFRESH_TOKEN_INVALID));

    return new BaseResponseDto<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), tokenRefreshResponse);
  }

  @Override
  public void logoutUser() {
    UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Integer userId = userDetails.getId();
    refreshTokenService.deleteByUserId(userId);
  }

  private void validateUsernameAndEmail(String username, String email) {
    if (userRepository.existsByUsername(username)) {
      throw new ExistingUsernameException(EXISTING_USERNAME);
    }
    if (userRepository.existsByEmail(email)) {
      throw new ExistingEmailException(EXISTING_EMAIL);
    }
  }

  private User createUser(String username, String email, String password) {
    User user = new User(username, email, encoder.encode(password));
    return user;
  }

  private Set<Role> getRoles(Set<String> rolesIsGranted) {
    Set<Role> roles = new HashSet<>();
    if (rolesIsGranted == null || rolesIsGranted.isEmpty()) {
      grantRole(ERole.ROLE_USER, roles);
    } else {
      rolesIsGranted.forEach(role -> {
        if (MOD.equals(role)) {
          grantRole(ERole.ROLE_MODERATOR, roles);
        } else {
          grantRole(ERole.ROLE_ADMIN, roles);
        }
      });
    }
    return roles;
  }

  private void grantRole(ERole roleUser, Set<Role> roles) {
    Role userRole = roleRepository
        .findByName(roleUser)
        .orElseThrow(() -> new RoleNotFoundException(ROLE_NOT_FOUND));

    roles.add(userRole);
  }
}
