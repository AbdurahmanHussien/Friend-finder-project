package com.springboot.friend_finder.mapper;

import com.springboot.friend_finder.dto.FriendshipDto;
import com.springboot.friend_finder.entity.Friendship;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface FriendshipMapper {


	@Mapping(target = "sender", source = "sender")
	@Mapping(target = "receiver.id", source = "receiverId")
	Friendship toEntity(FriendshipDto friendshipDto);

	@Mapping(target = "sender", source = "sender")
	@Mapping(target = "receiverId", source = "receiver.id")
	FriendshipDto toDto(Friendship friendship);

	@Mapping(target = "sender", source = "sender")
	@Mapping(target = "receiverId", source = "receiver.id")
	List<FriendshipDto> toDtoList(List<Friendship> friendships);

	List<Friendship> toEntityList(List<FriendshipDto> friendshipDtos);
}
