package com.minderaSchool.userGi.userControllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minderaSchool.userGi.entity.UserEntity;
import com.minderaSchool.userGi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc

public class UserControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;
    @Autowired
    @MockBean
    UserRepository userRepository;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllUsers_success() throws Exception {
        UserEntity user1 = new UserEntity(1, "kjh", "david", "kahjsd@gmail.com");
        UserEntity user2 = new UserEntity(2, "jhg", "leo", "rins@gmail.com");

        List<UserEntity> getAllUsers = new ArrayList<>(Arrays.asList(user1, user2));
        Mockito.when(userRepository.findAll()).thenReturn(getAllUsers);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/user")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[1].username", is("jhg")))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllUsers_failure() throws Exception {
        UserEntity user1 = new UserEntity("david", "elefante", "kahjsd@gmail.com");
        UserEntity user2 = new UserEntity("leo", "rins", "rins@gmail.com");

        List<UserEntity> getAllUsers = new ArrayList<>(Arrays.asList(user1, user2));

        Mockito.when(userRepository.findAll()).thenReturn(null);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put("/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(getAllUsers));

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getUserById_success() throws Exception {
        UserEntity user1 = new UserEntity(1, "titi", "mimi", "riri");
        Mockito.when(userRepository.findById(user1.getId())).thenReturn(java.util.Optional.of(user1));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/user/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.username", is("titi")))
                .andExpect(status().isOk());
    }


    @Test
    public void getUserById_failure() throws Exception {
        UserEntity user1 = new UserEntity("hjvjb", "agua", "aqua");

        Mockito.when(userRepository.findById(1)).thenReturn(Optional.empty());
        Mockito.when(userRepository.save(user1)).thenReturn(user1);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put("/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(user1));

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest());
    }


    @Test
    public void createUser_success() throws Exception {
        UserEntity user1 = new UserEntity("chipi", "chapa", "chipichapa@gmail.com");

        Mockito.when(userRepository.save(user1)).thenReturn(user1);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(user1));

        mockMvc.perform(mockRequest)
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.username", is("chipi")))
                .andExpect(status().isOk());
    }

    @Test
    public void createUser_failure() throws Exception {
        UserEntity user1 = new UserEntity("chipi", "zdg", null);

        Mockito.when(userRepository.save(user1)).thenReturn(user1);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(user1));

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest());
    }


    @Test
    public void updateUser_success() throws Exception {
        UserEntity updatedUser1 = new UserEntity(1, "hjg", "dubi", "daba");
        UserEntity updatedUser2 = new UserEntity(1, "hjg", "dfdfdfdf", "dadddba");

        Mockito.when(userRepository.findById(updatedUser1.getId())).thenReturn(Optional.of(updatedUser1));
        Mockito.when(userRepository.save(updatedUser1)).thenReturn(updatedUser2);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put("/user/{id}",1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(updatedUser2));

        mockMvc.perform(mockRequest)
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.username", is("hjg")))
                .andExpect(status().isOk());
    }


    @Test
    public void updateUser_nullIdFailure() throws Exception {
        UserEntity updatedUser1 = new UserEntity("hh", "agua", "aqua");

        Mockito.when(userRepository.findById(1)).thenReturn(Optional.empty());
        Mockito.when(userRepository.save(updatedUser1)).thenReturn(updatedUser1);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put("/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(updatedUser1));

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest());
    }


    @Test
    public void updateUser_UserNotFoundFailure() throws Exception {
        UserEntity updatedUser = new UserEntity(1, "jhg", "pedro", "milho");

        Mockito.when(userRepository.findById(updatedUser.getId())).thenReturn(Optional.empty());

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put("/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(updatedUser));

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest());
    }


    @Test
    public void deleteUserById_success() throws Exception {
        UserEntity updatedUser = new UserEntity(1, "ahahah", "qwe", "rty");

        Mockito.when(userRepository.findById(updatedUser.getId())).thenReturn(Optional.of(updatedUser));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/user/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    public void deleteUserById_notFound() throws Exception {
        UserEntity updatedUser = new UserEntity("he", "min", "der");

        Mockito.when(userRepository.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/user/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
