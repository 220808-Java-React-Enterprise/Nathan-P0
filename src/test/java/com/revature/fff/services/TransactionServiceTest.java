package com.revature.fff.services;

import com.revature.fff.dao.TransactionDAO;
import com.revature.fff.dao.UserDAO;
import com.revature.fff.models.DBTransaction;
import com.revature.fff.models.DBUser;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;

import java.sql.SQLException;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TransactionServiceTest {

    UUID dummyUserId, dummyCartId, dummyLocationId;
    DBUser dummyUser, dummyUserNoCart, dummyUserNoLoc;
    private final TransactionDAO mockTransactionDAO = mock(TransactionDAO.class);

    @Before
    public void before() {
        dummyUserId = UUID.randomUUID();
        dummyCartId = UUID.randomUUID();
        dummyLocationId = UUID.randomUUID();
        dummyUser = new DBUser(dummyUserId, "dummmyuser", "dummypass", dummyCartId, dummyLocationId, null);
        dummyUserNoCart = new DBUser(dummyUserId, "dummmyuser", "dummypass", null, dummyLocationId, null);
        dummyUserNoLoc = new DBUser(dummyUserId, "dummmyuser", "dummypass", dummyCartId, null, null);
    }
    
    @Test
    public void test_getTransaction_no_cart() throws SQLException {
        try (MockedStatic mockedDAO = mockStatic(TransactionDAO.class);
             MockedStatic mockedUserService = mockStatic(UserService.class)) {
            mockedDAO.when(TransactionDAO::getInstance).thenReturn(mockTransactionDAO);
            mockedUserService.when(UserService::getActiveUser).thenReturn(dummyUser);
            when(mockTransactionDAO.put(any())).thenReturn(mock(UUID.class));
            when(mockTransactionDAO.get(any())).thenReturn(mock(DBTransaction.class));

            DBTransaction transaction = TransactionService.getTransaction(false);
            
            assertNotNull(transaction);
            verify(mockTransactionDAO, times(1)).put(any());
            verify(mockTransactionDAO, times(1)).get(any());
        }
    }

    @Test
    public void test_getTransaction_cart() throws SQLException {
        try (MockedStatic mockedDAO = mockStatic(TransactionDAO.class);
             MockedStatic mockedUserService = mockStatic(UserService.class)) {
            mockedDAO.when(TransactionDAO::getInstance).thenReturn(mockTransactionDAO);
            mockedUserService.when(UserService::getActiveUser).thenReturn(dummyUser);
            when(mockTransactionDAO.put(any())).thenReturn(mock(UUID.class));
            when(mockTransactionDAO.get(any())).thenReturn(mock(DBTransaction.class));

            DBTransaction transaction = TransactionService.getTransaction(true);

            assertNotNull(transaction);
            verify(mockTransactionDAO, times(0)).put(any());
            verify(mockTransactionDAO, times(1)).get(any());
        }
    }

    @Test
    public void test_getTransaction_cart_null() throws SQLException {
        try (MockedStatic mockedDAO = mockStatic(TransactionDAO.class);
             MockedStatic mockedUserService = mockStatic(UserService.class)) {
            mockedDAO.when(TransactionDAO::getInstance).thenReturn(mockTransactionDAO);
            mockedUserService.when(UserService::getActiveUser).thenReturn(dummyUserNoCart);
            when(mockTransactionDAO.put(any())).thenReturn(mock(UUID.class));
            when(mockTransactionDAO.get(any())).thenReturn(mock(DBTransaction.class));

            DBTransaction transaction = TransactionService.getTransaction(false);

            assertNotNull(transaction);
            verify(mockTransactionDAO, times(1)).put(any());
            verify(mockTransactionDAO, times(1)).get(any());
        }
    }

    @Test(expected = NoStore.class)
    public void test_getTransaction_location_null() throws SQLException {
        try (MockedStatic mockedDAO = mockStatic(TransactionDAO.class);
             MockedStatic mockedUserService = mockStatic(UserService.class)) {
            mockedDAO.when(TransactionDAO::getInstance).thenReturn(mockTransactionDAO);
            mockedUserService.when(UserService::getActiveUser).thenReturn(dummyUserNoLoc);
            when(mockTransactionDAO.put(any())).thenReturn(mock(UUID.class));
            when(mockTransactionDAO.get(any())).thenReturn(mock(DBTransaction.class));

            DBTransaction transaction = TransactionService.getTransaction(false);

            assertNotNull(transaction);
            verify(mockTransactionDAO, times(1)).put(any());
            verify(mockTransactionDAO, times(1)).get(any());
        }
    }
}