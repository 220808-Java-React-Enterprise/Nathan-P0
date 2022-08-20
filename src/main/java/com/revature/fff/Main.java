package com.revature.fff;

import com.revature.fff.dao.Database;
import com.revature.fff.dao.UserDAO;
import com.revature.fff.models.User;
import com.revature.fff.ui.Console;
import com.revature.fff.ui.Login;
import com.revature.fff.ui.ScreenManager;
import org.postgresql.util.PSQLException;

import java.sql.SQLException;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        Console.initConsole(25, 80);
//        try {
//            Database.getConnection();
//            User user = new User(null, "auser", "passw0rd", null, null);
//            UserDAO ud = UserDAO.getInstance();
//            Database.getConnection().setAutoCommit(false);
//            UUID id = ud.put(user);
//            System.out.println(id);
//            Database.getConnection().commit();
//        } catch (SQLException e) {
//            System.out.println(e.getSQLState());
////            throw new RuntimeException(e);
//        }

        ScreenManager sm = new ScreenManager();
        sm.setScreen(new Login(sm));
        sm.run();

//        Random r = new Random();
//        for (int x = 0; x < 10; x++) {
//            c.clear();
//            c.setPosition(0, 0);
//            c.resetMargins();
//            c.print("\u00da");
//            for (int i = 1; i < 80; i++) c.print("-");
//            c.setMargins(5, 19, 20, 59);
//            for (int i = 0; i < 10; i++) {
//                c.setPosition(r.nextInt(24), r.nextInt(70));
//                c.print("Hello World!");
//            }
//            c.flush();
//            c.readLine();
//        }
    }
}
