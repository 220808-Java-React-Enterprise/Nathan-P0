package com.revature.fff.ui;

import com.revature.fff.models.User;
import com.revature.fff.services.UserService;

public class MainMenu extends Screen {
    public MainMenu(ScreenManager sm) {
        super(sm);
        User user = UserService.getActiveUser();
        if (user == null) sm.setScreen(new Login(sm));
        Label welcome = new Label("Welcome " + user.getUsername() + " (" + user.getId() + ")");
        welcome.setPosition(2,2);
        components.add(welcome);
    }
}
