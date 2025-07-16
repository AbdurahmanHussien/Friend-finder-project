package com.springboot.friend_finder.service.auth;


import com.springboot.friend_finder.entity.auth.User;
import com.springboot.friend_finder.request.LoginRequest;
import com.springboot.friend_finder.request.RegisterRequest;
import com.springboot.friend_finder.response.AuthResponse;

public interface IAuthenticationService {

    AuthResponse createUser(RegisterRequest user);

    AuthResponse loginUser(LoginRequest user);

    User getUserById(Long id);

   void resetPassword(String email);
}
