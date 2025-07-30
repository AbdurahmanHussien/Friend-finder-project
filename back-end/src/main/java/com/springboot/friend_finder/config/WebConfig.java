package com.springboot.friend_finder.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Value("${file.upload-dir}")
	private String uploadDir;

	@Value("${file.avatar-dir}")
	private String uploadAvatarDir;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/posts/**")
				.addResourceLocations("file:" + uploadDir + "/");

		registry.addResourceHandler("/avatars/**")
				.addResourceLocations("file:" + uploadAvatarDir + "/");
	}
}
