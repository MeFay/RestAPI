package com.minderaSchool.userGi.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDtoWithoutEmail extends UserDto {
    private String username;
    private String password;
}