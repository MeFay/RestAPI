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

    UserRepository userRepository;

    public UserEntity createUser(UserEntity user) {
        return userRepository.save(user);
    }

    public Optional<UserEntity> getUser(Integer id) {
        return userRepository.findById(id);
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .toList();
    }

    public UserEntity update(Integer id, UserEntity newUser) {
        UserEntity oldUser = userRepository.getReferenceById(id);
        oldUser.setUsername(newUser.getUsername());
        oldUser.setPassword(newUser.getPassword());
        userRepository.save(oldUser);
        return oldUser;
    }

    public UserEntity patch(Integer id, UserEntity newUser) {
       UserEntity oldUser = userRepository.getReferenceById(id);
       if (newUser.getUsername() != null){
           oldUser.setUsername((newUser.getUsername()));
       }
        if (newUser.getPassword() != null){
            oldUser.setPassword((newUser.getPassword()));
        }
        userRepository.save(oldUser);
        return oldUser;
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }


}
