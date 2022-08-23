package com.revature.fff.services;

import com.revature.fff.dao.UserDAO;
import com.revature.fff.models.DBLocation;
import com.revature.fff.models.DBUser;

import java.sql.SQLException;
import java.util.UUID;

public class UserService {
    private static DBUser activeUser;

    public static void checkUsername(String username) throws InvalidInput {
        if (username.length() < 4) throw new InvalidInput("Username must be at least 4 characters.");
        if (username.length() > 20) throw new InvalidInput("Username must be at most 20 characters.");
        if (!username.matches("^[a-zA-Z0-9_-]{4,16}$"))
            throw new InvalidInput("Username must contain only letters, numbers, '_' and '-'");
    }

    public static void checkPassword(String password) throws InvalidInput {
        if (password.length() < 8) throw new InvalidInput("Password must be at least 8 characters.");
        if (password.length() > 20) throw new InvalidInput("Password must be at most 20 characters.");
        return;
    }

    public static void signup(String username, String password) {
        DBUser user = new DBUser(null, username, password,null,null,null);
        try {
            checkUsername(username);
            checkPassword(password);
            UUID id = UserDAO.getInstance().put(user);
            activeUser = UserDAO.getInstance().get(id);
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505"))
                throw new UserExists("That username has already been taken. Please choose another.");
            throw new RuntimeException(e);
        }
    }

    public static void login(String username, String password) {
        activeUser = UserDAO.getInstance().getByUsernameAndPassword(username, password);
        if (activeUser == null) throw new UserNotFound("A user with the given credentials could not be found.");
    }
    
    public static void logout() { activeUser = null; }

    public static DBUser getActiveUser() {
        return activeUser;
    }
    
    public static void setLocation(DBLocation location) {
        if (activeUser != null) UserDAO.getInstance().setLocation(activeUser, location);
    }
}
