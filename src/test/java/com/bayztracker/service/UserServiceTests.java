package com.bayztracker.service;

import com.bayztracker.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;


    @Test
    void it_returns_true_when_user_exists() {
        var userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(true);
        var userExists = userService.existById(userId);
        assertTrue(userExists);
    }

    @Test
    void it_returns_false_when_user_exists() {
        var userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(false);
        var userExists = userService.existById(userId);
        assertFalse(userExists);
    }

}
