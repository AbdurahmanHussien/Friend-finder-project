package com.springboot.friend_finder.config.jwt;

import com.springboot.friend_finder.service.auth.CustomUserDetails;
import com.springboot.friend_finder.service.auth.CustomUserDetailsService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class AuthChannelInterceptor implements ChannelInterceptor {

	private  JwtUtils jwtUtil;

	private  CustomUserDetailsService userDetailsService;


	@Autowired
	public AuthChannelInterceptor(JwtUtils jwtUtil, CustomUserDetailsService userDetailsService) {
		this.jwtUtil = jwtUtil;
		this.userDetailsService = userDetailsService;
	}



	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {

		StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

		// بنعترض بس رسائل الـ CONNECT
		if (StompCommand.CONNECT.equals(accessor.getCommand())) {
			// بنقرأ الـ header بتاع الـ Authorization
			String authHeader = accessor.getFirstNativeHeader("Authorization");
			if (authHeader != null && authHeader.startsWith("Bearer ")) {
				String jwt = authHeader.substring(7);
				String email = jwtUtil.extractUsername(jwt);


				if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
					CustomUserDetails customUserDetails = userDetailsService.loadUserByUsername(email);
					if (jwtUtil.isTokenValid(jwt, customUserDetails)) {

						UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
								email, null, customUserDetails.getAuthorities());
						accessor.setUser(authentication);
					}
				}
			}
			}
		System.out.println("Auth Interceptor Triggered for CONNECT");
		System.out.println("Authorization headers: " + accessor.getNativeHeader("Authorization"));

		return message;
		}

}