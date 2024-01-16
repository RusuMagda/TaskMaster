package com.backend.dam.ControllerTest;

import com.backend.dam.controller.ProjectController;
import com.backend.dam.controller.UserController;
import com.backend.dam.dto.JwtAuthResponse;
import com.backend.dam.dto.LoginDto;
import com.backend.dam.dto.UserDto;
import com.backend.dam.service.ProjectService;
import com.backend.dam.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class UserControllerTest {
    @InjectMocks
    private UserController userController;
    @Mock
    private UserService userService;

    @Test
    void registerUser() {
        UserDto userDto = new UserDto();
        userDto.setEmail("testUser");

        String response = "User registered successfully";

        when(userService.register(userDto)).thenReturn(response);

        ResponseEntity<String> responseEntity = userController.register(userDto);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(response, responseEntity.getBody());

        verify(userService, times(1)).register(userDto);
    }

    @Test
    void loginUser() {
        // Mock input
        LoginDto loginDto = new LoginDto();
        loginDto.setEmail("testUser");
        loginDto.setPassword("testPassword");

        // Mock output
        String token = "dummyToken";

        when(userService.login(loginDto)).thenReturn(token);

        ResponseEntity<JwtAuthResponse> responseEntity = userController.login(loginDto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(token, responseEntity.getBody().getAccessToken());

        verify(userService, times(1)).login(loginDto);
    }

    @Test
    void getUserById() {
        Long userId = 1L;

        // Mock output
        UserDto userDto = new UserDto();
        userDto.setId(userId);
        userDto.setEmail("testUser");

        when(userService.getUserById(userId)).thenReturn(userDto);

        ResponseEntity<UserDto> responseEntity = userController.getUserById(userId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(userDto, responseEntity.getBody());

        verify(userService, times(1)).getUserById(userId);
    }

    @Test
    void getAllUsers() {
        List<UserDto> users = Arrays.asList(new UserDto(), new UserDto());

        when(userService.getAllUser()).thenReturn(users);

        ResponseEntity<List<UserDto>> responseEntity = userController.getAllUser();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(users, responseEntity.getBody());

        verify(userService, times(1)).getAllUser();
    }

    @Test
    void updateUser() {
        Long userId = 1L;
        UserDto updatedUser = new UserDto();
        updatedUser.setEmail("updatedUser");

        UserDto userDto = new UserDto();
        userDto.setId(userId);
        userDto.setEmail("updatedUser");

        when(userService.updateUser(userId, updatedUser)).thenReturn(userDto);

        ResponseEntity<UserDto> responseEntity = userController.updateUser(userId, updatedUser);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(userDto, responseEntity.getBody());

        verify(userService, times(1)).updateUser(userId, updatedUser);
    }

    @Test
    void deleteUser() {
        Long userId = 1L;

        ResponseEntity<String> responseEntity = userController.deleteUser(userId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("User deleted successfully!", responseEntity.getBody());

        verify(userService, times(1)).deleteUser(userId);
    }











































}

