package com.springboot.friend_finder.request;

import lombok.Builder;

@Builder
public record LoginRequest(  String email, String password) {

}
