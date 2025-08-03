package com.springboot.friend_finder.request;

import com.springboot.friend_finder.constant.Gender;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record RegisterRequest(  @NotBlank(message = "user.name.notblank")
                                String name,

                                @NotBlank(message = "password.notblank")
                                @Size(min = 8, max = 20, message = "password.size")
                                @Pattern(
                                        regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
                                        message = "password.pattern"
                                )
                               String password,
                                @NotBlank(message = "phoneNum.notblank")
                                @Pattern(
                                        regexp = "^01[0-2,5]{1}[0-9]{8}$",
                                        message = "egypt.phoneNum.pattern"
                                )
                               String phoneNum,
                                @NotBlank(message = "email.notblank")
                                @Email(message = "email.valid")
                               String email,

                               LocalDate birthDate,

                                Gender gender

)
{}
