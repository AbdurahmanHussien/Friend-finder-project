package com.springboot.friend_finder.dto.authDto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleDto {


        private Long id;

        @Enumerated(EnumType.STRING)
        private String roleName;





}
