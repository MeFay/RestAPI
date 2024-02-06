package com.minderaSchool.userGi.service;

import com.minderaSchool.userGi.entity.UserEntity;
import com.minderaSchool.userGi.exception.EmptyException;
import com.minderaSchool.userGi.exception.ErrorException;
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
            if (user.getUsername() == null || user.getPassword() == null) {
                throw new EmptyException();
            }
        } catch (Exception e) {
            throw new EmptyException();
        }
        return userRepository.save(user);
    }

    public Optional<UserEntity> getUser(Integer id) throws EmptyException {
        return Optional.ofNullable(userRepository.findById(id).orElseThrow(() -> new ErrorException()));
    }

    public List<UserEntity> getAllUsers() {
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            throw new ErrorException();
        }
    }

    public UserEntity update(Integer id, UserEntity newUser) {

        if (newUser.getUsername() == null || newUser.getPassword() == null) {
            throw new EmptyException();
        }

        UserEntity oldUser = userRepository.findById(id).orElseThrow(() -> new ErrorException());

        oldUser.setUsername(newUser.getUsername());
        oldUser.setPassword(newUser.getPassword());
        userRepository.save(oldUser);
        return oldUser;
    }

    public UserEntity patch(Integer id, UserEntity newUser) {
        try {
            if (newUser.getUsername() == null && newUser.getPassword() == null) {
                throw new EmptyException();
            }

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
            throw new ErrorException();
        }
    }

    public void deleteUser(Integer id) {
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            throw new ErrorException();
        }
    }
}
