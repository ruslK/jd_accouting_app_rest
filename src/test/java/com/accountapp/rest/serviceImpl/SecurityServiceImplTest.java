package com.accountapp.rest.serviceImpl;

import com.accountapp.rest.Utils.TestUtils;
import com.accountapp.rest.entity.User;
import com.accountapp.rest.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SecurityServiceImplTest {

    @Mock
    UserService userService;

    @InjectMocks
    SecurityServiceImpl securityServiceImpl;

    @Test
    void loadUserByUsernameTest() throws Exception {
        User user = TestUtils.setUpUser();
        lenient().when(userService.findByUserName(Mockito.anyString())).thenReturn(user);
        UserDetails userDetails = securityServiceImpl.loadUserByUsername(Mockito.anyString());
        assertNotNull(userDetails);
        assertEquals(userDetails.getUsername(), String.valueOf(user.getId()));
        assertEquals(userDetails.getPassword(), user.getPassword());
        assertEquals(userDetails.getAuthorities().toArray()[0].toString(), user.getRole().getName());
    }

    @Test
    void loadUserByUsernameNotExistTest() throws Exception {
        when(userService.findByUserName("")).thenThrow(new RuntimeException("Account not found"));
        Throwable exception = assertThrows(RuntimeException.class, () -> securityServiceImpl.loadUserByUsername(""));
        assertEquals(exception.getMessage(), "Account not found");
    }

    @Test
    void loadUser() throws Exception {
        User user = new User();
        lenient().when(userService.findByUserName(Mockito.anyString())).thenReturn(user);
        User loadedUser = securityServiceImpl.loadUser(Mockito.anyString());
        assertNotNull(user);
        assertEquals(loadedUser, user);
    }

    @Test
    void loadUserNotExistExceptionTest() throws Exception {
        when(userService.findByUserName("")).thenThrow(new RuntimeException("Account not found"));
        Throwable exception = assertThrows(RuntimeException.class, () -> securityServiceImpl.loadUser(""));
        assertEquals(exception.getMessage(), "Account not found");
    }
}