package com.springboot.friend_finder.mapper;


import com.springboot.friend_finder.dto.authDto.UserDetailsDto;
import com.springboot.friend_finder.entity.auth.UserDetails;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserDetailsMapper {


	UserDetailsDto toDto(UserDetails userDetails);

	UserDetails toEntity(UserDetailsDto userDetailsDto);

	List<UserDetailsDto> toDtoList(List<UserDetails> userDetailsList);

}
