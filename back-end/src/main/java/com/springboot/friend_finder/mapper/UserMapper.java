package com.springboot.friend_finder.mapper;

import com.springboot.friend_finder.dto.authDto.UserDto;
import com.springboot.friend_finder.entity.auth.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = { RoleMapper.class, UserDetailsMapper.class, FriendshipMapper.class })
public interface UserMapper {

	@Mapping(target = "roles", source = "roles")
	@Mapping(target = "userDetails", source = "userDetails")
	@Mapping(target = "sentRequests",  source = "sentRequests")
	@Mapping(target = "receivedRequests",  source = "receivedRequests")
	UserDto toDto(User user);

	@Mapping(target = "roles", source = "roles")
	@Mapping(target = "userDetails", source = "userDetails")
	@Mapping(target = "sentRequests",  source = "sentRequests")
	@Mapping(target = "receivedRequests",  source = "receivedRequests")
	User toEntity(UserDto userDto);

	@Mapping(target = "userDetails", source = "userDetails")
	@Mapping(target = "roles", source = "roles")
	@Mapping(target = "sentRequests",  source = "sentRequests")
	@Mapping(target = "receivedRequests",  source = "receivedRequests")
	List<UserDto> toDtoList(List<User> users);

	@Mapping(target = "roles", source = "roles")
	@Mapping(target = "sentRequests", ignore = true)
	@Mapping(target = "receivedRequests", ignore = true)
	List<User> toEntityList(List<UserDto> userDtos);


}

