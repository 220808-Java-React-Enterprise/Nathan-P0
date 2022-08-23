package com.revature.fff.services;

import com.revature.fff.dao.UserDAO;
import com.revature.fff.models.DBUser;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private UserService sut;
    private final UserDAO mockUserDAO = mock(UserDAO.class);
    private final DBUser mockUser = mock(DBUser.class);

    @Test
    public void test_checkUsername_valid() {
        UserService.checkUsername("auser");
    }

    @Test
    public void test_checkUsername_valid_with_symbols() {
        UserService.checkUsername("a-us_er");
    }

    @Test(expected = InvalidInput.class)
    public void test_checkUsername_too_short() {
        UserService.checkUsername("a");
    }

    @Test(expected = InvalidInput.class)
    public void test_checkUsername_too_long() {
        UserService.checkUsername("averyveryverylonguser");
    }

    @Test(expected = InvalidInput.class)
    public void test_checkUsername_invalid() {
        UserService.checkUsername("a@user");
    }

    @Test
    public void test_checkPassword_valid() {
        UserService.checkPassword("agoodp@ssw0rd!");
    }

    @Test(expected = InvalidInput.class)
    public void test_checkPassword_too_short() {
        UserService.checkPassword("passwrd");
    }

    @Test(expected = InvalidInput.class)
    public void test_checkPassword_too_long() {
        UserService.checkPassword("averyverylongpassword");
    }

    @Test
    public void test_signup_valid() throws SQLException {
        try (MockedStatic mockedDAO = mockStatic(UserDAO.class)) {
            String username = "auser";
            String password = "Passw0rd";
            mockedDAO.when(UserDAO::getInstance).thenReturn(mockUserDAO);
            when(mockUserDAO.put(any())).thenReturn(mock(UUID.class));
            when(mockUserDAO.get(any())).thenReturn(mock(DBUser.class));

            UserService.signup(username, password);

            verify(mockUserDAO, times(1)).put(any());
            verify(mockUserDAO, times(1)).get(any());
        }
    }


    @Test(expected = UserExists.class)
    public void test_signup_exists() throws SQLException {
        try (MockedStatic mockedDAO = mockStatic(UserDAO.class)) {
            String username = "auser";
            String password = "Passw0rd";
            SQLException uniqueViolation = mock(SQLException.class);
            when(uniqueViolation.getSQLState()).thenReturn("23505");

            mockedDAO.when(UserDAO::getInstance).thenReturn(mockUserDAO);
            when(mockUserDAO.put(any())).thenThrow(uniqueViolation);
            when(mockUserDAO.get(any())).thenReturn(mock(DBUser.class));

            UserService.signup(username, password);

            verify(mockUserDAO, times(1)).put(any());
            verify(mockUserDAO, times(1)).get(any());
        }
    }

    @Test
    public void test_login_valid() {
        try (MockedStatic mockedDAO = mockStatic(UserDAO.class)) {
            String username = "auser";
            String password = "Passw0rd";
            mockedDAO.when(UserDAO::getInstance).thenReturn(mockUserDAO);
            when(mockUserDAO.getByUsernameAndPassword(username, password)).thenReturn(mock(DBUser.class));
            
            UserService.login(username, password);
            
            verify(mockUserDAO, times(1)).getByUsernameAndPassword(username, password);
        }
    }

    @Test(expected = UserNotFound.class)
    public void test_login_user_missing() {
        try (MockedStatic mockedDAO = mockStatic(UserDAO.class)) {
            String username = "auser";
            String password = "Passw0rd";
            
            mockedDAO.when(UserDAO::getInstance).thenReturn(mockUserDAO);

            UserService.login(username, password);

            verify(mockUserDAO, times(1)).getByUsernameAndPassword(username, password);
        }
    }
}
