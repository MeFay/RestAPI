package com.minderaSchool.userGi.service;

import com.minderaSchool.userGi.entity.UserEntity;
import com.minderaSchool.userGi.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserEntity createUser(UserEntity user) {
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Error creating user, the username and/or password field cannot be empty or invalid", e);
        }
    }

    public Optional<UserEntity> getUser(Integer id) {
        try {
            return userRepository.findById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error getting user, the username and/or password field cannot be empty or invalid", e);
        }
    }

    public List<UserEntity> getAllUsers() {
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error getting all users", e);
        }
    }

    public UserEntity update(Integer id, UserEntity newUser) {
        try {
            UserEntity oldUser = userRepository.getReferenceById(id);
            oldUser.setUsername(newUser.getUsername());
            oldUser.setPassword(newUser.getPassword());
            userRepository.save(oldUser);
            return oldUser;
        } catch (Exception e) {
            throw new RuntimeException("Error updating user, the username and/or password field cannot be empty or invalid", e);
        }
    }

    public UserEntity patch(Integer id, UserEntity newUser) {
        try {
            UserEntity oldUser = userRepository.getReferenceById(id);
            if (newUser.getUsername() != null) {
                oldUser.setUsername(newUser.getUsername());
            }
            if (newUser.getPassword() != null) {
                oldUser.setPassword(newUser.getPassword());
            }
            userRepository.save(oldUser);
            return oldUser;
        } catch (Exception e) {
            throw new RuntimeException("Error patching user, the field must not be null or invalid", e);
        }
    }

    public void deleteUser(Integer id) {
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting user, the field must not be null or invalid", e);
        }
    }
}
