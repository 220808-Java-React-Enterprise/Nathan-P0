package com.revature.fff.services;

import com.revature.fff.dao.Database;
import com.revature.fff.dao.UserDAO;
import com.revature.fff.models.User;

public class UserService {
    private static User activeUser;
    public static void checkUsername(String username) throws InvalidInput {
        if (username.length() < 4) throw new InvalidInput("Username must be at least 4 characters.");
        if (username.length() > 20) throw new InvalidInput("Username must be at most 20 characters.");
        if (!username.matches("^[a-zA-Z0-9_-]{4,16}$"))
            throw new InvalidInput("Username must contain only letters, numbers, '_' and '-'");
    }

    public static void checkPassword(String password) throws InvalidInput {
//        if(!password.matches("/^[a-zA-Z0-9_-]{4,16}$/"))
//            throw new InvalidInput("Username must be 4 to 16 characters and only include letters, numbers, '_' and
//            '-'");
        return;
    }

    public static void signup(String username, String password) {
        User user = new User(null, username, password, null, null);
        UserDAO ud = new UserDAO();
        ud.put(user);
    }

    public static void login(String username, String password) {
        UserDAO ud = new UserDAO();
        activeUser = ud.getByUsernameAndPassword(username, password);
        if (activeUser == null) throw new InvalidInput("A user with the given credentials could not be found.");
    }

    public static User getActiveUser() {
        return activeUser;
    }
}
