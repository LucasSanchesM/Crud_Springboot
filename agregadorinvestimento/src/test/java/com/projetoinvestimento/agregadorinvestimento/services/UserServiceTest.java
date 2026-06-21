package com.projetoinvestimento.agregadorinvestimento.services;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.mockito.junit.jupiter.MockitoExtension;

import com.projetoinvestimento.agregadorinvestimento.controller.dtos.CreateUserDto;
import com.projetoinvestimento.agregadorinvestimento.controller.dtos.UpdateUserDto;
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
        @DisplayName("Should return user when optional is present")
        void shouldReturnUserWithSucessWhenOptionalIsPresent(){
            var user = new User(UUID.randomUUID(),
                                "lucas",
                                "lucas@email.com",
                                "12345678",
                                Instant.now(),
                                null);
            doReturn(Optional.of(user)).when(userRepository).findById(uuidArgumentCaptor.capture());

            var output = userService.getUserById(user.getId().toString());
            assertTrue(output.isPresent());
            assertEquals(user.getId(), uuidArgumentCaptor.getValue());
        }

        @Test
        @DisplayName("Should return user when optional is present")
        void shouldReturnUserWithSucessWhenOptionalIsEmpty(){
            var user = new User(UUID.randomUUID(),
                                "lucas",
                                "lucas@email.com",
                                "12345678",
                                Instant.now(),
                                null);
            doReturn(Optional.empty()).when(userRepository).findById(uuidArgumentCaptor.capture());

            var output = userService.getUserById(user.getId().toString());
            assertTrue(output.isEmpty());
            assertEquals(user.getId(), uuidArgumentCaptor.getValue());
        }
    }


    @Nested
    class listarUsuarios{
        @Test
        @DisplayName("Should return list of users")
        void shouldReturnListOfUsers(){
            var user1 = new User (UUID.randomUUID(),
                                "Lucas",
                                "luucaassanches@gmail.com",
                                "12345678",
                                Instant.now(),
                                null);
            var user2 = new User (UUID.randomUUID(),
                                "Lucas",
                                "luucaassanches@gmail.com",
                                "12345678",
                                Instant.now(),
                                null);

            doReturn(List.of(user1, user2)).when(userRepository).findAll();

            var output = userService.listarUsuarios();
            assertNotNull(output);
            assertEquals(2, output.size());
        }
    }

    @Nested
    class deleteUserById{
        @Test
        @DisplayName("Should return succes when user exist and them is deleted")
        void shouldReturnDeleteSuccessWhenUserExist(){
            //Arrange
            doReturn(true).when(userRepository).existsById(uuidArgumentCaptor.capture());
            doNothing().when(userRepository).deleteById(uuidArgumentCaptor.capture());
            var userId = UUID.randomUUID();
            //Act
            userService.deleteUserById(userId.toString());
            //Assert
            var idList = uuidArgumentCaptor.getAllValues();
            assertEquals(userId, idList.get(0));
            assertEquals(userId, idList.get(1));

            verify(userRepository, times(1)).existsById(idList.get(0));
            verify(userRepository, times(1)).existsById(idList.get(1));
        }

        @Test
        @DisplayName("Should return sucess when user dont't exist")
        void shouldReturnSucessWhenUserDontExist(){
            //Arrange            
            doReturn(false).when(userRepository).existsById(uuidArgumentCaptor.capture());
            var userId = UUID.randomUUID();
            //Act
            userService.deleteUserById(userId.toString());
            /*The method named getAllvalues return the values did catch by capture method, this is important for verify if the flux is doing fine*/
            var idList = uuidArgumentCaptor.getAllValues();
            //Asser
            assertEquals(userId, idList.get(0));
            
            verify(userRepository, times(0)).deleteById(idList.get(0));
            verify(userRepository, times(1)).existsById(idList.get(0));
        }
    }
    
    @Nested
    class updateUserById{
        @Test
        @DisplayName("Should return sucess when user exist and did edit")
        void ShouldReturnSucessWhenUserExistAndDidEdit(){
            //Arrange
            var userId = UUID.randomUUID();
            var userForEdit = new User(userId,
                                        "Joao",
                                        "joazinho@email.com",
                                        "12345678",
                                        Instant.now(),
                                        null
            );
            var userEdits = new UpdateUserDto("Geraldo", "87654321");
            var input1 = userId.toString();
            var input2 =  userEdits;
            doReturn(Optional.of(userForEdit)).when(userRepository).findById(uuidArgumentCaptor.capture());
            doReturn(userForEdit).when(userRepository).save(userArgumentCaptor.capture());

            //Act
            userService.updateUserById(input1, input2);
            //Assert
            var idCaptured = uuidArgumentCaptor.getValue();
            var userCaptured = userArgumentCaptor.getValue();
            assertEquals("Geraldo", userCaptured.getUsername());
            assertEquals("87654321", userCaptured.getPassword());

            verify(userRepository, times(1)).findById(userId);
            verify(userRepository, times(1)).save(any(User.class));
        }
    }
}
