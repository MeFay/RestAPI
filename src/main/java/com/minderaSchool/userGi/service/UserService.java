package com.minderaSchool.userGi.service;

import com.minderaSchool.userGi.dto.UserDtoAllInfo;
import com.minderaSchool.userGi.dto.UserDtoUsernamePassword;
import com.minderaSchool.userGi.dto.UserDto;
import com.minderaSchool.userGi.dto.UserDtoWithoutEmail;
import com.minderaSchool.userGi.entity.UserEntity;
import com.minderaSchool.userGi.exception.BodyNotCompleteException;
import com.minderaSchool.userGi.exception.UserNotFoundException;
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
            if (userDto.getUsername() == null || userDto.getPassword() == null || userDto.getEmail() == null) {
                throw new BodyNotCompleteException();
            }
        } catch (Exception e) {
            throw new BodyNotCompleteException();
        }
        UserEntity userEntity = convertUserDtoToUserEntity(userDto);
        UserEntity userEntitySaved = userRepository.save(userEntity);
        return convertUserEntityToUserDto(userEntitySaved);

    }

    private UserEntity convertUserDtoToUserEntity(UserDtoUsernamePassword userDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userDto.getUsername());
        userEntity.setPassword(userDto.getPassword());
        userEntity.setEmail(userDto.getEmail());

        return userEntity;
    }

    private UserDtoUsernamePassword convertUserEntityToUserDto(UserEntity userEntity) {
        UserDtoUsernamePassword userDto = new UserDtoUsernamePassword();
        userDto.setId(userEntity.getId());
        userDto.setUsername(userEntity.getUsername());
        userDto.setPassword(userEntity.getPassword());
        userDto.setEmail(userEntity.getEmail());

        return userDto;
    }

    public UserDtoAllInfo getUser(Integer id) throws BodyNotCompleteException {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        return new UserDtoAllInfo(userEntity.getId(), userEntity.getUsername(), userEntity.getPassword(), userEntity.getEmail());
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userEntity -> new UserDto(userEntity.getId(), userEntity.getUsername()))
                .toList();
    }

    public UserDtoWithoutEmail update(Integer id, UserDtoWithoutEmail newUser) {
        UserEntity oldUser = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        if (newUser.getUsername() == null || newUser.getPassword() == null) {
            throw new BodyNotCompleteException();
        }
        oldUser = new UserEntity(oldUser.getId(), newUser.getUsername(), newUser.getPassword(), oldUser.getEmail());
        userRepository.save(oldUser);
        return newUser;
    }

    public UserDtoWithoutEmail patch(Integer id, UserDtoWithoutEmail newUser) {
        if (newUser == null) {
            throw new IllegalArgumentException("newUser cannot be null");
        }
        UserEntity oldUser = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        if (newUser.getUsername() == null || newUser.getPassword() == null) {
            throw new BodyNotCompleteException();
        }
        if (newUser.getUsername() != null && !newUser.getUsername().isEmpty()) {
            oldUser.setUsername(newUser.getUsername());
        }
        if (newUser.getPassword() != null && !newUser.getPassword().isEmpty()) {
            oldUser.setPassword(newUser.getPassword());
        }
        userRepository.save(oldUser);
        return newUser;
    }


    public void deleteUser(Integer id) {
        userRepository.findById(id).orElseThrow(BodyNotCompleteException::new);
        userRepository.deleteById(id);
    }
}
