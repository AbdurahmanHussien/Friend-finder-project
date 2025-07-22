package com.springboot.friend_finder.service.auth;


import com.springboot.friend_finder.config.jwt.JwtUtils;
import com.springboot.friend_finder.constant.RoleType;
import com.springboot.friend_finder.entity.auth.Role;
import com.springboot.friend_finder.entity.auth.User;
import com.springboot.friend_finder.entity.auth.UserDetails;
import com.springboot.friend_finder.exceptions.DuplicateFieldException;
import com.springboot.friend_finder.exceptions.ResourceNotFoundException;
import com.springboot.friend_finder.repository.auth.RoleRepository;
import com.springboot.friend_finder.repository.auth.UserRepository;
import com.springboot.friend_finder.request.LoginRequest;
import com.springboot.friend_finder.request.RegisterRequest;
import com.springboot.friend_finder.response.AuthResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;


@Service
@AllArgsConstructor
public class AuthenticationService implements IAuthenticationService {

        private final UserRepository userRepository;
        private final RoleRepository roleRepository;
        private final JwtUtils jwtUtils;
        private final PasswordEncoder passwordEncoder;
        private final AuthenticationManager authenticationManager;
        private final CustomUserDetailsService customUserDetailsService;



    @Override
    public User getUserById(Long id) {
       return userRepository.findById(id)
               .orElseThrow(()-> new ResourceNotFoundException("user.not.found"));
    }

    @Override
    public void resetPassword(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("user.not.found"));
        user.setPassword(passwordEncoder.encode("Hello@1234"));
        userRepository.save(user);
    }

    @Override
    public AuthResponse createUser(RegisterRequest request) {

        UserDetails userDetails = UserDetails.builder()
                .name(request.name())
                .phoneNum(request.phoneNum())
                .gender(request.gender())
                .build();

        Optional<User> getUser= userRepository.findByEmail(request.email());
        if (getUser.isPresent()) {
            throw new DuplicateFieldException("email.exists");
        }

        Role userRole = roleRepository.findByRoleType(RoleType.USER)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

        User user = User.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password() != null && !request.password().isEmpty() ? request.password() : "Hello@1234"))
                .userDetails(userDetails)
                .roles(Set.of((userRole)))
                .build();

        userDetails.setUser(user);

      User savedUser =  userRepository.save(user);

        if (savedUser.getId() == null) {
            throw new RuntimeException("User not saved");
        }

        List<String> roles = user.getRoles().stream().map(Role::getRoleType).map(RoleType::name).toList();

        String accessToken = jwtUtils.generateToken(user, 24 * 60 * 60 * 1000);
        String refreshToken = jwtUtils.generateToken(user, 7 * 24 * 60 * 60 * 1000);



        return AuthResponse.builder()
                .userId(savedUser.getId())
                .token(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public AuthResponse loginUser(LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new AuthenticationServiceException("email.not.found"));
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.email(),
                            request.password()
                    )
            );
        } catch (BadCredentialsException ex) {
            throw new BadCredentialsException("Invalid password");
        }
        List<String> roles = user.getRoles().stream().map(Role::getRoleType).map(RoleType::name).toList();

        String accessToken = jwtUtils.generateToken(user,  7* 30 * 60 * 1000);  // untill testing                          // 30 minutes
        String refreshToken = jwtUtils.generateToken(user, 7 * 24 * 60 * 60 * 1000);

        return AuthResponse.builder()
                    .userId(user.getId())
                    .token(accessToken)
                    .refreshToken(refreshToken)
                    .build();
        }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("user.not.found"));
        userRepository.delete(user);
    }

    public ResponseEntity<?> refreshAccessToken(String refreshToken) {
        try {
            String email = jwtUtils.extractUsername(refreshToken);

            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new ResourceNotFoundException("user.not.found"));
            CustomUserDetails customUserDetails = (CustomUserDetails) customUserDetailsService.loadUserByUsername(email);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
            }

            if (jwtUtils.isTokenValid(refreshToken, customUserDetails)) {
                String newAccessToken = jwtUtils.generateToken(user,    30 * 60 * 1000);   // 30 minutes
                return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
    }




}
