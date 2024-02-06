package com.minderaSchool.userGi.controller;

import com.minderaSchool.userGi.dto.UserDtoAllInfo;
import com.minderaSchool.userGi.dto.UserDtoUsernamePassword;
import com.minderaSchool.userGi.dto.UserDtoGetUsers;
import com.minderaSchool.userGi.entity.UserEntity;
import com.minderaSchool.userGi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService service;

    @PostMapping()
    public UserDtoUsernamePassword createUser(@RequestBody UserDtoUsernamePassword user) {
        return service.createUser(user);
    }

    @GetMapping("/{id}")
    public UserDtoAllInfo getUser(@PathVariable Integer id) {
        return service.getUser(id);
    }

    @GetMapping()
    public List<UserDtoGetUsers> getUserList() {
        return service.getAllUsers();
    }

    @PutMapping("/{id}")
    public UserDtoUsernamePassword update(@PathVariable Integer id, @RequestBody UserDtoUsernamePassword user) {
        return service.update(id, user);
    }

    @PatchMapping({"/{id}"})
    public UserDtoUsernamePassword patch(@PathVariable Integer id, @RequestBody UserDtoUsernamePassword user) {
        return service.patch(id, user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Integer id) {
        service.deleteUser(id);
    }
}
