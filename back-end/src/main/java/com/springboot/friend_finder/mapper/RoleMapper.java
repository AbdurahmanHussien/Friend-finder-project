package com.springboot.friend_finder.mapper;

import com.springboot.friend_finder.dto.authDto.RoleDto;
import com.springboot.friend_finder.entity.auth.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
@Mapper(componentModel = "spring")
public interface RoleMapper {


	@Mapping(target ="roleType", source = "roleType")
	RoleDto toDto(Role role);

	@Mapping(target ="roleType", source = "roleType")
	Role toEntity(RoleDto roleDto);


	List<Role> toEntityList(List<RoleDto> roleDtos);

	List<RoleDto> toDtoList(List<Role> roles);
}
