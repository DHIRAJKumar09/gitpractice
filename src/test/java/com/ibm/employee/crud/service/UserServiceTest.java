package com.ibm.employee.crud.service;

import com.ibm.employee.crud.entity.User;
import com.ibm.employee.crud.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;



    @Test
    void shouldCreateUserWhenValidData22() {
        User user = new User();
        user.setName("Rahul");
        user.setEmail("rahul@gmail.com");
        when(userRepository.save(user)).thenReturn(user);

        //When
        User saved = userService.create(user);
        //Then
        assertEquals("Rahul",saved.getName());
        assertEquals("rahul@gmail.com",saved.getEmail());
        verify(userRepository).save(user);


    }
    @Test
    void shouldFailWhenNameOrEmailIsNull(){
        User user = new User();
        user.setName(null);
        user.setEmail("rahul@gmail.com");

        assertThrows(IllegalArgumentException.class,()->userService.create(user));
        verify(userRepository,never()).save(any());
    }

    @Test
    void shouldReturnUserWhenIdExists() {
        User user = new User();
        user.setId(1L);
        user.setName("Rahul");
        user.setEmail("rahul@gmail.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.get(1L);

        assertEquals("Rahul",result.getName());


    }
    @Test
    void shouldFailWhenUserNotFound(){
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,()->userService.get(1L));
    }
    @Test
    void shouldUpdateWhenUserExists(){
        User existing = new User();
        existing.setId(1l);
        existing.setName("Rahul");
        existing.setEmail("rahul@gmail.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(userRepository.save(any())).thenReturn(existing);

        User update = new User();
        update.setName("RahulRaj");
        update.setEmail("rahul09@gmail.com");

        User result = userService.update(1L,update);
        assertEquals("RahulRaj",result.getName());
        assertEquals("rahul09@gmail.com",result.getEmail());
    }
    @Test
    void shouldFailWhenDeletingNonExistingUser(){
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class,()->userService.delete(1L));

    }


}