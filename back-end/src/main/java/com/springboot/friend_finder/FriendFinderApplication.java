package com.springboot.friend_finder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class FriendFinderApplication {

	public static void main(String[] args) {
		SpringApplication.run(FriendFinderApplication.class, args);
	}

}
