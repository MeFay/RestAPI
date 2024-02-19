package com.minderaSchool.userGi.UserServiceTest;

import com.minderaSchool.userGi.dto.UserDto;
import com.minderaSchool.userGi.dto.UserDtoAllInfo;
import com.minderaSchool.userGi.dto.UserDtoUsernamePassword;
import com.minderaSchool.userGi.dto.UserDtoWithoutEmail;
import com.minderaSchool.userGi.entity.UserEntity;
import com.minderaSchool.userGi.exception.BodyNotCompleteException;
import com.minderaSchool.userGi.exception.ErrorException;
import com.minderaSchool.userGi.exception.UserNotFoundException;
import com.minderaSchool.userGi.repository.UserRepository;
import com.minderaSchool.userGi.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.mockito.Mockito.*;

@SpringBootTest

public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;


    @Test
    public void getUserById_success() {
        UserEntity userEntity = UserEntity.builder()
                .id(1)
                .username("chipi")
                .password("chapa")
                .email("chipichapa@gmail.com")
                .build();

        when(userRepository.findById(1)).thenReturn(Optional.of(userEntity));

        UserDtoAllInfo returnUserDto = userService.getUser(1);

        Assertions.assertEquals(userEntity.getId(), returnUserDto.getId());
        Assertions.assertEquals(userEntity.getUsername(), returnUserDto.getUsername());
        Assertions.assertEquals(userEntity.getPassword(), returnUserDto.getPassword());
        Assertions.assertEquals(userEntity.getEmail(), returnUserDto.getEmail());

        verify(userRepository, times(1)).findById(1);
    }

    @Test
    public void getUserById_failure() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());
        Assertions.assertThrows(UserNotFoundException.class, () -> {
            userService.getUser(1);
        });
        verify(userRepository, times(1)).findById(1);
    }


    @Test
    public void getAllUsers_success() {
        UserEntity userEntity1 = UserEntity.builder()
                .id(1)
                .username("chipi")
                .password("chapa")
                .email("chipichapa@gmail.com")
                .build();

        UserEntity userEntity2 = UserEntity.builder()
                .id(2)
                .username("abc")
                .password("def")
                .email("abcdef@gmail.com")
                .build();

        List<UserEntity> userList = List.of(userEntity1, userEntity2);

        when(userRepository.findAll()).thenReturn(userList);

        List<UserDto> returnUserDtoList = userService.getAllUsers();

        IntStream.range(0, userList.size())
                .forEach(i -> {
                    UserEntity userEntity = userList.get(i);
                    UserDto returnUserDto = returnUserDtoList.get(i);

                    Assertions.assertEquals(userEntity.getId(), returnUserDto.getId());
                    Assertions.assertEquals(userEntity.getUsername(), returnUserDto.getUsername());
                });
    }

    @Test
    public void getAllUsers_failure() {
        when(userRepository.findAll()).thenThrow(new ErrorException());
        Assertions.assertThrows(ErrorException.class, () -> {
            userService.getAllUsers();
        });
        verify(userRepository, times(1)).findAll();
    }


    @Test
    public void createUser_success() throws Exception {
        UserDtoUsernamePassword user1DTO = new UserDtoUsernamePassword("chipi", "chapa", "chipichapa@gmail.com");

        UserEntity userInput = UserEntity.builder()
                .username(user1DTO.getUsername())
                .password(user1DTO.getPassword())
                .email(user1DTO.getEmail())
                .build();

        UserEntity userOutput = UserEntity.builder()
                .id(0)
                .username(user1DTO.getUsername())
                .password(user1DTO.getPassword())
                .email(user1DTO.getEmail())
                .build();
        when(userRepository.save(userInput)).thenReturn(userOutput);

        UserDtoUsernamePassword returnUserDto = userService.createUser(user1DTO);

        Assertions.assertEquals(userOutput.getUsername(), returnUserDto.getUsername());
        Assertions.assertEquals(userOutput.getPassword(), returnUserDto.getPassword());
        Assertions.assertEquals(userOutput.getEmail(), returnUserDto.getEmail());
        Assertions.assertEquals(userOutput.getId(), returnUserDto.getId());

        verify(userRepository, times(1)).save(userInput);
    }

    @Test
    public void createUser_failure() {
        UserDtoUsernamePassword user1DTO = new UserDtoUsernamePassword("chipi", null, "chipichapa@gmail.com");
        Assertions.assertThrows(BodyNotCompleteException.class, () -> {
            userService.createUser(user1DTO);
        });
        verify(userRepository, never()).save(any());
    }


    @Test
    public void updateUser_success() throws Exception {
        UserEntity oldUser = new UserEntity(1, "hjg", "dubi", "daba");
        UserDtoWithoutEmail updatedUserDto = new UserDtoWithoutEmail("dfdfdfdf", "dadddba");

        when(userRepository.findById(oldUser.getId())).thenReturn(Optional.of(oldUser));
        when(userRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        UserDtoWithoutEmail updatedUser = userService.update(oldUser.getId(), updatedUserDto);


        verify(userRepository, times(1)).findById(oldUser.getId());

        verify(userRepository, times(1)).save(argThat(userEntity ->
                userEntity.getId().equals(oldUser.getId()) &&
                        userEntity.getUsername().equals(updatedUserDto.getUsername()) &&
                        userEntity.getPassword().equals(updatedUserDto.getPassword()) &&
                        userEntity.getEmail().equals(oldUser.getEmail())
        ));

        Assertions.assertEquals(updatedUserDto.getUsername(), updatedUser.getUsername());
        Assertions.assertEquals(updatedUserDto.getPassword(), updatedUser.getPassword());
        Assertions.assertEquals(oldUser.getEmail(), oldUser.getEmail());
        Assertions.assertEquals(oldUser.getId(), oldUser.getId());
    }

    @Test
    public void updateUser_failure() {
        UserEntity oldUser = new UserEntity(1, "hjg", "dubi", "daba");
        UserDtoWithoutEmail updatedUserDto = new UserDtoWithoutEmail(null, "dadddba");
        when(userRepository.findById(oldUser.getId())).thenReturn(Optional.of(oldUser));
        Assertions.assertThrows(BodyNotCompleteException.class, () -> {
            userService.update(oldUser.getId(), updatedUserDto);
        });
        verify(userRepository, times(1)).findById(oldUser.getId());
        verify(userRepository, never()).save(any());
    }

    @Test
    public void patchUser_success() throws Exception {
        UserEntity oldUser = new UserEntity(1, "hjg", "dubi", "daba");
        UserDtoWithoutEmail patchedUserDto = new UserDtoWithoutEmail("dfdfdfdf", "dadddba");

        when(userRepository.findById(oldUser.getId())).thenReturn(Optional.of(oldUser));
        when(userRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        UserDtoWithoutEmail patchedUser = userService.patch(oldUser.getId(), patchedUserDto);

        verify(userRepository, times(1)).findById(oldUser.getId());

        verify(userRepository, times(1)).save(argThat(userEntity ->
                userEntity.getId().equals(oldUser.getId()) &&
                        userEntity.getUsername().equals(patchedUserDto.getUsername()) &&
                        userEntity.getPassword().equals(patchedUserDto.getPassword()) &&
                        userEntity.getEmail().equals(oldUser.getEmail())
        ));

        Assertions.assertEquals(patchedUserDto.getUsername(), patchedUser.getUsername());
        Assertions.assertEquals(patchedUserDto.getPassword(), patchedUser.getPassword());
        Assertions.assertEquals(oldUser.getEmail(), oldUser.getEmail());
        Assertions.assertEquals(oldUser.getId(), oldUser.getId());
    }

    @Test
    public void patchUser_failure() {
        UserEntity oldUser = new UserEntity(1, "hjg", "dubi", "daba");
        UserDtoWithoutEmail patchedUserDto = new UserDtoWithoutEmail(null, "dadddba");

        when(userRepository.findById(oldUser.getId())).thenReturn(Optional.of(oldUser));

        Assertions.assertThrows(BodyNotCompleteException.class, () -> {
            userService.patch(oldUser.getId(), patchedUserDto);
        });

        verify(userRepository, times(1)).findById(oldUser.getId());
        verify(userRepository, never()).save(any());
    }


    @Test
    public void deleteUser_success() {
        int userId = 1;
        when(userRepository.findById(userId)).thenReturn(Optional.of(new UserEntity()));
        userService.deleteUser(userId);
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    public void deleteUser_failure() {
        int invalidUserId = 1000;
        when(userRepository.findById(invalidUserId)).thenReturn(Optional.empty());
        Assertions.assertThrows(BodyNotCompleteException.class, () -> userService.deleteUser(invalidUserId));
        verify(userRepository, times(1)).findById(invalidUserId);
        verify(userRepository, never()).delete(any(UserEntity.class));
        verify(userRepository, never()).deleteById(anyInt());
    }
}


