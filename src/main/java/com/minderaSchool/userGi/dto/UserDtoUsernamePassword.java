package com.minderaSchool.userGi.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDtoUsernamePassword extends UserDto {
    private String username;
    private String password;
    private String email;
}