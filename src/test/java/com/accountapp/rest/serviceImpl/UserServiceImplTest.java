package com.accountapp.rest.serviceImpl;

import com.accountapp.rest.Utils.TestUtils;
import com.accountapp.rest.entity.User;
import com.accountapp.rest.exception.ApplicationException;
import com.accountapp.rest.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;

    @Test
    void getAllUsersForRootTest() throws ApplicationException {
        TestUtils.onSetUpAuthentication("Root");
        List<User> userList = new ArrayList<>();
        lenient().when(userRepository.getUsersManagedByRoot()).thenReturn(userList);
        List<User> searchedUsers = userService.getAllUsers();
        verify(userRepository, only()).getUsersManagedByRoot();
        verify(userRepository, times(1)).getUsersManagedByRoot();
        verify(userRepository, never()).getUsersManagedByAdmin(Mockito.anyLong());
        assertNotNull(searchedUsers);
        assertEquals(searchedUsers, userList);
    }

    @Test
    void getAllUsersForAdminTest() throws ApplicationException {
        TestUtils.onSetUpAuthentication("Admin");
        List<User> userList = new ArrayList<>();
        lenient().when(userRepository.getUsersManagedByAdmin(Mockito.anyLong())).thenReturn(userList);
        List<User> searchedUsers = userService.getAllUsers();
        verify(userRepository, only()).getUsersManagedByAdmin(Mockito.anyLong());
        verify(userRepository, times(1)).getUsersManagedByAdmin(Mockito.anyLong());
        verify(userRepository, never()).getUsersManagedByRoot();
        assertNotNull(searchedUsers);
        assertEquals(searchedUsers, userList);
    }

    @Test
    void getAllUsersForExceptionTest() throws ApplicationException {
        TestUtils.onSetUpAuthentication("Manager");
        Throwable exception = assertThrows(ApplicationException.class, () -> userService.getAllUsers());
        verify(userRepository, never()).getUsersManagedByRoot();
        verify(userRepository, never()).getUsersManagedByAdmin(Mockito.anyLong());
        assertEquals(exception.getMessage(), "Role - Manager - does not have access to get List of Users");
    }

    @Test
    void deleteUserByRoot() throws ApplicationException {
        TestUtils.onSetUpAuthentication("Root");
        List<User> userList = new ArrayList<>();
        User toDelete = new User();
        toDelete.setId(1L);
        userList.add(toDelete);
        lenient().when(userRepository.getUsersManagedByRoot()).thenReturn(userList);
        lenient().when(userRepository.save(Mockito.any())).thenReturn(toDelete);
        User deletedUser = userService.deleteUser(1l);
        verify(userRepository, times(1)).getUsersManagedByRoot();
        verify(userRepository, times(1)).save(Mockito.any());
        verify(userRepository, never()).getUsersManagedByAdmin(Mockito.anyLong());
        assertNotNull(deletedUser);
        assertEquals(deletedUser, toDelete);
    }

    @Test
    void deleteUserByAdmin() throws ApplicationException {
        TestUtils.onSetUpAuthentication("Admin");
        List<User> userList = new ArrayList<>();
        User toDelete = new User();
        toDelete.setId(1L);
        userList.add(toDelete);
        lenient().when(userRepository.getUsersManagedByAdmin(Mockito.anyLong())).thenReturn(userList);
        lenient().when(userRepository.save(Mockito.any())).thenReturn(toDelete);
        User deletedUser = userService.deleteUser(1l);
        verify(userRepository, times(1)).getUsersManagedByAdmin(Mockito.anyLong());
        verify(userRepository, times(1)).save(Mockito.any());
        verify(userRepository, never()).getUsersManagedByRoot();
        assertNotNull(deletedUser);
        assertEquals(deletedUser, toDelete);
    }

    @Test
    void deleteUserByAdminException() throws ApplicationException {
        TestUtils.onSetUpAuthentication("Admin");
        List<User> userList = new ArrayList<>();
        lenient().when(userRepository.getUsersManagedByAdmin(Mockito.anyLong())).thenReturn(userList);
        Throwable exception = assertThrows(ApplicationException.class, () -> userService.deleteUser(1L));
        assertEquals(exception.getMessage(), "User ID 1 not found for your role");
        verify(userRepository, times(1)).getUsersManagedByAdmin(Mockito.anyLong());
        verify(userRepository, never()).getUsersManagedByRoot();
        verify(userRepository, never()).save(Mockito.any());
    }

    @Test
    void deleteUserByRootException() throws ApplicationException {
        TestUtils.onSetUpAuthentication("Root");
        List<User> userList = new ArrayList<>();
        lenient().when(userRepository.getUsersManagedByRoot()).thenReturn(userList);
        Throwable exception = assertThrows(ApplicationException.class, () -> userService.deleteUser(1L));
        assertEquals(exception.getMessage(), "User ID 1 not found for your role");
        verify(userRepository, times(1)).getUsersManagedByRoot();
        verify(userRepository, never()).getUsersManagedByAdmin(Mockito.anyLong());
        verify(userRepository, never()).save(Mockito.any());
    }
}