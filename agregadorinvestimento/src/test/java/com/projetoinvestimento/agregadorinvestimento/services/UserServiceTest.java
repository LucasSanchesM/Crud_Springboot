package com.projetoinvestimento.agregadorinvestimento.services;

import java.time.Instant;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import org.mockito.junit.jupiter.MockitoExtension;

import com.projetoinvestimento.agregadorinvestimento.controller.CreateUserDto;
import com.projetoinvestimento.agregadorinvestimento.entity.User;
import com.projetoinvestimento.agregadorinvestimento.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;


    @Captor
    private ArgumentCaptor<UUID> uuidArgumentCaptor;


    @Nested
    class createUser{
        @Test
        @DisplayName("Should create user successfully")
        void shouldCreateUserWithSucess(){
            //Arrange
            var user = new User(UUID.randomUUID(),
                                "lucas",
                                "lucas@email.com",
                                "12345678",
                                Instant.now(),
                                null);

            doReturn(user).when(userRepository).save(userArgumentCaptor.capture());
            var input = new CreateUserDto("lucas", "lucas@email.com", "12345678");
            //Act
            var output =userService.createUser(input);
            assertNotNull(output);
            var userCaptured = userArgumentCaptor.getValue();
            assertEquals(input.username(), userCaptured.getUsername());
            assertEquals(input.email(), userCaptured.getEmail());
            assertEquals(input.password(), userCaptured.getPassword());
        }

        @Test
        @DisplayName("Should throw exception when error occurs")
        void shouldThrowExceptionWhenErrorOccurs() {
            //Arrange
            var input = new CreateUserDto("lucas",
                                "lucas@email.com",
                                "12345678"
                            );

            doThrow(new RuntimeException()).when(userRepository).save(any());
            //Act
            assertThrows(RuntimeException.class, () -> userService.createUser(input));

        }
    }


    @Nested
    class getUserById{
        @Test
        @DisplayName("Should return user when user exists")
        void shouldReturnUserWhenUserExists(){
            var user = new User(UUID.randomUUID(),
                                "lucas",
                                "lucas@email.com",
                                "12345678",
                                Instant.now(),
                                null);
            doReturn(java.util.Optional.of(user)).when(userRepository).findById(uuidArgumentCaptor.capture());

            var output = userService.getUserById(user.getId().toString());
            assertTrue(output.isPresent());
            assertEquals(user.getId(), uuidArgumentCaptor.getValue());
        }
    }
}
