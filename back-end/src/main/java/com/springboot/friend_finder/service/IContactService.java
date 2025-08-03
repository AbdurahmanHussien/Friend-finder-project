package com.springboot.friend_finder.service;


import com.springboot.friend_finder.dto.ContactInfoDto;

import java.util.List;


public interface IContactService {


        ContactInfoDto addInfo(ContactInfoDto contactInfoDto);
        List<ContactInfoDto> getAllInfo();
        List<ContactInfoDto> getAllInfoByUserId(Long userId);



}
