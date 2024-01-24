package com.minderaSchool.userGi.controller;

import com.minderaSchool.userGi.entity.UserEntity;
import com.minderaSchool.userGi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService service;

    @PostMapping()
    public UserEntity createUser(@RequestBody UserEntity user) {
        return service.createUser(user);
    }

    @GetMapping("/{id}")
    public Optional<UserEntity> getUser(@PathVariable Integer id) {
        return service.getUser(id);
    }

    @GetMapping()
    public List<UserEntity> getUserList() {
        return service.getAllUsers();
    }

    @PutMapping("/{id}")
    public UserEntity update(@PathVariable Integer id, @RequestBody UserEntity user) {
        return service.update(id, user);
    }

    @PatchMapping({"/{id}"})
    public UserEntity patch(@PathVariable Integer id, @RequestBody UserEntity user) {
        return service.patch(id, user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Integer id) {
        service.deleteUser(id);
    }
}
