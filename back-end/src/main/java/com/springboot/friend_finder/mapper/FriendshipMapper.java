package com.springboot.friend_finder.mapper;

import com.springboot.friend_finder.dto.FriendshipDto;
import com.springboot.friend_finder.entity.Friendship;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FriendshipMapper {


	@Mapping(target = "sender.id", source = "senderId")
	@Mapping(target = "receiver.id", source = "receiverId")
	Friendship toEntity(FriendshipDto friendshipDto);

	@Mapping(target = "senderId", source = "sender.id")
	@Mapping(target = "receiverId", source = "receiver.id")
	FriendshipDto toDto(Friendship friendship);

	@Mapping(target = "senderId", source = "sender.id")
	@Mapping(target = "receiverId", source = "receiver.id")
	List<FriendshipDto> toDtoList(List<Friendship> friendships);

	List<Friendship> toEntityList(List<FriendshipDto> friendshipDtos);
}
