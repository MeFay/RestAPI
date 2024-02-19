package com.minderaSchool.userGi.controller;

import com.minderaSchool.userGi.dto.UserDtoAllInfo;
import com.minderaSchool.userGi.dto.UserDtoUsernamePassword;
import com.minderaSchool.userGi.dto.UserDto;
import com.minderaSchool.userGi.dto.UserDtoWithoutEmail;
import com.minderaSchool.userGi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService service;

    @PostMapping()
    public UserDtoUsernamePassword createUser(@RequestBody UserDtoUsernamePassword user) {
        return service.createUser(user);
    }

    @GetMapping("/{id}")
    public UserDtoAllInfo getUser(@PathVariable Integer id) {
        return service.getUser(id);
    }

    @GetMapping()
    public List<UserDto> getUserList() {
        return service.getAllUsers();
    }

    @PutMapping("/{id}")
    public UserDtoWithoutEmail update(@PathVariable Integer id, @RequestBody UserDtoWithoutEmail user) {
        return service.update(id, user);
    }

    @PatchMapping({"/{id}"})
    public UserDtoWithoutEmail patch(@PathVariable Integer id, @RequestBody UserDtoWithoutEmail user) {
        return service.patch(id, user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Integer id) {
        service.deleteUser(id);
    }
}
