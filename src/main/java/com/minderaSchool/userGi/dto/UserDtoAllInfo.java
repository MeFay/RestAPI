package com.minderaSchool.userGi.dto;

import jakarta.persistence.Column;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserDtoAllInfo {
    private Integer id;
    private String username;
    private String password;
}
