package com.springboot.friend_finder.mapper;


import com.springboot.friend_finder.dto.ContactInfoDto;
import com.springboot.friend_finder.entity.ContactInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ContactInfoMapper {


    @Mapping(source = "user.id", target = "userId")
    ContactInfoDto contactToContactDTO(ContactInfo contactInfo);


    @Mapping(source = "userId", target = "user.id")
    ContactInfo contactDTOToContact(ContactInfoDto dto);

}
