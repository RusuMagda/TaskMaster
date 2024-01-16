package com.backend.dam.ServiceTest;

import com.backend.dam.dto.UserDto;
import com.backend.dam.exception.APIException;
import com.backend.dam.exception.ResourceNotFoundException;
import com.backend.dam.mapper.UserMapper;
import com.backend.dam.model.User;
import com.backend.dam.repository.UserRepository;
import com.backend.dam.security.JwtTokenProvider;
import com.backend.dam.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private UserServiceImpl userService;


    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        userService = new UserServiceImpl(userRepository, passwordEncoder);
    }

    @Test
    void testRegister() {

        UserDto userDto = new UserDto();
        userDto.setFirstName("John");
        userDto.setLastName("Doe");
        userDto.setEmail("john.doe@example.com");
        userDto.setPassword("password");


        when(userRepository.existsByEmail(userDto.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(userDto.getPassword())).thenReturn("encodedPassword");


        String resultMessage = userService.register(userDto);


        assertEquals("User registered successfully!", resultMessage);
    }
    @Test
    void testRegisterWithExistingEmail() {

        UserDto userDto = new UserDto();
        userDto.setEmail("existing.email@example.com");

        when(userRepository.existsByEmail(userDto.getEmail())).thenReturn(true);

        assertThrows(APIException.class, () -> userService.register(userDto));
    }

    @Test
    void testDeleteUser() {

        Long userId = 1L;
        User mockUser = new User();
        mockUser.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));

        userService.deleteUser(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void testDeleteNonexistentUser() {

        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.deleteUser(userId));
    }
    @Test
    void testGetUserById() {

        Long userId = 1L;
        User mockUser = new User();
        mockUser.setId(userId);
        mockUser.setFirstName("John");
        mockUser.setLastName("Doe");
        mockUser.setEmail("johnnn.doe@example.com");

        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));

        UserDto resultUserDto = userService.getUserById(userId);

        assertEquals(UserMapper.mapToUserDto(mockUser), resultUserDto);
    }

    @Test
    void testGetAllUser() {

        User user1 = new User( "John", "Doe", "john@example.com", "password");
        User user2 = new User( "Jane", "Doe", "jane@example.com", "password");
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));


        List<UserDto> userDtos = userService.getAllUser();
        assertEquals(2, userDtos.size());
        assertEquals(UserMapper.mapToUserDto(user1), userDtos.get(0));
        assertEquals(UserMapper.mapToUserDto(user2), userDtos.get(1));
    }

    @Test
    void testUpdateUser() {
        Long userId = 1L;
        UserDto updatedUserDto = new UserDto("Updated", "User", "updated@example.com", "password");

        User existingUser = new User("John", "Doe", "john@example.com", "password");
        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0);
            savedUser.setFirstName("Updated");
            savedUser.setLastName("User");
            savedUser.setEmail("updated@example.com");
            savedUser.setPassword("password");
            return savedUser;
        });

        UserDto result = userService.updateUser(userId, updatedUserDto);
        assertEquals(updatedUserDto, result);
    }

    @Test
    void testUpdateUserNotFound() {

        Long userId = 1L;
        UserDto updatedUserDto = new UserDto("Updated", "User", "updated@example.com", "password");

        when(userRepository.findById(userId)).thenReturn(Optional.empty());


        assertThrows(ResourceNotFoundException.class, () -> userService.updateUser(userId, updatedUserDto));
    }

    @Test
    void testDeleteUserS() {

        Long userId = 1L;
        User existingUser = new User( "John", "Doe", "john@example.com", "password");
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));


        userService.deleteUser(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void testDeleteUserNotFound() {

        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());


        assertThrows(ResourceNotFoundException.class, () -> userService.deleteUser(userId));
    }

}
