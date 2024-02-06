package com.minderaSchool.userGi.service;

import com.minderaSchool.userGi.dto.UserDtoAllInfo;
import com.minderaSchool.userGi.dto.UserDtoUsernamePassword;
import com.minderaSchool.userGi.dto.UserDtoGetUsers;
import com.minderaSchool.userGi.entity.UserEntity;
import com.minderaSchool.userGi.exception.EmptyException;
import com.minderaSchool.userGi.exception.ErrorException;
import com.minderaSchool.userGi.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserDtoUsernamePassword createUser(UserDtoUsernamePassword userDto) {
        try {
            if (userDto.getUsername() == null || userDto.getPassword() == null) {
                throw new EmptyException();
            }
        } catch (Exception e) {
            throw new EmptyException();
        }
        UserEntity userEntity = new UserEntity();

        userEntity.setUsername(userDto.getUsername());
        userEntity.setPassword(userDto.getPassword());
        userRepository.save(userEntity);
        return userDto;
    }

    public UserDtoAllInfo getUser(Integer id) throws EmptyException {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new EmptyException());

        return new UserDtoAllInfo(userEntity.getId(), userEntity.getUsername(), userEntity.getPassword());
    }

    public List<UserDtoGetUsers> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userEntity -> new UserDtoGetUsers(userEntity.getId(), userEntity.getUsername()))
                .toList();
    }

    public UserDtoUsernamePassword update(Integer id, UserDtoUsernamePassword newUser) {

        UserEntity oldUser = userRepository.findById(id).orElseThrow(() -> new EmptyException());
        if (newUser.getUsername() == null || newUser.getPassword() == null) {
            throw new EmptyException();
        }

        oldUser = new UserEntity(oldUser.getId(), newUser.getUsername(), newUser.getPassword(), oldUser.getEmail());
        userRepository.save(oldUser);
        return newUser;
    }

    public UserDtoUsernamePassword patch(Integer id, UserDtoUsernamePassword newUser) {

        UserEntity oldUser = userRepository.findById(id).orElseThrow(() -> new EmptyException());

        if (newUser.getUsername() == null && newUser.getPassword() == null) {
            throw new EmptyException();
        }
        if (newUser.getUsername() != null) {
            oldUser.setUsername(newUser.getUsername());
        }

        if (newUser.getPassword() != null) {
            oldUser.setPassword(newUser.getPassword());
        }

        oldUser = new UserEntity(oldUser.getId(), newUser.getUsername(), newUser.getPassword(),oldUser.getEmail());
        userRepository.save(oldUser);
        return newUser;
    }

    public void deleteUser(Integer id) {
        userRepository.findById(id).orElseThrow(() -> new EmptyException());
        userRepository.deleteById(id);
    }
}
